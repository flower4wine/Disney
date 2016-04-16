using System;
using System.IO;
using System.Drawing;
using System.Drawing.Imaging;
using System.Text;
using System.Windows.Forms;

using ThoughtWorks.QRCode.Codec;
using ThoughtWorks.QRCode.Codec.Data;

namespace QRCode
{
    public partial class Form1 : Form
    {
        private readonly char[] _pathInvalidFileNameChars = Path.GetInvalidFileNameChars();
        public Form1()
        {
            InitializeComponent();
            comboBox1.SelectedIndex = 2;
        }

        private void encodeButton_Click(object sender, EventArgs e)
        {
            CleanflowLayoutPanelData();
            StringReader sr = new StringReader(textBox1.Text);

            string str =sr.ReadLine();
            do
            {
                if (!string.IsNullOrEmpty(str))
                {
                    Bitmap qRCodeBitmap = GetQrCode(str);
                    {
                        using (Bitmap bLogo = GetLogoBitmap(qRCodeBitmap.Size.Width/5))
                        {
                            if (bLogo != null)
                            {
                                Point point = new Point((qRCodeBitmap.Size.Width / 2) - (bLogo.Size.Width / 2),
                                                        (qRCodeBitmap.Size.Height / 2) - (bLogo.Size.Height / 2));
                                using (Graphics g = Graphics.FromImage(qRCodeBitmap))
                                {
                                    g.DrawImage(bLogo, point);
                                }
                            }
                        }
                    }

                    PictureBox qRCodePicBox = GetQrCodePictureBox(qRCodeBitmap);
                    string nameStr =string.IsNullOrEmpty(textBox2.Text)?str: str.Replace(textBox2.Text, string.Empty).Trim();

                    qRCodePicBox.Name = string.IsNullOrEmpty(nameStr)? Guid.NewGuid().ToString():nameStr;
                    foreach (char pathInvalidFileNameChar in _pathInvalidFileNameChars)
                    {
                        qRCodePicBox.Name = qRCodePicBox.Name.Replace(pathInvalidFileNameChar, '|');
                    }
                    qRCodePicBox.Name = qRCodePicBox.Name.Replace(@"|", String.Empty);
                    flowLayoutPanel1.Controls.Add(qRCodePicBox);
                }
                str = sr.ReadLine();
            }
            while (str != null);
        }

        private void decodeButton_Click(object sender, EventArgs e)
        {
            textBox1.Text = string.Empty;
            StringBuilder myStringBuilder = new StringBuilder();    
            foreach (Control control in flowLayoutPanel1.Controls)
            {
                try
                {
                    QRCodeDecoder decoder = new QRCodeDecoder();
                    PictureBox box = control as PictureBox;
                    if (box != null)
                        myStringBuilder.AppendLine(decoder.decode(new QRCodeBitmapImage(new Bitmap(box.Image)), Encoding.UTF8));
                }
                catch
                {
                    // ignored
                }
            }
            textBox1.Text = myStringBuilder.ToString();
        }

        private void openPicButton_Click(object sender, EventArgs e)
        {
            OpenFileDialog fileDialog = new OpenFileDialog
            {
                InitialDirectory = "C://",
                Filter =
                    @"图片(*.bmp;*.jpg;*.jpeg;*.jpe;*.png;*.ico)|*.bmp;*.jpg;*.jpeg;*.jpe;*.png;*.ico|All files (*.*)|*.*",
                FilterIndex = 1,
                RestoreDirectory = true,
                Multiselect = true
            };





            if (fileDialog.ShowDialog() == DialogResult.OK)
            {
                foreach (string path in fileDialog.FileNames)
                {
                    PictureBox qRCodePicBox = GetQrCodePictureBox(path);
                    flowLayoutPanel1.Controls.Add(qRCodePicBox);
                }
            }
        }

        private void logoImportButton_Click(object sender, EventArgs e)
        {
            OpenFileDialog fileDialog = new OpenFileDialog
            {
                InitialDirectory = "C://",
                Filter =
                    @"图片(*.bmp;*.jpg;*.jpeg;*.jpe;*.png;*.ico)|*.bmp;*.jpg;*.jpeg;*.jpe;*.png;*.ico|All files (*.*)|*.*",
                FilterIndex = 1,
                RestoreDirectory = true
            };





            if (fileDialog.ShowDialog() == DialogResult.OK)
            {
                logoPictureBox.ImageLocation = fileDialog.FileName;
            }
        }

