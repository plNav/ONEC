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
module.exports = mongoose.model('oferta',ofertaSchema)