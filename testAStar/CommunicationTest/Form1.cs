using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using CommunicationTest.DB.Sqlite;
using System.Threading;

namespace CommunicationTest
{
    public partial class Form1 : Form
    {
        private SqliteCommunicationLogHelper _sqliteHelper;
        private BindingList<MateWeb> _bindingWebs;
        private BindingList<MateNoticeUser> _bindingNoticeUsers;
        private MateAdmin _admin;
        private List<Thread> _threadList = new List<Thread>();

        public Form1()
        {
            InitializeComponent();
            Init();
        }
        private void Init()
        {
            dataGridViewWebsite.AutoGenerateColumns = false;
            dataGridViewNoticeUserEmail.AutoGenerateColumns = false;
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            _sqliteHelper = new SqliteCommunicationLogHelper("Log.db");
            _bindingWebs = new BindingList<MateWeb>(_sqliteHelper.GetWebs(true).ToList());
            _sqliteHelper.CloseCon();
            _admin = _sqliteHelper.GetAdmin();

            dataGridViewWebsite.DataSource = _bindingWebs;
        }

        private void toolStripButtonSetting_Click(object sender, EventArgs e)
        {
            SettingForm form = new SettingForm(_sqliteHelper);
            form.ShowDialog(this);
            _admin = _sqliteHelper.GetAdmin();
        }

        private void toolStripButtonSave_Click(object sender, EventArgs e)
        {
            _sqliteHelper.SaveWebs(_bindingWebs, true);
            toolStripButtonListen.Enabled = true;
        }

        private void buttonDeleteWebsiteUrl_Click(object sender, EventArgs e)
        {
            foreach (DataGridViewRow row in dataGridViewWebsite.SelectedRows)
            {
                toolStripButtonListen.Enabled = false;
                _bindingWebs.Remove(row.DataBoundItem as MateWeb);
            }
        }

        private void buttonAddWebsiteUrl_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrWhiteSpace(textBoxWebsiteUrl.Text))
            {
                return;
            }

            toolStripButtonListen.Enabled = false;
            string urltemp = textBoxWebsiteUrl.Text.Trim();
            if (_bindingWebs.FirstOrDefault(record => record.WebUrl == urltemp) == null)
            {
                _bindingWebs.Add(new MateWeb()
                {
                    WebUrl = urltemp,
                    DetectionInterval = _admin.DetectionInterval
                });
            }
            if (dataGridViewWebsite.Rows.Count > 0)
            {
                dataGridViewWebsite.Rows[dataGridViewWebsite.Rows.Count - 1].Selected = true;
            }
            textBoxWebsiteUrl.Text = string.Empty;
        }


        private void dataGridViewWebsite_SelectionChanged(object sender, EventArgs e)
        {
            if (dataGridViewWebsite.SelectedRows.Count == 1)
            {
                MateWeb selectWeb = dataGridViewWebsite.SelectedRows[0].DataBoundItem as MateWeb;
                _bindingNoticeUsers = new BindingList<MateNoticeUser>(selectWeb.NoticeUsers);
                dataGridViewNoticeUserEmail.DataSource = _bindingNoticeUsers;
            }
            else
            {
                _bindingNoticeUsers = new BindingList<MateNoticeUser>();
                dataGridViewNoticeUserEmail.DataSource = _bindingNoticeUsers;
            }
        }

        private void buttonDeleteNoticUserEmail_Click(object sender, EventArgs e)
        {
            foreach (DataGridViewRow row in dataGridViewNoticeUserEmail.SelectedRows)
            {
                toolStripButtonListen.Enabled = false;
                _bindingNoticeUsers.Remove(row.DataBoundItem as MateNoticeUser);
            }
        }

        private void buttonAddNoticUserEmail_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrWhiteSpace(textBoxNoticUserEmail.Text))
            {
                return;
            }
            toolStripButtonListen.Enabled = false;
            string emailtemp = textBoxNoticUserEmail.Text.Trim();
            if (_bindingNoticeUsers.FirstOrDefault(record => record.EmailAddress == emailtemp) == null)
            {
                _bindingNoticeUsers.Add(new MateNoticeUser()
                {
                    EmailAddress = emailtemp
                });
            }
            textBoxNoticUserEmail.Text = string.Empty;
        }

        private void buttonLogExport_Click(object sender, EventArgs e)
        {

        }

        private void buttonAllLogExport_Click(object sender, EventArgs e)
        {

        }

        private void checkBoxShowSelectWebsiteOnly_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void toolStripButtonListen_Click(object sender, EventArgs e)
        {
            if (toolStripButtonListen.Text == @"点击开始监听")
            {
                toolStripButtonListen.BackColor = System.Drawing.Color.Red;
                toolStripButtonListen.Text = @"正在监听，点击停止监听";
                toolStripButtonListen.Image = Properties.Resources.listen;
                buttonDeleteWebsiteUrl.Enabled = false;
                buttonAddWebsiteUrl.Enabled = false;
                buttonDeleteNoticUserEmail.Enabled = false;
                buttonAddNoticUserEmail.Enabled = false;
                StartListen();
            }
            else
            {
                toolStripButtonListen.BackColor = System.Drawing.SystemColors.Control;
                toolStripButtonListen.Text = @"点击开始监听";
                toolStripButtonListen.Image = Properties.Resources.not_listen;
                buttonDeleteWebsiteUrl.Enabled = true;
                buttonAddWebsiteUrl.Enabled = true;
                buttonDeleteNoticUserEmail.Enabled = true;
                buttonAddNoticUserEmail.Enabled = true;
                StopListen();
            }
        }

        private void StartListen()
        {
            foreach (MateWeb mateWeb in _bindingWebs)
            {
                Thread t = new Thread(new ParameterizedThreadStart(WebListenThread));
                t.Start(mateWeb);
            }
        }
        private void StopListen()
        {
            foreach (Thread thread in _threadList)
            {
                if (thread.IsAlive)
                {
                    thread.Abort();
                }
            }
            _threadList.Clear();
        }

        private void WebListenThread(object obj)
        {
            if (obj is MateWeb)
            {
                while (true)
                {
                    MateWeb mateWeb = obj as MateWeb;
                    string stateCode = WebHelper.GetRequestStatusCode(mateWeb.WebUrl).ToString();

                    textBoxErrorLog.Invoke(new Action(
                        () =>
                        {
                            textBoxErrorLog.AppendText(string.Format("{{Url:{0}  StateCode:{1}  LogTime:{2}}}{3}", mateWeb.WebUrl, stateCode,DateTime.Now, Environment.NewLine));
                            textBoxErrorLog.ScrollToCaret();
                        }));

                    if (stateCode != mateWeb.LastStateCode)
                    {
                        mateWeb.LastStateCode = stateCode;
                        MateErrorLog mateErrorLog = new MateErrorLog()
                        {
                            ErrorTime = DateTime.Now,
                            DetectionInterval = mateWeb.DetectionInterval,
                            WebId = mateWeb.Id,
                            StateCode = stateCode
                        };
                        _sqliteHelper.AddErrorLog(mateErrorLog);
                        if(stateCode!="200")
                        {
                            EmailHelper.SendMail(_admin, mateWeb.ToMailAddress, "检查网站链接发生故障",   mateWeb.WebUrl + "  " + "链接发生故障");
                        }
                    }
                    Thread.Sleep((int)mateWeb.DetectionInterval*1000);
                }
            }
        }
    }
}