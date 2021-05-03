package ninja.robbert.mayhem.api;

import java.util.Date;

public class AcceptMessage implements OutputMessage {
    private Date timestamp = new Date();

    @Override
    public Date getTimestamp() {
        return timestamp;
    }
}
