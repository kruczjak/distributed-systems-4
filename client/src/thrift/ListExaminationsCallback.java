package thrift;

import org.apache.thrift.async.AsyncMethodCallback;
import thrift.generated.ExaminationStruct;

import java.util.List;

/**
 * Created by kruczjak on 5/3/17.
 */
public class ListExaminationsCallback implements AsyncMethodCallback<List<ExaminationStruct>> {
    @Override
    public void onComplete(List<ExaminationStruct> examinationStructs) {
        if (examinationStructs.isEmpty()) {
            System.out.println("No examinations found");
        } else {
            Common.printExaminations(examinationStructs);
        }
    }

    @Override
    public void onError(Exception e) {
        System.out.println("!!!Error while searching for examinations!!!");
    }
}
