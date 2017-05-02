struct Patient {
    1:i32 identifier,
    2:string name,
    3:list<Examination> examinations,
}

struct Examination {
    1:Patient patient,
    2:string date,
    3:string doctorName,
    4:list<Result> results,
}

struct Result {
    1:string unit,
    2:string value,
}

exception InvalidPatientIdentifier {
   1: string why,
}

service HospitalService {
    Patient getPatient(1:i32 identifier) throws (1:InvalidPatientIdentifier error),
    list<Examination> listExaminations(1:string name),
}
