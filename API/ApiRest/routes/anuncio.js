const express = require('express');
const router = express.Router();
const anuncioSchema = require('../controllers/anuncio');
const { route } = require('./anunciosGuardados');

//Crear nuevo anuncio
router.post("/anuncio", (req, res) => {
    const anuncio = anuncioSchema(req.body);
    anuncio
    .save()
    .then((data) => {
        res.json(data);
        console.log("\nNuevo anuncio: \n"+data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("Error post /api/anuncio: "+err);
    })
});

//Obtener anuncios
router.get("/anuncio", (req, res) => {
    anuncioSchema
    .find()
    .then((data) => {
        res.json(data);
        console.log("\nTodos los anuncios:\n" + data);
    })
    .catch((err) => {
        res.json({message: err});
        console.error("Error get /api/anuncio");
    })
});

//Obtener puntuacion
router.get("/anuncio/puntuacion/:id" , (req, res) => {
    const {id} = req.params
    anuncioSchema
    .findById(id)
    .then((data) => {
        res.json(data.puntuacion)
        console.log("get puntuacion anuncio " + data.puntuacion)
    })
    .catch((err) => {
        res.json({message:err})
        console.log("Error get /api/anuncio/puntuacion")
    })
});

//Obtener anuncio específico
router.get("/anuncio/:id", (req, res) => {
    const {id} = req.params;
    anuncioSchema
        .findById(id)
        .then((data) =>{
            res.json(data);
            console.log(`\nAnuncio: \n ${data}`);
        })
        .catch((err) => {
            res.json({message:err});
            console.log(`Error get /api/anuncio/${id} : ${err}`);
        })
})

//Obtener anuncios de un usuario
router.get("/anuncio/usuario/:id", (req, res) => {
    console.log("entra")
    const {id} = req.params;
    anuncioSchema
        .find({id_user : id})
        .then((data) =>{
            res.json(data);
            console.log(`\nAnuncios de usuario: \n ${data}`);
        })
        .catch((err) => {
            res.json({message:err});
            console.log(`Error get /api/anuncio/usuario/${id} : ${err}`);
        })
})


//Actualizar anuncio
router.put("/anuncio/:id", (req, res) => {
    const {id} = req.params;
    const {id_user,categoria,nombre,descripcion,precio,precioPorHora,numVecesVisto,numVotos,puntuacion} = req.body;
    anuncioSchema
    .updateOne({_id: id},{$set:{id_user,categoria,nombre,descripcion,precio,precioPorHora,numVecesVisto,numVotos,puntuacion}})
    .then((data) => {
        res.json(data);
        console.log("\nAnuncio actualizado\n" + data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("\nError update\n"+ err);
    })
});

//Eliminar anuncio
router.delete("/anuncio/:id", (req, res) => {
    const {id} = req.params;
    anuncioSchema
    .remove({_id: id})
    .then((data) => {
        res.json(data);
        console.log("\nAnuncio eliminado\n"+data)
    })
    .catch((err) =>{
        res.json
    })
});


//Buscar anuncios
router.get("/anuncio/buscar/:campo",(req,res) => {
const {campo} = req.params
const campoSlit = campo.includes(" ") ? campo.split(" ").join("|") : null
console.log(campoSlit)
anuncioSchema
.find({
    $or: [
        {nombre : {$regex: '.*' + campo + ".*"}},
        {categoria : {$regex: '.*' + campo + ".*"}},
        {descripcion : {$regex: '.*' + campo + ".*"}}
    ]
})
.then((data) => {
    if (data.length != []) {
        res.json(data)
        console.log("Anuncios Buscados\n" +data);   
    }else if(data.length == [] && campoSlit == null) {
        res.json(data)
        console.log("Anuncios Buscados\n" +data);   
    }else {
        console.log("asdasd")
        anuncioSchema
        .find({
            $or : [
                {
                    nombre: {
                        $regex : campoSlit,
                        $options : "i"
                    }
                },
                {
                    categoria: {
                        $regex : campoSlit,
                        $options : "i"
                    }
                },
                {
                    descripcion : {
                        $regex : campoSlit,
                        $options : "i" 
                    }
                }
            ]
        })
        .then((data) => {
            res.json(data)
            console.log("Anuncios Buscados\n" +data);   
        })
        .catch((err) => {
            res.json({message : err})
            console.log("Error get /anuncio/buscar/:campo")
        })
    }
})
.catch((err) => {
    res.json({message:err})
    console.log("Error get /anuncio/buscar/:campo")
})

});

module.exports = router;