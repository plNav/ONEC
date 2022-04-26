const express = require('express');
const router = express.Router();
const anunciosGuardadosSchema = require('../controllers/anunciosGuardados');

//Crear nuevo anuncio Favorito
router.post("/anunciosGuardados", (req, res) => {
    const anuncio = anunciosGuardadosSchema(req.body);
    anuncio
    .save()
    .then((data) => {
        res.json(data);
        console.log("\nNuevo anuncio favorito: \n"+data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("Error post /api/anunciosGuardados: "+err);
    })
});


//Obterner anuncios Guardados de un usuario
router.get("/anunciosGuardados/usuario/:id", (req, res) => {
    const {id} = req.params;
    anunciosGuardadosSchema
        .find({id_user : id})
        .then((data) =>{
            res.json(data);
            console.log(`\nAnuncios favoritos de usuario: \n ${data}`);
        })
        .catch((err) => {
            res.json({message:err});
            console.log(`Error get /api/anunciosGuardados/${id} : ${err}`);
        })
})


//Obtener anuncio guardado específico
router.get("/anunciosGuardados/:id", (req, res) => {
    const {id} = req.params;
    anunciosGuardadosSchema
        .findById(id)
        .then((data) =>{
            res.json(data);
            console.log(`\nAnuncio favorito : \n ${data}`);
        })
        .catch((err) => {
            res.json({message:err});
            console.log("Error get /api/anunciosGuardados ");
        })
})


//Eliminar anuncio Favorito
router.delete("/anunciosGuardados/:id", (req, res) => {
    const {id} = req.params;
    anunciosGuardadosSchema
    .remove({_id: id})
    .then((data) => {
        res.json(data);
        console.log("\nAnuncio favorito eliminado\n"+data)
    })
    .catch((err) =>{
        res.json({message : err})
    })
});

module.exports = router;