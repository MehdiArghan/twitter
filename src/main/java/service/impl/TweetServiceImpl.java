package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Tweet;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.Session;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import repository.TweetRepository;
import service.TweetService;

import java.util.Set;

public class TweetServiceImpl extends BaseServiceImpl<Long, Tweet, TweetRepository> implements TweetService {
    private TweetRepository tweetRepository;

    public TweetServiceImpl(Session session, TweetRepository repository) {
        super(session, repository);
    }

    @Override
    public void validate(Tweet tweet) {
        ValidatorFactory validatorFactory = Validation.byDefaultProvider()
                .configure().messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Tweet>> violations = validator.validate(tweet);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Tweet> violation : violations) {
                System.out.println(violation.getMessage());
            }
            validatorFactory.close();
        } else
            tweetRepository.save(tweet);
    }
}