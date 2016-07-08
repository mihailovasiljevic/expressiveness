package twitterparser;

import java.util.ArrayList;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import twitter4j.IDs;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.Authorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterParser {

	private Configuration configuration = null;
	Twitter twitter = null;
	
	public TwitterParser() {

	}

	public void makeConfiguration(String fileName) {
		ResourceBundle bundle = PropertyResourceBundle
				.getBundle(fileName);
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(bundle.getString("ConsumerKey"));
		builder.setOAuthConsumerSecret(bundle.getString("ConsumerSecret"));
		builder.setOAuthAccessToken(bundle.getString("AccessToken"));
		builder.setOAuthAccessTokenSecret(bundle.getString("AccessTokenSecret"));
		builder.setDebugEnabled(true);
		
		this.configuration = builder.build();
	}
	
	public void makeConfiguration(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(consumerKey);
		builder.setOAuthConsumerSecret(consumerSecret);
		builder.setOAuthAccessToken(accessToken);
		builder.setOAuthAccessTokenSecret(accessTokenSecret);
		builder.setDebugEnabled(true);
		
		this.configuration = builder.build();
		
	}
	
	public void makeConfiguration() {
		this.makeConfiguration("twitterparser.twitter1");
	}
	
	public boolean enableTwitter() {
		if(this.configuration == null) {
			System.out.println("Configuration is not setted");
			return false;
		}
		this.twitter = new TwitterFactory(this.configuration).getInstance();
		return true;
	}
	
	public long getIdFromUsername(String username) {
		User user;
		try {
			user = twitter.showUser(username);
			return user.getId();
		} catch (TwitterException e) {
			e.printStackTrace();
			return -1L;
		}
	}
	
	public MainUser twitter(String username)  {
		long id = this.getIdFromUsername(username);
		if(id != -1L) {
			return twitter(id);
		} else {
			return null;
		}
	}
	
	public MainUser twitter(long mainUserId) {
		
		try {
			MainUser mainUser = new MainUser(this.makeFellow(mainUserId));
			IDs ids = twitter.getFriendsIDs(mainUser.getId(),-1L,20);
			for(long i : ids.getIDs()) {
				Fellow fellow = this.makeFellow(i);
				mainUser.addFriend(fellow);
			}
			return mainUser;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Fellow makeFellow(Long id) {
		Fellow fellow = new Fellow(id);
		User user;
		try {
			user = (User) twitter.showUser(id);
			fellow.setUsername(user.getScreenName());
			fellow.setName(user.getName());
			fellow.setCreatedAt(user.getCreatedAt());
			fellow.setFollowersNumber(user.getFollowersCount());
			fellow.setFriendsNumber(user.getFriendsCount());
			fellow.setTwittsNumber(user.getStatusesCount());
			fellow.setDescription(user.getDescription());
			fellow.setImageUrl(user.getProfileImageURLHttps());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return fellow;
	}
	
	public ArrayList<Twitt> getTwitts(long id) {
		ArrayList<Twitt> twitts = new ArrayList<Twitt>();
		ResponseList<Status> timeline;
		try {
			timeline = twitter.getUserTimeline(id);
			for(Status status : timeline) {
				Twitt twitt = new Twitt();
				twitt.setCreatedAt(status.getCreatedAt());
				twitt.setText(status.getText());
				twitts.add(twitt);
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return twitts;
	}
	
	public ArrayList<Long> getFriendsIds(long id) {
		ArrayList<Long> friendsIds = new ArrayList<Long>();
		try {
			IDs ids = twitter.getFriendsIDs(id,-1L);
			for(long i : ids.getIDs()) {
				friendsIds.add(i);
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return friendsIds;
	}
	
	/*public Config getConfig() {
		Config config = new Config();
		config.getConfig().add("ConsumerKey");
		config.getConfig().add("ConsumerSecret");
		config.getConfig().add("AccessToken");
		config.getConfig().add("AccessTokenSecret");
		config.getConfig().add("Username");
		return config;
	}*/
	
	public static void main(String[] args) {
		TwitterParser parser = new TwitterParser();
		parser.makeConfiguration();
		parser.enableTwitter();
		//parser.twitter(4016517975L);
		parser.twitter("seselj_vojislav");
	}
	
}
