class Result < ApplicationRecord
  has_one :examination

  def to_struct
    result_struct = ResultStruct.new
    result_struct.name = name
    result_struct.unit = unit
    result_struct.value = value
    result_struct
  end
end
