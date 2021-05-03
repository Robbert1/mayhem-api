package ninja.robbert.mayhem.api;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @Type(value = RegisterMessage.class, name = "register"),
        @Type(value = ActionMessage.class, name = "action")
})
public interface InputMessage {
}
