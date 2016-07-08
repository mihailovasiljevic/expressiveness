package twitterparser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class MainUser extends Fellow implements Serializable {
	
	protected ArrayList<Fellow> friends;

	public MainUser(Long id) {
		super(id);
	}
	
	public MainUser(Fellow fellow) {
		super(fellow);
	}
	
	public void addFriend(Fellow fellow) {
		if(friends == null) {
			friends = new ArrayList<Fellow>();
		}
		friends.add(fellow);
	}

	public ArrayList<Fellow> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<Fellow> friends) {
		this.friends = friends;
	}
	
}
