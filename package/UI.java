
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.text.DecimalFormat;
import java.awt.image.BufferedImage;

/**
Displays game messages for player
 */
public class UI
{
    Graphics2D g2;
    GamePanel gp;
    Font arial_40;
    Font arial_20;
    double ScoreMP;
    DecimalFormat df = new DecimalFormat("#0.00");
    public int commandNum = 0;
    BufferedImage hf,hh,he;
    public boolean messageOn= false;
    public String message = "";
    int messageCounter = 0;

    public UI(GamePanel g)
    {
        gp = g;
        arial_40 = new Font( "Arial", Font.PLAIN, 40);
        arial_20 = new Font( "Arial", Font.PLAIN, 20);

        //Object HUD
        SuperObject heart = new OBJ_Heart(gp);
        hf=heart.image;
        hh=heart.image2;
        he=heart.image3;
    }

    public void showMessage(String m)
    {
        message = m;
        messageOn = true;
    }

    public void draw(Graphics2D g)
    {
        g2 = g;
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        if(gp.GameState == gp.TitleState)
        {
            drawTitleScreen();
        }
        if(gp.GameState == gp.OverState)
        {
            drawGameOver();
        }
        if(gp.GameState == gp.TutState)
        {
            gp.tile.loadMap("/Maps/Tutorial.txt");
            gp.tile.draw(g2);
            g2.setFont(arial_20);
            g2.drawString("W A S D to move", gp.tileSize, gp.tileSize);
            g2.drawString("Spacebar to attack", gp.tileSize, gp.tileSize*3);
            g2.drawString("P to pause", gp.tileSize, gp.tileSize *5);
            g2.drawString("ESC to return", gp.tileSize, gp.tileSize *7);
            g2.drawString("Enemies can spawn anywhere and more spawn each wave", gp.tileSize*15, gp.tileSize *3);
        }
        //Checks wether the player paused the game before updtaing the UI print
        if(gp.GameState == gp.PlayState)
        {
            gp.tile.loadMap("/Maps/MainMap.txt");
            g2.drawString("Score: "+ gp.score, gp.tileSize*15, gp.tileSize); 
            drawPlayerLife();
            if(messageCounter >120)
            {
                messageCounter = 0;
                messageOn = false;
            }
        }
        if(gp.GameState == gp.PauseState)
        {
            drawPause();
        }
        if(messageOn == true)
        {
            g2.drawString(message, getXForCenteredText(message), gp.tileSize*11);
            messageCounter++;
        }
    }

    public void drawPlayerLife()
    {
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        while(i<gp.player.Life)
        {
            g2.drawImage(hh,x,y,null);
            i++;
            if(i <gp.player.Life)
            {
                g2.drawImage(hf,x,y,null);
            }
            i++;
            x+=gp.tileSize/2;

        }
    }

    public void drawGameOver()
    {
        //Background of the title screen
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0,gp.ScreenWidth,gp.ScreenHeight);

        //Font and size of the title
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "GAME OVER";
        int x = getXForCenteredText(text);
        int y = gp.tileSize*3;

        //Shadow under the title
        g2.setColor(Color.WHITE);
        g2.drawString(text,x+5,y+5);

        g2.setColor(Color.BLACK);
        g2.drawString(text,x,y);

        //Image
        x = gp.ScreenWidth/2 - (gp.tileSize*5)/2;
        y += gp.tileSize*2;
        g2.drawImage(gp.tile.tile[4].image, x,y,gp.tileSize*5, gp.tileSize*5,null);

        //Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        text = "Rerty";
        x = getXForCenteredText(text);
        y+= gp.tileSize*7;
        g2.drawString(text, x,y);
        if(commandNum == 0)
        {
            g2.drawString(">",x-gp.tileSize*2,y);
        }

        text = "Exit";
        x = getXForCenteredText(text);
        y+= gp.tileSize*4;
        g2.drawString(text, x,y);
        if(commandNum == 1)
        {
            g2.drawString(">",x-gp.tileSize*2,y);
        }
    }

    public void drawTitleScreen()
    {
        //Background of the title screen
        g2.setColor(new Color(255,255,53));
        g2.fillRect(0,0,gp.ScreenWidth,gp.ScreenHeight);

        //Font and size of the title
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Monkey Mayhem";
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.ScreenWidth/2 - (length/2);
        int y = gp.tileSize*3;

        //Shadow under the title
        g2.setColor(Color.WHITE);
        g2.drawString(text,x+5,y+5);

        g2.setColor(Color.BLACK);
        g2.drawString(text,x,y);

        //Image
        x = gp.ScreenWidth/2 - (gp.tileSize*5)/2;
        y += gp.tileSize*2;
        g2.drawImage(gp.tile.tile[3].image, x,y,gp.tileSize*5, gp.tileSize*5,null);

        //Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        text = "Start";
        x = getXForCenteredText(text);
        y+= gp.tileSize*7;
        g2.drawString(text, x,y);
        if(commandNum == 0)
        {
            g2.drawString(">",x-gp.tileSize*2,y);
        }

        text = "Tutorial";
        x = getXForCenteredText(text);
        y+= gp.tileSize*4;
        g2.drawString(text, x,y);
        if(commandNum == 1)
        {
            g2.drawString(">",x-gp.tileSize*2,y);
        }

        text = "Exit";
        x = getXForCenteredText(text);
        y+= gp.tileSize*4;
        g2.drawString(text, x,y);
        if(commandNum == 2)
        {
            g2.drawString(">",x-gp.tileSize*2,y);
        }
    }

    public void drawPause()
    {
        //Displays Text when player pauses game
        String text = "PAUSED";
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.ScreenWidth/2 - (length/2);
        int y = gp.ScreenHeight/2;

        g2.drawString(text,x,y);
    }

    //Returns the center x value for a specific text size;
    public int getXForCenteredText(String text)
    {
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.ScreenWidth/2 - (length/2);
        return x;
    }
}
