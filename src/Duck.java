import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Duck extends JButton {
    private static int speed = 50;
    private static int cnt = 0;
    private int hp;
    private Duck duck;
    private static int score = 0;
    private static int damage = 1;

    public Duck() {
        super();
        duck = this;
        int randomDuck = (int) (Math.random() * 5);
        String ducks;

        switch (randomDuck) {
            case 0 -> {
                ducks = "Duck.png";
                hp = 2;
                break;
            }
            case 1 -> {
                ducks = "Duck1.png";
                hp = 4;
                break;
            }
            case 2 -> {
                ducks = "Duck2.png";
                hp = 6;
                break;
            }
            case 3 -> {
                ducks = "Duck3.png";
                hp = 8;
                break;
            }
            default -> {
                ducks = "Duck.png";
                hp = 2;
            }

        }

        Icon icon = new ImageIcon(ducks);
        setIcon(icon);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusable(false);
        setBorder(null);


        Thread thread = new Thread(() -> {
            int i = 0;
            int y = (int) (Math.random() * 400); //
            while (!Thread.interrupted() && cnt < 5) {
                setBounds(i, y, 70, 70);
                if (i > 500) {
                    cnt++;
                    DuckShooter.getHowManyDucksEscaped().setText("Ducks that escaped - " + cnt);
                    if (cnt >= 5) {
                        String nick = JOptionPane.showInputDialog(null, "Your nick :", "Game Over", JOptionPane.QUESTION_MESSAGE);
                        if (nick == null) {

                            System.exit(0);

                        } else {

                            new HighScore(nick, score);
                            System.out.println("*go to menu*");
                            DuckShooter.getInstance().remove(DuckShooter.getJPanel2());
                            DuckShooter.getInstance().add(DuckShooter.getJPanel1());
                            DuckShooter.getInstance().revalidate();
                            DuckShooter.getInstance().repaint();
                            speed = 50;
                        }
                    }
                    return;
                }
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException ea) {
                    return;
                }
                i = i + 2;
            }
        });
        thread.start();

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hp-=damage;
                if (hp <= 0) {
                    thread.interrupt();
                    DuckShooter.getJPanel2().remove(duck);
                    DuckShooter.getJPanel2().revalidate();
                    DuckShooter.getJPanel2().repaint();
                    score++;
                    DuckShooter.getCurrentScore().setText("Score - " + score);
                }
            }
        });
    }

    public static void setSpeed(int speed) {
        Duck.speed = speed;
    }

    public static int getSpeed() {
        return speed;
    }

    public static void setCnt(int cnt) {
        Duck.cnt = cnt;
    }

    public static int getCnt() {
        return cnt;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Duck.score = score;
        DuckShooter.getCurrentScore().setText("Score - " + score);
    }

    public static int getDamage() {
        return damage;
    }

    public static void setDamage(int damage) {
        Duck.damage = damage;
    }
}
