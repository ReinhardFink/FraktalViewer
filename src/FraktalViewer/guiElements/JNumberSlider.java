package FraktalViewer.guiElements;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;

import FraktalViewer.*;

/**	 */
public class JNumberSlider extends JPanel {

	private static final long serialVersionUID = 3659791998872082572L;

	private JTextField NumberField;

	private JSlider NumberSlider;

	private Point2D.Double Range;

	private double lastCorrectNumber;

	public JNumberSlider(boolean TextFieldFirst, String Label,
			Point2D.Double Range, double StartValue) {
		this.setLayout(new GridLayout(2, 1));
		this.NumberField = new JTextField();
		this.NumberField.setPreferredSize(CONSTANTS.TEXTFIELD21);
		this.NumberField.addKeyListener(new myKeyAdapter());
		this.NumberSlider = new JSlider();
		this.reset(Range, StartValue);
		this.NumberSlider.addChangeListener(new SliderListener());
		JLabel label = new JLabel(Label);
		label.setFont(new Font(CONSTANTS.FONTTYPE, Font.PLAIN,
				CONSTANTS.FONTSIZE));
		JPanel panelForLabelAndNumber = new JPanel();
		panelForLabelAndNumber.add(label);
		panelForLabelAndNumber.add(NumberField);
		if (TextFieldFirst == true) {
			this.add(panelForLabelAndNumber);
			this.add(this.NumberSlider);
		} else {
			this.add(this.NumberSlider);
			this.add(this.NumberField);
		}
	}

	/** Setzt die unter und die obere Grenze für das Textfeld und den JSlider. */
	public void setRange(Point2D.Double Range) {
		this.Range = new Point2D.Double();
		this.Range.setLocation(Range);
	}

	public void setNumberField(double Number) {
		if (Number >= Range.x && Number <= Range.x + Range.y) {
			NumberField.setText(new Double(Number).toString());
			lastCorrectNumber = Number;
		}
	}

	public double getNumber() {
		return lastCorrectNumber;
	}

	private int makePerCent() {
		return (int) Math.round((lastCorrectNumber - Range.x) / Range.y
				* (NumberSlider.getMaximum() - NumberSlider.getMinimum()));
	}

	/**
	 * Diese Methode muss überschrieben werden, um mit der slided Number etwas
	 * anzufangen.
	 */
	public void onChange() {
	};

	public void reset(Point2D.Double Range, double StartValue) {
		setRange(Range);
		// System.out.println(Range);
		setNumberField(StartValue);
		NumberSlider.setValue(makePerCent());
	}

	/** KeyListener für das Textfelder NumberField. */
	public class myKeyAdapter extends KeyAdapter {
		public void keyTyped(KeyEvent e) {
			if (e.getKeyChar() == KeyEvent.VK_ENTER) {
				try {
					lastCorrectNumber = new Double(NumberField.getText())
							.doubleValue();
				} catch (NumberFormatException NFE) {
					NumberField.setText(new Double(lastCorrectNumber)
							.toString());
					return;
				}
				if (makePerCent() != NumberSlider.getValue())
					NumberSlider.setValue(makePerCent());
				onChange();
			}
		}
	}

	/** SliderListener für den Slider,NumberSlider. */
	public class SliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			if (makePerCent() != NumberSlider.getValue()) {
				lastCorrectNumber = Range.x
						+ Range.y
						* NumberSlider.getValue()
						/ (NumberSlider.getMaximum() - NumberSlider
								.getMinimum());
				setNumberField(lastCorrectNumber);
				onChange();
			}
		}
	}

}
