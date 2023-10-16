package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Tweet;
import org.hibernate.Session;
import repository.TweetRepository;
import service.TweetService;

public class TweetServiceImpl extends BaseServiceImpl<Long, Tweet, TweetRepository> implements TweetService {
    public TweetServiceImpl(Session session, TweetRepository repository) {
        super(session, repository);
    }
}
