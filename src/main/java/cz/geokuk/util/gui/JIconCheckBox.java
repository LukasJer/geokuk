package cz.geokuk.util.gui;

import java.awt.*;

import javax.swing.Icon;
import javax.swing.JCheckBox;

import cz.geokuk.framework.Action0;

public class JIconCheckBox extends JCheckBox {
	private abstract class Icon0 implements Icon {
		@Override
		public int getIconHeight() {
			return icon.getIconHeight();
		}

		@Override
		public int getIconWidth() {
			return icon.getIconWidth();
		}
	}

	private class RolloverIcon extends Icon0 {

		@Override
		public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
			icon.paintIcon(c, g, x, y);
			g.setColor(Color.BLACK);
			final int iconWidth = icon.getIconWidth();
			final int iconHeight = icon.getIconHeight();
			g.drawRect(x - 4, y - 4, iconWidth - 1 + 8, iconHeight - 1 + 8);
			g.setColor(Color.RED);
			// int width2 = c.getWidth()-1;
			// int height2 = c.getHeight()-1;
			// g.drawRect(0, 0, width2, height2);
		}

	}

	private class RollOverSelectedIcon extends Icon0 {
		@Override
		public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
			new SelectedIcon().paintIcon(c, g, x, y);
			new RolloverIcon().paintIcon(c, g, x, y);
		}

	}

	private class SelectedIcon extends Icon0 {
		@Override
		public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
			g.setColor(Color.GREEN);
			g.fillRect(x - 2, y - 2, icon.getIconWidth() + 4, icon.getIconHeight() + 4);
			icon.paintIcon(c, g, x, y);
		}

	}

	private static final long serialVersionUID = 1L;

	private Icon icon;

	public JIconCheckBox() {}

	public JIconCheckBox(final Action0 action) {
		super(action);
		setIcon(action.getIcon());
	}

	public JIconCheckBox(final Icon icon) {
		super(icon);
	}

	@Override
	public void setIcon(final Icon defaultIcon) {
		super.setIcon(defaultIcon);
		icon = defaultIcon;
		setRolloverIcon(new RolloverIcon());
		setSelectedIcon(new SelectedIcon());
		setRolloverSelectedIcon(new RollOverSelectedIcon());
	}

}
