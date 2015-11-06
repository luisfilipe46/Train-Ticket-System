<?php
use Migrations\AbstractMigration;

class AddChangeTrainStationsToRoutes extends AbstractMigration
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
        $table->addColumn('change_train_stations', 'text', [
            'default' => null,
            'null' => false,
        ]);
        $table->update();
    }
}
