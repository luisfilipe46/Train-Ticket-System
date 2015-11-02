<?php
namespace App\Controller;

use App\Controller\AppController;

/**
 * CreditCards Controller
 *
 * @property \App\Model\Table\CreditCardsTable $CreditCards
 */
class CreditCardsController extends AppController
{

    /**
     * Index method
     *
     * @return void
     */
    public function index()
    {
        $this->set('creditCards', $this->paginate($this->CreditCards));
        $this->set('_serialize', ['creditCards']);
    }

    /**
     * View method
     *
     * @param string|null $id Credit Card id.
     * @return void
     * @throws \Cake\Network\Exception\NotFoundException When record not found.
     */
    public function view($id = null)
    {
        $creditCard = $this->CreditCards->get($id, [
            'contain' => []
        ]);
        $this->set('creditCard', $creditCard);
        $this->set('_serialize', ['creditCard']);
    }

    /**
     * Add method
     *
     * @return void Redirects on successful add, renders view otherwise.
     */
    public function add()
    {
        $creditCard = $this->CreditCards->newEntity();
        if ($this->request->is('post')) {
            $creditCard = $this->CreditCards->patchEntity($creditCard, $this->request->data);
            if ($this->CreditCards->save($creditCard)) {
                $this->Flash->success(__('The credit card has been saved.'));
                return $this->redirect(['action' => 'index']);
            } else {
                $this->Flash->error(__('The credit card could not be saved. Please, try again.'));
            }
        }
        $this->set(compact('creditCard'));
        $this->set('_serialize', ['creditCard']);
    }

    /**
     * Edit method
     *
     * @param string|null $id Credit Card id.
     * @return void Redirects on successful edit, renders view otherwise.
     * @throws \Cake\Network\Exception\NotFoundException When record not found.
     */
    public function edit($id = null)
    {
        $creditCard = $this->CreditCards->get($id, [
            'contain' => []
        ]);
        if ($this->request->is(['patch', 'post', 'put'])) {
            $creditCard = $this->CreditCards->patchEntity($creditCard, $this->request->data);
            if ($this->CreditCards->save($creditCard)) {
                $this->Flash->success(__('The credit card has been saved.'));
                return $this->redirect(['action' => 'index']);
            } else {
                $this->Flash->error(__('The credit card could not be saved. Please, try again.'));
            }
        }
        $this->set(compact('creditCard'));
        $this->set('_serialize', ['creditCard']);
    }

    /**
     * Delete method
     *
     * @param string|null $id Credit Card id.
     * @return void Redirects to index.
     * @throws \Cake\Network\Exception\NotFoundException When record not found.
     */
    public function delete($id = null)
    {
        $this->request->allowMethod(['post', 'delete']);
        $creditCard = $this->CreditCards->get($id);
        if ($this->CreditCards->delete($creditCard)) {
            $this->Flash->success(__('The credit card has been deleted.'));
        } else {
            $this->Flash->error(__('The credit card could not be deleted. Please, try again.'));
        }
        return $this->redirect(['action' => 'index']);
    }
}
