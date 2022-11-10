package Project3_220;

import static Project3_220.MyButton.crop;
import java.awt.Image;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class SettingButton extends MyButton{
    public SettingButton(MainApplication Frame, JLayeredPane Content, JPanel MenuPanel){
        this.Frame = Frame;
        this.ContentPane = Content;
        this.MenuPanel = MenuPanel;
        set3Icon(BTPath + "SettingButton.png");
        OwnPanel = new JPanel();
        
        //Texts&Pics
        String[] path = {"SettingText.png", "SFXText.png", "BGMText.png", "Board.png"};
        int[][] bound = {{170,0,450,100},{0,130,220,85},{0,230,220,85},{0,300,500,500}};//set bounds correctly
        ImageIcon[] icon = importImg(ImgPath, path);
        JLabel[] text = new JLabel[path.length];
        for(int i=0; i<path.length; i++){
            text[i] = new JLabel();
            text[i].setIcon(icon[i]);
            text[i].setBounds(bound[i][0], bound[i][1], bound[i][2], bound[i][3]);
        }
        
        //Sliders
        JSlider SFX = new JSlider(SwingConstants.HORIZONTAL);//setlocation
        //SFX.setBounds(TOP, TOP, WIDTH, HEIGHT);
        JSlider BGM = new JSlider(SwingConstants.HORIZONTAL);
        //SFXSlider.addChangeListener(new ChangeListener());//SFXSlider.getValue()
        
        
        //Buttons
        class NoteButton extends MyButton{
            private final ImageIcon[] icon = new ImageIcon[2];//Off-On
            public NoteButton(){
                ImageIcon pic = new ImageIcon(BTPath+"NoteSwitch.png");
                Image img;
                for(int i=0; i<2; i++){
                    img = crop(pic.getImage(), i*105,0, 105,104);//correct the parameters
                    icon[i] = new ImageIcon(img);
                }
                setIcon(icon[1]);
                //make sound on
                
                setBorderPainted(false);
                setContentAreaFilled(false);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if(Entered){
                    if(getIcon()==icon[0]) setIcon(icon[1]);//sound on
                    else setIcon(icon[0]);//sound off
                }
            }
        }
        MyButton SFXButton = new NoteButton();
        SFXButton.setBounds(670, 130, 105, 104);
        SFXButton.addMouseListener(SFXButton);
        
        MyButton BGMButton = new NoteButton();
        BGMButton.setBounds(670, 230, 105, 104);
        BGMButton.addMouseListener(BGMButton);
        
        MyButton BackButton = new MyButton(){
            @Override
            public void setPanel(MainApplication Frame, JLayeredPane Content, JPanel MenuPanel, JPanel OwnPanel){
                this.Frame = Frame;
                this.ContentPane = Content;
                this.MenuPanel = MenuPanel;
                this.OwnPanel = OwnPanel;
                set3Icon(BTPath+"BackButton.png");
                setBounds(213, 472, 304, 98);
                setBorderPainted(false);
                setContentAreaFilled(false);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if(Entered){
                    setSelected(Entered = false);
                    ContentPane.remove(OwnPanel);
                    ContentPane.add(MenuPanel, JLayeredPane.DRAG_LAYER);
                    Frame.repaint();
                }
            }
        };
        BackButton.setPanel(Frame, Content, MenuPanel, OwnPanel);
        BackButton.addMouseListener(BackButton);
        
        OwnPanel.setLayout(null);
        OwnPanel.setBounds(275, 57, 1005, Frame.getheight()-57);
        OwnPanel.setOpaque(false);
        
        for(int i=0; i<path.length-1; i++) OwnPanel.add(text[i]);//Board is the last component
        OwnPanel.add(SFXButton);
        OwnPanel.add(BGMButton);//add 2 sliders
        OwnPanel.add(BackButton);
        //OwnPanel.add(text[path.length-1]);//Board is the last component
        
        /*
        A) Remove the MenuPanel(/)
        B) add conponent in OwnPanel
            1 add the 'Setting' logo(/)
            2 add 3 JLabel: SFX, BGM, Board(/)
            3 add 2 slider
            4 add 3 buttons: on/off sound, Back(/)
            5 add backgrounds (thread)
        C) add event listener: 3 buttons(_,_,/), 2 sliders
        - re-add the MenuPanel(/)
        */
    }
}