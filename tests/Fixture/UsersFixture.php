<?php
namespace App\Test\Fixture;

use Cake\TestSuite\Fixture\TestFixture;

/**
 * UsersFixture
 *
 */
class UsersFixture extends TestFixture
{

    /**
     * Fields
     *
     * @var array
     */
    // @codingStandardsIgnoreStart
    public $fields = [
        'username' => ['type' => 'string', 'length' => 255, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null, 'fixed' => null],
        'name' => ['type' => 'string', 'length' => 255, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null, 'fixed' => null],
        'password' => ['type' => 'string', 'length' => 1023, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null, 'fixed' => null],
        'id_credit_card' => ['type' => 'string', 'length' => 255, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null, 'fixed' => null],
        'created' => ['type' => 'timestamp', 'length' => null, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null],
        'modified' => ['type' => 'timestamp', 'length' => null, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null],
        '_constraints' => [
            'primary' => ['type' => 'primary', 'columns' => ['username'], 'length' => []],
            'users_id_credit_card' => ['type' => 'foreign', 'columns' => ['id_credit_card'], 'references' => ['credit_cards', 'number'], 'update' => 'noAction', 'delete' => 'setNull', 'length' => []],
        ],
    ];
    // @codingStandardsIgnoreEnd

    /**
     * Records
     *
     * @var array
     */
    public $records = [
        [
            'username' => '2dcf9a3c-a8d6-4f2c-a124-76795cd4dd9f',
            'name' => 'Lorem ipsum dolor sit amet',
            'password' => 'Lorem ipsum dolor sit amet',
            'id_credit_card' => 'Lorem ipsum dolor sit amet',
            'created' => 1445464587,
            'modified' => 1445464587
        ],
    ];
}
