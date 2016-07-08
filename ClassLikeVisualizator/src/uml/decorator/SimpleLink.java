package uml.decorator;

public class SimpleLink implements Link{
	
	protected Link linkForDecoration;

	public SimpleLink(Link linkForDecoration) {
		this.linkForDecoration = linkForDecoration;
	}

	@Override
	public void decorateLink() {
		linkForDecoration.decorateLink();
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return linkForDecoration.getInfo();
	}

}
