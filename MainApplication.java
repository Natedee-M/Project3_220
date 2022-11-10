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
    
    private MainApplication   CurrentFrame;
    private UpdateFrameThread UPS;
    private JLayeredPane      ContentPane;
    private JPanel            MenuPanel;
    
    private MyButton     Play;
    private SettingButton Setting;
    private CreditButton Credit;
    private MyButton     Exit;
    private JLabel[]     Background;
    
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
                if(Entered){ //new GameFrame;
                    //setSelected(Entered = false);
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
        /*addMouseMotionListener(new MouseMotionAdapter(){
            private boolean enter = false;
            private MouseEvent e;
            private void update(){
                
            }
            private Point cal(int j){
                float numx = 8+j*4;
                int currentx = (int) Math.ceil((double) e.getX()/Math.round(width/numx));
            }
            private void Hold(){
                for(int i =0,j=6; i<Background.length-1; i++,j--) Background[i].setLocation(cal(j));
            }
            private void Release(){
                
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                this.e = e;
                if(e.getX()>=0&&e.getX()<=width && e.getY()>=0&&e.getY()<=height) enter = true;
                else enter = false;
                
                /*{//Background[i].setLocation(p);
                    for(int i =0,j=6; i<Background.length-1; i++,j--) Hold();
                }
                else{
                    for(int i =0,j=6; i<Background.length-1; i++,j--) Release();
                }
            }
        });*/
        
        
        MenuPanel.setBounds(0, 0, width, height);
        MenuPanel.setLayout( new FlowLayout(FlowLayout.CENTER,width,30) );
        MenuPanel.setOpaque(false);
        
        MenuPanel.add(Play);
        MenuPanel.add(Setting);
        MenuPanel.add(Credit);
        MenuPanel.add(Exit);
        ContentPane.add(MenuPanel, JLayeredPane.DRAG_LAYER);
        for(JLabel i : Background) ContentPane.add(i, JLayeredPane.DEFAULT_LAYER);
        /*
        A) create new panel named MenuPanel
        B) add conpomnents in the MenuPanel
            1 add the logo (thread)
            2 add 4 buttons: Play, Setting, Credits, Exit(this.dispose();)
            3 add backgrounds (MouseMoitonListener)
        C) add event listener: 4 buttons
        D) add MenuPanel into CententPane
        - setMaximizedSize
        - UpdateFrameThread focus on the frame
        - this.setLayout(null);
        */
    }
    public int getwidth(){ return width; }
    public int getheight(){ return height; }

    public static void main(String[] args) {
        UpdateFrameThread UPS = new UpdateFrameThread();
        MainApplication main = new MainApplication(UPS,1280,720);
        UPS.setFrame(main);
        //UPS.start();
    }
    
}

/*class playLabel extends MyButton{
    protected JFrame frame;
    protected JPanel MenuPanel;
    
    private JLabel MenuPanel;
    private String filepath;
    private MyImageIcon[] img;
    
    playLabel(JFrame frame , JLabel MenuPanel , String path){
        this.frame = frame;
        this.filepath = path;
        this.MenuPanel = MenuPanel;
        
        img = new MyImageIcon[3];
        
        super.setIcon(filepath+"PlayButton.png");
        super.setBorderPainted(false);
        super.setOpaque(false);
        super.setContentAreaFilled(false);
        super.setBorderPainted(false);
        super.setFocusable(false);
        setBounds(50, 200, 304,98);
        setVisible(true);
        this.MenuPanel.add(this);
        this.setBackground(new Color(0,0,0,0));
        this.setBorderPainted(false);
        addMouseListener(this);
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) { 
    }
    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mousePressed(MouseEvent e) { 
    }
    @Override
    public void mouseEntered(MouseEvent e) { 
        this.setSelected(true);
    }
    @Override
    public void mouseExited(MouseEvent e) {
        this.setSelected(false);
    }
}

class settingLabel extends MyButton{
    protected JFrame frame;
    protected JPanel MenuPanel;
    
    private JLabel MenuPanel;
    private String filepath;
    private MyImageIcon[] img;
    
    settingLabel(JFrame frame , JLabel MenuPanel , String path){
        this.frame = frame;
        this.filepath = path;
        this.MenuPanel = MenuPanel;
        
        img = new MyImageIcon[3];
        
        super.setIcon(filepath+"SettingButton.png");
        super.setBorderPainted(false);
        super.setOpaque(false);
        super.setContentAreaFilled(false);
        super.setBorderPainted(false);
        super.setFocusable(false);
        setBounds(50, 300, 304,98);
        setVisible(true);
        this.MenuPanel.add(this);
        this.setBackground(new Color(0,0,0,0));
        this.setBorderPainted(false);
        addMouseListener(this);
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) { 
    }
    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mousePressed(MouseEvent e) { 
    }
    @Override
    public void mouseEntered(MouseEvent e) { 
        this.setSelected(true);
    }
    @Override
    public void mouseExited(MouseEvent e) {
        this.setSelected(false);
    }
}

class creditLabel extends MyButton{
    protected JFrame frame;
    protected JPanel MenuPanel;
    
    private JLabel MenuPanel;
    private String filepath;
    private MyImageIcon[] img;
    
    creditLabel(JFrame frame , JLabel MenuPanel , String path){
        this.frame = frame;
        this.filepath = path;
        this.MenuPanel = MenuPanel;
        
        img = new MyImageIcon[3];
        
        super.setIcon(filepath+"CreditButton.png");
        super.setBorderPainted(false);
        super.setOpaque(false);
        super.setContentAreaFilled(false);
        super.setBorderPainted(false);
        super.setFocusable(false);
        setBounds(50, 400, 304,98);
        setVisible(true);
        this.MenuPanel.add(this);
        this.setBackground(new Color(0,0,0,0));
        this.setBorderPainted(false);
        addMouseListener(this);
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) { 
    }
    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mousePressed(MouseEvent e) { 
    }
    @Override
    public void mouseEntered(MouseEvent e) { 
        this.setSelected(true);
    }
    @Override
    public void mouseExited(MouseEvent e) {
        this.setSelected(false);
    }
}

class MyImageIcon extends ImageIcon
{
    public MyImageIcon(String fname)  { super(fname); }
    public MyImageIcon(Image image)   { super(image); }

    public MyImageIcon resize(int width, int height)
    {
	Image oldimg = this.getImage();
	Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT);
	return new MyImageIcon(newimg);
    }
};*/