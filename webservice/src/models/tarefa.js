const bancodedados = require('../database');
const Sequelize = require('sequelize');

const tarefa = bancodedados.define('tarefa', {
    // Colunas da tabela no bd
    descricao:{ // Titulo da tarefa
        type: Sequelize.STRING
    },
    flag: { // flag que representa se a tarefa ja foi concluida
        type: Sequelize.BOOLEAN
    },
    img64:{ // Titulo da tarefa
        type: Sequelize.TEXT
    }
});

tarefa.sync();

module.exports = tarefa;