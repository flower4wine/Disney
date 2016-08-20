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
                MateAdmin admin = new MateAdmin()
                {
                    SslEnable = checkBoxSsl.Checked,
                    Server = textBoxServer.Text,
                    Email = textBoxSendEmail.Text,
                    PassWord = textBoxSendpassword.Text,
                    UserName = textBoxSendUsername.Text
                };
                int tempInt;

                if (int.TryParse(textBoxDetectionInterval.Text, out tempInt))
                {
                    admin.Port = tempInt;
                }
                TimeSpan tempTimeSpan;

                if (TimeSpan.TryParse(textBoxDetectionInterval.Text, out tempTimeSpan))
                {
                    admin.DetectionInterval = tempTimeSpan;
                }

                _sqliteHelper.SaveAdmin(admin);
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
    }
}