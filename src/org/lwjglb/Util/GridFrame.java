package org.lwjglb.Util;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Andrea Nardi on 5/19/2017.
 */

public class GridFrame extends JFrame
{
    public GridFrame(Color[][] colors)
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new GridPanel(colors,16));
        pack();
        setVisible(true);
    }
}
class GridPanel extends JPanel
{
    final Color[][] colors;
    final int BLOCK_SIZE;
    public GridPanel(final Color[][] colors, final int BLOCK_SIZE)
    {
        this.colors=colors;
        this.BLOCK_SIZE=BLOCK_SIZE;
        setPreferredSize(new Dimension(colors.length*BLOCK_SIZE,colors[0].length*BLOCK_SIZE));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
    }
    public void paintComponent(Graphics g)
    {
        for(int i=0;i<colors.length;i++)
        {
            for(int j=0;j<colors[i].length;j++)
            {
                g.setColor(colors[i][j]);
                g.fillRect(i*BLOCK_SIZE,j*BLOCK_SIZE,BLOCK_SIZE,BLOCK_SIZE);
            }
        }
    }
}
