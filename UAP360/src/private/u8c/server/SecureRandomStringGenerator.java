package u8c.server;
import java.security.SecureRandom;

public class SecureRandomStringGenerator {
    public static String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(length);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
    
	/*
	 * public static void main(String[] args) {
	 * System.out.println(generateRandomString(4)); }
	 */
}
