package thrift;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import thrift.generated.HospitalService;
import thrift.generated.PatientStruct;

import javax.print.Doc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by kruczjak on 5/2/17.
 */
public class DoctorClient {
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
        }

        this.connector.close();
    }

    private void perform(HospitalService.Client client) throws IOException, TException {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("------------------------------------------");
            System.out.println("Wpisz q, aby wyjść.");
            System.out.println("1. Utwórz pacjenta");
            System.out.println("2. Pobierz pacjenta");
            System.out.println("3. Utwórz badanie");

            String input = inputReader.readLine();

            if (input.startsWith("q")) {
                break;
            } else if (input.startsWith("1")) {
                System.out.println("Podaj imię i nazwisko:");
                String name = inputReader.readLine();
                int patientId = client.createPatient(name);
                System.out.println("Stworzono pacjenta z id: " + patientId);
            } else if (input.startsWith("2")) {
                Common.getPatient(client, inputReader);
            } else if (input.startsWith("3")) {
                System.out.println("Podaj identyfikator pacjenta:");
                String patientId = inputReader.readLine();
                System.out.println("Podaj swoje nazwisko:");
                String doctorName = inputReader.readLine();
                int examinationId = client.addExamination(Integer.parseInt(patientId), null, doctorName);
                System.out.println("Stworzono badanie z id: " + examinationId);
            }
        }
    }

    public static void main(String[] args) {
        new DoctorClient().run();
    }
}
