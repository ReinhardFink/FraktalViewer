package FraktalViewer;


import java.awt.*;
import java.awt.geom.*;

public class CONSTANTS {

/** Globale Variablen f�r alle Klassen */
	public static final int FONTSIZE = 11;
	public static final int CONTROLSHIGHT = 15;
	public static final String FONTTYPE = "serif";
	public static final Color COLOR_AXIS = Color.black;
	public static final Color COLOR_SELECTED_RECTANGLE = Color.gray;
	public static final int COLOR_BLACK = 255 << 24;
    // Hintergrundfarbe weiss, wenn nicht durch makeColor() gelaufen!!
	public static final int BACKGROUND_COLOR1 = 256*256*256*256 - 1;
	public static final int BACKGROUND_COLOR2 = (255 << 24); // | (256*256*256 - 1);

	/** Dimensionen f�r Labels */
	public static final Dimension LABEL_SHORT = new Dimension(10,CONTROLSHIGHT);
	public static final Dimension LABEL_MEDIUM = new Dimension(35,CONTROLSHIGHT);
	public static final Dimension LABEL_LONG60 =  new Dimension(60,CONTROLSHIGHT);
	public static final Dimension LABEL_LONG =  new Dimension(70,CONTROLSHIGHT);

	/** Dimension f�r Textfelder */
	public static final Dimension TEXTFIELD3 = new Dimension(30,CONTROLSHIGHT);
	public static final Dimension TEXTFIELD4 = new Dimension(40,CONTROLSHIGHT);
	public static final Dimension TEXTFIELD5 = new Dimension(50,CONTROLSHIGHT);
	public static final Dimension TEXTFIELD21 =  new Dimension(170,CONTROLSHIGHT);
		
	/** Anzahl der Iterationen zum Start */
	public static final int MAXITERATIONS = 50;

	
/** Konstante f�r FraktalViewer.java */
	
	/** Gr��e des Bildes zum Anzeigen des Fraktales */
	public static final int XSIZE = 500;
	public static final int YSIZE = 500;
	
	/** Startpunkt des Programmes */
	public static final int XSTART = 30;
	public static final int YSTART = 5;
	
	public static final String POPUP_XQUADRAT = "Reelle Fixpunkte von f(x)= x²+c.";
	public static final String POPUP_NXQUADRAT = "Reelle Fixpunkte von f^n wobei f(x)= x²+c.";
	public static final String POPUP_FEIGENBAUM = "Sammlung alle Reelle Fixpunkte von f(x)= x²+c; c wird auf x-Achse aufgetragen.";
	public static final String POPUP_JULIA = "Complexe Fixpunkte von f(x)=x²+c; c=fix vorgegeben; Fixpunkte dunkel";
	public static final String POPUP_MANDELBROT = "Sammlung aller Complexen Fixpunkte von f(x)=x²+c; c=Pixel(x,y); Fixpunkte dunkel";
	
	
/** Konstante f�r RxR.java */                        	
	/** Anzahl der Pixel f�r Pfeilablenkung aus der Pfeilrichtung */
	public static final Dimension ARROW = new Dimension(4,6);
	public static final int SCALE_HEIGHT = 4;
	public static final int SCALE_NUMBER_LEFT = 12;
	public static final int SCALE_NUMBER_BELOW = 8;
	public static final int RxR_RAND = 1;
	/** Prozentgrenze die der berechnete Ursprung zum Rand halten sollte */
	public static final double ORIGIN_BORDER = 0.1;
	
	/**	Priorit�t des WorkerThreads.
			Der zu setztende Variablenwert muss zwischen 1 (kleinste) und 10 (gr�sste Priorit�t) liegen.
			Da main Thread eine Priorit�t von 5 hat, findet ab "workerPriority" >= 5 keine Unterbrechung mehr statt! */
	public static final int WORKER_PRIORITY = 4;
	
	
/** Konstante f�r RxRplus.java */

	/** Dimension des Panels zur Anzeige der Koordinateninformation */
	public static final Dimension COORDINATES_PANEL = new Dimension(0,45);
	
	/** Beschriftungen f�r die Felder, die Mouse- und Auswahlinformation anzeigen */
	public static final String[] LABELS = {"X", "Y", "X(2D)", "Y(2D)", "StartX", "StartY", "width", "hight", "OriginX(2D)", "OriginY(2D)"};
	
