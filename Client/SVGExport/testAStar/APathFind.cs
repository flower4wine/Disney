using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Windows.Forms;

namespace testAStar
{
    public class APathFind
    {
        #region 构造函数
        public APathFind(Control mapControl = null)
            : this(400, 400, mapControl)
        {
        }
        public APathFind(float lineWidth = 1.0F, int cellWidth = 10, int mapWidth = 400, int mapHeight = 400, Control mapControl = null)
        {
            if (lineWidth > 0.1F)
            {
                this.FLOAT_LINE_WIDTH = lineWidth;
                this.CELL_HALF_LINE_WIDTH = Convert.ToInt32(Math.Ceiling(this.FLOAT_LINE_WIDTH / 2)); ;
                this.INT_LINE_WIDTH = Convert.ToInt32(this.FLOAT_LINE_WIDTH);
            }
            else
            {
                this.FLOAT_LINE_WIDTH = 0.0F;
                this.CELL_HALF_LINE_WIDTH = 0; ;
                this.INT_LINE_WIDTH = 0;
            }

            this.CELL_WIDTH = cellWidth;
            this.CELL_REAL_WIDTH = this.CELL_WIDTH - this.INT_LINE_WIDTH;
            this.CELL_WIDTH_M_INT_LINE_WIDTH = this.CELL_WIDTH - this.INT_LINE_WIDTH;

            this.MAP_WIDTH = mapWidth;
            this.CELL_WIDTH_COUNT = this.MAP_WIDTH / this.CELL_WIDTH;

            this.MAP_HEIGHT = mapHeight;
            this.CELL_HEIGHT_COUNT = this.MAP_HEIGHT / this.CELL_WIDTH;
            this.GIDTempBmp = new Bitmap(this.MAP_WIDTH, this.MAP_HEIGHT);

            this.MapControl = mapControl;

            this.StoneCells = new List<Cell>();
            this.KeyCells = new List<Cell>();
            this.OpenList = new List<Cell>();
            this.ClosedList = new List<Cell>();
            this.BestLoad = new List<Cell>();
            this.SVGLocation = new List<Point>();
        }
        public APathFind(int mapWidth, int mapHeight, Control mapControl = null)
            : this(1.0F, 10, 400, 400, mapControl)
        {

        }
        #endregion 构造函数

        #region 绘图尺寸相关参数
        public float FLOAT_LINE_WIDTH { get; private set; }
        private int CELL_HALF_LINE_WIDTH { get; set; }
        private int INT_LINE_WIDTH { get; set; }
        public int CELL_WIDTH { get; private set; }
        private int CELL_REAL_WIDTH { get; set; }
        private int CELL_WIDTH_M_INT_LINE_WIDTH { get; set; }

        public int MAP_WIDTH { get; private set; }
        private int CELL_WIDTH_COUNT { get; set; }
        public int MAP_HEIGHT { get; private set; }
        private int CELL_HEIGHT_COUNT { get; set; }

        #endregion 绘图尺寸相关参数

        /// <summary>
        /// 障碍物集合
        /// </summary>
        public List<Cell> StoneCells { private set; get; }
        /// <summary>
        /// 关键点(有名字)
        /// </summary>
        public List<Cell> KeyCells { private set; get; }
        /// <summary>
        /// Open列表集合
        /// </summary>
        List<Cell> OpenList { set; get; }
        /// <summary>
        /// Closed列表集合
        /// </summary>
        List<Cell> ClosedList { set; get; }
        Bitmap GIDTempBmp { set; get; }
        /// <summary>
        /// 起始点
        /// </summary>
        public Cell StartCell { private set; get; }
        /// <summary>
        /// 目标点
        /// </summary>
        public Cell GoalCell { private set; get; }
        public List<Cell> BestLoad { private set; get; }
        public List<Point> SVGLocation { private set; get; }

        public Control MapControl { set; get; }

