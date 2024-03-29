package pl.edu.pg.s165391.musicstore.album.sockets;

import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ServerEndpoint("/web-sockets")
@ApplicationScoped
@NoArgsConstructor
public class AlbumSocketHandler {
    private List<Session> sessions = new CopyOnWriteArrayList<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    public void sendNewAlbumEventToAllClients() {
        sessions.forEach(session -> {
            try {
                session.getBasicRemote().sendText("new album");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }
}
