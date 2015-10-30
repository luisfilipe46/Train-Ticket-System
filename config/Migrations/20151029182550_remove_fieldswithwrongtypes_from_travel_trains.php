<?php
use Migrations\AbstractMigration;

class RemoveFieldswithwrongtypesFromTravelTrains extends AbstractMigration
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
        $table->removeColumn('date');
        $table->update();
    }
}
