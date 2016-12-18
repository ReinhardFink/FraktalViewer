package FraktalViewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import FraktalViewer.RxR.PicturePanePlus;
import FraktalViewer.complex.Mandelbrot.*;
import FraktalViewer.complex.Julia.*;
import FraktalViewer.reell.Feigenbaum.*;
import FraktalViewer.reell.xQuadrat.*;

public class startFraktal extends JApplet {

	private static final long serialVersionUID = -1725631214363144223L;

	/** Statische Methode main um Programm mittels java startFraktal zu starten. */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
			// "com.sun.java.swing.plaf.metal.MetalLookAndFeel");
			// "com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch (Exception e) {
		}
		;
		JFrame it = new JFrame("Fraktal Viewer");
		// WindowListener to close Window
		it.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		it.getContentPane().add(getTabbedPane(), BorderLayout.CENTER);
		it.setLocation(CONSTANTS.XSTART, CONSTANTS.YSTART);
		it.pack();
		it.setVisible(true);
	};

	public void init() {
		this.getContentPane().add(getTabbedPane(), BorderLayout.CENTER);
	}

	private static JTabbedPane getTabbedPane() {
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN,
				CONSTANTS.FONTSIZE));
		nXQuadrat myXQuadrat = new nXQuadrat();
		tabbedPane.addTab("x²+c", null, new JScrollPane(new PicturePanePlus(
				myXQuadrat, new Panel_XQuadrat(myXQuadrat), new Dimension(
						CONSTANTS.XSIZE, CONSTANTS.YSIZE))),
				CONSTANTS.POPUP_XQUADRAT);
		nXQuadrat mynXQuadrat = new nXQuadrat();
		tabbedPane.addTab("f^n", null, new JScrollPane(new PicturePanePlus(
				mynXQuadrat, new Panel_nXQuadrat(mynXQuadrat), new Dimension(
						CONSTANTS.XSIZE, CONSTANTS.YSIZE))),
				CONSTANTS.POPUP_NXQUADRAT);
		Feigenbaum myFeigenbaum = new Feigenbaum();
		tabbedPane.addTab("Feigenbaum", null, new JScrollPane(
				new PicturePanePlus(myFeigenbaum, new Panel_Feigenbaum(
						myFeigenbaum), new Dimension(CONSTANTS.XSIZE,
						CONSTANTS.YSIZE))), CONSTANTS.POPUP_FEIGENBAUM);
		Julia myJulia = new Julia();
		tabbedPane.addTab("Juliamenge", null, new JScrollPane(
				new PicturePanePlus(myJulia, new Panel_Julia(myJulia),
						new Dimension(CONSTANTS.XSIZE, CONSTANTS.YSIZE))),
				CONSTANTS.POPUP_JULIA);
		Mandelbrot myMandelbrot = new Mandelbrot();
		tabbedPane.addTab("Mandelbrot", null, new JScrollPane(
				new PicturePanePlus(myMandelbrot, new Panel_Fraktal(
						myMandelbrot), new Dimension(CONSTANTS.XSIZE,
						CONSTANTS.YSIZE))), CONSTANTS.POPUP_MANDELBROT);
		return tabbedPane;
	};
}
