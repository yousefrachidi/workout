package ma.boumlyk.onboarding.data.sources.remote.interceptors;

import java.io.IOException;

public class NetworkException extends IOException {

    String statusCode;
    String statusLabel;
    boolean sentSuccessfully;

    public NetworkException(String statusCode, String statusLabel, boolean sentSuccessfully) {
        super(statusCode);
        this.statusCode = statusCode;
        this.statusLabel = statusLabel;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public boolean isSentSuccessfully() {
        return sentSuccessfully;
    }

    public void setSentSuccessfully(boolean sentSuccessfully) {
        this.sentSuccessfully = sentSuccessfully;
    }
}
