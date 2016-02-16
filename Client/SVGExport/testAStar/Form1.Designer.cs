namespace testAStar
{
    partial class Form1
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.panelMap = new System.Windows.Forms.Panel();
            this.btnCtStoneRodom = new System.Windows.Forms.Button();
            this.btnCtStoneHand = new System.Windows.Forms.Button();
            this.btnStartSearch = new System.Windows.Forms.Button();
            this.btnRest = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.SvgImportButton = new System.Windows.Forms.Button();
            this.openSvgFile = new System.Windows.Forms.OpenFileDialog();
            this.panel1 = new System.Windows.Forms.Panel();
            this.CellWidthNumericUpDown = new System.Windows.Forms.NumericUpDown();
            this.SaveSvgButton = new System.Windows.Forms.Button();
            this.SaveMapInfoButton = new System.Windows.Forms.Button();
            this.UseSavedMapInfoButton = new System.Windows.Forms.Button();
            this.label6 = new System.Windows.Forms.Label();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.CellWidthNumericUpDown)).BeginInit();
            this.SuspendLayout();
            // 
            // panelMap
            // 
            this.panelMap.BackColor = System.Drawing.SystemColors.Control;
            this.panelMap.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.panelMap.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.panelMap.Location = new System.Drawing.Point(0, 0);
            this.panelMap.Margin = new System.Windows.Forms.Padding(4);
            this.panelMap.Name = "panelMap";
            this.panelMap.Size = new System.Drawing.Size(453, 424);
            this.panelMap.TabIndex = 0;
            this.panelMap.MouseDown += new System.Windows.Forms.MouseEventHandler(this.panelMap_MouseDown);
            // 
            // btnCtStoneRodom
            // 
            this.btnCtStoneRodom.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnCtStoneRodom.Location = new System.Drawing.Point(642, 15);
            this.btnCtStoneRodom.Margin = new System.Windows.Forms.Padding(4);
            this.btnCtStoneRodom.Name = "btnCtStoneRodom";
            this.btnCtStoneRodom.Size = new System.Drawing.Size(127, 29);
            this.btnCtStoneRodom.TabIndex = 1;
            this.btnCtStoneRodom.Text = "随机生成障碍物";
            this.btnCtStoneRodom.UseVisualStyleBackColor = true;
            this.btnCtStoneRodom.Click += new System.EventHandler(this.btnCreateStone_Click);
            // 
            // btnCtStoneHand
            // 
            this.btnCtStoneHand.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnCtStoneHand.Location = new System.Drawing.Point(642, 51);
            this.btnCtStoneHand.Margin = new System.Windows.Forms.Padding(4);
            this.btnCtStoneHand.Name = "btnCtStoneHand";
            this.btnCtStoneHand.Size = new System.Drawing.Size(127, 29);
            this.btnCtStoneHand.TabIndex = 2;
            this.btnCtStoneHand.Text = "手动生成障碍";
            this.btnCtStoneHand.UseVisualStyleBackColor = true;
            this.btnCtStoneHand.Click += new System.EventHandler(this.btnCtStoneHand_Click);
            // 
            // btnStartSearch
            // 
            this.btnStartSearch.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnStartSearch.Location = new System.Drawing.Point(642, 88);
            this.btnStartSearch.Margin = new System.Windows.Forms.Padding(4);
            this.btnStartSearch.Name = "btnStartSearch";
            this.btnStartSearch.Size = new System.Drawing.Size(127, 29);
            this.btnStartSearch.TabIndex = 4;
            this.btnStartSearch.Text = "开始寻路";
            this.btnStartSearch.UseVisualStyleBackColor = true;
            this.btnStartSearch.Click += new System.EventHandler(this.btnStartSearch_Click);
            // 
            // btnRest
            // 
            this.btnRest.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnRest.Location = new System.Drawing.Point(642, 124);
            this.btnRest.Margin = new System.Windows.Forms.Padding(4);
            this.btnRest.Name = "btnRest";
            this.btnRest.Size = new System.Drawing.Size(127, 29);
            this.btnRest.TabIndex = 5;
            this.btnRest.Text = "重置";
            this.btnRest.UseVisualStyleBackColor = true;
            this.btnRest.Click += new System.EventHandler(this.btnRest_Click);
            // 
            // label1
            // 
            this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label1.AutoSize = true;
            this.label1.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.label1.Location = new System.Drawing.Point(640, 156);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(112, 15);
            this.label1.TabIndex = 6;
            this.label1.Text = "红色代表障碍物";
            // 
            // label2
            // 
            this.label2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label2.AutoSize = true;
            this.label2.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.label2.Location = new System.Drawing.Point(640, 182);
            this.label2.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(112, 15);
            this.label2.TabIndex = 7;
            this.label2.Text = "紫色代表起始点";
            // 
            // label3
            // 
            this.label3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label3.AutoSize = true;
            this.label3.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.label3.Location = new System.Drawing.Point(640, 209);
            this.label3.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(112, 15);
            this.label3.TabIndex = 8;
            this.label3.Text = "黄色代表目的地";
            // 
            // label4
            // 
            this.label4.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label4.AutoSize = true;
            this.label4.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.label4.Location = new System.Drawing.Point(640, 264);
            this.label4.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(127, 15);
            this.label4.TabIndex = 9;
            this.label4.Text = "蓝色代表最佳路径";
            // 
            // label5
            // 
            this.label5.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label5.AutoSize = true;
            this.label5.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.label5.Location = new System.Drawing.Point(640, 238);
            this.label5.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(142, 15);
            this.label5.TabIndex = 10;
            this.label5.Text = "绿色代表寻路经过点";
            // 
            // SvgImportButton
            // 
            this.SvgImportButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.SvgImportButton.Location = new System.Drawing.Point(642, 300);
            this.SvgImportButton.Margin = new System.Windows.Forms.Padding(4);
            this.SvgImportButton.Name = "SvgImportButton";
            this.SvgImportButton.Size = new System.Drawing.Size(127, 29);
            this.SvgImportButton.TabIndex = 11;
            this.SvgImportButton.Text = "载入SVG";
            this.SvgImportButton.UseVisualStyleBackColor = true;
            this.SvgImportButton.Click += new System.EventHandler(this.SvgImportButton_Click);
            // 
            // openSvgFile
            // 
            this.openSvgFile.FileName = "openFileDialog1";
            // 
            // panel1
            // 
            this.panel1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.panel1.AutoScroll = true;
            this.panel1.BackColor = System.Drawing.SystemColors.ActiveBorder;
            this.panel1.Controls.Add(this.panelMap);
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Margin = new System.Windows.Forms.Padding(4);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(622, 555);
            this.panel1.TabIndex = 0;
            // 
            // CellWidthNumericUpDown
            // 
            this.CellWidthNumericUpDown.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.CellWidthNumericUpDown.Location = new System.Drawing.Point(716, 336);
            this.CellWidthNumericUpDown.Margin = new System.Windows.Forms.Padding(4);
            this.CellWidthNumericUpDown.Minimum = new decimal(new int[] {
            2,
            0,
            0,
            0});
            this.CellWidthNumericUpDown.Name = "CellWidthNumericUpDown";
            this.CellWidthNumericUpDown.Size = new System.Drawing.Size(86, 25);
            this.CellWidthNumericUpDown.TabIndex = 12;
            this.CellWidthNumericUpDown.Value = new decimal(new int[] {
            2,
            0,
            0,
            0});
            this.CellWidthNumericUpDown.ValueChanged += new System.EventHandler(this.CellWidthNumericUpDown_ValueChanged);
            // 
            // SaveSvgButton
            // 
            this.SaveSvgButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.SaveSvgButton.Location = new System.Drawing.Point(642, 370);
            this.SaveSvgButton.Margin = new System.Windows.Forms.Padding(4);
            this.SaveSvgButton.Name = "SaveSvgButton";
            this.SaveSvgButton.Size = new System.Drawing.Size(127, 29);
            this.SaveSvgButton.TabIndex = 13;
            this.SaveSvgButton.Text = "保存SVG";
            this.SaveSvgButton.UseVisualStyleBackColor = true;
            this.SaveSvgButton.Click += new System.EventHandler(this.SaveSvgButton_Click);
            // 
            // SaveMapInfoButton
            // 
            this.SaveMapInfoButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.SaveMapInfoButton.Location = new System.Drawing.Point(642, 407);
            this.SaveMapInfoButton.Margin = new System.Windows.Forms.Padding(4);
            this.SaveMapInfoButton.Name = "SaveMapInfoButton";
            this.SaveMapInfoButton.Size = new System.Drawing.Size(127, 29);
            this.SaveMapInfoButton.TabIndex = 15;
            this.SaveMapInfoButton.Text = "保存地图信息";
            this.SaveMapInfoButton.UseVisualStyleBackColor = true;
            this.SaveMapInfoButton.Click += new System.EventHandler(this.SaveMapInfoButton_Click);
            // 
            // UseSavedMapInfoButton
            // 
            this.UseSavedMapInfoButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.UseSavedMapInfoButton.Location = new System.Drawing.Point(643, 444);
            this.UseSavedMapInfoButton.Margin = new System.Windows.Forms.Padding(4);
            this.UseSavedMapInfoButton.Name = "UseSavedMapInfoButton";
            this.UseSavedMapInfoButton.Size = new System.Drawing.Size(127, 29);
            this.UseSavedMapInfoButton.TabIndex = 16;
            this.UseSavedMapInfoButton.Text = "使用缓存信息";
            this.UseSavedMapInfoButton.UseVisualStyleBackColor = true;
            this.UseSavedMapInfoButton.Click += new System.EventHandler(this.UseSavedMapInfoButton_Click);
            // 
            // label6
            // 
            this.label6.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label6.AutoSize = true;
            this.label6.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.label6.Location = new System.Drawing.Point(640, 338);
            this.label6.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(37, 15);
            this.label6.TabIndex = 17;
            this.label6.Text = "精度";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(829, 552);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.UseSavedMapInfoButton);
            this.Controls.Add(this.SaveMapInfoButton);
            this.Controls.Add(this.SaveSvgButton);
            this.Controls.Add(this.CellWidthNumericUpDown);
            this.Controls.Add(this.SvgImportButton);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.btnRest);
            this.Controls.Add(this.btnStartSearch);
            this.Controls.Add(this.btnCtStoneHand);
            this.Controls.Add(this.btnCtStoneRodom);
            this.Controls.Add(this.panel1);
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "Form1";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Form1";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Form1_FormClosing);
            this.Load += new System.EventHandler(this.Form1_Load);
            this.panel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.CellWidthNumericUpDown)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Panel panelMap;
        private System.Windows.Forms.Button btnCtStoneRodom;
        private System.Windows.Forms.Button btnCtStoneHand;
        private System.Windows.Forms.Button btnStartSearch;
        private System.Windows.Forms.Button btnRest;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Button SvgImportButton;
        private System.Windows.Forms.OpenFileDialog openSvgFile;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.NumericUpDown CellWidthNumericUpDown;
        private System.Windows.Forms.Button SaveSvgButton;
        private System.Windows.Forms.Button SaveMapInfoButton;
        private System.Windows.Forms.Button UseSavedMapInfoButton;
        private System.Windows.Forms.Label label6;
    }
}

