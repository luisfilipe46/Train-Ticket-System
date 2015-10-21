<?php
namespace App\Test\Fixture;

use Cake\TestSuite\Fixture\TestFixture;

/**
 * TicketsFixture
 *
 */
class TicketsFixture extends TestFixture
{

    /**
     * Fields
     *
     * @var array
     */
    // @codingStandardsIgnoreStart
    public $fields = [
        'id' => ['type' => 'integer', 'length' => 10, 'autoIncrement' => true, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null, 'unsigned' => null],
        'username' => ['type' => 'string', 'length' => 255, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null, 'fixed' => null],
        'origin_station' => ['type' => 'string', 'length' => 255, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null, 'fixed' => null],
        'destiny_station' => ['type' => 'string', 'length' => 255, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null, 'fixed' => null],
        'qr_code' => ['type' => 'string', 'length' => 255, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null, 'fixed' => null],
        'used' => ['type' => 'boolean', 'length' => null, 'default' => 0, 'null' => false, 'comment' => null, 'precision' => null],
        'departure_time' => ['type' => 'timestamp', 'length' => null, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null],
        'arrival_time' => ['type' => 'timestamp', 'length' => null, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null],
        'created' => ['type' => 'timestamp', 'length' => null, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null],
        'modified' => ['type' => 'timestamp', 'length' => null, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null],
        '_constraints' => [
            'primary' => ['type' => 'primary', 'columns' => ['id'], 'length' => []],
            'unique_qr_code' => ['type' => 'unique', 'columns' => ['qr_code'], 'length' => []],
            'tickets_destiny_station' => ['type' => 'foreign', 'columns' => ['destiny_station'], 'references' => ['stations', 'name'], 'update' => 'noAction', 'delete' => 'setNull', 'length' => []],
            'tickets_origin_station' => ['type' => 'foreign', 'columns' => ['origin_station'], 'references' => ['stations', 'name'], 'update' => 'noAction', 'delete' => 'setNull', 'length' => []],
            'tickets_username' => ['type' => 'foreign', 'columns' => ['username'], 'references' => ['users', 'username'], 'update' => 'noAction', 'delete' => 'setNull', 'length' => []],
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
            'id' => 1,
            'username' => 'Lorem ipsum dolor sit amet',
            'origin_station' => 'Lorem ipsum dolor sit amet',
            'destiny_station' => 'Lorem ipsum dolor sit amet',
            'qr_code' => 'Lorem ipsum dolor sit amet',
            'used' => 1,
            'departure_time' => 1445464781,
            'arrival_time' => 1445464781,
            'created' => 1445464781,
            'modified' => 1445464781
        ],
    ];
}
