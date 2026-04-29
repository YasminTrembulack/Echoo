package com.trycatchus.echoo.service;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.function.IntPredicate;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Service;

import com.trycatchus.echoo.dto.system.PasswordVerification;
import com.trycatchus.echoo.exception.HashingAlgorithmException;
import com.trycatchus.echoo.exception.KeySpecException;
import com.trycatchus.echoo.interfaces.PasswordService;

@Service
public class PBKDF2PasswordService implements PasswordService {
    private static int iterations = 65536;

    @Override
    public String applyCriptography(String rawPassword){
        SecureRandom random = new SecureRandom();

        byte[] salt = new byte[16];
        random.nextBytes(salt);

        String generatedHash;
        try {
            KeySpec spec = new PBEKeySpec(rawPassword.toCharArray(), salt, iterations, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] signature = factory.generateSecret(spec).getEncoded();

            generatedHash = String.format("%s:%s:%s", iterations, toHex(salt), toHex(signature));
        } catch (NoSuchAlgorithmException ex) {
            throw new HashingAlgorithmException();
        } catch (InvalidKeySpecException ex) {
            throw new KeySpecException();
        }

        return generatedHash;
    }

    @Override
    public Boolean matchPasswords(String rawPassword, String hashedPassword){
        String[] params = hashedPassword.split(":");

        int iterations = Integer.parseInt(params[0]);
        byte[] salt = fromHex(params[1]);
        byte[] signature = fromHex(params[2]);

        PBEKeySpec spec = new PBEKeySpec(
                rawPassword.toCharArray(),
                salt,
                iterations,
                signature.length * 8);

        byte[] validationSignature;
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            validationSignature = skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            throw new HashingAlgorithmException();
        } catch (InvalidKeySpecException ex) {
            throw new KeySpecException();
        }

        return Arrays.equals(signature, validationSignature);
    }

    @Override
    public PasswordVerification verifyPrerequisites(String rawPassword) {
        boolean length = (rawPassword.length() >= 8);

        boolean lowerCase = (containsLowerCase(rawPassword));

        boolean upperCase = (containsUpperCase(rawPassword));

        boolean specialChar = (containsSpecialCharacter(rawPassword));

        boolean number = (containsNumber(rawPassword));

        boolean isValid = (length &&
                lowerCase &&
                upperCase &&
                specialChar &&
                number);

        return new PasswordVerification(
                isValid,
                length,
                upperCase,
                lowerCase,
                number,
                specialChar);
    }

    private boolean contains(String value, IntPredicate predicate) {
        return value.chars().anyMatch(predicate);
    }

    private boolean containsLowerCase(String value) {
        return contains(value, i -> Character.isLetter(i) && Character.isLowerCase(i));
    }

    private boolean containsUpperCase(String value) {
        return contains(value, i -> Character.isLetter(i) && Character.isUpperCase(i));
    }

    private boolean containsSpecialCharacter(String value) {
        return contains(value, i -> !Character.isLetterOrDigit(i) && !Character.isWhitespace(i));
    }

    private boolean containsNumber(String value) {
        return contains(value, Character::isDigit);
    }

    private static String toHex(byte[] byteArray) {
        BigInteger bi = new BigInteger(1, byteArray);
        String hex = bi.toString(16);

        int paddingLength = (byteArray.length * 2) - hex.length();

        if (paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }

    private static byte[] fromHex(String hex) {
        byte[] byteArray = new byte[hex.length() / 2];
        for (int i = 0; i < byteArray.length; i++)
            byteArray[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);

        return byteArray;
    }
}