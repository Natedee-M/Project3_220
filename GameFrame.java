package Project3_220;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;

public class GameFrame extends MyFrame{
    
    private GameFrame CurrentFrame;
    private MainApplication MenuFrame;
    private JPanel GamePanel;
    private JLayeredPane ContentPane;
    
    private JLabel[]     Background;
    private UpdateFrameThread UPS;
    
    //Enter Your name Component
    private JLabel Text;
    private JTextField nameTextField;
    private JToggleButton [] DifficultyButton;
    private JComboBox Wave;
    private ButtonGroup DifficultyGroup;
    private JPanel Difficultypanel;
    private MyButton BackButton,PlayButton;
    
    public GameFrame(UpdateFrameThread UPS, int frameWidth, int frameHeight ,MainApplication Menuframe){
        super("Penguin Edgerunner Gameplay");
        this.UPS = UPS;
        width  = frameWidth;
        height = frameHeight;
        this.MenuFrame = Menuframe;
        
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
        
        Font font1 = new Font("SansSerif",Font.BOLD,50);
        Text = new JLabel("Enter Your Name");
        Text.setFont(new Font("SansSerif",Font.BOLD,70));
        Text.setHorizontalAlignment(JTextField.CENTER);
        Text.setBounds(0, -150, 1280, 720);
        
        nameTextField = new JTextField();
        nameTextField.setBounds(340, 300, 600, 70);
        nameTextField.setFont(font1);
        nameTextField.setHorizontalAlignment(JTextField.CENTER);
        nameTextField.setBackground(new Color(19, 43, 48));
        nameTextField.setBorder(new LineBorder(new Color(0,0,0),5));
        nameTextField.setForeground(Color.white);
        nameTextField.setDisabledTextColor(Color.black);
        
        String [] Difficulty = {"Medium","Hard","Expert","Insane", "Hell"};
        DifficultyButton = new JRadioButton[Difficulty.length];
        DifficultyGroup = new ButtonGroup();
        Difficultypanel = new JPanel();
        Difficultypanel.setAlignmentY(CENTER_ALIGNMENT);
        Difficultypanel.setBounds(340, 400, 600, 45);
        Difficultypanel.setBackground(new Color(19, 43, 48));
        Difficultypanel.setBorder(new LineBorder(new Color(0,0,0),5));
        Difficultypanel.add(new JLabel("Difficulty : ")).setForeground(Color.white);
        
        String [] Waveqty = {"1100","1200","1300","1400","1500"};
        Wave = new JComboBox(Waveqty);
        
        for(int i = 0 ; i < Difficulty.length ; i++){
            DifficultyButton[i] = new JRadioButton(Difficulty[i]);
            DifficultyGroup.add(DifficultyButton[i]);
            DifficultyButton[i].setBackground(new Color(19, 43, 48));
            DifficultyButton[i].setForeground(Color.white);
            if(i==0)DifficultyButton[i].setSelected(true);
            Difficultypanel.add(DifficultyButton[i]);
        }
        Difficultypanel.add(new JLabel("     Last Wave : ")).setForeground(Color.white);
        Difficultypanel.add(Wave);
        
        BackButton = new MyButton(){
            @Override
            public void mouseReleased(MouseEvent e) {
                if(Entered){
                    setSelected(Entered = false);
                    CurrentFrame.dispose();
                    MenuFrame.setVisible(true);
                }
            }
        };
        
        BackButton.set3Icon(BTPath+"BackButton.png");
        BackButton.setBounds(300, 472, 304, 98);
        BackButton.setBorderPainted(false);
        BackButton.setContentAreaFilled(false);
        BackButton.addMouseListener(BackButton);
        
        PlayButton = new MyButton(){
            @Override
            public void mouseReleased(MouseEvent e) {
                if(Entered&&!nameTextField.getText().equals("")){
                    setSelected(Entered = false);
                    CurrentFrame.dispose();
                    MenuFrame.setVisible(true);
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
        PlayButton.setBounds(678, 472, 304, 98);
        PlayButton.setBorderPainted(false);
        PlayButton.setContentAreaFilled(false);
        PlayButton.setEnabled(false);
        PlayButton.setDisabledIcon(new ImageIcon(BTPath+"PlayButtonDisible.png"));
        PlayButton.addMouseListener(PlayButton);
        
        nameTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
              changed();
            }
            public void removeUpdate(DocumentEvent e) {
              changed();
            }
            public void insertUpdate(DocumentEvent e) {
              changed();
            }
            public void changed() {
               if (nameTextField.getText().equals("")){
                 PlayButton.setEnabled(false);
               }
               else {
                 PlayButton.setEnabled(true);
              }

            }
          });
        
        
        Background = new JLabel[7];
        Image img;
        for(int i = 0 ; i < Background.length ; i++){
            Background[i] = new JLabel();
            img = new ImageIcon(BGPath+"MenuBG"+Integer.toString(i+1)+".png").getImage().getScaledInstance(1344, 756, Image.SCALE_SMOOTH);
            Background[i].setIcon(new ImageIcon(img));
            Background[i].setBounds(-32, -18, 1344, 756);
        }
        
        ContentPane.add(Text,JLayeredPane.DEFAULT_LAYER);
        ContentPane.add(nameTextField,JLayeredPane.DEFAULT_LAYER);
        ContentPane.add(Difficultypanel,JLayeredPane.DEFAULT_LAYER);
        ContentPane.add(BackButton,JLayeredPane.DEFAULT_LAYER);
        ContentPane.add(PlayButton,JLayeredPane.DEFAULT_LAYER);
        for(JLabel i : Background) ContentPane.add(i, JLayeredPane.DEFAULT_LAYER);
        validate();
    }
    
    @Override
    public void Update(int num){};
}

/*
        A) new JFrame
        B) add component in new panel
            1 add JLabel: "Enter Your Name"
            2 add TextField
            3 add 2 buttons: Next, Back
        C) add background
        D.1) Back: close this JFrame => End
        D.2) Next: Save the Name, then remove the panel
        E) add new Panel
        F) ...
        
        -need threads for each enemy and player's character
        */
        
