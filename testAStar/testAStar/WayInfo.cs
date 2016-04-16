using System.Runtime.Serialization;

namespace testAStar
{
    [DataContract]
    public class WayInfo
    {
        [IgnoreDataMember]
        public string PicDir { set; get; }
        [DataMember(Name = "from")]
        public string From { set; get; }
        [DataMember(Name = "to")]
        public string To { set; get; }
        [IgnoreDataMember]
        public string PicName { set; get; }

        [DataMember(Name = "inner")]
        public string Inner
        {
            get
            {
                if (IsOut)
                {
                    return null;
                }
                return @"/inner/" + PicName;
            }
        }
        [IgnoreDataMember]
        public string InnerDirPath
        {
            get
            {
                if (IsOut)
                {
                    return null;

                }
                return @"/inner/";
            }
        }
        [DataMember(Name = "out")]
        public string Out
        {
            get
            {
                if (!IsOut)
                {
                    return null;
                }
                if (string.IsNullOrEmpty(From))
                {
                    return @"/out/" + PicName;
                }
                else
                {
                    return @"/out/"  + PicName;
                }
            }
        }
        [IgnoreDataMember]
        public string OutDirPath
        {
            get
            {
                if (!IsOut)
                {
                    return null;
                }
                if (string.IsNullOrEmpty(From))
                {
                    return @"/out/";
                }
                else
                {
                    return @"/out/";
                }
            }
        }
        [DataMember(Name = "time")]
        public int Time { set; get; }
        [DataMember(Name = "distance")]
        public int Distance { set; get; }

        [IgnoreDataMember]
        public bool IsOut { set; get; }

        public WayInfo Clone()
        {
            WayInfo newWayInfo = new WayInfo
            {
                From = this.From,
                To = this.To,
                PicName = this.PicName,
                Time = this.Time,
                Distance = this.Distance
            };

            return newWayInfo;
        }
    }
}