package ca.ubc.cpsc210.drawingEditor.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;

import ca.ubc.cpsc210.drawingEditor.editor.DrawingEditor;

public class UnGroupTool extends Tool {
	
	public UnGroupTool(DrawingEditor editor, JComponent parent) {
		super(editor, parent);
	}

	@Override
	protected void createButton(JComponent parent) {
		button = new JButton("UnGroup");
		addToParent(parent);
	}

	@Override
	protected void addListener() {
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editor.setActiveTool(UnGroupTool.this);
				editor.getCurrentDrawing().ungroupSelectedFigures();
			}
		});
	}

	@Override
	public void mousePressedInDrawingArea (MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleasedInDrawingArea (MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClickedInDrawingArea (MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDraggedInDrawingArea (MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
