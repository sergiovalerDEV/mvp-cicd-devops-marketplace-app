package com.example.wallapop.utils;

import android.util.Log;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    private static final String TAG = "EmailSender";
    private static final String HOST = "sandbox.smtp.mailtrap.io";
    private static final String PORT = "2525";  // You can change this to 25, 465, or 587 if needed
    private static final String USERNAME = "57802e138e0e64";
    private static final String PASSWORD = "681911a2e6fda8";  // Replace with the actual password

    public interface EmailCallback {
        void onSuccess();
        void onFailure(String error);
    }

    public static void sendEmail(String to, String subject, String body, EmailCallback callback) {
        new Thread(() -> {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", HOST);
            props.put("mail.smtp.port", PORT);

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USERNAME, PASSWORD);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(USERNAME));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);
                Log.d(TAG, "Email sent successfully");
                callback.onSuccess();
            } catch (MessagingException e) {
                Log.e(TAG, "Error sending email", e);
                callback.onFailure("Error al enviar el correo: " + e.getMessage());
            }
        }).start();
    }
}