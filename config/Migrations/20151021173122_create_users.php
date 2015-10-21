<?php
use Migrations\AbstractMigration;

class CreateUsers extends AbstractMigration
{

    public $autoId = false;

    /**
     * Change Method.
     *
     * More information on this method is available here:
     * http://docs.phinx.org/en/latest/migrations.html#the-change-method
     * @return void
     */
    public function change()
    {
        $table = $this->table('users');
        $table->addColumn('username', 'string', [
            'default' => null,
            'limit' => 255,
            'null' => false,
        ]);
        $table->addColumn('name', 'string', [
            'default' => null,
            'limit' => 255,
            'null' => false,
        ]);
        $table->addColumn('password', 'string', [
            'default' => null,
            'limit' => 1023,
            'null' => false,
        ]);
        $table->addColumn('id_credit_card', 'string', [
            'default' => null,
            'limit' => 255,
            'null' => false,
        ])->addForeignKey('id_credit_card', 'credit_cards', 'number', array('delete'=>'SET_NULL', 'update'=>'NO_ACTION'));
        $table->addColumn('created', 'datetime', [
            'default' => null,
            'null' => false,
        ]);
        $table->addColumn('modified', 'datetime', [
            'default' => null,
            'null' => false,
        ]);
        $table->addPrimaryKey([
            'username',
        ]);
        /*$table->addForeignKey([
            'id_credit_card', 'credit_cards', 'number', array('delete'=>'SET_NULL', 'update'=>'NO_ACTION')
        ]);*/
        $table->create();
    }
}
