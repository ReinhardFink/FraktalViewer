package FraktalViewer.RxR;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

import FraktalViewer.*;

public class RxRplus extends RxR {

	private static final long serialVersionUID = -9116845810555142509L;

	/** Definition der Konstanten siehe Constants.java */
	
	/** Panels zur Anzeige der Koordinateninformationsichtbar? */
	private boolean CoordinatesJPanelVisible;
	
	/** Mouse-Koordinaten in Pixeln sichtbar? */
	private boolean MouseVisible;
		
	/** Mouse-Koordinaten in RxR - Koordinaten sichtbar? */
	private boolean MouseVisible2D;
	
	/** Pixelkoordinaten des ausgew�hlten Bereiches sichtbar? */
	private boolean AreaVisible;
		
	/** RxR - Koordinaten des ausgew�hlten Bereiches sichtbar? */
	private boolean AreaVisible2D;
	
	/** RxR - Koordinaten des Koordinatenkreuzes sichtbar? */
	private boolean OriginVisible2D;
	
	/** Panel zur Darstellung der Textfelder f�r die Koordinateninformation */
	public JPanel CoordinatesJPanel;	
	
	/** Textfelder zur Darstellung der Koordinateninformationen */
	public JTextField MouseX, MouseY, MouseX2D, MouseY2D, selectedX, selectedY, selectedWidth, selectedHeight, OriginX2D, OriginY2D;
	
	/** Checkboxen um Sichtbarkeit des Koordinatensystem, der Skalierung und der Beschriftung zu steuern. */
	public JCheckBoxMenuItem cbMenuItemAxis, cbMenuItemScale, cbMenuItemScaleNumbered;
			
	/** Konstruktor. Setzt per Vorgabe alle boolschen Variablen true; d.h. alle Koordinateninformationen sind sichtbar */
	public RxRplus(Rectangle2D.Double AREA, Dimension PIXELS) {

		super(AREA, PIXELS);	
		// Initialisierung der Variablen
		this.setCoordinatesJPanelVisible(true);
      this.setMouseVisible(true);
		this.setMouseVisible2D(true);
		this.setAreaVisible(true);
		this.setAreaVisible2D(true);
		this.setOriginVisible2D(true);
		// Koordinatenpanel wird organisiert und in BorderLayout eingeh�ngt
		this.setLayout(new BorderLayout());
		//if (getCoordinatesJPanelVisible() == true) this.organizeCoordinatesJPanelLayout();
		this.organizeCoordinatesJPanelLayout();
		this.organizePopup();
		
		this.setPreferredSize(new Dimension(super.getPreferredSize().width,super.getPreferredSize().height + CONSTANTS.COORDINATES_PANEL.height));
		this.setVisible(true);
			
	}; // end public RxRplus
	
	// Methoden um auf private Variablen zuzugreifen	
		
	/** Methode um boolsche Variable CoordinatesJPanelVisible zu stetzen. */
	public void setCoordinatesJPanelVisible(boolean logic) { CoordinatesJPanelVisible = logic; }
	/** Methode um boolsche Variable CoordinatesJPanelVisible abzufragen. */
	public boolean getCoordinatesJPanelVisible() { return CoordinatesJPanelVisible; }
	
   /** Methode um boolsche Variable MouseVisible zu stetzen. */
	public void setMouseVisible(boolean logic) { MouseVisible = logic; }
	/** Methode um boolsche Variable MouseVisible abzufragen. */
	public boolean getMouseVisible() { return MouseVisible; }
	
   /** Methode um boolsche Variable MouseVisible2D zu stetzen. */
	public void setMouseVisible2D(boolean logic) { MouseVisible2D = logic; }
	/** Methode um boolsche Variable MouseVisible2D abzufragen. */
	public boolean getMouseVisible2D() { return MouseVisible2D; }
	
   /** Methode um boolsche Variable AeraVisible zu stetzen. */
	public void setAreaVisible(boolean logic) { AreaVisible = logic; }
	/** Methode um boolsche Variable AeraVisible abzufragen. */
	public boolean getAreaVisible() { return AreaVisible; }
	
   /** Methode um boolsche Variable AreaVisible2D zu stetzen. */		
   public void setAreaVisible2D(boolean logic) { AreaVisible2D = logic; }
   /** Methode um boolsche Variable AreaVisible2D abzufragen. */		
	public boolean getAreaVisible2D() { return AreaVisible2D; }
	
