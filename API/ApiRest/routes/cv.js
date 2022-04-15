const express = require('express');
const router = express.Router();
const cvSchema = require('../controllers/cv');


//Crear nuevo CV
router.post("/cv", (req, res) => {
    const cv = cvSchema(req.body);
    cv
    .save()
    .then((data) => {
        res.json(data);
        console.log("\nNuevo CV: \n"+data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("Error post /api/cv: "+err);
    })
});

//Obtener CVs
router.get("/cv", (req, res) => {
    cvSchema
    .find()
    .then((data) => {
        res.json(data);
        console.log("\nTodos los CV:\n" + data);
    })
    .catch((err) => {
        res.json({message: err});
        console.error("Error get /api/cv");
    })
});

//Obtener CV de un usuario
router.get("/cv/:id", (req, res) => {
    const {id} = req.params
    cvSchema
    .findById(id)
    .then((data) => {
        res.json(data);
        console.log("\nCV de usuario:\n" + data);
    })
    .catch((err) => {
        res.json({message: err});
        console.error("Error get /api/cv");
    })
});

//Actualizar CV
router.put("/cv/:id", (req, res) => {
    const {id} = req.params;
    const {id_user, foto_url, nombre, telefono, ubicacion, correo, experiencia, titulo, especialidad, habilidades} = req.body;
    cvSchema
    .updateOne({_id: id},{$set:{id_usuario,foto_url, nombre, telefono, ubicacion, correo, experiencia, titulo, especialidad, habilidades}})
    .then((data) => {
        res.json(data);
        console.log("\nCV actualizado\n" + data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("\nError update\n"+ err);
    })
});

//Eliminar CV
router.delete("/cv/:id", (req, res) => {
    const {id} = req.params;
    cvSchema
    .remove({_id: id})
    .then((data) => {
        res.json(data);
        console.log("\nCV Eliminado\n"+data)
    })
    .catch((err) =>{
        res.json({err : message})
    })
});

module.exports = router;
