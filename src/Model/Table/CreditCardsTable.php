<?php
namespace App\Model\Table;

use App\Model\Entity\CreditCard;
use Cake\ORM\Query;
use Cake\ORM\RulesChecker;
use Cake\ORM\Table;
use Cake\Validation\Validator;

/**
 * CreditCards Model
 *
 */
class CreditCardsTable extends Table
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

        $this->table('credit_cards');
        $this->displayField('id');
        $this->primaryKey('id');

        $this->addBehavior('Timestamp');
	//$this->hasOne('Users');

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
            ->requirePresence('number', 'create')
            ->notEmpty('number')
            ->add('number', 'unique', ['rule' => 'validateUnique', 'provider' => 'table']);

        $validator
            ->requirePresence('type', 'create')
            ->notEmpty('type');

        $validator
            ->add('validity', 'valid', ['rule' => 'date'])
            ->requirePresence('validity', 'create')
            ->notEmpty('validity');

        return $validator;
    }
}
