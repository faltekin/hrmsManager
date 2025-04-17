package dev.patika.hrmsManager.dto.request;

import dev.patika.hrmsManager.entities.ActionType;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventLogSaveRequest {

    private ActionType actionType;

    private String details;

    private Long userId;

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
