package ca.ubc.cpsc210.drawingEditor.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;

import ca.ubc.cpsc210.drawingEditor.editor.DrawingEditor;
import ca.ubc.cpsc210.drawingEditor.figures.Circle;

public class CircleTool extends Tool {
	private Circle current;
	
	public CircleTool(DrawingEditor editor, JComponent parent) {
		super(editor, parent);
		current = null;
	}

	@Override
	protected void createButton(JComponent parent) {
		button = new JButton("Circle");
	}

	@Override
	protected void addListener() {
		button.addActionListener(new CircleToolClickListener());
	}

	@Override
	public void mousePressedInDrawingArea(MouseEvent e) {
		current = new Circle(e.getPoint());
		editor.addToDrawing(current);
	}

	@Override
	public void mouseReleasedInDrawingArea(MouseEvent e) {
		current = null;

	}

	@Override
	public void mouseClickedInDrawingArea(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDraggedInDrawingArea(MouseEvent e) {
		current.setDiameter(e.getPoint());
	}
	
	/**
	 * Listener for mouse events on this circle tool
	 */
	private class CircleToolClickListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			editor.setActiveTool(CircleTool.this);	
		}
	}
}
