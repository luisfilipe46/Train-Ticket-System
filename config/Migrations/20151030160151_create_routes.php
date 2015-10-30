<?php
use Migrations\AbstractMigration;

class CreateRoutes extends AbstractMigration
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
        $table = $this->table('routes');
        $table->addColumn('name_station1', 'string', [
            'default' => null,
            'limit' => 255,
            'null' => false,
        ])->addForeignKey('name_station1', 'stations', 'name', array('delete'=>'CASCADE', 'update'=>'NO_ACTION'));
        $table->addColumn('name_station2', 'string', [
            'default' => null,
            'limit' => 255,
            'null' => false,
        ])->addForeignKey('name_station2', 'stations', 'name', array('delete'=>'CASCADE', 'update'=>'NO_ACTION'));
        $table->addColumn('route', 'text', [
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

        $table->create();
    }
}
