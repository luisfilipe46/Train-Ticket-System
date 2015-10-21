<?php
namespace App\Controller;

use App\Controller\AppController;

/**
 * Timetables Controller
 *
 * @property \App\Model\Table\TimetablesTable $Timetables
 */
class TimetablesController extends AppController
{

    /**
     * Index method
     *
     * @return void
     */
    public function index()
    {
        $this->set('timetables', $this->paginate($this->Timetables));
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
}
