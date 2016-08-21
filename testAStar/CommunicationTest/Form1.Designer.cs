namespace CommunicationTest
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
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.notifyIcon1 = new System.Windows.Forms.NotifyIcon(this.components);
            this.toolStrip1 = new System.Windows.Forms.ToolStrip();
            this.toolStripButtonSetting = new System.Windows.Forms.ToolStripButton();
            this.toolStripButtonSave = new System.Windows.Forms.ToolStripButton();
            this.toolStripLabel1 = new System.Windows.Forms.ToolStripLabel();
            this.toolStripSeparator1 = new System.Windows.Forms.ToolStripSeparator();
            this.toolStripButtonListen = new System.Windows.Forms.ToolStripButton();
            this.panel1 = new System.Windows.Forms.Panel();
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.buttonDeleteWebsiteUrl = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.dataGridViewWebsite = new System.Windows.Forms.DataGridView();
            this.WebUrl = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.panel2 = new System.Windows.Forms.Panel();
            this.label4 = new System.Windows.Forms.Label();
            this.buttonAddWebsiteUrl = new System.Windows.Forms.Button();
            this.textBoxWebsiteUrl = new System.Windows.Forms.TextBox();
            this.splitContainer2 = new System.Windows.Forms.SplitContainer();
            this.dataGridViewNoticeUserEmail = new System.Windows.Forms.DataGridView();
            this.dataGridViewTextBoxColumn1 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.panel3 = new System.Windows.Forms.Panel();
            this.label5 = new System.Windows.Forms.Label();
            this.buttonAddNoticUserEmail = new System.Windows.Forms.Button();
            this.textBoxNoticUserEmail = new System.Windows.Forms.TextBox();
            this.buttonDeleteNoticUserEmail = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.buttonAllLogExport = new System.Windows.Forms.Button();
            this.textBoxErrorLog = new System.Windows.Forms.TextBox();
            this.buttonLogExport = new System.Windows.Forms.Button();
            this.label3 = new System.Windows.Forms.Label();
            this.toolStrip1.SuspendLayout();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer1)).BeginInit();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewWebsite)).BeginInit();
            this.panel2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer2)).BeginInit();
            this.splitContainer2.Panel1.SuspendLayout();
            this.splitContainer2.Panel2.SuspendLayout();
            this.splitContainer2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewNoticeUserEmail)).BeginInit();
            this.panel3.SuspendLayout();
            this.SuspendLayout();
            // 
            // notifyIcon1
            // 
            this.notifyIcon1.Icon = ((System.Drawing.Icon)(resources.GetObject("notifyIcon1.Icon")));
            this.notifyIcon1.Text = "notifyIcon1";
            this.notifyIcon1.Visible = true;
            // 
            // toolStrip1
            // 
            this.toolStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripButtonSetting,
            this.toolStripButtonSave,
            this.toolStripLabel1,
            this.toolStripSeparator1,
            this.toolStripButtonListen});
            this.toolStrip1.Location = new System.Drawing.Point(0, 0);
            this.toolStrip1.Name = "toolStrip1";
            this.toolStrip1.Size = new System.Drawing.Size(1267, 25);
            this.toolStrip1.TabIndex = 2;
            this.toolStrip1.Text = "toolStrip1";
            // 
            // toolStripButtonSetting
            // 
            this.toolStripButtonSetting.Image = global::CommunicationTest.Properties.Resources.setting2;
            this.toolStripButtonSetting.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonSetting.Name = "toolStripButtonSetting";
            this.toolStripButtonSetting.Size = new System.Drawing.Size(52, 22);
            this.toolStripButtonSetting.Text = "设置";
            this.toolStripButtonSetting.ToolTipText = "设置";
            this.toolStripButtonSetting.Click += new System.EventHandler(this.toolStripButtonSetting_Click);
            // 
            // toolStripButtonSave
            // 
            this.toolStripButtonSave.Image = global::CommunicationTest.Properties.Resources.setting2;
            this.toolStripButtonSave.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonSave.Name = "toolStripButtonSave";
            this.toolStripButtonSave.Size = new System.Drawing.Size(52, 22);
            this.toolStripButtonSave.Text = "保存";
            this.toolStripButtonSave.Click += new System.EventHandler(this.toolStripButtonSave_Click);
            // 
            // toolStripLabel1
            // 
            this.toolStripLabel1.Font = new System.Drawing.Font("Microsoft YaHei UI", 9F, System.Drawing.FontStyle.Bold);
            this.toolStripLabel1.ForeColor = System.Drawing.Color.Red;
            this.toolStripLabel1.Name = "toolStripLabel1";
            this.toolStripLabel1.Size = new System.Drawing.Size(260, 22);
            this.toolStripLabel1.Text = "说明：修改数据后需要点击保存按钮使修改生效";
            // 
            // toolStripSeparator1
            // 
            this.toolStripSeparator1.Name = "toolStripSeparator1";
            this.toolStripSeparator1.Size = new System.Drawing.Size(6, 25);
            // 
            // toolStripButtonListen
            // 
            this.toolStripButtonListen.Image = global::CommunicationTest.Properties.Resources.not_listen;
            this.toolStripButtonListen.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButtonListen.Name = "toolStripButtonListen";
            this.toolStripButtonListen.Size = new System.Drawing.Size(100, 22);
            this.toolStripButtonListen.Text = "点击开始监听";
            this.toolStripButtonListen.Click += new System.EventHandler(this.toolStripButtonListen_Click);
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.splitContainer1);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel1.Location = new System.Drawing.Point(0, 25);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(1267, 495);
            this.panel1.TabIndex = 3;
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Name = "splitContainer1";
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.buttonDeleteWebsiteUrl);
            this.splitContainer1.Panel1.Controls.Add(this.label1);
            this.splitContainer1.Panel1.Controls.Add(this.dataGridViewWebsite);
            this.splitContainer1.Panel1.Controls.Add(this.panel2);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.splitContainer2);
            this.splitContainer1.Size = new System.Drawing.Size(1267, 495);
            this.splitContainer1.SplitterDistance = 422;
            this.splitContainer1.TabIndex = 0;
            // 
            // buttonDeleteWebsiteUrl
            // 
            this.buttonDeleteWebsiteUrl.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonDeleteWebsiteUrl.Location = new System.Drawing.Point(341, 423);
            this.buttonDeleteWebsiteUrl.Name = "buttonDeleteWebsiteUrl";
            this.buttonDeleteWebsiteUrl.Size = new System.Drawing.Size(75, 23);
            this.buttonDeleteWebsiteUrl.TabIndex = 3;
            this.buttonDeleteWebsiteUrl.Text = "删除选中";
            this.buttonDeleteWebsiteUrl.UseVisualStyleBackColor = true;
            this.buttonDeleteWebsiteUrl.Click += new System.EventHandler(this.buttonDeleteWebsiteUrl_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(3, 5);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(89, 12);
            this.label1.TabIndex = 4;
            this.label1.Text = "要监听的网站：";
            // 
            // dataGridViewWebsite
            // 
            this.dataGridViewWebsite.AllowUserToAddRows = false;
            this.dataGridViewWebsite.AllowUserToDeleteRows = false;
            this.dataGridViewWebsite.AllowUserToOrderColumns = true;
            this.dataGridViewWebsite.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.dataGridViewWebsite.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.dataGridViewWebsite.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewWebsite.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.WebUrl});
            this.dataGridViewWebsite.Location = new System.Drawing.Point(0, 20);
            this.dataGridViewWebsite.Name = "dataGridViewWebsite";
            this.dataGridViewWebsite.ReadOnly = true;
            this.dataGridViewWebsite.RowHeadersVisible = false;
            this.dataGridViewWebsite.RowTemplate.Height = 23;
            this.dataGridViewWebsite.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dataGridViewWebsite.Size = new System.Drawing.Size(422, 397);
            this.dataGridViewWebsite.TabIndex = 0;
            this.dataGridViewWebsite.SelectionChanged += new System.EventHandler(this.dataGridViewWebsite_SelectionChanged);
            // 
            // WebUrl
            // 
            this.WebUrl.DataPropertyName = "WebUrl";
            this.WebUrl.HeaderText = "网站Url";
            this.WebUrl.Name = "WebUrl";
            this.WebUrl.ReadOnly = true;
            // 
            // panel2
            // 
            this.panel2.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.panel2.Controls.Add(this.label4);
            this.panel2.Controls.Add(this.buttonAddWebsiteUrl);
            this.panel2.Controls.Add(this.textBoxWebsiteUrl);
            this.panel2.Location = new System.Drawing.Point(0, 452);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(419, 43);
            this.panel2.TabIndex = 3;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(12, 15);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(59, 12);
            this.label4.TabIndex = 5;
            this.label4.Text = "网站Url：";
            // 
            // buttonAddWebsiteUrl
            // 
            this.buttonAddWebsiteUrl.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonAddWebsiteUrl.Location = new System.Drawing.Point(341, 10);
            this.buttonAddWebsiteUrl.Name = "buttonAddWebsiteUrl";
            this.buttonAddWebsiteUrl.Size = new System.Drawing.Size(75, 23);
            this.buttonAddWebsiteUrl.TabIndex = 2;
            this.buttonAddWebsiteUrl.Text = "添加";
            this.buttonAddWebsiteUrl.UseVisualStyleBackColor = true;
            this.buttonAddWebsiteUrl.Click += new System.EventHandler(this.buttonAddWebsiteUrl_Click);
            // 
            // textBoxWebsiteUrl
            // 
            this.textBoxWebsiteUrl.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.textBoxWebsiteUrl.Location = new System.Drawing.Point(77, 10);
            this.textBoxWebsiteUrl.Name = "textBoxWebsiteUrl";
            this.textBoxWebsiteUrl.Size = new System.Drawing.Size(248, 21);
            this.textBoxWebsiteUrl.TabIndex = 1;
            // 
            // splitContainer2
            // 
            this.splitContainer2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer2.Location = new System.Drawing.Point(0, 0);
            this.splitContainer2.Name = "splitContainer2";
            // 
            // splitContainer2.Panel1
            // 
            this.splitContainer2.Panel1.Controls.Add(this.dataGridViewNoticeUserEmail);
            this.splitContainer2.Panel1.Controls.Add(this.panel3);
            this.splitContainer2.Panel1.Controls.Add(this.buttonDeleteNoticUserEmail);
            this.splitContainer2.Panel1.Controls.Add(this.label2);
            // 
            // splitContainer2.Panel2
            // 
            this.splitContainer2.Panel2.Controls.Add(this.buttonAllLogExport);
            this.splitContainer2.Panel2.Controls.Add(this.textBoxErrorLog);
            this.splitContainer2.Panel2.Controls.Add(this.buttonLogExport);
            this.splitContainer2.Panel2.Controls.Add(this.label3);
            this.splitContainer2.Size = new System.Drawing.Size(841, 495);
            this.splitContainer2.SplitterDistance = 427;
            this.splitContainer2.TabIndex = 0;
            // 
            // dataGridViewNoticeUserEmail
            // 
            this.dataGridViewNoticeUserEmail.AllowUserToAddRows = false;
            this.dataGridViewNoticeUserEmail.AllowUserToDeleteRows = false;
            this.dataGridViewNoticeUserEmail.AllowUserToOrderColumns = true;
            this.dataGridViewNoticeUserEmail.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.dataGridViewNoticeUserEmail.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.dataGridViewNoticeUserEmail.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewNoticeUserEmail.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.dataGridViewTextBoxColumn1});
            this.dataGridViewNoticeUserEmail.Location = new System.Drawing.Point(0, 20);
            this.dataGridViewNoticeUserEmail.Name = "dataGridViewNoticeUserEmail";
            this.dataGridViewNoticeUserEmail.ReadOnly = true;
            this.dataGridViewNoticeUserEmail.RowHeadersVisible = false;
            this.dataGridViewNoticeUserEmail.RowTemplate.Height = 23;
            this.dataGridViewNoticeUserEmail.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dataGridViewNoticeUserEmail.Size = new System.Drawing.Size(425, 397);
            this.dataGridViewNoticeUserEmail.TabIndex = 5;
            // 
            // dataGridViewTextBoxColumn1
            // 
            this.dataGridViewTextBoxColumn1.DataPropertyName = "EmailAddress";
            this.dataGridViewTextBoxColumn1.HeaderText = "邮箱地址";
            this.dataGridViewTextBoxColumn1.Name = "dataGridViewTextBoxColumn1";
            this.dataGridViewTextBoxColumn1.ReadOnly = true;
            // 
            // panel3
            // 
            this.panel3.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.panel3.Controls.Add(this.label5);
            this.panel3.Controls.Add(this.buttonAddNoticUserEmail);
            this.panel3.Controls.Add(this.textBoxNoticUserEmail);
            this.panel3.Location = new System.Drawing.Point(5, 452);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(419, 43);
            this.panel3.TabIndex = 4;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(3, 15);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(65, 12);
            this.label5.TabIndex = 6;
            this.label5.Text = "邮箱地址：";
            // 
            // buttonAddNoticUserEmail
            // 
            this.buttonAddNoticUserEmail.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonAddNoticUserEmail.Location = new System.Drawing.Point(341, 10);
            this.buttonAddNoticUserEmail.Name = "buttonAddNoticUserEmail";
            this.buttonAddNoticUserEmail.Size = new System.Drawing.Size(75, 23);
            this.buttonAddNoticUserEmail.TabIndex = 2;
            this.buttonAddNoticUserEmail.Text = "添加";
            this.buttonAddNoticUserEmail.UseVisualStyleBackColor = true;
            this.buttonAddNoticUserEmail.Click += new System.EventHandler(this.buttonAddNoticUserEmail_Click);
            // 
            // textBoxNoticUserEmail
            // 
            this.textBoxNoticUserEmail.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.textBoxNoticUserEmail.Location = new System.Drawing.Point(68, 10);
            this.textBoxNoticUserEmail.Name = "textBoxNoticUserEmail";
            this.textBoxNoticUserEmail.Size = new System.Drawing.Size(257, 21);
            this.textBoxNoticUserEmail.TabIndex = 1;
            // 
            // buttonDeleteNoticUserEmail
            // 
            this.buttonDeleteNoticUserEmail.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonDeleteNoticUserEmail.Location = new System.Drawing.Point(349, 423);
            this.buttonDeleteNoticUserEmail.Name = "buttonDeleteNoticUserEmail";
            this.buttonDeleteNoticUserEmail.Size = new System.Drawing.Size(75, 23);
            this.buttonDeleteNoticUserEmail.TabIndex = 5;
            this.buttonDeleteNoticUserEmail.Text = "删除选中";
            this.buttonDeleteNoticUserEmail.UseVisualStyleBackColor = true;
            this.buttonDeleteNoticUserEmail.Click += new System.EventHandler(this.buttonDeleteNoticUserEmail_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(3, 5);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(77, 12);
            this.label2.TabIndex = 5;
            this.label2.Text = "通知的邮箱：";
            // 
            // buttonAllLogExport
            // 
            this.buttonAllLogExport.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonAllLogExport.Location = new System.Drawing.Point(154, 462);
            this.buttonAllLogExport.Name = "buttonAllLogExport";
            this.buttonAllLogExport.Size = new System.Drawing.Size(116, 23);
            this.buttonAllLogExport.TabIndex = 10;
            this.buttonAllLogExport.Text = "导出全部历史数据";
            this.buttonAllLogExport.UseVisualStyleBackColor = true;
            this.buttonAllLogExport.Click += new System.EventHandler(this.buttonAllLogExport_Click);
            // 
            // textBoxErrorLog
            // 
            this.textBoxErrorLog.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.textBoxErrorLog.Location = new System.Drawing.Point(0, 20);
            this.textBoxErrorLog.Multiline = true;
            this.textBoxErrorLog.Name = "textBoxErrorLog";
            this.textBoxErrorLog.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.textBoxErrorLog.Size = new System.Drawing.Size(404, 426);
            this.textBoxErrorLog.TabIndex = 9;
            // 
            // buttonLogExport
            // 
            this.buttonLogExport.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonLogExport.Location = new System.Drawing.Point(276, 462);
            this.buttonLogExport.Name = "buttonLogExport";
            this.buttonLogExport.Size = new System.Drawing.Size(122, 23);
            this.buttonLogExport.TabIndex = 6;
            this.buttonLogExport.Text = "导出当前显示数据";
            this.buttonLogExport.UseVisualStyleBackColor = true;
            this.buttonLogExport.Click += new System.EventHandler(this.buttonLogExport_Click);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(6, 5);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(65, 12);
            this.label3.TabIndex = 7;
            this.label3.Text = "监听日志：";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1267, 520);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.toolStrip1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "Form1";
            this.Text = "网站监听";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.toolStrip1.ResumeLayout(false);
            this.toolStrip1.PerformLayout();
            this.panel1.ResumeLayout(false);
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel1.PerformLayout();
            this.splitContainer1.Panel2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer1)).EndInit();
            this.splitContainer1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewWebsite)).EndInit();
            this.panel2.ResumeLayout(false);
            this.panel2.PerformLayout();
            this.splitContainer2.Panel1.ResumeLayout(false);
            this.splitContainer2.Panel1.PerformLayout();
            this.splitContainer2.Panel2.ResumeLayout(false);
            this.splitContainer2.Panel2.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer2)).EndInit();
            this.splitContainer2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewNoticeUserEmail)).EndInit();
            this.panel3.ResumeLayout(false);
            this.panel3.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.NotifyIcon notifyIcon1;
        private System.Windows.Forms.ToolStrip toolStrip1;
        private System.Windows.Forms.ToolStripButton toolStripButtonSetting;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.SplitContainer splitContainer1;
        private System.Windows.Forms.DataGridView dataGridViewWebsite;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Button buttonAddWebsiteUrl;
        private System.Windows.Forms.TextBox textBoxWebsiteUrl;
        private System.Windows.Forms.SplitContainer splitContainer2;
        private System.Windows.Forms.Button buttonDeleteWebsiteUrl;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Panel panel3;
        private System.Windows.Forms.Button buttonAddNoticUserEmail;
        private System.Windows.Forms.TextBox textBoxNoticUserEmail;
        private System.Windows.Forms.Button buttonDeleteNoticUserEmail;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Button buttonLogExport;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.ToolStripButton toolStripButtonSave;
        private System.Windows.Forms.ToolStripLabel toolStripLabel1;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox textBoxErrorLog;
        private System.Windows.Forms.Button buttonAllLogExport;
        private System.Windows.Forms.DataGridView dataGridViewNoticeUserEmail;
        private System.Windows.Forms.DataGridViewTextBoxColumn dataGridViewTextBoxColumn1;
        private System.Windows.Forms.DataGridViewTextBoxColumn WebUrl;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator1;
        private System.Windows.Forms.ToolStripButton toolStripButtonListen;
    }
}

