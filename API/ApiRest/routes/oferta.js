const express = require('express');
const router = express.Router();
const ofertaSchema = require('../controllers/oferta');


//Crear nueva Oferta
router.post("/oferta", (req, res) => {
    const oferta = ofertaSchema(req.body);
    oferta
    .save()
    .then((data) => {
        res.json(data);
        console.log("\nNueva Oferta: \n"+data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("Error post /api/oferta: "+err);
    })
});

//Obtener Ofertas
router.get("/oferta", (req, res) => {
    ofertaSchema
    .find()
    .then((data) => {
        res.json(data);
        console.log("\nTodas las Ofertas:\n" + data);
    })
    .catch((err) => {
        res.json({message: err});
        console.error("Error get /api/oferta");
    })
});

//Obtener ofertas de un usuario
router.get("/oferta/usuario/:id", (req, res) => {
    const {id} = req.params
    ofertaSchema
    .find({id_user : id})
    .then((data) => {
        res.json(data);
        console.log("\nOfertas de usuario:\n" + data);
    })
    .catch((err) => {
        res.json({message: err});
        console.error("Error get /api/oferta/usuario");
    })
});

//Obtener oferta por ID
router.get("/oferta/:id", (req, res) => {
    const {id} = req.params
    ofertaSchema
    .findById(id)
    .then((data) => {
        res.json(data);
        console.log("\noferta: :\n" + data);
    })
    .catch((err) => {
        res.json({message: err});
        console.error("Error get /api/oferta");
    })
});

//Actualizar Oferta
router.put("/oferta/:id", (req, res) => {
    const {id} = req.params;
    const {id_user, nombre, experiencia, titulo, especialidad, habilidades} = req.body;
    ofertaSchema
    .updateOne({_id: id},{$set:{id_user, nombre, experiencia, titulo, especialidad, habilidades}})
    .then((data) => {
        res.json(data);
        console.log("\nOferta actualizada\n" + data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("\nError update\n"+ err);
    })
});

//Eliminar Oferta
router.delete("/oferta/:id", (req, res) => {
    const {id} = req.params;
    ofertaSchema
    .remove({_id: id})
    .then((data) => {
        res.json(data);
        console.log("\nOferta eliminada\n"+data)
    })
    .catch((err) =>{
        res.json({message:err})
    })
});

module.exports = router;