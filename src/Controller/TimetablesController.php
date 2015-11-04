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

        $routes = TableRegistry::get('Routes');
        $query = $routes->find()->where(['name_station1 =' => $station1, 'name_station2 =' => $station2])->toArray();
        $routeArray = unserialize($query[0]['route']);
        for ($a = 0; $a < sizeof($routeArray)-1; ) {
            $origin_station = $routeArray[$a];
            $destiny_station = $routeArray[$a+1];
            $timetable_aux = $this->Timetables->find()->where(['origin_station =' => $origin_station, 'destiny_station =' => $destiny_station])->toArray();

            if ($a == 0)
                $departure_time = array();
            $arrival_time = array();

            //Debugger::dump($timetable_aux);
            //die;

            for ($i = 0; $i < sizeof($timetable_aux); $i++) {
                $arrival_time[] = $timetable_aux[$i]['arrival_time'];
                if ($a == 0)
                    $departure_time[] = $timetable_aux[$i]['departure_time'];
            }
            //Debugger::dump($departure_time);
            //Debugger::dump($arrival_time);

            $a++;


            if (($a+1) == sizeof($routeArray))
            {
                $this->addElementsToTimetablesArray($departure_time, $routeArray, $destiny_station, $arrival_time, $timetables, $ii);

            }
            elseif ($destiny_station == '01')
            {
                $this->addElementsToTimetablesArray($departure_time, $routeArray, $destiny_station, $arrival_time, $timetables, $ii);

                //Debugger::dump($timetables);

                for($aa = $a; $aa < sizeof($routeArray); $aa++)
                {
                    $routeArrayAux[] = $routeArray[$aa];
                }

                $a = 0;
                $routeArray = $routeArrayAux;
                //Debugger::dump($routeArray);

            }

        }


        $this->set('timetables', $timetables);
        $this->set('_serialize', ['timetables']);

    }

    public function timetableBetweenStations($station1, $station2)
    {
        $routes = TableRegistry::get('Routes');
        $query = $routes->find()->where(['name_station1 =' => $station1, 'name_station2 =' => $station2])->toArray();
        $routeArray = unserialize($query[0]['route']);
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
    private function addElementsToTimetablesArray($departure_time, $routeArray, $destiny_station, $arrival_time, &$timetables, &$ii)
    {
        for ($ii = 0; $ii < sizeof($departure_time); $ii++) {
            $timetables[] = [
                'origin_station' => $routeArray[0],
                'destiny_station' => $destiny_station,
                'departure_time' => $departure_time[$ii],
                'arrival_time' => $arrival_time[$ii]
            ];
        }
    }
}
