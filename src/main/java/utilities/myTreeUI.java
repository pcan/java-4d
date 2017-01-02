/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTreeUI;

/**
 *
 * @author Pierantonio
 */
public class myTreeUI extends BasicTreeUI {

    public boolean lineTypeDashed;

    public myTreeUI() {
        super();
    }

    public void setHashColor2(Color color) {
        super.setHashColor(color);
    }

    /**
     * Paints a vertical line.
     */
    @Override
    protected void paintVerticalLine(Graphics g, JComponent c, int x, int top,
            int bottom) {
        if (lineTypeDashed) {
            drawDashedVerticalLine(g, x, top, bottom);
        } else {
            g.drawLine(x, top, x, bottom);
            g.drawLine(x + 1, top, x + 1, bottom);
        }
    }


    /**
     * Paints a horizontal line.
     */
    @Override
    protected void paintHorizontalLine(Graphics g, JComponent c, int y,
            int left, int right) {
        if (lineTypeDashed) {
            drawDashedHorizontalLine(g, y, left, right);
        } else {
            g.drawLine(left, y, right, y);
            g.drawLine(left, y + 1, right, y + 1);
        }
    }
}
