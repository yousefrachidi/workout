package ma.boumlyk.onboarding.data.sources.remote.interceptors;


import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import javax.inject.Inject;

import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.models.tools.Message;
import ma.boumlyk.onboarding.tools.message.Messenger;
import timber.log.Timber;

public final class ErrorHandler {

    static Messenger messenger;

    @Inject
    public ErrorHandler(Messenger messenger) {
        ErrorHandler.messenger = messenger;
    }

    public NetworkException handleResponseCode(okhttp3.Response response) {

        try {
            Timber.tag(ErrorHandler.class.getSimpleName()).d("Response : %s", response.peekBody(2048).string());
        } catch (Exception ignored) {
        }

        int code = response.code();
        if (code >= 200 && code < 300) {
            Timber.tag(ErrorHandler.class.getSimpleName()).d("Success ...");
            return null;
        } else if (code == 401) {
            Timber.tag(ErrorHandler.class.getSimpleName()).d("Authentication error ...");
            new Handler(Looper.getMainLooper()).post(() -> messenger.communicateMessage(new Message(R.string.retrofit_error_handler_divers, Message.ERROR_TYPE)));
            return new NetworkException(String.valueOf(code), "Authentication error ...", false);
        } else if (code >= 400 && code < 500) {
            Timber.tag(ErrorHandler.class.getSimpleName()).d("Client error ...");
            new Handler(Looper.getMainLooper()).post(() -> messenger.communicateMessage(new Message(R.string.retrofit_error_handler_divers, Message.ERROR_TYPE)));
            return new NetworkException(String.valueOf(code), "Client error ...", false);
        } else if (code >= 500 && code < 600) {
            Timber.tag(ErrorHandler.class.getSimpleName()).d("Server error ...");
            new Handler(Looper.getMainLooper()).post(() -> messenger.communicateMessage(new Message(R.string.retrofit_error_handler_divers, Message.WARNING_TYPE)));
            return new NetworkException(String.valueOf(code), "Server error ...", false);
        } else {
            Timber.tag(ErrorHandler.class.getSimpleName()).d("Unexpected error ...");
            new Handler(Looper.getMainLooper()).post(() -> messenger.communicateMessage(new Message(R.string.retrofit_error_handler_divers, Message.ERROR_TYPE)));
            return new NetworkException(String.valueOf(code), "Unexpected error ...", false);
        }
    }

    public NetworkException handleExceptions(Exception e) {
        if ((e instanceof NetworkException)&&((NetworkException) e).getStatusCode().equalsIgnoreCase("999")) {
            new Handler(Looper.getMainLooper()).post(() -> messenger.communicateMessage(new Message(R.string.retrofit_error_handler_not_allowed_cnx, Message.ERROR_TYPE)));
            return ((NetworkException) e);
        } else if (e instanceof IOException) {
            Timber.tag(ErrorHandler.class.getSimpleName()).d("Connexion error ...");
            Timber.tag(ErrorHandler.class.getSimpleName()).e(e);
            new Handler(Looper.getMainLooper()).post(() -> messenger.communicateMessage(new Message(R.string.retrofit_error_handler_cnx, Message.WARNING_TYPE)));
            return new NetworkException(String.valueOf(404), "Unexpected error ...", false);
        } else {
            Timber.tag(ErrorHandler.class.getSimpleName()).d("Unexpected Unknown error ...");
            Timber.tag(ErrorHandler.class.getSimpleName()).e(e);
            new Handler(Looper.getMainLooper()).post(() -> messenger.communicateMessage(new Message(R.string.retrofit_error_handler_divers, Message.ERROR_TYPE)));
            return new NetworkException(String.valueOf(5000), "Unexpected Unknown error ...", false);
        }
    }
}