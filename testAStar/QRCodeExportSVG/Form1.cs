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
                RootFolder = Environment.SpecialFolder.MyComputer
            };

            if (fileDialog.ShowDialog() == DialogResult.OK)
            {
                List<QRCodeUrlInfo> qRCodeUrlInfos = new List<QRCodeUrlInfo>();
                try
                {
                    qRCodeUrlInfos = JsonConvert.DeserializeObject<List<QRCodeUrlInfo>>(textBox1.Text);
                }
                catch (Exception)
                {
                    
                }
                foreach (QRCodeUrlInfo qRCodeUrlInfo in qRCodeUrlInfos)
                {
                    WebClient wc = new WebClient();
                    string xxx = System.Web.HttpUtility.UrlEncode(qRCodeUrlInfo.Url, Encoding.UTF8);
                    string url = "http://liantu.com/savevector.php?text=" + xxx + "&el=Q&m=15&vt=svg";
                    wc.DownloadFile(url, fileDialog.SelectedPath + "\\" + qRCodeUrlInfo.Name+".svg");
                }
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
