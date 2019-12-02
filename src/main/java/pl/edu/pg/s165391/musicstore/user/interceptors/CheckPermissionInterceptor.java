package pl.edu.pg.s165391.musicstore.user.interceptors;

import lombok.extern.java.Log;
import pl.edu.pg.s165391.musicstore.permissions.model.Permission;
import pl.edu.pg.s165391.musicstore.user.UserService;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import java.security.AccessControlException;

@Interceptor
@CheckPermission
@Priority(100)
@Log
public class CheckPermissionInterceptor {
    @Inject
    private HttpServletRequest securityContext;

    @Inject
    private UserService userService;

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        Permission permission = null;
        if (securityContext.isUserInRole(User.Roles.ADMIN)) {
            permission = userService.getUserPermission(User.Roles.ADMIN, context.getMethod().getName());
        } else if (securityContext.isUserInRole(User.Roles.USER)) {
            permission = userService.getUserPermission(User.Roles.USER, context.getMethod().getName());
        }

        if (permission == null || permission.getPermissionType()
                                            .equals(Permission.PermissionType.DENIED)) {
            throw new AccessControlException("Access denied");
        }
        // IF_OWNER ?
        return context.proceed();
    }
}
