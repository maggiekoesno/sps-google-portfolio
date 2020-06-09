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
 * Fetches random greeting from the server and adds it to the DOM.
 */
function getRandomGreeting() {
    fetch('/greeting').then(response => response.text()).then((greeting) => {
    document.getElementById('greeting-header').innerText = greeting;
    });
}