package dev.patika.hrmsManager.dto.request;

import dev.patika.hrmsManager.entities.ActionType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventLogUpdateRequest {

    private Long id;

    private ActionType actionType;

    private String details;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
