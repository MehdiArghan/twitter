package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.User;
import org.hibernate.Session;
import repository.UserRepository;
import service.UserService;

import java.util.Optional;

public class UserServiceImpl extends BaseServiceImpl<Long, User, UserRepository> implements UserService {
    public UserServiceImpl(Session session, UserRepository repository) {
        super(session, repository);
    }

    @Override
    public Optional<User> findByUserName(String UserName) {
        return repository.findByUserName(UserName);
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

    }
}