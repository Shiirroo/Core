package de.shiro.api;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ChatPage<T> {

    @Getter
    private List<T> messages;

    @Getter
    private final int messagesPerPage = 5;

    public ChatPage() {
    }
    public ChatPage(List<T> messages) {
        setMessages(messages);
    }
    public void setMessages(List<T> messages){
        this.messages = messages;
    }

    public void addMessage(T message){
        this.messages.add(message);
    }


    public int getPageCount() {
        return (int) Math.ceil(this.messages.size() / (double) this.messagesPerPage);
    }

    /*public List<T> getPage(int page, boolean reverse) {
        if(reverse) page = getPageCount() - page + 1;


        int start = (page - 1) * this.messagesPerPage;
        int end = start + this.messagesPerPage;

        if(start < 0) return new ArrayList<>();
        return this.messages.subList(start, Math.min(end, this.messages.size()));
    }*/

    public List<T> getPage(int page, boolean reverse) {
        if (!reverse) {
            page = getPageCount() - page + 1;
        }

        int totalMessages = this.messages.size();
        int start = totalMessages - (page * this.messagesPerPage);
        int end = totalMessages - ((page - 1) * this.messagesPerPage);

        if (end <= 0) {
            return new ArrayList<>();
        }
        start = Math.max(start, 0);
        return this.messages.subList(start, end);
    }

    public boolean hasNextPage(int page) {
        return page < getPageCount();
    }
    public boolean hasPreviousPage(int page) {
        return page > 1;
    }

















}
