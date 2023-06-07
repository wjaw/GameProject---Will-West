import java.awt.Rectangle;

public class Fast_Monkey extends Entity
{
    boolean onPath;
    public Fast_Monkey(GamePanel gp)
    {
        super(gp);

        name = "Fast Monkey";
        speed = 2;
        maxLife = 1;
        Life = maxLife;
        invincible = false;

        solidArea = new Rectangle();
        solidArea.x = 4;
        solidArea.y = 5;
        solidArea.height = 21;
        solidArea.width = 19;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y; 
        onPath = true;

        getImage();
    }

    public void getImage()
    {
        left1 = setup("/Enemies/fastMonkey/fm_left_1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/Enemies/fastMonkey/fm_left_2.png", gp.tileSize, gp.tileSize);
        right1 = setup("/Enemies/fastMonkey/fm_right_1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/Enemies/fastMonkey/fm_right_2.png", gp.tileSize, gp.tileSize);
    }

}
