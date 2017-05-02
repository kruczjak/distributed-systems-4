class CreateExamination < ActiveRecord::Migration[5.0]
  def change
    create_table :examinations do |t|
      t.integer :patient_id, null: false

      t.timestamps null: false
    end

    add_index :examinations, :patient_id
  end
end
