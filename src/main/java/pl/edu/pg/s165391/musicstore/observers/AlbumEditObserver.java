package pl.edu.pg.s165391.musicstore.observers;

import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.album.sockets.AlbumSocketHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class AlbumEditObserver {
    @Inject
    private AlbumSocketHandler albumSocketHandler;

    public void onAlbumEdit(@Observes Album album) {
        albumSocketHandler.sendNewAlbumEventToAllClients();
    }
}
