 

import java.io.IOException;
import javax.imageio.ImageIO;

//properties for heart
public class OBJ_Heart extends SuperObject
{
    GamePanel gp;

    public OBJ_Heart(GamePanel g)
    {
        gp = g;
        name = "Heart";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/player/heart_full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/player/heart_half.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
    }

}