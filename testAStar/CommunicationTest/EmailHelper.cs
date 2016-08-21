using System;
using System.Net.Mail;
using System.Text;
using System.Net;

namespace CommunicationTest
{
    public static class EmailHelper
    {
        private static Encoding _encoding = Encoding.UTF8;

        /// <summary>
        /// 
        /// </summary>
        /// <param name="admin"></param>
        /// <param name="toMailAddress">
        /// <para>分号(;)风格用户  逗号(,)分割名字和地址</para>
        /// <para>eg: 张三,zhangsan@qq.com;李四,lisi@qq.com</para>
        /// </param>
        /// <param name="title"></param>
        /// <param name="body"></param>
        /// <returns></returns>
        public static string SendMail(MateAdmin admin,string toMailAddress,string title,string body)
        {
            string result = string.Empty;
            try
            {
                #region set message
                MailMessage message = new MailMessage();
                message.From= new MailAddress(admin.Email);

                string[] toEmailArry = toMailAddress.Split(';');

                foreach(string toEmail in toEmailArry)
                {
                    if(!string.IsNullOrWhiteSpace(toEmail))
                    {
                        string[] temp = toEmail.Split(',');
                        MailAddress toMail;
                        if (temp.Length==1)
                        {
                            toMail = new MailAddress(temp[0]);
                        }
                        else
                        {
                            toMail = new MailAddress(temp[1],temp[0]);
                        }
                        message.To.Insert(0, toMail);
                    }
                }

                message.BodyEncoding = _encoding;
                message.HeadersEncoding = _encoding;
                message.SubjectEncoding = _encoding;

                message.Subject = title;
                message.IsBodyHtml =true;
                message.Body = body;
                #endregion set message


                SmtpClient client = new SmtpClient(admin.Server, admin.Port);
                client.EnableSsl = admin.SslEnable;
                client.Credentials = new NetworkCredential(admin.UserName, admin.PassWord);
                client.Send(message);

            }
            catch (Exception ex)
            {
                result = ex.ToString();
                Console.WriteLine(ex.ToString());
            }
            return result;
        }

        public static string SendTestMail(MateAdmin admin)
        {
            return SendMail(admin, admin.Email, @"我的测试邮件", "我的测试邮件");
        }
    }
}