package ma.boumlyk.onboarding;

import ma.boumlyk.onboarding.models.tools.Language;

public class Configs {

    public static Language DEFAULT_LANGUAGE = Language.FRENCH;
    public static final long DISCONNECT_TIMEOUT = 120000;   // 2 min = 2 * 60 * 1000 ms

    public static int DUAL_VERIFICATION_LINK_VALIDITY = 180;
    public static final String ACCOUNT_CREATED_STEP = "99:00:00";
    public static final String APPLICATION_REJECTED_STEP = "98:00:00";
    public static final String REQUEST_VISIT_TO_AGENCY = "99:00:00";
    public static final String REQUEST_AFTER_SIGNE = "17:00:00";

    public static final String SERVICE_CIN = "0102";
    public static final String SERVICE_PASSPORT = "03";
    public static final String SERVICE_CARD_STAY = "0405";

    public static final String CODE_PHONE = "+212";
    public static final String CODE_FIX_05 = "05";
    public static final String CODE_FIX_08 = "08";
    public static final String KEY_CURRENT_ACTIVITY = "currentActivity";
    public static final String KEY_BACK_ACTIVITY = "backActivity";
    public static final String KEY_DOCUMENT = "document";
}
