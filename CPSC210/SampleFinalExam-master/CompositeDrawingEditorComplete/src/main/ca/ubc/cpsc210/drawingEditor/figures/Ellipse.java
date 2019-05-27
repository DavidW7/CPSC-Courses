package ca.ubc.cpsc210.drawingEditor.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Ellipse extends AbstractFigure {

	private int width;
	private int height;
	
	public Ellipse(Point topLeft) {
		x = (int) topLeft.getX();
		y = (int) topLeft.getY();
		width = 0;
		height = 0;
	}
	
	@Override
	public void draw(Graphics g) {
		if (selected) {
			Color save = g.getColor();
			g.setColor(SHADOW_COLOR);
			g.drawOval(x - DX, y - DY, width + 2 * DX, height + 2 * DY);
			g.setColor(save);
		}
		g.drawOval(x, y, width, height);
	}

	@Override
	public boolean contains(Point point) {
		int point_x = (int) point.getX();
		int point_y = (int) point.getY();
		if ( (x <= point_x) && (point_x <= x+width) &&
				(y <= point_y) && (point_y <= y+height))
			return true;
		return false;
	}
	
	/**
	 * Sets the width of this rectangle
	 * @param bottomRight  width determined using x coordinate of this point
	 */
	public void setWidth(Point bottomRight) {
		width = ((int) bottomRight.getX()) - x;
	}
	
	/**
	 * Sets the height of this rectangle
	 * @param bottomRight  height determined using y coordinate of this point
	 */
	public void setHeight(Point bottomRight) {
		height = ((int) bottomRight.getY()) - y;
	}
	
	/**
	 * Sets the width of this rectangle
	 * @param w  the width of the rectangle
	 */
	public void setWidth(int w) {
		width = w;
	}
	
	/**
	 * Sets the height of this rectangle
	 * @param h  the height of the rectangle
	 */
	public void setHeight(int h) {
		height = h;
	}

}
