package dialogs;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class GFormField extends Text {

	private String name;
	private String type;
	private boolean req;
	
	public GFormField(Canvas canvas, int style) {
		super(canvas,style);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if(type.equalsIgnoreCase("String")) {
			this.type = type;
		} else
		if(type.equalsIgnoreCase("Integer")) {
			this.type = type;
		}
	}

	public boolean isReq() {
		return req;
	}

	public void setReq(boolean req) {
		this.req = req;
	}
	
	@Override
	protected void checkSubclass() {
		
	}
	
}
