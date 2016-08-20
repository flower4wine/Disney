using System;
using System.Collections.Generic;

namespace CommunicationTest
{

    public class MateAdmin
    {
        public string Server { set; get; }
        public string Email { set; get; }
        public string UserName { set; get; }
        public string PassWord { set; get; }
        public int Port { set; get; }
        public bool SslEnable { set; get; }
        public TimeSpan DetectionInterval { set; get; }
    }
    public class MateBase
    {
        public MateBase()
        {
            this.Id = -1;
        }
        public int Id { set; get; }
    }
    public class MateWeb: MateBase
    {
        public MateWeb()
            : base()
        {
            NoticeUsers = new List<MateNoticeUser>();
            ErrorLogs = new List<MateErrorLog>();
        }
        public string WebName { set; get; }
        public string WebUrl { set; get; }

        public TimeSpan DetectionInterval { set; get; }
        public DateTime LastCommunicationTime { set; get; }

        public List<MateNoticeUser> NoticeUsers { set; get; }
        public List<MateErrorLog> ErrorLogs { set; get; }
    }

    public class MateNoticeUser : MateBase
    {
        public int WebId { set; get; }
        public string EmailAddress { set; get; }
    }

    public class MateErrorLog
    {
        public int WebId { set; get; }
        public string StateCode { set; get; }
        public DateTime ErrorTime { set; get; }
        public TimeSpan DetectionInterval { set; get; }
    }
}