package pl.edu.pg.s165391.musicstore.permissions;

import pl.edu.pg.s165391.musicstore.permissions.model.Permission;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class InitPermissions {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        Permission permissionUser1 = new Permission(User.Roles.USER, "findAlbum",
                Permission.PermissionType.GRANTED);
        Permission permissionUser2 = new Permission(User.Roles.USER, "findAllAlbums",
                Permission.PermissionType.GRANTED);
        Permission permissionUser3 = new Permission(User.Roles.USER, "removeAlbum",
                Permission.PermissionType.DENIED);
        Permission permissionUser4 = new Permission(User.Roles.USER, "saveAlbum",
                Permission.PermissionType.DENIED);
        Permission permissionUser5 = new Permission(User.Roles.USER, "findBand",
                Permission.PermissionType.GRANTED);
        Permission permissionUser6 = new Permission(User.Roles.USER, "findAllBands",
                Permission.PermissionType.GRANTED);
        Permission permissionUser7 = new Permission(User.Roles.USER, "removeBand",
                Permission.PermissionType.DENIED);
        Permission permissionUser8 = new Permission(User.Roles.USER, "saveBand",
                Permission.PermissionType.DENIED);
        Permission permissionUser9 = new Permission(User.Roles.USER, "findUser",
                Permission.PermissionType.DENIED);
        Permission permissionUser10 = new Permission(User.Roles.USER, "findAllUsers",
                Permission.PermissionType.DENIED);
        Permission permissionUser11 = new Permission(User.Roles.USER, "removeUser",
                Permission.PermissionType.DENIED);
        Permission permissionUser12 = new Permission(User.Roles.USER, "saveUser",
                Permission.PermissionType.DENIED);

        Permission permissionAdmin1 = new Permission(User.Roles.ADMIN, "findAlbum",
                Permission.PermissionType.GRANTED);
        Permission permissionAdmin2 = new Permission(User.Roles.ADMIN, "findAllAlbums",
                Permission.PermissionType.GRANTED);
        Permission permissionAdmin3 = new Permission(User.Roles.ADMIN, "removeAlbum",
                Permission.PermissionType.GRANTED);
        Permission permissionAdmin4 = new Permission(User.Roles.ADMIN, "saveAlbum",
                Permission.PermissionType.GRANTED);
        Permission permissionAdmin5 = new Permission(User.Roles.ADMIN, "findBand",
                Permission.PermissionType.GRANTED);
        Permission permissionAdmin6 = new Permission(User.Roles.ADMIN, "findAllBands",
                Permission.PermissionType.GRANTED);
        Permission permissionAdmin7 = new Permission(User.Roles.ADMIN, "removeBand",
                Permission.PermissionType.GRANTED);
        Permission permissionAdmin8 = new Permission(User.Roles.ADMIN, "saveBand",
                Permission.PermissionType.GRANTED);
        Permission permissionAdmin9 = new Permission(User.Roles.ADMIN, "findUser",
                Permission.PermissionType.GRANTED);
        Permission permissionAdmin10 = new Permission(User.Roles.ADMIN, "findAllUsers",
                Permission.PermissionType.GRANTED);
        Permission permissionAdmin11 = new Permission(User.Roles.ADMIN, "removeUser",
                Permission.PermissionType.GRANTED);
        Permission permissionAdmin12 = new Permission(User.Roles.ADMIN, "saveUser",
                Permission.PermissionType.GRANTED);

        em.persist(permissionUser1);
        em.persist(permissionUser2);
        em.persist(permissionUser3);
        em.persist(permissionUser4);
        em.persist(permissionUser5);
        em.persist(permissionUser6);
        em.persist(permissionUser7);
        em.persist(permissionUser8);
        em.persist(permissionUser9);
        em.persist(permissionUser10);
        em.persist(permissionUser11);
        em.persist(permissionUser12);

        em.persist(permissionAdmin1);
        em.persist(permissionAdmin2);
        em.persist(permissionAdmin3);
        em.persist(permissionAdmin4);
        em.persist(permissionAdmin5);
        em.persist(permissionAdmin6);
        em.persist(permissionAdmin7);
        em.persist(permissionAdmin8);
        em.persist(permissionAdmin9);
        em.persist(permissionAdmin10);
        em.persist(permissionAdmin11);
        em.persist(permissionAdmin12);
    }
}
