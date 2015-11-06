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
        /*else
        {

            $this->response->statusCode(401);
        }*/

        $this->set('tickets', $this->paginate($this->Tickets));
        $this->set('_serialize', ['tickets']);
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
                $arrival_time = $this->request->data('arrival_time');
                $departure_time = $this->request->data('departure_time');
                $origin_station = $this->request->data('origin_station');
                $destiny_station = $this->request->data('destiny_station');


                parent::getRouteBetweenStations($origin_station, $destiny_station, $routeArray, $stationsWithChangeOfTrain);


                if (sizeof($routeArray) == 2) {

                    $queryTimetablesResultsInArray = $timetables->find()->select(['id', 'lotation', 'price'])->where(['departure_time =' => $departure_time, 'arrival_time =' => $arrival_time,
                        'origin_station =' => $origin_station, 'destiny_station =' => $destiny_station
                    ])->toArray();
                    $idTimetable = $queryTimetablesResultsInArray[0]['id'];
                    $lotation = $queryTimetablesResultsInArray[0]['lotation'];
                    $price = $queryTimetablesResultsInArray[0]['price'];

                    $queryTravelTrainsResultsInArray = $travelTrains->find()->select(['id', 'passengers'])->where(['timetable_id =' => $idTimetable, 'date =' => $day])->toArray();
                    $passengers = $queryTravelTrainsResultsInArray[0]['passengers'];
                    $idTravelTrains = $queryTravelTrainsResultsInArray[0]['id'];
                    if ($lotation > $passengers) {

                        $randomInt = rand(1, 100);
                        if ($randomInt >= 96)
                        {
                            $this->response->statusCode(400);
                            $this->set('error', 'credit card rejected');
                            return;
                        }

                        $query = $this->TravelTrains->query();
                        $result = $query
                            ->update()
                            ->set(
                                $query->newExpr('passengers = passengers + 1')
                            )
                            ->where([
                                'id' => $idTravelTrains
                            ])
                            ->execute();


                        $this->insertTicketInDatabase($origin_station, $dataForTicket, $destiny_station, $day, $departure_time, $arrival_time, $idUser, $price);
                        return;
                    }
                    else {
                        $this->response->statusCode(400);
                        $this->set('error', 'no more tickets available');
                        return;
                    }
                } elseif (sizeof($routeArray) == 3) {
                    $queryTimetablesResultsInArray1 = $timetables->find()->select(['id', 'lotation', 'price'])->where(['departure_time =' => $departure_time,
                        'origin_station =' => $origin_station, 'destiny_station =' => $routeArray[1]
                    ])->toArray();
                    $queryTimetablesResultsInArray2 = $timetables->find()->select(['id', 'lotation', 'price'])->where(['arrival_time =' => $arrival_time,
                        'origin_station =' => $routeArray[1], 'destiny_station =' => $destiny_station
                    ])->toArray();
                    $idTimetable1 = $queryTimetablesResultsInArray1[0]['id'];
                    $lotation1 = $queryTimetablesResultsInArray1[0]['lotation'];
                    $price1 = $queryTimetablesResultsInArray1[0]['price'];

                    $idTimetable2 = $queryTimetablesResultsInArray2[0]['id'];
                    $lotation2 = $queryTimetablesResultsInArray2[0]['lotation'];
                    $price2 = $queryTimetablesResultsInArray2[0]['price'];

                    $queryTravelTrainsResultsInArray1 = $travelTrains->find()->select(['id', 'passengers'])->where(['timetable_id =' => $idTimetable1, 'date =' => $day])->toArray();
                    $passengers1 = $queryTravelTrainsResultsInArray1[0]['passengers'];
                    $idTravelTrains1 = $queryTravelTrainsResultsInArray1[0]['id'];

                    $queryTravelTrainsResultsInArray2 = $travelTrains->find()->select(['id', 'passengers'])->where(['timetable_id =' => $idTimetable2, 'date =' => $day])->toArray();
                    $passengers2 = $queryTravelTrainsResultsInArray2[0]['passengers'];
                    $idTravelTrains2 = $queryTravelTrainsResultsInArray2[0]['id'];

                    if ($lotation1 > $passengers1 && $lotation2 > $passengers2) {
                        $randomInt = rand(1, 100);
                        if ($randomInt >= 96)
                        {
                            $this->response->statusCode(400);
                            $this->set('error', 'credit card rejected');
                            return;
                        }

                        $query1 = $this->TravelTrains->query();
                        $result1 = $query1
                            ->update()
                            ->set(
                                $query1->newExpr('passengers = passengers + 1')
                            )
                            ->where([
                                'id' => $idTravelTrains1
                            ])
                            ->execute();

                        $query2 = $this->TravelTrains->query();
                        $result2 = $query2
                            ->update()
                            ->set(
                                $query2->newExpr('passengers = passengers + 1')
                            )
                            ->where([
                                'id' => $idTravelTrains2
                            ])
                            ->execute();


                        $this->insertTicketInDatabase($origin_station, $dataForTicket, $destiny_station, $day, $departure_time, $arrival_time, $idUser, $price1+$price2);
                        return;
                    }
                    else {
                        $this->response->statusCode(400);
                        $this->set('error', 'no more tickets available');
                        return;
                    }
                }
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

    public function ticketsOfTravel($station1, $station2, $day, $departure_time) {


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

    private function insertTicketInDatabase($origin_station, &$dataForTicket, $destiny_station, $day, $departure_time, $arrival_time, $idUser, $price)
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

        $this->set(compact('ticket'));
        $this->set('_serialize', ['ticket']);
    }
}
