package ca.ubc.cpsc210.drawingEditor.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Represents figures in the drawing application.
 */
public abstract class AbstractFigure implements Figure {
	/* offsets for drawing shadow when figure is selected */
	protected static final int DX = 1;
	protected static final int DY = 1;
	/* colour of shadow */
	protected static final Color SHADOW_COLOR = new Color(130, 130, 230);
	/* x and y coordinate of Figure */
	protected int x;
	protected int y;
	/* true if figure is selected, false otherwise */
	protected boolean selected = false;

	/* (non-Javadoc)
	 * @see ca.ubc.cpsc210.drawingEditor.figures.Figure#draw(java.awt.Graphics)
	 */
	public abstract void draw(Graphics g);

	/* (non-Javadoc)
	 * @see ca.ubc.cpsc210.drawingEditor.figures.Figure#contains(java.awt.Point)
	 */
	public abstract boolean contains(Point point);

	/* (non-Javadoc)
	 * @see ca.ubc.cpsc210.drawingEditor.figures.Figure#translate(int, int)
	 */
	public void translate(int dx, int dy) {
		x += dx;
		y += dy;
	}
	
	/* (non-Javadoc)
	 * @see ca.ubc.cpsc210.drawingEditor.figures.Figure#select()
	 */
	public void select() {
		selected = true;
	}
	
	/* (non-Javadoc)
	 * @see ca.ubc.cpsc210.drawingEditor.figures.Figure#unselect()
	 */
	public void unselect() {
		selected = false;
	}
	
	/* (non-Javadoc)
	 * @see ca.ubc.cpsc210.drawingEditor.figures.Figure#isSelected()
	 */
	public boolean isSelected() {
		return selected;
	}
}

