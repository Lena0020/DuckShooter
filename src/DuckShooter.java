import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class DuckShooter extends JFrame {
    int counter;
    int difficulty;

    private static JPanel jPanel1;
    private static JLayeredPane jPanel2;
    private static JButton b5;
    private static DuckShooter instance;
    private static Thread timer;
    private static Thread addingDucks;
    private static Thread difficultyOverTime;
    private static Thread clouds;
    private static JLabel currentScore;
    private static JLabel howManyDucksEscaped;

    public DuckShooter() {
        instance = this;

        jPanel1 = new JPanel(); //main menu
        jPanel2 = new JLayeredPane(); //duck shooter
        JPanel jPanel3 = new JPanel(); //high scores
        JLabel timer1 = new JLabel(); //timer
        JButton tree1 = new JButton();
        JButton tree2 = new JButton();
        currentScore = new JLabel();
        howManyDucksEscaped = new JLabel();



        jPanel1.setBackground((new java.awt.Color(51,204,255)));
        jPanel1.setLayout(null);
        jPanel2.setLayout(null);
        jPanel3.setLayout(new BorderLayout());

        JButton b1 = new JButton("New Game");
        b1.setFont(new Font("DialogInput", Font.BOLD, 18));
        b1.setBounds(115, 100, 250, 50);
        b1.setBackground((new java.awt.Color(255,255,255)));
        b1.setFocusable(false);
        b1.setBorder(null);

        JButton b2 = new JButton("High Scores");
        b2.setFont(new Font("DialogInput", Font.BOLD, 18));
        b2.setBounds(115, 200, 250, 50);
        b2.setBackground((new java.awt.Color(255,255,255)));
        b2.setFocusable(false);
        b2.setBorder(null);

        JButton b3 = new JButton("Exit");
        b3.setFont(new Font("DialogInput", Font.BOLD, 18));
        b3.setBounds(115, 300, 250, 50);
        b3.setBackground((new java.awt.Color(255,255,255)));
        b3.setFocusable(false);
        b3.setBorder(null);

        JButton b4 = new JButton("Back to the Menu");
        b4.setFont(new Font("DialogInput", Font.BOLD, 20));
        b4.setBackground(Color.white);
        b4.setFocusable(false);
        b4.setBorder(null);

        b5 = new JButton("Upgrade weapon");
        b5.setFont(new Font("DialogInput", Font.BOLD, 15));
        b5.setBounds(345, 0, 140, 35);
        b5.setBackground((new java.awt.Color(255,255,255)));
        b5.setFocusable(false);
        b5.setBorder(BorderFactory.createBevelBorder(1, Color.darkGray, Color.darkGray, Color.darkGray, Color.darkGray));


        jPanel1.add(b1);
        jPanel1.add(b2);
        jPanel1.add(b3);
        jPanel3.add(b4, BorderLayout.PAGE_END);

        //First button - New Game

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("*new window opening*");

                String[] arr = {"Easy", "Normal", "Hard"};

                String options = (String) JOptionPane.showInputDialog(null, "Please select the difficulty : ", "Duck Shooter - difficulty options", JOptionPane.PLAIN_MESSAGE, null, arr, arr[1]);
                System.out.println(options);
                if(options == null) return;

                jPanel2 = new JLayeredPane();
                jPanel2.setOpaque(true);

                remove(jPanel1);
                add(jPanel2);
                revalidate();
                repaint();
                Duck.setCnt(0);
                Duck.setScore(0);


                jPanel2.setBackground((new java.awt.Color(51,153,255)));
                jPanel2.add(timer1);
                timer1.setBounds(0, 400, 100, 100);
                timer1.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));

                if(timer != null && timer.isAlive()) timer.interrupt();
                timer = new Thread(() -> {
                    counter = 0;
                    while (!Thread.interrupted()) {
                        try {
                            timer1.setText("Timer : " + counter + "s");
                            Thread.sleep(1000);
                        } catch (InterruptedException ea) {
                            return;
                        }
                        counter++;
                    }
                });
                timer.start();

                difficultyOverTime = new Thread(() -> {
                    while (!Thread.interrupted()) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ea) {
                            return;
                        }
                        if (Duck.getSpeed() > 10)
                            Duck.setSpeed(Duck.getSpeed() - 5);
                        else Duck.setSpeed(10);
                    }
                });
                difficultyOverTime.start();

                switch (options) {
                        case "Easy" -> difficulty = 4000;
                        case "Normal" -> difficulty = 3000;
                        case "Hard" -> difficulty = 2500;
                        default -> difficulty = 3000;
                }

                if(addingDucks != null && addingDucks.isAlive()) addingDucks.interrupt();
                addingDucks = new Thread(() -> {
                    while (!Thread.interrupted()) {
                        jPanel2.add(new Duck());
                        try {
                            Thread.sleep(difficulty);
                        } catch (InterruptedException ea) {
                            return;
                        }
                    }
                });
                addingDucks.start();

                if(clouds != null && clouds.isAlive()) clouds.interrupt();
                clouds = new Thread(() -> {
                    while (!Thread.interrupted()) {
                        jPanel2.add(new Cloud(),1);
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ea) {
                            return;
                        }
                    }
                });
                clouds.start();

                jPanel2.add(tree1,2);
                tree1.setBounds(250,270,196,200);
                Icon icon1 = new ImageIcon("Tree1.png");
                tree1.setIcon(icon1);
                tree1.setOpaque(false);
                tree1.setContentAreaFilled(false);
                tree1.setBorderPainted(false);
                tree1.setFocusable(false);
                tree1.setBorder(null);

                jPanel2.add(tree2,2);
                tree2.setBounds(20,270,196,200);
                Icon icon2 = new ImageIcon("Tree1.png");
                tree2.setIcon(icon2);
                tree2.setOpaque(false);
                tree2.setContentAreaFilled(false);
                tree2.setBorderPainted(false);
                tree2.setFocusable(false);
                tree2.setBorder(null);

                jPanel2.add(currentScore);
                currentScore.setBounds(0,20, 250,20);
                currentScore.setText("Score - 0");

                jPanel2.add(howManyDucksEscaped);
                howManyDucksEscaped.setBounds(0,0, 250,20);
                howManyDucksEscaped.setText("Ducks that escaped - 0");

                jPanel2.add(b5,2);
            }
        });

        //Second button - High Scores

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JList jList = new JList(new ListOfHighscoresModel());
                jPanel3.add(new JScrollPane(jList));
                jList.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
                jList.setBackground((new java.awt.Color(51,204,255)));


                System.out.println("*new window opening*");
                remove(jPanel1);
                add(jPanel3);
                revalidate();
                repaint();
            }
        });

        //Third button - Exit

        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("*exiting the program*");
                System.exit(0);
            }
        });
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("*go to menu*");
                remove(jPanel3);
                add(jPanel1);
                revalidate();
                repaint();
            }
        });

        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Duck.getScore() >= 5 && Duck.getDamage() <=3){
                    Duck.setScore(Duck.getScore()-5);
                    Duck.setDamage(Duck.getDamage() + 1);
                }
            }
        });
        //

        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("*go to menu*");
                remove(jPanel2);
                add(jPanel1);
                revalidate();
                repaint();
            }
        };
        b5.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Q,KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK),"Back");
        b5.getActionMap().put("Back",action);
        b5.setFocusable(true);

        add(jPanel1);
        this.setTitle("Duck Shooter");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public static JPanel getJPanel1() {
        return jPanel1;
    }

    public static JLayeredPane getJPanel2() {
        return jPanel2;
    }

    public static DuckShooter getInstance() {
        return instance;
    }

    public static Thread getTimer() {
        return timer;
    }

    public static Thread getAddingDucks() {
        return addingDucks;
    }

    public static Thread getDifficultyOverTime() {
        return difficultyOverTime;
    }

    public static JButton getB5() {
        return b5;
    }

    public static JLabel getCurrentScore() {
        return currentScore;
    }

    public static JLabel getHowManyDucksEscaped() {
        return howManyDucksEscaped;
    }
}

