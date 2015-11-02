<?php
namespace App\Model\Table;

use App\Model\Entity\Ticket;
use Cake\ORM\Query;
use Cake\ORM\RulesChecker;
use Cake\ORM\Table;
use Cake\Validation\Validator;

/**
 * Tickets Model
 *
 */
class TicketsTable extends Table
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

        $this->table('tickets');
        $this->displayField('id');
        $this->primaryKey('id');

        $this->addBehavior('Timestamp');

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
            ->requirePresence('qr_code', 'create')
            ->notEmpty('qr_code')
            ->add('qr_code', 'unique', ['rule' => 'validateUnique', 'provider' => 'table']);

        $validator
            ->add('used', 'valid', ['rule' => 'boolean'])
            ->requirePresence('used', 'create')
            ->notEmpty('used');

        $validator
            ->requirePresence('departure_time', 'create')
            ->notEmpty('departure_time');

        $validator
            ->requirePresence('arrival_time', 'create')
            ->notEmpty('arrival_time');

        $validator
            ->add('id_users', 'valid', ['rule' => 'numeric'])
            ->requirePresence('id_users', 'create')
            ->notEmpty('id_users');

        return $validator;
    }
}
