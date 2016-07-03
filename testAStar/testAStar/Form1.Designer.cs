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
            this.SvgImportButton = new System.Windows.Forms.Button();
            this.openSvgFile = new System.Windows.Forms.OpenFileDialog();
            this.panel1 = new System.Windows.Forms.Panel();
            this.pictureBoxMap = new System.Windows.Forms.PictureBox();
            this.CellWidthNumericUpDown = new System.Windows.Forms.NumericUpDown();
            this.SaveButton = new System.Windows.Forms.Button();
            this.SaveMapInfoButton = new System.Windows.Forms.Button();
            this.UseSavedMapInfoButton = new System.Windows.Forms.Button();
            this.label6 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.rotateNumericUpDown = new System.Windows.Forms.NumericUpDown();
            this.buttonName = new System.Windows.Forms.Button();
            this.textBoxName = new System.Windows.Forms.TextBox();
            this.label8 = new System.Windows.Forms.Label();
            this.buttonChangeName = new System.Windows.Forms.Button();
            this.button1 = new System.Windows.Forms.Button();
            this.checkBoxIsAll = new System.Windows.Forms.CheckBox();
            this.buttonSetGoalCells = new System.Windows.Forms.Button();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.buttonAddName = new System.Windows.Forms.Button();
            this.textBoxGoals = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.label9 = new System.Windows.Forms.Label();
            this.numericUpDown1 = new System.Windows.Forms.NumericUpDown();
            this.label11 = new System.Windows.Forms.Label();
            this.label13 = new System.Windows.Forms.Label();
            this.button2 = new System.Windows.Forms.Button();
            this.numericUpDown3 = new System.Windows.Forms.NumericUpDown();
            this.label14 = new System.Windows.Forms.Label();
            this.textBoxMapName = new System.Windows.Forms.TextBox();
            this.button3 = new System.Windows.Forms.Button();
            this.button4 = new System.Windows.Forms.Button();
            this.button5 = new System.Windows.Forms.Button();
            this.panel2 = new System.Windows.Forms.Panel();
            this.label10 = new System.Windows.Forms.Label();
            this.label12 = new System.Windows.Forms.Label();
            this.numericUpDown2 = new System.Windows.Forms.NumericUpDown();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.radioButton5 = new System.Windows.Forms.RadioButton();
            this.radioButton6 = new System.Windows.Forms.RadioButton();
            this.radioButton7 = new System.Windows.Forms.RadioButton();
            this.radioButton8 = new System.Windows.Forms.RadioButton();
            this.radioButton3 = new System.Windows.Forms.RadioButton();
            this.radioButton4 = new System.Windows.Forms.RadioButton();
            this.radioButton2 = new System.Windows.Forms.RadioButton();
            this.radioButton1 = new System.Windows.Forms.RadioButton();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBoxMap)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.CellWidthNumericUpDown)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.rotateNumericUpDown)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown3)).BeginInit();
            this.panel2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown2)).BeginInit();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // panelMap
            // 
            this.panelMap.BackColor = System.Drawing.SystemColors.Control;
            this.panelMap.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.panelMap.Location = new System.Drawing.Point(0, 0);
            this.panelMap.Name = "panelMap";
            this.panelMap.Size = new System.Drawing.Size(340, 340);
            this.panelMap.TabIndex = 0;
            this.panelMap.MouseDown += new System.Windows.Forms.MouseEventHandler(this.panelMap_MouseDown);
            this.panelMap.MouseMove += new System.Windows.Forms.MouseEventHandler(this.panelMap_MouseMove);
            this.panelMap.MouseUp += new System.Windows.Forms.MouseEventHandler(this.panelMap_MouseUp);
            // 
            // btnCtStoneRodom
            // 
            this.btnCtStoneRodom.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnCtStoneRodom.Enabled = false;
            this.btnCtStoneRodom.Location = new System.Drawing.Point(738, 12);
            this.btnCtStoneRodom.Name = "btnCtStoneRodom";
            this.btnCtStoneRodom.Size = new System.Drawing.Size(95, 23);
            this.btnCtStoneRodom.TabIndex = 1;
            this.btnCtStoneRodom.Text = "随机生成障碍物";
            this.btnCtStoneRodom.UseVisualStyleBackColor = true;
            this.btnCtStoneRodom.Click += new System.EventHandler(this.btnCreateStone_Click);
            // 
            // btnCtStoneHand
            // 
            this.btnCtStoneHand.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnCtStoneHand.Location = new System.Drawing.Point(862, 12);
            this.btnCtStoneHand.Name = "btnCtStoneHand";
            this.btnCtStoneHand.Size = new System.Drawing.Size(95, 23);
            this.btnCtStoneHand.TabIndex = 2;
            this.btnCtStoneHand.Text = "手动生成障碍";
            this.btnCtStoneHand.UseVisualStyleBackColor = true;
            this.btnCtStoneHand.Click += new System.EventHandler(this.btnCtStoneHand_Click);
            // 
            // btnStartSearch
            // 
            this.btnStartSearch.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnStartSearch.Location = new System.Drawing.Point(738, 41);
            this.btnStartSearch.Name = "btnStartSearch";
            this.btnStartSearch.Size = new System.Drawing.Size(95, 23);
            this.btnStartSearch.TabIndex = 4;
            this.btnStartSearch.Text = "开始寻路";
            this.btnStartSearch.UseVisualStyleBackColor = true;
            this.btnStartSearch.Click += new System.EventHandler(this.btnStartSearch_Click);
            // 
            // btnRest
            // 
            this.btnRest.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnRest.Location = new System.Drawing.Point(862, 41);
            this.btnRest.Name = "btnRest";
            this.btnRest.Size = new System.Drawing.Size(95, 23);
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
            this.label1.Location = new System.Drawing.Point(744, 78);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(89, 12);
            this.label1.TabIndex = 6;
            this.label1.Text = "红色代表障碍物";
            // 
            // label2
            // 
            this.label2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label2.AutoSize = true;
            this.label2.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.label2.Location = new System.Drawing.Point(860, 78);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(89, 12);
            this.label2.TabIndex = 7;
            this.label2.Text = "紫色代表起始点";
            // 
            // label3
            // 
            this.label3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label3.AutoSize = true;
            this.label3.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.label3.Location = new System.Drawing.Point(744, 107);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(89, 12);
            this.label3.TabIndex = 8;
            this.label3.Text = "黄色代表目的地";
            // 
            // label4
            // 
            this.label4.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label4.AutoSize = true;
            this.label4.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.label4.Location = new System.Drawing.Point(860, 107);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(101, 12);
            this.label4.TabIndex = 9;
            this.label4.Text = "蓝色代表最佳路径";
            // 
            // SvgImportButton
            // 
            this.SvgImportButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.SvgImportButton.Location = new System.Drawing.Point(738, 135);
            this.SvgImportButton.Name = "SvgImportButton";
            this.SvgImportButton.Size = new System.Drawing.Size(95, 23);
            this.SvgImportButton.TabIndex = 11;
            this.SvgImportButton.Text = "载入";
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
            this.panel1.Controls.Add(this.pictureBoxMap);
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(603, 750);
            this.panel1.TabIndex = 0;
            // 
            // pictureBoxMap
            // 
            this.pictureBoxMap.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.pictureBoxMap.Location = new System.Drawing.Point(0, 0);
            this.pictureBoxMap.Name = "pictureBoxMap";
            this.pictureBoxMap.Size = new System.Drawing.Size(100, 50);
            this.pictureBoxMap.TabIndex = 0;
            this.pictureBoxMap.TabStop = false;
            // 
            // CellWidthNumericUpDown
            // 
            this.CellWidthNumericUpDown.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.CellWidthNumericUpDown.Location = new System.Drawing.Point(897, 138);
            this.CellWidthNumericUpDown.Minimum = new decimal(new int[] {
            2,
            0,
            0,
            0});
            this.CellWidthNumericUpDown.Name = "CellWidthNumericUpDown";
            this.CellWidthNumericUpDown.Size = new System.Drawing.Size(60, 21);
            this.CellWidthNumericUpDown.TabIndex = 12;
            this.CellWidthNumericUpDown.Value = new decimal(new int[] {
            2,
            0,
            0,
            0});
            this.CellWidthNumericUpDown.ValueChanged += new System.EventHandler(this.CellWidthNumericUpDown_ValueChanged);
            // 
            // SaveButton
            // 
            this.SaveButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.SaveButton.Enabled = false;
            this.SaveButton.Location = new System.Drawing.Point(738, 164);
            this.SaveButton.Name = "SaveButton";
            this.SaveButton.Size = new System.Drawing.Size(95, 23);
            this.SaveButton.TabIndex = 13;
            this.SaveButton.Text = "保存";
            this.SaveButton.UseVisualStyleBackColor = true;
            this.SaveButton.Click += new System.EventHandler(this.SaveSvgButton_Click);
            // 
            // SaveMapInfoButton
            // 
            this.SaveMapInfoButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.SaveMapInfoButton.Location = new System.Drawing.Point(738, 193);
            this.SaveMapInfoButton.Name = "SaveMapInfoButton";
            this.SaveMapInfoButton.Size = new System.Drawing.Size(95, 23);
            this.SaveMapInfoButton.TabIndex = 15;
            this.SaveMapInfoButton.Text = "保存地图信息";
            this.SaveMapInfoButton.UseVisualStyleBackColor = true;
            this.SaveMapInfoButton.Click += new System.EventHandler(this.SaveMapInfoButton_Click);
            // 
            // UseSavedMapInfoButton
            // 
            this.UseSavedMapInfoButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.UseSavedMapInfoButton.Location = new System.Drawing.Point(862, 194);
            this.UseSavedMapInfoButton.Name = "UseSavedMapInfoButton";
            this.UseSavedMapInfoButton.Size = new System.Drawing.Size(95, 23);
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
            this.label6.Location = new System.Drawing.Point(860, 140);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(29, 12);
            this.label6.TabIndex = 17;
            this.label6.Text = "精度";
            // 
            // label7
            // 
            this.label7.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label7.AutoSize = true;
            this.label7.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.label7.Location = new System.Drawing.Point(860, 169);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(29, 12);
            this.label7.TabIndex = 19;
            this.label7.Text = "旋转";
            // 
            // rotateNumericUpDown
            // 
            this.rotateNumericUpDown.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.rotateNumericUpDown.Enabled = false;
            this.rotateNumericUpDown.Location = new System.Drawing.Point(897, 167);
            this.rotateNumericUpDown.Maximum = new decimal(new int[] {
            180,
            0,
            0,
            0});
            this.rotateNumericUpDown.Minimum = new decimal(new int[] {
            180,
            0,
            0,
            -2147483648});
            this.rotateNumericUpDown.Name = "rotateNumericUpDown";
            this.rotateNumericUpDown.Size = new System.Drawing.Size(60, 21);
            this.rotateNumericUpDown.TabIndex = 18;
            this.rotateNumericUpDown.ValueChanged += new System.EventHandler(this.rotateNumericUpDown_ValueChanged);
            // 
            // buttonName
            // 
            this.buttonName.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonName.Location = new System.Drawing.Point(738, 222);
            this.buttonName.Name = "buttonName";
            this.buttonName.Size = new System.Drawing.Size(95, 23);
            this.buttonName.TabIndex = 20;
            this.buttonName.Text = "停车区域起名";
            this.buttonName.UseVisualStyleBackColor = true;
            this.buttonName.Click += new System.EventHandler(this.buttonName_Click);
            // 
            // textBoxName
            // 
            this.textBoxName.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.textBoxName.Location = new System.Drawing.Point(789, 251);
            this.textBoxName.Name = "textBoxName";
            this.textBoxName.Size = new System.Drawing.Size(168, 21);
            this.textBoxName.TabIndex = 21;
            // 
            // label8
            // 
            this.label8.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label8.AutoSize = true;
            this.label8.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.label8.Location = new System.Drawing.Point(744, 254);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(29, 12);
            this.label8.TabIndex = 22;
            this.label8.Text = "前缀";
            // 
            // buttonChangeName
            // 
            this.buttonChangeName.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonChangeName.Location = new System.Drawing.Point(862, 223);
            this.buttonChangeName.Name = "buttonChangeName";
            this.buttonChangeName.Size = new System.Drawing.Size(95, 23);
            this.buttonChangeName.TabIndex = 25;
            this.buttonChangeName.Text = "改名";
            this.buttonChangeName.UseVisualStyleBackColor = true;
            this.buttonChangeName.Click += new System.EventHandler(this.buttonChangeName_Click);
            // 
            // button1
            // 
            this.button1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.button1.Location = new System.Drawing.Point(738, 552);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(95, 23);
            this.button1.TabIndex = 26;
            this.button1.Text = "批量生成";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // checkBoxIsAll
            // 
            this.checkBoxIsAll.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.checkBoxIsAll.AutoSize = true;
            this.checkBoxIsAll.Enabled = false;
            this.checkBoxIsAll.Location = new System.Drawing.Point(885, 559);
            this.checkBoxIsAll.Name = "checkBoxIsAll";
            this.checkBoxIsAll.Size = new System.Drawing.Size(72, 16);
            this.checkBoxIsAll.TabIndex = 27;
            this.checkBoxIsAll.Text = "外部地图";
            this.checkBoxIsAll.UseVisualStyleBackColor = true;
            // 
            // buttonSetGoalCells
            // 
            this.buttonSetGoalCells.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonSetGoalCells.Location = new System.Drawing.Point(738, 413);
            this.buttonSetGoalCells.Name = "buttonSetGoalCells";
            this.buttonSetGoalCells.Size = new System.Drawing.Size(95, 23);
            this.buttonSetGoalCells.TabIndex = 28;
            this.buttonSetGoalCells.Text = "设置批量终点";
            this.buttonSetGoalCells.UseVisualStyleBackColor = true;
            this.buttonSetGoalCells.Click += new System.EventHandler(this.buttonSetGoalCells_Click);
            // 
            // textBox1
            // 
            this.textBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.textBox1.Enabled = false;
            this.textBox1.Location = new System.Drawing.Point(815, 278);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(142, 21);
            this.textBox1.TabIndex = 29;
            // 
            // buttonAddName
            // 
            this.buttonAddName.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonAddName.Location = new System.Drawing.Point(862, 305);
            this.buttonAddName.Name = "buttonAddName";
            this.buttonAddName.Size = new System.Drawing.Size(95, 23);
            this.buttonAddName.TabIndex = 30;
            this.buttonAddName.Text = "整理尾缀";
            this.buttonAddName.UseVisualStyleBackColor = true;
            this.buttonAddName.Click += new System.EventHandler(this.buttonAddName_Click);
            // 
            // textBoxGoals
            // 
            this.textBoxGoals.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.textBoxGoals.Location = new System.Drawing.Point(738, 472);
            this.textBoxGoals.Multiline = true;
            this.textBoxGoals.Name = "textBoxGoals";
            this.textBoxGoals.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.textBoxGoals.Size = new System.Drawing.Size(219, 74);
            this.textBoxGoals.TabIndex = 31;
            // 
            // label5
            // 
            this.label5.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label5.AutoSize = true;
            this.label5.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.label5.Location = new System.Drawing.Point(738, 445);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(95, 12);
            this.label5.TabIndex = 32;
            this.label5.Text = "可达目标(;分割)";
            // 
            // label9
            // 
            this.label9.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label9.AutoSize = true;
            this.label9.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.label9.Location = new System.Drawing.Point(744, 281);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(65, 12);
            this.label9.TabIndex = 33;
            this.label9.Text = "选中项名字";
            // 
            // numericUpDown1
            // 
            this.numericUpDown1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.numericUpDown1.DecimalPlaces = 2;
            this.numericUpDown1.Enabled = false;
            this.numericUpDown1.Location = new System.Drawing.Point(75, 18);
            this.numericUpDown1.Maximum = new decimal(new int[] {
            9999,
            0,
            0,
            0});
            this.numericUpDown1.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.numericUpDown1.Name = "numericUpDown1";
            this.numericUpDown1.Size = new System.Drawing.Size(60, 21);
            this.numericUpDown1.TabIndex = 36;
            this.numericUpDown1.Value = new decimal(new int[] {
            120,
            0,
            0,
            0});
            // 
            // label11
            // 
            this.label11.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label11.AutoSize = true;
            this.label11.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.label11.Location = new System.Drawing.Point(168, 20);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(47, 12);
            this.label11.TabIndex = 37;
            this.label11.Text = "米/分钟";
            // 
            // label13
            // 
            this.label13.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label13.AutoSize = true;
            this.label13.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.label13.Location = new System.Drawing.Point(168, 46);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(47, 12);
            this.label13.TabIndex = 40;
            this.label13.Text = "米/分钟";
            // 
            // button2
            // 
            this.button2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.button2.Location = new System.Drawing.Point(740, 672);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(95, 23);
            this.button2.TabIndex = 41;
            this.button2.Text = "载入SVG";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // numericUpDown3
            // 
            this.numericUpDown3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.numericUpDown3.Increment = new decimal(new int[] {
            5,
            0,
            0,
            0});
            this.numericUpDown3.Location = new System.Drawing.Point(850, 672);
            this.numericUpDown3.Maximum = new decimal(new int[] {
            10,
            0,
            0,
            0});
            this.numericUpDown3.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.numericUpDown3.Name = "numericUpDown3";
            this.numericUpDown3.Size = new System.Drawing.Size(60, 21);
            this.numericUpDown3.TabIndex = 42;
            this.numericUpDown3.Value = new decimal(new int[] {
            10,
            0,
            0,
            0});
            // 
            // label14
            // 
            this.label14.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label14.AutoSize = true;
            this.label14.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.label14.Location = new System.Drawing.Point(754, 707);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(53, 12);
            this.label14.TabIndex = 43;
            this.label14.Text = "地图名称";
            // 
            // textBoxMapName
            // 
            this.textBoxMapName.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.textBoxMapName.Location = new System.Drawing.Point(815, 704);
            this.textBoxMapName.Name = "textBoxMapName";
            this.textBoxMapName.Size = new System.Drawing.Size(146, 21);
            this.textBoxMapName.TabIndex = 44;
            // 
            // button3
            // 
            this.button3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.button3.Enabled = false;
            this.button3.Location = new System.Drawing.Point(738, 340);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(95, 23);
            this.button3.TabIndex = 45;
            this.button3.Text = "景点起名";
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // button4
            // 
            this.button4.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.button4.Location = new System.Drawing.Point(862, 340);
            this.button4.Name = "button4";
            this.button4.Size = new System.Drawing.Size(95, 33);
            this.button4.TabIndex = 46;
            this.button4.Text = "站点起名\r\n(有序)";
            this.button4.UseVisualStyleBackColor = true;
            this.button4.Click += new System.EventHandler(this.button4_Click);
            // 
            // button5
            // 
            this.button5.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.button5.Location = new System.Drawing.Point(866, 395);
            this.button5.Name = "button5";
            this.button5.Size = new System.Drawing.Size(83, 41);
            this.button5.TabIndex = 47;
            this.button5.Text = "导出二维码坐标";
            this.button5.UseVisualStyleBackColor = true;
            this.button5.Click += new System.EventHandler(this.buttonExportQRCodeLocation_Click);
            // 
            // panel2
            // 
            this.panel2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.panel2.Controls.Add(this.label11);
            this.panel2.Controls.Add(this.label10);
            this.panel2.Controls.Add(this.numericUpDown1);
            this.panel2.Controls.Add(this.label12);
            this.panel2.Controls.Add(this.numericUpDown2);
            this.panel2.Controls.Add(this.label13);
            this.panel2.Location = new System.Drawing.Point(742, 581);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(219, 71);
            this.panel2.TabIndex = 48;
            // 
            // label10
            // 
            this.label10.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label10.AutoSize = true;
            this.label10.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.label10.Location = new System.Drawing.Point(4, 20);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(29, 12);
            this.label10.TabIndex = 35;
            this.label10.Text = "车速";
            // 
            // label12
            // 
            this.label12.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label12.AutoSize = true;
            this.label12.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.label12.Location = new System.Drawing.Point(4, 46);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(53, 12);
            this.label12.TabIndex = 38;
            this.label12.Text = "步行速度";
            // 
            // numericUpDown2
            // 
            this.numericUpDown2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.numericUpDown2.DecimalPlaces = 2;
            this.numericUpDown2.Enabled = false;
            this.numericUpDown2.Location = new System.Drawing.Point(75, 44);
            this.numericUpDown2.Maximum = new decimal(new int[] {
            9999,
            0,
            0,
            0});
            this.numericUpDown2.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.numericUpDown2.Name = "numericUpDown2";
            this.numericUpDown2.Size = new System.Drawing.Size(60, 21);
            this.numericUpDown2.TabIndex = 39;
            this.numericUpDown2.Value = new decimal(new int[] {
            80,
            0,
            0,
            0});
            // 
            // groupBox1
            // 
            this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBox1.Controls.Add(this.radioButton5);
            this.groupBox1.Controls.Add(this.radioButton6);
            this.groupBox1.Controls.Add(this.radioButton7);
            this.groupBox1.Controls.Add(this.radioButton8);
            this.groupBox1.Controls.Add(this.radioButton3);
            this.groupBox1.Controls.Add(this.radioButton4);
            this.groupBox1.Controls.Add(this.radioButton2);
            this.groupBox1.Controls.Add(this.radioButton1);
            this.groupBox1.Location = new System.Drawing.Point(609, 251);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(123, 119);
            this.groupBox1.TabIndex = 49;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "二维码标牌朝向";
            // 
            // radioButton5
            // 
            this.radioButton5.AutoSize = true;
            this.radioButton5.Location = new System.Drawing.Point(64, 67);
            this.radioButton5.Name = "radioButton5";
            this.radioButton5.Size = new System.Drawing.Size(59, 16);
            this.radioButton5.TabIndex = 53;
            this.radioButton5.Text = "左上↖";
            this.radioButton5.UseVisualStyleBackColor = true;
            // 
            // radioButton6
            // 
            this.radioButton6.AutoSize = true;
            this.radioButton6.Location = new System.Drawing.Point(64, 92);
            this.radioButton6.Name = "radioButton6";
            this.radioButton6.Size = new System.Drawing.Size(59, 16);
            this.radioButton6.TabIndex = 54;
            this.radioButton6.Text = "左下↙";
            this.radioButton6.UseVisualStyleBackColor = true;
            // 
            // radioButton7
            // 
            this.radioButton7.AutoSize = true;
            this.radioButton7.Location = new System.Drawing.Point(64, 40);
            this.radioButton7.Name = "radioButton7";
            this.radioButton7.Size = new System.Drawing.Size(59, 16);
            this.radioButton7.TabIndex = 52;
            this.radioButton7.Text = "右下↘";
            this.radioButton7.UseVisualStyleBackColor = true;
            // 
            // radioButton8
            // 
            this.radioButton8.AutoSize = true;
            this.radioButton8.Location = new System.Drawing.Point(64, 18);
            this.radioButton8.Name = "radioButton8";
            this.radioButton8.Size = new System.Drawing.Size(59, 16);
            this.radioButton8.TabIndex = 51;
            this.radioButton8.Text = "右上↗";
            this.radioButton8.UseVisualStyleBackColor = true;
            // 
            // radioButton3
            // 
            this.radioButton3.AutoSize = true;
            this.radioButton3.Location = new System.Drawing.Point(6, 67);
            this.radioButton3.Name = "radioButton3";
            this.radioButton3.Size = new System.Drawing.Size(53, 16);
            this.radioButton3.TabIndex = 6;
            this.radioButton3.Text = "左 ←";
            this.radioButton3.UseVisualStyleBackColor = true;
            // 
            // radioButton4
            // 
            this.radioButton4.AutoSize = true;
            this.radioButton4.Location = new System.Drawing.Point(6, 92);
            this.radioButton4.Name = "radioButton4";
            this.radioButton4.Size = new System.Drawing.Size(53, 16);
            this.radioButton4.TabIndex = 50;
            this.radioButton4.Text = "右 →";
            this.radioButton4.UseVisualStyleBackColor = true;
            // 
            // radioButton2
            // 
            this.radioButton2.AutoSize = true;
            this.radioButton2.Location = new System.Drawing.Point(6, 40);
            this.radioButton2.Name = "radioButton2";
            this.radioButton2.Size = new System.Drawing.Size(53, 16);
            this.radioButton2.TabIndex = 5;
            this.radioButton2.Text = "下 ↓";
            this.radioButton2.UseVisualStyleBackColor = true;
            // 
            // radioButton1
            // 
            this.radioButton1.AutoSize = true;
            this.radioButton1.Checked = true;
            this.radioButton1.Location = new System.Drawing.Point(6, 18);
            this.radioButton1.Name = "radioButton1";
            this.radioButton1.Size = new System.Drawing.Size(53, 16);
            this.radioButton1.TabIndex = 4;
            this.radioButton1.TabStop = true;
            this.radioButton1.Text = "上 ↑";
            this.radioButton1.UseVisualStyleBackColor = true;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(966, 748);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.button5);
            this.Controls.Add(this.button4);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.textBoxMapName);
            this.Controls.Add(this.label14);
            this.Controls.Add(this.numericUpDown3);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.label9);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.textBoxGoals);
            this.Controls.Add(this.buttonAddName);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.buttonSetGoalCells);
            this.Controls.Add(this.checkBoxIsAll);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.buttonChangeName);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.textBoxName);
            this.Controls.Add(this.buttonName);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.rotateNumericUpDown);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.UseSavedMapInfoButton);
            this.Controls.Add(this.SaveMapInfoButton);
            this.Controls.Add(this.SaveButton);
            this.Controls.Add(this.CellWidthNumericUpDown);
            this.Controls.Add(this.SvgImportButton);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.btnRest);
            this.Controls.Add(this.btnStartSearch);
            this.Controls.Add(this.btnCtStoneHand);
            this.Controls.Add(this.btnCtStoneRodom);
            this.Controls.Add(this.panel1);
            this.Name = "Form1";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Form1";
            this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Form1_FormClosing);
            this.Load += new System.EventHandler(this.Form1_Load);
            this.panel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBoxMap)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.CellWidthNumericUpDown)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.rotateNumericUpDown)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown3)).EndInit();
            this.panel2.ResumeLayout(false);
            this.panel2.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown2)).EndInit();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
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
        private System.Windows.Forms.Button SvgImportButton;
        private System.Windows.Forms.OpenFileDialog openSvgFile;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.NumericUpDown CellWidthNumericUpDown;
        private System.Windows.Forms.Button SaveButton;
        private System.Windows.Forms.Button SaveMapInfoButton;
        private System.Windows.Forms.Button UseSavedMapInfoButton;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.NumericUpDown rotateNumericUpDown;
        private System.Windows.Forms.PictureBox pictureBoxMap;
        private System.Windows.Forms.Button buttonName;
        private System.Windows.Forms.TextBox textBoxName;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Button buttonChangeName;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.CheckBox checkBoxIsAll;
        private System.Windows.Forms.Button buttonSetGoalCells;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.Button buttonAddName;
        private System.Windows.Forms.TextBox textBoxGoals;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.NumericUpDown numericUpDown1;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.NumericUpDown numericUpDown3;
        private System.Windows.Forms.Label label14;
        private System.Windows.Forms.TextBox textBoxMapName;
        private System.Windows.Forms.Button button3;
        private System.Windows.Forms.Button button4;
        private System.Windows.Forms.Button button5;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.NumericUpDown numericUpDown2;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.RadioButton radioButton4;
        private System.Windows.Forms.RadioButton radioButton2;
        private System.Windows.Forms.RadioButton radioButton1;
        private System.Windows.Forms.RadioButton radioButton3;
        private System.Windows.Forms.RadioButton radioButton5;
        private System.Windows.Forms.RadioButton radioButton6;
        private System.Windows.Forms.RadioButton radioButton7;
        private System.Windows.Forms.RadioButton radioButton8;
    }
}

