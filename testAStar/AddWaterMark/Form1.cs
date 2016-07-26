using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;
using System.Windows.Forms;
using System.IO;

namespace AddWaterMark
{
    public enum Paring
    {
        P1,
        P2,
        P3,
        P4
    }

    [Flags]
    public enum WarterMarkLocation
    {
        Middel = 0x0,
        Top = 0x1,
        Right = 0x2,
        Bottom = 0x4,
        Left = 0x8,
    }
    public partial class Form1 : Form
    {
        private Paring _parking = Paring.P1;
        
        private Size _padding = new Size(10,10);
        public Form1()
        {
            InitializeComponent();
            textBoxSaveDir.Text = Path.Combine( Environment.CurrentDirectory,"OutPut");
        }

        private void buttonStart_Click(object sender, EventArgs e)
        {
            #region 判定路径合法性
            if (textBoxFromDir.Text == textBoxSaveDir.Text)
            {
                MessageBox.Show(@"请换一个输出路径");
                return;
            }

            if (!Directory.Exists(textBoxFromDir.Text))
            {
                MessageBox.Show(@"起始目录不存在");
                return;
            }

            if (!Directory.Exists(textBoxSaveDir.Text))
            {
                try
                {
                    Directory.CreateDirectory(textBoxSaveDir.Text);
                }
                catch (Exception)
                {

                    MessageBox.Show(@"无法创建输出目录");
                    return;
                }
            }
            #endregion

            this.Enabled = false;
            progressBar1.Value = 0;

            foreach (Control control in groupBoxParkingChoice.Controls)
            {
                if (control is RadioButton && (control as RadioButton).Checked)
                {
                    _parking = (Paring)Enum.Parse(typeof(Paring), (control as RadioButton).Text);
                    break;
                }
            }

            List<string> allImgPaths =  GetAll(new DirectoryInfo(textBoxFromDir.Text));
            progressBar1.Maximum = allImgPaths.Count;

            foreach (string imgPath in allImgPaths)
            {
                SaveBitMap(imgPath, textBoxSaveDir.Text+ imgPath.Remove(0, textBoxFromDir.Text.Length));

                progressBar1.Invoke(new EventHandler(delegate
                {
                    progressBar1.Value++;
                }));
            }
            this.Enabled = true;
        }

        List<string> GetAll(DirectoryInfo dir)//搜索文件夹中的文件
        {
            List<string> fileList = new List<string>();

            FileInfo[] allFile = dir.GetFiles();
            foreach (FileInfo fi in allFile)
            {
                if (fi.Extension.ToLower() == ".jpg"
                    || fi.Extension.ToLower() == ".png")
                {
                    fileList.Add(fi.FullName);
                }
            }

            DirectoryInfo[] allDir = dir.GetDirectories();
            foreach (DirectoryInfo d in allDir)
            {
                fileList.AddRange(GetAll(d));
            }
            return fileList;
        }

        private void DisposeBitmap(ref Bitmap bitmap)
        {
            if (bitmap != null)
            {
                try
                {
                    bitmap.Dispose();
                }
                finally
                {
                    bitmap = null;
                }
            }
        }

        private void SaveBitMap(string fromFullPath, string saveFulPath)
        {
            FileInfo saveFileInfo = new FileInfo(saveFulPath);

            try
            {
                if (saveFileInfo.DirectoryName != null)
                {
                    if ( !Directory.Exists(saveFileInfo.DirectoryName))
                    {
                        Directory.CreateDirectory(saveFileInfo.DirectoryName);
                    }
                }
                else
                {
                    return;
                }
            }
            catch (Exception)
            {
                return;
            }

            Bitmap bitmap = new Bitmap(fromFullPath);
            Bitmap bmp = new Bitmap(bitmap.Width, bitmap.Height, PixelFormat.Format32bppArgb);
            using (Graphics g = Graphics.FromImage(bmp))
            {
                g.InterpolationMode = System.Drawing.Drawing2D.InterpolationMode.HighQualityBicubic;
                g.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.HighQuality;
                g.CompositingQuality = System.Drawing.Drawing2D.CompositingQuality.HighQuality;
                g.DrawImage(bitmap, 0, 0);
            }
            DisposeBitmap(ref bitmap);
            bitmap = bmp;

            Point warterMarkLocationPoint  = Point.Empty;

            using (bitmap)
            {
                using (Graphics g = Graphics.FromImage(bitmap))
                {
                    g.InterpolationMode = System.Drawing.Drawing2D.InterpolationMode.HighQualityBicubic;
                    g.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.HighQuality;
                    g.CompositingQuality = System.Drawing.Drawing2D.CompositingQuality.HighQuality;

                    switch (_parking)
                    {
                        case Paring.P1:
                            g.DrawImage(Properties.Resources.scale, bitmap.Width - Properties.Resources.scale.Width - _padding.Width, _padding.Height);
                            g.DrawImage(Properties.Resources.P1, _padding.Width, _padding.Height);
                            break;
                        case Paring.P2:
                            g.DrawImage(Properties.Resources.scale, bitmap.Width - Properties.Resources.scale.Width- _padding.Width, _padding.Height);
                            g.DrawImage(Properties.Resources.P2, bitmap.Width - Properties.Resources.P2.Width - Properties.Resources.scale.Width - _padding.Width - _padding.Width, _padding.Height);
                            break;
                        case Paring.P3:
                            g.DrawImage(Properties.Resources.scale, Properties.Resources.P3.Width + _padding.Width, bitmap.Height - Properties.Resources.scale.Height - _padding.Height);
                            g.DrawImage(Properties.Resources.P3, _padding.Width, bitmap.Height - Properties.Resources.P3.Height-  _padding.Height);
                            break;
                        case Paring.P4:
                            g.DrawImage(Properties.Resources.scale, bitmap.Width - Properties.Resources.scale.Width - _padding.Width, _padding.Height);
                            g.DrawImage(Properties.Resources.P4, _padding.Width, _padding.Height);
                            break;
                    }

                }

                bitmap.Save(saveFulPath);
            }
        }
    }
}