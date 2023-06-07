
/**
Keyboard Controls
 */
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
public class KeyHandler implements KeyListener
{
    GamePanel gp;
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    public boolean attack;

    public KeyHandler(GamePanel g)
    {
        gp = g;
    }

    public void keyTyped(KeyEvent e)
    {

    }

    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();
        if(gp.GameState == gp.TutState)
        {
            if(code == KeyEvent.VK_ESCAPE)
            {
                gp.GameState = gp. TitleState;  
            }
        }
        if(gp.GameState == gp.OverState)
        {
            if(code == KeyEvent.VK_W)
            {
                gp.ui.commandNum --;
                if(gp.ui.commandNum <0)
                {
                    gp.ui.commandNum =1;
                }
            }
            if(code == KeyEvent.VK_S)
            {
                gp.ui.commandNum ++;
                if(gp.ui.commandNum >1)
                {
                    gp.ui.commandNum =0;
                }
            }
            if(code == KeyEvent.VK_ENTER)
            {
                if(gp.ui.commandNum == 0)
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
                if(gp.ui.commandNum == 1)
                {
                    System.exit(0);  
                }
            }
        }
        if(gp.GameState == gp.TitleState)
        {
            if(code == KeyEvent.VK_W)
            {
                gp.ui.commandNum --;
                if(gp.ui.commandNum <0)
                {
                    gp.ui.commandNum =2;
                }
            }
            if(code == KeyEvent.VK_S)
            {
                gp.ui.commandNum ++;
                if(gp.ui.commandNum >2)
                {
                    gp.ui.commandNum =0;
                }
            }
            if(code == KeyEvent.VK_ENTER)
            {
                if(gp.ui.commandNum == 0)
                {
                    gp.GameState = gp.PlayState;
                }
                if(gp.ui.commandNum == 1)
                {
                    gp.GameState = gp.TutState;
                }
                if(gp.ui.commandNum == 2)
                {
                    System.exit(0);  
                }
            }
        }
        if(code == KeyEvent.VK_W)
        {
            up = true;
        }
        if(code == KeyEvent.VK_A)
        {
            left = true;
        }
        if(code == KeyEvent.VK_S)
        {
            down = true;
        }
        if(code == KeyEvent.VK_D)
        {
            right = true;
        }
        if(code == KeyEvent.VK_SPACE)
        {
            attack = true;
        }

        if(code == KeyEvent.VK_P)
        {
            if(gp.GameState == gp.PlayState)
            {
                gp.GameState = gp.PauseState;
            }
            else if(gp.GameState == gp.PauseState)
            {
                gp.GameState = gp.PlayState;
            }
        }
    }

    public void keyReleased(KeyEvent e)
    {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W)
        {
            up = false;
        }
        if(code == KeyEvent.VK_A)
        {
            left = false;
        }
        if(code == KeyEvent.VK_S)
        {
            down = false;
        }
        if(code == KeyEvent.VK_D)
        {
            right = false;
        }
        if(code == KeyEvent.VK_SPACE)
        {
            attack = false;
        }
    }
}