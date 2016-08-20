namespace CommunicationTest.DB.Interface
{
    interface ISqlHelperBase
    {
        void CloseCon();
        void CloseTransaction();
        void OpenTransaction();
    }
}
