<?php
use Migrations\AbstractMigration;

class CreateTickets extends AbstractMigration
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
        $table = $this->table('tickets');
        $table->addColumn('email', 'string', [
            'default' => null,
            'limit' => 255,
            'null' => false,
        ])->addForeignKey('email', 'users', 'email', array('delete'=>'SET_NULL', 'update'=>'CASCADE'));
        $table->addColumn('origin_station', 'string', [
            'default' => null,
            'limit' => 255,
            'null' => false,
        ])->addForeignKey('origin_station', 'stations', 'name', array('delete'=>'SET_NULL', 'update'=>'CASCADE'));
        $table->addColumn('destiny_station', 'string', [
            'default' => null,
            'limit' => 255,
            'null' => false,
        ])->addForeignKey('destiny_station', 'stations', 'name', array('delete'=>'SET_NULL', 'update'=>'CASCADE'));
        $table->addColumn('qr_code', 'text', [
            'default' => null,
            'null' => false,
        ]);
        $table->addColumn('used', 'boolean', [
            'default' => false,
            'null' => false,
        ]);
        $table->addColumn('departure_time', 'datetime', [
            'default' => null,
            'null' => false,
        ]);
        $table->addColumn('arrival_time', 'datetime', [
            'default' => null,
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
        $table->addIndex([
            'qr_code',
        ], [
            'name' => 'UNIQUE_QR_CODE',
            'unique' => true,
        ]);
        $table->create();
    }
}
