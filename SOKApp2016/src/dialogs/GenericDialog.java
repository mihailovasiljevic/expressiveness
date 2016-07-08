package dialogs;

import gui.MainFrame;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import listeners.CancelListener;
import listeners.ConfirmCFilSear;
import listeners.ConfirmConfiguration;
import listeners.ConfirmUserDialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import components.ConfigSourceReq;
import components.Source;
import components.Visualizer;

public class GenericDialog {

	private Shell shell;
	private Text txt;
	private Shell parent;
	private MainFrame frame;
	private Combo combo;
	
	private int labelLength = 0;
	private int width = 0;
	private int height = 0;
	
	private final int k = 9;
	private final int fieldWidth = 150;
	private final int margin = 30;
	private final int spacing = 10;
	private final int buttonWidth = 60;
	private final int buttonHeight = 25;
	private final int top = 20;
	private final int h = 18;
	private final int mtop = 12;
	private final int ftop = 12;
	private final int bmtop = 15;
	
	private ArrayList<GFormField> fields = new ArrayList<GFormField>();
	private Button okButton;
	private Button cancelButton;
	
	private String name;
	private String mainField;
	
	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Visualizer getVisualizer() {
		return visualizer;
	}

	public void setVisualizer(Visualizer visualizer) {
		this.visualizer = visualizer;
	}

	private Source source;
	private Visualizer visualizer;
	
	public GenericDialog(MainFrame frame, String name) {

		this.parent = frame.getShell();
		this.frame = frame;
		this.name = name;
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        shell.setText(name);
        
	}
	
	public GenericDialog(MainFrame frame, String name, Source source, Visualizer visualizer) {
		this.parent = frame.getShell();
		this.frame = frame;
		this.name = name;
		this.source = source;
		this.visualizer = visualizer;
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        shell.setText(name);
	}
	
	public void makeFields(ArrayList<String> parameters) {
		RowLayout layout = new RowLayout();
        layout.type = SWT.VERTICAL;
        
        layout.marginLeft = this.margin;
        layout.marginTop = this.top;
        layout.center = true;
        shell.setLayout(layout);
        
        int max = -1;
        for(int i = 0; i < parameters.size(); i++) {
        	if(parameters.get(i).length() > max) {
        		max = parameters.get(i).length();
        	}
        }
        this.labelLength = k*max;
        
        this.width = labelLength + this.margin*2 + this.spacing + this.fieldWidth + 30;
        this.height = this.top*2 + (this.h + this.ftop + this.mtop) * parameters.size() + this.bmtop + this.buttonHeight + 30;
        
        for(int i = 0; i < parameters.size(); i++) {
        	
	        Canvas canvas = new Canvas(shell, SWT.NONE);
	        RowLayout composite_layout = new RowLayout();
	        composite_layout.type = SWT.HORIZONTAL;
	        composite_layout.marginTop = this.mtop;
	        composite_layout.spacing = this.spacing;
	        canvas.setLayout(composite_layout);
	        
	        Label label = new Label(canvas,SWT.NONE);
	        label.setText(parameters.get(i));
	        label.setFont(new Font(parent.getDisplay(), new FontData("Arial", this.ftop, SWT.NONE )));
	        label.setLayoutData(new RowData(labelLength,this.h));
	        
	        GFormField txt = new GFormField(canvas,SWT.SINGLE | SWT.BORDER);
	        txt.setFont(new Font(parent.getDisplay(), new FontData("Arial", this.ftop, SWT.NONE )));
	        txt.setLayoutData(new RowData(this.fieldWidth,this.h));
	        fields.add(txt);
	        
        } 
	}
	
	public void makeFields(HashMap<String, String> map) {
		 
		RowLayout layout = new RowLayout();
        layout.type = SWT.VERTICAL;
        
        layout.marginLeft = this.margin;
        layout.marginTop = this.top;
        layout.center = true;
        shell.setLayout(layout);
        
        
        
        int max = -1;
        
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            String key = (String) pair.getKey();
            if(key.length() > max) {
        		max = key.length();
        	}
        }
        
        this.labelLength = k*max;
        

        
        it = map.entrySet().iterator();
        int num = 0;
        while (it.hasNext()) {
        	
        	Map.Entry pair = (Map.Entry)it.next();
        	String key = (String) pair.getKey();
        	String value = (String) pair.getValue();
        	
	        Canvas canvas = new Canvas(shell, SWT.NONE);
	        RowLayout composite_layout = new RowLayout();
	        composite_layout.type = SWT.HORIZONTAL;
	        composite_layout.marginTop = this.mtop;
	        composite_layout.spacing = this.spacing;
	        canvas.setLayout(composite_layout);
	        
	        Label label = new Label(canvas,SWT.NONE);
	        label.setText(key);
	        label.setFont(new Font(parent.getDisplay(), new FontData("Arial", this.ftop, SWT.NONE )));
	        label.setLayoutData(new RowData(labelLength,this.h));
	        
	        GFormField txt = new GFormField(canvas,SWT.SINGLE | SWT.BORDER);
	        txt.setFont(new Font(parent.getDisplay(), new FontData("Arial", this.ftop, SWT.NONE )));
	        txt.setLayoutData(new RowData(this.fieldWidth,this.h));
	        if(value.equalsIgnoreCase("Integer")) {
	        	txt.setName(key+".from");
	        	txt.setType("Integer");
	        } else {
	        	txt.setName(key);
	        	txt.setType("String");
	        }
	        fields.add(txt);
	        num++;
	        
	        if(value.equalsIgnoreCase("Integer")) {
		        canvas = new Canvas(shell, SWT.NONE);
		        composite_layout = new RowLayout();
		        composite_layout.type = SWT.HORIZONTAL;
		        composite_layout.marginTop = this.mtop;
		        composite_layout.spacing = this.spacing;
		        canvas.setLayout(composite_layout);
		        
		        label = new Label(canvas,SWT.NONE);
		        label.setText("to");
		        label.setFont(new Font(parent.getDisplay(), new FontData("Arial", this.ftop, SWT.NONE )));
		        label.setLayoutData(new RowData(labelLength,this.h));
		        
		        txt = new GFormField(canvas,SWT.SINGLE | SWT.BORDER);
		        txt.setFont(new Font(parent.getDisplay(), new FontData("Arial", this.ftop, SWT.NONE )));
		        txt.setLayoutData(new RowData(this.fieldWidth,this.h));
		        txt.setName(key+".to");
		        txt.setType("Integer");
		        fields.add(txt);
		        num++;
	        }
	        
	        
        } 
        
