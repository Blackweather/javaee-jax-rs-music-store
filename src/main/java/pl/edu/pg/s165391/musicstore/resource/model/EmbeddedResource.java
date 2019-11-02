package pl.edu.pg.s165391.musicstore.resource.model;

import lombok.*;

import javax.json.bind.annotation.JsonbProperty;
import java.util.Map;

/**
 * Representation of embedded resource for HAL. Pure JAX-RS is not supporting HAL directly at this moment.
 *
 * @author Karol
 */
@Builder
@Data
public class EmbeddedResource<V> {

    /**
     * HATEOAS links.
     */
    @Singular
    @JsonbProperty("_links")
    private Map<String, Link> links;

    /**
     * Embedded resource, i.e. collection
     */
    @Singular("embedded")
    @JsonbProperty("_embedded")
    private Map<String, V> embedded;

    @Builder
    private EmbeddedResource(Map<String, Link> links, Map<String, V> embedded) {
        if (embedded.size() > 1) {
            throw new IllegalArgumentException("There can be only one embedded object.");
        }
        this.links = links;
        this.embedded = embedded;

    }
}
