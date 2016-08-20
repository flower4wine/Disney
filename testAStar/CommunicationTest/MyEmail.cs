using System;
using System.IO;
using System.Net.Mail;
using System.Net.Mime;
using System.Text;
// ReSharper disable FieldCanBeMadeReadOnly.Local

namespace CommunicationTest
{
    public class MyEmail
    {
        /// <summary>
        /// 主要处理发送邮件的内容（如：收发人地址、标题、主体、图片等等）
        /// </summary>
        private MailMessage _mailMessage;
        /// <summary>
        /// 主要处理用smtp方式发送此邮件的配置信息（如：邮件服务器、发送端口号、验证方式等等）
        /// </summary>
        private SmtpClient _smtpClient;
        /// <summary>
        /// 发送邮件所用的端口号（htmp协议默认为25）
        /// </summary>
        private int _senderPort;
        /// <summary>
        /// 发件箱的邮件服务器地址（IP形式或字符串形式均可）
        /// </summary>
        private string _senderServerHost;
        /// <summary>
        /// 发件箱的密码
        /// </summary>
        private string _senderPassword;
        /// <summary>
        /// 发件箱的用户名（即@符号前面的字符串，例如：hello@163.com，用户名为：hello）
        /// </summary>
        private string _senderUsername;
        /// <summary>
        /// 是否对邮件内容进行socket层加密传输
        /// </summary>
        private bool _enableSsl;
        /// <summary>
        /// 是否对发件人邮箱进行密码验证
        /// </summary>
        private bool _enablePwdAuthentication;

        ///<summary>
        /// 构造函数
        ///</summary>
        ///<param name="server">发件箱的邮件服务器地址</param>
        ///<param name="toMail">收件人地址（可以是多个收件人，程序中是以“;"进行区分的）</param>
        ///<param name="fromMail">发件人地址</param>
        ///<param name="subject">邮件标题</param>
        ///<param name="emailBody">邮件内容（可以以html格式进行设计）</param>
        ///<param name="username">发件箱的用户名（即@符号前面的字符串，例如：hello@163.com，用户名为：hello）</param>
        ///<param name="password">发件人邮箱密码</param>
        ///<param name="port">发送邮件所用的端口号（htmp协议默认为25）</param>
        ///<param name="sslEnable">true表示对邮件内容进行socket层加密传输，false表示不加密</param>
        ///<param name="pwdCheckEnable">true表示对发件人邮箱进行密码验证，false表示不对发件人邮箱进行密码验证</param>
        public MyEmail(string server, string toMail, string fromMail, string subject, string emailBody, string username, string password, string port, bool sslEnable, bool pwdCheckEnable)
        {
            try
            {
                _mailMessage = new MailMessage();
                _mailMessage.To.Add(toMail);
                _mailMessage.From = new MailAddress(fromMail);
                _mailMessage.Subject = subject;
                _mailMessage.Body = emailBody;
                _mailMessage.IsBodyHtml = true;
                _mailMessage.BodyEncoding = Encoding.UTF8;
                _mailMessage.Priority = MailPriority.Normal;
                this._senderServerHost = server;
                this._senderUsername = username;
                this._senderPassword = password;
                this._senderPort = Convert.ToInt32(port);
                this._enableSsl = sslEnable;
                this._enablePwdAuthentication = pwdCheckEnable;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
        }

        ///<summary>
        /// 添加附件
        ///</summary>
        ///<param name="attachmentsPath">附件的路径集合，以分号分隔</param>
        public void AddAttachments(string attachmentsPath)
        {
            try
            {
                string[] paths = attachmentsPath.Split(';'); //以什么符号分隔可以自定义
                foreach (string path in paths)
                {
                    Attachment data = new Attachment(path, MediaTypeNames.Application.Octet);
                    ContentDisposition disposition = data.ContentDisposition;
                    disposition.CreationDate = File.GetCreationTime(path);
                    disposition.ModificationDate = File.GetLastWriteTime(path);
                    disposition.ReadDate = File.GetLastAccessTime(path);
                    _mailMessage.Attachments.Add(data);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
        }

        ///<summary>
        /// 邮件的发送
        ///</summary>
        public void Send()
        {
            try
            {
                if (_mailMessage != null)
                {
                    _smtpClient = new SmtpClient
                    {
                        Host = this._senderServerHost,
                        Port = this._senderPort,
                        UseDefaultCredentials = false,
                        EnableSsl = this._enableSsl
                    };
                    //_SmtpClient.Host = "smtp." + mMailMessage.From.Host;
                    if (this._enablePwdAuthentication)
                    {
                        System.Net.NetworkCredential nc = new System.Net.NetworkCredential(this._senderUsername, this._senderPassword);
                        //_SmtpClient.Credentials = new System.Net.NetworkCredential(this.mSenderUsername, this.mSenderPassword);
                        //NTLM: Secure Password Authentication in Microsoft Outlook Express
                        _smtpClient.Credentials = nc.GetCredential(_smtpClient.Host, _smtpClient.Port, "NTLM");
                    }
                    else
                    {
                        _smtpClient.Credentials = new System.Net.NetworkCredential(this._senderUsername, this._senderPassword);
                    }
                    _smtpClient.DeliveryMethod = SmtpDeliveryMethod.Network;
                    _smtpClient.Send(_mailMessage);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
        }
    }
}