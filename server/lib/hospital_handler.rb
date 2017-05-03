class HospitalHandler
  def getPatient(id)
    puts "Getting patient with id:#{id}"
    patient = find_patient(id).to_struct
    puts "Patient #{patient.inspect} fetched"
    patient
  end

  def createPatient(name)
    Patient.create!(name: name).id
  end

  def addExamination(patient_id, date, doctor_name)
    Examination.create!(
      patient_id: find_patient(patient_id, false).id,
      created_at: date || Time.current,
      doctor_name: doctor_name
    ).id
  end

  def addResult(exam_id, name, unit, value)
    Result.create!(examination_id: exam_id, name: name, unit: unit, value: value).id
  end

  private

  def find_patient(id, include = true)
    scope = Patient.all
    scope = scope.includes(examinations: :results) if include
    scope.find(id)
  rescue ActiveRecord::RecordNotFound => e
    puts e
    raise InvalidPatientIdentifier, "Patient with #{id} was not found..."
  end
end
