class Examination < ApplicationRecord
  has_one :patient
  has_many :results

  scope :search_for, -> (name) {
    joins(:results).where(
      'lower(doctor_name) LIKE lower(?) OR lower(results.name) LIKE lower(?)',
      "%#{name}%",
      "%#{name}%"
    )
  }

  def to_struct
    examination_struct = ExaminationStruct.new
    examination_struct.doctorName = doctor_name || ''
    examination_struct.date = created_at.to_s
    examination_struct.results = results&.map(&:to_struct) || []
    examination_struct
  end
end
