package com.google.sps.data;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/** Class containing server statistics. */
public final class Comment {

    private final long id;
    private final String commenter;
    private final String commentMessage;
    private final List<Comment> subcomments;

    public Comment(long id, String commenter, String commentMessage) {
        this.id = id;
        this.commenter = commenter;
        this.commentMessage = commentMessage;
        this.subcomments = new ArrayList();
    }

    public long getId() {
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
