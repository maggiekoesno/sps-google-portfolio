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

/**
 * Adds a random fact to the page.
 */
function addRandomFact() {
  const funFacts =
      ['I dyed my hair on a bet. It used to be purple but faded to a blonde.', 
      'I love to travel and spent a semester in the UK during my second year. ', 
      'I absolutely love animals and almost became a veterinarian. Glad I chose computer science instead.', 
      'My favorite programming language is python even though I have a love-hate relationship with duck-typing', 
      'This is an outdated picture (my hair is fully black now) but I hate taking pictures so this is the only one I have'
      ];

  // Pick a fact.
  const funFact = funFacts[Math.floor(Math.random() * funFacts.length)];

  // Add it to the page.
  const factContainer = document.getElementById('fact-container');
  factContainer.innerText = funFact;
}

/**
 * Run functions on load
 */
function runOnLoadFunctions(){
    getRandomGreeting();
    getComments();
}

/**
 * Fetches random greeting from the server and adds it to the DOM.
 */
function getRandomGreeting() {
    fetch('/greeting').then(response => response.text()).then((greeting) => {
    document.getElementById('greeting-header').innerText = greeting;
    });
}

/**
 * Fetches comments from the server and adds it to the DOM.
 */
function getComments() {
  fetch('/comments').then(response => response.json()).then((comments) => {
    // stats is an object, not a string, so we have to
    // reference its fields to create HTML content

    const commentContainer = document.getElementById('comments-container');
    createCommentElement(commentContainer, comments);
  });
}

/** Creates a <div> element containing comment. */
function createCommentElement(commentContainer, comments) {
    if (comments.length == 0){
        return commentContainer;
    }

    var i;
    var commentDiv;
    var paragraph;
    var node;
    var replyForm;
    var formInput;
    var commenter;

    for (i = 0; i < comments.length; i++) {
        commentDiv = document.createElement("div");
        commentDiv.className = "comment-container";

        paragraph = document.createElement("p");
        paragraph.className = "commenter";
        commenter = comments[i].commenter;
        if (commenter === ""){
            commenter = "Anonymous";
        }
        node = document.createTextNode(commenter);
        paragraph.appendChild(node);
        commentDiv.appendChild(paragraph);
        
        paragraph = document.createElement("p");
        paragraph.className = "comment-message";
        node = document.createTextNode(comments[i].commentMessage);
        paragraph.appendChild(node);
        commentDiv.appendChild(paragraph);

        replyForm = document.createElement("form");
        replyForm.action = "/comments";
        replyForm.method = "POST";

        replyForm.appendChild(createInputElement("commenter", "Name (Optional)", "20", "20", false));

        replyForm.appendChild(createInputElement("comment-message", "Add a reply...", "100", "400", true));
        
        formInput = document.createElement("input");
        formInput.type = "hidden";
        formInput.name = "parent-comment";
        formInput.value = comments[i].id;
        replyForm.appendChild(formInput);

        formInput = document.createElement("input");
        formInput.type = "submit";
        formInput.value = "Reply";
        replyForm.appendChild(formInput);

        commentDiv.appendChild(replyForm);

        commentDiv = createCommentElement(commentDiv, comments[i].subcomments);

        commentContainer.appendChild(commentDiv);
    }

    return commentContainer;
}

function createInputElement(name, placeholder, size, maxLength, required) {
    var userInput = document.createElement("input");
    userInput.name = name;
    userInput.placeholder = placeholder;
    userInput.size = size;
    userInput.maxlength = maxLength;
    userInput.required = required;

    return userInput
}