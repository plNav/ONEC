const express = require('express');
const router = express.Router();
const anuncioSchema = require('../controllers/anuncio');
const path = require('path');

//Crear nuevo anuncio
router.post("/anuncio", (req, res) => {
    const anuncio = anuncioSchema(req.body);
    anuncio
    .save()
    .then((data) => {
        res.json(data);
        console.log("\nNuevo anuncio: \n"+data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("Error post /api/anuncio: "+err);
    })
});

//Obtener anuncios
router.get("/anuncio", (req, res) => {
    anuncioSchema
    .find()
    .then((data) => {
        res.json(data);
        console.log("\nTodos los anuncios:\n" + data);
    })
    .catch((err) => {
        res.json({message: err});
        console.error("Error get /api/anuncio");
    })
});

//Obtener anuncio específico
router.get("/anuncio/:id", (req, res) => {
    const {id} = req.params;
    anuncioSchema
        .findById(id)
        .then((data) =>{
            res.json(data);
            console.log(`\nAnuncio: \n ${data}`);
        })
        .catch((err) => {
            res.json({message:err});
            console.log(`Error get /api/anuncio/${id} : ${err}`);
        })
})

//Obterner anuncios de un usuario
router.get("/anuncio/usuario/:id", (req, res) => {
    const {id} = req.params;
    anuncioSchema
        .find({id_user : id})
        .then((data) =>{
            res.json(data);
            console.log(`\nAnuncios de usuario: \n ${data}`);
        })
        .catch((err) => {
            res.json({message:err});
            console.log(`Error get /api/anuncio/usuario/${id} : ${err}`);
        })
})


//Actualizar anuncio
router.put("/anuncio/:id", (req, res) => {
    const {id} = req.params;
    const {id_user,categoria,nombre,descripcion,votos} = req.body;
    anuncioSchema
    .updateOne({_id: id},{$set:{id_user,categoria,nombre,descripcion,votos}})
    .then((data) => {
        res.json(data);
        console.log("\nAnuncio actualizado\n" + data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("\nError update\n"+ err);
    })
});

//Eliminar anuncio
router.delete("/anuncio/:id", (req, res) => {
    const {id} = req.params;
    anuncioSchema
    .remove({_id: id})
    .then((data) => {
        res.json(data);
        console.log("\nAnuncio eliminado\n"+data)
    })
    .catch((err) =>{
        res.json
    })
});

module.exports = router;