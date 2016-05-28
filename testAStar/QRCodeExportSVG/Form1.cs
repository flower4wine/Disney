using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Newtonsoft.Json;

namespace WindowsFormsApplication1
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            FolderBrowserDialog fileDialog = new FolderBrowserDialog
            {
                Description = @"请选择保存输出图件的文件夹",
                ShowNewFolderButton = true,
                SelectedPath = Environment.CurrentDirectory,
            };

            if (fileDialog.ShowDialog() == DialogResult.OK)
            {
                List<QRCodeUrlInfo> qRCodeUrlInfos = new List<QRCodeUrlInfo>();
                try
                {
                    qRCodeUrlInfos = JsonConvert.DeserializeObject<List<QRCodeUrlInfo>>(textBox1.Text);
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
                try
                {
                    foreach (QRCodeUrlInfo qRCodeUrlInfo in qRCodeUrlInfos)
                    {
                        WebClient wc = new WebClient();
                        string xxx = System.Web.HttpUtility.UrlEncode(qRCodeUrlInfo.Url, Encoding.UTF8);
                        string url = "http://www.liantu.com/savevector.php?text=" + xxx + "&el=Q&m=15&vt=svg";
                        wc.DownloadFile(url, fileDialog.SelectedPath + "\\" + qRCodeUrlInfo.Name + ".svg");
                    }
                    MessageBox.Show("导出完成");
                }
                catch(Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
            }
        }

        private void buttonImport_Click(object sender, EventArgs e)
        {
            OpenFileDialog fileDialog = new OpenFileDialog();
            fileDialog.Multiselect = false;
            fileDialog.Title = "请选择数据文件";
            fileDialog.Filter = "所有文件(*.*)|*.*";
            
            if (fileDialog.ShowDialog() == DialogResult.OK)
            {
                string file = fileDialog.FileName;
                textBox1.Text = System.IO.File.ReadAllText(file);
            }
        }
    }

    [DataContract]
    public class QRCodeUrlInfo
    {
        [DataMember(Name = "name")]
        public string Name { set; get; }
        [DataMember(Name = "url")]
        public string Url { set; get; }


    }
}
