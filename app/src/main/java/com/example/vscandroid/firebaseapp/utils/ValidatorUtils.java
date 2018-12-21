package com.example.vscandroid.firebaseapp.utils;


import com.example.vscandroid.firebaseapp.domain.constants.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class ValidatorUtils {

    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    @Inject
    public ValidatorUtils() {
    }

    private static boolean isEmptyField(String txt) {
        return txt.isEmpty();
    }

    public boolean validateName(String name) {
        if(!isEmptyField(name)){
            Pattern pattern = Pattern.compile(Patterns.NAME_PATTERN, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(name);
            return matcher.find();
        }
        return false;
    }

    public boolean validateEmail(String email) {
        return !isEmptyField(email) && Patterns.EMAIL_ADDRESS_PATTERNS.matcher(email).matches();
    }

    public boolean validateAddress(String address) {
        return !isEmptyField(address);
    }

    public boolean validatePhone(String phone) {
        return !isEmptyField(phone);
    }

    public boolean validateText(String text) {
        return !isEmptyField(text);
    }

    public boolean validateUrl(String url) {

        if(!isEmptyField(url)) {
            Pattern pattern = Pattern.compile(Patterns.URL_PATTERN);
            Matcher matcher = pattern.matcher(url);
            return matcher.find();
        }
        return false;
    }

    public boolean validatePassword(String password) {
        if (!isEmptyField(password)) {
            return password.length() >= MINIMUM_PASSWORD_LENGTH;
        }
        return false;
    }
}
