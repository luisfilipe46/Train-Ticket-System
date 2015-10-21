<?php
namespace App\Controller;

use App\Controller\AppController;

/**
 * TravelTrains Controller
 *
 * @property \App\Model\Table\TravelTrainsTable $TravelTrains
 */
class TravelTrainsController extends AppController
{

    /**
     * Index method
     *
     * @return void
     */
    public function index()
    {
        $this->paginate = [
            'contain' => ['Timetables']
        ];
        $this->set('travelTrains', $this->paginate($this->TravelTrains));
        $this->set('_serialize', ['travelTrains']);
    }

    /**
     * View method
     *
     * @param string|null $id Travel Train id.
     * @return void
     * @throws \Cake\Network\Exception\NotFoundException When record not found.
     */
    public function view($id = null)
    {
        $travelTrain = $this->TravelTrains->get($id, [
            'contain' => ['Timetables']
        ]);
        $this->set('travelTrain', $travelTrain);
        $this->set('_serialize', ['travelTrain']);
    }

    /**
     * Add method
     *
     * @return void Redirects on successful add, renders view otherwise.
     */
    public function add()
    {
        $travelTrain = $this->TravelTrains->newEntity();
        if ($this->request->is('post')) {
            $travelTrain = $this->TravelTrains->patchEntity($travelTrain, $this->request->data);
            if ($this->TravelTrains->save($travelTrain)) {
                $this->Flash->success(__('The travel train has been saved.'));
                return $this->redirect(['action' => 'index']);
            } else {
                $this->Flash->error(__('The travel train could not be saved. Please, try again.'));
            }
        }
        $timetables = $this->TravelTrains->Timetables->find('list', ['limit' => 200]);
        $this->set(compact('travelTrain', 'timetables'));
        $this->set('_serialize', ['travelTrain']);
    }

    /**
     * Edit method
     *
     * @param string|null $id Travel Train id.
     * @return void Redirects on successful edit, renders view otherwise.
     * @throws \Cake\Network\Exception\NotFoundException When record not found.
     */
    public function edit($id = null)
    {
        $travelTrain = $this->TravelTrains->get($id, [
            'contain' => []
        ]);
        if ($this->request->is(['patch', 'post', 'put'])) {
            $travelTrain = $this->TravelTrains->patchEntity($travelTrain, $this->request->data);
            if ($this->TravelTrains->save($travelTrain)) {
                $this->Flash->success(__('The travel train has been saved.'));
                return $this->redirect(['action' => 'index']);
            } else {
                $this->Flash->error(__('The travel train could not be saved. Please, try again.'));
            }
        }
        $timetables = $this->TravelTrains->Timetables->find('list', ['limit' => 200]);
        $this->set(compact('travelTrain', 'timetables'));
        $this->set('_serialize', ['travelTrain']);
    }

    /**
     * Delete method
     *
     * @param string|null $id Travel Train id.
     * @return void Redirects to index.
     * @throws \Cake\Network\Exception\NotFoundException When record not found.
     */
    public function delete($id = null)
    {
        $this->request->allowMethod(['post', 'delete']);
        $travelTrain = $this->TravelTrains->get($id);
        if ($this->TravelTrains->delete($travelTrain)) {
            $this->Flash->success(__('The travel train has been deleted.'));
        } else {
            $this->Flash->error(__('The travel train could not be deleted. Please, try again.'));
        }
        return $this->redirect(['action' => 'index']);
    }
}
