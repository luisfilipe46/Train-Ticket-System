<?php
namespace App\Controller;

use App\Controller\AppController;
use Cake\ORM\TableRegistry;
use Cake\Error\Debugger;

/**
 * CreditCards Controller
 *
 * @property \App\Model\Table\CreditCardsTable $CreditCards
 */
class CreditCardsController extends AppController
{
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
        //Debugger::dump($creditCard->user);
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
        if ($this->request->header('token') != null && $this->request->is('post')) {
            $users = TableRegistry::get('Users');
            $queryResultsInArray = $users->find()->select(['id'])->where(['token =' => $this->request->header('token'), 'role =' => 'cliente'])->toArray();
            if (!empty($queryResultsInArray)) {
                $data['id_user'] = $queryResultsInArray[0]['id'];
                $data['validity'] = new \DateTime($this->request->data['validity']);
                $data['number'] = $this->request->data['number'];
                $data['type'] = $this->request->data['type'];
                $creditCard = $this->CreditCards->newEntity();
                $creditCard = $this->CreditCards->patchEntity($creditCard, $data);
                if ($this->CreditCards->save($creditCard)) {
                    $this->response->statusCode(201);
                } else {
                    $this->response->statusCode(400);
                }

            } else {
                $this->response->statusCode(400);
            }
            $this->set(compact('creditCard'));
            $this->set('_serialize', ['creditCard']);
        }
        $this->response->statusCode(401);
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
