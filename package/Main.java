 

/**
 * The main screen when you start the games with play, tutorial and exit options
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
public class Main
{
    public static void main(String[] args)
    {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Monkey Adventure");
        GamePanel game = new GamePanel();
        window.add(game);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        game.setupGame();
        game.startGameThread();
    }
}