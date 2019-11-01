package pl.edu.pg.s165391.musicstore.resource.model;

import lombok.*;

import java.net.URI;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Link {

    /**
     * Resource URI.
     */
    private URI href;

    /**
     * HTTP method
     */
    private String method;
}
