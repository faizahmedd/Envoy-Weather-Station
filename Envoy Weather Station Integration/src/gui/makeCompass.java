package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

/**
 * Create the compass to display on the GUI.
 * 
 * @author Group 4
 * @version Spring 2020
 */
class makeCompass extends JPanel {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The width of the panel.
	 */
	private static final int WIDTH = 300;

	/**
	 * The height of the panel.
	 */
	private static final int HEIGHT = 200;

	/**
	 * The current windspeed.
	 */
	private String windSpeed;

	/**
	 * Variable to store radian value when converted from degrees.
	 */
	private double radian;

	/**
	 * The x-value in the data point.
	 */
	private double x;

	/**
	 * The y-value in the data point.
	 */
	private double y;
	
	/**
	 * Parameterized constructor. 
	 * 
	 * @param theSpeed current wind speed value.
	 * @param theDirection the direction of the wind. 
	 */
	public makeCompass(int theSpeed, int theDirection) {
		
		super();
		this.windSpeed = Integer.toString(theSpeed);
	
		radian = ((450-theDirection)%360)*Math.PI/180;	// converts compass degrees to radians
		x=80*Math.cos(radian);							// Radius=80
		y=80*Math.sin(radian);
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		final Shape circle = new Ellipse2D.Double(getWidth()/8, 0, getHeight(), getHeight());
		final Shape circle2 = new Ellipse2D.Double((getWidth()/8)+14, 14, 
				getHeight()-28,getHeight()-28);
		g2d.draw(circle);
		g2d.draw(circle2);

		g2d.drawString("o", (getWidth()/2-16)+(int)x, getHeight()/2+5 -(int) y);  /// Need to add better image for pointer
	
		g2d.drawString("Wind", 10,15);
		g2d.drawString("N", getWidth()/2-16, 12);
		g2d.drawString("S", getWidth()/2-16, getHeight()-2);
		g2d.drawString("W", getWidth()/8+2, getHeight()/2);
		g2d.drawString("E", getWidth()-(getWidth()/4), getHeight()/2);
		g2d.drawString(windSpeed, (getWidth()/2)-15, getHeight()/2);
		g2d.drawString("MPH", (getWidth()/2)-25, (getHeight()/2)+15);
	}

}
