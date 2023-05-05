package ma.boumlyk.onboarding.tools.message.voice;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import ma.boumlyk.onboarding.models.tools.Language;
import timber.log.Timber;

public class SkyCoreGoogleTTS {

    String TAG = getClass().getSimpleName();

    public TextToSpeech TTS;
    Map<String, Long> messagesHistory;
    Map<String, Boolean> canISpeakThisMessage;
    public int backlogQueueMode = TextToSpeech.QUEUE_FLUSH;
    public String backlogTXT = null, backlogTXTID = null;
    public boolean isTTSReady = false;

    public SkyCoreGoogleTTS(Context context, Language language, String gender) {

        instantiate(context, language, gender);
    }


    public void instantiate(Context context, Language language, String gender) {
        canISpeakThisMessage = new HashMap<>();
        messagesHistory = new HashMap<>();
        try {
            TTS = new TextToSpeech(context.getApplicationContext(), status -> {
                if (status == TextToSpeech.SUCCESS) {
                    int result;
                    TTS.setSpeechRate(1.0f);
                    if ((!gender.equals("")) && (getVoiceByLanguageANDGender(language, gender) != null)) {
                        result = TTS.setVoice(getVoiceByLanguageANDGender(language, gender));
                    } else {
                        result = TTS.setLanguage(new Locale(language.getIso2(), ""));
                    }

                    isTTSReady = true;
                    switch (result) {
                        case TextToSpeech.LANG_MISSING_DATA:
                            isTTSReady = false;
                            Timber.tag("GoogleTTS").d("error: LANG_MISSING_DATA");
                            break;
                        case TextToSpeech.LANG_NOT_SUPPORTED:
                            isTTSReady = false;
                            Timber.tag("GoogleTTS").d("error: LANG_NOT_SUPPORTED");
                            break;
                    }

                    TTS.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                        @Override
                        public void onDone(String utteranceId) {
                            Timber.tag("MyTTS-onDone").d("%s", utteranceId.split("<--#-->")[0]);
                            canISpeakThisMessage.put(utteranceId.split("<--#-->")[0], true);
                        }

                        @Override
                        public void onError(String utteranceId) {
                            Timber.tag("MyTTS-onError").d("%s", utteranceId.split("<--#-->")[0]);
                            canISpeakThisMessage.put(utteranceId.split("<--#-->")[0], true);
                        }

                        @Override
                        public void onError(String utteranceId, int errorCode) {
                            Timber.tag("MyTTS-onError").d("%s", utteranceId.split("<--#-->")[0]);
                            canISpeakThisMessage.put(utteranceId.split("<--#-->")[0], true);
                        }

                        @Override
                        public void onStart(String utteranceId) {
                            Timber.tag("MyTTS-onStart").d("%s", utteranceId.split("<--#-->")[0]);
                        }

                        @Override
                        public void onStop(String utteranceId, boolean interrupted) {
                            Timber.tag("MyTTS-onStop").d("%s", utteranceId.split("<--#-->")[0]);
                            canISpeakThisMessage.put(utteranceId.split("<--#-->")[0], true);
                        }

                    });

                    say(backlogTXT, backlogQueueMode, backlogTXTID);
                    Timber.tag("TTS").d("TextToSpeech.OnInitListener  TextToSpeech.SUCCESS");

                } else {
                    Timber.tag("TTS").d("unable to initialize TTS");
                }

            });

        } catch (Exception e) {
            isTTSReady = false;
         }


    }


    public void updateLanguage(Context context, Language language, String gender) {
        instantiate(context, language, gender);
    }

    public void sayMsgPlease(String message, int mTtsQueueMode) {

        if ((!message.equals("")) && (isTTSReady)) {
            if ((canISpeakThisMessage.get(message) == null) || (!TTS.isSpeaking())) {
                say(message, mTtsQueueMode, message + "<--#-->" + System.currentTimeMillis());
                canISpeakThisMessage.put(message, false);
            } else if (Boolean.TRUE.equals(canISpeakThisMessage.get(message))) {
                say(message, mTtsQueueMode, message + "<--#-->" + System.currentTimeMillis());
                canISpeakThisMessage.put(message, false);
            }
        } else if ((isTTSReady)) {
            say(message, mTtsQueueMode);
        }

    }

    public static boolean sayArabicVoiceMessage(Context context, String message) {

        /*
        try {

            switch (message) {
                case "المرجو تقديم الوجه الأمامي لبطاقة التعريف الخاصة بكم":

                    HXMusic.music()
                            .load(R.raw.product_01_service_0102_msg01recto) // Sets the resource of the sound effect. [REQUIRED]
                            .looped(false)                // Sets the sound effect to be looped. [OPTIONAL]
                            .play(context.getApplicationContext());                 // Plays the sound effect. [REQUIRED]
                    break;
                default:
                    return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

         */

        return true;
    }


    public void say(final String message, int mTtsQueueMode) {
        final String utteranceId = UUID.randomUUID().toString();
        say(message, mTtsQueueMode, utteranceId);
    }

    public void say(final String message, int mTtsQueueMode, String utteranceId) {
        if (isTTSReady) {
            int mAudioStream = TextToSpeech.Engine.DEFAULT_STREAM;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                final Bundle params = new Bundle();
                params.putString(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(mAudioStream));
                TTS.speak(message, mTtsQueueMode, params, utteranceId);
            } else {
                final HashMap<String, String> params = new HashMap<>();
                params.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(mAudioStream));
                params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId);
                TTS.speak(message, mTtsQueueMode, params);
            }
        } else {
            backlogTXT = message;
            backlogQueueMode = mTtsQueueMode;
            backlogTXTID = utteranceId;
        }

    }

    public Voice getVoiceByLanguageANDGender(Language language, String gender) {
        for (Voice tmpVoice : TTS.getVoices()) {
            if (tmpVoice.getLocale().getISO3Language().equals(language.getIso3()) && tmpVoice.getName().contains(gender))
                return tmpVoice;
        }
        for (Voice tmpVoice : TTS.getVoices()) {
            if (tmpVoice.getLocale().getISO3Language().equals(language.getIso3()))
                return tmpVoice;
        }
        for (Voice tmpVoice : TTS.getVoices()) {
            if (tmpVoice.getLocale().getISO3Language().equals(language.getIso2()) && tmpVoice.getName().contains(gender))
                return tmpVoice;
        }
        for (Voice tmpVoice : TTS.getVoices()) {
            if (tmpVoice.getLocale().getISO3Language().equals(language.getIso2()))
                return tmpVoice;
        }
        return null;
    }

    public List<Locale> getAvailableLanguages() {
        Set<Locale> availableLocales = TTS.getAvailableLanguages();
        return new ArrayList<>(availableLocales);
    }

    public TextToSpeech getTTS() {
        return TTS;
    }

    public void stop() {
        if (TTS != null) {
            TTS.stop();
        }
    }

    public void shutdown() {
        if (TTS != null) {
            TTS.stop();
            TTS.shutdown();
        }
    }


}
