namespace AddWaterMark
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
        /// 设计器支持所需的方法 - 不要修改
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.textBoxFromDir = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.textBoxSaveDir = new System.Windows.Forms.TextBox();
            this.groupBoxParkingChoice = new System.Windows.Forms.GroupBox();
            this.radioButtonP4 = new System.Windows.Forms.RadioButton();
            this.radioButtonP3 = new System.Windows.Forms.RadioButton();
            this.radioButtonP2 = new System.Windows.Forms.RadioButton();
            this.radioButtonP1 = new System.Windows.Forms.RadioButton();
            this.groupBoxWaterMarkType = new System.Windows.Forms.GroupBox();
            this.checkBoxScale = new System.Windows.Forms.CheckBox();
            this.checkBoxDirection = new System.Windows.Forms.CheckBox();
            this.buttonStart = new System.Windows.Forms.Button();
            this.progressBar1 = new System.Windows.Forms.ProgressBar();
            this.groupBoxParkingChoice.SuspendLayout();
            this.groupBoxWaterMarkType.SuspendLayout();
            this.SuspendLayout();
            // 
            // textBoxFromDir
            // 
            this.textBoxFromDir.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.textBoxFromDir.Location = new System.Drawing.Point(114, 6);
            this.textBoxFromDir.Name = "textBoxFromDir";
            this.textBoxFromDir.Size = new System.Drawing.Size(309, 21);
            this.textBoxFromDir.TabIndex = 0;
            this.textBoxFromDir.Text = "D:\\GitHub\\Disney\\doc\\tu\\库内 旋转 - 副本";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(19, 41);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(77, 12);
            this.label1.TabIndex = 1;
            this.label1.Text = "图片保存路径";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(19, 9);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(89, 12);
            this.label2.TabIndex = 2;
            this.label2.Text = "图片所在文件夹";
            // 
            // textBoxSaveDir
            // 
            this.textBoxSaveDir.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.textBoxSaveDir.Location = new System.Drawing.Point(114, 38);
            this.textBoxSaveDir.Name = "textBoxSaveDir";
            this.textBoxSaveDir.Size = new System.Drawing.Size(309, 21);
            this.textBoxSaveDir.TabIndex = 3;
            this.textBoxSaveDir.Text = "D:\\GitHub\\Disney\\doc\\tu";
            // 
            // groupBoxParkingChoice
            // 
            this.groupBoxParkingChoice.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBoxParkingChoice.Controls.Add(this.radioButtonP4);
            this.groupBoxParkingChoice.Controls.Add(this.radioButtonP3);
            this.groupBoxParkingChoice.Controls.Add(this.radioButtonP2);
            this.groupBoxParkingChoice.Controls.Add(this.radioButtonP1);
            this.groupBoxParkingChoice.Location = new System.Drawing.Point(19, 65);
            this.groupBoxParkingChoice.Name = "groupBoxParkingChoice";
            this.groupBoxParkingChoice.Size = new System.Drawing.Size(404, 77);
            this.groupBoxParkingChoice.TabIndex = 4;
            this.groupBoxParkingChoice.TabStop = false;
            this.groupBoxParkingChoice.Text = "停车场选择";
            // 
            // radioButtonP4
            // 
            this.radioButtonP4.AutoSize = true;
            this.radioButtonP4.Location = new System.Drawing.Point(295, 30);
            this.radioButtonP4.Name = "radioButtonP4";
            this.radioButtonP4.Size = new System.Drawing.Size(35, 16);
            this.radioButtonP4.TabIndex = 3;
            this.radioButtonP4.Text = "P4";
            this.radioButtonP4.UseVisualStyleBackColor = true;
            // 
            // radioButtonP3
            // 
            this.radioButtonP3.AutoSize = true;
            this.radioButtonP3.Location = new System.Drawing.Point(227, 30);
            this.radioButtonP3.Name = "radioButtonP3";
            this.radioButtonP3.Size = new System.Drawing.Size(35, 16);
            this.radioButtonP3.TabIndex = 2;
            this.radioButtonP3.Text = "P3";
            this.radioButtonP3.UseVisualStyleBackColor = true;
            // 
            // radioButtonP2
            // 
            this.radioButtonP2.AutoSize = true;
            this.radioButtonP2.Location = new System.Drawing.Point(151, 30);
            this.radioButtonP2.Name = "radioButtonP2";
            this.radioButtonP2.Size = new System.Drawing.Size(35, 16);
            this.radioButtonP2.TabIndex = 1;
            this.radioButtonP2.Text = "P2";
            this.radioButtonP2.UseVisualStyleBackColor = true;
            // 
            // radioButtonP1
            // 
            this.radioButtonP1.AutoSize = true;
            this.radioButtonP1.Checked = true;
            this.radioButtonP1.Location = new System.Drawing.Point(78, 30);
            this.radioButtonP1.Name = "radioButtonP1";
            this.radioButtonP1.Size = new System.Drawing.Size(35, 16);
            this.radioButtonP1.TabIndex = 0;
            this.radioButtonP1.TabStop = true;
            this.radioButtonP1.Text = "P1";
            this.radioButtonP1.UseVisualStyleBackColor = true;
            // 
            // groupBoxWaterMarkType
            // 
            this.groupBoxWaterMarkType.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBoxWaterMarkType.Controls.Add(this.checkBoxScale);
            this.groupBoxWaterMarkType.Controls.Add(this.checkBoxDirection);
            this.groupBoxWaterMarkType.Enabled = false;
            this.groupBoxWaterMarkType.Location = new System.Drawing.Point(19, 148);
            this.groupBoxWaterMarkType.Name = "groupBoxWaterMarkType";
            this.groupBoxWaterMarkType.Size = new System.Drawing.Size(404, 57);
            this.groupBoxWaterMarkType.TabIndex = 5;
            this.groupBoxWaterMarkType.TabStop = false;
            this.groupBoxWaterMarkType.Text = "水印类型";
            // 
            // checkBoxScale
            // 
            this.checkBoxScale.AutoSize = true;
            this.checkBoxScale.Checked = true;
            this.checkBoxScale.CheckState = System.Windows.Forms.CheckState.Checked;
            this.checkBoxScale.Location = new System.Drawing.Point(286, 22);
            this.checkBoxScale.Name = "checkBoxScale";
            this.checkBoxScale.Size = new System.Drawing.Size(60, 16);
            this.checkBoxScale.TabIndex = 1;
            this.checkBoxScale.Text = "比例尺";
            this.checkBoxScale.UseVisualStyleBackColor = true;
            // 
            // checkBoxDirection
            // 
            this.checkBoxDirection.AutoSize = true;
            this.checkBoxDirection.Checked = true;
            this.checkBoxDirection.CheckState = System.Windows.Forms.CheckState.Checked;
            this.checkBoxDirection.Location = new System.Drawing.Point(70, 22);
            this.checkBoxDirection.Name = "checkBoxDirection";
            this.checkBoxDirection.Size = new System.Drawing.Size(60, 16);
            this.checkBoxDirection.TabIndex = 0;
            this.checkBoxDirection.Text = "指南针";
            this.checkBoxDirection.UseVisualStyleBackColor = true;
            // 
            // buttonStart
            // 
            this.buttonStart.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonStart.Location = new System.Drawing.Point(348, 211);
            this.buttonStart.Name = "buttonStart";
            this.buttonStart.Size = new System.Drawing.Size(75, 23);
            this.buttonStart.TabIndex = 7;
            this.buttonStart.Text = "开始添加";
            this.buttonStart.UseVisualStyleBackColor = true;
            this.buttonStart.Click += new System.EventHandler(this.buttonStart_Click);
            // 
            // progressBar1
            // 
            this.progressBar1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.progressBar1.Location = new System.Drawing.Point(19, 211);
            this.progressBar1.Name = "progressBar1";
            this.progressBar1.Size = new System.Drawing.Size(323, 23);
            this.progressBar1.TabIndex = 8;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(446, 247);
            this.Controls.Add(this.progressBar1);
            this.Controls.Add(this.buttonStart);
            this.Controls.Add(this.groupBoxWaterMarkType);
            this.Controls.Add(this.groupBoxParkingChoice);
            this.Controls.Add(this.textBoxSaveDir);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.textBoxFromDir);
            this.MinimumSize = new System.Drawing.Size(462, 286);
            this.Name = "Form1";
            this.Text = "Form1";
            this.groupBoxParkingChoice.ResumeLayout(false);
            this.groupBoxParkingChoice.PerformLayout();
            this.groupBoxWaterMarkType.ResumeLayout(false);
            this.groupBoxWaterMarkType.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox textBoxFromDir;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox textBoxSaveDir;
        private System.Windows.Forms.GroupBox groupBoxParkingChoice;
        private System.Windows.Forms.RadioButton radioButtonP4;
        private System.Windows.Forms.RadioButton radioButtonP3;
        private System.Windows.Forms.RadioButton radioButtonP2;
        private System.Windows.Forms.RadioButton radioButtonP1;
        private System.Windows.Forms.GroupBox groupBoxWaterMarkType;
        private System.Windows.Forms.CheckBox checkBoxScale;
        private System.Windows.Forms.CheckBox checkBoxDirection;
        private System.Windows.Forms.Button buttonStart;
        private System.Windows.Forms.ProgressBar progressBar1;
    }
}

