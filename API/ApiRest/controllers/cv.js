const mongoose = require('mongoose');
const cvSchema = mongoose.Schema({
    id_user: {
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
    experiencia: {
        type: Number,
        required: true
    },
    titulo: {
        type: String,
        required: true
    },
    especialidad: {
        type: String,
        required: false
    },
    habilidades: {
        type: Array,
        required: false
    }
});
module.exports = mongoose.model('CV',cvSchema)