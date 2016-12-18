package FraktalViewer.RxR;

import java.awt.*;
import java.awt.image.*;

import FraktalViewer.*;

public class PicturePane extends RxRplus {
	
	private static final long serialVersionUID = 5775554941109213151L;
	
	private Fraktal myFraktal;
	private Image fractalImage;
			
	/**	Konstruktor: Verknüpft die Zeichenebene RxRplus.java mit dem jeweiligen Fraktal */
	public PicturePane(Fraktal myFraktal, Dimension PIXELS) {
		super(myFraktal.getStartArea2D(), PIXELS);
		this.myFraktal = myFraktal;
		int[] PixelArray = myFraktal.getColorArray(getArea2D(), getPixels());
		fractalImage = createImage(new MemoryImageSource(getPixels().width, getPixels().height, PixelArray, 0, getPixels().width));
	};
	
	public Fraktal getMyFraktal() { return myFraktal; };
	
	public void run() {
		addThread();
	   setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	   getRootPane().repaint();
		int[] PixelArray = myFraktal.getColorArray(getSelectedArea2D(), getPixels());
		fractalImage = createImage(new MemoryImageSource(getPixels().width, getPixels().height, PixelArray, 0, getPixels().width));
		setArea2D(getSelectedArea2D()); 	// neues reelles Rechteck wird gesetzt!
		//System.out.println(getSelectedArea2D());
		setAreaSelected(false); 			// neues Bild soll ohne Auswahlrechteck erscheinen
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setSelectedArea(new Rectangle(0,0,getPixels().width,getPixels().height));
		subThread(); 		
		getRootPane().repaint();
	};
	
	public void paintUserStuff(Graphics g) {
	   g.drawImage(fractalImage, 0, 0, this);
	};
}
