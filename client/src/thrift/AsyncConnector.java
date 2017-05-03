package thrift;

import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import thrift.generated.HospitalService;

import java.io.IOException;

/**
 * Created by kruczjak on 5/3/17.
 */
public class AsyncConnector {
    private static final int PORT = 9091;
    private TNonblockingTransport tNonblockingTransport;
    private TAsyncClientManager tAsyncClientManager;

    public void open() throws IOException {
         this.tNonblockingTransport = new TNonblockingSocket("localhost", PORT);
         this.tAsyncClientManager = new TAsyncClientManager();
    }

    public HospitalService.AsyncClient initializeClient() {
        TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
        return new HospitalService.AsyncClient(protocolFactory, this.tAsyncClientManager, this.tNonblockingTransport);
    }

    public void close() {
        this.tNonblockingTransport.close();
    }
}
