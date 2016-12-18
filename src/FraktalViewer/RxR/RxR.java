package FraktalViewer.RxR;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

import FraktalViewer.*;

/**	Basisklasse für alle Klassen, die 2D Grafiken darstellen wollen.
	 	Rechteckige Bereiche können ausgewählt werden */
public class RxR extends JPanel implements Runnable {

	private static final long serialVersionUID = -1835574026961452645L;

	//Klassenvariablen:
	/** Anzahl der laufenden Threads.*/
	private static int runningThreads;
	
//Objectvariablen:

	/** Mathematikpaket zur Umrechnung von R²-Koordinaten in Pixel und umgekehrt */
	protected TransformModel Transform;
	
	/** Das zum Zeichnen benützte Pixelrechteck */
	private Rectangle usedRectangle;
	
	/** Am Bildschirm dargestelltes Rechteck im RxR - Koordinaten*/
	private Rectangle2D.Double Area2D;		
	
	/** Bildschirmkoordinaten in Pixeln eines ausgewählten Rechteckes, von links oben -> rechts unten */
	private Rectangle selectedArea;
	
	/** Startpunkt für ausgewähltes Recheck */
	private Point Start;	
						
	/** wurde etwas selektiert? */
	private  boolean areaSelected;
	
	/** soll ein Koordinatensystem gezeichnet werden? */
	private boolean coordinatesSelected;
	
	/** soll das Koordinatensystem skaliert werden? */
	private boolean scaleSelected;
	
	/** soll das Koordinatensystem beschriftet werden? */
	private boolean scaleNumbered;						
	
	/** letzter MouseEvent */
	private MouseEvent lastMouseEvent;		
	
	/** Thread zum Abarbeiten der Dinge, die nach einer Selektion passieren sollen */
	private Thread worker;
	
	/**	Priorität des WorkerThreads.
			Der zu setztende Variablenwert muss zwischen 1 (kleinste) und 10 (grösste Priorit�t) liegen.
			Da main Thread eine Priorität von 5 hat, findet ab "workerPriority" >= 5 keine Unterbrechung mehr statt! */
	private int workerPriority;						
	
	/** Popup Menü um CoordinatesJPane ein- bzw. auszublenden
		Kann nicht in RxRplus verschoben werden, da in this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) nach popupEvent abgefragt werden muss!! */
	private JPopupMenu popup;
	
	/**	Wurde die Popuptaste gedrückt? */
	private boolean ispopuped;
	
//Klassenmethoden:
	/** Mehode um die Anzahl der laufende Threads abzufragen. */	
	public static int getrunningThreads() { return runningThreads; }
	/** Ein Thread meldet sich an. */
	public static void addThread() { runningThreads++ ; }
	/** Ein Thread meldet sich ab. */
	public static void subThread() { runningThreads-- ; }
	
//Konstruktoren:		
	/**	Konstuktor:	in AERA Übergabe des darzustellende Rechteck im R²
							in PIXELS Übergabe der zu verwendeten Bildschirmpunkte */
	public RxR(Rectangle2D.Double AREA, Dimension PIXELS) {
		
		// Initialisierung der Variablen
		this.Transform = new TransformModel(AREA, PIXELS);
		this.usedRectangle = new Rectangle(0,0,PIXELS.width,PIXELS.height);
		this.setArea2D(AREA);
		this.setAreaSelected(false);
		this.setSelectedArea(usedRectangle);
		this.setCoordinatesSelected(true);
		this.setScaleSelected(true);
		this.setScaleNumbered(true);
		this.lastMouseEvent = new MouseEvent(this,0,0,0,0,0,0,false);
		this.popup = new JPopupMenu();
		this.ispopuped = false;
		this.setWorkerPriority(CONSTANTS.WORKER_PRIORITY);
				
		this.setPreferredSize(new Dimension(PIXELS.width + CONSTANTS.RxR_RAND,PIXELS.height + CONSTANTS.RxR_RAND));
		this.setVisible(true);		
			
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!usedRectangle.contains(new Point(e.getX(),e.getY()))) return;
			   if (e.isPopupTrigger()) {
			   	ispopuped = true;
               popup.show(e.getComponent(),e.getX(), e.getY());
               return;
            }
				makeSelectedArea(e);
				lastMouseEvent = e;
				Start = new Point(e.getX(), e.getY());
				selectedArea = new Rectangle(Start);
				setAreaSelected(true);
				//System.out.println("mouse true aeraSelected = "+Start);
			}
			public void mouseReleased(MouseEvent e) {
				if (!usedRectangle.contains(new Point(e.getX(),e.getY()))) {
					if (getAreaSelected() == true) onAreaSelected();
					return;
				}
				if (ispopuped == true) {
			   	ispopuped = false;
			   	return;
			   }
				makeSelectedArea(e);
				lastMouseEvent = e;
				onAreaSelected();
			}
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (!usedRectangle.contains(new Point(e.getX(),e.getY()))) return;
				setAreaSelected(true);	
				makeSelectedArea(e);
				lastMouseEvent = e;
				repaint();
			}
			public void mouseMoved(MouseEvent e) {
				if (!usedRectangle.contains(new Point(e.getX(),e.getY()))) return;
				lastMouseEvent = e;
				repaint();
			}
		});
	} // end public RxR
	
