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
using Svg.Transforms;
using System.IO;

namespace testAStar
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();

            Init();
            this.panelMap.Paint += new System.Windows.Forms.PaintEventHandler(this.panelMap_Paint);
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            CellWidthNumericUpDown.Maximum = MAP_WIDTH < MAP_HEIGHT ? MAP_WIDTH : MAP_HEIGHT;
            CellWidthNumericUpDown.Value = Convert.ToDecimal(CELL_WIDTH);
        }
        void Init()
        {
            //aPathFind = new APathFind(FLOAT_LINE_WIDTH, CELL_WIDTH, MAP_WIDTH, MAP_HEIGHT);
            aPathFind = new APathFind(FLOAT_LINE_WIDTH, CELL_WIDTH, MAP_WIDTH, MAP_HEIGHT, this.panelMap);
        }
        APathFind aPathFind = null;
        static float FLOAT_LINE_WIDTH = 1.0F;
        static int CELL_WIDTH = 10;
        static int MAP_WIDTH = 300;
        static int MAP_HEIGHT = 300;
        Thread shThread = null;

        bool isCreateStoneFlage = false;

        bool isFirst = true;
        SvgDocument svgDoc;
        private bool isMouseDown = false;
        Point lastPoint = new Point(-1, -1);
        private string _imgPath = string.Empty;
        CommandType CommandType = CommandType.None;

        #region 比例尺

        private decimal ruleWidth = 50;// 米
        private decimal rulePix = 143;// 像素
        // 2.86 像素/米
        // 0.3496503497米/像素
        // 停车场 （10倍放大）
        #endregion

        #region const
        const string DATA_DIR = @".\Data";
        const string MPA_SAVE_PATH = @".\Data\Map.svg";
        const string StoneCells_SAVE_PATH = @".\Data\StoneCells.json";
        const string KeyCells_SAVE_PATH = @".\Data\KeyCells.json";
        const string GoalCells_SAVE_PATH = @".\Data\GoalCells.json";
        #endregion const

        private void panelMap_Paint(object sender, PaintEventArgs e)
        {
            //if (isFirst)
            //{
            isFirst = false;
            //    aPathFind.DrawMapGrid();
            //}
            //aPathFind.MapControlDraw();
        }

        #region 随机生成障碍物
        private void btnCreateStone_Click(object sender, EventArgs e)
        {
            aPathFind.RandStoneCell();
            aPathFind.MapControlDraw();
        }
        #endregion

        //鼠标按下事件
        private void panelMap_MouseDown(object sender, MouseEventArgs e)
        {
            if (this.CommandType == CommandType.CreateStone)
            {
                isMouseDown = true;
            }
            SetCellInfo(e);
        }
        private void panelMap_MouseUp(object sender, MouseEventArgs e)
        {
            isMouseDown = false;
        }

        Point[] GetPointList(Point startPoint, Point endPoint)
        {
            List<Point> result = new List<Point>();

            return result.ToArray();
        }

        private void panelMap_MouseMove(object sender, MouseEventArgs e)
        {

            if (!isMouseDown)
            {
                return;
            }
            SetCellInfo(e);
        }

        private Cell lastNameCell;
        private void SetCellInfo(MouseEventArgs e)
        {
            textBox1.Text = string.Empty;
            if (this.CommandType == CommandType.SetStation)
            {
                if (string.IsNullOrEmpty(textBoxName.Text))
                {
                    MessageBox.Show("前缀不能为空");
                    return;
                }
                textBox1.Text = aPathFind.GetStationCellName(e.X, e.Y);
                lastNameCell = new Cell(e.X / CELL_WIDTH * CELL_WIDTH, e.Y / CELL_WIDTH * CELL_WIDTH,
                    textBoxName.Text);
                aPathFind.SetStationCell(lastNameCell);
            }
            else if (this.CommandType == CommandType.SetScenery)
            {
                if (string.IsNullOrEmpty(textBoxName.Text))
                {
                    MessageBox.Show("前缀不能为空");
                    return;
                }
                textBox1.Text = aPathFind.GetSceneryCellName(e.X, e.Y);
                lastNameCell = new Cell(e.X / CELL_WIDTH * CELL_WIDTH, e.Y / CELL_WIDTH * CELL_WIDTH,
                    textBoxName.Text);
                aPathFind.SetSceneryCell(lastNameCell);
            }
            else if (this.CommandType == CommandType.SetGoalCells)
            {
                Cell cell = aPathFind.GetKeyCell(e.X, e.Y);
                if (cell != null)
                {
                    cell.Remark = textBoxGoals.Text;
                    this.goalCells.Add(cell);
                }
            }
            else if (this.CommandType == CommandType.CreateStone)
            {
                aPathFind.SetStoneCell(e.X, e.Y);
            }
            else if (this.CommandType == CommandType.BeName)
            {
                if (string.IsNullOrEmpty(textBoxName.Text))
                {
                    MessageBox.Show("前缀不能为空");
                    return;
                }
                textBox1.Text = aPathFind.GetKeyCellName(e.X, e.Y);
                lastNameCell = new Cell(e.X / CELL_WIDTH * CELL_WIDTH, e.Y / CELL_WIDTH * CELL_WIDTH,
                    //textBoxName.Text + "-" + numericUpDownName.Value.ToString().PadLeft(4, '0'));
                    textBoxName.Text);
                aPathFind.SetKeyCell(lastNameCell);
            }
            else if (aPathFind.IsInStone(e.X, e.Y))
            {
                // MessageBox.Show("请另选方格");
                return;
            }
            else if (aPathFind.StartCell == null)
            {
                aPathFind.SetStartCell(e.X, e.Y);
                this.btnCtStoneHand.Enabled = false;
                this.btnCtStoneRodom.Enabled = false;
            }
            else if (aPathFind.GoalCell == null)
            {
                aPathFind.SetGoalCell(e.X, e.Y);
            }
            aPathFind.MapControlDraw();
        }

        //开启手动生成障碍
        private void btnCtStoneHand_Click(object sender, EventArgs e)
        {
            ResetButtonName();
            if (CommandType != CommandType.CreateStone)
            {
                this.btnCtStoneHand.Text = @"取消生成障碍";
                this.btnCtStoneHand.ForeColor = Color.Red;
                isCreateStoneFlage = true;
                CommandType = CommandType.CreateStone;
            }
            else
            {
                isCreateStoneFlage = false;
                CommandType = CommandType.None;
                this.btnCtStoneHand.Text = @"手动生成障碍";
                this.btnCtStoneHand.ForeColor = Color.Black;
            }
        }
        //开启寻路
        private void btnStartSearch_Click(object sender, EventArgs e)
        {
            if (aPathFind.StartCell == null || aPathFind.GoalCell == null)
            {
                MessageBox.Show("缺少起点或目标点");
                return;
            }
            this.btnRest.Enabled = false;
            this.btnStartSearch.Enabled = false;
            shThread = new Thread(new ThreadStart(this.BeginSearch));
            shThread.Start();
        }

        #region 开始搜索
        public void BeginSearch()
        {
            if (aPathFind.BeginSearch())
            {
                aPathFind.MapControlDraw();

                this.btnRest.Invoke(new EventHandler(delegate
                {
                    this.btnRest.Enabled = true;
                }));
            }
            else
            {
                MessageBox.Show("苦海无边，回头是岸！");

                this.btnRest.Invoke(new EventHandler(delegate
                {
                    this.btnRest.Enabled = true;
                }));
                return;
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
            aPathFind.ReSet();
            aPathFind.MapControlDraw();
            this.btnCtStoneHand.Enabled = true;
            // this.btnCtStoneRodom.Enabled = true;
            this.btnStartSearch.Enabled = true;

            if (svgDoc != null)
            {
                MAP_WIDTH = Convert.ToInt32(svgDoc.Width.Value *Convert.ToSingle(numericUpDown3.Value));
                MAP_HEIGHT = Convert.ToInt32(svgDoc.Height.Value * Convert.ToSingle(numericUpDown3.Value));
                CellWidthNumericUpDown.Maximum = MAP_WIDTH < MAP_HEIGHT ? MAP_WIDTH : MAP_HEIGHT;
                Image oldBitmap = panelMap.BackgroundImage;
                panelMap.BackgroundImage = svgDoc.Draw(MAP_WIDTH, MAP_HEIGHT);
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

            aPathFind = new APathFind(FLOAT_LINE_WIDTH, CELL_WIDTH, MAP_WIDTH, MAP_HEIGHT, this.panelMap);

            aPathFind.DrawMapGrid();
        }
        #endregion 

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (shThread != null)
            {
                shThread.Abort();
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
                        foreach (Point location in aPathFind.PathPoint)
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
                                    g.DrawCurve(p, new Point[] { p1, p2 });
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
                    PicName = aPathFind.StartCell.Name + "-" + aPathFind.GoalCell.Name + ".png",
                    From = aPathFind.StartCell.Name,
                    Distance = Convert.ToInt32(Math.Floor(Convert.ToDecimal(length) * ruleWidth / rulePix))
                };
                wayInfo.Time = Convert.ToInt32(Convert.ToDecimal(wayInfo.Distance) / numericUpDown2.Value);

                if (!Directory.Exists(@"./Save" + wayInfo.InnerDirPath))
                {
                    Directory.CreateDirectory(@"./Save" + wayInfo.InnerDirPath);
                }
                bitmapTemp.Save(@"./Save" + wayInfo.Inner);
                bitmapTemp.Dispose();
                bitmapTemp = null;
                if (!string.IsNullOrEmpty(aPathFind.GoalCell.Remark))
                {
                    string[] toStrs = aPathFind.GoalCell.Remark.Split(';');
                    foreach (string toStr in toStrs)
                    {
                        WayInfo newWayInfo = wayInfo.Clone();
                        newWayInfo.To = toStr;
                        wayInfos.Add(newWayInfo);
                    }
                }
                wayInfo.To = aPathFind.GoalCell.Name;
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
                    PicName = aPathFind.GoalCell.Name + "-" + aPathFind.StartCell.Name + ".png",
                    To = aPathFind.StartCell.Name,
                    Distance = Convert.ToInt32(Math.Floor(Convert.ToDecimal(length) * ruleWidth / rulePix))
                };
                wayInfo.Time = Convert.ToInt32(Convert.ToDecimal(wayInfo.Distance) / numericUpDown2.Value);

                if (!Directory.Exists(@"./Save" + wayInfo.InnerDirPath))
                {
                    Directory.CreateDirectory(@"./Save" + wayInfo.InnerDirPath);
                }
                bitmapTemp.Save(@"./Save" + wayInfo.Inner);
                bitmapTemp.Dispose();
                bitmapTemp = null;
                if (!string.IsNullOrEmpty(aPathFind.GoalCell.Remark))
                {
                    string[] toStrs = aPathFind.GoalCell.Remark.Split(';');
                    foreach (string toStr in toStrs)
                    {
                        WayInfo newWayInfo = wayInfo.Clone();
                        newWayInfo.From = toStr;
                        wayInfos.Add(newWayInfo);
                    }
                }
                wayInfo.From = aPathFind.GoalCell.Name;
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
            svgDoc.Children.Add(groupElement);
            Point? startPoint = null;
            foreach (Point location in aPathFind.PathPoint)
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

            var svgXml = svgDoc.GetXML();
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
            //File.WriteAllText(MPA_SAVE_PATH, svgDoc.GetXML());
            //Image oldImage = panelMap.BackgroundImage;
            //panelMap.BackgroundImage = null;
            //oldImage.Dispose();

            if (_imgPath != @".\Data\Map.png"
                && !string.IsNullOrEmpty(_imgPath))
                File.Copy(_imgPath, @".\Data\Map.png", true);
            File.WriteAllText(StoneCells_SAVE_PATH, JsonConvert.SerializeObject(aPathFind.StoneCells, Formatting.Indented,
      new JsonSerializerSettings { NullValueHandling = NullValueHandling.Ignore }));
            File.WriteAllText(KeyCells_SAVE_PATH, JsonConvert.SerializeObject(aPathFind.KeyCells, Formatting.Indented,
      new JsonSerializerSettings { NullValueHandling = NullValueHandling.Ignore }));
            File.WriteAllText(GoalCells_SAVE_PATH, JsonConvert.SerializeObject(this.goalCells, Formatting.Indented,
      new JsonSerializerSettings { NullValueHandling = NullValueHandling.Ignore }));
            File.WriteAllText(@".\Data\SceneryCells.json", JsonConvert.SerializeObject(aPathFind.SceneryCells, Formatting.Indented,
      new JsonSerializerSettings { NullValueHandling = NullValueHandling.Ignore }));
            File.WriteAllText(@".\Data\StationCells.json", JsonConvert.SerializeObject(aPathFind.StationCells, Formatting.Indented,
      new JsonSerializerSettings { NullValueHandling = NullValueHandling.Ignore }));

            //panelMap.BackgroundImage = new Bitmap(@".\Data\Map.png");
        }

        private void UseSavedMapInfoButton_Click(object sender, EventArgs e)
        {
            //if (File.Exists(MPA_SAVE_PATH))
            if (File.Exists(@".\Data\Map.png"))
            {
                //svgDoc = SvgDocument.Open(MPA_SAVE_PATH);
                _imgPath = @".\Data\Map.png";
                this.panelMap.BackgroundImage = new Bitmap(@".\Data\Map.png");
                this.panelMap.Size = this.panelMap.BackgroundImage.Size;
                ReSet();
                if (File.Exists(StoneCells_SAVE_PATH))
                {
                    aPathFind.ReLoadStoneCells(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(StoneCells_SAVE_PATH)));
                }
                if (File.Exists(KeyCells_SAVE_PATH))
                {
                    aPathFind.ReLoadKeyCells(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(KeyCells_SAVE_PATH)));
                }
                if (File.Exists(GoalCells_SAVE_PATH))
                {
                    this.goalCells.Clear();
                    this.goalCells.AddRange(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(GoalCells_SAVE_PATH)));
                }
                if (File.Exists(@".\Data\SceneryCells.json"))
                {
                    aPathFind.ReLoadSceneryCells(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(@".\Data\SceneryCells.json")));
                }
                if (File.Exists(@".\Data\StationCells.json"))
                {
                    aPathFind.ReLoadStationCells(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(@".\Data\StationCells.json")));
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
            Bitmap dsImage = null;
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
                int W = (int)(Math.Max(Math.Abs(w * cos - h * sin), Math.Abs(w * cos + h * sin)));
                int H = (int)(Math.Max(Math.Abs(w * sin - h * cos), Math.Abs(w * sin + h * cos)));

                //目标位图  
                dsImage = new Bitmap(W, H);
                using (Graphics g = Graphics.FromImage(dsImage))
                {
                    g.InterpolationMode = System.Drawing.Drawing2D.InterpolationMode.Bilinear;
                    g.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.HighQuality;

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
            if (CommandType != CommandType.BeName)
            {
                buttonName.Text = @"取消停车区域起名";
                CommandType = CommandType.BeName;
            }
            else
            {
                buttonName.Text = @"停车区域起名";
                CommandType = CommandType.None;
            }
        }


        private void buttonChangeName_Click(object sender, EventArgs e)
        {
            if (lastNameCell != null)
            {
                if (string.IsNullOrEmpty(textBoxName.Text))
                {
                    aPathFind.RemoveKeyCell(lastNameCell);
                    lastNameCell = null;
                }
                else
                {
                    lastNameCell.Name = textBoxName.Text;// + "-" + numericUpDownName.Value.ToString().PadLeft(4, '0');
                }
                //lastNameCell.Name = textBoxName.Text + "-" + numericUpDownName.Value.ToString().PadLeft(4, '0');
            }
            textBox1.Text = string.Empty;
        }
        private void buttonAddName_Click(object sender, EventArgs e)
        {
            aPathFind.SetKeyCellSuffix();

            using (Bitmap bitmap = new Bitmap(panelMap.Width, panelMap.Height))
            {
                panelMap.DrawToBitmap(bitmap, panelMap.Bounds);
                using (Graphics g = Graphics.FromImage(bitmap))
                {
                    Font font = new Font("Times New Roman", 9);
                    SolidBrush colorPointBrush = new SolidBrush(Color.Chartreuse);
                    SolidBrush colorBrush = new SolidBrush(Color.Black);
                    foreach (Cell keyCell in aPathFind.KeyCells)
                    {
                        g.FillEllipse(colorPointBrush, new Rectangle(keyCell.Location, new Size(CELL_WIDTH, CELL_WIDTH)));
                    }
                    foreach (Cell keyCell in aPathFind.KeyCells)
                    {
                        Point stringLocation = keyCell.Location;
                        stringLocation.X += CELL_WIDTH + 1;
                        g.DrawString(keyCell.Name.Substring(keyCell.Name.LastIndexOf('-') + 1).Trim('0'), font, colorBrush,
                            stringLocation);
                    }
                }
                bitmap.Save(@".\Data\MapIndex.png", ImageFormat.Png);
            }
        }

        private void buttonSetGoalCells_Click(object sender, EventArgs e)
        {
            this.btnCtStoneHand.Text = @"手动生成障碍";
            this.btnCtStoneHand.ForeColor = Color.Black;
            this.buttonName.Text = @"停车区域起名";

            if (CommandType != CommandType.SetGoalCells)
            {
                buttonSetGoalCells.Text = @"取消设置批量终点";
                this.CommandType = CommandType.SetGoalCells;
            }
            else
            {
                buttonSetGoalCells.Text = @"设置批量终点";
                CommandType = CommandType.None;
            }
        }

        private List<Cell> goalCells = new List<Cell>();

        public void BeginSearchMany()
        {
            Cell[] keyCellsTemp = new Cell[aPathFind.KeyCells.Count];
            Cell[] sceneryCellsTemp = new Cell[aPathFind.SceneryCells.Count];
            Cell[] stationCellsTemp = new Cell[aPathFind.StationCells.Count];
            aPathFind.KeyCells.CopyTo(keyCellsTemp);
            aPathFind.SceneryCells.CopyTo(sceneryCellsTemp);
            aPathFind.StationCells.CopyTo(stationCellsTemp);
            List<WayInfo> wayInfos = new List<WayInfo>();

            if (!checkBoxIsAll.Checked)
            {
                #region 库内
                foreach (Cell goalCell in this.goalCells)
                {
                    foreach (Cell keyCellTemp in keyCellsTemp)
                    {
                        if (this.goalCells.FindIndex(record => record.Location.Equals(keyCellTemp.Location)) > -1)
                        {
                            continue;
                        }
                        ReSet();
                        if (File.Exists(StoneCells_SAVE_PATH))
                        {
                            aPathFind.ReLoadStoneCells(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(StoneCells_SAVE_PATH)));
                        }

                        aPathFind.SetStartCell(keyCellTemp);
                        aPathFind.SetGoalCell(goalCell);

                        if (aPathFind.BeginSearch())
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
                                    using (Pen p = new Pen(Color.FromArgb(0xff, 0x48, 0xA2, 0x7C), CELL_WIDTH))
                                    {
                                        p.StartCap = LineCap.Round;
                                        p.EndCap = LineCap.Round;
                                        p.LineJoin = LineJoin.Round;
                                        Point? startPoint = null;
                                        foreach (Point location in aPathFind.PathPoint)
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
                                                    g.DrawCurve(p, new Point[] { p1, p2 });
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
                                    PicName = aPathFind.StartCell.Name + "-" + aPathFind.GoalCell.Name + ".png",
                                    From = aPathFind.StartCell.Name,
                                    Distance = Convert.ToInt32(Math.Floor(Convert.ToDecimal(length) * ruleWidth / rulePix))
                                };
                                wayInfo.Time = Convert.ToInt32(Convert.ToDecimal(wayInfo.Distance) / numericUpDown2.Value);

                                if (!Directory.Exists(@"./Save" + wayInfo.InnerDirPath))
                                {
                                    Directory.CreateDirectory(@"./Save" + wayInfo.InnerDirPath);
                                }
                                bitmapTemp.Save(@"./Save" + wayInfo.Inner);
                                bitmapTemp.Dispose();
                                bitmapTemp = null;
                                if (!string.IsNullOrEmpty(aPathFind.GoalCell.Remark))
                                {
                                    string[] toStrs = aPathFind.GoalCell.Remark.Split(';');
                                    foreach (string toStr in toStrs)
                                    {
                                        WayInfo newWayInfo = wayInfo.Clone();
                                        newWayInfo.To = toStr;
                                        wayInfos.Add(newWayInfo);
                                    }
                                }
                                wayInfo.To = aPathFind.GoalCell.Name;
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
                                    PicName = aPathFind.GoalCell.Name + "-" + aPathFind.StartCell.Name + ".png",
                                    To = aPathFind.StartCell.Name,
                                    Distance = Convert.ToInt32(Math.Floor(Convert.ToDecimal(length) * ruleWidth / rulePix))
                                };
                                wayInfo.Time = Convert.ToInt32(Convert.ToDecimal(wayInfo.Distance) / numericUpDown2.Value);

                                if (!Directory.Exists(@"./Save" + wayInfo.InnerDirPath))
                                {
                                    Directory.CreateDirectory(@"./Save" + wayInfo.InnerDirPath);
                                }
                                bitmapTemp.Save(@"./Save" + wayInfo.Inner);
                                bitmapTemp.Dispose();
                                bitmapTemp = null;
                                if (!string.IsNullOrEmpty(aPathFind.GoalCell.Remark))
                                {
                                    string[] toStrs = aPathFind.GoalCell.Remark.Split(';');
                                    foreach (string toStr in toStrs)
                                    {
                                        WayInfo newWayInfo = wayInfo.Clone();
                                        newWayInfo.From = toStr;
                                        wayInfos.Add(newWayInfo);
                                    }
                                }
                                wayInfo.From = aPathFind.GoalCell.Name;
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

                #endregion 总图
            }

            if (!Directory.Exists(@"./Save/" + textBoxMapName.Text))
                Directory.CreateDirectory(@"./Save/" + textBoxMapName.Text);
            File.WriteAllText(@"./Save/" + textBoxMapName.Text + "/ Data.json", JsonConvert.SerializeObject(wayInfos, Formatting.Indented,
                            new JsonSerializerSettings { NullValueHandling = NullValueHandling.Ignore }));
        }

        /// <summary>
        /// 批量生成
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void button1_Click(object sender, EventArgs e)
        {
            SaveMapInfoButton_Click(sender, e);

            shThread = new Thread(new ThreadStart(this.BeginSearchMany));
            shThread.Start();
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
                    svgDoc = SvgDocument.Open(filePath);

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
            if (CommandType != CommandType.SetStation)
            {
                button3.Text = @"取消景点起名";
                CommandType = CommandType.SetScenery;
            }
            else
            {
                button3.Text = @"景点起名";
                CommandType = CommandType.None;
            }
        }
        /// <summary>
        /// 站点起名
        /// </summary>
        private void button4_Click(object sender, EventArgs e)
        {
            ResetButtonName();
            if (CommandType != CommandType.SetStation)
            {
                button4.Text = @"取消站点起名";
                CommandType = CommandType.SetStation;
            }
            else
            {
                button4.Text = @"站点起名";
                CommandType = CommandType.None;
            }
        }

        private void ResetButtonName()
        {
            this.btnCtStoneHand.Text = @"手动生成障碍";
            this.btnCtStoneHand.ForeColor = Color.Black;
            buttonSetGoalCells.Text = @"设置批量终点";
            buttonName.Text = @"停车区域起名";
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
