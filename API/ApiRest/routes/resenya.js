const express = require('express');
const router = express.Router();
const resenyaSchema = require('../controllers/resenya');

//Crear nueva reseña
router.post("/review", (req, res) => {
    const review = resenyaSchema(req.body);
    review
    .save()
    .then((data) => {
        res.json(data);
        console.log("\nNueva reseña: \n"+data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("Error post /api/review: "+err);
    })
});

//Obtener reseñas
router.get("/review", (req, res) => {
    resenyaSchema
    .find()
    .then((data) => {
        res.json(data);
        console.log("\nTodas las reseñas:\n" + data);
    })
    .catch((err) => {
        res.json({message: err});
        console.error("Error get /api/review");
    })
});

//Obtener reseñas de un anuncio
router.get("/review/anuncio/:id", (req, res) => {
    const {id} = req.params
    resenyaSchema
    .find({id_anuncio : id})
    .then((data) => {
        res.json(data);
        console.log("\nTodas las reseñas del anuncio:\n" + data);
    })
    .catch((err) => {
        res.json({message: err});
        console.error("Error get /api/review/anuncio");
    })
});


//Obtener reseña específica
router.get("/review/:id", (req, res) => {
    const {id} = req.params;
    resenyaSchema
        .findById(id)
        .then((data) =>{
            res.json(data);
            console.log(`\nReseña: \n ${data}`);
        })
        .catch((err) => {
            res.json({message:err});
            console.log(`Error get /api/review/${id} : ${err}`);
        })
})

//Obterner todas las reseñas de un usuario
router.get("/review/usuario/:id", (req, res) => {
    const {id} = req.params;
    resenyaSchema
        .find({id_user : id})
        .then((data) =>{
            res.json(data);
            console.log(`\nReseñas de un usuario: \n ${data}`);
        })
        .catch((err) => {
            res.json({message:err});
            console.log(`Error get /api/review/usuario/${id} : ${err}`);
        })
})

//Obtener las reseñas que tiene un usuario en un anuncio específico
router.get("/review/:id/:id_user", (req, res) => {
    const {id} = req.params;
    const {id_user} = req.params;
    resenyaSchema
    .find({id_anuncio : id, id_user : id_user})
    .then((data) => {
        res.json(data);
        console.log(`\nReseñas de un usuario en un anuncio: \n ${data}`)
    })
    .catch((err) => {
        res.json({message : err})
        console.log("Error al obtener reseñas de usuario de un anuncio " + err)
    })
});


//Actualizar reseña
router.put("/review/:id", (req, res) => {
    const {id} = req.params;
    const {id_user,id_anuncio,puntuacion,descripcion} = req.body;
    resenyaSchema
    .updateOne({_id: id},{$set:{id_user,id_anuncio,puntuacion,descripcion}})
    .then((data) => {
        res.json(data);
        console.log("\nReseña actualizada\n" + data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("\nError update\n"+ err);
    })
});

//Eliminar reseña
router.delete("/review/:id", (req, res) => {
    const {id} = req.params;
    resenyaSchema
    .remove({_id: id})
    .then((data) => {
        res.json(data);
        console.log("\nReseña eliminada\n"+data)
    })
    .catch((err) =>{
        res.json
    })
});

//Eliminar reseñas de un anuncio
router.delete("/review/anuncio/:id", (req, res) => {
    const {id} = req.params;
    resenyaSchema
    .deleteMany({id_anuncio: id})
    .then((data) => {
        res.json(data);
        console.log("\nReseña eliminada\n"+data)
    })
    .catch((err) =>{
        res.json({message:err})
        console.log("Error delete /review/anuncio/:id");
    })
});

//Obtener puntuación de reseñas
router.get("/review/anuncio/puntuacion/:id", (req, res) => {
    const {id} = req.params;
    resenyaSchema
    .find({id_anuncio : id})
    .then((data) => {
        if (data.length != []) {
            const puntuacion = data.map(review => review.puntuacion).reduce((a,b) => a + b, 0) / data.length
            console.log(puntuacion)
            res.json(Number.parseFloat(puntuacion.toFixed(1)));
            console.log("Media " + puntuacion)
        }else {
            res.json(0);
            console.log("Media 0")
        }
    })
    .catch((err) => {
       res.json({message : err});
       console.log("Error /review/puntuacion")
    })
});

module.exports = router;