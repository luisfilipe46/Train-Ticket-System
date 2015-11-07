<?php
namespace App\Controller;

use App\Controller\AppController;
use Cake\Error\Debugger;
use Cake\ORM\TableRegistry;
use Cake\Event\Event;

/**
 * Tickets Controller
 *
 * @property \App\Model\Table\TicketsTable $Tickets
 */
class TicketsController extends AppController
{
    public function beforeFilter(Event $event) {
        parent::beforeFilter($event);
    }
    public function initialize()
    {
        parent::initialize();
        $this->loadComponent('RequestHandler');
        $this->loadComponent('Flash');
    }

    /**
     * Index method
     *
     * @return void
     */
    public function index()
    {
        if ($this->request->header('email') != null && $this->request->header('password') != null)
        {
            $users = TableRegistry::get('Users');
            $queryResultsInArray = $users->find()->select(['id'])->where(['email =' => $this->request->header('email'), 'password =' => $this->request->header('password')])->toArray();
            if (!empty($queryResultsInArray))
            {

                $tickets = $this->Tickets->find()->where(['id_users =' => $queryResultsInArray[0]['id']])->toArray();
                $this->set('tickets', $tickets);
                $this->set('_serialize', ['tickets']);
            }
            else {
                $this->response->statusCode(400);
            }

            return;
        }
        else
        {

            $this->response->statusCode(401);
        }

        /*
        $this->set('tickets', $this->Tickets->find('all'));
        $this->set('_serialize', ['tickets']);
        */
    }

    /**
     * View method
     *
     * @param string|null $id Ticket id.
     * @return void
     * @throws \Cake\Network\Exception\NotFoundException When record not found.
     */
    public function view($id = null)
    {
        $ticket = $this->Tickets->get($id, [
            'contain' => []
        ]);
        $this->set('ticket', $ticket);
        $this->set('_serialize', ['ticket']);
    }

    /**
     * Add method
     *
     * @return void Redirects on successful add, renders view otherwise.
     */
    public function add()
    {
        if ($this->request->header('email') != null && $this->request->header('password') != null && $this->request->is('post')) {
            $users = TableRegistry::get('Users');
            $queryResultsInArray = $users->find()->select(['id'])->where(['email =' => $this->request->header('email'), 'password =' => $this->request->header('password')])->toArray();
            $idUser = $queryResultsInArray[0]['id'];
            if (!empty($queryResultsInArray)) {

                $this->loadModel('TravelTrains');

                $travelTrains = TableRegistry::get('TravelTrains');
                $timetables = TableRegistry::get('Timetables');
                $day = $this->request->data('day');
                $departure_time = $this->request->data('departure_time');
                $origin_station = $this->request->data('origin_station');
                $destiny_station = $this->request->data('destiny_station');


                parent::getRouteBetweenStations($origin_station, $destiny_station, $routeArray, $stationsWithChangeOfTrain);


                $lastArrivalTime = $departure_time;
                for ($i = 0; $i < sizeof($routeArray)-1; $i++)
                {

                    $queryTimetablesResultsInArray = $timetables->find()->select(['id', 'lotation', 'price', 'arrival_time'])->where(['departure_time =' => $lastArrivalTime,
                        'origin_station =' => $routeArray[$i], 'destiny_station =' => $routeArray[$i+1]
                    ])->toArray();
                    if (empty($queryTimetablesResultsInArray))
                    {
                        $this->response->statusCode(400);
                        $this->set('error', 'departure hour is wrong');
                        return;
                    }
                    $idTimetable[] = $queryTimetablesResultsInArray[0]['id'];
                    $lotation[] = $queryTimetablesResultsInArray[0]['lotation'];
                    $price[] = $queryTimetablesResultsInArray[0]['price'];
                    $lastArrivalTime = $queryTimetablesResultsInArray[0]['arrival_time'];


                    $queryTravelTrainsResultsInArray = $travelTrains->find()->select(['id', 'passengers'])->where(['timetable_id =' => $idTimetable[$i], 'date =' => $day])->toArray();
                    if (empty($queryTravelTrainsResultsInArray))
                    {
                        $this->response->statusCode(400);
                        $this->set('error', 'day is wrong');
                        return;
                    }
                    $passengers[] = $queryTravelTrainsResultsInArray[0]['passengers'];
                    $idTravelTrains[] = $queryTravelTrainsResultsInArray[0]['id'];

                    if($lotation[$i] <= $passengers[$i])
                    {
                        $this->response->statusCode(400);
                        $this->set('error', 'no more tickets available');
                        return;
                    }
                }

                $randomInt = rand(1, 100);
                if ($randomInt >= 96)
                {
                    $this->response->statusCode(400);
                    $this->set('error', 'credit card rejected');
                    return;
                }
                for($i = 0; $i < sizeof($routeArray)-1; $i++) {
                    $query = $this->TravelTrains->query();
                    $result = $query
                        ->update()
                        ->set(
                            $query->newExpr('passengers = passengers + 1')
                        )
                        ->where([
                            'id' => $idTravelTrains[$i]
                        ])
                        ->execute();
                }
                $finalPrice = array_sum($price);

                $lastArrivalTime = $lastArrivalTime->i18nFormat('HH:mm:ss');

                $this->insertTicketInDatabase($origin_station, $dataForTicket, $destiny_station, $day, $departure_time, $lastArrivalTime, $idUser, $finalPrice, $ticket);

                $this->set(compact('ticket'));
                $this->set('_serialize', ['ticket']);
                return;
            }
            $this->response->statusCode(401);
            return;
        }
        $this->response->statusCode(400);
    }

