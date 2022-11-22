package Project3_220;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
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
    public static Image flipH(BufferedImage image){
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(image, null);
    }
    public static BufferedImage resizeBuffer(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
