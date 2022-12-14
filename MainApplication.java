/*
Ploypailin            6413106
Tanawat     Kanchan   6413215
Natedee     Mueankrut 6413220
*/
package Project3_220;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Math;

public final class MainApplication extends MyFrame{
    private JPanel            MenuPanel;
    
    //private boolean Entered = false;
    
    private Animation     Logo;
    private MyButton      Play;
    private GameFrame      Game;
    private SettingButton Setting;
    private CreditButton  Credit;
    private MyButton      Exit;
    private JLabel[]      Background;
    
    private MainApplication(UpdateFrameThread UPS, int frameWidth, int frameHeight){
        super("Penguin Edgerunner");
        this.UPS = UPS;
        width  = frameWidth;
        height = frameHeight;
        
        setSize(width,height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        CurrentFrame = this;
        
        ContentPane = new JLayeredPane();
        setContentPane(ContentPane);
        ContentPane.setLayout(null);
        
        AddComponents();
        
        ContentPane.setBounds(0, 0, width, height);
	setVisible(true);
    }
    
    public void AddComponents(){
        MenuPanel = new JPanel();
        
        Play = new MyButton(){
            @Override
            public void mouseReleased(MouseEvent e) {
                if(Entered){
                    /*Game = new GameFrame();
                    setSelected(Entered = false);
                    CurrentFrame.setVisible(false);
                    UPS.setFrame(Game);*/
                }
            }
        };
        Play.set3Icon(BTPath+"PlayButton.png");
        Setting = new SettingButton(CurrentFrame, ContentPane, MenuPanel);
        Credit = new CreditButton(CurrentFrame, ContentPane, MenuPanel);
        Exit = new MyButton(){
            @Override
            public void mouseReleased(MouseEvent e) {
                if(Entered){
                    UPS.setGameOn(false);
                    CurrentFrame.dispose();
                    System.exit(0);
                }
            }
        };
        Exit.set3Icon(BTPath+"ExitButton.png");
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                UPS.setGameOn(false);
            }
        });
        
        JButton[] MenuButton = {Play,Setting,Credit,Exit};
        for(JButton b: MenuButton){
            b.setPreferredSize(new Dimension(304,98));
            b.setBorderPainted(false);
            b.setContentAreaFilled(false);
            b.addMouseListener((MouseListener) b);
        }
        
        
        Background = new JLabel[7];
        Image img;
        for(int i = 0 ; i < Background.length ; i++){
            Background[i] = new JLabel();
            img = new ImageIcon(BGPath+"MenuBG"+Integer.toString(i+1)+".png").getImage().getScaledInstance(1344, 756, Image.SCALE_SMOOTH);
            Background[i].setIcon(new ImageIcon(img));
            Background[i].setBounds(-32, -18, 1344, 756);
        }
        addMouseMotionListener(new MouseMotionAdapter(){
            private MouseEvent e;
            private boolean reachTarget;
            private Point cal(Point current, int j){
                double numx = 8+j*4;
                double currentx = current.x+32;
                double targetX =  Math.ceil(e.getX()/Math.round(width/numx)) - (numx/2);
                if(targetX>0) targetX--;
                
                double numy = 6+j*2;
                double currenty = current.y+18;
                double targetY =  Math.ceil(e.getY()/Math.round(height/numy)) - (numy/2);
                if(targetY>0) targetY--;
                
                reachTarget = Math.signum(targetX-currentx)==0 && Math.signum(targetY-currenty)==0;
                return new Point((int) (current.x+Math.signum(targetX-currentx)), (int) (current.y+Math.signum(targetY-currenty)));
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                this.e = e;
                do{
                    for(int i =0,j=6; i<Background.length-1; i++,j--)  Background[i].setLocation(cal(Background[i].getLocation(),j));
                }while(!reachTarget);
            }
        });
        
        Logo = new Animation(19, 600,157);
        Logo.setSpriteSheet(Path.SSPath+"Penguin_Edgerunner.png", 5,4, 1978,519, Image.SCALE_FAST);
        
        
        MenuPanel.setBounds(15, 0, width, height);
        MenuPanel.setLayout( new FlowLayout(FlowLayout.CENTER,width,20) );
        MenuPanel.setOpaque(false);
        
        MenuPanel.add(Logo);
        MenuPanel.add(Play);
        MenuPanel.add(Setting);
        MenuPanel.add(Credit);
        MenuPanel.add(Exit);
        ContentPane.add(MenuPanel, JLayeredPane.DRAG_LAYER);
        for(JLabel i : Background) ContentPane.add(i, JLayeredPane.DEFAULT_LAYER);
    }
    
    @Override
    public void Update(int num) { if(num%20==0) Logo.update(); }
    public int getwidth()       { return width; }
    public int getheight()      { return height; }

    public static void main(String[] args) {
        UpdateFrameThread UPS = new UpdateFrameThread();
        MainApplication main = new MainApplication(UPS,1280,720);
        UPS.setFrame(main);
        UPS.start();
    }
}