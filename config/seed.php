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
		[
			'name' => '11', // 1 = A
			'line' => 'A',
			'real_name' => 'S. Joao'
		],
		[
			'name' => '12', // 1 = A
			'line' => 'A',
			'real_name' => 'IPO'
		],
		[
			'name' => '01', // 0 = A, B, C
			'line' => '0',
			'real_name' => 'Trindade'
		],
		[
			'name' => '21', // 2 = B
			'line' => 'B',
			'real_name' => 'Aliados'
		],
		[
			'name' => '22', // 2 = B
			'line' => 'B',
			'real_name' => 'Faria Guimaraes',
		],
		[
			'name' => '31', // 3 = C
			'line' => 'C',
			'real_name' => 'Azurara',
		],
		[
			'name' => '32', // 3 = C
			'line' => 'C',
			'real_name' => 'Vila do Conde',
		],
	],
];


$this->importTables($data);
