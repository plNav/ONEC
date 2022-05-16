const express = require('express');
const router = express.Router();
const cvSchema = require('../controllers/cv');
const ofertaSchema = require('../controllers/oferta');
const multer = require('multer');
const storage = multer.diskStorage({
    destination: function(req, file, callback) {
        callback(null, 'images')
    },
    filename: (req, file, cb) => {
      cb(null, file.originalname)
    }
});

const upload = multer({storage: storage})

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
        res.status(500).json({message : err});
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
    const {id_user, nombre, telefono, ubicacion, correo, experiencia, titulo, especialidad, habilidades, habilidadesLow} = req.body;
    cvSchema
    .updateOne({_id: id},{$set:{id_user, nombre, telefono, ubicacion, correo, experiencia, titulo, especialidad, habilidades, habilidadesLow}})
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

//Subir imagen
router.post( "/cv/upload/", upload.single("upload"),(req, res) => {
      console.log(req.file);
      console.log(req.body);
      return res.status(200).end();
    }
  );


//Obtener CVs Filtrados partiendo de una oferta
router.get("/cv/oferta/:id/:habilidadesReq", (req, res) => {
    const {id} = req.params;
    const {habilidadesReq} = req.params;
    ofertaSchema.findById(id).then((data) => {
       let titulo = data.titulo;
       let especialidad = data.especialidad;
       let experiencia = data.experiencia;
       let habilidades = data.habilidades;
       let habilidadesLow = data.habilidadesLow;
        if(titulo == null) {
           if (habilidadesReq != "S") {
                cvSchema
                .find()
                .where("habilidadesLow").in(habilidadesLow)
                .where("experiencia").gte(experiencia)
                .sort({experiencia : -1})
                .then((data) => {
                    res.json(data);
                })
                .catch((err) => {
                    res.json({message : err})
                })
           }else {
            cvSchema
                .find()
                .where("habilidadesLow").all(habilidadesLow)
                .where("experiencia").gte(experiencia)
                .sort({experiencia : -1})
                .then((data) => {
                    res.json(data);
                })
                .catch((err) => {
                    res.json({message : err})
                })
           }
        }
        else if(especialidad != null) {
           if (habilidadesReq != "S") {
                if (habilidades == null || habilidades == [] || habilidades.length == 0) {
                    cvSchema
                    .find({titulo : titulo,especialidad : especialidad})
                    .where("experiencia").gte(experiencia) //Mayor o igual
                    .sort({experiencia : -1})
                    .then((data) => {
                        res.json(data);
                    })
                    .catch((err) => {
                        res.json({message : err});
                    })
                }else {
                    cvSchema
                    .find({titulo : titulo,especialidad : especialidad})
                    .where("habilidades").in(habilidades)
                    .where("experiencia").gte(experiencia) //Mayor o igual
                    .sort({experiencia : -1})
                    .then((data) => {
                        res.json(data);
                    })
                    .catch((err) => {
                        res.json({message : err});
                    }) 
                }  
           }else {
                cvSchema
                .find({titulo : titulo,especialidad : especialidad})
                .where("habilidades").all(habilidades)
                .where("experiencia").gte(experiencia) //Mayor o igual
                .sort({experiencia : -1})
                .then((data) => {
                 res.json(data);
                })
                .catch((err) => {
                    res.json({message : err});
                }) 
           }
        }else {
            if (habilidades == null || habilidades == [] || habilidades.length == 0) {
                //La oferta no tiene habilidades
                cvSchema
                .find({titulo : titulo})
                .where("experiencia").gte(experiencia)
                .sort({experiencia : -1})
                .then((data) => {
                    res.json(data);
                })
                .catch((err) => {
                    res.json({message : err});
                })
            }else {
                //La oferta tiene habilidades
                if (habilidadesReq != "S") {
                    console.log("entra bien");
                    //usar in
                    cvSchema
                        .find({titulo : titulo})
                        .where("habilidadesLow").in(habilidadesLow)
                        .where("experiencia").gte(experiencia)
                        .sort({experiencia : -1})
                        .then((data) => {
                            res.json(data)
                            console.log("prueba\n" + data)
                        })
                        .catch((err) => {
                            res.json({message : err})
                        })
                }else {
                    //usar all
                    cvSchema
                .find({titulo : titulo})
                .where("habilidadesLow").all(habilidadesLow)
                .where("experiencia").gte(experiencia)
                .sort({experiencia : -1})
                .then((data) => {
                    res.json(data)
                })
                .catch((err) => {
                    res.json({message : err})
                })
                }
            }
        }   
       
    }).catch((err) => {
        res.json({message : err})
    })

    })


module.exports = router;
