package Project3_220;

import java.awt.Image;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class SettingButton extends MyButton{
    public SettingButton(MainApplication Frame, JLayeredPane Content, JPanel MenuPanel){
        /*
        didnt do Audio things
        */
        this.Frame = Frame;
        this.ContentPane = Content;
        this.MenuPanel = MenuPanel;
        set3Icon(BTPath + "SettingButton.png");
        OwnPanel = new JPanel();
        
        String[] path = {"SettingText.PNG", "SFXText.PNG", "BGMText.PNG"};//(213, 472, 304, 98)
        int[][] bound = {{213,0,304,115},{0,177,220,85},{0,292,220,85}};//set bounds correctly
        ImageIcon[] icon = Path.importImg(ImgPath, path);
        JLabel[] text = new JLabel[path.length];
        for(int i=0; i<path.length; i++){
            text[i] = new JLabel();
            text[i].setIcon(icon[i]);
            text[i].setBounds(bound[i][0], bound[i][1], bound[i][2], bound[i][3]);
        }
        
        JSlider SFX = new JSlider(SwingConstants.HORIZONTAL);
        SFX.setBounds(235, 214, 350, 10);
        SFX.setOpaque(false);
        JSlider BGM = new JSlider(SwingConstants.HORIZONTAL);
        BGM.setBounds(235, 329, 350, 10);
        BGM.setOpaque(false);
        //SFXSlider.addChangeListener(new ChangeListener());//SFXSlider.getValue()
        
        
        class NoteButton extends MyButton{
            private final ImageIcon[] icon = new ImageIcon[4];//Off-On
            public NoteButton(){
                //icon = new ImageIcon[4];
                ImageIcon pic = new ImageIcon(Path.BTPath+"NoteSwitch.png");
                Image img;
                for(int i=0; i<2; i++){
                    for(int j=0; j<2; j++){
                        img = Path.crop(pic.getImage(), i*103,j*102, 103,102);
                        icon[i*2+j] = new ImageIcon(img);
                    }
                }
                for(ImageIcon i: icon) if(i==null) System.out.println("y");
                setIcon(icon[2]);
                setSelectedIcon(icon[3]);
                //make sound on
                
                setBorderPainted(false);
                setContentAreaFilled(false);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if(Entered){
                    if(getIcon()==icon[0]){
                        setIcon(icon[2]);
                        setSelectedIcon(icon[3]);//sound on
                    }
                    else{
                        setIcon(icon[0]);
                        setSelectedIcon(icon[1]);//sound off
                    }
                }
            }
        }
        MyButton SFXButton = new NoteButton();
        SFXButton.setBounds(600, 169, 103, 102);
        SFXButton.addMouseListener(SFXButton);
        
        MyButton BGMButton = new NoteButton();
        BGMButton.setBounds(600, 284, 103, 102);
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
        OwnPanel.setBounds(275, 57, 703, Frame.getHeight()-57);
        OwnPanel.setOpaque(false);
        
        for(int i=0; i<path.length; i++) OwnPanel.add(text[i]);
        OwnPanel.add(SFXButton);
        OwnPanel.add(BGMButton);
        OwnPanel.add(SFX);
        OwnPanel.add(BGM);
        OwnPanel.add(BackButton);
    }
}