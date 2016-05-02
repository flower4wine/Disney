using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Imaging;
using System.Windows.Forms;
using System.Threading;

using Newtonsoft.Json;

using Svg;
using System.IO;

namespace testAStar
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();

            Init();
            this.panelMap.Paint += this.panelMap_Paint;
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            CellWidthNumericUpDown.Maximum = MAP_WIDTH < MAP_HEIGHT ? MAP_WIDTH : MAP_HEIGHT;
            CellWidthNumericUpDown.Value = Convert.ToDecimal(CELL_WIDTH);
        }
        private void Init()
        {
            //_aPathFind = new APathFind(FLOAT_LINE_WIDTH, CELL_WIDTH, MAP_WIDTH, MAP_HEIGHT);
            _aPathFind = new APathFind(FLOAT_LINE_WIDTH, CELL_WIDTH, MAP_WIDTH, MAP_HEIGHT, this.panelMap);
        }
        private APathFind _aPathFind;
        private static float FLOAT_LINE_WIDTH = 1.0F;
        private static int CELL_WIDTH = 10;
        private static int MAP_WIDTH = 300;
        private static int MAP_HEIGHT = 300;
        private Thread _shThread = null;

        private bool _isFirst = true;
        private SvgDocument _svgDoc;
        private bool _isMouseDown = false;
        private string _imgPath = string.Empty;
        private CommandType _commandType = CommandType.None;
        private Cell _lastNameCell;

        private List<Cell> _goalCells = new List<Cell>();

        #region 比例尺

        private decimal ruleWidth = 50;// 米
        private decimal rulePix = 143;// 像素
        // 2.86 像素/米
        // 0.3496503497米/像素
        // 停车场 （10倍放大）
        #endregion

        #region const
        const string DATA_DIR = @".\Data";
        const string StoneCells_SAVE_PATH = @".\Data\StoneCells.json";
        const string KeyCells_SAVE_PATH = @".\Data\KeyCells.json";
        const string GoalCells_SAVE_PATH = @".\Data\GoalCells.json";
        #endregion const

        private void panelMap_Paint(object sender, PaintEventArgs e)
        {
            //if (_isFirst)
            //{
            _isFirst = false;
            //    _aPathFind.DrawMapGrid();
            //}
            //_aPathFind.MapControlDraw();
        }

        #region 随机生成障碍物
        private void btnCreateStone_Click(object sender, EventArgs e)
        {
            _aPathFind.RandStoneCell();
            _aPathFind.MapControlDraw();
        }
        #endregion

        //鼠标按下事件
        private void panelMap_MouseDown(object sender, MouseEventArgs e)
        {
            if (this._commandType == CommandType.CreateStone)
            {
                _isMouseDown = true;
            }
            SetCellInfo(e);
        }
        private void panelMap_MouseUp(object sender, MouseEventArgs e)
        {
            _isMouseDown = false;
        }
        
        private void panelMap_MouseMove(object sender, MouseEventArgs e)
        {

            if (!_isMouseDown)
            {
                return;
            }
            SetCellInfo(e);
        }

        private void SetCellInfo(MouseEventArgs e)
        {
            textBox1.Text = string.Empty;
            if (this._commandType == CommandType.SetStation)
            {
                if (string.IsNullOrEmpty(textBoxName.Text))
                {
                    MessageBox.Show(@"前缀不能为空");
                    return;
                }
                textBox1.Text = _aPathFind.GetStationCellName(e.X, e.Y);
                _lastNameCell = new Cell(e.X / CELL_WIDTH * CELL_WIDTH, e.Y / CELL_WIDTH * CELL_WIDTH,
                    textBoxName.Text);
                _aPathFind.SetStationCell(_lastNameCell);
            }
            else if (this._commandType == CommandType.SetScenery)
            {
                if (string.IsNullOrEmpty(textBoxName.Text))
                {
                    MessageBox.Show(@"前缀不能为空");
                    return;
                }
                textBox1.Text = _aPathFind.GetSceneryCellName(e.X, e.Y);
                _lastNameCell = new Cell(e.X / CELL_WIDTH * CELL_WIDTH, e.Y / CELL_WIDTH * CELL_WIDTH,
                    textBoxName.Text);
                _aPathFind.SetSceneryCell(_lastNameCell);
            }
            else if (this._commandType == CommandType.SetGoalCells)
            {
                Cell cell = _aPathFind.GetKeyCell(e.X, e.Y);
                if (cell != null)
                {
                    cell.Remark = textBoxGoals.Text;
                    this._goalCells.Add(cell);
                }
            }
            else if (this._commandType == CommandType.CreateStone)
            {
                _aPathFind.SetStoneCell(e.X, e.Y);
            }
            else if (this._commandType == CommandType.BeName)
            {
                if (string.IsNullOrEmpty(textBoxName.Text))
                {
                    MessageBox.Show(@"前缀不能为空");
                    return;
                }
                textBox1.Text = _aPathFind.GetKeyCellName(e.X, e.Y);
                _lastNameCell = new Cell(e.X / CELL_WIDTH * CELL_WIDTH, e.Y / CELL_WIDTH * CELL_WIDTH,
                    //textBoxName.Text + "-" + numericUpDownName.Value.ToString().PadLeft(4, '0'));
                    textBoxName.Text);
                _aPathFind.SetKeyCell(_lastNameCell);
            }
            else if (_aPathFind.IsInStone(e.X, e.Y))
            {
                // MessageBox.Show("请另选方格");
                return;
            }
            else if (_aPathFind.StartCell == null)
            {
                _aPathFind.SetStartCell(e.X, e.Y);
                this.btnCtStoneHand.Enabled = false;
                this.btnCtStoneRodom.Enabled = false;
            }
            else if (_aPathFind.GoalCell == null)
            {
                _aPathFind.SetGoalCell(e.X, e.Y);
            }
            _aPathFind.MapControlDraw();
        }

        //开启手动生成障碍
        private void btnCtStoneHand_Click(object sender, EventArgs e)
        {
            ResetButtonName();
            if (_commandType != CommandType.CreateStone)
            {
                this.btnCtStoneHand.Text = @"取消生成障碍";
                this.btnCtStoneHand.ForeColor = Color.Red;
                _commandType = CommandType.CreateStone;
            }
            else
            {
                _commandType = CommandType.None;
                this.btnCtStoneHand.ForeColor = Color.Black;
            }
        }
        //开启寻路
        private void btnStartSearch_Click(object sender, EventArgs e)
        {
            if (_aPathFind.StartCell == null || _aPathFind.GoalCell == null)
            {
                MessageBox.Show(@"缺少起点或目标点");
                return;
            }
            this.btnRest.Enabled = false;
            this.btnStartSearch.Enabled = false;
            _shThread = new Thread(this.BeginSearch);
            _shThread.Start();
        }

        #region 开始搜索
        public void BeginSearch()
        {
            if (_aPathFind.BeginSearch())
            {
                _aPathFind.MapControlDraw();

                this.btnRest.Invoke(new EventHandler(delegate
                {
                    this.btnRest.Enabled = true;
                }));
            }
            else
            {
                MessageBox.Show(@"苦海无边，回头是岸！");

                this.btnRest.Invoke(new EventHandler(delegate
                {
                    this.btnRest.Enabled = true;
                }));
            }
        }

        #endregion

        #region 重置窗体
        private void btnRest_Click(object sender, EventArgs e)
        {
            ReSet();
        }
        private void ReSet(string fileName = "")
        {
            _aPathFind.ReSet();
            _aPathFind.MapControlDraw();
            this.btnCtStoneHand.Enabled = true;
            // this.btnCtStoneRodom.Enabled = true;
            this.btnStartSearch.Enabled = true;

            if (_svgDoc != null)
            {
                MAP_WIDTH = Convert.ToInt32(_svgDoc.Width.Value *Convert.ToSingle(numericUpDown3.Value));
                MAP_HEIGHT = Convert.ToInt32(_svgDoc.Height.Value * Convert.ToSingle(numericUpDown3.Value));
                CellWidthNumericUpDown.Maximum = MAP_WIDTH < MAP_HEIGHT ? MAP_WIDTH : MAP_HEIGHT;
                Image oldBitmap = panelMap.BackgroundImage;
                panelMap.BackgroundImage = _svgDoc.Draw(MAP_WIDTH, MAP_HEIGHT);
                if (oldBitmap != null)
                    oldBitmap.Dispose();
                if (!string.IsNullOrEmpty(fileName))
                    panelMap.BackgroundImage.Save(fileName + ".png", ImageFormat.Png);
            }
            else
            {
                MAP_WIDTH = panelMap.Width;
                MAP_HEIGHT = panelMap.Height;
            }
            panelMap.Size = new Size(MAP_WIDTH, MAP_HEIGHT);

            _aPathFind = new APathFind(FLOAT_LINE_WIDTH, CELL_WIDTH, MAP_WIDTH, MAP_HEIGHT, this.panelMap);

            _aPathFind.DrawMapGrid();
        }
        #endregion 

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (_shThread != null)
            {
                _shThread.Abort();
            }
        }


        private void SvgImportButton_Click(object sender, EventArgs e)
        {

            openSvgFile.Filter = @"PNG文件(*.png;*.jpg)|*.png;*.jpg";
            if (openSvgFile.ShowDialog() == DialogResult.OK)
            {
                _imgPath = openSvgFile.FileName;
                this.panelMap.BackgroundImage = new Bitmap(_imgPath);
                this.panelMap.Size = this.panelMap.BackgroundImage.Size;
                ReSet();
            }
        }
        private void CellWidthNumericUpDown_ValueChanged(object sender, EventArgs e)
        {
            CELL_WIDTH = Convert.ToInt32(CellWidthNumericUpDown.Value);
            ReSet();
        }

        private void SaveSvgButton_Click(object sender, EventArgs e)
        {
            List<WayInfo> wayInfos = new List<WayInfo>();
            Bitmap bitmap  = (Bitmap)panelMap.BackgroundImage.Clone();
            using(bitmap)
            {
                double length = 0d;
                Point point1 = Point.Empty;
                Point point2 = Point.Empty;
                using (Graphics g = Graphics.FromImage(bitmap))
                {
                    #region 绘制路线
                    using (Pen p = new Pen(Color.FromArgb(0xff,0x48, 0xA2, 0x7C), CELL_WIDTH))
                    {
                        p.StartCap = LineCap.Round;
                        p.EndCap = LineCap.Round;
                        p.LineJoin = LineJoin.Round;
                        Point? startPoint = null;
                        foreach (Point location in _aPathFind.PathPoint)
                        {
                            if (startPoint.HasValue)
                            {
                                length += GetPointLength(startPoint.Value, location);
                                Point p1 = new Point(startPoint.Value.X + CELL_WIDTH / 2, startPoint.Value.Y + CELL_WIDTH / 2); 
                                Point p2 = new Point(location.X + CELL_WIDTH / 2, location.Y + CELL_WIDTH / 2);
                                if (startPoint.Value.Y - location.Y > CELL_WIDTH * 2
                                    || startPoint.Value.X - location.X > CELL_WIDTH * 2)
                                {
                                    g.DrawLine(p, p1, p2);
                                }
                                else
                                {
                                    g.DrawCurve(p, new[] { p1, p2 });
                                }
                                point2 = p2;
                            }
                            else
                            {
                                point1 = new Point(location.X + CELL_WIDTH / 2, location.Y + CELL_WIDTH / 2);
                            }
                            startPoint = location;
                        }
                    }
                    #endregion
                }
                #region 正向
                Bitmap bitmapTemp = (Bitmap)bitmap.Clone();
                using (Graphics g = Graphics.FromImage(bitmapTemp))
                {
                    Point startImgPoint = new Point(point1.X - Properties.Resources.MapStart.Width / 2, point1.Y - Properties.Resources.MapStart.Height - CELL_WIDTH);
                    Point endImgPoint = new Point(point2.X - Properties.Resources.MapEnd.Width / 2 , point2.Y - Properties.Resources.MapEnd.Height - CELL_WIDTH);
                    g.DrawImage(Properties.Resources.MapStart, startImgPoint);
                    g.DrawImage(Properties.Resources.MapEnd, endImgPoint);
                }
                WayInfo wayInfo = new WayInfo
                {
                    PicName = _aPathFind.StartCell.Name + "-" + _aPathFind.GoalCell.Name + ".png",
                    From = _aPathFind.StartCell.Name,
                    Distance = Convert.ToInt32(Math.Floor(Convert.ToDecimal(length) * ruleWidth / rulePix))
                };
                wayInfo.Time = Convert.ToInt32(Convert.ToDecimal(wayInfo.Distance) / numericUpDown2.Value);

                if (!Directory.Exists(@"./Save" + wayInfo.InnerDirPath))
                {
                    Directory.CreateDirectory(@"./Save" + wayInfo.InnerDirPath);
                }
                bitmapTemp.Save(@"./Save" + wayInfo.Inner);
                bitmapTemp.Dispose();

                if (!string.IsNullOrEmpty(_aPathFind.GoalCell.Remark))
                {
                    string[] toStrs = _aPathFind.GoalCell.Remark.Split(';');
                    foreach (string toStr in toStrs)
                    {
                        WayInfo newWayInfo = wayInfo.Clone();
                        newWayInfo.To = toStr;
                        wayInfos.Add(newWayInfo);
                    }
                }
                wayInfo.To = _aPathFind.GoalCell.Name;
                wayInfos.Add(wayInfo);
                #endregion 正向

                #region 反向
                bitmapTemp = (Bitmap)bitmap.Clone();
                using (Graphics g = Graphics.FromImage(bitmapTemp))
                {
                    Point startImgPoint = new Point(point2.X - Properties.Resources.MapStart.Width / 2, point2.Y - Properties.Resources.MapStart.Height - CELL_WIDTH);
                    Point endImgPoint = new Point(point1.X - Properties.Resources.MapEnd.Width / 2, point1.Y - Properties.Resources.MapEnd.Height - CELL_WIDTH);
                    g.DrawImage(Properties.Resources.MapStart, startImgPoint);
                    g.DrawImage(Properties.Resources.MapEnd, endImgPoint);
                }
                wayInfo = new WayInfo
                {
                    PicName = _aPathFind.GoalCell.Name + "-" + _aPathFind.StartCell.Name + ".png",
                    To = _aPathFind.StartCell.Name,
                    Distance = Convert.ToInt32(Math.Floor(Convert.ToDecimal(length) * ruleWidth / rulePix))
                };
                wayInfo.Time = Convert.ToInt32(Convert.ToDecimal(wayInfo.Distance) / numericUpDown2.Value);

                if (!Directory.Exists(@"./Save" + wayInfo.InnerDirPath))
                {
                    Directory.CreateDirectory(@"./Save" + wayInfo.InnerDirPath);
                }
                bitmapTemp.Save(@"./Save" + wayInfo.Inner);
                bitmapTemp.Dispose();

                if (!string.IsNullOrEmpty(_aPathFind.GoalCell.Remark))
                {
                    string[] toStrs = _aPathFind.GoalCell.Remark.Split(';');
                    foreach (string toStr in toStrs)
                    {
                        WayInfo newWayInfo = wayInfo.Clone();
                        newWayInfo.From = toStr;
                        wayInfos.Add(newWayInfo);
                    }
                }
                wayInfo.From = _aPathFind.GoalCell.Name;
                wayInfos.Add(wayInfo);
                #endregion 反向
            }
            File.WriteAllText(@"./Save/" + textBoxMapName.Text + "/ Data.json", JsonConvert.SerializeObject(wayInfos, Formatting.Indented,
                            new JsonSerializerSettings { NullValueHandling = NullValueHandling.Ignore }));
            GC.Collect();
        }

        private double GetPointLength(Point startPoint, Point endPoint)
        {
            return Math.Sqrt(Math.Abs(startPoint.X - endPoint.X) * Math.Abs(startPoint.X - endPoint.X)
                + Math.Abs(startPoint.Y - endPoint.Y) * Math.Abs(startPoint.Y - endPoint.Y));
        }
        private void SetPath()
        {
            SvgColourServer _Stroke = new SvgColourServer(Color.Black);

            var groupElement = new SvgGroup() { ID = "MapPathWay" };
            _svgDoc.Children.Add(groupElement);
            Point? startPoint = null;
            foreach (Point location in _aPathFind.PathPoint)
            {
                if (startPoint.HasValue)
                {
                    var line = new SvgLine()
                    {
                        ID = "lineLinkedPoint",
                        StartX = startPoint.Value.X,
                        StartY = startPoint.Value.Y,
                        EndX = location.X,
                        EndY = location.Y,
                        Stroke = _Stroke,
                        StrokeWidth = CELL_WIDTH
                    };
                    groupElement.Children.Add(line);
                }
                startPoint = location;
            }

            //g.FillRectangle(new SolidBrush(cell.CellColor), cell.Location.X + CELL_HALF_LINE_WIDTH, cell.Location.Y + CELL_HALF_LINE_WIDTH, CELL_WIDTH_M_INT_LINE_WIDTH, CELL_WIDTH_M_INT_LINE_WIDTH);

            var svgXml = _svgDoc.GetXML();
            File.WriteAllText(@"c:\aa.svg", svgXml);
        }

        private void SaveMapInfoButton_Click(object sender, EventArgs e)
        {
            if (!Directory.Exists(DATA_DIR))
            {
                Directory.CreateDirectory(DATA_DIR);
            }
            ////File.CreateText(MPA_SAVE_PATH);
            ////File.CreateText(StoneCells_SAVE_PATH);
            ////File.CreateText(KeyCells_SAVE_PATH);
            //File.WriteAllText(MPA_SAVE_PATH, _svgDoc.GetXML());
            //Image oldImage = panelMap.BackgroundImage;
            //panelMap.BackgroundImage = null;
            //oldImage.Dispose();

            if (_imgPath != @".\Data\Map.png"
                && !string.IsNullOrEmpty(_imgPath))
                File.Copy(_imgPath, @".\Data\Map.png", true);
            File.WriteAllText(StoneCells_SAVE_PATH, JsonConvert.SerializeObject(_aPathFind.StoneCells, Formatting.Indented,
      new JsonSerializerSettings { NullValueHandling = NullValueHandling.Ignore }));
            File.WriteAllText(KeyCells_SAVE_PATH, JsonConvert.SerializeObject(_aPathFind.KeyCells, Formatting.Indented,
      new JsonSerializerSettings { NullValueHandling = NullValueHandling.Ignore }));
            File.WriteAllText(GoalCells_SAVE_PATH, JsonConvert.SerializeObject(this._goalCells, Formatting.Indented,
      new JsonSerializerSettings { NullValueHandling = NullValueHandling.Ignore }));
            File.WriteAllText(@".\Data\SceneryCells.json", JsonConvert.SerializeObject(_aPathFind.SceneryCells, Formatting.Indented,
      new JsonSerializerSettings { NullValueHandling = NullValueHandling.Ignore }));
            File.WriteAllText(@".\Data\StationCells.json", JsonConvert.SerializeObject(_aPathFind.StationCells, Formatting.Indented,
      new JsonSerializerSettings { NullValueHandling = NullValueHandling.Ignore }));

            //panelMap.BackgroundImage = new Bitmap(@".\Data\Map.png");
        }

        private void UseSavedMapInfoButton_Click(object sender, EventArgs e)
        {
            //if (File.Exists(MPA_SAVE_PATH))
            if (File.Exists(@".\Data\Map.png"))
            {
                //_svgDoc = SvgDocument.Open(MPA_SAVE_PATH);
                _imgPath = @".\Data\Map.png";
                this.panelMap.BackgroundImage = new Bitmap(@".\Data\Map.png");
                this.panelMap.Size = this.panelMap.BackgroundImage.Size;
                ReSet();
                if (File.Exists(StoneCells_SAVE_PATH))
                {
                    _aPathFind.ReLoadStoneCells(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(StoneCells_SAVE_PATH)));
                }
                if (File.Exists(KeyCells_SAVE_PATH))
                {
                    _aPathFind.ReLoadKeyCells(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(KeyCells_SAVE_PATH)));
                }
                if (File.Exists(GoalCells_SAVE_PATH))
                {
                    this._goalCells.Clear();
                    this._goalCells.AddRange(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(GoalCells_SAVE_PATH)));
                }
                if (File.Exists(@".\Data\SceneryCells.json"))
                {
                    _aPathFind.ReLoadSceneryCells(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(@".\Data\SceneryCells.json")));
                }
                if (File.Exists(@".\Data\StationCells.json"))
                {
                    _aPathFind.ReLoadStationCells(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(@".\Data\StationCells.json")));
                }
            }
        }

        private void rotateNumericUpDown_ValueChanged(object sender, EventArgs e)
        {
            Image oldImage = this.panelMap.BackgroundImage;

            this.panelMap.BackgroundImage = RotateImg(_imgPath, Convert.ToInt32(rotateNumericUpDown.Value));

            this.panelMap.Size = this.panelMap.BackgroundImage.Size;
            oldImage.Dispose();
        }

        /// <summary>  
        /// 以逆时针为方向对图像进行旋转  
        /// </summary>  
        /// <param name="filePath"></param>  
        /// <param name="angle">旋转角度</param>  
        /// <returns></returns>  
        public Image RotateImg(string filePath, int angle)
        {
            Bitmap dsImage;
            using (Bitmap b = new Bitmap(filePath))
            {
                angle = angle % 360;
                //弧度转换  
                double radian = angle * Math.PI / 180.0;
                double cos = Math.Cos(radian);
                double sin = Math.Sin(radian);
                //原图的宽和高  
                int w = b.Width;
                int h = b.Height;
                int W = (int)(Math.Max(Math.Abs(b.Width * cos - b.Height * sin), Math.Abs(b.Width * cos + b.Height * sin)));
                int H = (int)(Math.Max(Math.Abs(b.Width * sin - b.Height * cos), Math.Abs(b.Width * sin + b.Height * cos)));

                //目标位图  
                dsImage = new Bitmap(W, H);
                using (Graphics g = Graphics.FromImage(dsImage))
                {
                    g.InterpolationMode = InterpolationMode.Bilinear;
                    g.SmoothingMode = SmoothingMode.HighQuality;

                    //计算偏移量  
                    Point offset = new Point((W - w) / 2, (H - h) / 2);

                    //构造图像显示区域：让图像的中心与窗口的中心点一致  
                    Rectangle rect = new Rectangle(offset.X, offset.Y, w, h);
                    Point center = new Point(rect.X + rect.Width / 2, rect.Y + rect.Height / 2);
                    g.TranslateTransform(center.X, center.Y);
                    g.RotateTransform(360 - angle);

                    //恢复图像在水平和垂直方向的平移  
                    g.TranslateTransform(-center.X, -center.Y);
                    g.DrawImage(b, rect);

                    //重至绘图的所有变换  
                    g.ResetTransform();
                    g.Save();
                }
            }

            return dsImage;

        }

        private void buttonName_Click(object sender, EventArgs e)
        {
            ResetButtonName();
            if (_commandType != CommandType.BeName)
            {
                buttonName.Text = @"取消停车区域起名";
                _commandType = CommandType.BeName;
            }
            else
            {
                _commandType = CommandType.None;
            }
        }


        private void buttonChangeName_Click(object sender, EventArgs e)
        {
            if (_lastNameCell != null)
            {
                if (string.IsNullOrEmpty(textBoxName.Text))
                {
                    _aPathFind.RemoveKeyCell(_lastNameCell);
                    _lastNameCell = null;
                }
                else
                {
                    _lastNameCell.Name = textBoxName.Text;// + "-" + numericUpDownName.Value.ToString().PadLeft(4, '0');
                }
                //_lastNameCell.Name = textBoxName.Text + "-" + numericUpDownName.Value.ToString().PadLeft(4, '0');
            }
            textBox1.Text = string.Empty;
        }
        private void buttonAddName_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(textBoxMapName.Text))
            {
                MessageBox.Show(@"输入地图名称");
                return;
            }
            _aPathFind.SetKeyCellSuffix();

            if (!Directory.Exists(@"./Save/" + textBoxMapName.Text + "/QRLocation/"))
            {
                Directory.CreateDirectory(@"./Save/" + textBoxMapName.Text + "/QRLocation/");
            }
            using (Bitmap bitmap = new Bitmap(panelMap.Width, panelMap.Height))
            {
                panelMap.DrawToBitmap(bitmap, panelMap.Bounds);
                using (Graphics g = Graphics.FromImage(bitmap))
                {
                    Font font = new Font("Times New Roman", 9);
                    SolidBrush colorPointBrush = new SolidBrush(Color.Chartreuse);
                    SolidBrush colorBrush = new SolidBrush(Color.Black);
                    foreach (Cell keyCell in _aPathFind.KeyCells)
                    {
                        g.FillEllipse(colorPointBrush, new Rectangle(keyCell.Location, new Size(CELL_WIDTH, CELL_WIDTH)));
                    }
                    foreach (Cell keyCell in _aPathFind.KeyCells)
                    {
                        Point stringLocation = keyCell.Location;
                        stringLocation.X += CELL_WIDTH + 1;
                        g.DrawString(keyCell.Name.Substring(keyCell.Name.LastIndexOf('-') + 1).Trim('0'), font, colorBrush,
                            stringLocation);

                        using (Bitmap bitmap2 = new Bitmap(panelMap.Width, panelMap.Height))
                        {
                            panelMap.DrawToBitmap(bitmap2, panelMap.Bounds);
                            using (Graphics g2 = Graphics.FromImage(bitmap2))
                            {
                                g2.DrawImage(Properties.Resources.star, keyCell.Location - new Size(16,16));
                            }
                            bitmap2.Save(@"./Save/" + textBoxMapName.Text + "/QRLocation/" + keyCell.Name + ".jpg", ImageFormat.Jpeg);
                        }
                    }
                }
                bitmap.Save(@"./Save/" + textBoxMapName.Text + "/ MapIndex.png", ImageFormat.Png);
            }
            MessageBox.Show(@"导出二维码坐标信息完成");
        }

        private void buttonSetGoalCells_Click(object sender, EventArgs e)
        {
            this.btnCtStoneHand.Text = @"手动生成障碍";
            this.btnCtStoneHand.ForeColor = Color.Black;
            this.buttonName.Text = @"停车区域起名";

            if (_commandType != CommandType.SetGoalCells)
            {
                buttonSetGoalCells.Text = @"取消设置批量终点";
                this._commandType = CommandType.SetGoalCells;
            }
            else
            {
                _commandType = CommandType.None;
            }
        }

        public void BeginSearchMany()
        {
            Bitmap[] roadBitmaps = new[]
            {
                Properties.Resources._0,
                Properties.Resources._1,
                Properties.Resources._2,
                Properties.Resources._3,
                Properties.Resources._4,
                Properties.Resources._5
            };
            Color penColor = Color.FromArgb(0xff, 66, 100, 176);
            Cell[] keyCellsTemp = new Cell[_aPathFind.KeyCells.Count];
            Cell[] sceneryCellsTemp = new Cell[_aPathFind.SceneryCells.Count];
            Cell[] stationCellsTemp = new Cell[_aPathFind.StationCells.Count];
            List<Cell> stationCellsList = new List<Cell>(_aPathFind.StationCells.Count);
            _aPathFind.KeyCells.CopyTo(keyCellsTemp);
            _aPathFind.SceneryCells.CopyTo(sceneryCellsTemp);
            _aPathFind.StationCells.CopyTo(stationCellsTemp);
            stationCellsList.AddRange(stationCellsTemp);
            List<WayInfo> wayInfos = new List<WayInfo>();

            if (!checkBoxIsAll.Checked)
            {
                int lineWidth = 12;
                #region 库内
                foreach (Cell goalCell in this._goalCells)
                {
                    foreach (Cell keyCellTemp in keyCellsTemp)
                    {
                        if (this._goalCells.FindIndex(record => record.Location.Equals(keyCellTemp.Location)) > -1)
                        {
                            continue;
                        }
                        ReSet();
                        if (File.Exists(StoneCells_SAVE_PATH))
                        {
                            _aPathFind.ReLoadStoneCells(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(StoneCells_SAVE_PATH)));
                        }

                        _aPathFind.SetStartCell(keyCellTemp);
                        _aPathFind.SetGoalCell(goalCell);

                        if (_aPathFind.BeginSearch())
                        {
                            Bitmap bitmap = (Bitmap)panelMap.BackgroundImage.Clone();

                            using (bitmap)
                            {
                                double length = 0d;
                                PointF point1 = PointF.Empty;
                                PointF point2 = PointF.Empty;
                                using (Graphics g = Graphics.FromImage(bitmap))
                                {
                                    #region 绘制路线
                                    using (Pen p = new Pen(Color.Red, lineWidth))
                                    {
                                        p.StartCap = LineCap.Round;
                                        p.EndCap = LineCap.Round;
                                        p.LineJoin = LineJoin.Round;
                                        

                                        if (_aPathFind.PathPoint.Count == 0)
                                        {
                                            // no
                                        }
                                        else if (_aPathFind.PathPoint.Count == 1)
                                        {
                                            point1 = new PointF(Convert.ToSingle(_aPathFind.PathPoint[0].X) + Convert.ToSingle(lineWidth) / 2.0F
                                                            , Convert.ToSingle(_aPathFind.PathPoint[0].Y) + Convert.ToSingle(lineWidth) / 2.0F);
                                            point2 = new PointF(Convert.ToSingle(_aPathFind.PathPoint[_aPathFind.PathPoint.Count - 1].X) + Convert.ToSingle(lineWidth) / 2.0F
                                                            , Convert.ToSingle(_aPathFind.PathPoint[_aPathFind.PathPoint.Count - 1].Y) + Convert.ToSingle(lineWidth) / 2.0F);

                                            g.FillEllipse(Brushes.Green, new RectangleF(point1, new Size(lineWidth, lineWidth)));
                                        }
                                        else if (_aPathFind.PathPoint.Count == 2)
                                        {
                                            point1 = new PointF(Convert.ToSingle(_aPathFind.PathPoint[0].X) + Convert.ToSingle(lineWidth) / 2.0F
                                                            , Convert.ToSingle(_aPathFind.PathPoint[0].Y) + Convert.ToSingle(lineWidth) / 2.0F);
                                            point2 = new PointF(Convert.ToSingle(_aPathFind.PathPoint[_aPathFind.PathPoint.Count - 1].X) + Convert.ToSingle(lineWidth) / 2.0F
                                                            , Convert.ToSingle(_aPathFind.PathPoint[_aPathFind.PathPoint.Count - 1].Y) + Convert.ToSingle(lineWidth) / 2.0F);

                                            g.DrawLine(p, point1, point2);
                                        }
                                        else if (_aPathFind.PathPoint.Count >= 3)
                                        {
                                            point1 = new PointF(Convert.ToSingle(_aPathFind.PathPoint[0].X) + Convert.ToSingle(lineWidth) / 2.0F
                                                            , Convert.ToSingle(_aPathFind.PathPoint[0].Y) + Convert.ToSingle(lineWidth) / 2.0F);
                                            point2 = new PointF(Convert.ToSingle(_aPathFind.PathPoint[_aPathFind.PathPoint.Count - 1].X) + Convert.ToSingle(lineWidth) / 2.0F
                                                            , Convert.ToSingle(_aPathFind.PathPoint[_aPathFind.PathPoint.Count - 1].Y) + Convert.ToSingle(lineWidth) / 2.0F);

                                            PointF lastPoint = point1;

                                            Point[] pathPoints = _aPathFind.PathPoint.ToArray();

                                            for (int i = 2; i < pathPoints.Length; i++)
                                            {
                                                PointF nowPoint = new PointF(Convert.ToSingle(pathPoints[i - 1].X) + Convert.ToSingle(lineWidth) / 2.0F
                                                                    , Convert.ToSingle(pathPoints[i - 1].Y) + Convert.ToSingle(lineWidth )/ 2.0F);
                                                PointF nextPoint = new PointF(Convert.ToSingle(pathPoints[i].X) + Convert.ToSingle(lineWidth) / 2.0F
                                                                    , Convert.ToSingle(pathPoints[i].Y) + Convert.ToSingle(lineWidth) / 2.0F);

                                                if ((Math.Abs(lastPoint.X - nowPoint.X) <= CELL_WIDTH
                                                    && Math.Abs(lastPoint.Y - nowPoint.Y) <= CELL_WIDTH)
                                                    && (Math.Abs(nextPoint.X - nowPoint.X) <= CELL_WIDTH
                                                    && Math.Abs(nextPoint.Y - nowPoint.Y) <= CELL_WIDTH)
                                                    )
                                                {
                                                    continue;
                                                }
                                                else
                                                {
                                                    g.DrawLine(p, lastPoint, nowPoint);
                                                    lastPoint = nowPoint;
                                                }
                                            }
                                            g.DrawLine(p, lastPoint, point2);
                                        }
                                    }
                                    #endregion
                                }
                                #region 正向
                                Bitmap bitmapTemp = (Bitmap)bitmap.Clone();
                                using (Graphics g = Graphics.FromImage(bitmapTemp))
                                {
                                    PointF startImgPoint = new PointF(point1.X - Convert.ToSingle(Properties.Resources.MapStart.Width) / 2.0F - Convert.ToSingle(lineWidth) / 2.0F// - 5.0F
                                                            , point1.Y - Convert.ToSingle(Properties.Resources.MapStart.Width) / 2.0F - Convert.ToSingle(lineWidth) / 2.0F);// - 3.0F);
                                    PointF endImgPoint = new PointF(point2.X - Convert.ToSingle(Properties.Resources.MapEnd.Width) / 2.0F - Convert.ToSingle(lineWidth) / 2.0F// - 5.0F
                                                            , point2.Y - Convert.ToSingle(Properties.Resources.MapEnd.Width) / 2.0F - Convert.ToSingle(lineWidth) / 2.0F);// - 3.0F);
                                    g.DrawImage(Properties.Resources.MapStart, startImgPoint);
                                    g.DrawImage(Properties.Resources.MapEnd, endImgPoint);
                                }
                                WayInfo wayInfo = new WayInfo
                                {
                                    PicName = _aPathFind.StartCell.Name + "-" + _aPathFind.GoalCell.Name + ".png",
                                    From = _aPathFind.StartCell.Name,
                                    Distance = Convert.ToInt32(Math.Floor(Convert.ToDecimal(length) * ruleWidth / rulePix))
                                };
                                wayInfo.Time = Convert.ToInt32(Convert.ToDecimal(wayInfo.Distance) / numericUpDown2.Value);

                                if (!Directory.Exists(@"./Save/" + textBoxMapName.Text + wayInfo.InnerDirPath))
                                {
                                    Directory.CreateDirectory(@"./Save/" + textBoxMapName.Text + wayInfo.InnerDirPath);
                                }
                                bitmapTemp.Save(@"./Save/" + textBoxMapName.Text + wayInfo.Inner, ImageFormat.Png);
                                bitmapTemp.Dispose();

                                if (!string.IsNullOrEmpty(_aPathFind.GoalCell.Remark))
                                {
                                    string[] toStrs = _aPathFind.GoalCell.Remark.Split(';');
                                    foreach (string toStr in toStrs)
                                    {
                                        WayInfo newWayInfo = wayInfo.Clone();
                                        newWayInfo.To = toStr;
                                        wayInfos.Add(newWayInfo);
                                    }
                                }
                                wayInfo.To = _aPathFind.GoalCell.Name;
                                wayInfos.Add(wayInfo);
                                #endregion 正向

                                #region 反向
                                bitmapTemp = (Bitmap)bitmap.Clone();
                                using (Graphics g = Graphics.FromImage(bitmapTemp))
                                {
                                    PointF startImgPoint = new PointF(point2.X - Convert.ToSingle(Properties.Resources.MapStart.Width) / 2.0F - Convert.ToSingle(lineWidth) / 2.0F
                                                            , point2.Y - Convert.ToSingle(Properties.Resources.MapStart.Width) / 2.0F - Convert.ToSingle(lineWidth) / 2.0F);
                                    PointF endImgPoint = new PointF(point1.X - Convert.ToSingle(Properties.Resources.MapEnd.Width) / 2.0F - Convert.ToSingle(lineWidth) / 2.0F
                                                            , point1.Y - Convert.ToSingle(Properties.Resources.MapEnd.Width) / 2.0F - Convert.ToSingle(lineWidth) / 2.0F);
                                    
                                    g.DrawImage(Properties.Resources.MapStart, startImgPoint);
                                    g.DrawImage(Properties.Resources.MapEnd, endImgPoint);
                                }
                                wayInfo = new WayInfo
                                {
                                    PicName = _aPathFind.GoalCell.Name + "-" + _aPathFind.StartCell.Name + ".png",
                                    To = _aPathFind.StartCell.Name,
                                    Distance = Convert.ToInt32(Math.Floor(Convert.ToDecimal(length) * ruleWidth / rulePix))
                                };
                                wayInfo.Time = Convert.ToInt32(Convert.ToDecimal(wayInfo.Distance) / numericUpDown2.Value);

                                if (!Directory.Exists(@"./Save/" + textBoxMapName.Text + wayInfo.InnerDirPath))
                                {
                                    Directory.CreateDirectory(@"./Save/" + textBoxMapName.Text + wayInfo.InnerDirPath);
                                }
                                bitmapTemp.Save(@"./Save/" + textBoxMapName.Text + wayInfo.Inner, ImageFormat.Png);
                                bitmapTemp.Dispose();

                                if (!string.IsNullOrEmpty(_aPathFind.GoalCell.Remark))
                                {
                                    string[] toStrs = _aPathFind.GoalCell.Remark.Split(';');
                                    foreach (string toStr in toStrs)
                                    {
                                        WayInfo newWayInfo = wayInfo.Clone();
                                        newWayInfo.From = toStr;
                                        wayInfos.Add(newWayInfo);
                                    }
                                }
                                wayInfo.From = _aPathFind.GoalCell.Name;
                                wayInfos.Add(wayInfo);
                                #endregion 反向
                            }
                        }
                        else
                        {
                            Debug.WriteLine("Can not Find from: " + keyCellTemp.Location + "\tto: " + goalCell.Location);
                        }
                    }
                    GC.Collect();
                }
                #endregion
            }
            else
            {
                #region 总图

                Dictionary<Cell, Cell> sceneryStationDic = new Dictionary<Cell, Cell>();
                Dictionary<Cell, Cell> parkingStationDic = new Dictionary<Cell, Cell>();

                #region 景点 - 最近站点 绑定
                Debug.WriteLine("景点 - 最近站点 绑定 Start");
                foreach (Cell sceneryCell in sceneryCellsTemp)
                {
                    double sceneryStationLength = double.MaxValue;
                    Cell zuijinSceneryStationCell = null;

                    foreach (Cell stationCell in stationCellsTemp)
                    {
                        if (sceneryCell.Location.Equals(stationCell.Location)
                            || GetPointLength(sceneryCell.Location, stationCell.Location) > sceneryStationLength)
                        {
                            continue;
                        }
                        ReSet();
                        if (File.Exists(StoneCells_SAVE_PATH))
                        {
                            _aPathFind.ReLoadStoneCells(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(StoneCells_SAVE_PATH)));
                        }
                        _aPathFind.SetStartCell(sceneryCell);
                        _aPathFind.SetGoalCell(stationCell);

                        Debug.WriteLine(string.Format("景点 - 最近站点 绑定{0} , {1} BeginSearch", sceneryCell, stationCell));
                        if (_aPathFind.BeginSearch())
                        {
                            Debug.WriteLine(string.Format("景点 - 最近站点 绑定{0} , {1} SearchEnd", sceneryCell, stationCell));
                            double length = 0.0d;
                            Point? startPoint = null;
                            foreach (Point location in _aPathFind.PathPoint)
                            {
                                if (startPoint.HasValue)
                                {
                                    length += GetPointLength(startPoint.Value, location);
                                }
                                startPoint = location;
                            }
                            if (sceneryStationLength - length > 0)
                            {
                                sceneryStationLength = length;
                                zuijinSceneryStationCell = stationCell;
                            }
                        }
                    }
                    sceneryStationDic.Add(sceneryCell, zuijinSceneryStationCell);
                    Debug.WriteLine(string.Format("sceneryStationDic Num: {0}", sceneryStationDic.Count));
                }
                Debug.WriteLine("景点 - 最近站点 绑定 End");
                #endregion 景点 - 最近站点 绑定

                #region 停车场 - 最近站点 绑定
                Debug.WriteLine("停车场 - 最近站点 绑定 Start");
                foreach (Cell parkingCell in keyCellsTemp)
                {
                    double parkingStationLength = double.MaxValue;
                    Cell zuijinParkingStationCell = null;

                    foreach (Cell stationCell in stationCellsTemp)
                    {
                        if (parkingCell.Location.Equals(stationCell.Location)
                            || GetPointLength(parkingCell.Location, stationCell.Location) > parkingStationLength)
                        {
                            continue;
                        }
                        ReSet();
                        if (File.Exists(StoneCells_SAVE_PATH))
                        {
                            _aPathFind.ReLoadStoneCells(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(StoneCells_SAVE_PATH)));
                        }
                        _aPathFind.SetStartCell(parkingCell);
                        _aPathFind.SetGoalCell(stationCell);

                        Debug.WriteLine(string.Format("停车场 - 最近站点 绑定{0} , {1} BeginSearch", parkingCell, stationCell));
                        if (_aPathFind.BeginSearch())
                        {
                            Debug.WriteLine(string.Format("停车场 - 最近站点 绑定{0} , {1} SearchEnd", parkingCell, stationCell));
                            double length = 0.0d;
                            Point? startPoint = null;
                            foreach (Point location in _aPathFind.PathPoint)
                            {
                                if (startPoint.HasValue)
                                {
                                    length += GetPointLength(startPoint.Value, location);
                                }
                                startPoint = location;
                            }
                            if (parkingStationLength - length > 0)
                            {
                                parkingStationLength = length;
                                zuijinParkingStationCell = stationCell;
                            }
                        }
                    }
                    parkingStationDic.Add(parkingCell, zuijinParkingStationCell);
                    Debug.WriteLine(string.Format("parkingStationDic Num: {0}", parkingStationDic.Count));
                }
                Debug.WriteLine("停车场 - 最近站点 绑定 End");
                #endregion 停车场 - 最近站点 绑定

                foreach (Cell sceneryCell in sceneryCellsTemp)
                {
                    foreach (Cell parkingCell in keyCellsTemp)
                    {

                        if (sceneryStationDic[sceneryCell].Location.Equals(parkingStationDic[parkingCell].Location))
                        {
                            ReSet();
                            if (File.Exists(StoneCells_SAVE_PATH))
                            {
                                _aPathFind.ReLoadStoneCells(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(StoneCells_SAVE_PATH)));
                            }
                            #region 最近站点相同

                            _aPathFind.SetStartCell(sceneryCell);
                            _aPathFind.SetGoalCell(parkingCell);

                            if (_aPathFind.BeginSearch())
                            {
                                Bitmap bitmap = (Bitmap)panelMap.BackgroundImage.Clone();

                                using (bitmap)
                                {
                                    double length = 0d;
                                    Point point1 = Point.Empty;
                                    Point point2 = Point.Empty;
                                    using (Graphics g = Graphics.FromImage(bitmap))
                                    {
                                        #region 绘制路线
                                        using (Pen p = new Pen(penColor, CELL_WIDTH))
                                        {
                                            p.StartCap = LineCap.Round;
                                            p.EndCap = LineCap.Round;
                                            p.LineJoin = LineJoin.Round;
                                            Point? startPoint = null;
                                            foreach (Point location in _aPathFind.PathPoint)
                                            {
                                                if (startPoint.HasValue)
                                                {
                                                    length += GetPointLength(startPoint.Value, location);
                                                    Point p1 = new Point(startPoint.Value.X + CELL_WIDTH / 2, startPoint.Value.Y + CELL_WIDTH / 2);
                                                    Point p2 = new Point(location.X + CELL_WIDTH / 2, location.Y + CELL_WIDTH / 2);
                                                    if (startPoint.Value.Y - location.Y > CELL_WIDTH * 2
                                                        || startPoint.Value.X - location.X > CELL_WIDTH * 2)
                                                    {
                                                        g.DrawLine(p, p1, p2);
                                                    }
                                                    else
                                                    {
                                                        g.DrawCurve(p, new[] { p1, p2 });
                                                    }
                                                    point2 = p2;
                                                }
                                                else
                                                {
                                                    point1 = new Point(location.X + CELL_WIDTH / 2, location.Y + CELL_WIDTH / 2);
                                                }
                                                startPoint = location;
                                            }
                                        }
                                        #endregion
                                    }
                                    #region 正向
                                    Bitmap bitmapTemp = (Bitmap)bitmap.Clone();
                                    using (Graphics g = Graphics.FromImage(bitmapTemp))
                                    {
                                        Point startImgPoint = new Point(point1.X - Properties.Resources.MapStart.Width / 2, point1.Y - Properties.Resources.MapStart.Height - CELL_WIDTH);
                                        Point endImgPoint = new Point(point2.X - Properties.Resources.MapEnd.Width / 2, point2.Y - Properties.Resources.MapEnd.Height - CELL_WIDTH);
                                        g.DrawImage(Properties.Resources.MapStart, startImgPoint);
                                        g.DrawImage(Properties.Resources.MapEnd, endImgPoint);
                                    }
                                    WayInfo wayInfo = new WayInfo
                                    {
                                        PicName = _aPathFind.StartCell.Name + "-" + _aPathFind.GoalCell.Name + ".jpg",
                                        From = _aPathFind.StartCell.Name,
                                        Distance = Convert.ToInt32(Math.Floor(Convert.ToDecimal(length) * ruleWidth / rulePix)),
                                        IsOut = true
                                    };
                                    wayInfo.Time = Convert.ToInt32(Convert.ToDecimal(wayInfo.Distance) / numericUpDown2.Value);

                                    if (!Directory.Exists(@"./Save/" + textBoxMapName.Text + wayInfo.OutDirPath))
                                    {
                                        Directory.CreateDirectory(@"./Save/" + textBoxMapName.Text + wayInfo.OutDirPath);
                                    }
                                    bitmapTemp.Save(@"./Save/" + textBoxMapName.Text + wayInfo.Out,ImageFormat.Png);
                                    bitmapTemp.Dispose();

                                    if (!string.IsNullOrEmpty(_aPathFind.GoalCell.Remark))
                                    {
                                        string[] toStrs = _aPathFind.GoalCell.Remark.Split(';');
                                        foreach (string toStr in toStrs)
                                        {
                                            WayInfo newWayInfo = wayInfo.Clone();
                                            newWayInfo.To = toStr;
                                            wayInfos.Add(newWayInfo);
                                        }
                                    }
                                    wayInfo.To = _aPathFind.GoalCell.Name;
                                    wayInfos.Add(wayInfo);
                                    #endregion 正向

                                    #region 反向
                                    bitmapTemp = (Bitmap)bitmap.Clone();
                                    using (Graphics g = Graphics.FromImage(bitmapTemp))
                                    {
                                        Point startImgPoint = new Point(point2.X - Properties.Resources.MapStart.Width / 2, point2.Y - Properties.Resources.MapStart.Height - CELL_WIDTH);
                                        Point endImgPoint = new Point(point1.X - Properties.Resources.MapEnd.Width / 2, point1.Y - Properties.Resources.MapEnd.Height - CELL_WIDTH);
                                        g.DrawImage(Properties.Resources.MapStart, startImgPoint);
                                        g.DrawImage(Properties.Resources.MapEnd, endImgPoint);
                                    }
                                    wayInfo = new WayInfo
                                    {
                                        PicName = _aPathFind.GoalCell.Name + "-" + _aPathFind.StartCell.Name + ".jpg",
                                        To = _aPathFind.StartCell.Name,
                                        Distance = Convert.ToInt32(Math.Floor(Convert.ToDecimal(length) * ruleWidth / rulePix)),
                                        IsOut = true
                                    };
                                    wayInfo.Time = Convert.ToInt32(Convert.ToDecimal(wayInfo.Distance) / numericUpDown2.Value);

                                    if (!Directory.Exists(@"./Save/" + textBoxMapName.Text + wayInfo.OutDirPath))
                                    {
                                        Directory.CreateDirectory(@"./Save/" + textBoxMapName.Text + wayInfo.OutDirPath);
                                    }
                                    bitmapTemp.Save(@"./Save/" + textBoxMapName.Text + wayInfo.Out, ImageFormat.Png);
                                    bitmapTemp.Dispose();

                                    if (!string.IsNullOrEmpty(_aPathFind.GoalCell.Remark))
                                    {
                                        string[] toStrs = _aPathFind.GoalCell.Remark.Split(';');
                                        foreach (string toStr in toStrs)
                                        {
                                            WayInfo newWayInfo = wayInfo.Clone();
                                            newWayInfo.From = toStr;
                                            wayInfos.Add(newWayInfo);
                                        }
                                    }
                                    wayInfo.From = _aPathFind.GoalCell.Name;
                                    wayInfos.Add(wayInfo);
                                    #endregion 反向
                                }
                            }
                            #endregion 最近站点相同
                            GC.Collect();
                        }
                        else
                        {
                            #region 最近站点不同

                            Bitmap bitmap = (Bitmap)panelMap.BackgroundImage.Clone();
                            double length = 0d;

                            using (bitmap)
                            {
                                ReSet();
                                if (File.Exists(StoneCells_SAVE_PATH))
                                {
                                    _aPathFind.ReLoadStoneCells(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(StoneCells_SAVE_PATH)));
                                }

                                _aPathFind.SetStartCell(sceneryCell);
                                _aPathFind.SetGoalCell(sceneryStationDic[sceneryCell]);
                                #region 绘制路线 景点 - 站点
                                if (_aPathFind.BeginSearch())
                                {
                                    using (Graphics g = Graphics.FromImage(bitmap))
                                    {
                                        using (Pen p = new Pen(penColor, CELL_WIDTH))
                                        {
                                            p.StartCap = LineCap.Round;
                                            p.EndCap = LineCap.Round;
                                            p.LineJoin = LineJoin.Round;
                                            Point? startPoint = null;
                                            foreach (Point location in _aPathFind.PathPoint)
                                            {
                                                if (startPoint.HasValue)
                                                {
                                                    length += GetPointLength(startPoint.Value, location);
                                                    Point p1 = new Point(startPoint.Value.X + CELL_WIDTH / 2, startPoint.Value.Y + CELL_WIDTH / 2);
                                                    Point p2 = new Point(location.X + CELL_WIDTH / 2, location.Y + CELL_WIDTH / 2);
                                                    if (startPoint.Value.Y - location.Y > CELL_WIDTH * 2
                                                        || startPoint.Value.X - location.X > CELL_WIDTH * 2)
                                                    {
                                                        g.DrawLine(p, p1, p2);
                                                    }
                                                    else
                                                    {
                                                        g.DrawCurve(p, new[] { p1, p2 });
                                                    }
                                                }
                                                startPoint = location;
                                            }
                                        }
                                    }
                                }
                                #endregion 绘制路线 景点 - 站点

                                ReSet();
                                if (File.Exists(StoneCells_SAVE_PATH))
                                {
                                    _aPathFind.ReLoadStoneCells(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(StoneCells_SAVE_PATH)));
                                }

                                _aPathFind.SetStartCell(parkingCell);
                                _aPathFind.SetGoalCell(parkingStationDic[parkingCell]);
                                #region 绘制路线 停车场 - 站点
                                if (_aPathFind.BeginSearch())
                                {
                                    using (Graphics g = Graphics.FromImage(bitmap))
                                    {
                                        using (Pen p = new Pen(penColor, CELL_WIDTH))
                                        {
                                            p.StartCap = LineCap.Round;
                                            p.EndCap = LineCap.Round;
                                            p.LineJoin = LineJoin.Round;
                                            Point? startPoint = null;
                                            foreach (Point location in _aPathFind.PathPoint)
                                            {
                                                if (startPoint.HasValue)
                                                {
                                                    length += GetPointLength(startPoint.Value, location);
                                                    Point p1 = new Point(startPoint.Value.X + CELL_WIDTH / 2, startPoint.Value.Y + CELL_WIDTH / 2);
                                                    Point p2 = new Point(location.X + CELL_WIDTH / 2, location.Y + CELL_WIDTH / 2);
                                                    if (startPoint.Value.Y - location.Y > CELL_WIDTH * 2
                                                        || startPoint.Value.X - location.X > CELL_WIDTH * 2)
                                                    {
                                                        g.DrawLine(p, p1, p2);
                                                    }
                                                    else
                                                    {
                                                        g.DrawCurve(p, new[] { p1, p2 });
                                                    }
                                                }
                                                startPoint = location;
                                            }
                                        }
                                    }
                                }
                                #endregion 绘制路线 停车场 - 站点

                                #region 站点 - 下一站点

                                int startIndex = stationCellsList.FindIndex(record => record.Location.Equals(sceneryStationDic[sceneryCell].Location));
                                int endIndex = stationCellsList.FindIndex(record => record.Location.Equals(parkingStationDic[parkingCell].Location));
                                Debug.WriteLine(string.Format("startIndex:{0}  endIndex:{1}", startIndex, endIndex));
                                do
                                {
                                    using (Graphics g = Graphics.FromImage(bitmap))
                                    {
                                        //Properties.Resources._0
                                        g.DrawImage(roadBitmaps[startIndex], new Point(23,13) );
                                    }

                                    startIndex++;
                                    if (startIndex >= stationCellsList.Count)
                                    {
                                        startIndex = 0;
                                    }
                                } while (startIndex!= endIndex);

                                #endregion 站点 - 下一站点

                                #region 正向
                                Bitmap bitmapTemp = (Bitmap)bitmap.Clone();
                                using (Graphics g = Graphics.FromImage(bitmapTemp))
                                {
                                    Point startImgPoint = new Point(sceneryCell.Location.X - Properties.Resources.MapStart.Width / 2
                                                                  , sceneryCell.Location.Y - Properties.Resources.MapStart.Height - CELL_WIDTH);
                                    Point endImgPoint = new Point(parkingCell.Location.X - Properties.Resources.MapEnd.Width / 2
                                                                , parkingCell.Location.Y - Properties.Resources.MapEnd.Height - CELL_WIDTH);

                                    g.DrawImage(Properties.Resources.MapStart, startImgPoint);
                                    g.DrawImage(Properties.Resources.MapEnd, endImgPoint);
                                }
                                WayInfo wayInfo = new WayInfo
                                {
                                    PicName = sceneryCell.Name + "-" + parkingCell.Name + ".jpg",
                                    From = sceneryCell.Name,
                                    Distance = Convert.ToInt32(Math.Floor(Convert.ToDecimal(length) * ruleWidth / rulePix / 10 * 3)),
                                    IsOut = true
                                };
                                wayInfo.Time = Convert.ToInt32(Convert.ToDecimal(wayInfo.Distance) / numericUpDown1.Value);

                                if (!Directory.Exists(@"./Save/" + textBoxMapName.Text + wayInfo.OutDirPath))
                                {
                                    Directory.CreateDirectory(@"./Save/" + textBoxMapName.Text + wayInfo.OutDirPath);
                                }
                                bitmapTemp.Save(@"./Save/" + textBoxMapName.Text + wayInfo.Out,ImageFormat.Png);
                                bitmapTemp.Dispose();

                                if (!string.IsNullOrEmpty(parkingCell.Remark))
                                {
                                    string[] toStrs = parkingCell.Remark.Split(';');
                                    foreach (string toStr in toStrs)
                                    {
                                        WayInfo newWayInfo = wayInfo.Clone();
                                        newWayInfo.To = toStr;
                                        wayInfos.Add(newWayInfo);
                                    }
                                }
                                wayInfo.To = parkingCell.Name;
                                wayInfos.Add(wayInfo);
                                #endregion 正向

                                #region 反向
                                bitmapTemp = (Bitmap)bitmap.Clone();
                                using (Graphics g = Graphics.FromImage(bitmapTemp))
                                {
                                    Point startImgPoint = new Point(parkingCell.Location.X - Properties.Resources.MapStart.Width / 2
                                                                  , parkingCell.Location.Y - Properties.Resources.MapStart.Height - CELL_WIDTH);
                                    Point endImgPoint = new Point(sceneryCell.Location.X - Properties.Resources.MapEnd.Width / 2
                                                                , sceneryCell.Location.Y - Properties.Resources.MapEnd.Height - CELL_WIDTH);
                                    g.DrawImage(Properties.Resources.MapStart, startImgPoint);
                                    g.DrawImage(Properties.Resources.MapEnd, endImgPoint);
                                }
                                wayInfo = new WayInfo
                                {
                                    PicName = parkingCell.Name + "-" + sceneryCell.Name + ".jpg",
                                    To = sceneryCell.Name,
                                    Distance = Convert.ToInt32(Math.Floor(Convert.ToDecimal(length) * ruleWidth / rulePix)),
                                    IsOut = true
                                };
                                wayInfo.Time = Convert.ToInt32(Convert.ToDecimal(wayInfo.Distance) / numericUpDown1.Value);

                                if (!Directory.Exists(@"./Save/" + textBoxMapName.Text + wayInfo.OutDirPath))
                                {
                                    Directory.CreateDirectory(@"./Save/" + textBoxMapName.Text + wayInfo.OutDirPath);
                                }
                                bitmapTemp.Save(@"./Save/" + textBoxMapName.Text + wayInfo.Out,ImageFormat.Png);
                                bitmapTemp.Dispose();

                                if (!string.IsNullOrEmpty(parkingCell.Remark))
                                {
                                    string[] toStrs = parkingCell.Remark.Split(';');
                                    foreach (string toStr in toStrs)
                                    {
                                        WayInfo newWayInfo = wayInfo.Clone();
                                        newWayInfo.From = toStr;
                                        wayInfos.Add(newWayInfo);
                                    }
                                }
                                wayInfo.From = parkingCell.Name;
                                wayInfos.Add(wayInfo);
                                #endregion 反向
                            }
                            #endregion 最近站点不同
                            GC.Collect();
                        }
                    }
                }
                #endregion 总图
            }
            GC.Collect();

            if (!Directory.Exists(@"./Save/" + textBoxMapName.Text))
                Directory.CreateDirectory(@"./Save/" + textBoxMapName.Text);
            File.WriteAllText(@"./Save/" + textBoxMapName.Text + "/ Data.json", JsonConvert.SerializeObject(wayInfos, Formatting.Indented,
                            new JsonSerializerSettings { NullValueHandling = NullValueHandling.Ignore }));
            MessageBox.Show(@"生成完成");
        }

        /// <summary>
        /// 批量生成
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void button1_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(textBoxMapName.Text))
            {
                MessageBox.Show(@"输入地图名称");
                return;
            }
            SaveMapInfoButton_Click(sender, e);

            _shThread = new Thread(this.BeginSearchMany);
            _shThread.Start();
        }

        /// <summary>
        /// 载入SVG
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void button2_Click(object sender, EventArgs e)
        {
            #region svg
            openSvgFile.Filter = @"svg(*.svg)|*.svg";
            openSvgFile.Multiselect = true;
            #endregion
            #region svg

            if (openSvgFile.ShowDialog() == DialogResult.OK)
            {
                foreach (string filePath in openSvgFile.FileNames)
                {
                    _svgDoc = SvgDocument.Open(filePath);

                    ReSet(Path.GetFileName(filePath));
                }
            }

            #endregion
        }
        /// <summary>
        /// 景点起名
        /// </summary>
        private void button3_Click(object sender, EventArgs e)
        {
            ResetButtonName();
            if (_commandType != CommandType.SetStation)
            {
                button3.Text = @"取消景点起名";
                _commandType = CommandType.SetScenery;
            }
            else
            {
                _commandType = CommandType.None;
            }
        }
        /// <summary>
        /// 站点起名
        /// </summary>
        private void button4_Click(object sender, EventArgs e)
        {
            ResetButtonName();
            if (_commandType != CommandType.SetStation)
            {
                button4.Text = @"取消站点起名/r/n(有序)";
                _commandType = CommandType.SetStation;
            }
            else
            {
                _commandType = CommandType.None;
            }
        }

        private void ResetButtonName()
        {
            this.btnCtStoneHand.Text = @"手动生成障碍";
            this.btnCtStoneHand.ForeColor = Color.Black;
            buttonSetGoalCells.Text = @"设置批量终点";
            buttonName.Text = @"停车区域起名";
            button4.Text = @"站点起名/r/n(有序)";
            button3.Text = @"景点起名";
        }
    }
    enum CommandType
    {
        None,
        /// <summary>
        /// 起名
        /// </summary>
        BeName,
        CreateStone,
        SetGoalCells,
        /// <summary>
        /// 景点
        /// </summary>
        SetScenery,
        /// <summary>
        /// 站点
        /// </summary>
        SetStation
    }
}
