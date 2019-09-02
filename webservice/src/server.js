const express = require('express');
const cors = require('cors');
const bancodedados = require('./database');

const routes = require('./routes');

const server = express();

bancodedados.authenticate()
.then(() => console.log('Database Connected!'))
.catch(err => console.log('Error: ' + err));

server.use(cors());
server.use(express.json());
server.use(routes);

server.listen(3333);
