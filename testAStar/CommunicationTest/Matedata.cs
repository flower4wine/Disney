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
        public long DetectionInterval { set; get; }
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

        public long DetectionInterval { set; get; }
        public DateTime LastCommunicationTime { set; get; }
        public string LastStateCode { set; get; }

        public List<MateNoticeUser> NoticeUsers { set; get; }
        public List<MateErrorLog> ErrorLogs { set; get; }

        public override bool Equals(object obj)
        {
            if (obj == null)
                return false;
            if(obj is MateWeb)
            {
                return this.WebUrl.Equals((obj as MateWeb).WebUrl);
            }
            else
            {
                return false;
            }
        }

        public override int GetHashCode()
        {
            return this.WebUrl.GetHashCode();
        }

        public override string ToString()
        {
            return this.WebUrl;
        }

        public string ToMailAddress
        {
            get
            {
                string result = string.Empty;

                foreach(MateNoticeUser user in NoticeUsers)
                {
                    result += user.EmailAddress + ";";
                }

                return result;
            }
        }
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
        public long DetectionInterval { set; get; }
    }
}