using System.Net;

namespace CommunicationTest
{
    public class WebHelper
    {
        public static int GetRequestStatusCode(string url)
        {
            HttpWebRequest webRequest = (HttpWebRequest)WebRequest.Create(url);
            webRequest.Method = @"GET";
            HttpWebResponse webResponse = (HttpWebResponse)webRequest.GetResponse();
            return (int) webResponse.StatusCode;
        }
    }
}