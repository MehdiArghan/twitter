package service;

import base.service.BaseService;
import entity.User;

import java.util.Optional;

public interface UserService extends BaseService<Long, User> {
    Optional<User> findByUserName(String UserName);
    Optional<User> findByPassword(String password);
    void singUp(User user);
    boolean validate(User user);
}
