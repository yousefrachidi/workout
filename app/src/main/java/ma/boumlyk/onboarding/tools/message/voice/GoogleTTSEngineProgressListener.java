package ma.boumlyk.onboarding.tools.message.voice;

import android.content.Context;
import android.os.Handler;
import android.speech.tts.UtteranceProgressListener;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * @author Kristiyan Petrov (kristiyan@igenius.net)
 */

public class GoogleTTSEngineProgressListener extends UtteranceProgressListener {

    private final Map<String, GoogleTTSEngineCallback> mTtsCallbacks;
    private final WeakReference<Context> contextWeakReference;

    public GoogleTTSEngineProgressListener(final Context context, final Map<String, GoogleTTSEngineCallback> mTtsCallbacks) {
        contextWeakReference = new WeakReference<>(context);
        this.mTtsCallbacks = mTtsCallbacks;
    }

    @Override
    public void onStart(final String utteranceId) {
        final GoogleTTSEngineCallback callback = mTtsCallbacks.get(utteranceId);
        final Context context = contextWeakReference.get();

        if (callback != null && context != null) {
            new Handler(context.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    callback.onStart();
                }
            });
        }
    }

    @Override
    public void onDone(final String utteranceId) {
        final GoogleTTSEngineCallback callback = mTtsCallbacks.get(utteranceId);
        final Context context = contextWeakReference.get();
        if (callback != null && context != null) {
            new Handler(context.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    callback.onCompleted();
                    mTtsCallbacks.remove(utteranceId);
                }
            });
        }
    }

    @Override
    public void onError(final String utteranceId) {
        final GoogleTTSEngineCallback callback = mTtsCallbacks.get(utteranceId);
        final Context context = contextWeakReference.get();

        if (callback != null && context != null) {
            new Handler(context.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    callback.onError();
                    mTtsCallbacks.remove(utteranceId);
                }
            });
        }
    }
}
