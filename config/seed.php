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
			'email' => 'revisor@r.com',
			'name' => 'suissinhos',
			'password' => '[105, -115, -63, -99, 72, -100, 78, 77, -73, 62, 40, -89, 19, -22, -80, 123]',
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
	'neighbor_stations' => [
		'_truncate' => true,
		'_defaults' => [],
		[
			'name1' => '11',
			'name2' => '12',
		],
		[
			'name1' => '12',
			'name2' => '11',
		],
		[
			'name1' => '01',
			'name2' => '12',
		],
		[
			'name1' => '12',
			'name2' => '01',
		],
		[
			'name1' => '01',
			'name2' => '22',
		],
		[
			'name1' => '22',
			'name2' => '01',
		],
		[
			'name1' => '01',
			'name2' => '32',
		],
		[
			'name1' => '32',
			'name2' => '01',
		],
		[
			'name1' => '22',
			'name2' => '21',
		],
		[
			'name1' => '21',
			'name2' => '22',
		],
		[
			'name1' => '32',
			'name2' => '31',
		],
		[
			'name1' => '31',
			'name2' => '32',
		],
	],
	'timetables' => [
		'_truncate' => true,
		'_defaults' => ['created' => '2016-06-06 01:00:00',
			'modified' => '2016-06-06 01:00:00', 'price' => 1.05],
		// A - B
			// first voyage
		[
			'origin_station' => '11',
			'destiny_station' => '12',
			'lotation' => 50,
			'departure_time' => '09:00:00',
			'arrival_time' => '09:10:00',
		],
		[
			'origin_station' => '12',
			'destiny_station' => '01',
			'lotation' => 50,
			'departure_time' => '09:10:00',
			'arrival_time' => '09:20:00',
		],
		[
			'origin_station' => '01',
			'destiny_station' => '22',
			'lotation' => 50,
			'departure_time' => '09:20:00',
			'arrival_time' => '09:30:00',
		],
		[
			'origin_station' => '22',
			'destiny_station' => '21',
			'lotation' => 50,
			'departure_time' => '09:30:00',
			'arrival_time' => '09:40:00',
		],

			// second voyage
		[
			'origin_station' => '11',
			'destiny_station' => '12',
			'lotation' => 50,
			'departure_time' => '15:00:00',
			'arrival_time' => '15:10:00',
		],
		[
			'origin_station' => '12',
			'destiny_station' => '01',
			'lotation' => 50,
			'departure_time' => '15:10:00',
			'arrival_time' => '15:20:00',
		],
		[
			'origin_station' => '01',
			'destiny_station' => '22',
			'lotation' => 50,
			'departure_time' => '15:20:00',
			'arrival_time' => '15:30:00',
		],
		[
			'origin_station' => '22',
			'destiny_station' => '21',
			'lotation' => 50,
			'departure_time' => '15:30:00',
			'arrival_time' => '15:40:00',
		],
		
			// third voyage
		[
			'origin_station' => '11',
			'destiny_station' => '12',
			'lotation' => 50,
			'departure_time' => '21:00:00',
			'arrival_time' => '21:10:00',
		],
		[
			'origin_station' => '12',
			'destiny_station' => '01',
			'lotation' => 50,
			'departure_time' => '21:10:00',
			'arrival_time' => '21:20:00',
		],
		[
			'origin_station' => '01',
			'destiny_station' => '22',
			'lotation' => 50,
			'departure_time' => '21:20:00',
			'arrival_time' => '21:30:00',
		],
		[
			'origin_station' => '22',
			'destiny_station' => '21',
			'lotation' => 50,
			'departure_time' => '21:30:00',
			'arrival_time' => '21:40:00',
		],





		// B - A
			// first voyage
		[
			'origin_station' => '21',
			'destiny_station' => '22',
			'lotation' => 50,
			'departure_time' => '10:00:00',
			'arrival_time' => '10:10:00',
		],
		[
			'origin_station' => '22',
			'destiny_station' => '01',
			'lotation' => 50,
			'departure_time' => '10:10:00',
			'arrival_time' => '10:20:00',
		],
		[
			'origin_station' => '01',
			'destiny_station' => '12',
			'lotation' => 50,
			'departure_time' => '10:20:00',
			'arrival_time' => '10:30:00',
		],
		[
			'origin_station' => '12',
			'destiny_station' => '11',
			'lotation' => 50,
			'departure_time' => '10:30:00',
			'arrival_time' => '10:40:00',
		],
			// second voyage
		[
			'origin_station' => '21',
			'destiny_station' => '22',
			'lotation' => 50,
			'departure_time' => '16:00:00',
			'arrival_time' => '16:10:00',
		],
		[
			'origin_station' => '22',
			'destiny_station' => '01',
			'lotation' => 50,
			'departure_time' => '16:10:00',
			'arrival_time' => '16:20:00',
		],
		[
			'origin_station' => '01',
			'destiny_station' => '12',
			'lotation' => 50,
			'departure_time' => '16:20:00',
			'arrival_time' => '16:30:00',
		],
		[
			'origin_station' => '12',
			'destiny_station' => '11',
			'lotation' => 50,
			'departure_time' => '16:30:00',
			'arrival_time' => '16:40:00',
		],
			// third voyage
		[
			'origin_station' => '21',
			'destiny_station' => '22',
			'lotation' => 50,
			'departure_time' => '22:00:00',
			'arrival_time' => '22:10:00',
		],
		[
			'origin_station' => '22',
			'destiny_station' => '01',
			'lotation' => 50,
			'departure_time' => '22:10:00',
			'arrival_time' => '22:20:00',
		],
		[
			'origin_station' => '01',
			'destiny_station' => '12',
			'lotation' => 50,
			'departure_time' => '22:20:00',
			'arrival_time' => '22:30:00',
		],
		[
			'origin_station' => '12',
			'destiny_station' => '11',
			'lotation' => 50,
			'departure_time' => '22:30:00',
			'arrival_time' => '22:40:00',
		],



		// CentralStation - C
			// first voyage
		[
			'origin_station' => '01',
			'destiny_station' => '32',
			'lotation' => 50,
			'departure_time' => '09:20:00',
			'arrival_time' => '09:30:00',
		],
		[
			'origin_station' => '32',
			'destiny_station' => '31',
			'lotation' => 50,
			'departure_time' => '09:30:00',
			'arrival_time' => '09:40:00',
		],
			// second voyage
		[
			'origin_station' => '01',
			'destiny_station' => '32',
			'lotation' => 50,
			'departure_time' => '15:20:00',
			'arrival_time' => '15:30:00',
		],
		[
			'origin_station' => '32',
			'destiny_station' => '31',
			'lotation' => 50,
			'departure_time' => '15:30:00',
			'arrival_time' => '15:40:00',
		],
			// third voyage
		[
			'origin_station' => '01',
			'destiny_station' => '32',
			'lotation' => 50,
			'departure_time' => '21:20:00',
			'arrival_time' => '21:30:00',
		],
		[
			'origin_station' => '32',
			'destiny_station' => '31',
			'lotation' => 50,
			'departure_time' => '21:30:00',
			'arrival_time' => '21:40:00',
		],



		// C - CentralStation
			// first voyage
		[
			'origin_station' => '31',
			'destiny_station' => '32',
			'lotation' => 50,
			'departure_time' => '10:00:00',
			'arrival_time' => '10:10:00',
		],
		[
			'origin_station' => '32',
			'destiny_station' => '01',
			'lotation' => 50,
			'departure_time' => '10:10:00',
			'arrival_time' => '10:20:00',
		],
			// second voyage
		[
			'origin_station' => '31',
			'destiny_station' => '32',
			'lotation' => 50,
			'departure_time' => '16:00:00',
			'arrival_time' => '16:10:00',
		],
		[
			'origin_station' => '32',
			'destiny_station' => '01',
			'lotation' => 50,
			'departure_time' => '16:10:00',
			'arrival_time' => '16:20:00',
		],
			// third voyage
		[
			'origin_station' => '31',
			'destiny_station' => '32',
			'lotation' => 50,
			'departure_time' => '22:00:00',
			'arrival_time' => '22:10:00',
		],
		[
			'origin_station' => '32',
			'destiny_station' => '01',
			'lotation' => 50,
			'departure_time' => '22:10:00',
			'arrival_time' => '22:20:00',
		],
	],
	'travel_trains' => [
		'_truncate' => true,
		'_defaults' => [],
	],
	'tickets' => [
		'_truncate' => true,
		'_defaults' => ['created' => '2016-06-06 01:00:00',
			'modified' => '2016-06-06 01:00:00', 'price' => 1.05],
		[
			'id_users' => 1,
			'origin_station' => '11',
			'destiny_station' => '12',
			'qr_code' => '5e9a31ca1cbeb6c0c38bb52d7d90c04f786bd5f12dd6339c67997cdac70759d2bfba2098cdc4e89c15bf8c0f4f2b',
			'used' => false,
			'departure_time' => '2015-10-06 09:00:00',
			'arrival_time' => '2015-10-06 09:10:00',
		],
		[
			'id_users' => 1,
			'origin_station' => '21',
			'destiny_station' => '22',
			'qr_code' => '0e5b22cd1dd525b8785b8c94537d7159f8b09f6eed078c949c26ff3f14f28de546819e551267ad0779b5e9e9eb9e',
			'used' => false,
			'departure_time' => '2015-10-06 22:00:00',
			'arrival_time' => '2015-10-06 22:10:00',
		],
		[
			'id_users' => 1,
			'origin_station' => '22',
			'destiny_station' => '01',
			'qr_code' => '47521b2674dc22d3012253bbf253013838242aaf72c38bb4b7d15918010fcdf19926a867bf0add51ccf098b8b7ab',
			'used' => false,
			'departure_time' => '2015-10-06 22:10:00',
			'arrival_time' => '2015-10-06 22:20:00',
		],
	],
	'routes' => [
		'_truncate' => true,
		'_defaults' => ['created' => '2016-06-06 01:00:00',
			'modified' => '2016-06-06 01:00:00'],
			// station 11 to anothers
		[
			'name_station1' => '11',
			'name_station2' => '12',
			'route' => serialize(['11', '12']),
			'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '11',
			'name_station2' => '01',
			'route' => serialize(['11', '12', '01']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '11',
			'name_station2' => '32',
			'route' => serialize(['11', '12', '01', '32']),
            'change_train_stations' => serialize(['01'])
		],
		[
			'name_station1' => '11',
			'name_station2' => '31',
			'route' => serialize(['11', '12', '01', '32', '31']),
            'change_train_stations' => serialize(['01'])
		],
		[
			'name_station1' => '11',
			'name_station2' => '22',
			'route' => serialize(['11', '12', '01', '22']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '11',
			'name_station2' => '21',
			'route' => serialize(['11', '12', '01', '22', '21']),
            'change_train_stations' => serialize([])
		],
			// station 21 to anothers
		[
			'name_station1' => '21',
			'name_station2' => '22',
			'route' => serialize(['21', '22']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '21',
			'name_station2' => '01',
			'route' => serialize(['21', '22', '01']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '21',
			'name_station2' => '32',
			'route' => serialize(['21', '22', '01', '32']),
            'change_train_stations' => serialize(['01'])
		],
		[
			'name_station1' => '21',
			'name_station2' => '31',
			'route' => serialize(['21', '22', '01', '32', '31']),
            'change_train_stations' => serialize(['01'])
		],
		[
			'name_station1' => '21',
			'name_station2' => '12',
			'route' => serialize(['21', '22', '01', '12']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '21',
			'name_station2' => '11',
			'route' => serialize(['21', '22', '01', '12', '11']),
            'change_train_stations' => serialize([])
		],
			// station 31 to anothers
		[
			'name_station1' => '31',
			'name_station2' => '32',
			'route' => serialize(['31', '32']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '31',
			'name_station2' => '01',
			'route' => serialize(['31', '32', '01']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '31',
			'name_station2' => '22',
			'route' => serialize(['31', '32', '01', '22']),
            'change_train_stations' => serialize(['01'])
		],
		[
			'name_station1' => '31',
			'name_station2' => '21',
			'route' => serialize(['31', '32', '01', '22', '21']),
            'change_train_stations' => serialize(['01'])
		],
		[
			'name_station1' => '31',
			'name_station2' => '12',
			'route' => serialize(['31', '32', '01', '12']),
            'change_train_stations' => serialize(['01'])
		],
		[
			'name_station1' => '31',
			'name_station2' => '11',
			'route' => serialize(['31', '32', '01', '12', '11']),
            'change_train_stations' => serialize(['01'])
		],
			// station 12 to anothers
		[
			'name_station1' => '12',
			'name_station2' => '11',
			'route' => serialize(['12', '11']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '12',
			'name_station2' => '01',
			'route' => serialize(['12', '01']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '12',
			'name_station2' => '32',
			'route' => serialize(['12', '01', '32']),
            'change_train_stations' => serialize(['01'])
		],
		[
			'name_station1' => '12',
			'name_station2' => '31',
			'route' => serialize(['12', '01', '32', '31']),
            'change_train_stations' => serialize(['01'])
		],
		[
			'name_station1' => '12',
			'name_station2' => '22',
			'route' => serialize(['12', '01', '22']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '12',
			'name_station2' => '21',
			'route' => serialize(['12', '01', '22', '21']),
            'change_train_stations' => serialize([])
		],
			// station 22 to anothers
		[
			'name_station1' => '22',
			'name_station2' => '21',
			'route' => serialize(['22', '21']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '22',
			'name_station2' => '01',
			'route' => serialize(['22', '01']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '22',
			'name_station2' => '32',
			'route' => serialize(['22', '01', '32']),
            'change_train_stations' => serialize(['01'])
		],
		[
			'name_station1' => '22',
			'name_station2' => '31',
			'route' => serialize(['22', '01', '32', '31']),
            'change_train_stations' => serialize(['01'])
		],
		[
			'name_station1' => '22',
			'name_station2' => '12',
			'route' => serialize(['22', '01', '12']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '22',
			'name_station2' => '11',
			'route' => serialize(['22', '01', '12', '11']),
            'change_train_stations' => serialize([])
		],
			// station 32 to anothers
		[
			'name_station1' => '32',
			'name_station2' => '31',
			'route' => serialize(['32', '31']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '32',
			'name_station2' => '01',
			'route' => serialize(['32', '01']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '32',
			'name_station2' => '22',
			'route' => serialize(['32', '01', '22']),
            'change_train_stations' => serialize(['01'])
		],
		[
			'name_station1' => '32',
			'name_station2' => '21',
			'route' => serialize(['32', '01', '22', '21']),
            'change_train_stations' => serialize(['01'])
		],
		[
			'name_station1' => '32',
			'name_station2' => '12',
			'route' => serialize(['32', '01', '12']),
            'change_train_stations' => serialize(['01'])
		],
		[
			'name_station1' => '32',
			'name_station2' => '11',
			'route' => serialize(['32', '01', '12', '11']),
            'change_train_stations' => serialize(['01'])
		],
			// station 01 to anothers
		[
			'name_station1' => '01',
			'name_station2' => '12',
			'route' => serialize(['01', '12']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '01',
			'name_station2' => '22',
			'route' => serialize(['01', '22']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '01',
			'name_station2' => '32',
			'route' => serialize(['01', '32']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '01',
			'name_station2' => '11',
			'route' => serialize(['01', '12', '11']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '01',
			'name_station2' => '21',
			'route' => serialize(['01', '22', '21']),
            'change_train_stations' => serialize([])
		],
		[
			'name_station1' => '01',
			'name_station2' => '31',
			'route' => serialize(['01', '32', '31']),
            'change_train_stations' => serialize([])
		],
	],
];

for ($i = 1; $i <= 36; $i++) {
	for ($a = 1; $a < 32; $a++) {
		if ($a < 10)
			$newA = '0'+(string)$a;
		else
			$newA = (string)$a;
		$data['travel_trains'][] = [
				'timetable_id' => $i,
				'passengers' => 0,
				'date' => new \DateTime("2015-10-".$newA)
		];
	}
}
for ($i = 1; $i <= 36; $i++) {
	for ($a = 1; $a < 31; $a++) {
		if ($a < 10)
			$newA = '0'+(string)$a;
		else
			$newA = (string)$a;
		$data['travel_trains'][] = [
				'timetable_id' => $i,
				'passengers' => 0,
				'date' => new \DateTime("2015-11-".$newA)
		];
	}
}
for ($i = 1; $i <= 36; $i++) {
	for ($a = 1; $a < 32; $a++) {
		if ($a < 10)
			$newA = '0'+(string)$a;
		else
			$newA = (string)$a;
		$data['travel_trains'][] = [
				'timetable_id' => $i,
				'passengers' => 0,
				'date' => new \DateTime("2015-12-".$newA)
		];
	}
}


$this->importTables($data);
