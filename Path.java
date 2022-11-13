package Project3_220;

import java.awt.Image;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public interface Path{
    static final String BGPath = "src/main/java/Project3_220/Background/";
    static final String SSPath = "src/main/java/Project3_220/Spritesheet/";
    static final String BTPath = "src/main/java/Project3_220/Button/";
    static final String ImgPath = "src/main/java/Project3_220/Image/";
    
    public static ImageIcon[] importImg(String prepath, String path[]){
        ImageIcon[] img = new ImageIcon[path.length];
        for(int i=0; i<path.length; i++) img[i] = new ImageIcon(prepath+path[i]);
        return img;
    }
    public static Image crop(Image img, int x, int y, int width,int height){
        JFrame frame = new JFrame();
        return frame.createImage(new FilteredImageSource(img.getSource(), new CropImageFilter(x,y,width,height)));
    }
}
