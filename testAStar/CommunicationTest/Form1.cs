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
    public partial class Form1 : Form
    {
        private SqliteCommunicationLogHelper _sqliteHelper;
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            _sqliteHelper = new SqliteCommunicationLogHelper("Log.db");
        }

        private void buttonSetting_Click(object sender, EventArgs e)
        {
            SettingForm form=new SettingForm(_sqliteHelper);
            form.ShowDialog();
        }
    }
}
