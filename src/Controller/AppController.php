<?php
/**
 * CakePHP(tm) : Rapid Development Framework (http://cakephp.org)
 * Copyright (c) Cake Software Foundation, Inc. (http://cakefoundation.org)
 *
 * Licensed under The MIT License
 * For full copyright and license information, please see the LICENSE.txt
 * Redistributions of files must retain the above copyright notice.
 *
 * @copyright Copyright (c) Cake Software Foundation, Inc. (http://cakefoundation.org)
 * @link      http://cakephp.org CakePHP(tm) Project
 * @since     0.2.9
 * @license   http://www.opensource.org/licenses/mit-license.php MIT License
 */
namespace App\Controller;

use Cake\Controller\Controller;
use Cake\Event\Event;
use Cake\ORM\TableRegistry;

/**
 * Application Controller
 *
 * Add your application-wide methods in the class below, your controllers
 * will inherit them.
 *
 * @link http://book.cakephp.org/3.0/en/controllers.html#the-app-controller
 */
class AppController extends Controller
{

    /**
     * Initialization hook method.
     *
     * Use this method to add common initialization code like loading components.
     *
     * e.g. `$this->loadComponent('Security');`
     *
     * @return void
     */
    public function initialize()
    {
        parent::initialize();

        $this->loadComponent('RequestHandler');
        $this->loadComponent('Flash');
        /*$this->loadComponent('Auth', [
            'authenticate' => [
                'Basic' => [
                    'fields' => [
                        'username' => 'email',
                        'password' => 'password'
                    ]
                ]
            ],
            'unauthorizedRedirect' => false,
            'storage' => 'Memory',

            'loginAction' => [
                'controller' => 'Users',
                'action' => 'login'

            ]
        ]);*/
    }

    /*public function isCliente($user)
    {
        // Admin can access every action
        if (isset($user['role']) && $user['role'] === 'cliente') {
            return true;
        }
    
        // Default deny
        return false;
    }
    public function isPica($user)
    {
        // Admin can access every action
        if (isset($user['role']) && $user['role'] === 'pica') {
            return true;
        }
    
        // Default deny
        return false;
    }*/

    protected static function getRouteBetweenStations($station1, $station2, &$routeArray, &$stationsWithChangeOfTrain)
    {
        $routes = TableRegistry::get('Routes');
        $query = $routes->find()->where(['name_station1 =' => $station1, 'name_station2 =' => $station2])->toArray();
        $routeArray = unserialize($query[0]['route']);
        $stationsWithChangeOfTrain = unserialize($query[0]['change_train_stations']);
    }

    public function beforeFilter(Event $event)
    {
        //debug(gettype($this->Auth));
        //$this->Auth->allow(['index', 'view']);
        //$this->Auth->allow();
    }

    /**
     * Before render callback.
     *
     * @param \Cake\Event\Event $event The beforeRender event.
     * @return void
     */
    public function beforeRender(Event $event)
    {
        if (!array_key_exists('_serialize', $this->viewVars) &&
            in_array($this->response->type(), ['application/json', 'application/xml'])
        ) {
            $this->set('_serialize', true);
        }
    }
}
