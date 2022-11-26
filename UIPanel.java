package Project3_220;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class UIPanel extends JPanel implements Path {
    //Data that can get from player
    private int HP ,CurrentHP;
    private int MP ,CurrentMP;
    private int Skill_CD;
    //private Player player;
    private JPanel HPBar,MPBar,CHPBar,CMPBar;
    
    //Panel Data
    private final int width;
    private final int height;
    private JPanel UIPanel;
    
    //Data From Others
    private MyFrame gameFrame;
    private JLayeredPane contentPane;
    private MainApplication MainFrame;
    private UpdateFrameThread UPS;
    
    //Pause Component
    private JPanel PausePanel;
    private MyButton Resume , Exit;
    private MyButton PauseButton;
    private SettingButton Setting;
    private boolean isPause = false;
    
    
    public UIPanel(/*Player Object Ex. Player player*/int Width,int Height,
            JLayeredPane ContentPane,MyFrame GameFrame,SettingButton Setting,
            MainApplication MainFrame,UpdateFrameThread UPS){
        //this.Player = player
        this.width = Width;
        this.height = Height;
        contentPane = ContentPane;
        gameFrame = GameFrame;
        this.MainFrame = MainFrame;
        this.Setting = Setting;
        this.UPS = UPS;
        
        /*
        HP = Player.getHP(); CurrentHP = Player.getHP(); 
        MP = Player.getMP(); CurrentMP = Player.getMP(); 
        */
        
        //HP and MP Debug
        HP = 100; CurrentHP = 100;
        MP = 100; CurrentMP = 100;
        
        UIPanel = new JPanel();
        setBounds(0,0,this.width,this.height);
        setLayout(null);
        setOpaque(false);
        setVisible(true);
        
        UIPanel = this;//move to line 45
        
        gameFrame.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_ESCAPE && !isPause){
                    System.out.println("ESC");
                    PauseComponent();
                    UIPanel.setVisible(false);//wanna change to using add/remove() of the ContentPane
                    isPause = true;
                }
            }
        });
        
        /*gameFrame.setFocusable(true);
        gameFrame.requestFocusInWindow();*/


        AddComponent();
        validate();
    }
    
    public void AddComponent(){
        JLabel HPText = new JLabel("HP");
        HPText.setBounds(20, -60, 200, 200);
        HPText.setFont(new Font("SansSerif",Font.BOLD,30));
        HPText.setForeground(Color.white);

        JLabel MPText = new JLabel("MP");
        MPText.setBounds(20, -18, 200, 200);
        MPText.setFont(new Font("SansSerif",Font.BOLD,30));
        MPText.setForeground(Color.white);

        HPBar = new JPanel();
        HPBar.setBackground(Color.white);
        HPBar.setBounds(75, 28, 250, 30);
        
        MPBar = new JPanel();
        MPBar.setBackground(Color.white);
        MPBar.setBounds(75, 68, 250, 30);
        
        CHPBar = new JPanel();
        CHPBar.setBackground(new Color((250*(HP-CurrentHP))/HP,50+(200*CurrentHP)/HP,25));
        CHPBar.setBounds(75, 28, (250*CurrentHP)/HP ,30);
        
        CMPBar = new JPanel();
        CMPBar.setBackground(new Color((70*CurrentMP)/MP+150,(70*CurrentMP)/MP+110,0));
        CMPBar.setBounds(75, 68, (250*CurrentMP)/MP ,30);

        PauseButton = new MyButton(){
//            boolean Entered = false;
            public void set3Icon(String path){
                ImageIcon ic = new ImageIcon(path);
                Image[] img = new Image[3];//mouse_enter, mouse_exit, mouse_press
                for(int i=0; i<img.length; i++) img[i] = Path.crop(ic.getImage(), 0,i*70, 70,70);
                setSelectedIcon(new ImageIcon(img[0]));
                setIcon(new ImageIcon(img[1]));
                setPressedIcon(new ImageIcon(img[2]));
                setBorderPainted(false);
                setContentAreaFilled(false);
            }
            public void mouseReleased(MouseEvent e) {
                if(Entered){
                    System.out.println("ESC");
                    PauseComponent();
                    UIPanel.setVisible(false);
                    isPause = true;
                }
            }
//            public void mouseEntered(MouseEvent e) { this.setSelected(Entered = true); }
//            public void mouseExited(MouseEvent e)  { this.setSelected(Entered = false); }
        };

        PauseButton.addMouseListener(PauseButton);
        PauseButton.setBounds(1170,28,70,70);
        PauseButton.set3Icon(BTPath+"PauseButton.png");

        UIPanel.add(PauseButton);
        UIPanel.add(CHPBar);
        UIPanel.add(CMPBar);
        UIPanel.add(HPText);
        UIPanel.add(MPText);
        UIPanel.add(HPBar);
        UIPanel.add(MPBar);

        contentPane.add(this,JLayeredPane.DRAG_LAYER);
    }
    
    public void UpdateStatus(){
        //CurrentHP = Player.getCurHP();
        //CurrentMP = Player.getCurMP();
        CHPBar.setBackground(new Color((250*(HP-CurrentHP))/HP,50+(200*CurrentHP)/HP,25));
        CHPBar.setBounds(75, 28, (250*CurrentHP)/HP ,30);
        CMPBar.setBackground(new Color((70*CurrentMP)/MP+150,(70*CurrentMP)/MP+110,0));
        CMPBar.setBounds(75, 68, (250*CurrentMP)/MP ,30);
        repaint();
    }
    
    public void PauseComponent(){
        PausePanel = new JPanel();
        PausePanel.setBounds(0,0,width,height);
        PausePanel.setLayout(null);
        PausePanel.setOpaque(false);
        Setting.setUIPanel(PausePanel);
        Setting.SettingConfig(true);
        
        JPanel BGLabel = new JPanel();
        BGLabel.setBounds(0,0,width,height);
        BGLabel.setBackground(new Color(0,0,0,70));
                
        Resume = new MyButton(){
            @Override
            public void mouseReleased(MouseEvent e) {
                if(Entered){
                    setSelected(Entered = false);
                    contentPane.remove(PausePanel);
                    UIPanel.setVisible(true);
                    isPause = false;
                    //GamethreadPause
                }
            }
        };
        
        Resume.set3Icon(BTPath+"ResumeButton.png");
        Resume.setBounds(488, 200, 304, 98);
        Resume.setBorderPainted(false);
        Resume.setContentAreaFilled(false);

        Exit = new MyButton(){
          @Override
          public void mouseReleased(MouseEvent e){
              if(Entered){
                    gameFrame.dispose();
                    MainFrame.setVisible(true);
                    UPS.setFrame(MainFrame);
                    MainFrame.refresh();
              }
          }
        };
        
        Exit.set3Icon(BTPath+"ExitButton.png");
        Exit.setBounds(488, 430, 304, 98);
        Exit.setBorderPainted(false);
        Exit.setContentAreaFilled(false);

        Resume.addMouseListener(Resume);
        Setting.addMouseListener(Setting);
        Exit.addMouseListener(Exit);
        
        PausePanel.add(Resume);
        PausePanel.add(Setting);
        PausePanel.add(Exit);
        PausePanel.add(BGLabel);

        contentPane.add(PausePanel, JLayeredPane.DRAG_LAYER);
    }
    
    public JPanel getPausePanel(){
        return PausePanel;
    }
    
}
