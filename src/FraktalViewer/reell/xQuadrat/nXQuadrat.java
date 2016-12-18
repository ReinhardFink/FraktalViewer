/**	Klasse nXQuadrat f�r Fraktal im Reellen. */

package FraktalViewer.reell.xQuadrat;

import java.awt.*;
import java.awt.geom.*;

import FraktalViewer.CONSTANTS;
import FraktalViewer.Fraktal;
import FraktalViewer.RxR.TransformModel;

/**	Stellt die Grundfunktionalit�t zum Erstellen eines Bildes in Form eines
		Integer - Arrays zur Verf�gung. */
public class nXQuadrat extends Fraktal {

	/** Parameter c in Formel x� - c */
	private double c;
	
	/** Parameter n in Formel (x� - c)^n */
	private int n;
	
	/** Iterationen sichtbar? */
	private boolean isXo1Visible;
	private boolean isXo2Visible;
	
	/** Xo Startwert f�r die Iterationen f^n(xo) */
	private double Xo1, Xo2;
	
	//private int visiblePercent;

	/** Methode um Variable c zu stetzen. */
	public void setc(double c) { this.c = c; };
	
	/** Methode um Variable c abzufragen. */
	public double getc() { return c; };
	
	/** Methode um Variable n zu stetzen. */
	public void setn(int n) { this.n = n; } //((n < 1) ? 1 : n); };
	
	/** Methode um Variable n abzufragen. */
	public int getn() { return n; };
	
	/** Methode um Variable Xo1 zu stetzen. */
	public void setXo1(double Xo1) { this.Xo1 = Xo1; };
	/** Methode um Variable Xo2 zu stetzen. */
	public void setXo2(double Xo2) { this.Xo2 = Xo2; };
	
	/** Methode um Variable Xo1 abzufragen. */
	public double getXo1() { return Xo1; };
	/** Methode um Variable Xo2 abzufragen. */
	public double getXo2() { return Xo2; };
	
	/** Methode um Variable isXo1Visible zu stetzen. */
	public void setIsXo1Visible(boolean isXo1Visible) {this.isXo1Visible = isXo1Visible; };
	/** Methode um Variable isXo2Visible zu stetzen. */
	public void setIsXo2Visible(boolean isXo2Visible) {this.isXo2Visible = isXo2Visible; };
	
	/** Methode um Variable isXo1Visible abzufragen. */
	public boolean getIsXo1Visible() { return isXo1Visible; };
	/** Methode um Variable isXo2Visible abzufragen. */
	public boolean getIsXo2Visible() { return isXo2Visible; };
	
	public void reset() {
		super.reset();
		setStartArea2D(CONSTANTS.START_XQUADRAT);
		setc(CONSTANTS.START_C);
		setn(CONSTANTS.START_N);
		setXo1(CONSTANTS.START_Xo1);
		setXo2(CONSTANTS.START_Xo2);
		setIsXo1Visible(CONSTANTS.ISXO1VISIBLE);
		setIsXo2Visible(CONSTANTS.ISXO2VISIBLE);
	}
	
	private double f(double x2D) {
		for(int i = 0; i < n; i++)	x2D = x2D*x2D + c;
		return x2D;
	}
	
	/**	Erzeugt ausgehend vom Punkt P(Area.x,Area.y) ein Rechteck zum Punkt Q(Area.x+width,Area.y+height)
			bestehend aus Pixels.width * Pixels.height Punkten.
			Die Daten (Iterationen) werden im Integer - Array[Pixels.width * Pixels.height] zur�ckgegeben */
	public int[] getIterationsArray(Rectangle2D.Double Area2D, Dimension Pixels) {
	
		TransformModel Transform = new TransformModel(Area2D, Pixels);
		int[] pix = new int[Pixels.height * Pixels.width];
		java.util.Arrays.fill(pix,CONSTANTS.BACKGROUND_COLOR1);
		double x2D = Area2D.x;
		double y2D = 0;
		int y = 0;
		for (int i = 0; i < Pixels.width; i++) {
			// Einzeichnen von y=f(x)=x
			y =  Transform.getPixel_Y(x2D);
			if ((y >= 0) & (y < Pixels.height)) pix[y * Pixels.width + i] = CONSTANTS.COLOR_FX;
			// Berechnung von x�
			y2D = f(x2D);
			//Einzeichnen von y=g(x)=x�
			y = Transform.getPixel_Y(y2D);
			if ((y >= 0) & (y < Pixels.height)) pix[y * Pixels.width + i] = CONSTANTS.COLOR_XQUADRAT;
			// weiterz�hlen auf n�chsten x - Wert
			x2D = x2D + Transform.getPixelSize().getX();
		};
		if (isXo1Visible == true) makeIterations(CONSTANTS.COLOR_START_Xo1,CONSTANTS.COLOR_Xo1_INC,Xo1,Pixels,pix,Transform);
		if (isXo2Visible == true) makeIterations(CONSTANTS.COLOR_START_Xo2,CONSTANTS.COLOR_Xo2_INC,Xo2,Pixels,pix,Transform);
		return pix;	
	};
	
