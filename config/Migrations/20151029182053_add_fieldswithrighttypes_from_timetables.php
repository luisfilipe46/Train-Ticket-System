<?php
use Migrations\AbstractMigration;

class AddFieldswithrighttypesFromTimetables extends AbstractMigration
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
        $table = $this->table('timetables');
        $table->addColumn('departure_time', 'time', [
            'default' => null,
            'null' => false,
        ]);
        $table->addColumn('arrival_time', 'time', [
            'default' => null,
            'null' => false,
        ]);
        $table->update();
    }
}
