/**	Klasse zur Darstellung allgemeiner Infos f�r alle Fraktale.
		Zeigt zur Zeit die Anzahl der maximal zu berechnenden Iterationen an. */
		
package FraktalViewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import FraktalViewer.RxR.PicturePanePlus;

public class Panel_Fraktal extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Textfeld um maxIterations anzuzeigen. */
	private JTextField maxIterations;
	
	private Fraktal FraktalInfo;
	
	private PicturePanePlus ParentPicturePane;
	
	/** Standardkonstruktor */
	public Panel_Fraktal(Fraktal FraktalInfo) {
		JPanel myBox = new JPanel();
		this.FraktalInfo = FraktalInfo;
		this.setBorder(BorderFactory.createBevelBorder(1));
		JLabel Label = new JLabel(CONSTANTS.ITERATIONS);
		Label.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN, CONSTANTS.FONTSIZE));
		Label.setPreferredSize(CONSTANTS.LABEL_LONG60);
		myBox.add(Label);	
		maxIterations = new JTextField();
		maxIterations.addKeyListener(
			new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					if (e.getKeyChar() ==  KeyEvent.VK_ENTER) {
						try { Panel_Fraktal.this.FraktalInfo.setMaxIterations(new Integer(maxIterations.getText()).intValue()); }
						catch (NumberFormatException NFE) {
							maxIterations.setText(new Integer(Panel_Fraktal.this.FraktalInfo.getMaxIterations()).toString());
						}
	 					ParentPicturePane.onAreaSelected();
					}
				}
			});
		maxIterations.setPreferredSize(CONSTANTS.TEXTFIELD5);
		maxIterations.setText(new Integer(FraktalInfo.getMaxIterations()).toString());
		myBox.add(maxIterations);
		JButton Up = new JButton("+1");
		Up.addMouseListener(
			new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					Panel_Fraktal.this.FraktalInfo.setMaxIterations(Panel_Fraktal.this.FraktalInfo.getMaxIterations() + 1);
					maxIterations.setText(new Integer(Panel_Fraktal.this.FraktalInfo.getMaxIterations()).toString());
					ParentPicturePane.onAreaSelected();			
			   };
		});
		myBox.add(Up);
		JButton Down = new JButton("-1");
		Down.addMouseListener(
			new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					if (Panel_Fraktal.this.FraktalInfo.getMaxIterations() == 0) return;
					Panel_Fraktal.this.FraktalInfo.setMaxIterations(Panel_Fraktal.this.FraktalInfo.getMaxIterations() - 1);
					maxIterations.setText(new Integer(Panel_Fraktal.this.FraktalInfo.getMaxIterations()).toString());
					ParentPicturePane.onAreaSelected();			
			   };
		});
		myBox.add(Down);
		this.add(myBox);		
		this.setPreferredSize(CONSTANTS.CONTROLPANEL);
		this.setVisible(true);
	};
	
	/** Setzt die ParentPicturePane um bei �nderungen den Befehl onAreaSelected() der ein Neuzeichnen mit
			neugesetzten Werten erzwingt aufrufen zu k�nnen. */
	public void setPicturePane(PicturePanePlus ParentPicturePane) { this.ParentPicturePane = ParentPicturePane; };
	
	public PicturePanePlus getParentPicturePane() { return ParentPicturePane; }

	public void reset() {
		FraktalInfo.reset();
		maxIterations.setText(new Integer(FraktalInfo.getMaxIterations()).toString());
	}
}
