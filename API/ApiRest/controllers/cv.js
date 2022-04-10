const mongoose = require('mongoose');
const cvSchema = mongoose.Schema({
    id_usuario: {
        type: String,
        required: true
    },
    foto_url: {
        type: String,
        required: true
    },
    nombre: {
        type: String,
        required: true
    },
    telefono: {
        type: String,
        required: true
    },
    ubicacion: {
        type: String,
        required: true
    },
    correo: {
        type: String,
        required: true
    },
});
module.exports = mongoose.model('CV',cvSchema)