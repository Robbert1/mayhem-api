package ninja.robbert.mayhem.api;

import java.util.Date;

public class ErrorMessage implements OutputMessage {
    private String message;
    private Date timestamp;

    ErrorMessage() {
        // for jackson
    }

    public ErrorMessage(String message) {
        this.message = message;
        this.timestamp = new Date();
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }
}
