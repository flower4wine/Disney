using System;

namespace CommunicationTest.DB
{
    public class DbException : Exception
    {
        public DbException(string message, Exception innerException)
            : base(message, innerException)
        {

        }
        public DbException(string message)
            : base(message)
        { }
        public DbException()
            :base()
        { }
    }
}
