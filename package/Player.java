
import java.awt.Graphics2D;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Player extends Entity
{
    GamePanel gp;
    KeyHandler kh;
    int x,y;

    int counter2=0;
    int monsterIndex;
    public boolean invincible = true;
    public int invincibleCounter = 0;
    boolean attacking = false;

    public Player(GamePanel g, KeyHandler k)
    {
        super(g);
        gp=g;
        kh = k;

        //sets up attack hitbox
        attackArea = new Rectangle();
        attackArea.height = 13;
        attackArea.width = 13;

        //sets up player invisible collision box
        solidArea = new Rectangle();
        solidArea.x = 4;
        solidArea.y = 8;
        solidArea.height = 18;
        solidArea.width = 18;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y; 

        setDefaultValues();
        getPlayerImage();
        getAttackImage();

    }

    public void setDefaultValues()
    {
        worldX = gp.tileSize*15;
        worldY=gp.tileSize*12;
        x = gp.tileSize*15;
        y = gp.tileSize*12;
        speed = 4;
        direction = "down";

        //Player Status
        maxLife = 50;
        Life = maxLife;
    }

    public void getAttackImage()
    {
        attackLeft = setup("/Player/monkey_attack_left.png", 48, 32);
        attackRight = setup("/Player/monkey_attack_right.png",48, 32);
        attackUp = setup("/Player/monkey_attack_up.png",32, 48);
        attackDown = setup("/Player/monkey_attack_down.png",32, 48);
    }

    public void getPlayerImage()
    {
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/Player/monkey_up_1.png")); 
            up2 = ImageIO.read(getClass().getResourceAsStream("/Player/monkey_up_2.png")); 

            down1 = ImageIO.read(getClass().getResourceAsStream("/Player/monkey_down_1.png")); 
            down2 = ImageIO.read(getClass().getResourceAsStream("/Player/monkey_down_2.png")); 

            left1 = ImageIO.read(getClass().getResourceAsStream("/Player/monkey_left_1.png")); 
            left2 = ImageIO.read(getClass().getResourceAsStream("/Player/monkey_left_2.png")); 

            right1 = ImageIO.read(getClass().getResourceAsStream("/Player/monkey_right_1.png")); 
            right2 = ImageIO.read(getClass().getResourceAsStream("/Player/monkey_right_2.png")); 
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update()
    {
        if(attacking == true){
            attacking(); 
        }

        else if(kh.up == true || kh.down == true || kh.left == true || kh.right == true || kh.attack == true)
        {
            if(kh.up == true)
            {
                direction = "up";
            }
            if(kh.down == true)
            {
                direction = "down";
            }
            if(kh.left == true)
            {
                direction = "left";
            }
            if(kh.right == true)
            {
                direction = "right";
            }
            if(kh.attack == true)
            {
                attacking = true;
            }

            //check player and tile collision
            collisionOn = false;
            gp.cCheck.checkTile(this);
            if(collisionOn == false)
            {
                switch(direction)
                {
                    case "up":
                        worldY = worldY - speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;  
                    case "left":
                        worldX -= speed; 
                        break;
                }
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

        int MonsterIndex = gp.cCheck.CheckEntity(this,gp.enemies);
        contactMonster(MonsterIndex);
        //makes sure player doesnt loose all health in 1 second
        if(invincible == true)
        {
            invincibleCounter++;
            if(invincibleCounter >=60)
            {
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    public void attacking()
    {
        spriteCounter++;

        //Saving current solid area positions
        int currentWorldX = worldX;
        int currentWorldY = worldY;
        int solidAreaWidth = solidArea.width;
        int solidAreaHeight = solidArea.height;

        //Change hitbox for attack
        switch(direction)
        {
            case "up":
                worldY -= attackArea.height; 
                break;
            case "left":
                worldX -= attackArea.width; 
                break;
            case "right":
                worldX += attackArea.height; 
                break;
            case "down":
                worldY += attackArea.height; 
                break;
        }

        solidArea.width = attackArea.width;
        solidArea.height = attackArea.height;

        //checks monster collision with current attackArea
        int MonsterIndex = gp.cCheck.CheckEntity(this,gp.enemies);
        damageMonster(MonsterIndex);

        //restores old hitbox
        worldX = currentWorldX;
        worldY = currentWorldY;
        solidArea.width = solidAreaWidth;
        solidArea.height = solidAreaHeight;

        if(spriteCounter > 25)
        {
            attacking = false;
            spriteCounter = 0;
        }
    }

    public void contactMonster(int index)
    {
        if(index != 999)
        {
            if(invincible == false)
            {
                Life --;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i)
    {
        if(i != 999)
        {
            if(gp.enemies.get(i).invincible == false)
            {
                gp.enemies.get(i).Life -= 1;
                gp.enemies.get(i).invincible = true;
            }
            if(gp.enemies.get(i).Life <= 0)
            {
                gp.enemies.remove(i);
                gp.waves.aliveEnemies --;
                gp.score += 100;
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
                if(attacking == false){
                    if(spriteNum == 1)
                    {
                        image = up1;
                    }
                    if(spriteNum == 2)
                    {
                        image = up2;
                    }
                }
                if(attacking == true)
                {
                    image = attackUp;
                }
                break;

            case "down":
                if(attacking == false){
                    if(spriteNum == 1){
                        image = down1;
                    }
                    if(spriteNum == 2){
                        image = down2;
                    }
                }
                if(attacking == true)
                {
                    image = attackDown;
                }
                break;

            case "right":
                if(attacking == false){
                    if(spriteNum == 1){
                        image = right1;
                    }
                    if(spriteNum == 2){
                        image = right2;
                    }
                }
                if(attacking == true)
                {
                    image = attackRight;
                }
                break;

            case "left":
                if(attacking == false){
                    if(spriteNum == 1){
                        image = left1;
                    }
                    if(spriteNum == 2){
                        image = left2;
                    }
                }
                if(attacking == true)
                {
                    image = attackLeft;
                }
                break;
        }

        g2.drawImage(image,worldX,worldY,null);
    }
}