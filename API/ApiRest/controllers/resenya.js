const mongoose = require('mongoose');
const resenyaSchema = mongoose.Schema({

    id_user: {
        type: String,
        required: true
    },

    id_anuncio: {
        type: String,
        required: true
    },

    puntuacion: {
        type: Number,
        required: true
    },

    descripcion: {
        type: String,
        required: false,
        default : ""
    }
});
module.exports = mongoose.model('Resenya',resenyaSchema);