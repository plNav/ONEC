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

    votos: {
        type: Array,
        required : false,
        default: []
    }
});
module.exports = mongoose.model('Anuncio',anuncioSchema);