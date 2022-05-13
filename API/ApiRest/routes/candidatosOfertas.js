const express = require('express');
const router = express.Router();
const candidatosOfertasSchema = require('../controllers/candidatosOfertas');

//añadir nuevo candidato a una oferta
router.post("/candidatosOfertas", (req, res) => {
    const candidato = candidatosOfertasSchema(req.body);
    candidato
    .save()
    .then((data) => {
        res.json(data);
        console.log("\nNuevo candidato añadido: \n"+data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("Error post /api/candidatosOfertas: "+err);
    })
});

//Obtener candidatos de una oferta
router.get("/candidatosOfertas/oferta/:id", (req, res) => {
    const {id} = req.params;
    candidatosOfertasSchema
    .find({id_oferta : id})
    .then((data) => {
        res.json(data);
        console.log("Candidatos de oferta indicada: \n" + data);
    })
    .catch((err) => {
        res.json({message : err});
        console.log("Error get /candidatosOfertas/oferta")
    })
});

//Obtener todas las ofertas de un candidato
router.get("/candidatosOfertas/candidato/:id", (req, res) => {
    const {id} = req.params;
    candidatosOfertasSchema
    .find({id_cv : id})
    .then((data) => {
        res.json(data);
        console.log("Ofertas de candidato indicado: \n" + data);
    })
    .catch((err) => {
        res.json({message : err});
        console.log("Error get /candidatosOfertas/candidatos")
    })
});

//Eliminar candidatosOfertas específico
router.delete("/candidatosOfertas/:id",(req,res) => {
    const {id} = req.params;
    candidatosOfertasSchema
    .remove({_id : id})
    .then((data) => {
        res.json(data);
        console.log("Respuesta api/candidatosOfertas "+data);
    })
    .catch((err) => {
        res.json({message:err});
        console.log("Error delete api/candidatosOfertas")
    })
});

//Eliminar ofertas de un candidato
router.delete("/candidatosOfertas/candidato/:id",(req, res) => {
    const {id} = req.params;
    candidatosOfertasSchema
    .deleteMany({id_cv : id})
    .then((data) => {
        res.json(data);
        console.log("Ofertas de candidato eliminadas:\n"+data);
    })
    .catch((err) => {
        res.json({message : err});
        console.log("Error delete /candidatosOfertas/candidato")
    });
});

//Eliminar candidatos de una oferta
router.delete("/candidatosOfertas/oferta/:id",(req, res) => {
    const {id} = req.params;
    candidatosOfertasSchema
    .deleteMany({id_oferta : id})
    .then((data) => {
        res.json(data);
        console.log("Candidatos de oferta eliminados:\n"+data);
    })
    .catch((err) => {
        res.json({message : err});
        console.log("Error delete /candidatosOfertas/oferta")
    });
});

module.exports = router;