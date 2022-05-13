const mongoose = require('mongoose');
const { required } = require('nodemon/lib/config');

//Relaciona las ofertas creadas por un empresario con los CV de los Candidatos, el id_usuario hace referencia al empresario, el id_oferta a la oferta y el id_CV al Curriculum del candidato
const candidatosOfertasSchema = mongoose.Schema({
    
    id_usuario : {
        type : String,
        required : true
    },

    id_oferta : {
        type : String,
        required : true
    },

    id_cv : {
        type : String,
        required : true
    }

})

module.exports = mongoose.model('CandidatosOfertas',candidatosOfertasSchema);