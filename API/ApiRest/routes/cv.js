const express = require('express');
const cv = require('../controllers/cv');
const router = express.Router();
const cvSchema = require('../controllers/cv');
const ofertaSchema = require('../controllers/oferta');


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

//Obtener CV por ID de CV
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


//Obtener CV por ID de usuario
router.get("/cv/usuario/:id", (req, res) => {
    const {id} = req.params;
    cvSchema
    .findOne({id_user : id}) //Buscamos solo uno
    .then((data) => {
        res.json(data)
    }).catch((err) => {
        res.json({message : err})
        console.log("Error get /api/cv/usuario");
    })
});

//Actualizar CV
router.put("/cv/:id", (req, res) => {
    const {id} = req.params;
    const {id_user, foto_url, nombre, telefono, ubicacion, correo, experiencia, titulo, especialidad, habilidades} = req.body;
    cvSchema
    .updateOne({_id: id},{$set:{id_user,foto_url, nombre, telefono, ubicacion, correo, experiencia, titulo, especialidad, habilidades}})
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



//Obtener CVs Filtrados partiendo de una oferta
router.get("/cv/oferta/:id", (req, res) => {
    const {id} = req.params;
    ofertaSchema.findById(id).then((data) => {
       let titulo = data.titulo;
       let especialidad = data.especialidad;
       let experiencia = data.experiencia;
       let habilidades = data.habilidades
        if(especialidad != null) {
            cvSchema
            .find({titulo : titulo})
            .find({especialidad : especialidad})
            .find({habilidades : habilidades})
            .where("experiencia").gte(experiencia)
            .then((data) => {
                if (data.length != []) {
                    res.json(data)
                }else {
                    cvSchema
                    .find({titulo : titulo})
                    .find({especialidad : especialidad})
                    .where("habilidades").in(habilidades)
                    .where("experiencia").gte(experiencia)
                    .then((data) => {
                        res.json(data)
                    })
                    .catch((err) => {
                        res.json({message : err})
                    })

                }
            })
            .catch((err) => {
                res.json({message : err})
            })
        }else {
           cvSchema
           .find({titulo : titulo})
           .find({habilidades : habilidades})
           .where("experiencia").gte(experiencia)
           .then((data) => {
                if (data.length != []) {
                    res.json(data)
                }else {
                    cvSchema
                    .find({titulo : titulo})
                    .where("habilidades").in(habilidades)
                    .where("experiencia").gte(experiencia)
                    .then((data) => {
                        res.json(data)
                    })
                    .catch((err) => {
                        res.json({message : err})
                    })
                }
           }) 
        }   
       
    }).catch((err) => {
        res.json({message : err})
    })

    })


module.exports = router;
