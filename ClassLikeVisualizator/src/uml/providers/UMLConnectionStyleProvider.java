package uml.providers;

import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef4.zest.core.viewers.IConnectionStyleProvider;
import org.eclipse.gef4.zest.core.widgets.decoration.IConnectionDecorator;
import org.eclipse.swt.graphics.Color;

public class UMLConnectionStyleProvider implements IConnectionStyleProvider{

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getConnectionStyle(Object rel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Color getColor(Object rel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getHighlightColor(Object rel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLineWidth(Object rel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IFigure getTooltip(Object entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionRouter getRouter(Object rel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IConnectionDecorator getConnectionDecorator(Object rel) {
		// TODO Auto-generated method stub
		return null;
	}

}
