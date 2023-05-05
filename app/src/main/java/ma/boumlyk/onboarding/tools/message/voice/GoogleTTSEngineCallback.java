package ma.boumlyk.onboarding.tools.message.voice;

/**
 * Contains the methods which are called to notify text to speech progress status.
 *
 * @author Aleksandar Gotev
 */
public interface GoogleTTSEngineCallback {
    void onStart();

    void onCompleted();

    void onError();
}
