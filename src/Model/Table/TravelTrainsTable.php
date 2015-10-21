<?php
namespace App\Model\Table;

use App\Model\Entity\TravelTrain;
use Cake\ORM\Query;
use Cake\ORM\RulesChecker;
use Cake\ORM\Table;
use Cake\Validation\Validator;

/**
 * TravelTrains Model
 *
 * @property \Cake\ORM\Association\BelongsTo $Timetables
 */
class TravelTrainsTable extends Table
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

        $this->table('travel_trains');
        $this->displayField('id');
        $this->primaryKey('id');

        $this->belongsTo('Timetables', [
            'foreignKey' => 'timetable_id',
            'joinType' => 'INNER'
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
            ->requirePresence('date', 'create')
            ->notEmpty('date');

        $validator
            ->add('passengers', 'valid', ['rule' => 'numeric'])
            ->requirePresence('passengers', 'create')
            ->notEmpty('passengers');

        return $validator;
    }

    /**
     * Returns a rules checker object that will be used for validating
     * application integrity.
     *
     * @param \Cake\ORM\RulesChecker $rules The rules object to be modified.
     * @return \Cake\ORM\RulesChecker
     */
    public function buildRules(RulesChecker $rules)
    {
        $rules->add($rules->existsIn(['timetable_id'], 'Timetables'));
        return $rules;
    }
}
