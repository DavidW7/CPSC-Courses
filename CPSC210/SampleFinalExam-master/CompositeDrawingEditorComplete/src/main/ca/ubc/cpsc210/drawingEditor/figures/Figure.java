package ca.ubc.cpsc210.drawingEditor.figures;

import java.awt.Graphics;
import java.awt.Point;

public interface Figure {

	/**
	 * Draws the figure on a given graphics object
	 * @param g  the graphics object on which the figure will be drawn.
	 */
	public void draw (Graphics g);

	/**
	 * Determines if the smallest rectangle that bounds this figure contains a 
	 * given point.
	 * @param point  the point to be checked
	 * @return  true if the point to be checked is contained within the rectangle
	 * that bounds this figure, false otherwise. 
	 */
	public boolean contains (Point point);

	/**
	 * Translates this figure by an amount dx in the x direction
	 * and dy in the y direction
	 * @param dx  translation in x direction
	 * @param dy  translation in y direction
	 */
	public void translate (int dx, int dy);

	/**
	 * Marks this figure as selected.
	 */
	public void select ();

	/**
	 * Marks this figure as not selected.  
	 */
	public void unselect ();

	/**
	 * Is the figure selected?
	 */
	public boolean isSelected ();

}