	/** Beschriftung des Popup Menues zum ein bzw. ausschalten des Koordinatensystems */
	public static final String AXIS_VISIBLE_INFORMATION = "Axis visible";
	/** Beschriftung des Popup Menues zum ein bzw. ausschalten der Koordinatensystemskalierung */
	public static final String SCALE_VISIBLE_INFORMATION = "Scale visible";
	/** Beschriftung des Popup Menues zum ein bzw. ausschalten der Koordinatensystemskalierung */
	public static final String SCALE_NUMBERED_VISIBLE_INFORMATION = "ScaleNumbers visible";
	/** Beschriftung des Popup Menues zum ein bzw. ausschalten des CoordinatenPanels */
	public static final String MOUSE_INFORMATION = "Mouse Information";
	
	
/** Konstante f�r PicturePane.java */

	public static final String RESET_BUTTON = "Reset";
	public static final String THREAD_PRIORITY = "Thread Priority & # running Threads";

		
/** Konstante f�r PicturePanePlus.java */

	/** Beschriftung des Eintrages im POPUP Menues zum Ein und Ausschalten der FraktalInformation */
	public static final String FRAKTAL_INFORMATION = "Fraktal Information";
   	
	/** Dimension des Panels zur Anzeige der der Steuerelemente f�r die Fraktale. Reset_Button usw. */
	public static final Dimension ROOT_CONTROL_PANEL = new Dimension(0,0);

			
/** Konstante f�r nXQuadrat.java */

	/** Startwerte f�r die x� - c Iteration */
	public static final Rectangle2D.Double	START_XQUADRAT = new Rectangle2D.Double(-3.0, -3.0, 6.0, 6.0);
	public static final double START_C = 0.0;
	public static final double START_Xo1 = 0.5;
	public static final double START_Xo2 = 0.8;
	public static final int COLOR_FX = ((255 << 24) | 255);
	public static final int COLOR_XQUADRAT = ((255 << 24) | 200);	
	public static final int COLOR_START_Xo1 = ((255 << 24) | 255 << 16);
	public static final int COLOR_START_Xo2 = ((255 << 24) | 255 << 8);
	public static final int COLOR_Xo1_INC = 5;
	public static final int COLOR_Xo2_INC = 5;
	public static final boolean ISXO1VISIBLE = true;
	public static final boolean ISXO2VISIBLE = true;

	/** Startwerte f�r die (x� - c)^n Iteration */
	public static final int START_N = 1;	

/** Konstante f�r Feigenbaum.java */

	/** Startwerte f�r das Feigenbaumfraktal */
	public static final Rectangle2D.Double	START_FEIGENBAUM = new Rectangle2D.Double(-2.5, -2.2, 3.0, 4.4);
   public static final int ITERATIONS_FEIGENBAUM = 800;
   public static final int VISIBLE_PERCENT = 50;
   public static final int COLOR_MULT = 2341;


/** Konstante f�r Julia.java */	

	/** Startwerte f�r das Juliafraktal */
	public static final Rectangle2D.Double	START_JULIA = new Rectangle2D.Double(-2.0, -2.0, 4.0, 4.0);
	public static final Point2D.Double C = new Point2D.Double(0.0,0.0);
	public static double START_CX = 0.0;
	public static double START_CY = 0.0;


/** Konstante f�r Mandelbrot.java */

	/** Startwer f�r das Mandelbrotfraktal */
	public static final Rectangle2D.Double	START_MANDELBROT = new Rectangle2D.Double(-2.5, -2.0, 4.0, 4.0);
	

/** Konstante f�r die Steuerpanels der jeweiligen Fraktale */
	
/** Konstante f�r Panel_Fraktal.java */
	
	/** Breite des ControlPanels */
	public static final Dimension CONTROLPANEL = new Dimension(240,0);
	
	public static final String ITERATIONS = "Iterations:";
	
	
/** Konstante f�r Panel_XQuadrat.java */
	public static final String LABEL_C = "C in x²+c:";
	public static final String XO1_VISIBLE_INFORMATION = "Xo1 Visible";
	public static final String LABEL_XO1 = "Xo1";
	public static final String XO2_VISIBLE_INFORMATION = "Xo2 Visible";
	public static final String LABEL_XO2 = "Xo2";
	public static double C_MIN = -2.1;
	public static double C_MAX = 2.1 + 0.3;
	
/** Konstante f�r Panel_NXQuadrat.java */
	public static final String LABEL_N = "N in f^n";
	

/** Konstante f�r Panel_Feigenbaum.java */

	public static final String LABEL_VISIBLE_PERCENT1 = "visible from";
   public static final String LABEL_VISIBLE_PERCENT2 = "% to 100%";


/** Konstante f�r Panel_Julia.java */
	public static final String LABEL_CX = "Cx";
   public static final String LABEL_CY = "Cy";
   //public static final int MAX_SLIDER = 100;	

}
