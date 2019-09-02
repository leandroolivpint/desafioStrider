const Sequelize = require('sequelize'); // variavel para controlar qualquer banco relacional

const bancodedados = new Sequelize("desafiobd", "root", "root", {
    host: "localhost", // Endereco do servidor mysql
    dialect: "mysql" // Tipo de banco que estou usando
});

module.exports = bancodedados;