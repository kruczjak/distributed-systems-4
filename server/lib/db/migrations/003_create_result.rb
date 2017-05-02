class CreateResult < ActiveRecord::Migration[5.0]
  def change
    create_table :results do |t|
      t.integer :examination_id, null: false
      t.string :name
      t.string :unit
      t.string :value
      t.string :reference_value
    end

    add_index :results, :examination_id
  end
end
