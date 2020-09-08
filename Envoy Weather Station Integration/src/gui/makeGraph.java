package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Class which creates and displays the data points on the graph.
 * 
 * @author Group 4
 * @version Spring 2020
 */
class makeGraph extends JPanel {
	
	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Data to be used for graph. 
	 */
	private int[] data;
	
	/**
	 * An ArrayList that stores Shape objects.
	 */
	private final ArrayList<Shape> shapes = new ArrayList<>();
	
	/**
	 * The width of the panel.
	 */
	private static final int WIDTH = 300;

	/**
	 * The height of the panel.
	 */
	private static final int HEIGHT = 200;

	/**
	 * Parameterized constructor. 
	 * 
	 * @param theData to be used for the graph.
	 */
	public makeGraph(final int[] theData) {
		super();
		this.data = theData;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		final Shape rectangle = new Rectangle2D.Double(10, 10, getWidth() - 20, getHeight() - 20);
		g2d.draw(rectangle);

		for (int i = 0; i < 24; i++) {

			if(data[i]>0) {
				shapes.add(new Ellipse2D.Double(((getWidth() / 24) * i) + 10, getHeight() - 13 - data[i], 5, 5));
			}
		}
		for (int i = 0; i < shapes.size(); i++) {
			g2d.fill(shapes.get(i));
			g2d.draw(shapes.get(i));
		}
	}
	
}