        /// <summary>
        /// 画网格
        /// </summary>
        public void DrawMapGrid()
        {
            Point pt1;
            Point pt2;

            Graphics g = Graphics.FromImage(GIDTempBmp);

            if (!this.INT_LINE_WIDTH.Equals(0))
            {
                Pen pen = new Pen(Color.Black, this.FLOAT_LINE_WIDTH);

                ///画横线
                for (int i = 0; i <= this.CELL_HEIGHT_COUNT; i++)
                {
                    pt1 = new Point(0, i * this.CELL_WIDTH);
                    pt2 = new Point(this.MAP_WIDTH, i * this.CELL_WIDTH);
                    g.DrawLine(pen, pt1, pt2);
                }

                ///画竖线
                for (int i = 0; i <= this.CELL_WIDTH_COUNT; i++)
                {
                    pt1 = new Point(i * this.CELL_WIDTH, 0);
                    pt2 = new Point(i * this.CELL_WIDTH, this.MAP_HEIGHT);
                    g.DrawLine(pen, pt1, pt2);
                }
            }
            ///TODO
            ///缓存地图数据
            ///
        }

        /// <summary>
        /// 生成随机障碍
        /// </summary>
        public void RandStoneCell()
        {
            Random rd = new Random();
            IList<Point> pts = new List<Point>();

            for (int i = 0; i < (this.CELL_WIDTH_COUNT * this.CELL_HEIGHT_COUNT / 100); i++)
            {
                int j = rd.Next(this.CELL_WIDTH_COUNT);
                int k = rd.Next(this.CELL_HEIGHT_COUNT);
                Point pt = new Point();
                pt.X = j * this.CELL_WIDTH;
                pt.Y = k * this.CELL_WIDTH;

                if (StoneCells.FindIndex(record => record.Location.Equals(pt)) == -1)
                {
                    pts.Add(pt);
                }
            }
            this.DrawStones(pts);
        }

        /// <summary>
        /// 设置起点
        /// </summary>
        public void SetStartCell(int mouseX, int mouseY)
        {
            StartCell = new Cell((mouseX / CELL_WIDTH) * CELL_WIDTH, (mouseY / CELL_WIDTH) * CELL_WIDTH);
            StartCell.Name = "起点";
            MapBmpFillRectangle(StartCell, Color.Purple);
        }
        /// <summary>
        /// 设置终点
        /// </summary>
        public void SetGoalCell(int mouseX, int mouseY)
        {
            GoalCell = new Cell((mouseX / CELL_WIDTH) * CELL_WIDTH, (mouseY / CELL_WIDTH) * CELL_WIDTH);
            GoalCell.Name = "终点";
            MapBmpFillRectangle(GoalCell, Color.Yellow);
        }
        /// <summary>
        /// 画石头（障碍物）
        /// </summary>
        public void SetStoneCell(int mouseX, int mouseY)
        {
            this.DrawStone(new Point((mouseX / CELL_WIDTH) * CELL_WIDTH, (mouseY / CELL_WIDTH) * CELL_WIDTH));
        }
        public void AddRangeStoneCells(IEnumerable<Cell> stoneCells)
        {
            this.StoneCells.AddRange(stoneCells);
            MapBmpFillRectangles(stoneCells.ToList(),Color.Red);
        }
        public void ReLoadStoneCells(IEnumerable<Cell> stoneCells)
        {
            this.StoneCells.Clear();
            this.StoneCells.AddRange(stoneCells);
            MapBmpFillRectangles(stoneCells.ToList(), Color.Red);
        }
        public void SetKeyeCell(int mouseX, int mouseY)
        {
            //this.DrawStone(new Point((mouseX / CELL_WIDTH) * CELL_WIDTH, (mouseY / CELL_WIDTH) * CELL_WIDTH));
            this.KeyCells.Add(new Cell((mouseX / CELL_WIDTH) * CELL_WIDTH, (mouseY / CELL_WIDTH) * CELL_WIDTH));
        }
        public void AddKeyCells(IEnumerable<Cell> keyCells)
        {
            this.KeyCells.AddRange(keyCells);
            ///TODO
        }
        public void ReLoadKeyCells(IEnumerable<Cell> keyCells)
        {
            this.KeyCells.Clear();
            this.KeyCells.AddRange(keyCells);
            ///TODO
        }
        /// <summary>
        /// 重置
        /// </summary>
        public void ReSet()
        {
            if (StartCell != null)
            {
                MapBmpFillRectangle(StartCell, Color.WhiteSmoke);
                StartCell = null;
            }
            if (GoalCell != null)
            {
                MapBmpFillRectangle(GoalCell, Color.WhiteSmoke);
                GoalCell = null;
            }

            MapBmpFillRectangles(StoneCells, Color.WhiteSmoke);
            StoneCells.Clear();

            MapBmpFillRectangles(ClosedList, Color.WhiteSmoke);
            ClosedList.Clear();
            
            MapBmpFillRectangles(OpenList, Color.WhiteSmoke);
            OpenList.Clear();
        }

