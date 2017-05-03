package thrift;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import thrift.generated.HospitalService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by kruczjak on 5/2/17.
 */
public class HospitalWorkerClient {
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
            System.out.println("1. Dodaj wynik");

            String input = inputReader.readLine();

            if (input.startsWith("q")) {
                break;
            } else if (input.startsWith("1")) {
                System.out.println("Podaj id badania:");
                String examId = inputReader.readLine();
                System.out.println("Podaj nazwe wyniku:");
                String name = inputReader.readLine();
                System.out.println("Podaj typ wyniku:");
                String unit = inputReader.readLine();
                System.out.println("Podaj wartość:");
                String value = inputReader.readLine();
                int resultId = client.addResult(Integer.parseInt(examId), name, unit, value);
                System.out.println("Stworzono wynik z id: " + resultId);
            }
        }
    }

    public static void main(String[] args) {
        new HospitalWorkerClient().run();
    }
}
