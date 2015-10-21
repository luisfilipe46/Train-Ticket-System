<?php
namespace App\Model\Table;

use App\Model\Entity\Timetable;
use Cake\ORM\Query;
use Cake\ORM\RulesChecker;
use Cake\ORM\Table;
use Cake\Validation\Validator;

/**
 * Timetables Model
 *
 * @property \Cake\ORM\Association\HasMany $TravelTrains
 */
class TimetablesTable extends Table
{

    /**
     * Initialize method
     *
     * @param array $config The configuration for the Table.
     * @return void
     */
    public function initialize(array $config)
    {
        parent::initialize($config);

        $this->table('timetables');
        $this->displayField('id');
        $this->primaryKey('id');

        $this->addBehavior('Timestamp');

        $this->hasMany('TravelTrains', [
            'foreignKey' => 'timetable_id'
        ]);
    }

    /**
     * Default validation rules.
     *
     * @param \Cake\Validation\Validator $validator Validator instance.
     * @return \Cake\Validation\Validator
     */
    public function validationDefault(Validator $validator)
    {
        $validator
            ->add('id', 'valid', ['rule' => 'numeric'])
            ->allowEmpty('id', 'create');

        $validator
            ->requirePresence('origin_station', 'create')
            ->notEmpty('origin_station');

        $validator
            ->requirePresence('destiny_station', 'create')
            ->notEmpty('destiny_station');

        $validator
            ->requirePresence('departure_time', 'create')
            ->notEmpty('departure_time');

        $validator
            ->requirePresence('arrival_time', 'create')
            ->notEmpty('arrival_time');

        $validator
            ->add('lotation', 'valid', ['rule' => 'numeric'])
            ->requirePresence('lotation', 'create')
            ->notEmpty('lotation');

        return $validator;
    }
}