        this.width = labelLength + this.margin*2 + this.spacing + this.fieldWidth + 30;
        this.height = this.top*2 + (this.h + this.ftop + this.mtop) * num + this.bmtop + this.buttonHeight + 30;
		
	}
	
	public void filsear() {
		HashMap<String, String> map = source.getFeaturesForFilteringAndSearching();
		if(map.size() != 0) {
			this.makeFields(map);
		}
		this.setButtons();
		okButton.addListener(SWT.Selection, new ConfirmCFilSear(this));
	}
	
	public void makeFields(Source source) {
		ConfigSourceReq requirments = source.getConfigRequirments();
		this.mainField = source.getConfigRequirments().getMain();
		ArrayList<String> parameters = new ArrayList<String>(requirments.getConfig());
		this.makeFields(parameters);
		for(int i = 0; i < fields.size(); i++) {
			fields.get(i).setName(parameters.get(i));
			fields.get(i).setType("string");
			fields.get(i).setReq(true);
		}
		this.addVisualizers();
		this.setButtons();
		
        okButton.addListener(SWT.Selection, new ConfirmConfiguration(this,source));
	}
	
	public String getValueOfMain() {
		for(int i = 0; i < fields.size(); i++) {
			if(fields.get(i).getName().equals(mainField)) {
				return fields.get(i).getText();
			}
		}
		return null;
	}
	
	private void addVisualizers() {
		Canvas canvas = new Canvas(shell, SWT.NONE);
        RowLayout composite_layout = new RowLayout();
        composite_layout.type = SWT.HORIZONTAL;
        composite_layout.marginTop = this.mtop;
        composite_layout.spacing = this.spacing;
        canvas.setLayout(composite_layout);
        
        Label label = new Label(canvas,SWT.NONE);
        label.setText("Visualizer");
        label.setFont(new Font(parent.getDisplay(), new FontData("Arial", this.ftop, SWT.NONE )));
        label.setLayoutData(new RowData(labelLength,this.h));
		
		this.combo = new Combo(canvas, SWT.READ_ONLY);
		ArrayList<String> names = new ArrayList<String>();
	    Iterator it = frame.getVisualizers().entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        combo.add((String) pair.getKey());
	    }
		this.height = this.height + (this.h + this.ftop + this.mtop) * 1 + this.bmtop;
		combo.setLayoutData(new RowData(135,this.h));
	}
	
	public void setButtons() {
        shell.setSize(this.width, this.height);
        
	    Monitor primary = parent.getDisplay().getPrimaryMonitor();
	    Rectangle bounds = primary.getBounds();
	    Rectangle rect = shell.getBounds();
	    
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 2;

	    shell.setLocation(x, y);
		
	    //int margin = (this.width)/2-this.buttonWidth*2-this.spacing*2;
	    int margin = 30;
        Canvas canvas = new Canvas(shell, SWT.NONE);
        RowLayout composite_layout = new RowLayout();
        composite_layout.type = SWT.HORIZONTAL;
        composite_layout.marginTop = this.bmtop;
        composite_layout.marginLeft = margin;
        composite_layout.spacing = this.spacing*2;
        canvas.setLayout(composite_layout);
        
        okButton = new Button(canvas,SWT.NONE);
        okButton.setText("Ok");
        okButton.setLayoutData(new RowData(this.buttonWidth, this.buttonHeight));
        okButton.setFont(new Font(parent.getDisplay(), new FontData("Arial", 12, SWT.NONE )));
        
        cancelButton = new Button(canvas,SWT.NONE);
        cancelButton.setText("Cancel");
        cancelButton.setLayoutData(new RowData(this.buttonWidth, this.buttonHeight));
        cancelButton.setFont(new Font(parent.getDisplay(), new FontData("Arial", 12, SWT.NONE )));
        cancelButton.addListener(SWT.Selection, new CancelListener(shell));
	}
	
	
	
	public void waiting() {
	
        
        shell.open();
        Display display = parent.getDisplay();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
	}

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public Text getTxt() {
		return txt;
	}

	public void setTxt(Text txt) {
		this.txt = txt;
	}

	public Shell getParent() {
		return parent;
	}

	public void setParent(Shell parent) {
		this.parent = parent;
	}

	public ArrayList<GFormField> getFields() {
		return fields;
	}

	public void setFields(ArrayList<GFormField> fields) {
		this.fields = fields;
	}

	public Combo getCombo() {
		return combo;
	}

	public void setCombo(Combo combo) {
		this.combo = combo;
	}

	public MainFrame getFrame() {
		return frame;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
