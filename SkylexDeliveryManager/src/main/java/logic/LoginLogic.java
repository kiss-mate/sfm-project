package logic;

import com.google.inject.Inject;
import common.exceptions.ArgumentNullException;
import common.exceptions.BusinessException;
import data.User;
import models.enums.ErrorCodes;
import repository.IUserRepository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginLogic implements ILoginLogic {
    private final Logger _log;
    private final IUserRepository _userRepo;

    /**
     * Creates new LoginLogic object for the project
     * @param log Logger log
     * @exception NullPointerException log cannot be null
     * @exception NullPointerException userRepo cannot be null
     */
    @Inject
    public LoginLogic(Logger log, IUserRepository userRepo) {
        if ((_log = log) == null) throw new ArgumentNullException("log");
        if ((_userRepo = userRepo) == null) throw new ArgumentNullException("userRepo");
    }

    @Override
    public void addUser(String username, String password, boolean isDefault) {
        validateLoginParams(username, password);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setDefault(isDefault);

        _userRepo.insert(newUser);
        _log.log(Level.INFO, "New User added to database " + newUser);
    }

    @Override
    public void changeOneUser(int id, String username, String password, boolean isDefault) {
        validateLoginParams(username, password);
        var user = _userRepo.getById(id);
        if (user == null)
            throw new BusinessException("User not found in database", ErrorCodes.NOT_FOUND_IN_DB);

        _userRepo.update(id, username, password, isDefault);
        _log.log(Level.INFO, "User updated: " + user);
    }

    @Override
    public User getOneUser(int id) {
        return _userRepo.getById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return _userRepo.getAll();
    }

    @Override
    public void deleteUser(int id) {
        var user = _userRepo.getById(id);
        if (user == null)
            throw new BusinessException("User not found in database", ErrorCodes.NOT_FOUND_IN_DB);

        if (_userRepo.delete(user)) {
            _log.log(Level.INFO, "Removed User object from database: " + user);
        } else {
            _log.log(Level.WARNING, "Could not remove User object from database: " + user);
        }
    }

    private void validateLoginParams(String username, String password) {
        if (username == null || username.isBlank() || username.isEmpty()) {
            throw new BusinessException("Username is null or whitespace");
        }
        if (password == null || password.isBlank() || password.isEmpty()) {
            throw new BusinessException("Password is null or whitespace");
        }
    }
}
