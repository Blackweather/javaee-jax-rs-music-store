(function () {
    var webSocket = new WebSocket("ws://localhost:8080/music-store-rest-1.0-SNAPSHOT/web-sockets");

    webSocket.onmessage = function (_) {
        if (window.location.href === "http://localhost:8080/music-store-rest-1.0-SNAPSHOT/album/album_last_modified.xhtml") {
            window.location.href = "http://localhost:8080/music-store-rest-1.0-SNAPSHOT/album/album_last_modified.xhtml";
        }
    };
})();
