using System;
using System.Data;
using System.Data.SQLite;
using System.Diagnostics;
using System.IO;
using CommunicationTest.DB.Interface;
using System.Collections.Generic;

namespace CommunicationTest.DB.Sqlite
{
    public class SqliteHelperBase : ISqlHelperBase
    {
        protected SQLiteConnection _conn;
        protected SQLiteCommand _cmd;
        protected SQLiteTransaction _tran;
        public SqliteHelperBase(string dbPath)
        {
            if (string.IsNullOrWhiteSpace(dbPath))
                throw new DbException("dbPath is empty");

            try
            {
                if (!File.Exists(dbPath))
                {
                    SQLiteConnection.CreateFile(dbPath);
                }
            }
            catch (Exception)
            {
                throw new DbException("dbPath is illegal");
            }

            _conn = new SQLiteConnection(string.Format("Data Source={0};Pooling=true;FailIfMissing=false", dbPath));
        }

        protected void OpenCon()
        {
            if (_conn.State == ConnectionState.Closed)
            {
                _conn.Open();
            }
        }

        public void CloseCon()
        {
            if (_conn.State != ConnectionState.Closed)
            {
                CloseTransaction();
                _conn.Close();
            }
        }
        public void CloseTransaction()
        {
            try
            {
                if (_tran != null)
                {
                    _tran.Commit();
                    _tran.Dispose();
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.Message);
                if (_tran != null)
                    _tran.Dispose();
            }
            finally
            {
                _tran = null;
            }
        }
        public void OpenTransaction()
        {
            if (_tran == null)
            {
                OpenCon();
                _tran = _conn.BeginTransaction();
            }
        }

        protected DataTable ExecuteDatatable(string cmdText)
        {
            return ExecuteDatatable(cmdText, new List<SQLiteParameter>());
        }

        protected List<SQLiteParameter> GetParametersList(Dictionary<string, object> dicParameters)
        {
            List<SQLiteParameter> lst = new List<SQLiteParameter>();
            if (dicParameters != null)
            {
                foreach (KeyValuePair<string, object> kv in dicParameters)
                {
                    lst.Add(new SQLiteParameter(kv.Key, kv.Value));
                }
            }
            return lst;
        }
        public DataTable ExecuteDatatable(string cmdText, Dictionary<string, object> dicParameters)
        {
            List<SQLiteParameter> lst = GetParametersList(dicParameters);
            return ExecuteDatatable(cmdText, lst);
        }

        protected DataTable ExecuteDatatable(string cmdText, IEnumerable<SQLiteParameter> parameters)
        {
            using (SQLiteCommand cmd = new SQLiteCommand())
            {
                cmd.Connection = _conn;
                cmd.CommandText = cmdText;
                if (parameters != null)
                {
                    foreach (var param in parameters)
                    {
                        cmd.Parameters.Add(param);
                    }
                }
                SQLiteDataAdapter da = new SQLiteDataAdapter(cmd);
                DataTable dt = new DataTable();
                da.Fill(dt);
                return dt;
            }
        }
        protected DataTable GetColumnStatus(string tableName)
        {
            return ExecuteDatatable(string.Format("PRAGMA table_info(`{0}`);", tableName));
        }

        /// <summary>
        /// 检查指定表里的字段是否存在
        /// </summary>
        /// <param name="tableName"></param>
        /// <param name="columnName"></param>
        /// <returns></returns>
        protected bool ColumnIsExist(string tableName, string columnName)
        {
            bool ret = false;
            if (string.IsNullOrWhiteSpace(tableName)
                || string.IsNullOrWhiteSpace(columnName))
            {
                return false;
            }

            DataTable dt = GetColumnStatus(tableName);
            if (dt == null || dt.Rows.Count == 0)
            {
                return false;
            }

            for (int i = 0; i < dt.Rows.Count; i++)
            {
                object colName = GetDataFieldValue(dt.Rows[i], "name");
                string name = string.Empty;
                if (colName != null)
                {
                    name = colName.ToString();
                }

                if (columnName == name)
                {
                    ret = true;
                    break;
                }
            }

            return ret;
        }

        /// <summary>
        /// 检查指定表里的多个字段是否存在，返回的key=字段名，value=是否存在
        /// </summary>
        /// <param name="tableName"></param>
        /// <param name="columnNames"></param>
        /// <returns></returns>
        protected Dictionary<string, bool> ColumnIsExist(string tableName, HashSet<string> columnNames)
        {
            Dictionary<string, bool> ret = new Dictionary<string, bool>();

            if (string.IsNullOrWhiteSpace(tableName)
                || columnNames == null
                || columnNames.Count == 0)
            {
                return ret;
            }

            // 全部置为不存在
            foreach (string item in columnNames)
            {
                ret.Add(item, false);
            }

            DataTable dt = GetColumnStatus(tableName);
            if (dt == null || dt.Rows.Count == 0)
            {
                return ret;
            }

            for (int i = 0; i < dt.Rows.Count; i++)
            {
                object colName = GetDataFieldValue(dt.Rows[i], "name");
                if (colName != null)
                {
                    var name = colName.ToString();
                    if (ret.ContainsKey(name))
                    {
                        ret[name] = true;
                    }
                }
            }

            return ret;
        }

        protected static object GetDataFieldValue(DataRow dataRow, string fieldName)
        {
            object obj = null;
            if (dataRow.Table.Columns.Contains(fieldName))
            {
                obj = dataRow[fieldName];
            }
            else
            {
                Debug.WriteLine(string.Format("GetDataFieldValue column {0} is not exit", fieldName));
            }

            return obj;
        }

        protected string ObjToString(object obj)
        {
            return obj == null ? string.Empty : obj.ToString();
        }

        protected List<string> GetTableNames()
        {
            List<string> result = new List<string>();
            OpenCon();
            string sql = "SELECT [name] FROM sqlite_master WHERE [type]='table';";

            _cmd = new SQLiteCommand(_conn)
            {
                CommandText = sql
            };

            SQLiteDataReader read = _cmd.ExecuteReader();

            while (read.Read())
            {
                result.Add(read[0].ToString());
            }

            return result;
        } 
    }
}