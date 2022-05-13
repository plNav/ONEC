const mongoose = require('mongoose');
const ofertaSchema = mongoose.Schema({
    id_user: {
        type: String,
        required: true
    },
    
    nombre: {
        type: String,
        required: true
    },

    descripcion: {
        type: String,
        required: true
    },
    
    experiencia: {
        type: Number,
        required: false,
        default: 0
    },

    titulo: {
        type: String,
        required: false
    },

    especialidad: {
        type: String,
        required: false
    },

    habilidades: {
        type: Array,
        required: false,
        default: []
    },

    habilidadesLow: {
        type : Array,
        required: false,
        default: []
    },

    habilidadesReq: {
        type : Boolean,
        required: false
    }
    
});
module.exports = mongoose.model('oferta',ofertaSchema)