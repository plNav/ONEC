const { links } = require('express/lib/response');
const nodemailer = require('nodemailer');

const sendEmail = async(email, subject, text) => {
    try {
        const transporter = nodemailer.createTransport({
            host: process.env.HOST,
            service: process.env.SERVICE,
            port: 465,
            secure: true,
            auth: {
                user: process.env.USER,
                pass: process.env.PASS,
            },
        });

        await transporter.sendMail({
            from: process.env.USER,
            to: email,
            subject: subject,
            html: "<p>Entra en este <a href="+text+">enlace</a> para cambiar la contraseña</p>"
        });

        console.log("email enviado correctamente");
    } catch (error) {
        console.log(error, "email no enviado");
    }
};

module.exports = sendEmail;