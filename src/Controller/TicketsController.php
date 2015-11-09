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
        if ($this->request->header('token') != null)
        {
            $users = TableRegistry::get('Users');
            $queryResultsInArray = $users->find()
                ->select(['id'])
                ->where(['token =' => $this->request->header('token'), 'role =' => 'cliente'])
                ->toArray();
            if (!empty($queryResultsInArray))
            {
                $tickets = $this->Tickets->find()
                    ->where(['id_users =' => $queryResultsInArray[0]['id']])
                    ->order(['departure_time' => 'ASC', 'arrival_time' => 'ASC'])
                    ->toArray();
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
        if ($this->request->header('token') != null && $this->request->is('post')) {
            $users = TableRegistry::get('Users');
            $queryResultsInArray = $users->find()->select(['id'])->where(['token =' => $this->request->header('token'), 'role =' => 'cliente'])->toArray();
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
        if ($this->request->header('token') != null) {
            $users = TableRegistry::get('Users');
            $queryResultsInArray = $users->find()->select(['id'])->where(['token =' => $this->request->header('token'), 'role =' => 'pica'])->toArray();

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

        if ($this->request->header('token') != null) {
            $users = TableRegistry::get('Users');
            $queryResultsInArray = $users->find()->select(['id'])->where(['token =' => $this->request->header('token'), 'role =' => 'pica'])->toArray();

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
                    if (!empty($ticket)) {
                        for($a = 0; $a < sizeof($ticket); $a++)
                        {
                            $tickets[] = $ticket[$a];
                        }
                    }

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

    public function pubkey() {

        if ($this->request->header('token') != null) {
            $users = TableRegistry::get('Users');
            $queryResultsInArray = $users->find()->select(['id'])->where(['token =' => $this->request->header('token'), 'role =' => 'pica'])->toArray();

            if (!empty($queryResultsInArray)) {
                $fp = fopen("pubkey.pem", "r");
                $public_key = fread($fp, 10000);
                fclose($fp);
                $this->set(['public_key'], [$public_key]);
                $this->set(['_serialize'], ['public_key']);
                return;
            }
            $this->response->statusCode(400);
        }
        $this->response->statusCode(401);
    }

    private function insertTicketInDatabase($origin_station, &$dataForTicket, $destiny_station, $day, $departure_time, $arrival_time, $idUser, $price, &$ticket)
    {
        $dataForTicket['origin_station'] = $origin_station;
        $dataForTicket['destiny_station'] = $destiny_station;

        $signature = null;
        $toSign = $origin_station . " " . $destiny_station . " " . $day . " " . $departure_time . " " . $arrival_time . " " . $price . " " . $idUser . " " . rand(1, 99999999);

        // Read the private key from the file.
        $fp = fopen("privatekey.pem", "r");
        $priv_key = fread($fp, 10000);
        fclose($fp);
        $pkeyid = openssl_get_privatekey($priv_key);

        // Compute the signature using OPENSSL_ALGO_SHA1
        // by default.
        openssl_sign($toSign, $signature, $pkeyid, "sha1WithRSAEncryption");

        // Free the key.
        openssl_free_key($pkeyid);

        // At this point, you've got $signature which
        // contains the digital signature as a series of bytes.
        // If you need to include the signature on a URL
        // for a request to be sent to a REST API, use
        // PHP's bin2hex() function.

        $hex = bin2hex( $signature );
        $dataForTicket['qr_code'] = $toSign.' '.$hex;
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
