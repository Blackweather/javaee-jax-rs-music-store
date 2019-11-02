package pl.edu.pg.s165391.musicstore.resource.model;

import lombok.*;

import java.net.URI;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
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