	 /** Methode um boolsche Variable OriginVisible2D zu stetzen. */		
   public void setOriginVisible2D(boolean logic) { OriginVisible2D = logic; }
   /** Methode um boolsche Variable OriginVisible2D abzufragen. */		
	public boolean getOriginVisible2D() { return OriginVisible2D; }

	
	/** Aufbau des Popup Men�s um CoordinatesJPane ein- bzw. auszublenden */
	private void organizePopup() {
		cbMenuItemAxis = new JCheckBoxMenuItem(CONSTANTS.AXIS_VISIBLE_INFORMATION);
		cbMenuItemAxis.setSelected(getCoordinatesSelected());
		cbMenuItemAxis.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				setCoordinatesSelected(!getCoordinatesSelected());
				if (getCoordinatesSelected() == false) {	
					setScaleSelected(false);
					cbMenuItemScale.setSelected(false);
					setScaleNumbered(false);
					cbMenuItemScaleNumbered.setSelected(false);
				}
				getParent().repaint();
			}
		});
		addToPopup(cbMenuItemAxis);
		cbMenuItemScale = new JCheckBoxMenuItem(CONSTANTS.SCALE_VISIBLE_INFORMATION);
		cbMenuItemScale.setSelected(getScaleSelected() && getCoordinatesSelected());
      cbMenuItemScale.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			   setScaleSelected(!getScaleSelected());
			   if (getScaleSelected() == true) {
			   	setCoordinatesSelected(true);
					cbMenuItemAxis.setSelected(true);
				}
				else {
					setScaleNumbered(false);
					cbMenuItemScaleNumbered.setSelected(false);
				}
				getParent().repaint();
			}
		});
		addToPopup(cbMenuItemScale);
		cbMenuItemScaleNumbered = new JCheckBoxMenuItem(CONSTANTS.SCALE_NUMBERED_VISIBLE_INFORMATION);
		cbMenuItemScaleNumbered.setSelected(getScaleNumbered() && getScaleSelected() && getCoordinatesSelected());
      cbMenuItemScaleNumbered.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				setScaleNumbered(!getScaleNumbered());
				if (getScaleNumbered() == true) {
			   	setCoordinatesSelected(true);
					cbMenuItemAxis.setSelected(true);
					setScaleSelected(true);
					cbMenuItemScale.setSelected(true);
				}
				getParent().repaint();
			}
		});
      addToPopup(cbMenuItemScaleNumbered);
		JCheckBoxMenuItem cbMenuItem = new JCheckBoxMenuItem(CONSTANTS.MOUSE_INFORMATION);
		cbMenuItem.setSelected(true);
      cbMenuItem.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				setCoordinatesJPanelVisible(getCoordinatesJPanelVisible() != true);
				CoordinatesJPanel.setVisible(getCoordinatesJPanelVisible());
				if (getCoordinatesJPanelVisible() == true) {
					setPreferredSize(new Dimension(getPreferredSize().width,
																	getPreferredSize().height + CONSTANTS.COORDINATES_PANEL.height));
				}
				else {
					setPreferredSize(new Dimension(getPreferredSize().width,
															 getPreferredSize().height - CONSTANTS.COORDINATES_PANEL.height));
				};
				getParent().repaint();
			}
		});
      addToPopup(cbMenuItem);
	 };
		
	
	/** Methode um die Darstellung des Koordinaten Panels zu organisieren */			
	private void organizeCoordinatesJPanelLayout() {		
		
		CoordinatesJPanel = new JPanel(new GridLayout(2,1));
			Dimension CoordinatesJPanelSize = new Dimension(getPixels().width,CONSTANTS.COORDINATES_PANEL.height);
			CoordinatesJPanel.setPreferredSize(CoordinatesJPanelSize);
			
			JPanel CoordinatesJPanelX = new JPanel(new FlowLayout(FlowLayout.LEFT));   // X - Zeile
			JPanel CoordinatesJPanelY = new JPanel(new FlowLayout(FlowLayout.LEFT));   // Y - Zeile
			if (getMouseVisible() == true) {
			   // X - Zeile
				JLabel Label = new JLabel(CONSTANTS.LABELS[0]);
				Label.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN, CONSTANTS.FONTSIZE));
				Label.setPreferredSize(CONSTANTS.LABEL_SHORT);
				CoordinatesJPanelX.add(Label);
				MouseX = new JTextField();
				MouseX.setPreferredSize(CONSTANTS.TEXTFIELD3);
				CoordinatesJPanelX.add(MouseX);
				// Y - Zeile
				Label = new JLabel(CONSTANTS.LABELS[1]);
				Label.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN, CONSTANTS.FONTSIZE));
				Label.setPreferredSize(CONSTANTS.LABEL_SHORT);
				CoordinatesJPanelY.add(Label);
				MouseY = new JTextField();
				MouseY.setPreferredSize(CONSTANTS.TEXTFIELD3);
				CoordinatesJPanelY.add(MouseY);
			};
			if (getMouseVisible2D() == true) {
				// X - Zeile
				JLabel Label = new JLabel(CONSTANTS.LABELS[2]);
				Label.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN, CONSTANTS.FONTSIZE));
				Label.setPreferredSize(CONSTANTS.LABEL_MEDIUM);
				CoordinatesJPanelX.add(Label);
				MouseX2D = new JTextField();
				MouseX2D.setPreferredSize(CONSTANTS.TEXTFIELD21);
				CoordinatesJPanelX.add(MouseX2D);
				// Y - Zeile
				Label = new JLabel(CONSTANTS.LABELS[3]);
				Label.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN, CONSTANTS.FONTSIZE));
				Label.setPreferredSize(CONSTANTS.LABEL_MEDIUM);
				CoordinatesJPanelY.add(Label);
				MouseY2D = new JTextField();
				MouseY2D.setPreferredSize(CONSTANTS.TEXTFIELD21);
				CoordinatesJPanelY.add(MouseY2D);
			};
			if (getAreaVisible() == true) {
			   // X - Zeile
				JLabel Label = new JLabel(CONSTANTS.LABELS[4]);
				Label.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN, CONSTANTS.FONTSIZE));
				Label.setPreferredSize(CONSTANTS.LABEL_MEDIUM);
				CoordinatesJPanelX.add(Label);
				selectedX = new JTextField();
				selectedX.setPreferredSize(CONSTANTS.TEXTFIELD3);
				CoordinatesJPanelX.add(selectedX);
				Label = new JLabel(CONSTANTS.LABELS[6]);
				Label.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN, CONSTANTS.FONTSIZE));
				Label.setPreferredSize(CONSTANTS.LABEL_MEDIUM);
				CoordinatesJPanelX.add(Label);
				selectedWidth = new JTextField();
				selectedWidth.setPreferredSize(CONSTANTS.TEXTFIELD3);
				CoordinatesJPanelX.add(selectedWidth);
				// Y - Zeile
				Label = new JLabel(CONSTANTS.LABELS[5]);
				Label.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN, CONSTANTS.FONTSIZE));
				Label.setPreferredSize(CONSTANTS.LABEL_MEDIUM);
				CoordinatesJPanelY.add(Label);
				selectedY = new JTextField();
				selectedY.setPreferredSize(CONSTANTS.TEXTFIELD3);
				CoordinatesJPanelY.add(selectedY);
				Label = new JLabel(CONSTANTS.LABELS[7]);
				Label.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN, CONSTANTS.FONTSIZE));
				Label.setPreferredSize(CONSTANTS.LABEL_MEDIUM);
				CoordinatesJPanelY.add(Label);
				selectedHeight = new JTextField();
				selectedHeight.setPreferredSize(CONSTANTS.TEXTFIELD3);
				CoordinatesJPanelY.add(selectedHeight);
			};
			if (getOriginVisible2D() == true) {
			   // X - Zeile
				JLabel Label = new JLabel(CONSTANTS.LABELS[8]);
				Label.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN, CONSTANTS.FONTSIZE));
				Label.setPreferredSize(CONSTANTS.LABEL_LONG);
				CoordinatesJPanelX.add(Label);
				OriginX2D = new JTextField();
				OriginX2D.setPreferredSize(CONSTANTS.TEXTFIELD21);
				CoordinatesJPanelX.add(OriginX2D);
				// Y - Zeile
				Label = new JLabel(CONSTANTS.LABELS[9]);
				Label.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN, CONSTANTS.FONTSIZE));
				Label.setPreferredSize(CONSTANTS.LABEL_LONG);
				CoordinatesJPanelY.add(Label);
				OriginY2D = new JTextField();
				OriginY2D.setPreferredSize(CONSTANTS.TEXTFIELD21);
				CoordinatesJPanelY.add(OriginY2D);
			};
			CoordinatesJPanel.add(CoordinatesJPanelX);
			CoordinatesJPanel.add(CoordinatesJPanelY);
			CoordinatesJPanel.setBorder(BorderFactory.createBevelBorder(1));
		this.add(CoordinatesJPanel, BorderLayout.SOUTH);
	};
	
	public void paintComponent(Graphics g) {
		
		Point lastMousePoint = new Point(getLastMouseEvent().getX(), getLastMouseEvent().getY());
		if (getMouseVisible() == true) {
			MouseX.setText(new Integer(lastMousePoint.x).toString());
			MouseY.setText(new Integer(lastMousePoint.y).toString());
		};
		if (getMouseVisible2D() == true) {
			Point2D.Double lastMousePoint2D = Transform.getPoint2D(lastMousePoint);
			MouseX2D.setText(new Double(lastMousePoint2D.x).toString());
			MouseY2D.setText(new Double(lastMousePoint2D.y).toString());
		};
		if (getAreaVisible() == true) {
			Rectangle selected = new Rectangle(getSelectedArea());
			selectedX.setText(new Integer(selected.x).toString());
			selectedY.setText(new Integer(selected.y).toString());
			selectedWidth.setText(new Integer(selected.width).toString());
			selectedHeight.setText(new Integer(selected.height).toString());
		};
		if (getOriginVisible2D() == true) {
			OriginX2D.setText(new Double(getOrigin2D().x).toString());
			OriginY2D.setText(new Double(getOrigin2D().y).toString());
		}
		super.paintComponent(g);
	};
}
