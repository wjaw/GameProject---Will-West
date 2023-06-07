import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class Entity
{
    GamePanel gp;

    //Instance variables for sprite values
    public int worldX,worldY;
    public int speed;
    public String name;

    //Instance Variables for getting sprite images
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackLeft, attackRight, attackUp, attackDown;
    public String direction = "down";

    //Sprite animations and hitboxes
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public int solidAreaDefaultY;
    public int solidAreaDefaultX;
    public boolean collisionOn = false;
    public Rectangle attackArea;
    public boolean invincible;
    public int invincibleCounter = 0;
    public boolean onPath = true;

    //Character Status
    public int maxLife;
    public int Life;

    public Entity(GamePanel g)
    {
        gp = g;
    }

    public BufferedImage setup(String imageName, int width, int height)
    {
        UtilityTool uTool = new UtilityTool();
        BufferedImage Scaledimage = null;
        try{
            Scaledimage = ImageIO.read(getClass().getResourceAsStream(imageName));
            Scaledimage = uTool.scaleImage(Scaledimage,width,height);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return Scaledimage;
    }

    public void setAction()
    {
        if(onPath == true)
        {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;

            searchPath(goalCol,goalRow);
        }
    }

    public void update()
    {
        setAction();

        collisionOn = false;
        gp.cCheck.checkTile(this);
        //gp.cCheck.CheckEntity(this,gp.enemies);
        boolean contactPlayer = gp.cCheck.checkPlayer(this);

        if(invincible == true)
        {
            invincibleCounter++;
            if(invincibleCounter >=60)
            {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(collisionOn == false)
        {
            switch(direction)
            {
                case "up": 
                    worldY -= speed;
                    break;
                case "down": 
                    worldY += speed; 
                    break;
                case "left": 
                    worldX -= speed; 
                    break;
                case "right": 
                    worldX += speed; 
                    break;
            }
            spriteCounter++;
            if(spriteCounter==10){
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 2)
                {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

    }

    public void searchPath(int goalCol, int goalRow)
    {
        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol,startRow,goalCol,goalRow);

        if(gp.pFinder.search() == true)
        {
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            //Checking entitys current solid area position and next area position to see where it is moving to
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX+gp.tileSize)
            {
                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX+gp.tileSize)
            {
                direction = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize)
            {
                if(enLeftX >nextX)
                {
                    direction = "left";
                }
                if(enLeftX < nextX)
                {
                    direction = "right";
                }
            }
        }
    }

    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;

        //Switches the image being drawn based on which key is being pressed
        switch(direction)
        {
            case "up":
                if(spriteNum == 1)
                {
                    image = right1;
                }
                if(spriteNum == 2)
                {
                    image = right2;
                }
                break;

            case "down":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;

            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;

            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
        }

        g2.drawImage(image,worldX,worldY,gp.tileSize,gp.tileSize,null);
    }
}
