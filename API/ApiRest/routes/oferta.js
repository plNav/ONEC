const express = require('express');
const router = express.Router();
const ofertaSchema = require('../controllers/oferta');

//Crear nueva oferta
router.post("/oferta", (req, res) => {
    const oferta = ofertaSchema(req.body);
    oferta
    .save()
    .then((data) => {
        res.json(data);
        console.log("\nNueva oferta: \n"+data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("Error post /api/oferta: "+err);
    })
});

//Obtener ofertas
router.get("/oferta", (req, res) => {
    ofertaSchema
    .find()
    .then((data) => {
        res.json(data);
        console.log("\nTodas las ofertas:\n" + data);
    })
    .catch((err) => {
        res.json({message: err});
        console.error("Error get /api/ofertas");
    })
});

//Actualizar oferta
router.put("/oferta/:id", (req, res) => {
    const {id} = req.params;
    const {id_usuario, titulo, descripcion} = req.body;
    ofertaSchema
    .updateOne({_id: id},{$set:{id_usuario,titulo,descripcion}})
    .then((data) => {
        res.json(data);
        console.log("\nOferta actualizada\n" + data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("\nError update\n"+ err);
    })
});

//Eliminar oferta
router.delete("/oferta/:id", (req, res) => {
    const {id} = req.params;
    ofertaSchema
    .remove({_id: id})
    .then((data) => {
        res.json(data);
        console.log("\nOferta Eliminada\n"+data)
    })
    .catch((err) =>{
        res.json
    })
});
module.exports = router;