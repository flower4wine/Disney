using System;
using System.Collections.Specialized;
using System.IO;
using System.Runtime.InteropServices;
using System.Text;
namespace BIMA.Foundation.Ini
{
	public class IniWriteReader
    {
        #region API函数声明
        [DllImport("kernel32")]
        private static extern long WritePrivateProfileString(string section, string key,
            byte[] val, string filePath);
        [DllImport("kernel32")]
        private static extern int GetPrivateProfileString(string section, string key,
            string def, byte[] retVal, int size, string filePath);
        #endregion API函数声明

        public static string IniFilePath = AppDomain.CurrentDomain.BaseDirectory + "Config.ini";

        #region 读Ini文件
        public static StringCollection ReadIniData(string Section, string iniFilePath = "")
        {
            if (!string.IsNullOrEmpty(iniFilePath))
            {
                IniFilePath = iniFilePath;
            }
            if (File.Exists(IniFilePath))
            {
                Byte[] buffer = new Byte[16384];
                int bufLen = GetPrivateProfileString(Section, null, null, buffer, 1024, IniFilePath);

                return GetStringsFromBuffer(buffer, bufLen);
            }
            else
            {
                return new StringCollection() { "" };
            }
        }
		public static string ReadIniData(string Section, string Key, string NoText="", string iniFilePath="")
		{
            if (!string.IsNullOrEmpty(iniFilePath))
            {
                IniFilePath = iniFilePath;
            }
            if (File.Exists(IniFilePath))
            {
                Byte[] buffer = new Byte[16384];
                int length = GetPrivateProfileString(Section, Key, NoText, buffer, 1024, iniFilePath);
                return Encoding.UTF8.GetString(buffer, 0, length);
			}
			return NoText;
		}
        private static StringCollection GetStringsFromBuffer(Byte[] buffer, int bufLen)
        {
            StringCollection strings = new StringCollection();
            if (bufLen != 0)
            {
                int start = 0;
                for (int i = 0; i < bufLen; i++)
                {
                    if ((buffer[i] == 0) && ((i - start) > 0))
                    {
                        String s = Encoding.UTF8.GetString(buffer, start, i - start);
                        strings.Add(s);
                        start = i + 1;
                    }
                }
            }
            return strings;
        }

        #endregion 读Ini文件

        #region 写Ini文件
        public static bool WriteIniData(string section, string key, string value, string iniFilePath = null)
        {
            if (string.IsNullOrEmpty(section) || string.IsNullOrEmpty(key))
            {
                return false;
            }
            if (!string.IsNullOrEmpty(iniFilePath))
            {
                IniFilePath = iniFilePath;
            }
            if (string.IsNullOrEmpty(value))
            {
                value = string.Empty;
            }

            if (!File.Exists(IniFilePath))
            {
                FileStream fs1 = new FileStream(IniFilePath, FileMode.Create);
                StreamWriter sw = new StreamWriter(fs1, Encoding.UTF8);
                sw.Close();
                fs1.Close();
            }
            byte[] bytes = System.Text.Encoding.UTF8.GetBytes(value.ToString());
            long OpStation = WritePrivateProfileString(section, key, bytes, IniFilePath);
            return OpStation != 0L;
		}

        public static bool CreatConfigIni()
        {
            if (!File.Exists(IniFilePath))
            {
                FileStream fs1 = new FileStream(IniFilePath, FileMode.Create);
                StreamWriter sw = new StreamWriter(fs1, Encoding.UTF8);
                sw.Close();
                fs1.Close();
            }
            return true;
        }
        #endregion 写Ini文件
	}
}
