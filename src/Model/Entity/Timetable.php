<?php
namespace App\Model\Entity;

use Cake\ORM\Entity;

/**
 * Timetable Entity.
 *
 * @property int $id
 * @property string $origin_station
 * @property string $destiny_station
 * @property \Cake\I18n\Time $departure_time
 * @property \Cake\I18n\Time $arrival_time
 * @property int $lotation
 * @property \Cake\I18n\Time $created
 * @property \Cake\I18n\Time $modified
 * @property \App\Model\Entity\TravelTrain[] $travel_trains
 */
class Timetable extends Entity
{

    /**
     * Fields that can be mass assigned using newEntity() or patchEntity().
     *
     * Note that when '*' is set to true, this allows all unspecified fields to
     * be mass assigned. For security purposes, it is advised to set '*' to false
     * (or remove it), and explicitly make individual fields accessible as needed.
     *
     * @var array
     */
    protected $_accessible = [
        '*' => true,
        'id' => false,
    ];
}
