package FraktalViewer.RxR;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import FraktalViewer.*;

public class PicturePanePlus extends PicturePane {
	private static final long serialVersionUID = 1297527932992584331L;
	private ControlPanel RootControlPanel;
	private boolean ControlPanelVisible;
	private Panel_Fraktal myPanel_Fraktal;
			
	/**	Konstruktor: Verknüpft die Zeichenebene RxRplus.java mit dem jeweiligen Fraktal */
	public PicturePanePlus(Fraktal myFraktal, Panel_Fraktal myPanel_Fraktal, Dimension PIXELS) {
		super(myFraktal, PIXELS);
		this.myPanel_Fraktal = myPanel_Fraktal;
		this.myPanel_Fraktal.setPicturePane(this);
		this.RootControlPanel = new ControlPanel();
		this.setCoordinatesJPanelVisible(true);
		this.add(RootControlPanel,BorderLayout.EAST);
		this.setPreferredSize(new Dimension(super.getPreferredSize().width + RootControlPanel.getPreferredSize().width,
														super.getPreferredSize().height));
		//System.out.println(myFraktal.getControlPanel().getPreferredSize());																													
		//System.out.println(getPreferredSize());
		this.organizePopup();
	};
	
	/** Methode um boolsche Variable ControlPanelVisible zu stetzen. */
	public void setCoordinatesPanelVisible(boolean logic) { ControlPanelVisible = logic; };
	/** Methode um boolsche Variable ControlJPanelVisible abzufragen. */
	public boolean getCoordinatesPanelVisible() { return ControlPanelVisible; };

		
	private class ControlPanel extends JPanel	{
		
		private static final long serialVersionUID = 6284700341404482454L;
		//JPanel panel1;
		JButton Reset;
		JSlider ThreadPriority;
		JTextField runningThreads;
	
		public ControlPanel() {
			this.setLayout(new BorderLayout());
			JPanel panel1 = new JPanel();
			panel1.setLayout(new GridLayout(3,1));
			panel1.setBorder(BorderFactory.createBevelBorder(1));
				Reset = new JButton(CONSTANTS.RESET_BUTTON);	
				Reset.addMouseListener(
					new MouseAdapter() {
						public void mouseReleased(MouseEvent e) {
							ThreadPriority.setValue(CONSTANTS.WORKER_PRIORITY);
							setWorkerPriority(CONSTANTS.WORKER_PRIORITY);
							setArea2D(getMyFraktal().getStartArea2D());
							getMyFraktal().reset();
							myPanel_Fraktal.reset();
							onAreaSelected();
			   		};
					});
			panel1.add(Reset);
				JPanel panel2 = new JPanel();
					JLabel sliderLabel = new JLabel(CONSTANTS.THREAD_PRIORITY, JLabel.CENTER);
        			sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        			sliderLabel.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN, CONSTANTS.FONTSIZE));
        		panel2.add(sliderLabel);
                runningThreads = new JTextField();
                runningThreads.setPreferredSize(CONSTANTS.TEXTFIELD3);
            panel2.add(runningThreads);
        	panel1.add(panel2);
				ThreadPriority = new JSlider(JSlider.HORIZONTAL,0, 10, CONSTANTS.WORKER_PRIORITY);
				ThreadPriority.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN, CONSTANTS.FONTSIZE));
            //Turn on labels at major tick marks.
        		ThreadPriority.setMajorTickSpacing(2);
        		ThreadPriority.setMinorTickSpacing(1);
        		ThreadPriority.setPaintTicks(true);
       		ThreadPriority.setPaintLabels(true);
        		//ThreadPriority.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        		ThreadPriority.addChangeListener(new SliderListener());
         panel1.add(ThreadPriority);
			this.add(panel1, BorderLayout.NORTH);
			//this.add(PicturePane.this.myPanel_Fraktal,BorderLayout.CENTER);
			this.add(myPanel_Fraktal,BorderLayout.CENTER);
			/**if (PicturePane.this.myPanel_Fraktal.isVisible() == true) {
					setPreferredSize(new Dimension(myPanel_Fraktal.getPreferredSize().width,
															 myPanel_Fraktal.getPreferredSize().height)); }
			else { setPreferredSize(CONSTANTS.ROOT_CONTROL_PANEL); }	*/
		};
		
		class SliderListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider)e.getSource();
           	setWorkerPriority(source.getValue());
        }
    	}
    	
    	public void paintComponent(Graphics g) {
			runningThreads.setText(new Integer(getrunningThreads()).toString());
			super.paintComponent(g);
		};	
	}
	
	/** Aufbau des Popup Menüs um ControlPanel ein- bzw. auszublenden */
	private void organizePopup() {
		JCheckBoxMenuItem cbMenuItem = new JCheckBoxMenuItem(CONSTANTS.FRAKTAL_INFORMATION);
		cbMenuItem.setSelected(true);
      cbMenuItem.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				RootControlPanel.setVisible(RootControlPanel.isVisible() != true);
				if (RootControlPanel.isVisible() == true) {
						//PicturePanePlus.this.
						setPreferredSize(new Dimension(PicturePanePlus.super.getPreferredSize().width + RootControlPanel.getPreferredSize().width,
																 PicturePanePlus.super.getPreferredSize().height));
				}
				else {setPreferredSize(new Dimension(PicturePanePlus.super.getPreferredSize().width - RootControlPanel.getPreferredSize().width,
																 PicturePanePlus.super.getPreferredSize().height)); };
				getParent().repaint();
			}
		});
      addToPopup(cbMenuItem);
	 }	
}
