package ninja.robbert.mayhem.api;

import java.util.Date;

public class WelcomeMessage implements OutputMessage {
    private Date timestamp = new Date();

    @Override
    public Date getTimestamp() {
        return timestamp;
    }
}