	private void makeIterations(int color, int colorUP, double Xo, Dimension Pixels, int[] pix, TransformModel Transform) {
		// Startwert (Xo/0) f�r den Beginn der Iteration festsetzen
		Point2D.Double P1_2D = new Point2D.Double(Xo,0.0);
		Point2D.Double P2_2D = new Point2D.Double(Xo,0.0);
		for (int i = 0; i < getMaxIterations(); i++) {
			Point p1 = Transform.getPixel(P1_2D);
			Point p2 = Transform.getPixel(P2_2D);
			// �berpr�fung, ob waagrechte Linie gezeichnet werden muss, oder Pixel ausserhalb des sichtbaren Bereichs liegen
			if ((p1.y >= 0  && p1.y < Pixels.height) && (p1.x >= 0 && p1.x < Pixels.width || p2.x >= 0 && p2.x < Pixels.width)) {
				if (p1.x < 0) p1.x = 0; else if (p1.x >= Pixels.height) p1.x = Pixels.height - 1;
				if (p2.x < 0) p2.x = 0; else if (p2.x >= Pixels.height) p2.x = Pixels.height - 1;
				// Linie || zu x - Achse zeichnen
				for (int start = p1.y * Pixels.width + java.lang.Math.min(p1.x,p2.x);
							start < p1.y * Pixels.width + java.lang.Math.max(p1.x,p2.x);
							start++) pix[start] = color;
			}
			color = color + colorUP;
			P1_2D = P2_2D;
			P2_2D = new Point2D.Double(P1_2D.x,f(P1_2D.x));
			p1 = Transform.getPixel(P1_2D);
			p2 = Transform.getPixel(P2_2D);
			// �berpr�fung, ob senkrechte Linie gezeichnet werden muss, oder Pixel ausserhalb des sichtbaren Bereichs liegen
		   if ((p1.x >= 0  && p1.x < Pixels.width) && (p1.y >= 0 && p1.y < Pixels.height || p2.y >= 0 && p2.y < Pixels.height)) {
				if (p1.y < 0) p1.y = 0; else if (p1.y >= Pixels.width) p1.y = Pixels.width - 1;
				if (p2.y < 0) p2.y = 0; else if (p2.y >= Pixels.width) p2.y = Pixels.width - 1;
				// Linie || zu y - Achse zeichnen
				for (int start = java.lang.Math.min(p1.y,p2.y) * Pixels.width + p1.x;
							start < java.lang.Math.max(p1.y,p2.y) * Pixels.width + p1.x;
							start = start + Pixels.width) pix[start] = color;
			}
			color = color + colorUP;
			P1_2D = P2_2D;
			P2_2D = new Point2D.Double(P1_2D.y,P1_2D.y);
		}
		// Querkreuz am Ende der Iteration einzeichnen
		Point p = Transform.getPixel(P1_2D);
		if ((p.x - 2 > 0) && (p.x - 2 < Pixels.width - 1)) {
			if ((p.y - 1 > 0) && (p.y - 1 < Pixels.height - 1)) pix[(p.y - 1) * Pixels.width + p.x - 1] = CONSTANTS.COLOR_BLACK;
			if ((p.y - 2 > 0) && (p.y - 2 < Pixels.height - 1)) pix[(p.y - 2) * Pixels.width + p.x - 2] = CONSTANTS.COLOR_BLACK;
			if ((p.y + 1 > 0) && (p.y + 1 < Pixels.height - 1)) pix[(p.y + 1) * Pixels.width + p.x - 1] = CONSTANTS.COLOR_BLACK;
			if ((p.y + 2 > 0) && (p.y + 2 < Pixels.height - 1)) pix[(p.y + 2) * Pixels.width + p.x - 2] = CONSTANTS.COLOR_BLACK;
		}
		if ((p.x + 2 > 0) && (p.x + 2 < Pixels.width - 1)) {
			if ((p.y - 1 > 0) && (p.y - 1 < Pixels.height - 1)) pix[(p.y - 1) * Pixels.width + p.x + 1] = CONSTANTS.COLOR_BLACK;
			if ((p.y - 2 > 0) && (p.y - 2 < Pixels.height - 1)) pix[(p.y - 2) * Pixels.width + p.x + 2] = CONSTANTS.COLOR_BLACK;
			if ((p.y + 1 > 0) && (p.y + 1 < Pixels.height - 1)) pix[(p.y + 1) * Pixels.width + p.x + 1] = CONSTANTS.COLOR_BLACK;
			if ((p.y + 2 > 0) && (p.y + 2 < Pixels.height - 1)) pix[(p.y + 2) * Pixels.width + p.x + 2] = CONSTANTS.COLOR_BLACK;
		}
	}	
	
	/** Urspr�ngliche Methode aus Fraktal.java, die das Iterationsarray einf�rbt wird �berschrieben. */
	public int[] getColorArray(Rectangle2D.Double Area2D, Dimension Pixels) { return getIterationsArray(Area2D, Pixels); }
}
