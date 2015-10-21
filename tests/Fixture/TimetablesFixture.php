<?php
namespace App\Test\Fixture;

use Cake\TestSuite\Fixture\TestFixture;

/**
 * TimetablesFixture
 *
 */
class TimetablesFixture extends TestFixture
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
        'departure_time' => ['type' => 'timestamp', 'length' => null, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null],
        'arrival_time' => ['type' => 'timestamp', 'length' => null, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null],
        'lotation' => ['type' => 'integer', 'length' => 10, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null, 'unsigned' => null, 'autoIncrement' => null],
        'created' => ['type' => 'timestamp', 'length' => null, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null],
        'modified' => ['type' => 'timestamp', 'length' => null, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null],
        '_constraints' => [
            'primary' => ['type' => 'primary', 'columns' => ['id'], 'length' => []],
            'timetables_destiny_station' => ['type' => 'foreign', 'columns' => ['destiny_station'], 'references' => ['stations', 'name'], 'update' => 'noAction', 'delete' => 'cascade', 'length' => []],
            'timetables_origin_station' => ['type' => 'foreign', 'columns' => ['origin_station'], 'references' => ['stations', 'name'], 'update' => 'noAction', 'delete' => 'cascade', 'length' => []],
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
            'departure_time' => 1445464963,
            'arrival_time' => 1445464963,
            'lotation' => 1,
            'created' => 1445464963,
            'modified' => 1445464963
        ],
    ];
}
