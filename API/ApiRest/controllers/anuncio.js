const mongoose = require('mongoose');
const { required } = require('nodemon/lib/config');
const anuncioSchema = mongoose.Schema({

    id_user: {
        type: String,
        required: true
    },

    categoria: {
        type: String,
        required: true
    },

    nombre: {
        type: String,
        required : true
    },

    descripcion: {
        type: String,
        required : true
    },

    precio: {
        type: Number,
        required : true
    },

    precioPorHora: {
        type: Boolean,
        required: true
    },

    numVecesVisto: {
        type : Number,
        required: false,
        default: 0
    },

    numVotos: {
        type: Number,
        required: false,
        default: 0
    },

    puntuacion: {
        type: Number,
        required : false,
        default: 0
    }

});
module.exports = mongoose.model('Anuncio',anuncioSchema);