package Project3_220;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;

public class GameFrame extends MyFrame{
    
    private JPanel PregamePanel;
    private JPanel BgPanel;
    private JLayeredPane contentPane;
    
    private JLabel[]     Background;
    
    //Enter Your name Component
    private JLabel Text;
    private JTextField nameTextField;
    private JToggleButton [] DifficultyButton;
    private JComboBox Wave;
    private ButtonGroup DifficultyGroup;
    private JPanel Difficultypanel;
    private MyButton BackButton,PlayButton;
    
    //Data before start game
    private String UserName;
    private int DiffIndex;
    private int wavelength = 1100;
    
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
        
        contentPane = new JLayeredPane();
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBounds(0, 0, width, height);
        
        AddComponents();
	setVisible(true);
    }
    
    public void AddComponents(){
        
        Font font1 = new Font("SansSerif",Font.BOLD,50);
        Text = new JLabel("Enter Your Name");
        Text.setFont(new Font("SansSerif",Font.BOLD,70));
        Text.setHorizontalAlignment(JTextField.CENTER);
        Text.setBounds(0, -180, 1280, 720);
        Text.setForeground(Color.white);
        
        JPanel TextPanel = new JPanel();
        TextPanel.setBounds(290, 137, 700, 90);
        TextPanel.setBackground(new Color(19, 43, 48));
        TextPanel.setBorder(new LineBorder(new Color(0,0,0),5));
        
        nameTextField = new JTextField();
        nameTextField.setBounds(340, 243, 600, 70);
        nameTextField.setFont(font1);
        nameTextField.setHorizontalAlignment(JTextField.CENTER);
        nameTextField.setBackground(new Color(73, 128, 104));
        nameTextField.setBorder(new LineBorder(new Color(0,0,0),5));
        nameTextField.setForeground(Color.white);
        nameTextField.setDisabledTextColor(Color.black);
        
        String [] Difficulty = {"Medium","Hard","Expert","Insane", "Hell"};
        DifficultyButton = new JRadioButton[Difficulty.length];
        DifficultyGroup = new ButtonGroup();
        Difficultypanel = new JPanel();
        Difficultypanel.setAlignmentY(CENTER_ALIGNMENT);
        Difficultypanel.setBounds(190, 330, 900, 130);
        Difficultypanel.setBackground(new Color(19, 43, 48));
        Difficultypanel.setBorder(new LineBorder(new Color(0,0,0),5));
        JLabel TextDiff = new JLabel("Difficulty : ");
        TextDiff.setFont(new Font("SansSerif",Font.BOLD,30));
        TextDiff.setForeground(Color.white);
        Difficultypanel.add(TextDiff);
        
        String [] Waveqty = {"5","8","10","12","15"};
        Wave = new JComboBox(Waveqty);
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
            DifficultyButton[i].setBackground(new Color(19, 43, 48));
            DifficultyButton[i].setForeground(Color.white);
            if(i==0){DifficultyButton[i].setSelected(true);}
            Difficultypanel.add(DifficultyButton[i]);
        }
        
        DifficultyButton[0].addItemListener((ItemEvent e) -> {DiffIndex = 0;});
        DifficultyButton[1].addItemListener((ItemEvent e) -> {DiffIndex = 1;});
        DifficultyButton[2].addItemListener((ItemEvent e) -> {DiffIndex = 2;});
        DifficultyButton[3].addItemListener((ItemEvent e) -> {DiffIndex = 3;});
        DifficultyButton[4].addItemListener((ItemEvent e) -> {DiffIndex = 4;});
        
        JLabel TextLast = new JLabel("Last Wave : ");
        TextLast.setFont(new Font("SansSerif",Font.BOLD,30));
        TextLast.setForeground(Color.white);
        Difficultypanel.add(TextLast);
        
        Difficultypanel.add(Wave);
        
        
        BackButton = new MyButton(){
            @Override
            public void mouseReleased(MouseEvent e) {
                if(Entered){
                    setSelected(Entered = false);
                    gameFrame.dispose();
                    MainFrame.setVisible(true);
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
                    if(nameTextField.getText().length()>10) UserName = nameTextField.getText().substring(0, 10);
                    else UserName = nameTextField.getText();
                    System.out.println("Player name = "+UserName);
                    System.out.println("Wave length = "+wavelength);
                    System.out.println("Difficulty  = "+ DiffIndex);
                    setSelected(Entered = false);
                    contentPane.remove(PregamePanel);
                    contentPane.repaint();
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
        Image img;
        for(int i = 0 ; i < Background.length ; i++){
            Background[i] = new JLabel();
            img = new ImageIcon(BGPath+Integer.toString(i+1)+".png").getImage().getScaledInstance(1344, 756, Image.SCALE_SMOOTH);
            Background[i].setIcon(new ImageIcon(img));
            Background[i].setBounds(-32, -18, 1344, 756);
        }
        
        BgPanel = new JPanel();
        PregamePanel = new JPanel();
        
        BgPanel.setBounds(0, 0, width, height);
        BgPanel.setLayout(null);
        PregamePanel.setBounds(0, 0, width, height);
        PregamePanel.setLayout(null);
        PregamePanel.setOpaque(false);
        
        PregamePanel.add(Text,JLayeredPane.DEFAULT_LAYER);
        PregamePanel.add(TextPanel,JLayeredPane.DEFAULT_LAYER);
        PregamePanel.add(nameTextField,JLayeredPane.DEFAULT_LAYER);
        PregamePanel.add(Difficultypanel,JLayeredPane.DEFAULT_LAYER);
        PregamePanel.add(BackButton,JLayeredPane.DEFAULT_LAYER);
        PregamePanel.add(PlayButton,JLayeredPane.DEFAULT_LAYER);
        for(JLabel i : Background) BgPanel.add(i);
        
        contentPane.add(PregamePanel);
        contentPane.add(BgPanel);
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
        
