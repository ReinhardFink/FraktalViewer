/**	Klasse zur Darstellung der Infos f�r das Julia Fraktal.
		Zeigt zur Zeit die Anzahl der maximal zu berechnenden Iterationen sowie
		den Beginn der sichbaren Iteration in Prozent an. */
		
package FraktalViewer.complex.Julia;
		
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

import FraktalViewer.*;
import FraktalViewer.guiElements.*;

@SuppressWarnings("serial")
public class Panel_Julia extends Panel_Fraktal {
	
	private JNumberSlider changeCx, changeCy;
	private Julia myJulia;
	private Rectangle2D.Double area2D;
	
	/** Standardkonstruktor */
	public Panel_Julia(Fraktal FraktalInfo) {
		super(FraktalInfo);
		myJulia = (Julia)FraktalInfo;
		area2D = myJulia.getStartArea2D();
		JPanel myBox = new JPanel();
		myBox.setLayout(new GridLayout(2,1));
		changeCx = new JNumberSlider(true,CONSTANTS.LABEL_CX,new Point2D.Double(area2D.x, area2D.width),CONSTANTS.START_CX) {
			public void onChange() {
				setC();
			};
		};
      myBox.add(changeCx);
		changeCy = new JNumberSlider(true,CONSTANTS.LABEL_CY,new Point2D.Double(area2D.y, area2D.height),CONSTANTS.START_CY) {
			public void onChange() {
				setC();
			};
		};
      myBox.add(changeCy);	
		this.add(myBox);
		this.setVisible(true);		
	};
	
	private void setC() {
		myJulia.set_c(new Point2D.Double(changeCx.getNumber(),
													changeCy.getNumber()));
		getParentPicturePane().onAreaSelected();
	}
		
	public void reset() {
		super.reset();
		area2D = myJulia.getStartArea2D();
		changeCx.reset(new Point2D.Double(area2D.x, area2D.width),CONSTANTS.START_CX);
		changeCy.reset(new Point2D.Double(area2D.y, area2D.height),CONSTANTS.START_CY);
	}
}
