namespace java thrift.generated

struct ResultStruct {
    1:string name,
    2:string unit,
    3:string value,
}

struct ExaminationStruct {
    1:string date,
    2:string doctorName,
    3:list<ResultStruct> results,
}

struct PatientStruct {
    1:i32 identifier,
    2:string name,
    3:list<ExaminationStruct> examinations,
}

exception InvalidPatientIdentifier {
   1: string why,
}

service HospitalService {
    i32 createPatient(1:string name),
    PatientStruct getPatient(1:i32 identifier) throws (1:InvalidPatientIdentifier error),
    list<ExaminationStruct> listExaminations(1:string name),
}
