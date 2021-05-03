package ninja.robbert.mayhem.api;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Date;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @Type(value = WelcomeMessage.class, name = "welcome"),
        @Type(value = StatusMessage.class, name = "status"),
        @Type(value = AcceptMessage.class, name = "accept"),
        @Type(value = ErrorMessage.class, name = "error")
})
public interface OutputMessage {
    Date getTimestamp();
}
