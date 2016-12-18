package FraktalViewer.complex.Mandelbrot;

import FraktalViewer.*;
import FraktalViewer.complex.*;

public class Mandelbrot extends FraktalComplex {

   /** Fraktal wird wieder in den Urzustand zur�ckversetzt */
   public void reset() {
   	super.reset();
   	setStartArea2D(CONSTANTS.START_MANDELBROT);
   }
	
	/**	Iterationsvorschrift f�r eine Juliamenge:
			Startwert complexC = c, comlexZ = (x,y)
			iterate comlexZ = complexZ^2 + complexC until |complexZ|^2 > 4
			
			Iterationsvorschrift f�r die Mandelbrotmenge
	 		Startwert complexC = (x,y),
	 		iterate comlexZ = complexZ^2 + complexC until |complexZ|^2 > 4 */
		
	public int iterate(java.awt.geom.Point2D.Double P) {
		
		double Zx = P.x;
		double Zy = P.y;
		double ZxQuad = P.x * P.x;
		double ZyQuad = P.y * P.y;
		int Iterations = 0;
		
		do {
			Zy = 2 * Zx * Zy + P.y;
			Zx = ZxQuad - ZyQuad + P.x;
		} while ((++Iterations <= getMaxIterations()) && ((ZxQuad = Zx * Zx) + (ZyQuad = Zy * Zy) < 4));
		return (Iterations);
	}
}
