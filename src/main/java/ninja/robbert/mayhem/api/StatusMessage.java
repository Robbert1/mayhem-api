package ninja.robbert.mayhem.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusMessage implements OutputMessage {
    private List<Hero> you;
    private List<Hero> opponent;
    private FightStatus status;
    private FightResult result;
    private Date timestamp;

    public enum FightStatus {
        idle, ready, fighting, overtime, finished
    }

    public enum FightResult {
        win, loss
    }

    StatusMessage() {
        // for jackson
    }

    public StatusMessage(List<Hero> you, List<Hero> opponent, FightStatus status, FightResult result) {
        this.you = you;
        this.opponent = opponent;
        this.status = status;
        this.result = result;
        this.timestamp = new Date();
    }

    public List<Hero> getYou() {
        return you;
    }

    public List<Hero> getOpponent() {
        return opponent;
    }

    public FightStatus getStatus() {
        return status;
    }

    public FightResult getResult() {
        return result;
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "StatusMessage{" +
                "you=" + you +
                ", opponent=" + opponent +
                ", status=" + status +
                ", result=" + result +
                ", timestamp=" + timestamp +
                '}';
    }
}
