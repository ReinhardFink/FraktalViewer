/**	Klasse Feigenbaum f�r Fraktal im Reellen. */

package FraktalViewer.reell.Feigenbaum;

import java.awt.*; // f�r Dimension
import java.awt.geom.*; // f�r Rectangle

import FraktalViewer.*;
import FraktalViewer.RxR.TransformModel;
import FraktalViewer.Fraktal;

/**	Stellt die Grundfunktionalit�t zum Erstellen eines Bildes in Form eines
		Integer - Arrays zur Verf�gung. */
public class Feigenbaum extends Fraktal {

	private int visiblePercent;

	/**	Standardkonstruktor muss f�r automatische Verkettung vorhanden sein */
	//public Feigenbaum() { reset(); };
	
	/** Fraktal wird wieder in den Urzustand zur�ckversetzt */
	public void reset() {
	   super.reset();
	   setMaxIterations(CONSTANTS.ITERATIONS_FEIGENBAUM);
		setStartArea2D(CONSTANTS.START_FEIGENBAUM);
		setVisiblePercent(CONSTANTS.VISIBLE_PERCENT);
	}
	
	/**	Methode um die Variable visiblePercent zu setzen. */
	public void setVisiblePercent(int visiblePercent) {
		this.visiblePercent = (visiblePercent < 0) ? 0 : visiblePercent;
		this.visiblePercent = (this.visiblePercent > 100) ? 100 : this.visiblePercent;
	}
	
	/**	Methode um die Variable visiblePercent abzufragen. */
	public int getVisiblePercent() { return visiblePercent; };
	
				
	/**	Erzeugt ausgehend vom Punkt P(Area.x,Area.y) ein Rechteck zum Punkt Q(Area.x+width,Area.y+height)
			bestehend aus Pixels.width * Pixels.height Punkten.
			Die Daten (Iterationen) werden im Integer - Array[Pixels.width * Pixels.height] zur�ckgegeben */
	public int[] getIterationsArray(Rectangle2D.Double Area2D, Dimension Pixels) {
	
		TransformModel Transform = new TransformModel(Area2D, Pixels);
		int[] pix = new int[Pixels.height * Pixels.width];
		java.util.Arrays.fill(pix,CONSTANTS.BACKGROUND_COLOR1);
		double c = Area2D.x;
		int startVisibleIterations =  (int)(getMaxIterations() * visiblePercent / 100);
		//System.out.println("startVisibleIterations = "+startVisibleIterations);
		for (int i = 0; i < Pixels.width; i++) {
			double x = c;
			for (int j = 0; j  < startVisibleIterations; j++) {x = x*x + c;}; 		// unsichbare Iterationen
			for (int j = startVisibleIterations; j < getMaxIterations(); j++) {   	// sichbare Iterationen
				x = x*x + c;
				int yPixel =  Transform.getPixel_Y(x);
				if ((yPixel >= 0) & (yPixel < Pixels.height)) pix[yPixel * Pixels.width + i] = (255 << 24) | j * CONSTANTS.COLOR_MULT;
			}
			//System.out.println("i= "+i+";  j= "+j+"  ;x= "+x+"; y= "+y);
			c = c + Transform.getPixelSize().getX();
		}
		return pix;
	}

	/** Urspr�ngliche Methode aus Fraktal.java, die das Iterationsarray einf�rbt wird �berschrieben. */
	public int[] getColorArray(Rectangle2D.Double Area2D, Dimension Pixels) { return getIterationsArray(Area2D, Pixels); }
}
