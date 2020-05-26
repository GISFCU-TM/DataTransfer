package tm.gis.datatransfer;

public interface HttpDataTransferResult<T extends Object>
{
    void taskFinish( T result ,int Code);
}
