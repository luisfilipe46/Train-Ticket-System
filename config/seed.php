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
			'password' => '2018-07-05',
			'role' => 'cliente',
		],
		[
			'email' => 'fnatic@cs.com',
			'name' => 'suissinhos',
			'password' => '2018-07-05',
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
		],
		[
			'name' => '12', // 1 = A
			'line' => 'A',
		],
		[
			'name' => '01', // 0 = A, B, C
			'line' => '0',
		],
		[
			'name' => '21', // 2 = B
			'line' => 'B',
		],
		[
			'name' => '22', // 2 = B
			'line' => 'B',
		],
		[
			'name' => '31', // 3 = C
			'line' => 'C',
		],
		[
			'name' => '32', // 3 = C
			'line' => 'C',
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
			'modified' => '2016-06-06 01:00:00'],
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
			'departure_time' => '09:00:00',
			'arrival_time' => '09:10:00',
		],
		[
			'origin_station' => '22',
			'destiny_station' => '01',
			'lotation' => 50,
			'departure_time' => '09:10:00',
			'arrival_time' => '09:20:00',
		],
		[
			'origin_station' => '01',
			'destiny_station' => '12',
			'lotation' => 50,
			'departure_time' => '09:20:00',
			'arrival_time' => '09:30:00',
		],
		[
			'origin_station' => '12',
			'destiny_station' => '11',
			'lotation' => 50,
			'departure_time' => '09:30:00',
			'arrival_time' => '09:40:00',
		],
			// second voyage
		[
			'origin_station' => '21',
			'destiny_station' => '22',
			'lotation' => 50,
			'departure_time' => '15:00:00',
			'arrival_time' => '15:10:00',
		],
		[
			'origin_station' => '22',
			'destiny_station' => '01',
			'lotation' => 50,
			'departure_time' => '15:10:00',
			'arrival_time' => '15:20:00',
		],
		[
			'origin_station' => '01',
			'destiny_station' => '12',
			'lotation' => 50,
			'departure_time' => '15:20:00',
			'arrival_time' => '15:30:00',
		],
		[
			'origin_station' => '12',
			'destiny_station' => '11',
			'lotation' => 50,
			'departure_time' => '15:30:00',
			'arrival_time' => '15:40:00',
		],
			// third voyage
		[
			'origin_station' => '21',
			'destiny_station' => '22',
			'lotation' => 50,
			'departure_time' => '21:00:00',
			'arrival_time' => '21:10:00',
		],
		[
			'origin_station' => '22',
			'destiny_station' => '01',
			'lotation' => 50,
			'departure_time' => '21:10:00',
			'arrival_time' => '21:20:00',
		],
		[
			'origin_station' => '01',
			'destiny_station' => '12',
			'lotation' => 50,
			'departure_time' => '21:20:00',
			'arrival_time' => '21:30:00',
		],
		[
			'origin_station' => '12',
			'destiny_station' => '11',
			'lotation' => 50,
			'departure_time' => '21:30:00',
			'arrival_time' => '21:40:00',
		],



		// B - C
			// first voyage
		[
			'origin_station' => '21',
			'destiny_station' => '22',
			'lotation' => 50,
			'departure_time' => '09:00:00',
			'arrival_time' => '09:10:00',
		],
		[
			'origin_station' => '22',
			'destiny_station' => '01',
			'lotation' => 50,
			'departure_time' => '09:10:00',
			'arrival_time' => '09:20:00',
		],
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
			'origin_station' => '21',
			'destiny_station' => '22',
			'lotation' => 50,
			'departure_time' => '15:00:00',
			'arrival_time' => '15:10:00',
		],
		[
			'origin_station' => '22',
			'destiny_station' => '01',
			'lotation' => 50,
			'departure_time' => '15:10:00',
			'arrival_time' => '15:20:00',
		],
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
			'origin_station' => '21',
			'destiny_station' => '22',
			'lotation' => 50,
			'departure_time' => '21:00:00',
			'arrival_time' => '21:10:00',
		],
		[
			'origin_station' => '22',
			'destiny_station' => '01',
			'lotation' => 50,
			'departure_time' => '21:10:00',
			'arrival_time' => '21:20:00',
		],
		[
			'origin_station' => '01',
			'destiny_station' => '12',
			'lotation' => 50,
			'departure_time' => '21:20:00',
			'arrival_time' => '21:30:00',
		],
		[
			'origin_station' => '12',
			'destiny_station' => '11',
			'lotation' => 50,
			'departure_time' => '21:30:00',
			'arrival_time' => '21:40:00',
		],










		// C - B
			// first voyage
		[
			'origin_station' => '31',
			'destiny_station' => '32',
			'lotation' => 50,
			'departure_time' => '09:00:00',
			'arrival_time' => '09:10:00',
		],
		[
			'origin_station' => '32',
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
			'origin_station' => '31',
			'destiny_station' => '32',
			'lotation' => 50,
			'departure_time' => '15:00:00',
			'arrival_time' => '15:10:00',
		],
		[
			'origin_station' => '32',
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
			'origin_station' => '31',
			'destiny_station' => '32',
			'lotation' => 50,
			'departure_time' => '21:00:00',
			'arrival_time' => '21:10:00',
		],
		[
			'origin_station' => '32',
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






		// C - A
			// first voyage
		[
			'origin_station' => '31',
			'destiny_station' => '32',
			'lotation' => 50,
			'departure_time' => '09:00:00',
			'arrival_time' => '09:10:00',
		],
		[
			'origin_station' => '32',
			'destiny_station' => '01',
			'lotation' => 50,
			'departure_time' => '09:10:00',
			'arrival_time' => '09:20:00',
		],
		[
			'origin_station' => '01',
			'destiny_station' => '12',
			'lotation' => 50,
			'departure_time' => '09:20:00',
			'arrival_time' => '09:30:00',
		],
		[
			'origin_station' => '12',
			'destiny_station' => '11',
			'lotation' => 50,
			'departure_time' => '09:30:00',
			'arrival_time' => '09:40:00',
		],
			// second voyage
		[
			'origin_station' => '31',
			'destiny_station' => '32',
			'lotation' => 50,
			'departure_time' => '15:00:00',
			'arrival_time' => '15:10:00',
		],
		[
			'origin_station' => '32',
			'destiny_station' => '01',
			'lotation' => 50,
			'departure_time' => '15:10:00',
			'arrival_time' => '15:20:00',
		],
		[
			'origin_station' => '01',
			'destiny_station' => '12',
			'lotation' => 50,
			'departure_time' => '15:20:00',
			'arrival_time' => '15:30:00',
		],
		[
			'origin_station' => '12',
			'destiny_station' => '11',
			'lotation' => 50,
			'departure_time' => '15:30:00',
			'arrival_time' => '15:40:00',
		],
			// third voyage
		[
			'origin_station' => '31',
			'destiny_station' => '32',
			'lotation' => 50,
			'departure_time' => '21:00:00',
			'arrival_time' => '21:10:00',
		],
		[
			'origin_station' => '32',
			'destiny_station' => '01',
			'lotation' => 50,
			'departure_time' => '21:10:00',
			'arrival_time' => '21:20:00',
		],
		[
			'origin_station' => '01',
			'destiny_station' => '12',
			'lotation' => 50,
			'departure_time' => '21:20:00',
			'arrival_time' => '21:30:00',
		],
		[
			'origin_station' => '12',
			'destiny_station' => '11',
			'lotation' => 50,
			'departure_time' => '21:30:00',
			'arrival_time' => '21:40:00',
		],





		// A - C
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
	],
	'travel_trains' => [
		'_truncate' => true,
		'_defaults' => [],
	],
	'tickets' => [
		'_truncate' => true,
		'_defaults' => ['created' => '2016-06-06 01:00:00',
			'modified' => '2016-06-06 01:00:00'],
		[
			'id_users' => 1,
			'origin_station' => '11',
			'destiny_station' => '12',
			'qr_code' => 'TO IMPROVE0',
			'used' => false,
			'departure_time' => '2016-10-06 09:00:00',
			'arrival_time' => '2016-10-06 09:10:00',
		],
		[
			'id_users' => 1,
			'origin_station' => '21',
			'destiny_station' => '22',
			'qr_code' => 'TO IMPROVE1',
			'used' => true,
			'departure_time' => '2015-10-06 21:00:00',
			'arrival_time' => '2015-10-06 21:10:00',
		],
		[
			'id_users' => 1,
			'origin_station' => '22',
			'destiny_station' => '01',
			'qr_code' => 'TO IMPROVE2',
			'used' => true,
			'departure_time' => '2016-10-06 21:10:00',
			'arrival_time' => '2016-10-06 21:20:00',
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
		],
		[
			'name_station1' => '11',
			'name_station2' => '01',
			'route' => serialize(['11', '12', '01']),
		],
		[
			'name_station1' => '11',
			'name_station2' => '32',
			'route' => serialize(['11', '12', '01', '32']),
		],
		[
			'name_station1' => '11',
			'name_station2' => '31',
			'route' => serialize(['11', '12', '01', '32', '31']),
		],
		[
			'name_station1' => '11',
			'name_station2' => '22',
			'route' => serialize(['11', '12', '01', '22']),
		],
		[
			'name_station1' => '11',
			'name_station2' => '21',
			'route' => serialize(['11', '12', '01', '22', '21']),
		],
			// station 21 to anothers
		[
			'name_station1' => '21',
			'name_station2' => '22',
			'route' => serialize(['21', '22']),
		],
		[
			'name_station1' => '21',
			'name_station2' => '01',
			'route' => serialize(['21', '22', '01']),
		],
		[
			'name_station1' => '21',
			'name_station2' => '32',
			'route' => serialize(['21', '22', '01', '32']),
		],
		[
			'name_station1' => '21',
			'name_station2' => '31',
			'route' => serialize(['21', '22', '01', '32', '31']),
		],
		[
			'name_station1' => '21',
			'name_station2' => '12',
			'route' => serialize(['21', '22', '01', '12']),
		],
		[
			'name_station1' => '21',
			'name_station2' => '11',
			'route' => serialize(['21', '22', '01', '12', '11']),
		],
			// station 31 to anothers
		[
			'name_station1' => '31',
			'name_station2' => '32',
			'route' => serialize(['31', '32']),
		],
		[
			'name_station1' => '31',
			'name_station2' => '01',
			'route' => serialize(['31', '32', '01']),
		],
		[
			'name_station1' => '31',
			'name_station2' => '22',
			'route' => serialize(['31', '32', '01', '22']),
		],
		[
			'name_station1' => '31',
			'name_station2' => '21',
			'route' => serialize(['31', '32', '01', '22', '21']),
		],
		[
			'name_station1' => '31',
			'name_station2' => '12',
			'route' => serialize(['31', '32', '01', '12']),
		],
		[
			'name_station1' => '31',
			'name_station2' => '11',
			'route' => serialize(['31', '32', '01', '12', '11']),
		],
			// station 12 to anothers
		[
			'name_station1' => '12',
			'name_station2' => '11',
			'route' => serialize(['12', '11']),
		],
		[
			'name_station1' => '12',
			'name_station2' => '01',
			'route' => serialize(['12', '01']),
		],
		[
			'name_station1' => '12',
			'name_station2' => '32',
			'route' => serialize(['12', '01', '32']),
		],
		[
			'name_station1' => '12',
			'name_station2' => '31',
			'route' => serialize(['12', '01', '32', '31']),
		],
		[
			'name_station1' => '12',
			'name_station2' => '22',
			'route' => serialize(['12', '01', '22']),
		],
		[
			'name_station1' => '12',
			'name_station2' => '21',
			'route' => serialize(['12', '01', '22', '21']),
		],
			// station 22 to anothers
		[
			'name_station1' => '22',
			'name_station2' => '21',
			'route' => serialize(['22', '21']),
		],
		[
			'name_station1' => '22',
			'name_station2' => '01',
			'route' => serialize(['22', '01']),
		],
		[
			'name_station1' => '22',
			'name_station2' => '32',
			'route' => serialize(['22', '01', '32']),
		],
		[
			'name_station1' => '22',
			'name_station2' => '31',
			'route' => serialize(['22', '01', '32', '31']),
		],
		[
			'name_station1' => '22',
			'name_station2' => '12',
			'route' => serialize(['22', '01', '12']),
		],
		[
			'name_station1' => '22',
			'name_station2' => '11',
			'route' => serialize(['22', '01', '12', '11']),
		],
			// station 32 to anothers
		[
			'name_station1' => '32',
			'name_station2' => '31',
			'route' => serialize(['32', '31']),
		],
		[
			'name_station1' => '32',
			'name_station2' => '01',
			'route' => serialize(['32', '01']),
		],
		[
			'name_station1' => '32',
			'name_station2' => '22',
			'route' => serialize(['32', '01', '22']),
		],
		[
			'name_station1' => '32',
			'name_station2' => '21',
			'route' => serialize(['32', '01', '22', '21']),
		],
		[
			'name_station1' => '32',
			'name_station2' => '12',
			'route' => serialize(['32', '01', '12']),
		],
		[
			'name_station1' => '32',
			'name_station2' => '11',
			'route' => serialize(['32', '01', '12', '11']),
		],
			// station 01 to anothers
		[
			'name_station1' => '01',
			'name_station2' => '12',
			'route' => serialize(['01', '12']),
		],
		[
			'name_station1' => '01',
			'name_station2' => '22',
			'route' => serialize(['01', '22']),
		],
		[
			'name_station1' => '01',
			'name_station2' => '32',
			'route' => serialize(['01', '32']),
		],
		[
			'name_station1' => '01',
			'name_station2' => '11',
			'route' => serialize(['01', '12', '11']),
		],
		[
			'name_station1' => '01',
			'name_station2' => '21',
			'route' => serialize(['01', '22', '21']),
		],
		[
			'name_station1' => '01',
			'name_station2' => '31',
			'route' => serialize(['01', '32', '31']),
		],
	],
];

for ($i = 1; $i < 73; $i++) {
	for ($a = 1; $a < 31; $a++) {
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


$this->importTables($data);
