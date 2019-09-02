const express = require('express');
const TarefaCtrl = require('./controllers/TarefaCtrl'); 

const routes = express.Router();

routes.post('/t', TarefaCtrl.salvar);
routes.get('/tarefaPendente', TarefaCtrl.buscarPendentes);
routes.get('/t', TarefaCtrl.buscarTodas);
routes.post('/t/:id', TarefaCtrl.updateImg);
// routes.post('/devs', DevController.store);

module.exports = routes;