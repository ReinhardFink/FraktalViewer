/**	Klasse FraktalComplex f�r alle Fraktale im Complexen. */
package FraktalViewer.complex;

import java.awt.*; // f�r Dimension
import java.awt.geom.*; // f�r Rectangle

import FraktalViewer.*;
import FraktalViewer.RxR.TransformModel;

/**	Stellt die Grundfunktionalit�t zum Erstellen eines Bildes in Form eines
		Integer - Arrays zur Verf�gung.
		Nur die Funktion iterate(x,y) muss noch mit einer konkreten Iteration
		�berschrieben werden. */
public abstract class FraktalComplex extends Fraktal {

	/**	Diese Funktion soll f�r Punkt P(x/y) einen Iterationswert (Integer) zur�ckliefern. */
	public abstract int iterate(Point2D.Double P);
			
	/**	Erzeugt im reellen Rechteck, vom Punkt P(Area.x,Area.y) zum Punkt Q(Area.x+width,Area.y+height),
			ein Pixelraster bestehend aus Pixels.width * Pixels.height Punkten.
			F�r jeden Pixel wird dann die Funktion iterate(x,y) des �berdeckten reellen Punktes P(x/y) aufgerufen.
			Die Daten (Iterationen) werden im Integer - Array pix[Pixels.width * Pixels.height] zur�ckgegeben.
			Im Pixelarray m�ssen die Punkte in der Reihenfolge von links oben nach rechts unten abgespeichert werden.
			Deshalb l�uft y = y - dy von (Area2D.y + Area2D.height) nach Area2D.y! */
	public int[] getIterationsArray(Rectangle2D.Double Area2D, Dimension Pixels) {
	
		TransformModel Transform = new TransformModel(Area2D, Pixels);
		int[] pix = new int[Pixels.height * Pixels.width];
		for (int j = 0; j < Pixels.height; j++)
			for (int i = 0; i < Pixels.width; i++)
				pix[j * Pixels.width + i] = iterate(Transform.getPoint2D(new Point(i,j)));
		return pix;
	};
}
