package ma.boumlyk.onboarding.models.tools;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

public class Message extends Model {

    public final static String INFO_TYPE = "INFO_TYPE";
    public final static String ERROR_TYPE = "ERROR_TYPE";
    public final static String SUCCESS_TYPE = "SUCCESS_TYPE";
    public final static String WARNING_TYPE = "WARNING_TYPE";

    int txtMessageId = -1, voiceMessageId = -1;
    int displayDuration = Toast.LENGTH_LONG;
    int voiceQueueMode = TextToSpeech.QUEUE_FLUSH;
    String messageType = INFO_TYPE;


    public Message(int messageId, String messageType) {
        this.txtMessageId = messageId;
        this.voiceMessageId = messageId;
        this.messageType = messageType;
    }

    public Message(int messageId, int voiceMessageId, String messageType) {
        this.txtMessageId = messageId;
        this.voiceMessageId = voiceMessageId;
        this.messageType = messageType;
    }

    public Message(String s, String infoType) {
        super();
    }

    public String getVoiceMessage(Context ctx) {
        if (voiceMessageId == -1)
            return "#";
        return ctx.getResources().getString(voiceMessageId);
    }

    public String getTxtMessage(Context ctx) {
        if (txtMessageId == -1)
            return "#";
        return ctx.getResources().getString(txtMessageId);
    }


    ////////////////////////////////////////////////////////////////////////////////
    //////////////////////////// Getters & Setters /////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////


    public int getTxtMessageId() {
        return txtMessageId;
    }

    public void setTxtMessageId(int txtMessageId) {
        this.txtMessageId = txtMessageId;
    }

    public int getVoiceMessageId() {
        return voiceMessageId;
    }

    public void setVoiceMessageId(int voiceMessageId) {
        this.voiceMessageId = voiceMessageId;
    }

    public int getDisplayDuration() {
        return displayDuration;
    }

    public void setDisplayDuration(int displayDuration) {
        this.displayDuration = displayDuration;
    }

    public int getVoiceQueueMode() {
        return voiceQueueMode;
    }

    public void setVoiceQueueMode(int voiceQueueMode) {
        this.voiceQueueMode = voiceQueueMode;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

}
