package thrift;

import org.apache.thrift.TException;
import thrift.generated.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by kruczjak on 5/3/17.
 */
public class Common {
    public static void getPatient(HospitalService.Client client, BufferedReader inputReader) throws IOException, TException {
        System.out.println("Podaj id:");
        String id = inputReader.readLine();
        try {
            PatientStruct patient = client.getPatient(Integer.parseInt(id));

            System.out.println("ID: " + patient.identifier);
            System.out.println("Name: " + patient.name);
            System.out.println("Examinations");
            printExaminations(patient.examinations);
        } catch (InvalidPatientIdentifier e) {
            System.out.println(e);
        }
    }

    public static void printExaminations(List<ExaminationStruct> examinations) {
        for (ExaminationStruct examinationStruct : examinations) {
            System.out.println("> Examination on: " + examinationStruct.date);
            System.out.println("> Doctor: " + examinationStruct.doctorName);
            System.out.println("> Results:");
            for (ResultStruct resultStruct : examinationStruct.results) {
                System.out.println(">> " + resultStruct.name + " " + resultStruct.unit + " " + resultStruct.value);
            }
            System.out.println("< Examination END >");
        }
    }
}
