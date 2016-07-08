package tree;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

//import twitterparser.MainUser;

public class TwitterTree extends Tree {

	private Display display;
	
	private final int imageSize = 30;
	
	public TwitterTree(Composite parent, int style, Display display) {
		super(parent, style);
		this.display = display;
		
	}

	@Override
	protected void checkSubclass() {
		
	}
	
	/*public void makeTree(MainUser user) {
		TreeItem root = new TreeItem(this,SWT.NONE);
		root.setText(user.getName());
		root.setImage(this.getImageFromUrl(user.getImageUrl()));

		for(int i = 0; i < user.getFriends().size(); i++) {
			TreeItem item = new TreeItem(root, SWT.NONE);
			item.setText(user.getFriends().get(i).getName());
			item.setImage(this.getImageFromUrl(user.getFriends().get(i).getImageUrl()));
		}
			
	}*/
	
	private Image getImageFromUrl(String imgUrl) {
		URL url;
		try {
			url = new URL(imgUrl);
			ImageData img = new ImageData(url.openStream());
			img = img.scaledTo(imageSize,imageSize);
			Image image = new Image(display,img);
			return image;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
}
