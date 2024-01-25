package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Tweet;
import org.hibernate.Session;
import repository.TweetRepository;
import service.TweetService;
import util.checkValidation;

public class TweetServiceImpl extends BaseServiceImpl<Long, Tweet, TweetRepository> implements TweetService {
    protected Session session;

    public TweetServiceImpl(Session session, TweetRepository repository) {
        super(session, repository);
        this.session = session;
    }

    @Override
    public void validate(Tweet tweet) {
        boolean validTweet = checkValidation.isValid(tweet);
        if (validTweet) {
            repository.save(tweet);
        } else {
            System.out.println("validation error");
        }
    }
}