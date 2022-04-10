const mongoose = require('mongoose');
const ofertaSchema = mongoose.Schema({
    id_usuario: {
        type:String,
        required: true
    },
    titulo: {
        type:String,
        required: true
    },
    descripcion: {
        type: String,
        required: true
    }
});
module.exports = mongoose.model('Oferta',ofertaSchema)