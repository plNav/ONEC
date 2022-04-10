const express = require('express');
const router = express.Router();
const usuariosSchema = require('../controllers/usuario');
const path = require('path');
const crypto = require('crypto');
const sendEmail = require('../utils/sendEmail');

//Crear nuevo usuario
router.post("/usuario", (req, res) => {
    const usuario = usuariosSchema(req.body);
    usuario
    .save()
    .then((data) => {
        res.json(data);
        console.log("\nNuevo usuario: \n"+data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("Error post /api/usuario: "+err);
    })
});

//Obtener usuarios
router.get("/usuario", (req, res) => {
    usuariosSchema
    .find()
    .then((data) => {
        res.json(data);
        console.log("\nTodos los usuarios:\n" + data);
    })
    .catch((err) => {
        res.json({message: err});
        console.error("Error get /api/usuario");
    })
});

//Obtener usuario específico
router.get("/usuario/:id", (req, res) => {
    const {id} = req.params;
    usuariosSchema
        .findById(id)
        .then((data) =>{
            res.json(data);
            console.log(`\nUsuario: \n ${data}`);
        })
        .catch((err) => {
            res.json({message:err});
            console.log(`Error get /api/usuario/${id} : ${err}`);
        })
})


//Actualizar usuario
router.put("/usuario/:id", (req, res) => {
    const {id} = req.params;
    const {email, password} = req.body;
    usuariosSchema
    .updateOne({_id: id},{$set:{email,password}})
    .then((data) => {
        res.json(data);
        console.log("\nUsuario actualizado\n" + data);
    })
    .catch((err) => {
        res.json({message:err});
        console.error("\nError update\n"+ err);
    })
});

//Eliminar usuario
router.delete("/usuario/:id", (req, res) => {
    const {id} = req.params;
    usuariosSchema
    .remove({_id: id})
    .then((data) => {
        res.json(data);
        console.log("\nUsuario eliminado\n"+data)
    })
    .catch((err) =>{
        res.json
    })
});

//Comprobar si un email ya existe en la BD
router.get("/usuario/email/:email", (req, res) => {
    const {email} = req.params;
    usuariosSchema
        .find({email:email})
        .then((data) =>{
            if(data.length != [])res.json(true);
            else res.json(false)
            console.log(`\nExiste ya ${email} en la BBDD? : ${(data.length != [] ? "SI" : "NO")} existe`);
        })
        .catch((err) => {
            res.json({message:err});
            console.log(`Error get /usuario/${email} : ${err}`);
        })
})

//Validar Email y password
router.get("/usuario/validar/:email/:passw", (req, res) => {
    const email = req.params.email;
    const passw = req.params.passw;
    usuariosSchema
        .find({email:email, password:passw})
        .then((data) =>{
            if(data.length != [])res.json(data);
            else res.status(500).json(data);
            console.log(`\nEmail: ${email} y Passw: ${passw} correctos? : ${(data.length != [] ? "SI" : "NO")}\n${data}\n ${res.statusMessage}`);
        })
        .catch((err) => {
            res.json({message:err});
            console.log(`Error get /usuario/validar`);
        })
})

//Enviar correo de cambio de pass
router.get("/usuario/resetPass/:email", async (req,res) => {
  const email = req.params.email
  usuariosSchema
    .findOne({email:email})
    .then((data) => {
        const link = "http://localhost:8081/api/usuario/resetLink/"+data._id
        sendEmail(data.email,"Cambio de contraseña",link);
        res.json(data);
    }).catch((err) => {
        res.json({message:err});
    })  
})

//Mostrar Formulario para cambiar contraseña
router.get("/usuario/resetLink/:id",(req,res) => {
    const {id} = req.params
    res.sendFile(path.resolve('www/resetPass.html'))
});

//actualizar password de usuario
router.post('/usuario/resetLink/:id',(req,res) => {
    const id = req.params.id
    const pass = req.body.pass
    const repass = req.body.repass
    usuariosSchema
    .findById(id)
    .then((data) => {
        if(pass == repass) {
            //Las contraseñas son iguales y por lo tanto se deben encriptar.
            const hash = crypto.createHash('sha256');
            const finalHex = hash.update(pass).digest('hex');
            data.password = finalHex
            data.save()
            res.send("<p>La contraseña ha sido actualizada.</p>")
        }else {
            res.send("<p>Las contraseñas no coinciden, <a href = http://localhost:8081/api"+req.url+"> Volver a intentarlo</a></p>")
        }
    }).catch((err) => {
        res.send("<p>Error al actualizar el usuario</p>")
        console.error(err.message);
    })
 
});
module.exports = router;