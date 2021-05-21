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
    private CompetitionStatus competitionStatus;
    private List<PlayerResult> competitionResult;
    private Date timestamp;

    public enum CompetitionStatus {
        idle, started, finished
    }

    public enum FightStatus {
        idle, ready, fighting, overtime, finished
    }

    public enum FightResult {
        win, loss
    }

    public static class PlayerResult {
        private String name;
        private int wins;
        private int losses;

        PlayerResult() {
            // for jackson
        }

        public PlayerResult(final String name, final int wins, final int losses) {
            this.name = name;
            this.wins = wins;
            this.losses = losses;
        }

        public String getName() {
            return name;
        }

        public int getWins() {
            return wins;
        }

        public int getLosses() {
            return losses;
        }

        @Override
        public String toString() {
            return "PlayerResult {" +
                    "name=" + name + 
                    ", wins=" + wins + 
                    ", losses=" + losses +
                    "}";
            }
    }

    StatusMessage() {
        // for jackson
    }

    public StatusMessage(List<Hero> you, List<Hero> opponent, FightStatus status, FightResult result, CompetitionStatus competitionStatus) {
        this(you, opponent, status, result, competitionStatus, null);
    }

    public StatusMessage(List<Hero> you, List<Hero> opponent, FightStatus status, FightResult result, CompetitionStatus competitionStatus, List<PlayerResult> competitionResult) {
        this.you = you;
        this.opponent = opponent;
        this.status = status;
        this.result = result;
        this.timestamp = new Date();
        this.competitionStatus = competitionStatus;
        this.competitionResult = competitionResult;
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

    public CompetitionStatus getCompetitionStatus() {
        return competitionStatus;
    }

    public List<PlayerResult> getCompetitionResult() {
        return competitionResult;
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
                ", competitionStatus=" + competitionStatus +
                ", competitionResult=" + competitionResult +
                ", timestamp=" + timestamp +
                '}';
    }
}
