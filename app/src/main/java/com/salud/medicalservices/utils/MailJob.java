package com.salud.medicalservices.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailJob extends AsyncTask<MailJob.Mail, Void, Void> {
    private final String user;
    private final String pass;
    private final Context context;

    public MailJob(String user, String pass, Context context) {
        super();
        this.user = user;
        this.pass = pass;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Mail... mails) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, pass);
                    }
                });
        for (Mail mail : mails) {

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(mail.from));

                //Logica
                String recipient2 = mail.to;
                String[] recipientList = recipient2.split(",");
                InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
                int counter = 0;
                for (String recipient : recipientList) {
                    recipientAddress[counter] = new InternetAddress(recipient.trim());
                    counter++;
                }
                message.setRecipients(Message.RecipientType.TO, recipientAddress);


                //message.setRecipients(Message.RecipientType.TO,
                //      InternetAddress.parse(mail.to));


                message.setSubject(mail.subject);
                message.setText(mail.content);

                Transport.send(message);

                handlerMessage("Mensaje  Enviado");

            } catch (MessagingException e) {
                handlerMessage("Ocurrio un error inesperado, vuelva  a enviar por favor...");
            }
        }
        return null;
    }

    public static class Mail {
        private final String subject;
        private final String content;
        private final String from;
        private final String to;

        public Mail(String from, String to, String subject, String content) {
            this.subject = subject;
            this.content = content;
            this.from = from;
            this.to = to;
        }
    }

    private void handlerMessage(final String mensaje) {

        Handler handler = new Handler(context.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


