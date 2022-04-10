const mongoose = require('mongoose');
const estudiosSchema = mongoose.Schema({
    id_usuario: {
        type: String,
        required: true
    },
    nombre: {
        type: String,
        required: true 
    },
    centro: {
        type: String,
        required: true  
    },
    fecha_ini: {
        type: Date,
        required: true,
    },
    fecha_fin: {
        type: Date,
        required: true,
    }
});
module.exports = mongoose.model('Estudios',estudiosSchema)