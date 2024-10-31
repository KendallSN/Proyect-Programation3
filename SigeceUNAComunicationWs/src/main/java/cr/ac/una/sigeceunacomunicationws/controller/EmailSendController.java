package cr.ac.una.sigeceunacomunicationws.controller;

import cr.ac.una.sigeceunacomunicationws.model.EmailmanagerDto;
import cr.ac.una.sigeceunacomunicationws.model.EmailsDto;
import cr.ac.una.sigeceunacomunicationws.model.FileDto;
import cr.ac.una.sigeceunacomunicationws.service.EmailManagerService;
import cr.ac.una.sigeceunacomunicationws.service.EmailsService;
import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Singleton
@Startup
public class EmailSendController {
    @EJB
    private EmailsService emailsService = new EmailsService();
    @EJB
    private EmailManagerService emailManagerService = new EmailManagerService();
    private LocalDateTime lastTimeSent;
    private List<EmailsDto> listEmails;
    private EmailmanagerDto emailmanagerDto;
    private List<LocalDateTime> lastHourSentEmailDates;
    
    @Schedule(minute = "*/1", hour = "*", persistent = false)
    public void sendEmailTimer() throws IOException {
        try{
            emailmanagerDto = (EmailmanagerDto) emailManagerService.getEmailManager(Long.valueOf(1)).getResult("EmailManager");
            listEmails = (List<EmailsDto>) emailsService.getEmails().getResult("Emails");
            listEmails.sort(Comparator.comparing(EmailsDto::getEmlId));
        }catch(Exception ex){
            emailmanagerDto = null;
            listEmails=new ArrayList<>();
        }

        if(lastHourSentEmailDates == null){
            lastHourSentEmailDates = new ArrayList<>();
        }

        Duration duration = Duration.ZERO;
        if (lastTimeSent != null) {
            LocalDateTime currentTime = LocalDateTime.now();
            duration = Duration.between(lastTimeSent, currentTime);
            if(!lastHourSentEmailDates.isEmpty() && Duration.between(lastHourSentEmailDates.getFirst(),currentTime).toMinutes()>=60){
                lastHourSentEmailDates.remove(0);
            }
        }

        if (emailmanagerDto != null && (lastTimeSent == null || duration.toMinutes() >= emailmanagerDto.getEmmTimeinminutes())) {
            if (lastHourSentEmailDates.size() < emailmanagerDto.getEmmLimitperhour()) {
                lastTimeSent = LocalDateTime.now().minusSeconds(1);
                Optional<EmailsDto> email = this.listEmails.stream()
                        .filter(e -> e.getEmlSent().contains("N"))
                        .findFirst();
                if (email.isPresent()) {
                    EmailsDto emailToSend = email.get();
                    this.sendEmailFunction(emailToSend.getEmlEmailaddress(), "Email", emailToSend.getEmlHtml(), emailmanagerDto.getEmmEmail(), emailmanagerDto.getEmmPassword(), emailmanagerDto.getEmmPort(), (List<FileDto>) emailToSend.getFileCollection());
                    emailToSend.setEmlSent("Y");
                    this.emailsService.saveEmail(emailToSend);
                    lastHourSentEmailDates.add(lastTimeSent);
                } else {
                    
                }
            } else {
                
            }
        }
    }
    
public static void sendEmailFunction(String to, String subject, String body, String username, String password, String port, List<FileDto> files) throws IOException {
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

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            
            for (FileDto file : files) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                
                File tempFile = File.createTempFile(file.getFleName(), "." + file.getFleType());
                tempFile.deleteOnExit();

                try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
                    char[] charArray = new char[file.getFleContent().length];
                    for (int i = 0; i < file.getFleContent().length; i++) {
                        charArray[i] = (char) (file.getFleContent()[i] & 0xFF);
                    }
                    for (int i = 0; i < charArray.length; i += 8) {
                        StringBuilder byteString = new StringBuilder();
                        for (int j = 0; j < 8 && i + j < charArray.length; j++) {
                            byteString.append(charArray[i + j]);
                        }
                        int byteValue = Integer.parseInt(byteString.toString(), 2);
                        fileOutputStream.write(byteValue);
                    }
                }
                attachmentPart.attachFile(tempFile);
                multipart.addBodyPart(attachmentPart);
            }
            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
