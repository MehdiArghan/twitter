package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.Like;
import org.hibernate.Session;
import repository.LikeRepository;

public class LikeRepositoryImpl extends BaseRepositoryImpl<Long, Like> implements LikeRepository {
    protected final Session session;

    public LikeRepositoryImpl(Session session) {
        super(session);
        this.session = session;
    }

    @Override
    public Class<Like> getEntityClass() {
        return Like.class;
    }
}
