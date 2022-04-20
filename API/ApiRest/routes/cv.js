const express = require('express');
const cv = require('../controllers/cv');
const router = express.Router();
const cvSchema = require('../controllers/cv');
const ofertaSchema = require('../controllers/oferta');
const path = require('path');
const multer = require('multer');
var crypto;
var storage = multer.diskStorage({
    destination: './images/',
    filename: (req, file, cb) => {
      return crypto.pseudoRandomBytes(16, function(err, raw) {
        if (err) {
          console.log(`Error in diskStorage ${err}`)
          return cb(err);
        }
      //return cb(null, "" + (raw.toString('hex')) + ( pathD.extname(file.originalname)));
        return cb(null, file.originalname);
  
      });
    }
});


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

//Subir imagen
router.post( "/cv/upload/", multer({storage: storage}).single('upload'),(req, res) => {
      console.log(req.file);
      console.log(req.body);
      res.redirect("./images" + req.file.filename);
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
       let habilidades = data.habilidades
        if(especialidad != null) {
            cvSchema
            .find({titulo : titulo,especialidad : especialidad})
            .where("habilidades").all(habilidades) //El all nos devuelve las colecciones que contienen todos los elementos del array
            .where("experiencia").gte(experiencia)
            .then((data) => {
                if (data.length != []) {
                    res.json(data)
                }else if(habilidadesReq != "S"){
                    cvSchema
                    .find({titulo : titulo,especialidad : especialidad})
                    .where("habilidades").in(habilidades) //Este solo muestra los que incluyen algunos de los valores del array
                    .where("experiencia").gte(experiencia) //Mayor o igual
                    .then((data) => {
                        res.json(data)
                    })
                    .catch((err) => {
                        res.json({message : err})
                    })

                }else {
                    res.json(data)
                }
            })
            .catch((err) => {
                res.json({message : err})
            })
        }else {
           cvSchema
           .find({titulo : titulo,habilidades:habilidades})
           .where("experiencia").gte(experiencia)
           .then((data) => {
                if (data.length != []) {
                    res.json(data)
                }else if(habilidadesReq != "S"){
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
                }else {
                    res.json(data)
                }
           }) 
        }   
       
    }).catch((err) => {
        res.json({message : err})
    })

    })


module.exports = router;
