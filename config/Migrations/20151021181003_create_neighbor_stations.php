<?php
use Migrations\AbstractMigration;

class CreateNeighborStations extends AbstractMigration
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
        $table = $this->table('neighbor_stations');
        $table->addColumn('name1', 'string', [
            'default' => null,
            'limit' => 255,
            'null' => false,
        ])->addForeignKey('name1', 'stations', 'name', array('delete'=>'CASCADE', 'update'=>'NO_ACTION'));
        $table->addColumn('name2', 'string', [
            'default' => null,
            'limit' => 255,
            'null' => false,
        ])->addForeignKey('name2', 'stations', 'name', array('delete'=>'CASCADE', 'update'=>'NO_ACTION'));
        $table->create();
    }
}
