using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;
using System.Data.SQLite;
using System.Diagnostics;
using System.Linq;
using System.Text;


namespace CommunicationTest.DB.Sqlite
{
    public class SqliteCommunicationLogHelper : SqliteHelperBase
    {
        public SqliteCommunicationLogHelper(string dbPath)
            : base(dbPath)
        {
            OpenCon();

            using (SQLiteCommand newCmd = new SQLiteCommand(_conn))
            {
                List<string> hadTables = GetTableNames();
                StringBuilder createTablesSql = new StringBuilder();

                #region 建表

                #region Web / 检测的网站

                if (!hadTables.Contains(@"Web"))
                {
                    createTablesSql.Append(@"CREATE TABLE [Web](
    [Id] INTEGER PRIMARY KEY AUTOINCREMENT, 
    [WebName] VARCHAR(100), 
    [WebUrl] TEXT, 
    [DetectionInterval] TIMESTAMP, 
    [LastCommunicationTime] DATETIME);");
                }
                // wacom本子方向 0 正竖 90 右转90度， 180 倒 270 左转90度， 
                #endregion

                #region NoticeUser / 通知的用户

                if (!hadTables.Contains(@"NoticeUser"))
                {
                    createTablesSql.Append(@"CREATE TABLE [NoticeUser](
    [Id] INTEGER PRIMARY KEY AUTOINCREMENT, 
    [WebId] INTEGER, 
    [EmailAddress] TEXT NOT NULL);");
                }

                #endregion

                #region NoticeUser / 错误日志

                if (!hadTables.Contains(@"NoticeUser"))
                {
                    createTablesSql.Append(@"CREATE TABLE [ErrorLog](
    [WebId] INTEGER, 
    [StateCode] TEXT, 
    [ErrorTime] DATETIME, 
    [DetectionInterval] TIMESTAMP);");
                }

                #endregion

                #region Admin / 发件箱设置

                if (!hadTables.Contains(@"Admin"))
                {
                    createTablesSql.Append(@"CREATE TABLE [Admin](
    [Server] TEXT, 
    [FromMail] TEXT, 
    [UserName] TEXT, 
    [PassWord] TEXT, 
    [Port] INT, 
    [SslEnable] BOOLEAN, 
    [DetectionInterval] TIMESTAMP);");
                }

                #endregion
                #endregion

                newCmd.CommandText = createTablesSql.ToString();
                newCmd.ExecuteNonQuery();
                newCmd.Parameters.Clear();

                #region 修改表结构
                /*
                // 修改表结构
                StringBuilder updateDataSql = new StringBuilder();
                StringBuilder alterTablesSql = new StringBuilder();
                HashSet<string> columns = null;
                Dictionary<string, bool> columnsExist = null;

                #region workbook

                columns = new HashSet<string>()
                {
                    "grade_name",
                    "server_subject_id",
                    "server_grade_id"
                };
                columnsExist = ColumnIsExist("workbook", columns);
                if (!columnsExist["grade_name"])
                {
                    alterTablesSql.Append(@"ALTER TABLE `workbook` ADD COLUMN [grade_name] VARCHAR(100);");
                }
                if (!columnsExist["server_subject_id"])
                {
                    alterTablesSql.Append(@"ALTER TABLE `workbook` ADD COLUMN [server_subject_id] INTEGER;");
                }
                if (!columnsExist["server_grade_id"])
                {
                    alterTablesSql.Append(@"ALTER TABLE `workbook` ADD COLUMN [server_grade_id] INTEGER;");
                }
                #endregion workbook

                if (alterTablesSql.Length > 0)
                {
                    newCmd.CommandText = alterTablesSql.ToString();
                    newCmd.ExecuteNonQuery();
                    newCmd.Parameters.Clear();
                }

                // 因结构变更导致的更新数据
                if (updateDataSql.Length > 0)
                {
                    newCmd.CommandText = updateDataSql.ToString();
                    newCmd.ExecuteNonQuery();
                    newCmd.Parameters.Clear();
                }
                */
                #endregion
            }
            CloseCon();
        }

        public void SaveAdmin(MateAdmin admin)
        {
            OpenCon();

            string sql = "DELETE FROM [Admin]; "
                + "INSERT INTO [Admin] ([Server],[FromMail],[UserName],[PassWord],[Port],[SslEnable],[DetectionInterval] ) "
                + " VALUES (@Server,@FromMail,@UserName,@PassWord,@Port,@SslEnable,@DetectionInterval);";
            _cmd = new SQLiteCommand(_conn);
            _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@Server", DbType = DbType.String, Value = admin.Server });
            _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@FromMail", DbType = DbType.String, Value = admin.Email });
            _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@UserName", DbType = DbType.String, Value = admin.UserName });
            _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@PassWord", DbType = DbType.String, Value = admin.PassWord });
            _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@Port", DbType = DbType.Int32, Value = admin.Port });
            _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@SslEnable", DbType = DbType.Boolean, Value = admin.SslEnable });
            _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@DetectionInterval", DbType = DbType.Object, Value = admin.DetectionInterval });

            _cmd.CommandText = sql;
            _cmd.ExecuteNonQuery();
        }
        public MateAdmin GetAdmin()
        {
            MateAdmin result = null;
            OpenCon();

            string sql = "SELECT * FROM [Admin]";

            _cmd = new SQLiteCommand(_conn);
            _cmd.CommandText = sql;

            SQLiteDataReader read = _cmd.ExecuteReader();

            while (read.Read())
            {
                result = new MateAdmin()
                {
                    Server = ObjToString(read["Server"]),
                    Email = ObjToString(read["FromMail"]),
                    UserName = ObjToString(read["UserName"]),
                    PassWord = ObjToString(read["PassWord"]),
                    Port = int.Parse(ObjToString(read["Port"])),
                    SslEnable = bool.Parse(ObjToString(read["SslEnable"])),
                    DetectionInterval = TimeSpan.Parse(ObjToString(read["DetectionInterval"]))
                };
            }
            return result; 
        }

