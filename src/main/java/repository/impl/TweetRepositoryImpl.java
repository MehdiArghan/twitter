package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.Tweet;
import org.hibernate.Session;
import repository.TweetRepository;

public class TweetRepositoryImpl extends BaseRepositoryImpl<Long, Tweet> implements TweetRepository {
    protected final Session session;
    public TweetRepositoryImpl(Session session) {
        super(session);
        this.session = session;
    }

    @Override
    public Class<Tweet> getEntityClass() {
        return Tweet.class;
    }
}
