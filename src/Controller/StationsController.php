<?php
namespace App\Controller;

use App\Controller\AppController;
use Cake\Error\Debugger;

/**
 * Stations Controller
 *
 * @property \App\Model\Table\StationsTable $Stations
 */
class StationsController extends AppController
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
        $this->set('stations', $this->paginate($this->Stations));
        $this->set('_serialize', ['stations']);
    }

    /**
     * View method
     *
     * @param string|null $id Station id.
     * @return void
     * @throws \Cake\Network\Exception\NotFoundException When record not found.
     */
    public function view($id = null)
    {
        $station = $this->Stations->get($id, [
            'contain' => []
        ]);
        $this->set('station', $station);
        $this->set('_serialize', ['station']);
    }

    /**
     * Add method
     *
     * @return void Redirects on successful add, renders view otherwise.
     */
    public function add()
    {
	//Debugger::dump($this->request->data);
	
	
        $station = $this->Stations->newEntity();
        if ($this->request->is('post')) {
            $station = $this->Stations->patchEntity($station, $this->request->data);
            if ($this->Stations->save($station)) {
                $this->Flash->success(__('The station has been saved.'));
                //return $this->redirect(['action' => 'index']);
	        $this->set('_serialize', ['station']);
            } else {
                $this->Flash->error(__('The station could not be saved. Please, try again.'));
            }
        }
        $this->set(compact('station'));
        $this->set('_serialize', ['station']);
    }

    /**
     * Edit method
     *
     * @param string|null $id Station id.
     * @return void Redirects on successful edit, renders view otherwise.
     * @throws \Cake\Network\Exception\NotFoundException When record not found.
     */
    public function edit($id = null)
    {
        $station = $this->Stations->get($id, [
            'contain' => []
        ]);
        if ($this->request->is(['patch', 'post', 'put'])) {
            $station = $this->Stations->patchEntity($station, $this->request->data);
            if ($this->Stations->save($station)) {
                $this->Flash->success(__('The station has been saved.'));
                return $this->redirect(['action' => 'index']);
            } else {
                $this->Flash->error(__('The station could not be saved. Please, try again.'));
            }
        }
        $this->set(compact('station'));
        $this->set('_serialize', ['station']);
    }

    /**
     * Delete method
     *
     * @param string|null $id Station id.
     * @return void Redirects to index.
     * @throws \Cake\Network\Exception\NotFoundException When record not found.
     */
    public function delete($id = null)
    {
        $this->request->allowMethod(['post', 'delete']);
        $station = $this->Stations->get($id);
        if ($this->Stations->delete($station)) {
            $this->Flash->success(__('The station has been deleted.'));
        } else {
            $this->Flash->error(__('The station could not be deleted. Please, try again.'));
        }
        return $this->redirect(['action' => 'index']);
    }
}
