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
        }

        this.connector.close();
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
                System.out.println("Podaj id:");
                String id = inputReader.readLine();
                PatientStruct patient = client.getPatient(Integer.parseInt(id));

                System.out.println("ID: " + patient.identifier);
                System.out.println("Name: " + patient.name);
                System.out.println("Examinations");
                for (ExaminationStruct examinationStruct : patient.examinations) {
                    System.out.println("> Examination on:" + examinationStruct.date);
                    System.out.println("> Doctor:" + examinationStruct.doctorName);
                    System.out.println("> Results:");
                    for (ResultStruct resultStruct : examinationStruct.results) {
                        System.out.println(">> " + resultStruct.name + resultStruct.unit + resultStruct.value);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new PatientClient().run();
    }
}
