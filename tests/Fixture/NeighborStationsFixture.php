<?php
namespace App\Test\Fixture;

use Cake\TestSuite\Fixture\TestFixture;

/**
 * NeighborStationsFixture
 *
 */
class NeighborStationsFixture extends TestFixture
{

    /**
     * Fields
     *
     * @var array
     */
    // @codingStandardsIgnoreStart
    public $fields = [
        'id' => ['type' => 'integer', 'length' => 10, 'autoIncrement' => true, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null, 'unsigned' => null],
        'name1' => ['type' => 'string', 'length' => 255, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null, 'fixed' => null],
        'name2' => ['type' => 'string', 'length' => 255, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null, 'fixed' => null],
        '_constraints' => [
            'primary' => ['type' => 'primary', 'columns' => ['id'], 'length' => []],
            'neighbor_stations_name1' => ['type' => 'foreign', 'columns' => ['name1'], 'references' => ['stations', 'name'], 'update' => 'noAction', 'delete' => 'cascade', 'length' => []],
            'neighbor_stations_name2' => ['type' => 'foreign', 'columns' => ['name2'], 'references' => ['stations', 'name'], 'update' => 'noAction', 'delete' => 'cascade', 'length' => []],
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
            'name1' => 'Lorem ipsum dolor sit amet',
            'name2' => 'Lorem ipsum dolor sit amet'
        ],
    ];
}
