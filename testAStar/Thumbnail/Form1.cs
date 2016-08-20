using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using System.Drawing.Imaging;
using System.Drawing.Drawing2D;

namespace Thumbnail
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        /// <summary>
        /// 源图片
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void button4_Click(object sender, EventArgs e)
        {
            OpenFileDialog fileDialog = new OpenFileDialog();
            fileDialog.Multiselect = false;
            fileDialog.Title = "请选择要导入的图片文件";
            fileDialog.Filter = "图片(*.Jpg)|*.Jpg";
            fileDialog.Multiselect = true;
            if (fileDialog.ShowDialog() == DialogResult.OK)
            {
                textBox3.Text = fileDialog.FileName;
                textBox1.Text = string.Empty;
            }
        }
        private void button1_Click(object sender, EventArgs e)
        {
            FolderBrowserDialog fileDialog = new FolderBrowserDialog
            {
                Description = @"请选择要导入的图片所在文件夹",
                ShowNewFolderButton = true,
                SelectedPath = Environment.CurrentDirectory,
            };

            if (fileDialog.ShowDialog() == DialogResult.OK)
            {
                textBox1.Text = fileDialog.SelectedPath;
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            FolderBrowserDialog fileDialog = new FolderBrowserDialog
            {
                Description = @"请选择保存输出图件的文件夹",
                ShowNewFolderButton = true,
                SelectedPath = Environment.CurrentDirectory,
            };

            if (fileDialog.ShowDialog() == DialogResult.OK)
            {
                textBox2.Text = fileDialog.SelectedPath;
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            try
            {
                if(!Directory.Exists(textBox2.Text))
                {
                    Directory.CreateDirectory(textBox2.Text);
                }
            }
            catch(Exception ex)
            {
                MessageBox.Show("目标文件夹 "+ex.Message);
                return;
            }

            if(!string.IsNullOrEmpty(textBox1.Text))
            {
                DirectoryInfo dir = new DirectoryInfo(textBox1.Text);
                foreach(FileInfo fileInfo in dir.GetFiles("*.Jpg"))
                {
                    ThumbnailImg(fileInfo.FullName);
                }
            }
            else
            {
                if(textBox3.Text.ToLower().EndsWith("*.jpg"))
                {
                    ThumbnailImg(textBox3.Text);
                }
            }
            MessageBox.Show("OK");
        }

        private void ThumbnailImg(string imgFullPath)
        {
            if(File.Exists(imgFullPath))
            {
                string imgName= Path.GetFileName(imgFullPath);
                GetPicThumbnail(imgFullPath, Path.Combine(textBox2.Text, imgName), trackBar1.Value);
            }
        }
        /// <summary>  
        /// 无损压缩图片  
        /// </summary>  
        /// <param name="sFile">原图片</param>  
        /// <param name="dFile">压缩后保存位置</param>  
        /// <param name="flag">压缩质量(数字越小压缩率越高) 1-100</param>  
        /// <returns></returns>  
        public static bool GetPicThumbnail(string sFile, string dFile, int flag)  
        {  
  
            System.Drawing.Image iSource = System.Drawing.Image.FromFile(sFile);  
            ImageFormat tFormat = iSource.RawFormat;  
            int sW = 0, sH = 0;  
            //按比例缩放  
            Size tem_size = new Size(iSource.Width, iSource.Height);

            sW = tem_size.Width;
            sH = tem_size.Height;

            //以下代码为保存图片时，设置压缩质量  
            EncoderParameters ep = new EncoderParameters();  
            long[] qy = new long[1];  
            qy[0] = flag;//设置压缩的比例1-100  
            EncoderParameter eParam = new EncoderParameter(System.Drawing.Imaging.Encoder.Quality, qy);  
            ep.Param[0] = eParam;  
            try  
            {  
                ImageCodecInfo[] arrayICI = ImageCodecInfo.GetImageEncoders();  
                ImageCodecInfo jpegICIinfo = null;  
                for (int x = 0; x < arrayICI.Length; x++)  
                {  
                    if (arrayICI[x].FormatDescription.Equals("JPEG"))  
                    {  
                        jpegICIinfo = arrayICI[x];  
                        break;  
                    }  
                }  
                if (jpegICIinfo != null)  
                {
                    iSource.Save(dFile, jpegICIinfo, ep);//dFile是压缩后的新路径  
                }  
                else  
                {
                    iSource.Save(dFile, tFormat);  
                }  
                return true;  
            }  
            catch  
            {  
                return false;  
            }  
            finally  
            {  
                iSource.Dispose();  
            }  
  
        }  
    }
}
