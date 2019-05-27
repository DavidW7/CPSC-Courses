package ca.ubc.cpsc210.drawingEditor.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;

import ca.ubc.cpsc210.drawingEditor.editor.DrawingEditor;
import ca.ubc.cpsc210.drawingEditor.figures.Square;

/**
 * Represents a tool for drawing a square
 */
public class SquareTool extends Tool {
	
	private Square current;

	public SquareTool(DrawingEditor editor, JComponent parent) {
		super(editor, parent);
		current = null;
	}

	@Override
	protected void createButton(JComponent parent) {
		button = new JButton("Square");
	}
	
	@Override
	protected void addListener() {
		button.addActionListener(new SquareToolClickListener());
	}

	@Override
	public void mouseClickedInDrawingArea(MouseEvent e) {
	}

	@Override
	public void mousePressedInDrawingArea(MouseEvent e) {
		current = new Square(e.getPoint());
		current.setSide(e.getPoint());
		editor.addToDrawing(current);
	}

	@Override
	public void mouseReleasedInDrawingArea(MouseEvent e) {
		current = null;
	}

	@Override
	public void mouseDraggedInDrawingArea(MouseEvent e) {
		current.setSide(e.getPoint());
	}
	
	/**
	 * Listener for mouse events on this square tool
	 */
	private class SquareToolClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			editor.setActiveTool(SquareTool.this);			
		}
	}
}
