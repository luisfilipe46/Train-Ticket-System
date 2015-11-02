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
        'origin_station' => ['type' => 'string', 'length' => 255, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null, 'fixed' => null],
        'destiny_station' => ['type' => 'string', 'length' => 255, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null, 'fixed' => null],
        'qr_code' => ['type' => 'text', 'length' => null, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null],
        'used' => ['type' => 'boolean', 'length' => null, 'default' => 0, 'null' => false, 'comment' => null, 'precision' => null],
        'departure_time' => ['type' => 'timestamp', 'length' => null, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null],
        'arrival_time' => ['type' => 'timestamp', 'length' => null, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null],
        'created' => ['type' => 'timestamp', 'length' => null, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null],
        'modified' => ['type' => 'timestamp', 'length' => null, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null],
        'id_users' => ['type' => 'integer', 'length' => 10, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null, 'unsigned' => null, 'autoIncrement' => null],
        '_constraints' => [
            'primary' => ['type' => 'primary', 'columns' => ['id'], 'length' => []],
            'unique_qr_code' => ['type' => 'unique', 'columns' => ['qr_code'], 'length' => []],
            'tickets_destiny_station' => ['type' => 'foreign', 'columns' => ['destiny_station'], 'references' => ['stations', 'name'], 'update' => 'cascade', 'delete' => 'setNull', 'length' => []],
            'tickets_id_users' => ['type' => 'foreign', 'columns' => ['id_users'], 'references' => ['users', 'id'], 'update' => 'cascade', 'delete' => 'setNull', 'length' => []],
            'tickets_origin_station' => ['type' => 'foreign', 'columns' => ['origin_station'], 'references' => ['stations', 'name'], 'update' => 'cascade', 'delete' => 'setNull', 'length' => []],
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
            'origin_station' => 'Lorem ipsum dolor sit amet',
            'destiny_station' => 'Lorem ipsum dolor sit amet',
            'qr_code' => 'Lorem ipsum dolor sit amet, aliquet feugiat. Convallis morbi fringilla gravida, phasellus feugiat dapibus velit nunc, pulvinar eget sollicitudin venenatis cum nullam, vivamus ut a sed, mollitia lectus. Nulla vestibulum massa neque ut et, id hendrerit sit, feugiat in taciti enim proin nibh, tempor dignissim, rhoncus duis vestibulum nunc mattis convallis.',
            'used' => 1,
            'departure_time' => 1446485352,
            'arrival_time' => 1446485352,
            'created' => 1446485352,
            'modified' => 1446485352,
            'id_users' => 1
        ],
    ];
}
