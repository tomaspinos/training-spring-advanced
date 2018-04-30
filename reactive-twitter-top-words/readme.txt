JS client

var eventSource = new EventSource("http://localhost:8080/twitter-top-words/top-words");

eventSource.onmessage = function (e) {
    console.log("Processing top words: ", e.data);
};
