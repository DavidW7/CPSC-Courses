package ca.ubc.cpsc210.drawingEditor.figures;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;


/**
 * Represent a composite figure
 */
public class CompositeFigure extends AbstractFigure implements Iterable<Figure> {

	// Composed figures
	private List<Figure> figures;

	/**
	 * Create a composite figure from the list of figures.
	 * @param selectedFigures
	 */
	public CompositeFigure(List<Figure> selectedFigures) {
		figures = selectedFigures;
	}

	@Override
	public void draw(Graphics g) {
		for (Figure f : figures)
			f.draw(g);
	}

	@Override
	public boolean contains(Point point) {
		// It would be better if this implementation computed a
		// bounding rectangle for all figures and used that for
		// containment. With this implementation, contains will 
		// only work for a point within one of the composed figures
		for (Figure f : figures)
			if (f.contains(point))
				return true;
		return false;
	}
	
	@Override
	public void translate(int dx, int dy) {
		for (Figure f: figures)
			f.translate(dx, dy);
	}
	
	@Override
	public void select() {
		super.select();
		for (Figure f: figures)
			f.select();
	}
	
	@Override
	public void unselect() {
		super.unselect();
		for (Figure f: figures)
			f.unselect();
	}

    @Override
    public Iterator<Figure> iterator() {
        return figures.iterator();
    }
}
