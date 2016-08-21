using System;
using System.Windows.Forms;
using CommunicationTest.DB.Sqlite;

namespace CommunicationTest
{
    public partial class SettingForm : Form
    {
        private SqliteCommunicationLogHelper _sqliteHelper;
        public SettingForm(SqliteCommunicationLogHelper sqliteHelper)
        {
            InitializeComponent();
            _sqliteHelper = sqliteHelper;
        }

        private void btnSave_Click(object sender, EventArgs e)
        {
            if (_sqliteHelper != null)
            {
                _sqliteHelper.SaveAdmin(GetAdminEmail());
            }
            Close();
        }

        private void  SendEmail(MateAdmin admin)
        {
            string ret = EmailHelper.SendTestMail(admin);
            if(string.IsNullOrEmpty(ret))
            {
                MessageBox.Show("发送成功，请查看发件箱 ");
            }
            else
            {
                MessageBox.Show(ret);
            }
        }

        private void SettingForm_Load(object sender, EventArgs e)
        {
            if (_sqliteHelper != null)
            {
                MateAdmin admin = _sqliteHelper.GetAdmin();
                if (admin != null)
                {
                    checkBoxSsl.Checked = admin.SslEnable;
                    textBoxServer.Text = admin.Server;
                    textBoxPort.Text = admin.Port.ToString();
                    textBoxSendEmail.Text = admin.Email;
                    textBoxSendpassword.Text = admin.PassWord;
                    textBoxSendUsername.Text = admin.UserName;
                    textBoxDetectionInterval.Text = admin.DetectionInterval.ToString();
                }
            }
        }

        private void buttonConnTest_Click(object sender, EventArgs e)
        {
            SendEmail(GetAdminEmail());
        }

        private MateAdmin GetAdminEmail()
        {
            MateAdmin admin = new MateAdmin()
            {
                SslEnable = checkBoxSsl.Checked,
                Server = textBoxServer.Text,
                Email = textBoxSendEmail.Text,
                PassWord = textBoxSendpassword.Text,
                UserName = textBoxSendUsername.Text
            };
            int tempInt;

            if (int.TryParse(textBoxPort.Text, out tempInt))
            {
                admin.Port = tempInt;
            }
            long tempTimeSpan;

            if (long.TryParse(textBoxDetectionInterval.Text, out tempTimeSpan))
            {
                admin.DetectionInterval = tempTimeSpan;
            }

            return admin;
        }
    }
}