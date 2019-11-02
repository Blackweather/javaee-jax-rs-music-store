package pl.edu.pg.s165391.musicstore.user;

import lombok.NoArgsConstructor;
import pl.edu.pg.s165391.musicstore.DataProvider;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service bean for managing user collection.
 *
 * @author Karol
 */
@ApplicationScoped
@NoArgsConstructor
public class UserService {

    /**
     * This class provides the data this class operates on.
     */
    private DataProvider dataProvider;

    @Inject
    public UserService(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    /**
     * @return all available users
     */
    public synchronized List<User> findAllUsers() {
        return dataProvider.getUsers().stream().map(User::new).collect(Collectors.toList());
    }

    /**
     * @param id user id
     * @return single user or null if empty
     */
    public synchronized User findUser(int id) {
        return dataProvider.getUsers().stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .map(User::new)
                .orElse(null);
    }

    /**
     * Saves new user
     *
     * @param user user to be saved
     */
    public synchronized void saveUser(User user) {
        if (user.getId() != 0) {
            // remove if id duplicated
            dataProvider.getUsers().removeIf(a -> a.getId() == user.getId());
        } else {
            // pick the next id
            user.setId(dataProvider.getUsers().stream()
                    .mapToInt(User::getId)
                    .max()
                    .orElse(0) + 1);
        }
        dataProvider.getUsers().add(new User(user));
    }

    /**
     * Removes a specific user.
     *
     * @param user user to be deleted
     */
    public void removeUser(User user) {
        dataProvider.getUsers().removeIf(a -> a.equals(user));
    }
}
