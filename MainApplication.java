/*
Ploypailin            6413106
Tanawat     Kanchan   6413215
Natedee     Mueankrut 6413220
*/
package Project3_220;

import javax.swing.*;

public class MainApplication extends JFrame{
    private int width, height;
    protected String BGPath = "src/main/java/Project3_220/Background/";
    protected String SSPath = "src/main/java/Project3_220/Sprite_sheet/";
    
    private MainApplication(int w, int h){
        /*
        A) create new panel named MenuPanel
        B) add conponent in the MenuPanel
            1 add the logo (thread)
            2 add 4 buttons: Play, Setting, Credits, Exit(this.dispose();)
            3 add backgrounds (MouseMoitonListener)
        C) add event listener: 4 buttons
        D) add MenuPanel into CententPane
        - setMaximizedSize
        - UpdateFrameThread focus on the frame
        */
    }

    public static void main(String[] args) {
        MainApplication main = new MainApplication(1280,800);
    }
    
}

        
        
/*
super("PNG");
        String[] s = {"small_clouds.png","big_clouds.png","playing_bg_img.png"};
        JLabel[] bg = new JLabel[3]; //3 layers of background
        for(int i=0; i<3; i++){
            bg[i] = new JLabel(); 
            bg[i].setIcon(new ImageIcon(BGPath+s[i]));
            bg[i].setBounds(0,0,w,h);//width = getSize().getWidth
        }
        
        JLabel content = new JLabel();
        setContentPane(content);
        
        setSize(w,h);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        setVisible(true);
        for(JLabel i: bg){
            content.add(i);
        }
*/
        /*JLabel img = new JLabel(); //4 layers of background
        img.setIcon(new ImageIcon(BGPath+"big_clouds.png"));
        add(img);*/