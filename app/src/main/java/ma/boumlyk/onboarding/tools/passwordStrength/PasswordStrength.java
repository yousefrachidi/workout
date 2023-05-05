package ma.boumlyk.onboarding.tools.passwordStrength;


import java.util.regex.Pattern;

import ma.boumlyk.onboarding.R;

public enum PasswordStrength {


    WEAK(R.string.password_strength_weak, R.color.errorTextViewColor, 25),
    MEDIUM(R.string.password_strength_medium, R.color.orange, 50),
    STRONG(R.string.password_strength_strong, R.color.colorPrimaryDark, 75),
    VERY_STRONG(R.string.password_strength_very_strong, R.color.colorPrimaryTooDark, 100);

    static int REQUIRED_LENGTH = 3;

    int resId;
    int color;
    int score;

    PasswordStrength(int resId, int color, int score) {
        this.resId = resId;
        this.color = color;
        this.score = score;

    }

    public int getResId() {
        return resId;
    }

    public int getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }

    public static PasswordStrength calculateStrength(String password) {

        int currentScore = 0;
        Pattern regex=Pattern.compile("-^[A-Za-z0-9]{3,31}[A-Za-z]{1}$");
        Pattern hasNumber = Pattern.compile("\\p{javaDigit}");
        Pattern hasUppercase = Pattern.compile("\\p{javaUpperCase}");
        Pattern hasLowercase = Pattern.compile("\\p{javaLowerCase}");
        //Pattern hasSpecialChar = Pattern.compile("[^\\p{javaLetterOrDigit} ]");

        if (password.length() >= REQUIRED_LENGTH) {

            if (hasLowercase.matcher(password).find())
                currentScore++;

            if (hasNumber.matcher(password).find())
                currentScore++;

            if (hasUppercase.matcher(password).find())
                currentScore++;

            if (/*hasSpecialChar.matcher(password).find()*/true)
                currentScore++;
        }

        switch (currentScore) {
            case 0:
            case 1:
                return WEAK;
            case 2:
                return MEDIUM;
            case 3:
                return STRONG;
            case 4:
                return VERY_STRONG;
        }
        return VERY_STRONG;
    }

    public static boolean isMatchedLogin(String login){
        return login.matches("^[A-Za-z0-9]{3,31}[A-Za-z]{1}$");
    }

}

