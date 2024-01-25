package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.User;
import org.hibernate.Session;
import repository.UserRepository;
import service.UserService;
import util.checkValidation;

import java.util.Optional;

public class UserServiceImpl extends BaseServiceImpl<Long, User, UserRepository> implements UserService {
    protected Session session;

    public UserServiceImpl(Session session, UserRepository repository) {
        super(session, repository);
        this.session = session;
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    @Override
    public Optional<User> findByPassword(String password) {
        return repository.findByPassword(password);
    }

    @Override
    public void singUp(User user) {
        try {
            session.getTransaction().begin();
            repository.save(user);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public boolean validate(User user) {
        boolean userValidate = checkValidation.isValid(user);
        if (userValidate) {
            singUp(user);
            return true;
        } else {
            return false;
        }
    }
}