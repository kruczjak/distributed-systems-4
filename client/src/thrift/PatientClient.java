package thrift;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import thrift.generated.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by kruczjak on 5/2/17.
 */
public class PatientClient {
    private final Connector connector = new Connector();

    private void run() {
        try {
            this.connector.open();
        } catch (TTransportException e) {
            e.printStackTrace();
        }

        try {
            this.perform(this.connector.initializeClient());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TException e) {
            System.out.println("Thrift exception catched");
            e.printStackTrace();
        } finally {
            this.connector.close();
        }
    }

    private void perform(HospitalService.Client client) throws IOException, TException {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("------------------------------------------");
            System.out.println("Wpisz q, aby wyjść.");
            System.out.println("1. Pobierz pacjenta");

            String input = inputReader.readLine();

            if (input.startsWith("q")) {
                break;
            } else if (input.startsWith("1")) {
                Common.getPatient(client, inputReader);
            }
        }
    }

    public static void main(String[] args) {
        new PatientClient().run();
    }
}
