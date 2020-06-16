// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.sps.data.Comment;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/comments")
public final class CommentServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Query commentQuery = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);


        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery commentResults = datastore.prepare(commentQuery);

        List<Comment> comments = new ArrayList<>();

        for (Entity commentEntity : commentResults.asIterable()) {
            long id = commentEntity.getKey().getId();
            String commenter = (String) commentEntity.getProperty("commenter");
            String message = (String) commentEntity.getProperty("message");

            Comment comment = new Comment(id, commenter, message);

            Query replyQuery = new Query("Reply").addSort("timestamp", SortDirection.DESCENDING);
            PreparedQuery replyResults = datastore.prepare(replyQuery);

            for (Entity replyEntity : replyResults.asIterable()) {
                long parentId = (long) replyEntity.getProperty("parentId");
                if(parentId == id){
                    long replyId = replyEntity.getKey().getId();
                    commenter = (String) replyEntity.getProperty("commenter");
                    message = (String) replyEntity.getProperty("message");

                    Comment reply = new Comment(replyId, commenter, message);

                    comment.addSubcomment(reply);
                }
            }

            comments.add(comment);
        }

        Gson gson = new Gson();

        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(comments));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String commenter = request.getParameter("commenter");
        String commentMessage = request.getParameter("comment-message");
        long parentCommentId = Long.parseLong(request.getParameter("parent-comment"));
        
        long timestamp = System.currentTimeMillis();

        Entity entity;

        if (parentCommentId == -1){
            entity = new Entity("Comment");
        }
        else{
            entity = new Entity("Reply");
            entity.setProperty("parentId", parentCommentId);
        }

        entity.setProperty("commenter", commenter);
        entity.setProperty("message", commentMessage);
        entity.setProperty("timestamp", timestamp);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(entity);

        response.sendRedirect("/index.html");
    }

    private Comment getComment(int id, List<Comment> comments){
        if(comments.isEmpty()){
            return null;
        }
        for (Comment comment : comments){
            if (comment.getId() == id){
                return comment;
            }
            else {
                Comment subcomment = getComment(id, comment.getSubcomments());
                if (subcomment != null){
                    return subcomment;
                }
            }
        }
        return null;
    }
}