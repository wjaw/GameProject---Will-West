
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel implements Runnable
{
    // Screen size settings
    public final int originalTileSize = 32;
    public final int scale = 1;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 32;
    public final int maxScreenRow = 24;
    public final int ScreenWidth = tileSize*maxScreenCol;
    public final int ScreenHeight = tileSize*maxScreenRow;

    //FPS and Timer
    int FPS = 60;
    double time = 0.0;

    Wave waves = new Wave(this);
    public CollisionChecker cCheck = new CollisionChecker(this);
    public TileManager tile = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Player player = new Player(this,keyH);
    public UI ui = new UI(this);
    OBJ_Heart heart = new OBJ_Heart(this);
    public Fast_Monkey fm = new Fast_Monkey(this);
    public PathFinder pFinder = new PathFinder(this);

    //GameState
    public int GameState;
    public final int PlayState = 1;
    public final int PauseState = 2;
    public final int OverState = 3;
    public final int TutState = 4;

    //Title Screen State
    public final int TitleState = 0;

    //Starting the timer
    Thread gameThread;

    //Player default position
    int playerX = 200;
    int playerY = 130;
    int playerSpeed = 4;

    //Entity Array List
    public ArrayList<Entity> enemies = new ArrayList<Entity>();
    public int score = 0;
    public BufferedImage image;

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame()
    {
        GameState = TitleState;
    }

    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run()
    {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;

        while(gameThread != null)
        {
            //Updates information and redraws it.
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1)
            {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000)
            {
                System.out.println("FPS: "+drawCount);  
                drawCount = 0;
                timer = 0;
            }
        }
    } 

    public void update()
    {
        if(player.Life == 0)
        {
            GameState = OverState;  
        }
        if(GameState == PlayState)
        {
            player.update();
            time += (double)1/60;
            updateEnemy();
            if(waves.aliveEnemies <= 0)
            {
                waves.Update();
            }
        }
        if(GameState == PauseState)
        {

        }
        if(GameState == TutState)
        {
            player.update();
            updateEnemy();
            waves.SpawnWave();
        }
    }

    public void updateEnemy()
    {
        for(Entity e: enemies)
        {
            e.update();
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g); 
        Graphics2D g2  = (Graphics2D)g;

        //Title Screen
        if(GameState == TitleState)
        {
            ui.draw(g2);
        }
        else if(GameState == OverState)
        {
            ui.draw(g2);  
        }
        else if(GameState == TutState)
        {
            ui.draw(g2);
            player.draw(g2);
            for(Entity e: enemies)
            {
                e.draw(g2);
            }
        }
        else
        {

            //Draws all the tiles on the map first
            tile.draw(g2);

            //Draws the player on a layer above the tiles
            player.draw(g2);

            //draws enemies
            for(Entity e: enemies)
            {
                e.draw(g2);
            }

            //Draws the UI
            ui.draw(g2);
        }

        g2.dispose();
    }
}