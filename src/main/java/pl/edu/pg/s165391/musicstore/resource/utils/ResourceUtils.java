package pl.edu.pg.s165391.musicstore.resource.utils;

import pl.edu.pg.s165391.musicstore.resource.Api;
import pl.edu.pg.s165391.musicstore.resource.model.EmbeddedResource;
import pl.edu.pg.s165391.musicstore.resource.model.Link;

import javax.ws.rs.core.UriInfo;

import java.util.Map;

import static pl.edu.pg.s165391.musicstore.resource.UriHelper.uri;

public class ResourceUtils {
    private static void addLink(EmbeddedResource.EmbeddedResourceBuilder builder, UriInfo info,
                               Class clazz, String methodName, String linkName) {
        builder.link(linkName,
                    Link.builder()
                    .href(uri(info, clazz, methodName))
                    .build());
    }

    public static void addLink(Map<String, Link> links, UriInfo info, Class clazz,
                                String methodName, String linkName) {
        links.put(linkName,
                  Link.builder()
                  .href(uri(info, clazz, methodName))
                  .build());
    }

    public static void addLink(Map<String, Link> links, UriInfo info, Class clazz,
                               String methodName, int id, String linkName) {
        links.put(linkName,
                Link.builder()
                .href(uri(info, clazz, methodName, id))
                .build());
    }

    public static void addLink(Map<String, Link> links, UriInfo info, Class clazz,
                               String methodName, int id, String linkName, String method) {
        links.put(linkName,
                Link.builder()
                .href(uri(info, clazz, methodName, id))
                .method(method)
                .build());
    }

    public static void addApiLink(EmbeddedResource.EmbeddedResourceBuilder builder, UriInfo info) {
        addLink(builder, info, Api.class, "getApi", "api");
    }

    public static void addApiLink(Map<String, Link> links, UriInfo info) {
        addLink(links, info, Api.class, "getApi", "api");
    }

    public static void addSelfLink(EmbeddedResource.EmbeddedResourceBuilder builder, UriInfo info,
                                   Class clazz, String methodName) {
        addLink(builder, info, clazz, methodName, "self");
    }

    public static void addSelfLink(Map<String, Link> links, UriInfo info,
                                   Class clazz, String methodName) {
        addLink(links, info, clazz, methodName, "self");
    }

    public static void addSelfLink(Map<String, Link> links, UriInfo info,
                                   Class clazz, String methodName, int id) {
        addLink(links, info,clazz, methodName, id, "self");
    }

}
