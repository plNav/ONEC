const mongoose = require("mongoose");
const experienciaSchema = mongoose.Schema({
    id_usuario: {
        type: String,
        required: true
    },
    id_oferta: {
        type: String,
        required: false,
        default: "none"
    },
    nombre: {
        type: String,
        required: true
    },
    tiempo: {
        type: Number,
        required: true
    }
});
module.exports = mongoose.model('Experiencia',experienciaSchema)