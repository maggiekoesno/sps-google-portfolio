package com.google.sps.data;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/** Class containing server statistics. */
public final class Comment {

    private static int id_counter = 0;

    private final int id;
    private final String commenter;
    private final String commentMessage;
    private final List<Comment> subcomments;

    public Comment(String commenter, String commentMessage) {
        this.commenter = commenter;
        this.commentMessage = commentMessage;
        this.subcomments = new ArrayList();
        this.id = ++id_counter;
    }

    public int getId() {
        return id;
    }

    public List<Comment> getSubcomments() {
        return subcomments;
    }

    public void addSubcomment(Comment subcomment){
        this.subcomments.add(subcomment);
    }

    public String getCommenter() {
        return commenter;
    }

    public String getCommentMessage() {
        return commentMessage;
    }
}
