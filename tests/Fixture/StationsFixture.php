<?php
namespace App\Test\Fixture;

use Cake\TestSuite\Fixture\TestFixture;

/**
 * StationsFixture
 *
 */
class StationsFixture extends TestFixture
{

    /**
     * Fields
     *
     * @var array
     */
    // @codingStandardsIgnoreStart
    public $fields = [
        'name' => ['type' => 'string', 'length' => 255, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null, 'fixed' => null],
        'line' => ['type' => 'string', 'fixed' => true, 'length' => 255, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null],
        'created' => ['type' => 'timestamp', 'length' => null, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null],
        'modified' => ['type' => 'timestamp', 'length' => null, 'default' => null, 'null' => false, 'comment' => null, 'precision' => null],
        '_constraints' => [
            'primary' => ['type' => 'primary', 'columns' => ['name'], 'length' => []],
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
            'name' => 'dae6b493-7988-41ca-8a92-5b9bcad9bb2d',
            'line' => 'Lorem ipsum dolor sit amet',
            'created' => 1445464854,
            'modified' => 1445464854
        ],
    ];
}
