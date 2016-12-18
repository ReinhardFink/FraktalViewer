package FraktalViewer.RxR;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.Dimension;


/** Mathematikobjekt zur Umwandlung von RxR - Koordinaten in Pixel - Koordinaten.
	RxR - Koordinaten: sind im Standard - R² Koordinatensystem gegeben.
	Intervalle sind in der Form [a,b) gegeben
	Pixel - Koordinaten sind im Standard Pixelkoordinatensystem (0/0) links oben gegeben.
	Ein Pixel entspricht der im linken unteren Eck liegenden reellen Zahl. 
*/
public class TransformModel {

	/** Rechteckiger Bereich im R² */
	private Rectangle2D.Double area2D;
	
	/** Länge und Breite eines rechteckiger Bereich in Pixelkoordinaten */
	private Dimension dimension;
	
	/** Dimensionen eines Pixels im R² */
	private Point2D.Double pixelSize;
	
	/** Konstructor */
	public TransformModel(Rectangle2D.Double area2D, Dimension dimension) {
		this.dimension = new Dimension(dimension);
		this.area2D = new Rectangle2D.Double();
		this.area2D.setRect(area2D);
		this.pixelSize = new Point2D.Double(area2D.width/dimension.width, area2D.height/dimension.height);
	}
	
	public Point2D.Double getPixelSize() { return new Point2D.Double(pixelSize.x,pixelSize.y); }

// Transformationen der x & y Koordinaten

	/** Umwandung der x - Koordinate, gegeben in Bildschirmkoordinaten in reelle Koordinaten
		 Pixel -> R² */
	public double getX2D(int n_x) { return area2D.x + pixelSize.x * n_x; }; //n_x von 0 bis n-1
	
	/** Umwandung der y - Koordinate, gegeben in Bildschirmkoordinaten in reelle Koordinaten
		 Pixel -> R² */
	public double getY2D(int n_y) { return area2D.y + pixelSize.y * upSideDownY(n_y); };
		
	private int upSideDownY(int n_y) { 
		return dimension.height - 1 - n_y;
		/** In der gesamten Rechteckhöhe befinden sich dimension.height Pixel,
		    diese sind von 0 bis dimension.height - 1 durchnummeriert.
		    d.h. das Pixel mit der Nummer n_y ist das n_y + 1 Pixel von oben.
		    Unter dem Pixel mit Nummer n_y liegen daher nur mehr dimension.height - (n_y + 1) Pixel.
			Daraus folgt weiter, dass das n_y + 1 Pixel von oben 
			dem dimension.height - (n_y + 1) + 1 Pixel von unten entspricht.
			Werden auch die Pixel von unten von 0 beginnend nummeriert, dann bekommt das
			dimension.height - (n_y + 1) + 1 Pixel von unten, die Nummer:
			dimension.height - (n_y + 1) + 1 - 1 = dimension.height - n_y - 1 
		 */
	}
	 
	/** Umwandung der x -Koordinate, gegeben in reellen Koordinaten in Bildschirmkoordinaten
		 R² -> Pixel */
	public int getPixel_X(double x2D) { return (int)((x2D - area2D.x) / pixelSize.x); }

	/** Umwandung der y -Koordinate, gegeben in reellen Koordinaten in Bildschirmkoordinaten
		 R² -> Pixel */
	public int getPixel_Y(double y2D) { return dimension.height - 1 - (int)((y2D - area2D.y) / pixelSize.y); }
	
	
// Transformationen Punkte

	/** Umwandung von Punkten, gegeben in Bildschirmkoordinaten in reelle Koordinaten
		 Pixel -> R² */
	public Point2D.Double getPoint2D(Point source) {
		Point2D.Double dest = new Point2D.Double();
		dest.x = getX2D(source.x);
		dest.y = getY2D(source.y);
		return dest;	
	};

	/** Umwandung von Punkten, gegeben in reellen Koordinaten in Bildschirmkoordinaten
		 R² -> Pixel */
	public Point getPixel(Point2D.Double source) {
		Point dest = new Point();
		dest.x = getPixel_X(source.x);
		dest.y = getPixel_Y(source.y);
		return dest;
	};		

//Transformationen Rechtecke	
	/** Umwandung von Rechtecken, gegeben in Bilschirmkoordinaten in reelle Koordinaten.
		 Achtung Rechtecke (links oben -> rechts unten) müssen in Rechtecke2D (links unten -> rechts oben) umgewandelt werden.
		 Pixel -> R² */ 
	public Rectangle2D.Double getArea2D(Rectangle source) {
		return new Rectangle2D.Double(getX2D(source.x),
												getY2D(source.y + source.height - 1),
												// heigh - 1, denn erst ab Rechteckslänge 1 kommt ein weiteres Pixel hinzu);
												pixelSize.x * source.width,
												pixelSize.y * source.height);
		
	};
	
	
	private int getNumberOfPixelsRightArea2D(Rectangle2D.Double source) {
		return (int)(float)((area2D.x + area2D.width - (source.x + source.width)) / pixelSize.y);
		//Korrektur mit float um Umwandlungsungenauigkeiten von Dezimal nach Binär und andersrum etwas auszuschalten
	};
		
	private int getNumberOfPixelsAboveArea2D(Rectangle2D.Double source) {
		return (int)(float)((area2D.y + area2D.height - (source.y + source.height)) / pixelSize.y);
	};
	
	public Rectangle getRectangle(Rectangle2D.Double source) {
		return new Rectangle(getPixel_X(source.x),
									getNumberOfPixelsAboveArea2D(source), //n Pixel darüber, dann muss das n+1 mit Nummer n eingeschaltet sein!
									//getNumberOfPixelsRightArea2D(source),
									dimension.width - getPixel_X(source.x) - getNumberOfPixelsRightArea2D(source),
									//getNumberOfPixelsAboveArea2D(source)
									getPixel_Y(source.y) - getNumberOfPixelsAboveArea2D(source) + 1
							  		);
	}
}
	
