const express = require('express');
const router = express.Router();
const visualizacionesSchema = require('../controllers/visualizaciones');

//Crear nueva visualización
router.post("/visualizacion", (req, res) => {
    const anuncio = visualizacionesSchema(req.body);
    anuncio
    .save()
    .then((data) => {
        res.json(data);
        console.log("\nNueva visualización: \n"+data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("Error post /api/visualizacion: "+err);
    })
});


//Obterner visualizaciones de usuario de un anuncio
router.get("/visualizacion/UsuarioEnAnuncio/:id_anuncio/:id_usuario", (req, res) => {
    const {id_anuncio} = req.params;
    const {id_usuario} = req.params;
   visualizacionesSchema
   .find({
       id_usuario : id_usuario,
       id_anuncio : id_anuncio
   })
   .then((data) => {
       res.json(data);
       console.log("Visualizaciones de usuario en anuncio "+data)
   })
   .catch((err) => {
       res.json({message : err});
       console.log("Error get /api/visualizacion/UsuarioEnAnuncio")
   })
})


module.exports = router;