package cr.ac.una.sigeceunacomunicationws.controller;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Properties;

@Path("/EmailController")
public class EmailController {
    String tempPasswordHTML = "<!DOCTYPE html>\n" +
"<html lang=\"es\">\n" +
"<head>\n" +
"    <meta charset=\"UTF-8\">\n" +
"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"    <title>Contraseña Temporal</title>\n" +
"    <style>\n" +
"        body {\n" +
"            font-family: Arial, sans-serif;\n" +
"            background-color: #f4f4f4;\n" +
"            margin: 0;\n" +
"            padding: 0;\n" +
"        }\n" +
"        .container {\n" +
"            max-width: 600px;\n" +
"            margin: 20px auto;\n" +
"            background-color: #ffffff;\n" +
"            padding: 20px;\n" +
"            border-radius: 8px;\n" +
"            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" +
"        }\n" +
"        .header {\n" +
"            text-align: center;\n" +
"            margin-bottom: 20px;\n" +
"        }\n" +
"        .header h1 {\n" +
"            color: #333333;\n" +
"            margin: 0;\n" +
"        }\n" +
"        .content {\n" +
"            color: #555555;\n" +
"            font-size: 16px;\n" +
"            line-height: 1.5;\n" +
"            margin-bottom: 20px;\n" +
"        }\n" +
"        .button {\n" +
"            text-align: center;\n" +
"            margin-bottom: 20px;\n" +
"        }\n" +
"        .button a {\n" +
"            background-color: #007bff;\n" +
"            color: #ffffff;\n" +
"            padding: 15px 25px;\n" +
"            text-decoration: none;\n" +
"            font-size: 16px;\n" +
"            border-radius: 5px;\n" +
"            display: inline-block;\n" +
"            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" +
"        }\n" +
"        .footer {\n" +
"            text-align: center;\n" +
"            color: #888888;\n" +
"            font-size: 14px;\n" +
"        }\n" +
"    </style>\n" +
"</head>\n" +
"<body>\n" +
"    <div class=\"container\">\n" +
"        <div class=\"header\">\n" +
"            <h1>Contraseña Temporal</h1>\n" +
"        </div>\n" +
"        <div class=\"content\">\n" +
"            <p> A continuación, encontrarás tu nueva contraseña temporal:</p>\n" +
"            <p><strong>[Contraseña Temporal]</strong></p>\n" +
"            <p>Para acceder a tu cuenta, usa esta contraseña temporal. Después de iniciar sesión, te recomendamos cambiarla por una contraseña más segura.</p>\n" +
"            <p>Si no solicitaste un restablecimiento de contraseña, por favor ignora este correo.</p>\n" +
"        </div>\n" +
"        <div class=\"footer\">\n" +
"            <p>&copy; 2024 Sigece UNA. Todos los derechos reservados.</p>\n" +
"        </div>\n" +
"    </div>\n" +
"</body>\n" +
"</html>";
    
    String activationLinkHTML = "<!DOCTYPE html>\n" +
"<html lang=\"es\">\n" +
"<head>\n" +
"    <meta charset=\"UTF-8\">\n" +
"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"    <title>Activación de Servicio</title>\n" +
"    <style>\n" +
"        body {\n" +
"            font-family: Arial, sans-serif;\n" +
"            margin: 0;\n" +
"            padding: 20px;\n" +
"            background-color: #f4f4f4;\n" +
"        }\n" +
"        .container {\n" +
"            max-width: 600px;\n" +
"            margin: 0 auto;\n" +
"            background: #fff;\n" +
"            padding: 20px;\n" +
"            border-radius: 8px;\n" +
"            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
"        }\n" +
"        .button {\n" +
"            display: inline-block;\n" +
"            padding: 10px 20px;\n" +
"            font-size: 16px;\n" +
"            color: #fff;\n" +
"            background-color: #007BFF;\n" +
"            text-decoration: none;\n" +
"            border-radius: 5px;\n" +
"        }\n" +
"        .button:hover {\n" +
"            background-color: #0056b3;\n" +
"        }\n" +
"        .footer {\n" +
"            margin-top: 20px;\n" +
"            font-size: 12px;\n" +
"            color: #888;\n" +
"        }\n" +
"    </style>\n" +
"</head>\n" +
"<body>\n" +
"    <div class=\"container\">\n" +
"        <h1>¡Hola!</h1>\n" +
"        <p>Para activar tu servicio, por favor haz clic en el siguiente enlace:</p>\n" +
"        <p>\n" +
"            <a href=\"[Link de activación]\" class=\"button\">Activar el Usuario</a>\n" +
"        </p>\n" +
"        <p>Si tienes problemas con el enlace, copia y pégalos en tu navegador:</p>\n" +
"        <p><code>[Link de activación]</code></p>\n" +
"        <div class=\"footer\">\n" +
"            <p>Si no has solicitado esta activación, por favor ignora este mensaje.</p>\n" +
"        </div>\n" +
"    </div>\n" +
"</body>\n" +
"</html>" ;
    
//    private final String username = "evacomunamail@gmail.com";
//    private final String password = "rgjp qncf ydwk euqp";

    @GET
    @Path("/sendEmailTempPassword/{email}/{tempPassword}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendEmailTempPassword(@PathParam("email") String email, @PathParam("tempPassword") String tempPassword) {
        try {
            String htmlModify=tempPasswordHTML.replace("[Contraseña Temporal]", tempPassword);
            sendEmailFunction(email, "Contraseña Temporal", htmlModify,"evacomunamail@gmail.com","rgjp qncf ydwk euqp","587");
            return Response.ok("Correo enviado exitosamente").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al enviar el correo").build();
        }
    }
    
    @GET
    @Path("/sendActivationLink/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendActivationLink(@PathParam("email") String email) {
        try {
            String htmlModify=activationLinkHTML.replace("[Link de activación]","http://localhost:8080/SigeceUNAComunicationWs/ws/UserController/activateUser/"+email);
            sendEmailFunction(email, "Link de activación", htmlModify,"evacomunamail@gmail.com","rgjp qncf ydwk euqp","587");
            return Response.ok("Correo enviado exitosamente").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al enviar el correo").build();
        }
    }
    
    @GET
    @Path("/sendEmail/{to}/{subject}/{body}/{username}/{password}/{port}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendEmail(@PathParam("to") String to,@PathParam("subject") String subject,@PathParam("body") String body,@PathParam("username") String username,@PathParam("password") String password,@PathParam("port") String port) {
        try {
            sendEmailFunction(to, subject, body, username, password, port);
            return Response.ok("email sent succesfully").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failure while sending email").build();
        }
    }
    
    private void sendEmailFunction(String to, String subject, String body, String username, String password, String port) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", port);
        props.setProperty("mail.smtp.user", username);
        props.setProperty("mail.smtp.auth", "true");
        
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            message.setContent(body, "text/html; charset=utf-8");

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
