package pl.edu.pg.s165391.musicstore.album.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;

@ToString
public class FilterTuple implements Serializable {
    @Getter
    @Setter
    SingularAttribute field;

    @Getter
    @Setter
    String filterValue;

    public FilterTuple(SingularAttribute field, String filterValue) {
        this.field = field;
        this.filterValue = filterValue;
    }
}
