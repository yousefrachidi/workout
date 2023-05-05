package ma.boumlyk.onboarding.tools.message.voice;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public class GoogleTTS {

    String TAG = getClass().getSimpleName();

    public TextToSpeech TTS;
    String gender;
    float speechRate;
    Locale language;
    private final int mAudioStream = TextToSpeech.Engine.DEFAULT_STREAM;

    public GoogleTTS(Context context, String language, float speechRate, String gender) {
        this.gender = "#" + gender;
        this.speechRate = speechRate;
        this.language = new Locale(language, "");
        TTS = new TextToSpeech(context.getApplicationContext(), status -> {

            if (status == TextToSpeech.SUCCESS) {

                int result;
                TTS.setSpeechRate(1.2f);
                Voice voice = getVoiceByLanguageANDGender(this.gender);
                if ((voice != null) && (!this.gender.equals("")))
                    result = TTS.setVoice(voice);
                else
                    result = TTS.setLanguage(this.language);

                switch (result) {
                    case TextToSpeech.LANG_MISSING_DATA:
                        TTS.setLanguage(Locale.getDefault());
                        Log.d("GoogleTTS", "error: LANG_MISSING_DATA");
                        break;
                    case TextToSpeech.LANG_NOT_SUPPORTED:
                        TTS.setLanguage(Locale.getDefault());
                        Log.d("GoogleTTS", "error: LANG_NOT_SUPPORTED");
                        break;
                }


            } else {
                Log.d("TTS", "unable to initialize TTS");
            }

        });

    }

    public void say_(final String message, int mTtsQueueMode) {

        TTS.speak(message, mTtsQueueMode, null);

    }

    public void say(final String message, int mTtsQueueMode) {

        final String utteranceId = UUID.randomUUID().toString();
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
    }


    public List<Voice> getVoices() {

        Set<Voice> voiceus = TTS.getVoices();
        List<Voice> voices = new ArrayList<>();
        for (Voice tmpVoice : TTS.getVoices()) {

            voices.add(tmpVoice);
        }
        return voices;
    }

    public Voice getVoiceByLanguageANDGender(String gender) {

        for (Voice tmpVoice : TTS.getVoices()) {
            if (tmpVoice.getLocale().getISO3Language().equals(this.language.getISO3Language()) && tmpVoice.getName().contains(gender))
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

    public void setTTS(TextToSpeech TTS) {
        this.TTS = TTS;
    }


    /**
     * Stops text to speech.
     */
    public void stop() {
        if (TTS != null) {
            TTS.stop();
        }
    }

    /**
     * Must be called inside Activity's onDestroy.
     */
    public void shutdown() {
        if (TTS != null) {
            TTS.stop();
            TTS.shutdown();
        }
    }


}
