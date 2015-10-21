<?php
use Migrations\AbstractMigration;

class CreateTimetables extends AbstractMigration
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
        $table->addColumn('origin_station', 'string', [
            'default' => null,
            'limit' => 255,
            'null' => false,
        ])->addForeignKey('origin_station', 'stations', 'name', array('delete'=>'CASCADE', 'update'=>'NO_ACTION'));
        $table->addColumn('destiny_station', 'string', [
            'default' => null,
            'limit' => 255,
            'null' => false,
        ])->addForeignKey('destiny_station', 'stations', 'name', array('delete'=>'CASCADE', 'update'=>'NO_ACTION'));
        $table->addColumn('departure_time', 'datetime', [
            'default' => null,
            'null' => false,
        ]);
        $table->addColumn('arrival_time', 'datetime', [
            'default' => null,
            'null' => false,
        ]);
        $table->addColumn('lotation', 'integer', [
            'default' => null,
            'limit' => 11,
            'null' => false,
        ]);
        $table->addColumn('created', 'datetime', [
            'default' => null,
            'null' => false,
        ]);
        $table->addColumn('modified', 'datetime', [
            'default' => null,
            'null' => false,
        ]);
        $table->create();
    }
}
