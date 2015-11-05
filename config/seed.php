<?php
/**
 * BasicSeed plugin data seed file.
 */

namespace App\Config\BasicSeed;

use Cake\ORM\TableRegistry;

// Write your data import statements here.
$data = [
	'users' => [
		'_truncate' => true,
		//'_entityOptions' => [
		//	'validate' => false,
		//],
		//'_saveOptions' => [
		//	'checkRules' => false,
		//],
		'_defaults' => ['created' => '2016-06-06 01:00:00',
			'modified' => '2016-06-06 01:00:00'],
		[
			'email' => 'luminosity@cs.com',
			'name' => 'brasuca',
			'password' => 'test',
			'role' => 'cliente',
		],
		[
			'email' => 'fnatic@cs.com',
			'name' => 'suissinhos',
			'password' => 'test',
			'role' => 'pica',
		],
	],
	'credit_cards' => [
		'_truncate' => true,
		'_defaults' => ['created' => '2016-06-06 01:00:00',
			'modified' => '2016-06-06 01:00:00'],
		[
			'number' => '123',
			'type' => 'visa',
			'validity' => new \DateTime('2018-07-05'),
			'id_user' => 1
		],
	],
	'stations' => [
		'_truncate' => true,
		'_defaults' => ['created' => '2016-06-06 01:00:00',
			'modified' => '2016-06-06 01:00:00'],
	],
];


$this->importTables($data);
