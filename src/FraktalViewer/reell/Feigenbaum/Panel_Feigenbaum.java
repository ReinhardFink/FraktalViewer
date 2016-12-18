/**	Klasse zur Darstellung der Infos f�r das Feigenbaum Fraktal.
		Zeigt zur Zeit die Anzahl der maximal zu berechnenden Iterationen sowie
		den Beginn der sichbaren Iteration in Prozent an. */
		
package FraktalViewer.reell.Feigenbaum;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import FraktalViewer.*;

@SuppressWarnings("serial")
public class Panel_Feigenbaum extends Panel_Fraktal {
	
	private JTextField visiblePercent;
	private Feigenbaum myFeigenbaum;	

	/** Standardkonstruktor */
	public Panel_Feigenbaum(Fraktal FraktalInfo) {
		super(FraktalInfo);
		myFeigenbaum = (Feigenbaum)FraktalInfo;
		JPanel myBox = new JPanel();
		JLabel Label = new JLabel(CONSTANTS.LABEL_VISIBLE_PERCENT1);
		Label.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN, CONSTANTS.FONTSIZE));
		myBox.add(Label);	
		visiblePercent = new JTextField();
		visiblePercent.addKeyListener(
			new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					if (e.getKeyChar() ==  KeyEvent.VK_ENTER) {
						myFeigenbaum.setVisiblePercent(new Integer(visiblePercent.getText()).intValue());
	 					getParentPicturePane().onAreaSelected();
					}
				}
		});
		visiblePercent.setPreferredSize(CONSTANTS.TEXTFIELD3);
		visiblePercent.setText(new Integer(myFeigenbaum.getVisiblePercent()).toString());
		this.add(visiblePercent);	
		myBox.add(visiblePercent);
		Label = new JLabel(CONSTANTS.LABEL_VISIBLE_PERCENT2);
		Label.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN, CONSTANTS.FONTSIZE));
		myBox.add(Label);	
		this.add(myBox);		
	};
	
	public void paintComponent(Graphics g) {
		visiblePercent.setText(new Integer(myFeigenbaum.getVisiblePercent()).toString());
		super.paintComponent(g);
	};
}