        public void AddErrorLogs(List<MateErrorLog> errorLogs)
        {
            
            OpenTransaction();

            foreach (var errorLog in errorLogs)
            {
                AddErrorLog(errorLog);
            }
            CloseTransaction();
        }

        public void AddErrorLog(MateErrorLog errorLog)
        {
            OpenCon();

            string sql = "INSERT INTO [ErrorLog] ([WebId],[StateCode],[ErrorTime],[DetectionInterval]) "
                + " VALUES (@WebId,@StateCode,@ErrorTime,@DetectionInterval);";
            _cmd = new SQLiteCommand(_conn);
            _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@WebId", DbType = DbType.String, Value = errorLog.WebId });
            _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@StateCode", DbType = DbType.String, Value = errorLog.StateCode });
            _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@ErrorTime", DbType = DbType.DateTime, Value = errorLog.ErrorTime });
            _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@DetectionInterval", DbType = DbType.Object, Value = errorLog.DetectionInterval });

            _cmd.CommandText = sql;
            _cmd.ExecuteNonQuery();
        }

        public IEnumerable<MateWeb> GetAllErrorLogs()
        {
            MateWeb[] result = GetWebs().ToArray();

            for (int i=0;i< result.Length; i++)
            {
                result[i] = GetWebErrorLogs(result[i]);
            }

            return result;
        }

        public MateWeb GetWebErrorLogs(MateWeb mateWeb)
        {
            OpenCon();
            string sql = "SELECT * FROM [ErrorLog] WHERE [WebId] = @WebId ";

            _cmd = new SQLiteCommand(_conn)
            {
                CommandText = sql
            };
            _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@WebId", DbType = DbType.String, Value = mateWeb.Id });

            SQLiteDataReader read = _cmd.ExecuteReader();

            while (read.Read())
            {
                mateWeb.ErrorLogs.Add(SetErrorLog(read));
            }

            return mateWeb;
        }

        private MateErrorLog SetErrorLog(DbDataReader read)
        {
            return new MateErrorLog()
            {
                WebId = int.Parse(ObjToString(read["WebId"])),
                StateCode = ObjToString(read["StateCode"]),
                ErrorTime = DateTime.Parse(ObjToString(read["ErrorTime"])),
                DetectionInterval = TimeSpan.Parse(ObjToString(read["DetectionInterval"]))
            };
        }

        public IEnumerable<MateWeb> GetWebs(bool isGetNoticeUser = false)
        {
            List<MateWeb> result = new List<MateWeb>();

            OpenCon();
            string sql = "SELECT * FROM [Web] ";

            _cmd = new SQLiteCommand(_conn)
            {
                CommandText = sql
            };

            SQLiteDataReader read = _cmd.ExecuteReader();

            while (read.Read())
            {
                result.Add(SetMateWeb(read, isGetNoticeUser));
            }

            return result;
        }

        private MateWeb SetMateWeb(DbDataReader read, bool isWithNoticeUser)
        {
            MateWeb result = new MateWeb()
            {
                Id = int.Parse(ObjToString(read["Id"])),
                WebName = ObjToString(read["WebName"]),
                WebUrl = ObjToString(read["WebUrl"]),
                DetectionInterval = TimeSpan.Parse(ObjToString(read["DetectionInterval"])),
                LastCommunicationTime = DateTime.Parse(ObjToString(read["LastCommunicationTime"]))
            };

            if (isWithNoticeUser)
            {
                result.NoticeUsers.AddRange(GetNoticeUsers(result));
            }

            return result;
        }

        private IEnumerable<MateNoticeUser> GetNoticeUsers(MateWeb mateWeb)
        {
            List<MateNoticeUser> result = new List<MateNoticeUser>();

            OpenCon();
            string sql = "SELECT * FROM [NoticeUser] WHERE [WebId] = @WebId ";

            _cmd = new SQLiteCommand(_conn)
            {
                CommandText = sql
            };
            _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@WebId", DbType = DbType.String, Value = mateWeb.Id });

            SQLiteDataReader read = _cmd.ExecuteReader();

            while (read.Read())
            {
                result.Add(SetMateNoticeUser(read));
            }
            return result;
        }

