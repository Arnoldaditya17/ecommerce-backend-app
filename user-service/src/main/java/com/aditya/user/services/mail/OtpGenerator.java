package com.aditya.user.services.mail;

import java.util.Random;

public class OtpGenerator {

    public static String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generates a 6-digit OTP
        return String.valueOf(otp);
    }

}
