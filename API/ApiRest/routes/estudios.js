const express = require('express');
const router = express.Router();
const estudiosSchema = require('../controllers/estudios');

//Crear nuevos estudios
router.post("/estudios", (req, res) => {
    const anuncio = estudiosSchema(req.body);
    anuncio
    .save()
    .then((data) => {
        res.json(data);
        console.log("\nNuevos Estudios: \n"+data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("Error post /api/estudios: "+err);
    })
});

//Obtener Estudios
router.get("/estudios", (req, res) => {
    estudiosSchema
    .find()
    .then((data) => {
        res.json(data);
        console.log("\nTodos los estudios:\n" + data);
    })
    .catch((err) => {
        res.json({message: err});
        console.error("Error get /api/estudios");
    })
});

//Actualizar estudios
router.put("/estudios/:id", (req, res) => {
    const {id} = req.params;
    const {id_usuario, nombre, centro, fecha_ini, fecha_fin} = req.body;
    estudiosSchema
    .updateOne({_id: id},{$set:{id_usuario, nombre, centro, fecha_ini, fecha_fin}})
    .then((data) => {
        res.json(data);
        console.log("\nEstudios actualizados\n" + data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("\nError update\n"+ err);
    })
});

//Eliminar estudios
router.delete("/estudios/:id", (req, res) => {
    const {id} = req.params;
    estudiosSchema
    .remove({_id: id})
    .then((data) => {
        res.json(data);
        console.log("\nEstudios eliminados\n"+data)
    })
    .catch((err) =>{
        res.json
    })
});
module.exports = router;
