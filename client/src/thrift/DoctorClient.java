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
    private final AsyncConnector asyncConnector = new AsyncConnector();

    private void run() {
        try {
            this.connector.open();
            this.asyncConnector.open();
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.perform(this.connector.initializeClient(), this.asyncConnector.initializeClient());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TException e) {
            System.out.println("Thrift exception catched");
            e.printStackTrace();
        } finally {
            this.connector.close();
            this.asyncConnector.close();
        }
    }

    private void perform(HospitalService.Client client, HospitalService.AsyncClient asyncClient) throws IOException, TException {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("------------------------------------------");
            System.out.println("Wpisz q, aby wyjść.");
            System.out.println("1. Utwórz pacjenta");
            System.out.println("2. Pobierz pacjenta");
            System.out.println("3. Utwórz badanie");
            System.out.println("4. Wyszukaj badanie");

            String input = inputReader.readLine();

            if (input.startsWith("q")) {
                break;
            } else if (input.startsWith("1")) {
                createPatient(client, inputReader);
            } else if (input.startsWith("2")) {
                Common.getPatient(client, inputReader);
            } else if (input.startsWith("3")) {
                createExamination(client, inputReader);
            } else if (input.startsWith("4")) {
                searchExamination(asyncClient, inputReader);
            }
        }
    }


    private void createPatient(HospitalService.Client client, BufferedReader inputReader) throws IOException, TException {
        System.out.println("Podaj imię i nazwisko:");
        String name = inputReader.readLine();
        int patientId = client.createPatient(name);
        System.out.println("Stworzono pacjenta z id: " + patientId);
    }

    private void createExamination(HospitalService.Client client, BufferedReader inputReader) throws IOException, TException {
        System.out.println("Podaj identyfikator pacjenta:");
        String patientId = inputReader.readLine();
        System.out.println("Podaj swoje nazwisko:");
        String doctorName = inputReader.readLine();
        int examinationId = client.addExamination(Integer.parseInt(patientId), null, doctorName);
        System.out.println("Stworzono badanie z id: " + examinationId);
    }

    private void searchExamination(HospitalService.AsyncClient asyncClient, BufferedReader inputReader) throws IOException, TException {
        System.out.println("Podaj kryterium:");
        String criterium = inputReader.readLine();
        asyncClient.listExaminations(criterium, new ListExaminationsCallback());
        System.out.println("Searching for " + criterium + "...");
    }

    public static void main(String[] args) {
        new DoctorClient().run();
    }
}
