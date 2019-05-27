package ca.ubc.cpsc210.drawingEditor.figures;

import java.awt.Graphics;
import java.awt.Point;

/**
 * Represents a square figure
 */
public class Square extends AbstractFigure {
	
	private Rectangle r;
	
	/**
	 * Constructor creates square with top left corner at given point
	 * and zero side length
	 * @param topLeft  the top left corner of square
	 */
	public Square(Point topLeft) {
		x = (int) topLeft.getX();
		y = (int) topLeft.getY();
		r = new Rectangle(topLeft);
	}
	
	/**
	 * Set the length of the side of this square
	 * @param bottomRight  length of side determined using x coordinate of this point
	 */
	public void setSide(Point bottomRight) {
		int width = (int) (bottomRight.getX() - x);
		r.setHeight(width);
		r.setWidth(width);
	}

	@Override
	public boolean contains(Point point) {
		return r.contains(point);
	}

	@Override
	public void draw(Graphics g) {
		r.draw(g);
	}
	
	@Override
	public void translate(int dx, int dy) {
		super.translate(dx, dy);
		r.translate(dx, dy);
	}
	
	@Override
	public void select() {
		super.select();
		r.select();
	}
	
	@Override
	public void unselect() {
		super.unselect();
		r.unselect();
	}
}
