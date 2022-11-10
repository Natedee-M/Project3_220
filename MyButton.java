package Project3_220;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import javax.swing.*;


public abstract class MyButton extends JButton implements MouseListener{
    protected static final String BGPath = "src/main/java/Project3_220/Background/";
    protected static final String SSPath = "src/main/java/Project3_220/Spritesheet/";
    protected static final String BTPath = "src/main/java/Project3_220/Button/";
    protected static final String ImgPath = "src/main/java/Project3_220/Image/";
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
        for(int i=0; i<img.length; i++) img[i] = crop(ic.getImage(), 0,i*99, 304,99);
        setSelectedIcon(new ImageIcon(img[0]));
        setIcon(new ImageIcon(img[1]));
        setPressedIcon(new ImageIcon(img[2]));
    }
    
    public static ImageIcon[] importImg(String prepath, String path[]){
        ImageIcon[] img = new ImageIcon[path.length];
        for(int i=0; i<path.length; i++) img[i] = new ImageIcon(prepath+path[i]);
        return img;
    }
    public static Image crop(Image img, int x, int y, int width,int height){
        JFrame frame = new JFrame();
        return frame.createImage(new FilteredImageSource(img.getSource(), new CropImageFilter(x,y,width,height)));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(Entered){
            ContentPane.remove(MenuPanel);
                    System.out.println("rm");
            ContentPane.add(OwnPanel, JLayeredPane.DRAG_LAYER);
                    System.out.println("add");
            Frame.repaint();
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
