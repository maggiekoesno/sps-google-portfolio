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

    private List<Comment> comments;

    @Override
    public void init() {
        comments = new ArrayList<>();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(comments);

        response.setContentType("application/json;");
        response.getWriter().println(json);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String commenter = request.getParameter("commenter");
        String commentMessage = request.getParameter("comment-message");
        int parentCommentId = Integer.parseInt(request.getParameter("parent-comment"));

        Entity commentEntity = new Entity("Comment");
        commentEntity.setProperty("commenter", commenter);
        commentEntity.setProperty("message", commentMessage);
        commentEntity.setProperty("parentId", parentCommentId);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(commentEntity);

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