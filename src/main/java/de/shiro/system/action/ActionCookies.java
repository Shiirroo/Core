package de.shiro.system.action;

import de.shiro.system.action.manager.ActionFuture;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.UUID;

public class ActionCookies {

    @Getter
    private final UUID actionId = UUID.randomUUID();

    @Getter
    private final Long createTime = System.currentTimeMillis();
    @Getter
    private Long startTime;

    @Getter @Setter
    private Long finishedTime = 0L;

    @Setter
    private Boolean subAction = false;

    @Getter
    private final boolean skipQue;


    @Getter @Setter
    private LinkedHashSet<ActionFuture<?,?>> subActions = new LinkedHashSet<>();


    public ActionCookies(){
        this.skipQue = false;
    }

    public ActionCookies(Boolean skipQue){
        this.skipQue = skipQue;
    }




    public boolean isFinished() {
        return getFinishedTime() != 0L;
    }

    public void setStartTime() {
        setStartTime(System.currentTimeMillis());
    }

    private void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public void setFinished() {
        setFinishedTime(System.currentTimeMillis());
    }

    public long getDuration() {
        if(!isFinished()) return System.currentTimeMillis() - getStartTime();
        else return getFinishedTime() - getStartTime();
    }

    public Integer getDurationSeconds() {
        return (int) this.getDuration() / 1000;
    }

    public Integer getDurationMinutes() {
        return this.getDurationSeconds() / 60;
    }

    public Integer getDurationHours() {
        return this.getDurationMinutes() / 60;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ActionCookies actionCookies) {
            return actionCookies.getActionId().equals(this.getActionId());
        }
        return false;
    }

    public void reset() {
        if(!getSubActions().isEmpty()) {
            getSubActions().forEach(ActionFuture::cancel);
            getSubActions().clear();
        }
    }

    public boolean isSubAction() {
        return this.subAction;
    }

    @Override
    public String toString() {
        return "ActionCookies{" +
                "actionId=" + actionId +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", finishedTime=" + finishedTime +
                ", subAction=" + subAction +
                ", skipQue=" + skipQue +
                ", subActions=" + subActions +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getActionId());
    }



}
