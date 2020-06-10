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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/comments")
public final class CommentServlet extends HttpServlet {

    private List<String> comments;

    @Override
    public void init() {
    comments = new ArrayList<>();
    comments.add("This is a comment");
    comments.add("This is probably also a comment");
    comments.add("Oh look! Another comment");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Calculate server stats
    Date currentTime = new Date();
    long maxMemory = Runtime.getRuntime().maxMemory();
    long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

    // Convert the server stats to JSON
    ServerStats serverStats = new ServerStats(startTime, currentTime, maxMemory, usedMemory);
    String json = convertToJson(serverStats);

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);
    }

    /**
    * Converts a ServerStats instance into a JSON string using the Gson library. Note: We first added
    * the Gson library dependency to pom.xml.
    */
    private String convertToJsonUsingGson(ServerStats serverStats) {
    Gson gson = new Gson();
    String json = gson.toJson(serverStats);
    return json;
    }
}