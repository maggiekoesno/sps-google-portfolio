package com.google.sps.data;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/** Class containing server statistics. */
public final class Comment {

  private final String commenter;
  private final String commentMessage;
  private final List<Comment> subcomments;

  public Comment(String commenter, String commentMessage) {
    this.commenter = commenter;
    this.commentMessage = commentMessage;
    this.subcomments = new ArrayList();

  }

  public Comment(String commenter, String commentMessage, List<Comment> subcomments) {
    this.commenter = commenter;
    this.commentMessage = commentMessage;
    this.subcomments = subcomments;

  }

  public List<Comment> getsubcomments() {
    return subcomments;
  }

  public String getCommenter() {
    return commenter;
  }

  public String getCommentMessage() {
    return commentMessage;
  }

  public void addSubcomment(Comment subcomment){
    this.subcomments.add(subcomment);
  }
}
