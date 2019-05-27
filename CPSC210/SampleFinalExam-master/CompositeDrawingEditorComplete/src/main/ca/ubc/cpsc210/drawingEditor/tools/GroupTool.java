package ca.ubc.cpsc210.drawingEditor.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;

import ca.ubc.cpsc210.drawingEditor.editor.DrawingEditor;

/**
 * Represents selection tools in the drawing application.
 */
public class GroupTool extends Tool {

	/**
	 * Constructor
	 * 
	 * @param editor
	 *            the drawing editor
	 * @param parent
	 *            the parent component to which this tool will be added
	 */
	public GroupTool(DrawingEditor editor, JComponent parent) {
		super(editor, parent);
	}

	@Override
	protected void createButton(JComponent parent) {
		button = new JButton("Group");
		addToParent(parent);
	}

	@Override
	protected void addListener() {
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editor.setActiveTool(GroupTool.this);
				editor.getCurrentDrawing().groupSelectedFigures();
			}
		});
	}

	@Override
	public void mousePressedInDrawingArea(MouseEvent e) {
	}

	@Override
	public void mouseClickedInDrawingArea(MouseEvent e) {

	}

	@Override
	public void mouseDraggedInDrawingArea(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleasedInDrawingArea(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
