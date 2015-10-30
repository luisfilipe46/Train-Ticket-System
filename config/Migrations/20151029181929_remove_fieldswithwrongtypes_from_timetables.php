<?php
use Migrations\AbstractMigration;

class RemoveFieldswithwrongtypesFromTimetables extends AbstractMigration
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
        $table->removeColumn('arrival_time');
        $table->removeColumn('departure_time');
        $table->update();
    }
}
