package de.shiro.api;

import de.shiro.utlits.Log;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ChatPage<T> {

    @Getter
    private final List<T> messages;

    @Getter
    private final int messagesPerPage = 5;


    public ChatPage(List<T> messages) {
        this.messages = messages;
    }


    public int getPageCount() {
        return (int) Math.ceil(this.messages.size() / (double) this.messagesPerPage);
    }

    public List<T> getPage(int page) {
        int start = (page - 1) * this.messagesPerPage;
        int end = start + this.messagesPerPage;
        if(start < 0) return new ArrayList<>();
        return this.messages.subList(start, Math.min(end, this.messages.size()));
    }

    public boolean hasNextPage(int page) {
        return page < getPageCount();
    }
    public boolean hasPreviousPage(int page) {
        return page > 1;
    }

















}
