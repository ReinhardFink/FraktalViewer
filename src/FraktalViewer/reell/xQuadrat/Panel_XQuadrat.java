/**	Klasse zur Darstellung der Infos f�r das Julia Fraktal.
		Zeigt zur Zeit die Anzahl der maximal zu berechnenden Iterationen sowie
		den Beginn der sichbaren Iteration in Prozent an. */
		
package FraktalViewer.reell.xQuadrat;
		
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

import FraktalViewer.*;
import FraktalViewer.guiElements.*;

@SuppressWarnings("serial")
public class Panel_XQuadrat extends Panel_Fraktal {
	
	private JNumberSlider changeC, changeXo1,  changeXo2;
	private JCheckBoxMenuItem cbXO1Visible, cbXO2Visible;
	private nXQuadrat myXQuadrat;
	//private Rectangle2D.Double area2D;
	
	/** Standardkonstruktor */
	public Panel_XQuadrat(Fraktal FraktalInfo) {
		super(FraktalInfo);
		myXQuadrat = (nXQuadrat)FraktalInfo;
		JPanel myBox = new JPanel();
			myBox.setLayout(new GridLayout(5,1));
			changeC = new JNumberSlider(true,CONSTANTS.LABEL_C,new Point2D.Double(CONSTANTS.C_MIN,CONSTANTS.C_MAX),myXQuadrat.getc()) {
				public void onChange() {
					myXQuadrat.setc(changeC.getNumber());
					getParentPicturePane().onAreaSelected();
				};
			};
    	 	myBox.add(changeC);
    		cbXO1Visible = new JCheckBoxMenuItem(CONSTANTS.XO1_VISIBLE_INFORMATION);
    		cbXO1Visible.setBackground(Color.red);
    		cbXO1Visible.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN, CONSTANTS.FONTSIZE));
    		
      	cbXO1Visible.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					myXQuadrat.setIsXo1Visible(!myXQuadrat.getIsXo1Visible());
					getParentPicturePane().onAreaSelected();
				} 	
			});
			cbXO1Visible.setSelected(myXQuadrat.getIsXo1Visible());
			myBox.add(cbXO1Visible);
			changeXo1 = new JNumberSlider(true,CONSTANTS.LABEL_XO1,
													new Point2D.Double(myXQuadrat.getStartArea2D().x, myXQuadrat.getStartArea2D().width), myXQuadrat.getXo1()) {
				public void onChange() {
					myXQuadrat.setXo1(changeXo1.getNumber());
					getParentPicturePane().onAreaSelected();
				};
			};
			myBox.add(changeXo1);
			cbXO2Visible = new JCheckBoxMenuItem(CONSTANTS.XO2_VISIBLE_INFORMATION);
			cbXO2Visible.setBackground(Color.green);
    		cbXO2Visible.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN, CONSTANTS.FONTSIZE));
      	cbXO2Visible.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					myXQuadrat.setIsXo2Visible(!myXQuadrat.getIsXo2Visible());
					getParentPicturePane().onAreaSelected();
				} 	
			});
			cbXO2Visible.setSelected(myXQuadrat.getIsXo2Visible());
			myBox.add(cbXO2Visible);
			changeXo2 = new JNumberSlider(true,CONSTANTS.LABEL_XO2,
													new Point2D.Double(myXQuadrat.getStartArea2D().x, myXQuadrat.getStartArea2D().width), myXQuadrat.getXo2()) {
				public void onChange() {
					myXQuadrat.setXo2(changeXo2.getNumber());
					getParentPicturePane().onAreaSelected();
				};
			};
			myBox.add(changeXo2);
		this.add(myBox);
		this.setVisible(true);		
	};
	
		
	public void reset() {
		super.reset();
		changeC.reset(new Point2D.Double(CONSTANTS.C_MIN,CONSTANTS.C_MAX),myXQuadrat.getc());
		cbXO1Visible.setSelected(myXQuadrat.getIsXo1Visible());
		changeXo1.reset(new Point2D.Double(myXQuadrat.getStartArea2D().x,myXQuadrat.getStartArea2D().width),myXQuadrat.getXo1());
		cbXO2Visible.setSelected(myXQuadrat.getIsXo2Visible());
		changeXo2.reset(new Point2D.Double(myXQuadrat.getStartArea2D().x,myXQuadrat.getStartArea2D().width),myXQuadrat.getXo2());
	}
}
