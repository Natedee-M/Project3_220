package Project3_220;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;


public abstract class MyButton extends JButton implements MouseListener, Path{
    protected MainApplication Frame;
    protected JLayeredPane ContentPane;
    protected JPanel MenuPanel;
    protected JPanel OwnPanel;
    protected boolean Entered = false;
    
    //public void setPanel(JPanel MenuPanel){ this.MenuPanel = MenuPanel; }
    public void setPanel(MainApplication Frame, JLayeredPane Content, JPanel MenuPanel, JPanel OwnPanel){}
    
    public final void set3Icon(String path){
        ImageIcon ic = new ImageIcon(path);
        Image[] img = new Image[3];//mouse_enter, mouse_exit, mouse_press
        for(int i=0; i<img.length; i++) img[i] = Path.crop(ic.getImage(), 0,i*99, 304,99);
        setSelectedIcon(new ImageIcon(img[0]));
        setIcon(new ImageIcon(img[1]));
        setPressedIcon(new ImageIcon(img[2]));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(Entered){
            ContentPane.remove(MenuPanel);
            ContentPane.add(OwnPanel, JLayeredPane.DRAG_LAYER);
            Frame.repaint();
            setSelected(Entered = false);
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) { this.setSelected(Entered = true); }
    @Override
    public void mouseExited(MouseEvent e)  { this.setSelected(Entered = false); }
    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mousePressed(MouseEvent e) { }
}
