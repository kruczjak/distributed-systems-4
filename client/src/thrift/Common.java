package thrift;

import org.apache.thrift.TException;
import thrift.generated.ExaminationStruct;
import thrift.generated.HospitalService;
import thrift.generated.PatientStruct;
import thrift.generated.ResultStruct;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by kruczjak on 5/3/17.
 */
public class Common {
    public static void getPatient(HospitalService.Client client, BufferedReader inputReader) throws IOException, TException {
        System.out.println("Podaj id:");
        String id = inputReader.readLine();
        PatientStruct patient = client.getPatient(Integer.parseInt(id));

        System.out.println("ID: " + patient.identifier);
        System.out.println("Name: " + patient.name);
        System.out.println("Examinations");
        for (ExaminationStruct examinationStruct : patient.examinations) {
            System.out.println("> Examination on: " + examinationStruct.date);
            System.out.println("> Doctor: " + examinationStruct.doctorName);
            System.out.println("> Results:");
            for (ResultStruct resultStruct : examinationStruct.results) {
                System.out.println(">> " + resultStruct.name + resultStruct.unit + resultStruct.value);
            }
            System.out.println("< Examination END >");
        }
    }
}