        private MateNoticeUser SetMateNoticeUser(DbDataReader read)
        {
            return new MateNoticeUser()
            {
                Id = int.Parse(ObjToString(read["Id"])),
                EmailAddress = ObjToString(read["EmailAddress"]),
                WebId = int.Parse(ObjToString(read["WebId"]))
            };
        }

        public int SaveWeb(MateWeb mateWeb, bool isSaveNoticeUser = false)
        {
            if (string.IsNullOrWhiteSpace(mateWeb.WebUrl))
            {
                throw new DbException(@"网站URL不存在");
            }

            int result = GetWebId(mateWeb.WebUrl);
            if (result < 0)
            {
                InsertWeb(mateWeb);
                result = GetWebId(mateWeb.WebUrl);
            }
            else
            {
                UpdataWeb(mateWeb);
            }
            if (isSaveNoticeUser)
            {
                mateWeb.Id = result;
                DeleteNoticeUser(mateWeb);

                foreach (MateNoticeUser noticeUser in mateWeb.NoticeUsers)
                {
                    OpenTransaction();
                    InsertNoticeUser(mateWeb.Id, noticeUser);
                    CloseTransaction();
                }
            }
            return result;
        }

        private void InsertNoticeUser(int webId ,MateNoticeUser noticeUser)
        {
            try
            {
                OpenCon();

                string sql = "INSERT INTO [NoticeUser] ([WebId],[UserEmailAddress] ) "
                    + " VALUES (@WebId,@UserEmailAddress);";
                _cmd = new SQLiteCommand(_conn);
                _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@WebId", DbType = DbType.String, Value = webId });
                _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@UserEmailAddress", DbType = DbType.String, Value = noticeUser.EmailAddress });

                _cmd.CommandText = sql;
                _cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                Debug.WriteLine("插入用户失败 : {0}  \t{1}", noticeUser.EmailAddress, ex);
            }
        }

        private void DeleteNoticeUser(MateWeb mateWeb)
        {
            try
            {
                OpenCon();
                string sql = "DELETE FROM [NoticeUser] WHERE [WebId] = @WebId";

                _cmd = new SQLiteCommand(_conn);
                _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@WebId", DbType = DbType.Int32, Value = mateWeb.Id });
                _cmd.CommandText = sql;
                _cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                Debug.WriteLine("删除监听网站通知用户失败 : {0} \t{1}", mateWeb.WebUrl, ex);
            }
        }

        /// <summary>
        /// 最后监听时间为 DateTime.MinValue
        /// </summary>
        /// <param name="mateWeb"></param>
        /// <returns></returns>
        private void InsertWeb(MateWeb mateWeb)
        {
            try
            {
                OpenCon();

                string sql = "INSERT INTO [Web] ([WebName],[WebUrl] ,[DetectionInterval],[LastCommunicationTime]) "
                    + " VALUES (@WebName,@WebUrl ,@DetectionInterval,@LastCommunicationTime);";
                _cmd = new SQLiteCommand(_conn);
                _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@WebName", DbType = DbType.String, Value = mateWeb.WebName });
                _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@WebUrl", DbType = DbType.String, Value = mateWeb.WebUrl });
                _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@DetectionInterval", DbType = DbType.Object, Value = mateWeb.DetectionInterval });
                _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@LastCommunicationTime", DbType = DbType.DateTime, Value = DateTime.MinValue });

                _cmd.CommandText = sql;
                _cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                Debug.WriteLine("保存监听网站失败 : {0}  \t{1}", mateWeb.WebUrl, ex);
            }
            return;
        }

        /// <summary>
        /// 只更新网站名、网站监听间隔
        /// </summary>
        /// <param name="mateWeb"></param>
        private void UpdataWeb(MateWeb mateWeb)
        {
            try
            {
                OpenCon();
                string sql = "UPDATE [Web] SET [WebName] = @WebName , [DetectionInterval] = @DetectionInterval WHERE [Id] = @Id";

                _cmd = new SQLiteCommand(_conn);
                _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@Id", DbType = DbType.Int32, Value = mateWeb.Id });
                _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@WebName", DbType = DbType.String, Value = mateWeb.WebName });
                _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@DetectionInterval", DbType = DbType.Object, Value = mateWeb.DetectionInterval });
                _cmd.CommandText = sql;
                _cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                Debug.WriteLine("更新监听网站数据失败 : {0} \t{1}", mateWeb.WebUrl, ex);
            }
        }

        public int GetWebId(string webUrl)
        {
            int result = -1;
            try
            {
                OpenCon();
                string sql = "SELECT [Id] FROM [Web] WHERE [WebUrl]=@WebUrl";

                _cmd = new SQLiteCommand(_conn);
                _cmd.Parameters.Add(new SQLiteParameter { ParameterName = "@WebUrl", DbType = DbType.String, Value = webUrl });
                _cmd.CommandText = sql;

                SQLiteDataReader read = _cmd.ExecuteReader();

                while (read.Read())
                {
                    int.TryParse(read[0].ToString(), out result);
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine("获取监听网站Id失败 : {0} \t{1}", webUrl, ex);
            }
            return result;
        }
    }
}