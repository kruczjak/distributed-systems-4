class HospitalHandler
  def initialize
    puts 'Initialized'
  end

  def getPatient(id)
    puts "Getting patient with id:#{id}"
    patient = find_patient(id).to_struct
    puts "Patient #{patient.inspect} fetched"
    patient
  end

  def createPatient(name)
    Patient.create!(name: name).id
  end

  private

  def find_patient(id)
    Patient.includes(examinations: :results).find(id)
  rescue ActiveRecord::RecordNotFound => e
    puts e
    raise InvalidPatientIdentifier, "Patient with #{id} was not found..."
  end
end