        private void logiClearbutton_Click(object sender, EventArgs e)
        {
            logoPictureBox.Image = null;
        }

        private Bitmap GetQrCode(string code)
        {
            QRCodeEncoder qrEntity = new QRCodeEncoder
            {
                QRCodeEncodeMode = QRCodeEncoder.ENCODE_MODE.BYTE, // 二维码编码方式
                QRCodeScale = Convert.ToInt32(numericUpDown1.Value), // 每个小方格的宽度
                QRCodeVersion = 1, // 二维码版本号
                QRCodeErrorCorrect = (QRCodeEncoder.ERROR_CORRECTION)Enum.Parse(typeof(QRCodeEncoder.ERROR_CORRECTION), comboBox1.SelectedItem.ToString())//QRCodeEncoder.ERROR_CORRECTION.M // 纠错码等级
            };

            Bitmap srcimage;
            //动态调整二维码版本号,上限40，过长返回空白图片，编码后字符最大字节长度2953
            while (true)
            {
                try
                {
                    srcimage = qrEntity.Encode(code, Encoding.UTF8);
                    break;
                }
                catch (IndexOutOfRangeException e)
                {
                    if (qrEntity.QRCodeVersion < 40)
                    {
                        qrEntity.QRCodeVersion++;
                    }
                    else
                    {
                        srcimage = new Bitmap(100, 100);
                        break;
                    }
                }
            }
            return srcimage;
        }

        private Bitmap GetLogoBitmap(int sizeWidth)
        {
            Bitmap logoBitmap = null;
            if (logoPictureBox.Image != null)
            {
                logoBitmap = new Bitmap(logoPictureBox.Image, sizeWidth, sizeWidth);
            }
            return logoBitmap;
        }

        private PictureBox GetQrCodePictureBox(Bitmap qRCodeBitmap)
        {
            PictureBox pb = CreatePictureBox();
            pb.Image = qRCodeBitmap;
            return pb;
        }
        private PictureBox GetQrCodePictureBox(string picPath)
        {
            PictureBox pb = CreatePictureBox();
            pb.ImageLocation = picPath;
            pb.Name = Path.GetFileNameWithoutExtension(picPath);
            return pb;
        }
        private PictureBox CreatePictureBox()
        {
            return new PictureBox()
            {
                BorderStyle = BorderStyle.FixedSingle,
                Size = new Size(100, 100),
                SizeMode = PictureBoxSizeMode.StretchImage,
                Padding = new Padding(4)
            };
        }

        private void clearPicButton_Click(object sender, EventArgs e)
        {
            CleanflowLayoutPanelData();
        }

        private void CleanflowLayoutPanelData()
        {
            
            foreach (Control control in flowLayoutPanel1.Controls)
            {
                if (control is PictureBox)
                {
                    PictureBox pb = control as PictureBox;
                    if (pb.Image != null)
                    {
                        Image oldImage = pb.Image;
                        pb.Image = null;
                        oldImage.Dispose();
                    }
                    pb.Dispose();
                }
            }
            flowLayoutPanel1.Controls.Clear();
            GC.Collect();
        }
        private void flowLayoutPanel1_DragDrop(object sender, DragEventArgs e)
        {
            string[] strs = (string[])e.Data.GetData(DataFormats.FileDrop, false);
            foreach (string path in strs)
            {
                PictureBox qRCodePicBox = GetQrCodePictureBox(path);
                flowLayoutPanel1.Controls.Add(qRCodePicBox);
            }
        }

        private void flowLayoutPanel1_DragEnter(object sender, DragEventArgs e)
        {
            if (e.Data.GetDataPresent(DataFormats.FileDrop))
            {
                e.Effect = DragDropEffects.All;
            }
            else
            {
                e.Effect = DragDropEffects.None;
            }
        }

        private void savePicButton_Click(object sender, EventArgs e)
        {
            FolderBrowserDialog fileDialog = new FolderBrowserDialog
            {
                Description = @"请选择保存输出图件的文件夹",
                ShowNewFolderButton = true,
                RootFolder = Environment.SpecialFolder.MyComputer
            };
            
            if (fileDialog.ShowDialog() == DialogResult.OK)
            {
                foreach (Control control in flowLayoutPanel1.Controls)
                {
                    var pictureBox = control as PictureBox;
                    if (pictureBox != null)
                        pictureBox.Image.Save(fileDialog.SelectedPath + "\\" + pictureBox.Name + ".png"
                            , ImageFormat.Png);
                }
            }
        }
    }
}