//Objectmethoden:	
	private void makeSelectedArea(MouseEvent e) {
		selectedArea.x = (e.getX() < Start.x) ? e.getX() : Start.x;
		selectedArea.width = java.lang.Math.abs(e.getX() - (Start.x - 1));
		selectedArea.y = (e.getY() < Start.y) ? e.getY() : Start.y;
		selectedArea.height = java.lang.Math.abs(e.getY() - (Start.y - 1));
		// System.out.println("selectedArea = "+ selectedArea);
	}
	
	/**	Abfrage der boolschen Variable "areaSelected", die anzeigt, ob etwas selektiert wurde */
	public boolean getAreaSelected() { return areaSelected; };
	/**	Setzen der boolschen Variable "areaSelected", die anzeigt, ob etwas selektiert wurde */
	public void setAreaSelected(boolean logic) { areaSelected = logic; };
	
	/**	Abfrage der Anzahl der verwendeten Bildschirmpixel */
	public Dimension getPixels() { return new Dimension(usedRectangle.width,usedRectangle.height); };
	
	/**	Abfrage des ausgewähltes Rechteck in Pixeln, gespeichert in der Variable "selectedArea" */
	public Rectangle getSelectedArea() { return new Rectangle(selectedArea); };
	/**	Setzen des ausgewähltes Rechteck in Pixeln, gespeichert in der Variable "selectedArea"
			Achtung Auswahlrechtecke beginnen links oben und gehen dann nach rechts unten. */
	public void setSelectedArea(Rectangle selectedArea) {
		this.selectedArea = new Rectangle();
		this.selectedArea.setRect(selectedArea);
		Start = selectedArea.getLocation();
	}
	
	/**	Abfrage des dargestellten Rechteck in reellen Koordinaten im R², gespeichert in der Variable "Area2D" */
	public Rectangle2D.Double getArea2D() {
		Rectangle2D.Double area2D = new Rectangle2D.Double();
		area2D.setRect(Area2D);
		return area2D;
	}
	
	/**	Setzen des dargestellten Rechteck in reellen Koordinaten im R�, gespeichert in der Variable "Area2D" */
	public void setArea2D(Rectangle2D.Double AREA) {
		this.Area2D = new Rectangle2D.Double();
		this.Area2D.setRect(AREA);
		this.Transform = new TransformModel(AREA, getPixels());
	}
	
	/**	Abfrage des selektierten Rechteck in reellen Koordinaten im R� */ 	
	public Rectangle2D.Double getSelectedArea2D() { return Transform.getArea2D(selectedArea); }
	
	/**	Setzt die Variable, die bestimmt, ob ein Koordinatensystem gezeichnet werden soll oder nicht?
			Befindet sich 0 im x bzw. y - Intervall, dann wird die Achse durch 0 gezeichnet. */
	public void setCoordinatesSelected(boolean logic) { coordinatesSelected = logic; }
	/**	Abfrage der Variable, die bestimmt, ob ein Koordinatensystem gezeichnet werden soll oder nicht?
			Befindet sich 0 im x bzw. y - Intervall, dann wird die Achse durch 0 gezeichnet. */
	public boolean getCoordinatesSelected() { return coordinatesSelected; }
	
	/**	Setzt die boolsche Variable scaleSelected, die steuert ob ein Koordinatensystem gezeichnet werden soll? */
	public void setScaleSelected(boolean scaleSelected) { this.scaleSelected = scaleSelected; }
	/**	Abfrage der boolsche Variable scaleSelected, die steuert ob ein Koordinatensystem gezeichnet werden soll? */
	public boolean getScaleSelected() { return scaleSelected; }	
	
	/**	Setzt die boolsche Variable scaleNumbered, die steuert ob das Koordinatensystem beschriftet werden soll? */
	public void setScaleNumbered(boolean scaleNumbered) { this.scaleNumbered = scaleNumbered; }
	/**	Abfrage der boolsche Variable scaleNumbered, die steuert ob das Koordinatensystem beschriftet werden soll? */
	public boolean getScaleNumbered() { return scaleNumbered; }	
	
	/**	Abfrage des letzten MouseEvents. Achtung lastMouseEvent ist ver�nderbar! */
	public MouseEvent getLastMouseEvent() { return lastMouseEvent; };
	
		
	/**	Ursprung des Koordinatensystem wird in RxR - Koordinaten bestimmt */
	public Point2D.Double getOrigin2D() {
		Point2D.Double origin2D = new Point2D.Double();
		// wird versucht m�glichst kurze Zahl zu finden
		double left =  Area2D.x + Area2D.width * CONSTANTS.ORIGIN_BORDER;  // soll nicht ganz links liegen
		double right = Area2D.x + Area2D.width * (1.0 - CONSTANTS.ORIGIN_BORDER); // soll nicht ganz rechts liegen
		// wenn 0 im Intervall wird Koordinatenursprung nach 0 gelegt
		if (left < 0 && right > 0) origin2D.x = 0;
		else {
			int counter = 0;
			// erste unterschiedliche Stelle finden
			while ((java.lang.Math.ceil(left) - java.lang.Math.ceil(right)) == 0) {
				left = left * 10.0;
				right = right * 10.0;
				counter++;
			} // right nun an letzter Stelle sicher um 1 gr��er als left!!
			double middle = java.lang.Math.ceil((java.lang.Math.ceil(left) + java.lang.Math.ceil(right) - 1) / 2);
			origin2D.x = java.lang.Math.ceil(middle) / java.lang.Math.pow(10,counter);
		}
		double down =  Area2D.y + Area2D.height * CONSTANTS.ORIGIN_BORDER;  // soll nicht ganz links liegen
		double up = Area2D.y + Area2D.height * (1.0 - CONSTANTS.ORIGIN_BORDER); // soll nicht ganz rechts liegen
		// wenn 0 im Intervall wird Koordinatenursprung nach 0 gelegt
   	if (down < 0 && up > 0) origin2D.y = 0;
   	else {
			int counter = 0;
			// erste unterschiedliche Stelle finden
			while ((java.lang.Math.ceil(down) - java.lang.Math.ceil(up)) == 0) {
				down = down * 10.0;
				up = up * 10.0;
				counter++;
			} // right nun an letzter Stelle sicher um 1 gr��er als left!!
			double middle = java.lang.Math.ceil((java.lang.Math.ceil(down) + java.lang.Math.floor(up)) / 2);
			origin2D.y = java.lang.Math.ceil(middle) / java.lang.Math.pow(10,counter);
		}
		return origin2D;
	}
	/**	Ursprung des Koordinatensystem wird bestimmt in Pixel - Koordinaten */
	public Point getOrigin() { return Transform.getPixel(getOrigin2D()); }
	
	/**	Abfrage der int Variable "workerPriority", die die Priorit�t des WorkerThreads angibt. */
	public int getWorkerPrioity() { return workerPriority; }
	/**	Setzen der int Variable "workerPriority", die die Priorit�t des WorkerThreads angibt.
			Der zu setztende Variablenwert muss zwischen 1 (kleinste) und 10 (gr�sste Priorit�t) liegen.
			Da main Thread eine Priorit�t von 5 hat, findet ab "workerPriority" >= 5 keine Unterbrechung mehr statt! */
	public void setWorkerPriority(int workerPriority) {
		if (workerPriority < 1 || workerPriority > 10)
			this.workerPriority = CONSTANTS.WORKER_PRIORITY;
		else
			this.workerPriority = workerPriority;
			//System.out.println("this.workerPriority: " + this.workerPriority);
	}
	
	/** Methode um Men�eintr�ge an Popupmen� anzuh�ngen */
	public void addToPopup(JMenuItem newMenu) {
		popup.add(newMenu);
	}

	/**	 wird ausgef�hrt, wenn etwas selektiert wird */
	public void onAreaSelected() {
		worker = new Thread(this);
		worker.setPriority(workerPriority);
		//System.out.println("Priority = " + worker.getPriority());
		//System.out.println("MINPriority = " + worker.MIN_PRIORITY);
		//System.out.println("MAXPriority = " + worker.MAX_PRIORITY);
		worker.start();
	}
	
	/**	Hier sollte der Code stehen, der ausgef�hrt wird, wenn etwas selektiert wird */
	public void run() {}
	
	/** zeichnet die Komponente neu */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	   paintUserStuff(g);
	   paintAfterUserStuff(g);
	}
	
	/** hier sollen die Zeichenaktionen des Benutzers stehen */
	public void paintUserStuff(Graphics g) {};
	
	/** hier stehen alle Aktionen, die über die Benutzerzeichenaktionen gezeichnet werden sollen */
	private void paintAfterUserStuff(Graphics g) {
			
		if (coordinatesSelected == true) {
			// System.out.println("true coordinateSelected = "+coordinatesSelected);
			Color c = g.getColor();
			g.setColor(CONSTANTS.COLOR_AXIS);
			Point Origin = new Point(getOrigin());
			//System.out.println("Origin = " + Origin);
			
			g.drawLine(Origin.x, 0, Origin.x, usedRectangle.height - 1);	// y - Koordinatenlinie
			// Pfeilspitzen für y -Koordinate
			g.drawLine(Origin.x, 0, Origin.x - CONSTANTS.ARROW.width, CONSTANTS.ARROW.height);
			g.drawLine(Origin.x, 0, Origin.x + CONSTANTS.ARROW.width, CONSTANTS.ARROW.height);
			g.drawLine(Origin.x, usedRectangle.height - 1, Origin.x - CONSTANTS.ARROW.width, usedRectangle.height - CONSTANTS.ARROW.height - 1);
			g.drawLine(Origin.x, usedRectangle.height - 1, Origin.x + CONSTANTS.ARROW.width, usedRectangle.height - CONSTANTS.ARROW.height - 1);
			
			g.drawLine(0, Origin.y, usedRectangle.width - 1, Origin.y);  	// x - Koordinatenlinie
			// Pfeilspitzen f�r x -Koordinate
			g.drawLine(0, Origin.y, CONSTANTS.ARROW.height, Origin.y - CONSTANTS.ARROW.width);
			g.drawLine(0, Origin.y, CONSTANTS.ARROW.height, Origin.y + CONSTANTS.ARROW.width);
			g.drawLine(usedRectangle.height - 1, Origin.y, usedRectangle.height - CONSTANTS.ARROW.height - 1, Origin.y + CONSTANTS.ARROW.width);
			g.drawLine(usedRectangle.height - 1, Origin.y, usedRectangle.height - CONSTANTS.ARROW.height - 1, Origin.y - CONSTANTS.ARROW.width);
			g.setColor(c);
		}
		if (scaleSelected == true) {
			Color c = g.getColor();
			g.setColor(CONSTANTS.COLOR_AXIS);
			int fontSize = g.getFont().getSize();
			int untenOben = fontSize + CONSTANTS.SCALE_NUMBER_BELOW;
			Point2D.Double Origin2D = getOrigin2D();
			Point Origin = Transform.getPixel(Origin2D);
			double scale = calcScale(Area2D.width * (1 - 2 * CONSTANTS.ORIGIN_BORDER));
			double l2D = Origin2D.x - scale;
			// Skalierung x - Achse
			while (l2D > Area2D.x + Area2D.width * CONSTANTS.ORIGIN_BORDER) {
				int l = Transform.getPixel_X(l2D);
				g.drawLine(l, Origin.y - CONSTANTS.SCALE_HEIGHT,l, Origin.y + CONSTANTS.SCALE_HEIGHT);
				if (scaleNumbered == true)
					g.drawString(new Float(l2D).toString(), l - CONSTANTS.SCALE_NUMBER_LEFT, Origin.y + untenOben);
				l2D = l2D - scale;
				untenOben = - untenOben + fontSize;
			}
			l2D = Origin2D.x + scale;
			while (l2D < Area2D.x + Area2D.width * (1 - CONSTANTS.ORIGIN_BORDER)) {
				int l = Transform.getPixel_X(l2D);
				g.drawLine(l, Origin.y - CONSTANTS.SCALE_HEIGHT,l, Origin.y + CONSTANTS.SCALE_HEIGHT);
				if (scaleNumbered == true)
					g.drawString(new Float(l2D).toString(), l - CONSTANTS.SCALE_NUMBER_LEFT, Origin.y + untenOben);
				l2D = l2D + scale;
				untenOben = - untenOben + fontSize;
			}
			// Skalierung y - Achse
			scale = calcScale(Area2D.height * (1 - 2 * CONSTANTS.ORIGIN_BORDER));
			l2D = Origin2D.y - scale;
			untenOben = CONSTANTS.SCALE_NUMBER_LEFT;
			while (l2D > Area2D.y + Area2D.height * CONSTANTS.ORIGIN_BORDER) {
				int l = Transform.getPixel_Y(l2D);
				g.drawLine(Origin.x - CONSTANTS.SCALE_HEIGHT,l, Origin.x + CONSTANTS.SCALE_HEIGHT,l);
				if (scaleNumbered == true)
					g.drawString(new Float(l2D).toString(),Origin.x + untenOben,l);
				l2D = l2D - scale;
			}
			l2D = Origin2D.y + scale;
			while (l2D < Area2D.y + Area2D.height * (1 - CONSTANTS.ORIGIN_BORDER)) {
				int l = Transform.getPixel_Y(l2D);
				g.drawLine(Origin.x - CONSTANTS.SCALE_HEIGHT,l, Origin.x + CONSTANTS.SCALE_HEIGHT,l);
				if (scaleNumbered == true)
					g.drawString(new Float(l2D).toString(),Origin.x + untenOben, l);
				l2D = l2D + scale;
			}
			g.setColor(c);
		}
		if (areaSelected == true) {
			// System.out.println("true aeraSelected = "+aeraSelected);
			Color c = g.getColor();
			g.setColor(CONSTANTS.COLOR_SELECTED_RECTANGLE);
			g.drawRect(selectedArea.x, selectedArea.y, selectedArea.width, selectedArea.height);
			g.setColor(c);
		}	
	}
	
	/** Berechnet eine Skalierung */
	private double calcScale(double intervall) {
		int counter = 0;
		while ((int)intervall == 0) {
			intervall = intervall * 10;
			counter++;
		}			
		if ((int)intervall == 1) {
			intervall = intervall * 10;
			intervall = intervall / 2;
			counter++;
		}
		intervall = 1 / java.lang.Math.pow(10,counter);
		return intervall;		
	}
}
