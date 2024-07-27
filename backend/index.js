//  Importation et initialisation
const express = require('express');
const app = express();
const port = process.env.port || 3000;

//Middleware
app.use(express.json());

//Lancer le serveur
app.listen(port, () => {
    console.log('Serveur en écoute sur le port ' + port)
})