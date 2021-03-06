﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Newtonsoft.Json;
using Svg;

namespace WindowsFormsApplication1
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            comboBox1.SelectedIndex = comboBox1.Items.Count - 1;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            /*
            L 最低
            M 低
            Q 中等
            H 高
            */
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
                    string elStr = comboBox1.SelectedItem.ToString().Split(' ')[0];
                    foreach (QRCodeUrlInfo qRCodeUrlInfo in qRCodeUrlInfos)
                    {
                        WebClient wc = new WebClient();
                        string xxx = System.Web.HttpUtility.UrlEncode(qRCodeUrlInfo.Url, Encoding.UTF8);
                        string url = "http://www.liantu.com/savevector.php?text=" + xxx + "&el=" + elStr +
                                     "&m=15&vt=svg";
                        string svgFileFullName = fileDialog.SelectedPath + "\\" + qRCodeUrlInfo.Name + ".svg";
                        string svgTempFileFullName = fileDialog.SelectedPath + "\\" + qRCodeUrlInfo.Name + ".Temp.svg";
                        wc.DownloadFile(url, svgTempFileFullName);

                        SvgDocument svgDoc = SvgDocument.Open(svgTempFileFullName);
                        SvgUnit minX = SvgUnit.None;
                        SvgUnit minY = SvgUnit.None;
                        SvgUnit maxX = SvgUnit.None;
                        SvgUnit maxY = SvgUnit.None;
                        SvgUnit recWidth = SvgUnit.None;

                        #region 获取真实边框大小
                        foreach (SvgElement element in svgDoc.Children)
                        {

                            if (element is SvgDefinitionList)
                            {
                                if (element.Children.Count > 0)
                                {
                                    if (element.Children[0] is SvgRectangle)
                                    {
                                        SvgRectangle svgRectangle = element.Children[0] as SvgRectangle;
                                        recWidth = svgRectangle.Width;
                                    }
                                }
                                SvgDefinitionList svgDefinitionList = element as SvgDefinitionList;
                            }
                            if (element is SvgGroup)
                            {
                                if (element.Children.Count > 0)
                                {
                                    if (element.Children[0] is SvgUse)
                                    {
                                        SvgUse svgUse = element.Children[0] as SvgUse;

                                        if (minX.IsNone || minY.IsNone)
                                        {
                                            minX = svgUse.X;
                                            minY = svgUse.Y;
                                        }
                                        else if (minX.Value > svgUse.X)
                                        {
                                            minX = svgUse.X;
                                        }
                                        else if (minY.Value > svgUse.Y)
                                        {
                                            minY = svgUse.Y;
                                        }
                                    }


                                    if (element.Children[element.Children.Count-1] is SvgUse)
                                    {
                                        SvgUse svgUse = element.Children[element.Children.Count - 1] as SvgUse;
                                        if (maxX.IsNone || maxY.IsNone)
                                        {
                                            maxX = svgUse.X;
                                            maxY = svgUse.Y;
                                        }
                                        else if (maxX.Value < svgUse.X)
                                        {
                                            maxX = svgUse.X;
                                        }
                                        else if (maxY.Value < svgUse.Y)
                                        {
                                            maxY = svgUse.Y;
                                        }
                                    }
                                }
                            }
                        }
                        maxX.Value = maxX.Value - minX.Value + recWidth.Value;
                        maxY.Value = maxY.Value - minY.Value + recWidth.Value;

                        SvgUnitType svgUnitType = minX.Type;
                        #endregion

                        int rowNum = Convert.ToInt32(Math.Floor(maxX.Value / recWidth.Value));

                        
                        #region 进行位置计算
                        foreach (SvgElement element in svgDoc.Children)
                        {
                            if (element is SvgRectangle)
                            {
                                SvgRectangle svgRectangle = element as SvgRectangle;
                                svgRectangle.Width = maxX;
                                svgRectangle.Height = maxY;
                            }

                            if (element is SvgGroup)
                            {
                                foreach (SvgElement element2 in element.Children)
                                {
                                    SvgUse svgUse = element2 as SvgUse;
                                    if (svgUse != null)
                                    {
                                        svgUse.X = new SvgUnit(svgUnitType, svgUse.X.Value - minX.Value);
                                        svgUse.Y = new SvgUnit(svgUnitType, svgUse.Y.Value - minY.Value);
                                    }
                                }
                            }
                        }
                        #endregion
                        

                        for (int i = 0; i < svgDoc.Children.Count; i++)
                        {
                            if (svgDoc.Children[i] is SvgRectangle)
                            {
                                SvgRectangle svgRectangle = svgDoc.Children[i] as SvgRectangle;
                                svgRectangle.Width = new SvgUnit(svgUnitType, maxX.Value - recWidth.Value - recWidth.Value); 
                                svgRectangle.Height = new SvgUnit(svgUnitType, maxY.Value - recWidth.Value - recWidth.Value); 
                            }

                            if (svgDoc.Children[i] is SvgGroup)
                            {
                                SvgElement element = svgDoc.Children[i] as SvgElement;
                                string attribute = string.Empty;
                                element.TryGetAttribute("fill", out attribute);

                                for (int j = element.Children.Count; j > 0; j--)
                                {

                                    SvgUse svgUse = element.Children[j - 1] as SvgUse;

                                    if (((int)svgUse.Y.Value ) + ((int)recWidth.Value) == ((int)maxY.Value)// 最后一行
                                        || ((int)svgUse.Y.Value) == 0          // 第一行
                                        || ((int)svgUse.X.Value) + ((int)recWidth.Value) == ((int)maxX.Value) // 最后一列
                                        || ((int)svgUse.X.Value) == 0)         // 第一列
                                    {
                                        // 最后一行
                                        element.Children.RemoveAt(j - 1);
                                    }
                                    else
                                    {
                                        if (svgUse != null)
                                        {
                                            svgUse.X = new SvgUnit(svgUnitType, svgUse.X.Value - recWidth.Value);
                                            svgUse.Y = new SvgUnit(svgUnitType, svgUse.Y.Value - recWidth.Value);
                                        }
                                    }
                                }
                            }
                        }
                        svgDoc.Width = new SvgUnit(svgUnitType, maxX.Value - recWidth.Value - recWidth.Value);
                        svgDoc.Height = new SvgUnit(svgUnitType, maxY.Value - recWidth.Value - recWidth.Value);
                        svgDoc.ViewBox =new SvgViewBox(0,0, svgDoc.Width.Value, svgDoc.Height.Value);

                        var svgXml = svgDoc.GetXML();
                        File.WriteAllText(svgFileFullName, svgXml);
                        File.Delete(svgTempFileFullName);
                    }
                    MessageBox.Show(@"导出完成");
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
