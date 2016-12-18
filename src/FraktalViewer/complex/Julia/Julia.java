/**	Klasse Julia berechnet Juliamenge im Complexen. */

package FraktalViewer.complex.Julia;

import java.awt.geom.*;

import FraktalViewer.*;
import FraktalViewer.complex.*;

public class Julia extends FraktalComplex {

	/** Comlexes c f�r das die Juliamenge berechnet wird.*/
	private Point2D.Double c;

   /** Fraktal wird wieder in den Urzustand zur�ckversetzt */
	public void reset() {
		super.reset();
		setStartArea2D(CONSTANTS.START_JULIA);
   	set_c(CONSTANTS.C);	
	};

   /** Methode um Variable c abzufragen */
   public Point2D.Double get_c() { return new Point2D.Double(c.getX(),c.getY()); };

   /** Methode um Variable c zu setzen */
   public void set_c(Point2D.Double c) { this.c = new Point2D.Double(c.getX(),c.getY()); };
	
	/**	Iterationsvorschrift f�r eine Juliamenge:
			Startwert complexC = c, comlexZ = (x,y)
			iterate comlexZ = complexZ^2 + complexC until |complexZ|^2 > 4
			
			Iterationsvorschrift f�r die Mandelbrotmenge
	 		Startwert complexC = (x,y),
	 		iterate comlexZ = complexZ^2 + complexC until |complexZ|^2 > 4 */
	
	public int iterate(Point2D.Double P) {
		
		double Zx = P.x;
		double Zy = P.y;
		double ZxQuad = P.x * P.x;
		double ZyQuad = P.y * P.y;
		int Iterations = 0;
		
		do {
			Zy = 2 * Zx * Zy + c.y;
			Zx = ZxQuad - ZyQuad + c.x;
		} while ((++Iterations <= getMaxIterations()) && ((ZxQuad = Zx * Zx) + (ZyQuad = Zy * Zy) < 4));
		return (Iterations);
	}
}
