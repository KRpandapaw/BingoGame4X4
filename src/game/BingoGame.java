package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class BingoGame {
    static JPanel panelNorth;
    static JPanel panelCenter;
    static JLabel labelMessage;
    static JButton[] buttons = new JButton[16];
    static String[] images = {
            "bear01.png", "bear02.png", "bear03.png", "bear04.png",
            "bear05.png", "bear06.png", "bear07.png", "bear08.png",
            "bear01.png", "bear02.png", "bear03.png", "bear04.png",
            "bear05.png", "bear06.png", "bear07.png", "bear08.png",
    };
    static int openCount = 0;
    static int buttonIndexSave1 = 0;
    static int buttonIndexSave2 = 0;
    static Timer timer;
    static int tryCount = 0;
    static int successCounter = 0;


    static class MyFrame extends JFrame implements ActionListener {
        public MyFrame(String title){

            super(title);
            this.setVisible(true);
            this.setLayout(new BorderLayout());
            this.setSize(400, 500);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            initUI(this);
            mixCard();
            this.pack();
        }
        @Override
        public void actionPerformed(ActionEvent e){
            if(openCount == 2){
                return;
            }
            JButton btn = (JButton)e.getSource();
            int index = getButtonIndex(btn);
            btn.setIcon(changeImage(images[index]));

            openCount++;
            if(openCount == 1){
                buttonIndexSave1 = index;
            } else if (openCount == 2){
                buttonIndexSave2 = index;
                tryCount++;
                labelMessage.setText("Find Same Card " + "Try " + tryCount);

                boolean isBingo = checkCard(buttonIndexSave1, buttonIndexSave2);
                if(isBingo){
                    openCount = 0;
                    successCounter++;
                    if(successCounter == 8){
                        labelMessage.setText("Game Over " + "Try " + tryCount);
                    }
                } else {
                    backToGame();
                }
            }
        }

        public void backToGame(){
            timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   // System.out.println("Timer.");
                    openCount = 0;
                    buttons[buttonIndexSave1].setIcon( changeImage("question.png"));
                    buttons[buttonIndexSave2].setIcon( changeImage("question.png"));
                    timer.stop();

                }
            });
            timer.start();
        }

        public boolean checkCard(int index1, int index2){
            if(index1 == index2){
                return false;
            }
            return images[index1].equals(images[index2]);
        }
        public int getButtonIndex(JButton btn){
            int index = 0;
            for (int i = 0; i < 16; i++){
                if(buttons[i] == btn)
                    index = i;
            }
            return index;
        }
        static void mixCard(){
            Random rand = new Random();
            for (int i = 0; i < 1000; i++){
                int random = rand.nextInt(15) + 1;
                String temp = images[0];
                images[0] = images[random];
                images[random] = temp;
            }
        }

    }
    static void initUI(MyFrame myFrame){
        panelNorth = new JPanel();
        panelNorth.setPreferredSize(new Dimension(400, 100));
        panelNorth.setBackground(Color.DARK_GRAY);
        labelMessage = new JLabel("Find Same Pair! " + "Try 0");
        labelMessage.setPreferredSize(new Dimension(400, 100));
        labelMessage.setForeground(Color.WHITE);
        labelMessage.setFont(new Font("Monaco", Font.BOLD, 20));
        labelMessage.setHorizontalAlignment(JLabel.CENTER);
        panelNorth.add(labelMessage);
        myFrame.add("North", panelNorth);

        panelCenter = new JPanel();
        panelCenter.setLayout(new GridLayout(4,4));
        panelCenter.setPreferredSize(new Dimension(400, 400));
        for(int i = 0; i < 16; i++){
            buttons[i] = new JButton();
            buttons[i].setPreferredSize(new Dimension(100,100));
            buttons[i].setIcon( changeImage("question.png"));
            buttons[i].addActionListener(myFrame);
            panelCenter.add(buttons[i]);
        }
        myFrame.add("Center", panelCenter);


    }
    static ImageIcon changeImage(String filename){
        ImageIcon icon = new ImageIcon("./src/image/" + filename);
        Image originalImage = icon.getImage();
        Image changedImage = originalImage.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon icon_new = new ImageIcon(changedImage);
        return icon_new;
    }

    public static void main(String[] args) {
        new MyFrame("Bingo Game");
    }



}
