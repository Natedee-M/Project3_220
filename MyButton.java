package Project3_220;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;


public abstract class MyButton extends JButton implements MouseListener{
    protected JFrame frame;
    protected JPanel MenuPanel;

    @Override
    public abstract void mouseReleased(MouseEvent e);
    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }
}
