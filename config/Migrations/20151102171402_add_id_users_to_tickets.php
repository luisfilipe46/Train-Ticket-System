<?php
use Migrations\AbstractMigration;

class AddIdUsersToTickets extends AbstractMigration
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
        $table->addColumn('id_users', 'integer', [
            'default' => null,
            'limit' => 11,
            'null' => false,
        ])->addForeignKey('id_users', 'users', 'id', array('delete'=>'SET_NULL', 'update'=>'CASCADE'));
        $table->update();
    }
}
