package ma.boumlyk.onboarding.tools.message.voice;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * Helper class to easily work with Android speech recognition.
 *
 * @author Aleksandar Gotev
 */

public class GoogleTTSEngine {

    private static final String LOG_TAG = GoogleTTSEngine.class.getSimpleName();
    private static GoogleTTSEngine instance = null;

    private TextToSpeech mTextToSpeech;
    private final Map<String, GoogleTTSEngineCallback> mTtsCallbacks = new HashMap<>();
    private Locale mLocale = Locale.US;
    private float mTtsRate = 1.0f;
    private float mTtsPitch = 1.0f;
    private int mTtsQueueMode = TextToSpeech.QUEUE_ADD;
    private int mAudioStream = TextToSpeech.Engine.DEFAULT_STREAM;
    private Context mContext;

    private final TextToSpeech.OnInitListener mTttsInitListener = new TextToSpeech.OnInitListener() {

        @Override
        public void onInit(final int status) {
            switch (status) {
                case TextToSpeech.SUCCESS:
                    int result = mTextToSpeech.setLanguage(mLocale);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.i(LOG_TAG, "This language is not supported");
                        return;
                    } else {
                        Log.i(LOG_TAG, "This language is supported");
                    }

                    break;

                case TextToSpeech.ERROR:
                    break;

                default:
                    break;
            }
        }

    };


    private UtteranceProgressListener mTtsProgressListener;

    private GoogleTTSEngine(final Context context) {
        initTts(context);
    }


    private void initTts(final Context context) {
        if (mTextToSpeech == null) {
            mTtsProgressListener = new GoogleTTSEngineProgressListener(mContext, mTtsCallbacks);
            mTextToSpeech = new TextToSpeech(context.getApplicationContext(), mTttsInitListener);
            mTextToSpeech.setOnUtteranceProgressListener(mTtsProgressListener);
            mTextToSpeech.setLanguage(mLocale);
            mTextToSpeech.setPitch(mTtsPitch);
            mTextToSpeech.setSpeechRate(mTtsRate);
        }

    }


    /**
     * Initializes speech recognition.
     *
     * @param context application context
     * @return speech instance
     */

    public static GoogleTTSEngine init(final Context context) {
        if (instance == null) {
            instance = new GoogleTTSEngine(context);
        }
        return instance;
    }


    /**
     * Must be called inside Activity's onDestroy.
     */

    public synchronized void shutdown() {
        if (mTextToSpeech != null) {
            try {
                mTtsCallbacks.clear();
                mTextToSpeech.stop();
                mTextToSpeech.shutdown();
            } catch (final Exception exc) {
            }
        }
        instance = null;
    }

    /**
     * Gets speech recognition instance.
     *
     * @return SpeechRecognition instance
     */
    public static GoogleTTSEngine getInstance() {
        if (instance == null) {
            throw new IllegalStateException("GoogleTTSEngine recognition has not been initialized! call init method first!");
        }
        return instance;
    }


    /**
     * Uses text to speech to transform a written message into a sound.
     *
     * @param message message to play
     */
    public void say(final String message) {
        say(message, null);
    }

    public void say(final String message, int mTtsQueueMode) {
        this.mTtsQueueMode = mTtsQueueMode;
        say(message, null);
    }

    /**
     * Uses text to speech to transform a written message into a sound.
     *
     * @param message  message to play
     * @param callback callback which will receive progress status of the operation
     */
    public void say(final String message, final GoogleTTSEngineCallback callback) {

        final String utteranceId = UUID.randomUUID().toString();

        if (callback != null) {
            mTtsCallbacks.put(utteranceId, callback);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Bundle params = new Bundle();
            params.putString(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(mAudioStream));
            mTextToSpeech.speak(message, mTtsQueueMode, params, utteranceId);
        } else {
            final HashMap<String, String> params = new HashMap<>();
            params.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(mAudioStream));
            params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId);
            mTextToSpeech.speak(message, mTtsQueueMode, params);
        }
    }

    /**
     * Stops text to speech.
     */
    public void stopTextToSpeech() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
        }
    }

    /**
     * Sets text to speech and recognition language.
     * Defaults to device language setting.
     *
     * @param locale new locale
     * @return speech instance
     */
    public GoogleTTSEngine setLocale(final Locale locale) {
        mLocale = locale;
        if (mTextToSpeech != null)
            mTextToSpeech.setLanguage(locale);
        return this;
    }

    /**
     * Sets the speech rate. This has no effect on any pre-recorded speech.
     *
     * @param rate GoogleTTSEngine rate. 1.0 is the normal speech rate, lower values slow down the speech
     *             (0.5 is half the normal speech rate), greater values accelerate it
     *             (2.0 is twice the normal speech rate).
     * @return speech instance
     */
    public GoogleTTSEngine setTextToSpeechRate(final float rate) {
        mTtsRate = rate;
        mTextToSpeech.setSpeechRate(rate);
        return this;
    }

    /**
     * Sets the speech pitch for the TextToSpeech engine.
     * This has no effect on any pre-recorded speech.
     *
     * @param pitch GoogleTTSEngine pitch. 1.0 is the normal pitch, lower values lower the tone of the
     *              synthesized voice, greater values increase it.
     * @return speech instance
     */
    public GoogleTTSEngine setTextToSpeechPitch(final float pitch) {
        mTtsPitch = pitch;
        mTextToSpeech.setPitch(pitch);
        return this;
    }


    /**
     * Sets the text to speech queue mode.
     * By default is TextToSpeech.QUEUE_FLUSH, which is faster, because it clears all the
     * messages before speaking the new one. TextToSpeech.QUEUE_ADD adds the last message
     * to speak in the queue, without clearing the messages that have been added.
     *
     * @param mode It can be either TextToSpeech.QUEUE_ADD or TextToSpeech.QUEUE_FLUSH.
     * @return speech instance
     */
    public GoogleTTSEngine setTextToSpeechQueueMode(final int mode) {
        mTtsQueueMode = mode;
        return this;
    }


    /**
     * Sets the audio stream type.
     * By default is TextToSpeech.Engine.DEFAULT_STREAM, which is equivalent to
     * AudioManager.STREAM_MUSIC.
     *
     * @param audioStream A constant from AudioManager.
     *                    e.g. {@link android.media.AudioManager#STREAM_VOICE_CALL}
     * @return speech instance
     */
    public GoogleTTSEngine setAudioStream(final int audioStream) {
        mAudioStream = audioStream;
        return this;
    }

}
