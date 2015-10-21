<?php
use Migrations\AbstractMigration;

class CreateTravelTrains extends AbstractMigration
{
    /**
     * Change Method.
     *
     * More information on this method is available here:
     * http://docs.phinx.org/en/latest/migrations.html#the-change-method
     * @return void
     */
    public function change()
    {
        $table = $this->table('travel_trains');
        $table->addColumn('timetable_id', 'integer', [
            'default' => null,
            'limit' => 255,
            'null' => false,
        ])->addForeignKey('timetable_id', 'timetables', 'id', array('delete'=>'SET_NULL', 'update'=>'NO_ACTION'));
        $table->addColumn('date', 'datetime', [
            'default' => null,
            'null' => false,
        ]);
        $table->addColumn('passengers', 'integer', [
            'default' => null,
            'limit' => 11,
            'null' => false,
        ]);
        $table->create();
    }
}
