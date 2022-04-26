const mongoose = require('mongoose');
const { required } = require('nodemon/lib/config');
const anunciosGuardadosSchema = mongoose.Schema({

    id_anuncio : {
        type : String,
        required: true
    },

    id_user: {
        type: String,
        required: true
    }

})

module.exports = mongoose.model('AnunciosGuardados',anunciosGuardadosSchema);