using System;
using System.Net;

namespace CommunicationTest
{
    public static class WebHelper
    {
        public static int GetRequestStatusCode(string url)
        {
            int result = 0;
            HttpWebRequest webRequest = (HttpWebRequest)WebRequest.Create(url);
            webRequest.Timeout = 5000;
            webRequest.Method = @"GET";
            HttpWebResponse webResponse = null;
            try
            {
                webResponse = (HttpWebResponse)webRequest.GetResponse();
                result = (int)webResponse.StatusCode;
            }
            catch (WebException ex)
            {
                result = (int)ex.Status;
            }
            catch (Exception ex)
            {
                result = 16;
            }
            finally
            {
                webResponse.Close();
            }
            return result;
        }
    }
}