class Patient < ApplicationRecord
  has_many :examinations

  def to_struct
    thrift_patient = PatientStruct.new
    thrift_patient.identifier = id
    thrift_patient.name = name
    thrift_patient.examinations = examinations&.map(&:to_struct) || []
    thrift_patient
  end
end
