class Patient < ApplicationRecord
  has_many :examinations
end
