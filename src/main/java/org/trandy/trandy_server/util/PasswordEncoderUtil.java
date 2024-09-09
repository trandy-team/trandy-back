package org.trandy.trandy_server.util;

import org.trandy.trandy_server.exception.CustomException;
import org.trandy.trandy_server.exception.ExceptionStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderUtil {
    private static final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static void checkPassword(String inputPassword, String savedPassword){
        if(!passwordEncoder.matches(inputPassword, savedPassword)){
            throw new CustomException(ExceptionStatus.PasswordNotMatchedException);
        }
    }

    public static String encodePassword(String password){
        return passwordEncoder.encode(password);
    }
}
