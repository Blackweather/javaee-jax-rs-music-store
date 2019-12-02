package pl.edu.pg.s165391.musicstore.basket.View;

import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.basket.Model.Basket;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@RequestScoped
@Named
public class BasketView implements Serializable {
    private Basket basket;

    @Inject
    public BasketView(Basket basket) {
        this.basket = basket;
    }

    public List<Album> getAlbums() {
        return basket.getAlbums();
    }

    public String removeAlbum(Album album) {
        basket.getAlbums().remove(album);
        return "basket_view?face-redirect=true";
    }
}