    /**
     * Edit method
     *
     * @param string|null $id Ticket id.
     * @return void Redirects on successful edit, renders view otherwise.
     * @throws \Cake\Network\Exception\NotFoundException When record not found.
     */
    public function edit($id = null)
    {
        if ($this->request->header('email') != null && $this->request->header('password') != null) {
            $users = TableRegistry::get('Users');
            $queryResultsInArray = $users->find()->select(['id'])->where(['email =' => $this->request->header('email'),
                'password =' => $this->request->header('password'), 'role =' => 'pica'])->toArray();

            if (!empty($queryResultsInArray)) {

                if ($this->request->is(['put'])) {
                    $query = $this->Tickets->query();
                    $result = $query
                        ->update()
                        ->set(
                            $query->newExpr('used = true')
                        )
                        ->where([
                            'id' => $id
                        ])
                        ->execute();
                }
                return;
            }
        }
        $this->response->statusCode(401);
    }

    public function ticketsOfTravel($origin_station, $destiny_station, $day, $departure_time) {

        $timetables = TableRegistry::get('Timetables');

        if ($this->request->header('email') != null && $this->request->header('password') != null) {
            $users = TableRegistry::get('Users');
            $queryResultsInArray = $users->find()->select(['id'])->where(['email =' => $this->request->header('email'),
                'password =' => $this->request->header('password'), 'role =' => 'pica'])->toArray();

            if (!empty($queryResultsInArray)) {
                parent::getRouteBetweenStations($origin_station, $destiny_station, $routeArray, $stationsWithChangeOfTrain);

                $lastArrivalTime = $departure_time;
                $tickets = array();
                for ($i = 0; $i < sizeof($routeArray)-1; $i++)
                {

                    $queryTimetablesResultsInArray = $timetables->find()->select(['id', 'arrival_time', 'departure_time'])->where(['departure_time =' => $lastArrivalTime,
                        'origin_station =' => $routeArray[$i], 'destiny_station =' => $routeArray[$i+1]
                    ])->toArray();
                    if (empty($queryTimetablesResultsInArray))
                    {
                        $this->response->statusCode(400);
                        $this->set('error', 'departure hour is wrong');
                        return;
                    }
                    $lastArrivalTime = $queryTimetablesResultsInArray[0]['arrival_time'];
                    $lastDepartureTime = $queryTimetablesResultsInArray[0]['departure_time'];


                    $hour = $lastDepartureTime->i18nFormat('HH:mm:ss');
                    $datetime = new \DateTime($day . ' ' . $hour);

                    $ticket = $this->Tickets->find()->where(['origin_station =' => $routeArray[$i], 'departure_time =' => $datetime])->toArray();
                    if (!empty($ticket))
                        $tickets[] = $ticket;

                }

                $this->set('tickets', $tickets);
                $this->set('_serialize',['tickets']);
                return;
            }
        }
        $this->response->statusCode(401);
    }

/**
     * Delete method
     *
     * @param string|null $id Ticket id.
     * @return void Redirects to index.
     * @throws \Cake\Network\Exception\NotFoundException When record not found.
     */
    public function delete($id = null)
    {
        $this->request->allowMethod(['post', 'delete']);
        $ticket = $this->Tickets->get($id);
        if ($this->Tickets->delete($ticket)) {
            $this->Flash->success(__('The ticket has been deleted.'));
        } else {
            $this->Flash->error(__('The ticket could not be deleted. Please, try again.'));
        }
        return $this->redirect(['action' => 'index']);
    }

    private function insertTicketInDatabase($origin_station, &$dataForTicket, $destiny_station, $day, $departure_time, $arrival_time, $idUser, $price, &$ticket)
    {
        $dataForTicket['origin_station'] = $origin_station;
        $dataForTicket['destiny_station'] = $destiny_station;
        $dataForTicket['qr_code'] = 'TESTE' . rand(1, 100000);
        $dataForTicket['used'] = false;
        $dataForTicket['departure_time'] = new \DateTime($day . ' ' . $departure_time);
        $dataForTicket['arrival_time'] = new \DateTime($day . ' ' . $arrival_time);
        $dataForTicket['id_users'] = $idUser;
        $dataForTicket['price'] = $price;
        $ticket = $this->Tickets->newEntity();
        $ticket = $this->Tickets->patchEntity($ticket, $dataForTicket);
        if (!$this->Tickets->save($ticket)) {
            $this->response->statusCode(400);
        }
    }
}
