<?php
use Migrations\AbstractMigration;

class AddPriceToTimetables extends AbstractMigration
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
        $table->addColumn('price', 'float', [
            'default' => null,
            'null' => false,
        ]);
        $table->update();
    }
}