        private void DrawStones(IList<Point> pts)
        {
            IList<Cell> cells = new List<Cell>();
            foreach (Point pt in pts)
            {
                Cell stoneCell = new Cell();
                stoneCell.Location = pt;
                cells.Add(stoneCell);
            }
            this.StoneCells.AddRange(cells.ToArray());
            MapBmpFillRectangles(cells, Color.Red);
        }

        /// <summary>
        /// 画石头（障碍物）
        /// </summary>
        private void DrawStone(Point pt)
        {
            if (!IsInStone(pt))
            {
                Cell stoneCell = new Cell();
                stoneCell.Location = pt;
                MapBmpFillRectangle(stoneCell, Color.Red);
                this.StoneCells.Add(stoneCell);
            }
        }
        /// <summary>
        /// 估计代价
        /// </summary>
        private int H(Cell nCell)
        {
            int x = Math.Abs(nCell.Location.X - GoalCell.Location.X) / CELL_WIDTH;
            int y = Math.Abs(nCell.Location.Y - GoalCell.Location.Y) / CELL_WIDTH;
            int value = x + y;
            return value;
        }

        private void MapBmpFillRectangles(IList<Cell> cells)
        {
            MapBmpFillRectangles(cells, Color.Empty);
        }
        private void MapBmpFillRectangles(IList<Cell> cells, Color brushColor)
        {
            Graphics g = Graphics.FromImage(GIDTempBmp);
            SolidBrush solidBrush = new SolidBrush(brushColor);
            foreach (Cell cell in cells)
            {
                if(brushColor==Color.Empty)
                {
                    g.FillRectangle(new SolidBrush(cell.CellColor), cell.Location.X + CELL_HALF_LINE_WIDTH, cell.Location.Y + CELL_HALF_LINE_WIDTH, CELL_WIDTH_M_INT_LINE_WIDTH, CELL_WIDTH_M_INT_LINE_WIDTH);
                }
                else
                {
                    g.FillRectangle(solidBrush, cell.Location.X + CELL_HALF_LINE_WIDTH, cell.Location.Y + CELL_HALF_LINE_WIDTH, CELL_WIDTH_M_INT_LINE_WIDTH, CELL_WIDTH_M_INT_LINE_WIDTH);
                }
            }
        }

        /// <summary>
        /// 绘色
        /// </summary>
        private void MapBmpFillRectangle(Cell cell)
        {
            MapBmpFillRectangle(cell, Color.Empty);
        }
        private void MapBmpFillRectangle(Cell cell, Color brushColor)
        {
            Graphics g = Graphics.FromImage(GIDTempBmp);
            if (brushColor == Color.Empty)
            {
                g.FillRectangle(new SolidBrush(cell.CellColor), cell.Location.X + CELL_HALF_LINE_WIDTH, cell.Location.Y + CELL_HALF_LINE_WIDTH, CELL_WIDTH_M_INT_LINE_WIDTH, CELL_WIDTH_M_INT_LINE_WIDTH);
            }
            else
            {
                g.FillRectangle(new SolidBrush(brushColor), cell.Location.X + CELL_HALF_LINE_WIDTH, cell.Location.Y + CELL_HALF_LINE_WIDTH, CELL_WIDTH_M_INT_LINE_WIDTH, CELL_WIDTH_M_INT_LINE_WIDTH);
            }
        }

