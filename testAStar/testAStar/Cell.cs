﻿using System.Drawing;
using System.Runtime.Serialization;

namespace testAStar
{
    [DataContract]
    public class Cell
    {
        public Cell()
        {
            this.Name = string.Empty;
        }
        public Cell(string name = "")
        {
            if(!string.IsNullOrEmpty(name))
                this.Name = name;
        }
        public Cell(Point location,string name= "")
        {
            this.Location = location;
            if (!string.IsNullOrEmpty(name))
                this.Name = name;
        }
        public Cell(int x, int y, string name = "")
        {
            this.Location = new Point(x, y);
            if (!string.IsNullOrEmpty(name))
                this.Name = name;
        }
        [DataMember (Name ="n")]
        public string Name { set; get; }
        /// <summary>
        /// 位置
        /// </summary>
        [DataMember(Name = "p")]
        public Point Location { get; set; }

        [DataMember(Name = "r")]
        public string Remark { set; get; }

        /// <summary>
        /// 起始停车区域尾缀 (;分割)
        /// </summary>
        [DataMember(Name = "f")]
        public string From { set; get; }

        /// <summary>
        /// 导出模式 in:出入口到停车区域 out:停车区域到出入口 其它:全部导出
        /// </summary>
        [DataMember(Name = "export_type")]
        public string ExportType { set; get; }

        /// <summary>
        /// 编号
        /// </summary>
        //[DataMember]
        //public int Id { get; set; }

        /// <summary>
        /// 到起始点的实际代价
        /// </summary>
        [IgnoreDataMember]
        public float RealDistance { get; set; }

        /// <summary>
        /// 估计代价
        /// </summary>
        [IgnoreDataMember]
        public float EvaluateDistance { get; set; }

        /// <summary>
        ///  最终代价
        /// </summary>
        public float FinalDistance { get; set; }
        /// <summary>
        /// 父亲节点
        /// </summary>
        [IgnoreDataMember]
        public Cell ParentCell { get; set; }

        /// <summary>
        /// 上方格子
        /// </summary>
        [IgnoreDataMember]
        public Cell UpCell { get; set; }

        /// <summary>
        /// 下方格子
        /// </summary>
        [IgnoreDataMember]
        public Cell DownCell { get; set; }

        /// <summary>
        /// 右边格子
        /// </summary>
        [IgnoreDataMember]
        public Cell RightCell { get; set; }


        /// <summary>
        /// 下方格子
        /// </summary>
        [IgnoreDataMember]
        public Cell LeftCell { get; set; }

        /// <summary>
        /// 格子颜色
        /// </summary>
        [IgnoreDataMember]
        public Color CellColor { get; set; }

        [IgnoreDataMember]
        public CellToward Toward { set; get; }

        [DataMember(Name = "toward")]
        public string TowardStr
        {
            set
            {
                if (string.IsNullOrEmpty(value))
                {
                    Toward = CellToward.None;
                }
                else if ("u" == value)
                {
                    Toward = CellToward.Up;
                }
                else if ("d" == value)
                {
                    Toward = CellToward.Down;
                }
                else if ("l" == value)
                {
                    Toward = CellToward.Left;
                }
                else if ("r" == value)
                {
                    Toward = CellToward.Right;
                }
                else if ("ld" == value)
                {
                    Toward = CellToward.LeftDown;
                }
                else if ("lu" == value)
                {
                    Toward = CellToward.LeftUp;
                }
                else if ("rd" == value)
                {
                    Toward = CellToward.RightDown;
                }
                else if ("ru" == value)
                {
                    Toward = CellToward.RightUp;
                }
            }
            get
            {
                switch(Toward)
                {
                    case CellToward.None:
                        return null;
                    case CellToward.Up:
                        return "u";
                    case CellToward.Down:
                        return "d";
                    case CellToward.Left:
                        return "l";
                    case CellToward.Right:
                        return "r";
                    case CellToward.LeftDown:
                        return "ld";
                    case CellToward.LeftUp:
                        return "lu";
                    case CellToward.RightDown:
                        return "rd";
                    case CellToward.RightUp:
                        return "ru";
                    default:
                        return null;
                }
            }
        }

        /// <summary>
        /// 判断是否有Cell
        /// </summary>
        /// <param name="cell">一个格子</param>
        /// <param name="pt">要比较的坐标点</param>
        /// <returns>true或false</returns>
        public static bool HasCell(Cell cell, Point pt)
        {
            if (cell.Location.Equals( pt))
            {
                return true;
            }
            else
                return false;
        }
        /// <summary>
        ///  判断两个格子是否是相同的
        /// </summary>
        /// <param name="cell1">格子1</param>
        /// <param name="cell2">格子2</param>
        /// <returns>true或false</returns>
        public static bool IsSampleCell(Cell cell1, Cell cell2)
        {
            if (cell1.Location.Equals(cell2.Location))
            {
                return true;
            }
            else
                return false;
        }
        public override string ToString()
        {
            return string.Format("{0} Location: X={1} Y={2} {3} "
                , string.IsNullOrEmpty(this.Name) ? string.Empty : "Name:"+this.Name, this.Location.X, this.Location.Y
                , this.ParentCell == null ? string.Empty : " Parent:{" + this.ParentCell + "}");
        }
    }

    public enum CellToward
    {
        None = 0,
        Up = 1,
        Down = 2,
        Left = 3,
        Right = 4,
        LeftUp = 5,
        LeftDown = 6,
        RightDown = 7,
        RightUp = 8
    }
}
