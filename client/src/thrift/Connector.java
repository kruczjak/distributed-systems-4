package thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import thrift.generated.HospitalService;

/**
 * Created by kruczjak on 5/2/17.
 */

public class Connector {
    private static final int PORT = 9090;
    private TTransport transport;

    public Connector() {
        this.transport = new TSocket("localhost", PORT);
    }

    public void open() throws TTransportException {
        this.transport.open();
    }

    public HospitalService.Client initializeClient() {
        TProtocol protocol = new TBinaryProtocol(transport);
        return new HospitalService.Client(protocol);
    }

    public void close() {
        this.transport.close();
    }
}
