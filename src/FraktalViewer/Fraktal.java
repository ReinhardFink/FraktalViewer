/** Abstrakte Klasse Fraktal f�r alle Fraktale. Stellt die Grundfunktionen zur Verf�gung. */
package FraktalViewer;

import java.awt.*; // f�r Dimension
import java.awt.geom.*; // f�r Rectangle

/**	Stellt die Grundstruktur zum Erstellen eines Bildes in Form eines
		Integer - Arrays zur Verf�gung */
public abstract class Fraktal {

	/** Anzahl der maximalen Iterationen */
	private int maxIterations;
	/** Voreinzustellender Startbereich im R� f�r jedes Fraktal */
	private Rectangle2D.Double StartArea2D;
		
	/** 	Festlegung des Startbereiches */
	public void setStartArea2D(Rectangle2D.Double StartArea2D) {
		this.StartArea2D = new Rectangle2D.Double();
		this.StartArea2D.setRect(StartArea2D);
		//System.out.println(StartArea2D);	
	}

	/** Standardkonstruktor */
	public Fraktal() { reset(); }
	
	/** Fraktal wird wieder in den Urzustand zur�ckversetzt */
	public void reset() { setMaxIterations(CONSTANTS.MAXITERATIONS); }

	/**	Aufruf des vom Fraktal festgesetzten Startbereiches */
	public Rectangle2D.Double getStartArea2D() { return StartArea2D; }
	
	/**	Setzt die Anzahl der durchzuf�hrenden Iterationen fest */
	public void setMaxIterations(int maxIterations) { this.maxIterations = maxIterations; }
	
	/**	Aufruf der Anzahl der durchzuf�hrenden Iterationen */
	public int getMaxIterations() { return maxIterations; }
	
		
	/**	Funktion zur Umwandlung von Intergerwerten in RGB - Farbe */
	public int makeColor(int Integer) { return ((255 << 24) | ((getMaxIterations() + 1 - Integer ) * 341)); }

	/**	Soll ausgehend vom Punkt P(Area.x,Area.y) ein Rechteck zum Punkt Q(Area.x+width,Area.y+height)
			bestehend aus Pixels.width * Pixels.height Punkten erzeugen.
			Die Daten (Iterationen) sollen im Integer - Array[Pixels.width * Pixels.height] zur�ckgegeben werden. */
	public abstract int[] getIterationsArray(Rectangle2D.Double Area2D, Dimension Pixels);
	
	/**	Ruft die Funktion @getIterationsArray auf. Die zur�ckgelieferten Integerwerte werden dann jedoch
			noch mit makeColor "eingef�rbt". */
	public int[] getColorArray(Rectangle2D.Double Area, Dimension Pixels) {
	
		int coloredArray[];
		coloredArray = getIterationsArray(Area, Pixels);
		for(int i = 0; i < Pixels.height * Pixels.width; i++) coloredArray[i] = makeColor(coloredArray[i]);
		return coloredArray;
	}
}
