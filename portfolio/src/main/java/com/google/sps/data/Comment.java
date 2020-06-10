package com.google.sps.data;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/** Class containing server statistics. */
public final class Comment {

  private final String commenter;
  private final String commentMessage;
  private final List<Comment> comments;

  public Comment(String commenter, String commentMessage) {
    this.commenter = commenter;
    this.commentMessage = commentMessage;
    this.comments = new ArrayList();

  }

  public Comment(String commenter, String commentMessage, List<Comment> comments) {
    this.commenter = commenter;
    this.commentMessage = commentMessage;
    this.comments = comments;

  }

  public List<Comment> getComments() {
    return comments;
  }

  public String getCommenter() {
    return commenter;
  }

  public String getCommentMessage() {
    return commentMessage;
  }

  public void addSubcomment(Comment subcomment){
    this.comments.add(subcomment);
  }
}
