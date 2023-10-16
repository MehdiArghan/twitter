package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.User;
import org.hibernate.Session;
import repository.UserRepository;

public class UserRepositoryImpl extends BaseRepositoryImpl<Long, User> implements UserRepository {
    protected final Session session;

    public UserRepositoryImpl(Session session) {
        super(session);
        this.session = session;
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}
