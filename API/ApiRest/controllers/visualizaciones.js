const mongoose = require('mongoose');
const { required } = require('nodemon/lib/config');
const visualizacionesSchema = mongoose.Schema({

    id_anuncio : {
        type : String,
        required: true
    },

    id_usuario: {
        type: String,
        required: true
    }

})

module.exports = mongoose.model('Visualizaciones',visualizacionesSchema);