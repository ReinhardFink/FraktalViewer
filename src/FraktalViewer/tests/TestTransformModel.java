package FraktalViewer.tests;
/*
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.Dimension;

import fraktale.RxR.*;
import junit.framework.*;

public class TestTransformModel extends TestCase {

	private TransformModel einheitsQuadrat1x1;
	private TransformModel einheitsQuadrat10x10;
	//private TransformModel einheitsQuadrat11x11;
	//private TransformModel einheitsQuadrat100x100;
	private TransformModel einheitsQuadrat1001x1001;
	
	protected void setUp() {
		Rectangle2D.Double einheitsQuadrat = new Rectangle2D.Double(0,0,1,1);
	
		einheitsQuadrat1x1 = new TransformModel(einheitsQuadrat,new Dimension(1,1));
		einheitsQuadrat10x10 = new TransformModel(einheitsQuadrat,new Dimension(10,10));
		//einheitsQuadrat11x11 = new TransformModel(einheitsQuadrat,new Dimension(11,11));
		//einheitsQuadrat100x100 = new TransformModel(einheitsQuadrat,new Dimension(100,100));
		einheitsQuadrat1001x1001 = new TransformModel(einheitsQuadrat,new Dimension(1001,1001));
	}
	
	public static void main(String args[]) {
		junit.textui.TestRunner.run(TestTransformModel.class);
	}
	
	// Tests 1x1
	public void test_1x1_getX2D() {
		//welche reelle Zahl entspricht dem eingegebenen Pixel
		assertEquals(0.0,einheitsQuadrat1x1.getX2D(0),0.0);
	}
	
	public void test_1x1_getY2D() {
		//welche reelle Zahl entspricht dem eingegebenen Pixel
		assertEquals(0.0,einheitsQuadrat1x1.getY2D(0),0.0);
	}
		
	public void test_1x1_getPixel_X() {
		//welches Pixel entspricht der eingegebenen reellen Zahl
		assertEquals(0,einheitsQuadrat1x1.getPixel_X(0.0));
		assertEquals(0,einheitsQuadrat1x1.getPixel_X(0.5));
		assertEquals(0,einheitsQuadrat1x1.getPixel_X(0.99));
	}
	
	public void test_1x1_getPixel_Y() {
		//welches Pixel entspricht der eingegebenen reellen Zahl
		assertEquals(0,einheitsQuadrat1x1.getPixel_Y(0.0));
		assertEquals(0,einheitsQuadrat1x1.getPixel_Y(0.5));
		assertEquals(0,einheitsQuadrat1x1.getPixel_Y(0.99));
	}
	
	// Tests 10x10
	public void test_10x10_getX2D() {
		//welche reelle Zahl entspricht dem eingegebenen Pixel
		assertEquals(0.0,einheitsQuadrat10x10.getX2D(0),0.0);
		assertEquals(0.1,einheitsQuadrat10x10.getX2D(1),0.0);
		assertEquals(0.5,einheitsQuadrat10x10.getX2D(5),0.0);
		assertEquals(0.9,einheitsQuadrat10x10.getX2D(9),0.0);
	}
	
	public void test_10x10_getY2D() {
		//welche reelle Zahl entspricht dem eingegebenen Pixel
		assertEquals(0.0,einheitsQuadrat10x10.getY2D(9),0.0);
		assertEquals(0.1,einheitsQuadrat10x10.getY2D(8),0.0);
		assertEquals(0.5,einheitsQuadrat10x10.getY2D(4),0.0);
		assertEquals(0.9,einheitsQuadrat10x10.getY2D(0),0.0);
	}
		
	public void test_10x10_getPixel_X() {
		//welches Pixel entspricht der eingegebenen reellen Zahl
		assertEquals(0,einheitsQuadrat10x10.getPixel_X(0.0));
		assertEquals(5,einheitsQuadrat10x10.getPixel_X(0.5));
		assertEquals(9,einheitsQuadrat10x10.getPixel_X(0.9));
		assertEquals(9,einheitsQuadrat10x10.getPixel_X(0.99));
	}
	
	public void test_10x10_getPixel_Y() {
		//welches Pixel entspricht der eingegebenen reellen Zahl
		assertEquals(9,einheitsQuadrat10x10.getPixel_Y(0.0));
		assertEquals(4,einheitsQuadrat10x10.getPixel_Y(0.5));
		assertEquals(0,einheitsQuadrat10x10.getPixel_Y(0.9));
		assertEquals(0,einheitsQuadrat10x10.getPixel_Y(0.99));
	}
	
	public void test_10x10_getArea2D() {
		assertEquals(new Rectangle2D.Double(0.2,0.4,0.8,0.4),
						einheitsQuadrat10x10.getArea2D(new Rectangle(new Point(2,2),new Dimension(8,4))));
		assertEquals(new Rectangle2D.Double(0.0,0.0,0.1,0.1),
						 einheitsQuadrat10x10.getArea2D(new Rectangle(new Point(0,9),new Dimension(1,1))));
		assertEquals(new Rectangle2D.Double(0.0,0.0,0.1,0.2),
						 einheitsQuadrat10x10.getArea2D(new Rectangle(new Point(0,8),new Dimension(1,2))));
	}
	
	public void test_10x10_getRectangle() {
		assertEquals(new Rectangle(new Point(0,9),new Dimension(1,1)),
						einheitsQuadrat10x10.getRectangle(new Rectangle2D.Double(0.0,0.0,0.09,0.09)));
		assertEquals(new Rectangle(new Point(2,2),new Dimension(8,4)),
						einheitsQuadrat10x10.getRectangle(new Rectangle2D.Double(0.2,0.4,0.8,0.4)));
		assertEquals(new Rectangle(new Point(2,2),new Dimension(8,4)),
						einheitsQuadrat10x10.getRectangle(new Rectangle2D.Double(0.21,0.41,0.79,0.39)));
		assertEquals(new Rectangle(new Point(2,2),new Dimension(8,4)),
						einheitsQuadrat10x10.getRectangle(new Rectangle2D.Double(0.29,0.49,0.71,0.31)));
		assertEquals(new Rectangle(new Point(2,1),new Dimension(8,5)),
						einheitsQuadrat10x10.getRectangle(new Rectangle2D.Double(0.21,0.41,0.8,0.4)));
		assertEquals(new Rectangle(new Point(2,1),new Dimension(8,5)),
						einheitsQuadrat10x10.getRectangle(new Rectangle2D.Double(0.23,0.43,0.8,0.4)));
		assertEquals(new Rectangle(new Point(0,0),new Dimension(10,10)),
						einheitsQuadrat10x10.getRectangle(new Rectangle2D.Double(0.0,0.0,1.0,1.0)));
		assertEquals(new Rectangle(new Point(0,0),new Dimension(10,10)),
						einheitsQuadrat10x10.getRectangle(new Rectangle2D.Double(0.09,0.09,0.91,0.91)));
		assertEquals(new Rectangle(new Point(0,0),new Dimension(10,10)),
						einheitsQuadrat10x10.getRectangle(new Rectangle2D.Double(0.09,0.09,0.9,0.9)));
	}
	
	// Tests 1001x1001
	public void test_1001x1001_getX2D() {
		//welche reelle Zahl entspricht dem eingegebenen Pixel
		assertEquals(0.0,einheitsQuadrat1001x1001.getX2D(0),0.0);
		assertEquals(1.0/1001,einheitsQuadrat1001x1001.getX2D(1),0.0);
		assertEquals(1.0/1001.0*500.0,einheitsQuadrat1001x1001.getX2D(500),0.0);
		assertEquals(1.0/1001.0*1000.0,einheitsQuadrat1001x1001.getX2D(1000),0.0);
	}
	
	public void test_1001x1001_getY2D() {
		//welche reelle Zahl entspricht dem eingegebenen Pixel
		assertEquals(0.0,einheitsQuadrat1001x1001.getY2D(1000),0.0);
		assertEquals(1.0/1001,einheitsQuadrat1001x1001.getY2D(999),0.0);
		assertEquals(1.0/1001.0*500.0,einheitsQuadrat1001x1001.getY2D(500),0.0);
		assertEquals(1.0/1001.0*1000.0,einheitsQuadrat1001x1001.getY2D(0),0.0);
	}
		
	public void test_1001x1001_getPixel_X() {
		//welches Pixel entspricht der eingegebenen reellen Zahl
		assertEquals(0,einheitsQuadrat1001x1001.getPixel_X(0.0));
		assertEquals(500,einheitsQuadrat1001x1001.getPixel_X(0.5));
		assertEquals((int)(0.9/(1.0/1001.0)),einheitsQuadrat1001x1001.getPixel_X(0.9));
		assertEquals(1000,einheitsQuadrat1001x1001.getPixel_X(0.99999));
	}
	
	public void test_1001x1001_getPixel_Y() {
		//welches Pixel entspricht der eingegebenen reellen Zahl
		assertEquals(1000,einheitsQuadrat1001x1001.getPixel_Y(0.0));
		assertEquals(1000 - (int)(0.5/(1.0/1001)),einheitsQuadrat1001x1001.getPixel_Y(0.5));
		assertEquals(1000 - (int)(0.9/(1.0/1001.0)),einheitsQuadrat1001x1001.getPixel_Y(0.9));
		assertEquals(0,einheitsQuadrat1001x1001.getPixel_Y(0.99999));
	}
	
	public void test_1001x1001_getPoint2D() {
		assertEquals(new Point2D.Double(1.0/1001.0*500.0,1.0/1001.0*500.0),einheitsQuadrat1001x1001.getPoint2D(new Point(500,500)));
	};
	
	public void test_1001x1001_getPixel() {
		assertEquals(new Point(500,500),einheitsQuadrat1001x1001.getPixel(new Point2D.Double(1.0/1001.0*500.0,1.0/1001.0*500.0)));
	};
	
	public void test_1001x1001_getArea2D() {
		assertEquals(new Rectangle2D.Double(1.0/1001.0*500.0,1.0/1001.0,500.0/1001.0,500.0/1001.0),einheitsQuadrat1001x1001.getArea2D(new Rectangle(new Point(500,500),new Dimension(500,500))));
	}
	
	public void test_1001x1001_getRectangle() {
		assertEquals(new Rectangle(new Point(0,1000),new Dimension(1,1)),
						einheitsQuadrat1001x1001.getRectangle(new Rectangle2D.Double(0.0,0.0,0.0001,0.0001)));
		assertEquals(new Rectangle(new Point(0,0),new Dimension(1001,1001)),
						einheitsQuadrat1001x1001.getRectangle(new Rectangle2D.Double(0.0,0.0,1.0,1.0)));

	}
}
*/