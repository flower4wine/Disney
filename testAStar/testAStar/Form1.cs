using System;
using System.Collections.Generic;
using System.Drawing;
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
            //this.panelMap.Size = new Size(MAP_WIDTH, MAP_HEIGHT);
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

        bool isCreteaFlage=false;

        bool isFirst = true;
        SvgDocument svgDoc;

        #region const
        const string DATA_DIR = @".\Data";
        const string MPA_SAVE_PATH = @".\Data\Map.svg";
        const string StoneCells_SAVE_PATH = @".\Data\StoneCells.json";
        const string KeyCells_SAVE_PATH = @".\Data\KeyCells.json";
        #endregion const

        private void panelMap_Paint(object sender, PaintEventArgs e)
        {
            if (isFirst)
            {
                isFirst=false;
                aPathFind.DrawMapGrid();
            }
            aPathFind.MapControlDraw();
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
            //判断是否手动添加障碍物
            if (isCreteaFlage)
            {
                aPathFind.SetStoneCell(e.X, e.Y);
            }
            else if (aPathFind.IsInStone(e.X, e.Y))
            {
                MessageBox.Show("请另选方格");
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
            if (this.btnCtStoneHand.Text == "手动生成障碍")
            {
                this.btnCtStoneHand.Text = "取消生成障碍";
                this.btnCtStoneHand.ForeColor = Color.Red;
                isCreteaFlage = true;
            }
            else
            {
                isCreteaFlage = false;
                this.btnCtStoneHand.Text = "手动生成障碍";
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
            System.Diagnostics.Stopwatch sw = new System.Diagnostics.Stopwatch();
            sw.Start();
            if(aPathFind.BeginSearch())
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
            sw.Stop();
            MessageBox.Show(sw.Elapsed.ToString());
        }
        #endregion
        
        #region 重置窗体
        private void btnRest_Click(object sender, EventArgs e)
        {
            ReSet();
        }
        private void ReSet()
        {
            aPathFind.ReSet();
            aPathFind.MapControlDraw();
            this.btnCtStoneHand.Enabled = true;
            this.btnCtStoneRodom.Enabled = true;
            this.btnStartSearch.Enabled = true;


            if (svgDoc != null)
            {
                MAP_WIDTH = Convert.ToInt32(svgDoc.Width.Value);
                MAP_HEIGHT = Convert.ToInt32(svgDoc.Height.Value);
                CellWidthNumericUpDown.Maximum = MAP_WIDTH < MAP_HEIGHT ? MAP_WIDTH : MAP_HEIGHT;
                panelMap.BackgroundImage = svgDoc.Draw();
            }
            else
            {
                panelMap.BackColor= System.Drawing.SystemColors.Control;
                panelMap.BackgroundImage = new Bitmap(1,1);
            }
            panelMap.Size = new System.Drawing.Size(MAP_WIDTH, MAP_HEIGHT);

            aPathFind = new APathFind(FLOAT_LINE_WIDTH, CELL_WIDTH, MAP_WIDTH, MAP_HEIGHT, this.panelMap);

            aPathFind.DrawMapGrid();
        }
        #endregion 

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
           if(shThread!=null)
           {
               shThread.Abort();
           }
        }

        private void SvgImportButton_Click(object sender, EventArgs e)
        {
            if (openSvgFile.ShowDialog() == DialogResult.OK)
            {
                svgDoc = SvgDocument.Open(openSvgFile.FileName);
                ReSet();
            }
        }
        private void CellWidthNumericUpDown_ValueChanged(object sender, EventArgs e)
        {
            CELL_WIDTH = Convert.ToInt32( CellWidthNumericUpDown.Value);
            ReSet();
        }

        private void SaveSvgButton_Click(object sender, EventArgs e)
        {
            SetPath();
            //File.WriteAllText("a.svg", svgDoc.GetXML());
        }
        private void SetPath()
        {
            SvgColourServer _Stroke = new SvgColourServer(Color.Black);

            var groupElement = new SvgGroup() { ID = "MapPathWay" };
            svgDoc.Children.Add(groupElement);
            Point? startPoint = null;
            foreach(Point location in aPathFind.SVGLocation)
            {
                if(startPoint.HasValue)
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
            if(!Directory.Exists(DATA_DIR))
            {
                Directory.CreateDirectory(DATA_DIR);
            }
            //File.CreateText(MPA_SAVE_PATH);
            //File.CreateText(StoneCells_SAVE_PATH);
            //File.CreateText(KeyCells_SAVE_PATH);
            File.WriteAllText(MPA_SAVE_PATH, svgDoc.GetXML());
            File.WriteAllText(StoneCells_SAVE_PATH, JsonConvert.SerializeObject(aPathFind.StoneCells));
            File.WriteAllText(KeyCells_SAVE_PATH, JsonConvert.SerializeObject(aPathFind.KeyCells));
        }

        private void UseSavedMapInfoButton_Click(object sender, EventArgs e)
        {
            if(File.Exists(MPA_SAVE_PATH))
            {
                svgDoc = SvgDocument.Open(MPA_SAVE_PATH);
                ReSet();
                if (File.Exists(StoneCells_SAVE_PATH))
                {
                    aPathFind.ReLoadStoneCells(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(StoneCells_SAVE_PATH)));
                }
                if (File.Exists(KeyCells_SAVE_PATH))
                {
                    aPathFind.ReLoadKeyCells(JsonConvert.DeserializeObject<List<Cell>>(File.ReadAllText(KeyCells_SAVE_PATH)));
                }
            }
        }
    }
}
