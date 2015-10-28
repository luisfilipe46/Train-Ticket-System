<?php
/**
 * BasicSeed plugin data seed file.
 */

namespace App\Config\BasicSeed;

use Cake\ORM\TableRegistry;

// Write your data import statements here.
$data = [
	'TableName' => [
		//'_truncate' => true,
		//'_entityOptions' => [
		//	'validate' => false,
		//],
		//'_saveOptions' => [
		//	'checkRules' => false,
		//],
		'_defaults' => [],
		[
			'id' => 1,
			'name' => 'record 1',
		],
	],
];

$this->importTables($data);
