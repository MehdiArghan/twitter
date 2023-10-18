package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import repository.UserRepository;

import java.util.Optional;

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

    @Override
    public Optional<User> findByUserName(String userName) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        query.where(builder.equal(root.get("userName"), userName));
        User result = session.createQuery(query).getSingleResult();
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<User> findByPassword(String password) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        query.where(builder.equal(root.get("password"), password));
        User result = session.createQuery(query).getSingleResult();
        return Optional.ofNullable(result);
    }
}