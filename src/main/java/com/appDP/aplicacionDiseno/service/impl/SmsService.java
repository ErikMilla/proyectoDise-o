package com.appDP.aplicacionDiseno.service.impl;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import org.springframework.stereotype.Service;

@Service
public class SmsService {
    private static final String ACCOUNT_SID = "${twilio.account.sid}";
    private static final String AUTH_TOKEN = "${twilio.auth.token}";
    private static final String FROM_NUMBER = "${twilio.phone.from}";

    public void enviarSms(String to, String mensaje) {
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message sms = Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(FROM_NUMBER),
                    "Sent from your Twilio trial account - " + mensaje).create();

            System.out.println("SMS enviado a " + to + " con SID: " + sms.getSid());
        } catch (Exception e) {
            System.err.println("Error al enviar SMS: " + e.getMessage());
        }
    }
}
