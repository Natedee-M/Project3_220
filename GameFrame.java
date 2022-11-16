package Project3_220;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;

public class GameFrame extends MyFrame{
    private static final Color c1 = new Color(19, 43, 48);
    private static final Color c2 = new Color(109, 139, 116);
    private static final Color c3 = new Color(239, 234, 216);
    
    //Background
    private JPanel   BgPanel;
    private JLabel[] Background;
    
    //Components
    private JPanel          PregamePanel;
    private JLabel           Text;
    private JTextField       nameTextField;
    private JPanel           Difficultypanel;
    private ButtonGroup      DifficultyGroup;
    private JToggleButton[]  DifficultyButton;
    private JComboBox        Wave;
    private MyButton         BackButton,PlayButton;
    
    //Data before start game
    private String UserName;
    private int DiffIndex = 0;
    private int wavelength = 5;
    
    public GameFrame(UpdateFrameThread UPS, int frameWidth, int frameHeight ,MainApplication Menuframe){
        super("Penguin Edgerunner Gameplay");
        this.UPS = UPS;
        width  = frameWidth;
        height = frameHeight;
        this.MainFrame = Menuframe;
        
        setSize(width,height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        gameFrame = this;
        
        ContentPane = new JLayeredPane();
        setContentPane(ContentPane);
        ContentPane.setLayout(null);
        ContentPane.setBounds(0, 0, width, height);
        
        AddComponents();
	setVisible(true);
    }
    
    public final void AddComponents(){
        
        Text = new JLabel("Enter Your Name");
        Text.setBounds(290, 110, 700, 90);
        Text.setHorizontalAlignment(JTextField.CENTER);
        Text.setVerticalAlignment(JTextField.CENTER);
        Text.setFont(new Font("SansSerif",Font.BOLD,70));
        Text.setForeground(c1);
        Text.setBackground(c3);
        Text.setOpaque(true);
        Text.setBorder(new LineBorder(c2,5));
        
        nameTextField = new JTextField();
        nameTextField.setBounds(340, 231, 600, 70);
        nameTextField.setHorizontalAlignment(JTextField.CENTER);
        nameTextField.setFont(new Font("SansSerif",Font.BOLD,50));
        nameTextField.setForeground(c1);
        nameTextField.setBackground(new Color(255, 249, 227));
        nameTextField.setBorder(new LineBorder(c1,5));
        nameTextField.setDisabledTextColor(Color.RED);
        
        
        String [] Difficulty = {"Medium","Hard","Expert","Insane", "Hell"};
        String [] Waveqty = {"5","8","10","12","15"};
        DifficultyButton = new JRadioButton[Difficulty.length];
        DifficultyGroup = new ButtonGroup();
        Difficultypanel = new JPanel();
        Wave = new JComboBox(Waveqty);
        
        Difficultypanel.setAlignmentY(CENTER_ALIGNMENT);
        Difficultypanel.setBounds(190, 332, 900, 130);
        Difficultypanel.setBackground(c3);
        Difficultypanel.setBorder(new LineBorder(c2,5));
        
        JLabel TextDiff = new JLabel("Difficulty : ");
        TextDiff.setFont(new Font("SansSerif",Font.BOLD,30));
        TextDiff.setForeground(c1);
        Difficultypanel.add(TextDiff);
        
        Wave.setFont(new Font("SansSerif",Font.BOLD,30));
        Wave.addItemListener((ItemEvent e) -> {
            switch(e.getItem().toString()){
                case "5"  -> wavelength = Integer.parseInt(Waveqty[0]);
                case "8"  -> wavelength = Integer.parseInt(Waveqty[1]);
                case "10" -> wavelength = Integer.parseInt(Waveqty[2]);
                case "12" -> wavelength = Integer.parseInt(Waveqty[3]);
                case "15" -> wavelength = Integer.parseInt(Waveqty[4]);
                default -> {
                }
            }
        });
        
        for(int i = 0 ; i < Difficulty.length ; i++){
            DifficultyButton[i] = new JRadioButton(Difficulty[i]);
            DifficultyButton[i].setFont(new Font("SansSerif",Font.BOLD,30));
            DifficultyGroup.add(DifficultyButton[i]);
            DifficultyButton[i].setOpaque(false);
            DifficultyButton[i].setForeground(c1);
            DifficultyButton[i].setBorderPainted(false);
            DifficultyButton[i].setFocusable(false);
            if(i==0){DifficultyButton[i].setSelected(true);}
            Difficultypanel.add(DifficultyButton[i]);
        }
        
        /*for(int i=0; i<Difficulty.length; i++) DifficultyButton[i].addItemListener((ItemEvent e) -> {DiffIndex = i;});*/
        DifficultyButton[0].addItemListener((ItemEvent e) -> {DiffIndex = 0;});
        DifficultyButton[1].addItemListener((ItemEvent e) -> {DiffIndex = 1;});
        DifficultyButton[2].addItemListener((ItemEvent e) -> {DiffIndex = 2;});
        DifficultyButton[3].addItemListener((ItemEvent e) -> {DiffIndex = 3;});
        DifficultyButton[4].addItemListener((ItemEvent e) -> {DiffIndex = 4;});
        
        JLabel TextLast = new JLabel("Last Wave : ");
        TextLast.setFont(new Font("SansSerif",Font.BOLD,30));
        TextLast.setForeground(c1);
        Difficultypanel.add(TextLast);
        
        Difficultypanel.add(Wave);
        
        
        BackButton = new MyButton(){
            @Override
            public void mouseReleased(MouseEvent e) {
                if(Entered){
                    UPS.setFrame(MainFrame);
                    gameFrame.dispose();
                    MainFrame.setVisible(true);
                }
            }
        };
        BackButton.set3Icon(BTPath+"BackButton.png");
        BackButton.setBounds(300, 493, 304, 98);
        BackButton.setBorderPainted(false);
        BackButton.setContentAreaFilled(false);
        BackButton.addMouseListener(BackButton);

        PlayButton = new MyButton(){
            @Override
            public void mouseReleased(MouseEvent e) {
                if(Entered&&!nameTextField.getText().equals("")){
                    if(nameTextField.getText().length()>10) UserName = nameTextField.getText().substring(0, 10);
                    else UserName = nameTextField.getText();
                    System.out.println("Player name = "+UserName);
                    System.out.println("Wave length = "+wavelength);
                    System.out.println("Difficulty  = "+ DiffIndex);
                    setSelected(Entered = false);
                    ContentPane.remove(PregamePanel);
                    ContentPane.repaint();
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) { if(!nameTextField.getText().equals("")){
                this.setSelected(Entered = true); this.setEnabled(true);}
                else{this.setEnabled(false);}
            }
            @Override
            public void mouseExited(MouseEvent e)  { if(!nameTextField.getText().equals(""))this.setSelected(Entered = false); }
        };
        
        PlayButton.set3Icon(BTPath+"PlayButton.png");
        PlayButton.setBounds(678, BackButton.getY(), 304, 98);
        PlayButton.setBorderPainted(false);
        PlayButton.setContentAreaFilled(false);
        PlayButton.setEnabled(false);
        PlayButton.setDisabledIcon(new ImageIcon(BTPath+"PlayButtonDisible.png"));
        PlayButton.addMouseListener(PlayButton);
        
        nameTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {changed();}
            @Override
            public void removeUpdate(DocumentEvent e)  {changed();}
            @Override
            public void insertUpdate(DocumentEvent e)  {changed();}
            public void changed() {
               if (nameTextField.getText().equals("")){PlayButton.setEnabled(false);}
               else {PlayButton.setEnabled(true);}

            }
          });
        
        Background = new JLabel[4];
        for(int i = 0 ; i < Background.length ; i++){
            Background[i] = new JLabel();
            Background[i].setIcon(new ImageIcon(BGPath+"GameBG"+Integer.toString(i+1)+".png"));
            Background[i].setBounds(0,0,1280,720);
        }
        
        BgPanel = new JPanel();
        PregamePanel = new JPanel();
        
        BgPanel.setBounds(0, 0, width, height);
        BgPanel.setLayout(null);
        PregamePanel.setBounds(0, 0, width, height);
        PregamePanel.setLayout(null);
        PregamePanel.setOpaque(false);
        
        PregamePanel.add(Text,JLayeredPane.DEFAULT_LAYER);
        PregamePanel.add(nameTextField,JLayeredPane.DEFAULT_LAYER);
        PregamePanel.add(Difficultypanel,JLayeredPane.DEFAULT_LAYER);
        PregamePanel.add(BackButton,JLayeredPane.DEFAULT_LAYER);
        PregamePanel.add(PlayButton,JLayeredPane.DEFAULT_LAYER);
        for(JLabel i : Background) BgPanel.add(i);
        
        ContentPane.add(PregamePanel);
        ContentPane.add(BgPanel);
        validate();
    }
    
    @Override
    public void Update(int num){}
}