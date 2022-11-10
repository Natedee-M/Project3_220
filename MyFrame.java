package Project3_220;

import java.awt.Image;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import javax.swing.JFrame;

public abstract class MyFrame extends JFrame{
    protected int width, height;
    protected static final String BGPath = "src/main/java/Project3_220/Background/";
    protected static final String SSPath = "src/main/java/Project3_220/Spritesheet/";
    protected static final String BTPath = "src/main/java/Project3_220/Button/";
    protected static final String ImgPath = "src/main/java/Project3_220/Image/";
    
    public MyFrame(){}
    public MyFrame(String t){ super(t); }
    public static Image crop(Image img, int x, int y, int width,int height){
        JFrame frame = new JFrame();
        return frame.createImage(new FilteredImageSource(img.getSource(), new CropImageFilter(x,y,width,height)));
    }
}
