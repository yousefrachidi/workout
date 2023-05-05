package ma.boumlyk.onboarding.tools.message;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
import es.dmoral.toasty.Toasty;
import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.models.tools.Language;
import ma.boumlyk.onboarding.models.tools.Message;
import ma.boumlyk.onboarding.tools.message.voice.SkyCoreGoogleTTS;

@Singleton
public class Messenger {

    Context appContext;
    static volatile SkyCoreGoogleTTS googleTTS;
    MutableLiveData<Message> message;
    boolean isMessengerBlocked = false;
    boolean isMessengerMuted = false;
    Language language;

    @Inject
    public Messenger(@ApplicationContext Context appContext) {
        this.appContext = appContext;
        message = new MutableLiveData<>();
        Toasty.Config.getInstance()
                .setToastTypeface(Typeface.createFromAsset(appContext.getAssets(), "fonts/roboto_medium.ttf"))
                .setTextSize((int) appContext.getResources().getDimension(com.intuit.sdp.R.dimen._13sdp))
                .allowQueue(false)
                .apply();
        new Handler(Looper.getMainLooper()).post(() -> message.observeForever(msg -> {
                    if (msg != null) {
                        communicateMessage(msg.getTxtMessage(appContext), msg.getMessageType(), msg.getDisplayDuration(), msg.getVoiceMessage(appContext), msg.getVoiceQueueMode());
                    }
                })
        );
    }

    public void setLanguage(Language language) {
        this.language = language;
        if (googleTTS == null) {
            synchronized (Messenger.class) {
                if (googleTTS == null)
                    googleTTS = new SkyCoreGoogleTTS(appContext, language, "");
            }
        } else {
            googleTTS.updateLanguage(appContext, language, "");
        }
    }

    public void mute(boolean mute) {
        isMessengerMuted = mute;
    }

    public void communicateMessage(Message msg) {
        if (msg != null) {
            communicateMessage(msg.getTxtMessage(appContext), msg.getMessageType(), msg.getDisplayDuration(), msg.getVoiceMessage(appContext), msg.getVoiceQueueMode());
        }
    }

    public void communicateMessage(String txt_message, String txt_type) {
        communicateMessage(txt_message, txt_type, Toast.LENGTH_LONG, txt_message, TextToSpeech.QUEUE_FLUSH);
    }


    public void communicateMessage(String txt_message, String txt_type, int txt_length, String voice_message, int voice_queue_mode) {

        try {
            if ((!isMessengerBlocked)) {
                if ((txt_message != null) && (!txt_message.equals("#")))
                    toastyMessage(txt_message, txt_type, txt_length);
                if ((!isMessengerMuted) && (googleTTS != null) && (voice_message != null) && (!voice_message.equals("#"))) {
                    if (this.language == Language.ARABIC) {
                        if (!SkyCoreGoogleTTS.sayArabicVoiceMessage(appContext, voice_message))
                            googleTTS.sayMsgPlease(voice_message, voice_queue_mode);
                    } else {
                        googleTTS.sayMsgPlease(voice_message, voice_queue_mode);
                    }
                }
            }
        } catch (Exception e) {
         }
    }


    private void toastyMessage(String message, String type, int length) {
        try {



            Toasty.Config.getInstance()
                    .setToastTypeface(Typeface.createFromAsset(appContext.getAssets(), "fonts/roboto_medium.ttf"))
                    .setTextSize((int) appContext.getResources().getDimension(com.intuit.sdp.R.dimen._13sdp))
                    .allowQueue(false)
                    .apply();
            Toast toast;
            if (!isMessengerBlocked) {
                try {
                    switch (type) {
                        case Message.INFO_TYPE:
                            toast = Toasty.custom(appContext, message, ContextCompat.getDrawable(appContext, R.drawable.ic_info_outline_white_24dp),
                                    ContextCompat.getColor(appContext, R.color.colorAccent), ContextCompat.getColor(appContext, R.color.white), length, true, true);
                            toast.setGravity(Gravity.CENTER, 0, -200);
                            toast.show();
                            break;
                        case Message.ERROR_TYPE:
                            toast = Toasty.custom(appContext, message, ContextCompat.getDrawable(appContext, R.drawable.ic_clear_white_24dp),
                                    ContextCompat.getColor(appContext, R.color.colorPrimary), ContextCompat.getColor(appContext, R.color.white), length, true, true);
                            toast.setGravity(Gravity.CENTER, 0, -200);
                            toast.show();
                            break;
                        case Message.SUCCESS_TYPE:
                            toast = Toasty.custom(appContext, message, ContextCompat.getDrawable(appContext, R.drawable.ic_check_white_24dp),
                                    ContextCompat.getColor(appContext, R.color.colorAccent), ContextCompat.getColor(appContext, R.color.white), length, true, true);
                            toast.setGravity(Gravity.CENTER, 0, -200);
                            toast.show();
                            break;
                        case Message.WARNING_TYPE:
                            toast = Toasty.custom(appContext, message, ContextCompat.getDrawable(appContext, R.drawable.ic_error_outline_white_24dp),
                                    ContextCompat.getColor(appContext, R.color.colorPrimary), ContextCompat.getColor(appContext, R.color.white), length, true, true);
                            toast.setGravity(Gravity.CENTER, 0, -200);
                            toast.show();
                            break;
                    }
                } catch (Exception e) {
                 }
            }
        } catch (Exception e) {
         }
    }

    public void mute() {
        if (googleTTS != null)
            googleTTS.sayMsgPlease("", TextToSpeech.QUEUE_FLUSH);
    }

    public void onDestroy() {
        if (googleTTS != null) {
            googleTTS.stop();
            googleTTS.shutdown();
        }
    }
}
