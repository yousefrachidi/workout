package ma.boumlyk.onboarding.models.tools;
import androidx.annotation.NonNull;
import java.util.Locale;

public enum Language {

    ARABIC("ara", "ar", "Arabic"),
    FRENCH("fra", "fr", "French"),
    ENGLISH("eng", "en", "English");

    private final String iso3;
    private final String iso2;
    private final String name;

    Language(String iso3, String iso2, String name) {
        this.iso3 = iso3;
        this.iso2 = iso2;
        this.name = name;
    }

    public String getIso3() {
        return this.iso3;
    }

    public String getIso2() {
        return this.iso2;
    }

    public String getName() {
        return this.name;
    }

    public static Language getLanguage(String iso3) {
        if (iso3.toLowerCase(Locale.ROOT).startsWith("ar"))
            return Language.ARABIC;
        if (iso3.toLowerCase(Locale.ROOT).startsWith("fr"))
            return Language.FRENCH;
        if (iso3.toLowerCase(Locale.ROOT).startsWith("en"))
            return Language.ENGLISH;
        return null;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name();
    }

}
