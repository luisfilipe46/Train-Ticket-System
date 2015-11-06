<?php
namespace App\Controller;

use App\Controller\AppController;
use Cake\ORM\TableRegistry;
use Cake\Error\Debugger;

/**
 * Timetables Controller
 *
 * @property \App\Model\Table\TimetablesTable $Timetables
 */
class TimetablesController extends AppController
{


    public function initialize()
    {
        parent::initialize();
        $this->loadComponent('RequestHandler');
    }

    /**
     * Index method
     *
     * @return void
     */
    public function index()
    {
        $this->set('timetables', $this->Timetables->find('all'));
        $this->set('_serialize', ['timetables']);

    }

    public function timetableBetweenFinalStations($station1, $station2)
    {
        parent::getRouteBetweenStations($station1, $station2, $routeArray);
        $routeArrayInResponse = $routeArray;
        $price = 0.0;
        for ($a = 0; $a < sizeof($routeArray)-1; ) {
            $origin_station = $routeArray[$a];
            $destiny_station = $routeArray[$a+1];
            $timetable_aux = $this->Timetables->find()->where(['origin_station =' => $origin_station, 'destiny_station =' => $destiny_station])->toArray();
            $price = $price + $timetable_aux[0]['price'];

            if ($a == 0)
                $departure_time = array();
            $arrival_time = array();

            for ($i = 0; $i < sizeof($timetable_aux); $i++) {
                $arrival_time[] = $timetable_aux[$i]['arrival_time'];
                if ($a == 0)
                    $departure_time[] = $timetable_aux[$i]['departure_time'];
            }
            $a++;


            if (($a+1) == sizeof($routeArray))
            {
                $this->addElementsToTimetablesArray($departure_time, $routeArray, $destiny_station, $arrival_time, $price, $timetables, $ii);
                $price = 0.0;
            }
            elseif ($destiny_station == '01')
            {
                $this->addElementsToTimetablesArray($departure_time, $routeArray, $destiny_station, $arrival_time, $price, $timetables, $ii);
                $price = 0.0;

                for($aa = $a; $aa < sizeof($routeArray); $aa++)
                {
                    $routeArrayAux[] = $routeArray[$aa];
                }

                $a = 0;
                $routeArray = $routeArrayAux;
            }
        }

        $this->set(['timetables', 'routes'], [$timetables, $routeArrayInResponse]);
        $this->set('_serialize', ['timetables', 'routes']);
    }

    public function timetableBetweenStations($station1, $station2)
    {
        parent::getRouteBetweenStations($station1, $station2, $routeArray);
        for($i = 0; $i < sizeof($routeArray)-1; $i++)
        {
            $timetables[] = $this->Timetables->find()->where(['origin_station =' => $routeArray[$i], 'destiny_station =' => $routeArray[$i+1]]);
        }

        $this->set('timetables', $timetables);
        $this->set('_serialize', ['timetables']);
    }


    /**
     * View method
     *
     * @param string|null $id Timetable id.
     * @return void
     * @throws \Cake\Network\Exception\NotFoundException When record not found.
     */
    public function view($id = null)
    {
        $timetable = $this->Timetables->get($id, [
            'contain' => ['TravelTrains']
        ]);
        $this->set('timetable', $timetable);
        $this->set('_serialize', ['timetable']);
    }

    /**
     * Add method
     *
     * @return void Redirects on successful add, renders view otherwise.
     */
    public function add()
    {
        $timetable = $this->Timetables->newEntity();
        if ($this->request->is('post')) {
            $timetable = $this->Timetables->patchEntity($timetable, $this->request->data);
            if ($this->Timetables->save($timetable)) {
                $this->Flash->success(__('The timetable has been saved.'));
                return $this->redirect(['action' => 'index']);
            } else {
                $this->Flash->error(__('The timetable could not be saved. Please, try again.'));
            }
        }
        $this->set(compact('timetable'));
        $this->set('_serialize', ['timetable']);
    }

    /**
     * Edit method
     *
     * @param string|null $id Timetable id.
     * @return void Redirects on successful edit, renders view otherwise.
     * @throws \Cake\Network\Exception\NotFoundException When record not found.
     */
    public function edit($id = null)
    {
        $timetable = $this->Timetables->get($id, [
            'contain' => []
        ]);
        if ($this->request->is(['patch', 'post', 'put'])) {
            $timetable = $this->Timetables->patchEntity($timetable, $this->request->data);
            if ($this->Timetables->save($timetable)) {
                $this->Flash->success(__('The timetable has been saved.'));
                return $this->redirect(['action' => 'index']);
            } else {
                $this->Flash->error(__('The timetable could not be saved. Please, try again.'));
            }
        }
        $this->set(compact('timetable'));
        $this->set('_serialize', ['timetable']);
    }

    /**
     * Delete method
     *
     * @param string|null $id Timetable id.
     * @return void Redirects to index.
     * @throws \Cake\Network\Exception\NotFoundException When record not found.
     */
    public function delete($id = null)
    {
        $this->request->allowMethod(['post', 'delete']);
        $timetable = $this->Timetables->get($id);
        if ($this->Timetables->delete($timetable)) {
            $this->Flash->success(__('The timetable has been deleted.'));
        } else {
            $this->Flash->error(__('The timetable could not be deleted. Please, try again.'));
        }
        return $this->redirect(['action' => 'index']);
    }

    /**
     * @param $departure_time
     * @param $routeArray
     * @param $destiny_station
     * @param $arrival_time
     * @param $timetables
     * @param $ii
     */
    private function addElementsToTimetablesArray($departure_time, $routeArray, $destiny_station, $arrival_time, $price, &$timetables, &$ii)
    {
        for ($ii = 0; $ii < sizeof($departure_time); $ii++) {
            $timetables[] = [
                'origin_station' => $routeArray[0],
                'destiny_station' => $destiny_station,
                'departure_time' => $departure_time[$ii],
                'arrival_time' => $arrival_time[$ii],
                'price' => $price
            ];
        }
    }
}
