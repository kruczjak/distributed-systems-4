class Examination < ApplicationRecord
  has_one :patient
  has_many :results

  def to_struct
    examination_struct = ExaminationStruct.new
    examination_struct.doctorName = doctor_name || ''
    examination_struct.date = created_at.to_s
    examination_struct.results = results&.map(&:to_struct) || []
    examination_struct
  end
end
