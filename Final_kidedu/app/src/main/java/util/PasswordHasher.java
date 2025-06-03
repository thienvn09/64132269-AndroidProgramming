package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
    public static String hashPassword(String password) {
        if (password == null) { // Thêm kiểm tra null cho password
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // Phương thức này dùng để so sánh mật khẩu nhập vào với hash đã lưu
    public static boolean verifyPassword(String inputPassword, String storedHash) {
        if (inputPassword == null || storedHash == null) { // Thêm kiểm tra null
            return false;
        }
        String hashedInputPassword = hashPassword(inputPassword);
        return hashedInputPassword != null && hashedInputPassword.equals(storedHash);
    }
}
