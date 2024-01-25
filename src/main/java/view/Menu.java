package view;

import base.repository.util.HibernateUtil;
import entity.Comment;
import entity.Like;
import entity.Tweet;
import entity.User;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import repository.impl.CommentRepositoryImpl;
import repository.impl.LikeRepositoryImpl;
import repository.impl.TweetRepositoryImpl;
import repository.impl.UserRepositoryImpl;
import service.CommentService;
import service.LikeService;
import service.TweetService;
import service.UserService;
import service.impl.CommentServiceImpl;
import service.impl.LikeServiceImpl;
import service.impl.TweetServiceImpl;
import service.impl.UserServiceImpl;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Menu {
    private final Session session = HibernateUtil.getSessionFactory().openSession();
    private final UserService userService = new UserServiceImpl(session, new UserRepositoryImpl(session));
    private final TweetService tweetService = new TweetServiceImpl(session, new TweetRepositoryImpl(session));
    private final LikeService likeService = new LikeServiceImpl(session, new LikeRepositoryImpl(session));
    private final CommentService commentService = new CommentServiceImpl(session, new CommentRepositoryImpl(session));
    private final Scanner scanner = new Scanner(System.in);
    private static User person = new User();
    private final Set<Tweet> tweetList = new HashSet<>();

    public void showMenu() {
        System.out.println("--------------------------Twitter--------------------------");
        System.out.println();
        System.out.println("-----(1)signUp-----------(2)logIn-----------(3)exit------");
        switch (scanner.nextInt()) {
            case 1 -> {
                signUp();
                logIn();
            }
            case 2 -> logIn();
            case 3 -> System.exit(0);
            default -> {
                System.out.println("invalid option");
                showMenu();
            }
        }
    }


    public void signUp() {
        System.out.println("name:----------------------");
        String name = scanner.next();
        System.out.println("family:--------------------");
        String family = scanner.next();
        System.out.println("userName:------------------");
        String username = scanner.next();
        System.out.println("password:------------------");
        String password = scanner.next();
        Tweet tweet = new Tweet();
        tweet.setMessage(username + "joined  tweeter ");
        tweetList.add(tweet);
        User user = new User(name, family, username, password);
        tweet.setUser(user);
        if (userService.validate(user)) {
            showMenu();
        } else {
            person = user;
        }
    }


    public void logIn() {
        System.out.println("=========LogIn=========");
        System.out.println("username:---------------------");
        String userName = scanner.next();
        System.out.println("password:---------------------");
        String password = scanner.next();
        try {
            if (userService.findByUserName(userName).isPresent() &&
                    userService.findByPassword(password).isPresent()) {
                person = userService.findByPassword(password).get();
                showHome();
            }
        } catch (NoResultException e) {
            System.out.println("username and password is inCorrect");
            showMenu();
        }
    }


    public void showProfile() {
        System.out.println("=========Profile=========");
        System.out.println("=====================================================================================================================================");
        System.out.println(person);
        System.out.println("=====================================================================================================================================");
        System.out.println("(1)Edit profile----(2)remove account----(3)log out------");
        switch (scanner.nextInt()) {
            case 1 -> editProfile();
            case 2 -> {
                userService.delete(person);
                System.out.println("this account is removed....");
                person = null;
                showMenu();
            }
            case 3 -> {
                person = null;
                showMenu();
            }
            default -> {
                System.out.println("invalid option");
                showProfile();
            }
        }
    }


    public void editProfile() {
        System.out.println("=========Edit=========");
        System.out.println("name:----------------------");
        String name = scanner.next();
        System.out.println("family:--------------------");
        String family = scanner.next();
        person.setFirstName(name);
        person.setLastName(family);
        if (!userService.validate(person)) {
            showMenu();
        } else {
            userService.update(person);
            System.out.println("updated.......");
        }
    }


    public String checkTweetLength() {
        return "a".repeat(270);
    }


    public void postTweet() {
        System.out.println("please write your message: ");
        String message = scanner.next();
        Set<Tweet> tweets = new HashSet<>();
        Set<Like> likeList = new HashSet<>();
        Set<Comment> commentList = new HashSet<>();
        Tweet tweet = new Tweet(message, person);
        tweet.setLikes(likeList);
        tweet.setComments(commentList);
        tweets.add(tweet);
        person.setTweets(tweets);
        tweetService.save(tweet);
        userService.update(person);
        showHome();
    }


    public Comment postComment(Tweet tweet) {
        System.out.println("please write your comment");
        String message = scanner.next();
        Set<Like> likeList = new HashSet<>();
        Comment comment = new Comment(message, tweet);
        comment.setUser(person);
        comment.setLikes(likeList);
        commentService.save(comment);
        return comment;
    }


    public String editMessage() {
        System.out.println("please type your message");
        return scanner.next();
    }

    public void showHome() {
        while (true) {
            System.out.println("=========Home=========");
            Set<Tweet> tweets = new HashSet<>(tweetService.loadAll());
            if (tweets.size() == 0) {
                System.out.println("no body write tweets until now, do you want write tweet Y or N ?");
                if (scanner.next().equals("y")) postTweet();
                else if (scanner.next().equals("n")) System.exit(0);
            } else {
                for (Tweet tweet : tweets) {
                    boolean flagOfMenu = true;
                    while (flagOfMenu) {
                        System.out.println("************************************************************************************************************************************");
                        System.out.println("tweet message is:    " + tweet.getMessage());
                        System.out.println("and written with this person  ===>" + tweet.getUser());
                        System.out.println("************************************************************************************************************************************");
                        System.out.print("like number is: " + tweet.getLikes().size());
                        tweet.getLikes().forEach(System.out::print);
                        System.out.println();
                        System.out.println("comment number is: " + tweet.getComments().size());
                        tweet.getComments().forEach(System.out::println);
                        System.out.println();
                        System.out.println("=====================================================================================================================================");
                        System.out.println("(1)like----(2)unlike----(3)ShowComment----(4)Like comment---(5)Unlike comment (6)writeComment---(7)Edit comment---(8)remove comment");
                        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
                        System.out.println("(9)write tweet---(10)Edit tweet--- (11)remove tweet---(12)search user---(13)show profile---(14)testTweetLength---(15)next tweet");
                        System.out.println("=====================================================================================================================================");
                        switch (scanner.nextInt()) {
                            case 1 -> {
                                int flagLike = 0;
                                Set<Like> temporaryListLike = new HashSet<>(tweet.getLikes());
                                for (Like like : temporaryListLike) {
                                    if (like.getUser().getUserName().equals(person.getUserName())) {
                                        flagLike++;
                                    }
                                }
                                if (flagLike == 0) {
                                    Like like = new Like(person.getUserName(), tweet);
                                    like.setUser(person);
                                    temporaryListLike.add(like);
                                    likeService.save(like);
                                    tweet.setLikes(temporaryListLike);
                                    tweetService.update(tweet);
                                } else {
                                    System.out.println("liked before and can not liked again");
                                }
                                System.out.println("like number is: " + temporaryListLike.size());
                            }
                            case 2 -> {
                                boolean flagStatus = false;
                                Set<Like> temporaryListUnLike = new HashSet<>(tweet.getLikes());
                                for (Like unlike : temporaryListUnLike) {
                                    if (unlike.getUser().getUserName().equals(person.getUserName())) {
                                        temporaryListUnLike.remove(unlike);
                                        likeService.delete(unlike);
                                        break;
                                    } else {
                                        flagStatus = true;
                                    }
                                }
                                if (flagStatus) System.out.println("user not any liked in past");
                                tweet.setLikes(temporaryListUnLike);
                                tweetService.update(tweet);
                                System.out.println("like number is: " + temporaryListUnLike.size());
                            }
                            case 3 -> {
                                System.out.println("comment: ");
                                if (tweet.getComments().size() == 0) {
                                    System.out.println("no comments found for this tweet");
                                } else {
                                    for (Comment comment : tweet.getComments()) {
                                        System.out.println("comment message is:  " + comment.getMassage());
                                        System.out.println("this comment is written with: " + comment.getUser());
                                    }
                                }
                            }
                            case 4 -> {
                                int flag = 0;
                                Set<Comment> temporaryComments = new HashSet<>(tweet.getComments());
                                for (Comment comment : temporaryComments) {
                                    System.out.println(comment);
                                    System.out.println("like number of this comment is: " + comment.getLikes().size());
                                    System.out.println("do you want like it? yes->y no->n");
                                    if (scanner.next().equals("y")) {
                                        Set<Like> temporaryCommentsLike = new HashSet<>(comment.getLikes());
                                        for (Like temporaryLike : temporaryCommentsLike) {
                                            if (temporaryLike.getUser().getUserName().equals(person.getUserName())) {
                                                flag++;
                                            }
                                        }
                                        if (flag == 0) {
                                            Like likeOfComment = new Like(person.getUserName(), comment);
                                            likeOfComment.setUser(person);
                                            temporaryCommentsLike.add(likeOfComment);
                                            likeService.save(likeOfComment);
                                            tweet.setLikes(temporaryCommentsLike);
                                            commentService.update(comment);
                                            System.out.println("like number of this comment is: " + temporaryCommentsLike.size());
                                        } else System.out.println("you liked in past and can not liked again");

                                    } else System.out.println();
                                }
                            }
                            case 5 -> {
                                int flagUnlike = 0;
                                Set<Comment> temporaryCommentSet = new HashSet<>(tweet.getComments());
                                for (Comment comment : temporaryCommentSet) {
                                    System.out.println(comment);
                                    System.out.println("like number of this comment is: " + comment.getLikes().size());
                                    System.out.println("do you want unlike it? yes->y no->n");
                                    if (scanner.next().equals("y")) {
                                        Like unlike = new Like();
                                        Set<Like> temporaryCommentsLike = new HashSet<>(comment.getLikes());
                                        for (Like temporaryLike : temporaryCommentsLike) {
                                            if (temporaryLike.getUser().getUserName().equals(person.getUserName())) {
                                                flagUnlike++;
                                                unlike = temporaryLike;
                                            }
                                        }
                                        if (flagUnlike > 0) {
                                            temporaryCommentsLike.remove(unlike);
                                            likeService.delete(unlike);
                                            comment.setLikes(temporaryCommentsLike);
                                            commentService.update(comment);
                                        } else System.out.println("you do not like before that unlike now");

                                        System.out.println("like number of this comment is: " + temporaryCommentsLike.size());
                                    } else System.out.println();
                                }
                            }
                            case 6 -> {
                                Set<Comment> comments = new HashSet<>(tweet.getComments());
                                comments.add(postComment(tweet));
                                tweet.setComments(comments);
                                tweetService.update(tweet);
                            }
                            case 7 -> {
                                Set<Comment> commentSetForEdit = new HashSet<>(tweet.getComments());
                                for (Comment comment : commentSetForEdit) {
                                    if (comment.getUser().getUserName().equals(person.getUserName())) {
                                        System.out.println(comment);
                                        System.out.println("do you want edit it? yes->y or no->n :");
                                        if (scanner.next().equals("y")) {
                                            comment.setMassage(editMessage());
                                            commentService.update(comment);
                                            System.out.println("Comment Edited.....");
                                        }
                                    } else System.out.println("no have comment");
                                }
                            }
                            case 8 -> {
                                Set<Comment> commentSet = new HashSet<>(tweet.getComments());
                                for (Comment comment : commentSet) {
                                    if (comment.getUser().getUserName().equals(person.getUserName())) {
                                        System.out.println(comment);
                                        System.out.println("do you want remove it? yes->y or no->n :");
                                        if (scanner.next().equals("y")) {
                                            commentService.delete(comment);
                                            System.out.println("comment removed.....");

                                        }
                                    } else System.out.println("you can not remove because you are not owner");
                                }
                            }
                            case 9 -> postTweet();
                            case 10 -> {
                                if (tweet.getUser().getUserName().equals(person.getUserName())) {
                                    tweet.setMessage(editMessage());
                                    tweetService.update(tweet);
                                    System.out.println("tweet is Edited.....");
                                }
                            }
                            case 11 -> {
                                if (tweet.getUser().getUserName().equals(person.getUserName())) {
                                    tweetService.delete(tweet);
                                    System.out.println("tweet is removed.....");
                                } else System.out.println("you can not remove this tweet because you are not owner");
                            }
                            case 12 -> {
                                System.out.println("please type username");
                                try {
                                    userService.findByUserName(scanner.next()).ifPresent(System.out::println);
                                } catch (NoResultException e) {
                                    System.out.println("person with this username not found");
                                }
                            }
                            case 13 -> showProfile();
                            case 14 -> {
                                String message = checkTweetLength();
                                Set<Tweet> tweetSet = new HashSet<>();
                                Tweet validTweet = new Tweet(message, person);
                                tweetSet.add(validTweet);
                                person.setTweets(tweetSet);
                                tweetService.validate(validTweet);
                                userService.update(person);
                            }
                            case 15 -> {
                                System.out.println();
                                flagOfMenu = false;
                            }
                        }
                    }
                }
            }
        }
    }
}