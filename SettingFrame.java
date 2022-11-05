package Project3_220;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class SettingFrame extends MyButton{
    public SettingFrame(JFrame frame, JPanel panel){
        this.frame = frame;
        this.MenuPanel = panel;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        /*
        A) Remove the MenuPanel//frame.remove(MenuPanel);
        B) create new panel
        C) add conponent in the panel(B)
            1 add the 'Setting' logo
            2 add 2 JLabel: SFX, BGM
            3 add 2 slider
            4 add 3 buttons: on/off sound, Back
            5 add backgrounds (thread)
        B) add event listener: 3 buttons, 2 sliders
        - re-add the MenuPanel
        */
        /*
        MyButton SFXbutton = new MyButton(){
            @Override
            public void mouseReleased(MouseEvent e) {
                ...
            }
        });
        add(SFXbutton);
        */
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