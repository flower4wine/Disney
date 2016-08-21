namespace CommunicationTest
{
    partial class SettingForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(SettingForm));
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.checkBoxSsl = new System.Windows.Forms.CheckBox();
            this.textBoxServer = new System.Windows.Forms.TextBox();
            this.textBoxPort = new System.Windows.Forms.TextBox();
            this.textBoxSendEmail = new System.Windows.Forms.TextBox();
            this.textBoxSendpassword = new System.Windows.Forms.TextBox();
            this.textBoxSendUsername = new System.Windows.Forms.TextBox();
            this.textBoxDetectionInterval = new System.Windows.Forms.TextBox();
            this.btnSave = new System.Windows.Forms.Button();
            this.buttonConnTest = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(11, 12);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(65, 12);
            this.label1.TabIndex = 0;
            this.label1.Text = "邮件服务器";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(11, 100);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(41, 12);
            this.label2.TabIndex = 1;
            this.label2.Text = "发件箱";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(11, 144);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(77, 12);
            this.label3.TabIndex = 2;
            this.label3.Text = "发件箱用户名";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(11, 188);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(53, 12);
            this.label4.TabIndex = 3;
            this.label4.Text = "发件密码";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(11, 56);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(89, 12);
            this.label5.TabIndex = 4;
            this.label5.Text = "邮件服务器端口";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(11, 280);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(95, 12);
            this.label6.TabIndex = 5;
            this.label6.Text = "轮询间隔时间(s)";
            // 
            // checkBoxSsl
            // 
            this.checkBoxSsl.AutoSize = true;
            this.checkBoxSsl.Location = new System.Drawing.Point(133, 235);
            this.checkBoxSsl.Name = "checkBoxSsl";
            this.checkBoxSsl.Size = new System.Drawing.Size(90, 16);
            this.checkBoxSsl.TabIndex = 6;
            this.checkBoxSsl.Text = "是否ssl加密";
            this.checkBoxSsl.UseVisualStyleBackColor = true;
            // 
            // textBoxServer
            // 
            this.textBoxServer.Location = new System.Drawing.Point(133, 9);
            this.textBoxServer.Name = "textBoxServer";
            this.textBoxServer.Size = new System.Drawing.Size(263, 21);
            this.textBoxServer.TabIndex = 7;
            // 
            // textBoxPort
            // 
            this.textBoxPort.Location = new System.Drawing.Point(133, 53);
            this.textBoxPort.Name = "textBoxPort";
            this.textBoxPort.Size = new System.Drawing.Size(263, 21);
            this.textBoxPort.TabIndex = 8;
            this.textBoxPort.Text = "25";
            // 
            // textBoxSendEmail
            // 
            this.textBoxSendEmail.Location = new System.Drawing.Point(133, 97);
            this.textBoxSendEmail.Name = "textBoxSendEmail";
            this.textBoxSendEmail.Size = new System.Drawing.Size(263, 21);
            this.textBoxSendEmail.TabIndex = 9;
            // 
            // textBoxSendpassword
            // 
            this.textBoxSendpassword.Location = new System.Drawing.Point(133, 185);
            this.textBoxSendpassword.Name = "textBoxSendpassword";
            this.textBoxSendpassword.Size = new System.Drawing.Size(263, 21);
            this.textBoxSendpassword.TabIndex = 10;
            // 
            // textBoxSendUsername
            // 
            this.textBoxSendUsername.Location = new System.Drawing.Point(133, 141);
            this.textBoxSendUsername.Name = "textBoxSendUsername";
            this.textBoxSendUsername.Size = new System.Drawing.Size(263, 21);
            this.textBoxSendUsername.TabIndex = 11;
            // 
            // textBoxDetectionInterval
            // 
            this.textBoxDetectionInterval.Location = new System.Drawing.Point(133, 277);
            this.textBoxDetectionInterval.Name = "textBoxDetectionInterval";
            this.textBoxDetectionInterval.Size = new System.Drawing.Size(263, 21);
            this.textBoxDetectionInterval.TabIndex = 12;
            // 
            // btnSave
            // 
            this.btnSave.Location = new System.Drawing.Point(321, 321);
            this.btnSave.Name = "btnSave";
            this.btnSave.Size = new System.Drawing.Size(75, 23);
            this.btnSave.TabIndex = 13;
            this.btnSave.Text = "保存";
            this.btnSave.UseVisualStyleBackColor = true;
            this.btnSave.Click += new System.EventHandler(this.btnSave_Click);
            // 
            // buttonConnTest
            // 
            this.buttonConnTest.Location = new System.Drawing.Point(224, 321);
            this.buttonConnTest.Name = "buttonConnTest";
            this.buttonConnTest.Size = new System.Drawing.Size(75, 23);
            this.buttonConnTest.TabIndex = 14;
            this.buttonConnTest.Text = "Conn Test";
            this.buttonConnTest.UseVisualStyleBackColor = true;
            this.buttonConnTest.Click += new System.EventHandler(this.buttonConnTest_Click);
            // 
            // SettingForm
            // 
            this.AcceptButton = this.btnSave;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(409, 356);
            this.Controls.Add(this.buttonConnTest);
            this.Controls.Add(this.btnSave);
            this.Controls.Add(this.textBoxDetectionInterval);
            this.Controls.Add(this.textBoxSendUsername);
            this.Controls.Add(this.textBoxSendpassword);
            this.Controls.Add(this.textBoxSendEmail);
            this.Controls.Add(this.textBoxPort);
            this.Controls.Add(this.textBoxServer);
            this.Controls.Add(this.checkBoxSsl);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MaximumSize = new System.Drawing.Size(425, 395);
            this.MinimizeBox = false;
            this.MinimumSize = new System.Drawing.Size(425, 395);
            this.Name = "SettingForm";
            this.Text = "SettingForm";
            this.Load += new System.EventHandler(this.SettingForm_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.CheckBox checkBoxSsl;
        private System.Windows.Forms.TextBox textBoxServer;
        private System.Windows.Forms.TextBox textBoxPort;
        private System.Windows.Forms.TextBox textBoxSendEmail;
        private System.Windows.Forms.TextBox textBoxSendpassword;
        private System.Windows.Forms.TextBox textBoxSendUsername;
        private System.Windows.Forms.TextBox textBoxDetectionInterval;
        private System.Windows.Forms.Button btnSave;
        private System.Windows.Forms.Button buttonConnTest;
    }
}