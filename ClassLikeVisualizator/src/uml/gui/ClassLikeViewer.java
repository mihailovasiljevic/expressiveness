package uml.gui;

import org.eclipse.gef4.layout.LayoutAlgorithm;
import org.eclipse.gef4.zest.core.viewers.AbstractStructuredGraphViewer;
import org.eclipse.gef4.zest.core.viewers.internal.IStylingGraphModelFactory;
import org.eclipse.gef4.zest.core.widgets.zooming.ZoomManager;
import org.eclipse.swt.widgets.Control;

public class ClassLikeViewer extends AbstractStructuredGraphViewer{

	protected ClassLikeViewer(int graphStyle) {
		super(graphStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setLayoutAlgorithm(LayoutAlgorithm algorithm, boolean run) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected LayoutAlgorithm getLayoutAlgorithm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IStylingGraphModelFactory getFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void applyLayout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ZoomManager getZoomManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Control getControl() {
		// TODO Auto-generated method stub
		return null;
	}

}
