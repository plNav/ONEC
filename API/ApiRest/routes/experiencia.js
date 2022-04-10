const express = require('express');
const router = express.Router();
const experienciaSchema = require('../controllers/experiencia');

//Crear nueva experiencia laboral
router.post("/experiencia", (req, res) => {
    const experiencia = experienciaSchema(req.body);
    experiencia
    .save()
    .then((data) => {
        res.json(data);
        console.log("\nNueva experiencia: \n"+data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("Error post /api/experiencia: "+err);
    })
});

//Obtener todas las experiencias laborales
router.get("/experiencia", (req, res) => {
    experienciaSchema
    .find()
    .then((data) => {
        res.json(data);
        console.log("\nTodas las experiencias:\n" + data);
    })
    .catch((err) => {
        res.json({message: err});
        console.error("Error get /api/experiencia");
    })
});

//Actualizar experiencia laboral
router.put("/experiencia/:id", (req, res) => {
    const {id} = req.params;
    const {id_usuario, tiempo} = req.body;
    experienciaSchema
    .updateOne({_id: id},{$set:{id_usuario, tiempo}})
    .then((data) => {
        res.json(data);
        console.log("\nExperiencia actualizada\n" + data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("\nError update\n"+ err);
    })
});

//Eliminar experiencia laboral
router.delete("/experiencia/:id", (req, res) => {
    const {id} = req.params;
    experienciaSchema
    .remove({_id: id})
    .then((data) => {
        res.json(data);
        console.log("\nExperiencia eliminada\n"+data)
    })
    .catch((err) =>{
        res.json
    })
});
module.exports = router;