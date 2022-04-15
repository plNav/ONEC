//Requerido y constantes
const fs = require('fs');
const path = require('path');
const port = process.env.PORT || 8081;
const mongoose = require('mongoose');
const express = require('express');
const rutaUsuarios = require('./routes/usuario');
const rutaCV = require('./routes/cv');
const app = express();
require('dotenv').config();

//MIDDLEWARE'S
app.use(express.urlencoded({extended:false}));
app.use(express.json());
app.use('/api',rutaUsuarios);
app.use('/api',rutaCV);


//Conexión DB
mongoose
.connect(process.env.URI)
//.connect("mongodb://127.0.0.1:27017/ONEC")
.then(() => console.log("Connected to MongoDB Atlas"))
.catch((err) => console.log(`Error al conectar con MongoDB: ${err}`));

//FAVICON
const favicon = require('serve-favicon');
app.get("/favicon.ico", () => 
app.use(favicon(path.join(__dirname, '/www/favicon.ico'))));

//Inicio servidor
app.listen(port, () => console.log(`Server running on port ${port}`));