package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.Tweet;
import org.hibernate.Session;
import repository.TweetRepository;

public class TweetRepositoryImpl extends BaseRepositoryImpl<Long, Tweet> implements TweetRepository {
    protected final Session session;

    public TweetRepositoryImpl(Session session, Session session1) {
        super(session);
        this.session = session1;
    }

    @Override
    public Class<Tweet> getEntityClass() {
        return Tweet.class;
    }
}
