package twitterparser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Fellow implements Serializable {
	
	protected Long id;
	protected String username;
	protected String name;
	protected Integer friendsNumber;
	protected Integer followersNumber;
	protected Integer twittsNumber;
	protected Date createdAt;
	protected String description;
	protected String imageUrl;
	
	protected ArrayList<Twitt> twitts;
	
	public Fellow(Long id) {
		this.id = id;
	}
	
	public Fellow(Fellow fellow) {
		this.id = fellow.id;
		this.username = fellow.username;
		this.name = fellow.name;
		this.friendsNumber = fellow.friendsNumber;
		this.followersNumber = fellow.followersNumber;
		this.twittsNumber = fellow.twittsNumber;
		this.createdAt = fellow.createdAt;
		this.description = fellow.description;
		this.imageUrl = fellow.imageUrl;
	}
	
	private ArrayList<Twitt> statuses;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getFriendsNumber() {
		return friendsNumber;
	}
	public void setFriendsNumber(Integer friendsNumber) {
		this.friendsNumber = friendsNumber;
	}
	public Integer getFollowersNumber() {
		return followersNumber;
	}
	public void setFollowersNumber(Integer followersNumber) {
		this.followersNumber = followersNumber;
	}
	public Integer getTwittsNumber() {
		return twittsNumber;
	}
	public void setTwittsNumber(Integer twittsNumber) {
		this.twittsNumber = twittsNumber;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<Twitt> getTwitts() {
		return twitts;
	}
	public void setTwitts(ArrayList<Twitt> twitts) {
		this.twitts = twitts;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