        private bool IsInClosed(Point cellPt)
        {
            return ClosedList.FindIndex(record => record.Location.Equals(cellPt)) != -1;
        }
        private bool IsInOpen(Point cellPt)
        {
            return OpenList.FindIndex(record => record.Location.Equals(cellPt)) != -1;
        }
        public bool IsInStone(int x, int y)
        {
            return IsInStone(new Point((x / this.CELL_WIDTH) * CELL_WIDTH, (y / this.CELL_WIDTH) * CELL_WIDTH));
        }
        private bool IsInStone(Point cellPt)
        {
            return StoneCells.FindIndex(record => record.Location.Equals(cellPt)) != -1;
        }
        private bool IsStoneOrOutWord(Point cellPt)
        {
            if (cellPt.X < 0 || cellPt.X > MAP_WIDTH
                || cellPt.Y < 0 || cellPt.Y > MAP_HEIGHT)
            {
                return false;
            }
            else
            {
                return IsInStone(cellPt);
            }
        }
        private bool IsStoneOrOutWord(Cell cell)
        {
            if (cell.Location.X >= 0 && cell.Location.X < MAP_WIDTH && cell.Location.Y >= 0 && cell.Location.Y < MAP_HEIGHT)
            {
                for (int i = 0; i < this.StoneCells.Count; i++)
                {
                    if (Cell.IsSampleCell(cell, this.StoneCells[i]))
                    {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

        #region 寻找路径估计消耗最小的格子
        private bool SearchBestCell()
        {
            //为起始节点初始化上下左右方格
            Cell upCell = new Cell(ClosedList[ClosedList.Count - 1].Location.X, ClosedList[ClosedList.Count - 1].Location.Y - CELL_WIDTH);

            Cell downCell = new Cell(ClosedList[ClosedList.Count - 1].Location.X, ClosedList[ClosedList.Count - 1].Location.Y + CELL_WIDTH);
            Cell rightCell = new Cell(ClosedList[ClosedList.Count - 1].Location.X + CELL_WIDTH, ClosedList[ClosedList.Count - 1].Location.Y);
            Cell leftCell = new Cell(ClosedList[ClosedList.Count - 1].Location.X - CELL_WIDTH, ClosedList[ClosedList.Count - 1].Location.Y);

            //判断这四个方格是否处于边界是否是障碍物，如果不是则将方格加入到Open集合列表中
            if (this.IsStoneOrOutWord(upCell) && !IsInClosed(upCell.Location) && !IsInOpen(upCell.Location))
            {
                upCell.ParentCell = ClosedList[ClosedList.Count - 1];
                upCell.RealDistance = upCell.ParentCell.RealDistance + 1;
                upCell.EvaluateDistance = this.H(upCell);
                upCell.FinalDistance = upCell.EvaluateDistance + upCell.RealDistance;
                this.OpenList.Add(upCell);
            }
            if (this.IsStoneOrOutWord(downCell) && !IsInClosed(downCell.Location) && !IsInOpen(downCell.Location))
            {
                downCell.ParentCell = ClosedList[ClosedList.Count - 1];
                downCell.RealDistance = downCell.ParentCell.RealDistance + 1;
                downCell.EvaluateDistance = this.H(downCell);
                downCell.FinalDistance = downCell.EvaluateDistance + downCell.RealDistance;
                this.OpenList.Add(downCell);
            }
            if (this.IsStoneOrOutWord(rightCell) && !IsInClosed(rightCell.Location) && !IsInOpen(rightCell.Location))
            {
                rightCell.ParentCell = ClosedList[ClosedList.Count - 1];
                rightCell.RealDistance = rightCell.ParentCell.RealDistance + 1;
                rightCell.EvaluateDistance = this.H(rightCell);
                rightCell.FinalDistance = rightCell.EvaluateDistance + rightCell.RealDistance;
                this.OpenList.Add(rightCell);
            }
            if (this.IsStoneOrOutWord(leftCell) && !IsInClosed(leftCell.Location) && !IsInOpen(leftCell.Location))
            {
                leftCell.ParentCell = ClosedList[ClosedList.Count - 1];
                leftCell.RealDistance = leftCell.ParentCell.RealDistance + 1;
                leftCell.EvaluateDistance = this.H(leftCell);
                leftCell.FinalDistance = leftCell.EvaluateDistance + leftCell.RealDistance;
                this.OpenList.Add(leftCell);
            }
            List<Cell> minCells = new List<Cell>();
            List<Cell> minestCells = new List<Cell>();
            int min = int.MaxValue;
            int n = int.MaxValue;
            //在open集合中找出最小的f(n)值
            foreach (Cell cell in OpenList)
            {
                if (cell.FinalDistance <= min)
                {
                    min = cell.FinalDistance;
                }
            }
            //找出f(n)值最小的格子
            foreach (Cell openCell in this.OpenList)
            {
                if (openCell.FinalDistance == min)
                {
                    minCells.Add(openCell);
                }
            }
            //在f(n)值最小的格子集合中找出最小的g(n)值
            for (int i = 0; i < minCells.Count; i++)
            {
                if (minCells[i].EvaluateDistance <= n)
                {
                    n = minCells[i].EvaluateDistance;
                }
            }
            ///在f(n)值最小的格子集合中找出g(n)值最小的格子
            for (int i = 0; i < minCells.Count; i++)
            {
                if (minCells[i].EvaluateDistance == n)
                {
                    minestCells.Add(minCells[i]);
                }
            }
            Random rd = new Random();
            //在f(n)值和g(n)的格子集合中随机选取一个格子
            if (minestCells.Count == 0)
            {
                return false;
            }
            int j = rd.Next(minestCells.Count);
            this.ClosedList.Add(minestCells[j]);

            //this.ClosedList[ClosedList.Count - 1].CellColor = Color.Green;
            //MapBmpFillRectangle(this.ClosedList[ClosedList.Count - 1]);

            //从open集合中移除刚刚添加到closed集合中的格子
            for (int i = 0; i < OpenList.Count; i++)
            {
                if (Cell.IsSampleCell(OpenList[i], minestCells[j]))
                {
                    this.OpenList.Remove(OpenList[i]);
                    break;
                }
            }
            return true;
        }
        #endregion

        #region 开始搜索
        public bool BeginSearch()
        {
            if (Cell.IsSampleCell(this.StartCell, this.GoalCell))
            {
                return true;
            }

            this.StartCell.EvaluateDistance = this.H(StartCell);
            //将起始节点添加到closedList集合中
            this.ClosedList.Add(StartCell);

            //不停的搜索直到到达目的地
            while (true)
            {
                if (!this.SearchBestCell())
                {
                    MessageBox.Show("苦海无边，回头是岸！");
                    return false;
                }
                Cell closedCell = this.ClosedList[ClosedList.Count - 1];
                Cell _upCell = new Cell();
                Cell _downCell = new Cell();
                Cell _rightCell = new Cell();
                Cell _leftCell = new Cell();
                _upCell.Location = new Point(closedCell.Location.X, closedCell.Location.Y + CELL_WIDTH);
                _downCell.Location = new Point(closedCell.Location.X, closedCell.Location.Y - CELL_WIDTH);
                _rightCell.Location = new Point(closedCell.Location.X + CELL_WIDTH, closedCell.Location.Y);
                _leftCell.Location = new Point(closedCell.Location.X - CELL_WIDTH, closedCell.Location.Y);

                if (Cell.IsSampleCell(_upCell, StartCell))
                {
                    //StartCell.DownCell = closedCell;
                    closedCell.UpCell = StartCell;
                }
                else if (Cell.IsSampleCell(_downCell, StartCell))
                {
                    //StartCell.UpCell = closedCell;
                    closedCell.DownCell = StartCell;
                }
                else if (Cell.IsSampleCell(_rightCell, StartCell))
                {
                    //StartCell.LeftCell = closedCell;
                    closedCell.RightCell = StartCell;
                }
                else if (Cell.IsSampleCell(_leftCell, StartCell))
                {
                    //StartCell.RightCell = closedCell;
                    closedCell.LeftCell = StartCell;
                }

                if (Cell.IsSampleCell(_upCell, GoalCell))
                {
                    //closedCell.UpCell = GoalCell;
                    GoalCell.DownCell = closedCell;
                    break;
                }
                if (Cell.IsSampleCell(_downCell, GoalCell))
                {
                    //closedCell.DownCell = GoalCell;
                    GoalCell.UpCell = closedCell;
                    break;
                }
                if (Cell.IsSampleCell(_rightCell, GoalCell))
                {
                    //closedCell.RightCell = GoalCell;
                    GoalCell.LeftCell = closedCell;
                    break;
                }
                if (Cell.IsSampleCell(_leftCell, GoalCell))
                {
                    //closedCell.LeftCell = GoalCell;
                    GoalCell.RightCell = closedCell;
                    break;
                }
            }

            List<Cell> BestLoad = new List<Cell>();
            Cell lastClosedCell = new Cell();
            int n = int.MaxValue;
            for (int i = 0; i < this.ClosedList.Count; i++)
            {
                if (this.ClosedList[i].EvaluateDistance <= n)
                {
                    n = this.ClosedList[i].EvaluateDistance;
                }
            }
            for (int i = 0; i < this.ClosedList.Count; i++)
            {
                if (this.ClosedList[i].EvaluateDistance == n)
                {
                    lastClosedCell = this.ClosedList[i];
                    break;
                }
            }
            while (true)
            {
                if (Cell.IsSampleCell(lastClosedCell.ParentCell, StartCell))
                {
                    BestLoad.Add(lastClosedCell);
                    break;
                }
                else
                {
                    BestLoad.Add(lastClosedCell);
                    lastClosedCell = lastClosedCell.ParentCell;
                }
            }
            for (int i = 0; i < BestLoad.Count; i++)
            {
                if (i == 0)
                {
                    GoalCell.ParentCell = BestLoad[i];
                }
                else
                {
                    BestLoad[i - 1].ParentCell = BestLoad[i];
                }
                if (i < BestLoad.Count - 1)
                {
                    Cell _upCell = new Cell();
                    Cell _downCell = new Cell();
                    Cell _rightCell = new Cell();
                    Cell _leftCell = new Cell();
                    _upCell.Location = new Point(BestLoad[i].Location.X, BestLoad[i].Location.Y + CELL_WIDTH);
                    _downCell.Location = new Point(BestLoad[i].Location.X, BestLoad[i].Location.Y - CELL_WIDTH);
                    _rightCell.Location = new Point(BestLoad[i].Location.X + CELL_WIDTH, BestLoad[i].Location.Y);
                    _leftCell.Location = new Point(BestLoad[i].Location.X - CELL_WIDTH, BestLoad[i].Location.Y);

                    if (Cell.IsSampleCell(_upCell, BestLoad[i + 1]))
                    {
                        BestLoad[i].UpCell = BestLoad[i + 1];
                    }
                    else if (Cell.IsSampleCell(_downCell, BestLoad[i + 1]))
                    {
                        BestLoad[i].DownCell = BestLoad[i + 1];
                    }
                    else if (Cell.IsSampleCell(_rightCell, BestLoad[i + 1]))
                    {
                        BestLoad[i].RightCell = BestLoad[i + 1];
                    }
                    else if (Cell.IsSampleCell(_leftCell, BestLoad[i + 1]))
                    {
                        BestLoad[i].LeftCell = BestLoad[i + 1];
                    }
                }
                else
                {

                }
            }
            MapBmpFillRectangles(BestLoad, Color.Blue);
            
            SVGLocation = new List<Point>();
            SVGLocation.Add(GoalCell.Location);

            Cell activeCheckCell=GoalCell;
            do
            {

                if (activeCheckCell.LeftCell != null)
                {
                    if (activeCheckCell.ParentCell.LeftCell != null)
                    {
                        activeCheckCell = activeCheckCell.ParentCell;
                        continue;
                    }
                    else
                    {
                        activeCheckCell = activeCheckCell.ParentCell;
                        SVGLocation.Add(activeCheckCell.Location);
                        continue;
                    }
                }
                else if (activeCheckCell.RightCell != null)
                {
                    if (activeCheckCell.ParentCell.RightCell != null)
                    {
                        activeCheckCell = activeCheckCell.ParentCell;
                        continue;
                    }
                    else
                    {
                        activeCheckCell = activeCheckCell.ParentCell;
                        SVGLocation.Add(activeCheckCell.Location);
                        continue;
                    }
                }
                else if (activeCheckCell.UpCell != null)
                {
                    if (activeCheckCell.ParentCell.UpCell != null)
                    {
                        activeCheckCell = activeCheckCell.ParentCell;
                        continue;
                    }
                    else
                    {
                        activeCheckCell = activeCheckCell.ParentCell;
                        SVGLocation.Add(activeCheckCell.Location);
                        continue;
                    }
                }
                else if (activeCheckCell.DownCell != null)
                {
                    if (activeCheckCell.ParentCell.DownCell != null)
                    {
                        activeCheckCell = activeCheckCell.ParentCell;
                        continue;
                    }
                    else
                    {
                        activeCheckCell = activeCheckCell.ParentCell;
                        SVGLocation.Add(activeCheckCell.Location);
                        continue;
                    }
                }
            } while (activeCheckCell != null && activeCheckCell.ParentCell != null);

            //for (int i = SVGLocation.Count - 1; i >= 0; i--)
            //{
            //    Graphics g = Graphics.FromImage(GIDTempBmp);
            //    g.DrawString((SVGLocation.Count - i).ToString(), new Font("宋体", 10), new SolidBrush(Color.Red), SVGLocation[i]);
            //}
            return true;
        }
        #endregion
        public void MapControlDraw()
        {
            if (MapControl != null)
            {
                this.MapControl.Invoke(new EventHandler(delegate
                {
                    this.MapControl.CreateGraphics().DrawImage(GIDTempBmp, 0, 0);
                }));
            }
        }
    }
}
