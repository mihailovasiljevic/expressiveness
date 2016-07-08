package twittersource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import graph.models.GraphLink;
import graph.models.GraphLinkType;
import graph.models.GraphModel;
import graph.models.GraphNode;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import twitter4j.TwitterFactory;
import twitterparser.Fellow;
import twitterparser.MainUser;
import twitterparser.TwitterParser;
import components.ConfigSource;
import components.ConfigSourceReq;
import components.Source;

public class Activator implements BundleActivator {

	private static BundleContext context;
	
	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {

		Activator.context = bundleContext;
		run(Activator.context);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

	public void run(BundleContext context) {
		/*TwitterParser parser = new TwitterParser();
		parser.makeConfiguration();
		parser.enableTwitter();*/
		TwitterGraph graph = new TwitterGraph();
		
		Hashtable<String, Object> dict = new Hashtable<String,Object>();
		dict.put(Source.NAME,"Twitter");
		context.registerService(Source.class.getName(), graph, dict);
	}
	
	public class TwitterGraph implements Source {

		private TwitterParser parser;
		private MainUser user;
		
		public TwitterGraph() {
			parser = new TwitterParser();
		}
		
		@Override
		public ConfigSourceReq getConfigRequirments() {
			ConfigSourceReq config = new ConfigSourceReq();
			ArrayList<String> myConfig = new ArrayList<String>();
			myConfig.add("Consumer Key");
			myConfig.add("Consumer Secret");
			myConfig.add("Access Token");
			myConfig.add("Access Token Secret");
			myConfig.add("Twitter Username");
			config.setConfig(myConfig);
			config.setMain("Twitter Username");
			return config;
		}

		@Override
		public ArrayList<String> setConfig(ConfigSource source) {

			ArrayList<String> errors = new ArrayList<String>();
			HashMap<String, String> map = source.getConfiguration();
			String consumerKey = map.get("Consumer Key");
			String consumerSecret = map.get("Consumer Secret");
			String accessToken = map.get("Access Token");
			String accessTokenSecret = map.get("Access Token Secret");

			
			parser.makeConfiguration(consumerKey, consumerSecret, accessToken, accessTokenSecret);
			//parser.makeConfiguration();
			if(parser.enableTwitter() == true) {
				this.user = parser.twitter(map.get("Twitter Username"));
				if(user == null) {
					errors.add("Configuration is not correctly setted");
				} else {
					
				}
			} else {
				errors.add("Configuration is not correctly setted");
			}
			return errors;
		}
		
		@Override
		public GraphModel getGraph() {

			ArrayList<GraphNode> allNodes = new ArrayList<GraphNode>();
			ArrayList<GraphLink> allLinks = new ArrayList<GraphLink>();
			
			
			
			GraphNode main = new GraphNode();
			main.setParents(new ArrayList<GraphNode>());
			main.setChildren(new ArrayList<GraphNode>());
			
			Long id = user.getId();
			String username = user.getUsername();
			String name = user.getName();
			Integer friendsNumber = user.getFriendsNumber();
			Integer followersNumber = user.getFollowersNumber();
			Integer twittsNumber = user.getTwittsNumber();
			Date createdAt = user.getCreatedAt();
			String description = user.getDescription();
			String imageUrl = user.getImageUrl();
			
			String hours = "";
			if(createdAt.getHours() < 9) {
				hours = "0" + createdAt.getHours();
			} else {
				hours = "" + createdAt.getHours();
			}
			
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(createdAt);
		    int day = cal.get(Calendar.DAY_OF_MONTH);
		    
			String minutes = "";
			if(createdAt.getMinutes() < 9) {
				minutes = "0" + createdAt.getMinutes();
			} else {
				minutes = "" + createdAt.getMinutes();
			}
			
			String created = day + "." + (createdAt.getMonth()+1) + 
					"." + (createdAt.getYear()+1900) + ". " + hours + ":" + minutes;
			
			ArrayList<GraphNode> nodes = new ArrayList<GraphNode>();
			
			HashMap<String, String> features = new HashMap<String, String>();
			features.put("id", Long.toString(id));
			features.put("username", username);
			features.put("name", name);
			features.put("friendsNumber", Integer.toString(friendsNumber));
			features.put("followersNumber", Integer.toString(followersNumber));
			features.put("twittsNumber", Integer.toString(twittsNumber));
			features.put("createdAt", created);
			features.put("description", description);
			features.put("image", imageUrl);
			
			main.setFeatures(features);
			allNodes.add(main);
			
			
			if(user != null) {
				ArrayList<GraphNode> children = new ArrayList<GraphNode>();
				for(int i = 0; i < user.getFriends().size(); i++) {

					//Fellow fellow = new Fellow(user.getFriends().get(i).getId());
					GraphNode user = new GraphNode();
					user.setParents(new ArrayList<GraphNode>());
					user.setChildren(new ArrayList<GraphNode>());
					
					nodes = new ArrayList<GraphNode>();
					
					id = this.user.getFriends().get(i).getId();
					username = this.user.getFriends().get(i).getUsername();
					name = this.user.getFriends().get(i).getName();
					friendsNumber = this.user.getFriends().get(i).getFriendsNumber();
					followersNumber = this.user.getFriends().get(i).getFollowersNumber();
					twittsNumber = this.user.getFriends().get(i).getTwittsNumber();
					createdAt = this.user.getFriends().get(i).getCreatedAt();
					description = this.user.getFriends().get(i).getDescription();
					imageUrl = this.user.getFriends().get(i).getImageUrl();
					
				    cal = Calendar.getInstance();
				    cal.setTime(createdAt);
				    day = cal.get(Calendar.DAY_OF_MONTH);
					
					hours = "";
					if(createdAt.getHours() < 9) {
						hours = "0" + createdAt.getHours();
					} else {
						hours = "" + createdAt.getHours();
					}
					
					minutes = "";
					if(createdAt.getMinutes() < 9) {
						minutes = "0" + createdAt.getMinutes();
					} else {
						minutes = "" + createdAt.getMinutes();
					}
					
					created = day + "." + (createdAt.getMonth()+1) + 
							"." + (createdAt.getYear()+1900) + ". " + hours + ":" + minutes;
					
					features = new HashMap<String, String>();
					features.put("id", Long.toString(id));
					features.put("username", username);
					features.put("name", name);
					features.put("friendsNumber", Integer.toString(friendsNumber));
					features.put("followersNumber", Integer.toString(followersNumber));
					features.put("twittsNumber", Integer.toString(twittsNumber));
					features.put("createdAt", created);
					features.put("description", description);
					features.put("image", imageUrl);
					
					user.setFeatures(features);

					
					GraphLink link = new GraphLink();
					link.setParent(main);
					link.setChild(user);
					GraphLinkType gtype = new GraphLinkType();
					gtype.setLinkType("default");
					link.setLinkType(gtype);

					allNodes.add(user);
					allLinks.add(link);
					
					children.add(user);
				}
				main.setChildren(children);
			}
			
			GraphModel model = new GraphModel();
			model.setNodes(allNodes);
			model.setLinks(allLinks);

			return model;
		}

		@Override
		public HashMap<String, String> getFeaturesForFilteringAndSearching() {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("username","String");
			map.put("name","String");
			map.put("friendsNumber","Integer");
			map.put("followersNumber","Integer");
			map.put("twittsNumber","Integer");
			map.put("description","String");
			return map;
		}

	}
	
}
