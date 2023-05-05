package ma.boumlyk.onboarding.models;

import java.security.SecureRandom;



public class ObjectsGenerator {

    public static String customerId = "9fe528a16c904aacbf3fc3d178b75b59";
    private static SecureRandom r = new SecureRandom();

    public static String getRandomEmail() {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++)
            sb.append((char) (r.nextInt(26) + 'a'));
        sb.append("@gmail.com");
        return sb.toString();
    }

    public static String getRandomPhone() {
        StringBuilder sb = new StringBuilder("+212");
        for (int i = 0; i < 9; i++)
            sb.append(r.nextInt(9));
        return sb.toString();
    }



}
