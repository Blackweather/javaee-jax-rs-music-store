package pl.edu.pg.s165391.musicstore.basket.Model;

import pl.edu.pg.s165391.musicstore.album.model.Album;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
public class Basket implements Serializable {
    private List<Album> albums = new ArrayList<>();

    public List<Album> getAlbums() {
        return albums;
    }
}
