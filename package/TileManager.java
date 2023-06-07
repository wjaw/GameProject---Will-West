 

/**
 * Write a description of class TileManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics2D;
import java.io.InputStream; 
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TileManager
{
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel g)
    {
        gp = g;
        tile = new Tile[10];
        mapTileNum = new int [gp.maxScreenCol][gp.maxScreenRow];

        getTileImage();
        loadMap("/Maps/MainMap.txt");

    }

    public void getTileImage()
    {
        //Instatiates each tile into the array from the Tiles folder
        try{
            //Game Over image
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/grave.png"));
            
            //Tile 3 = title image
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/TitleSwords.png"));
            
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/grass01.png")); 

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall.png"));
            tile[0].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/water01.png"));

        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g)
    {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col<gp.maxScreenCol && row<gp.maxScreenRow)
        {
            int tileNum = mapTileNum[col][row];

            g.drawImage(tile[tileNum].image,x,y,gp.tileSize, gp.tileSize,null); 
            col++;
            x+= gp.tileSize;

            if(col == gp.maxScreenCol)
            {
                col = 0;
                row++;
                x=0;
                y+= gp.tileSize;
            }
        }
    }

    public void loadMap(String s)
    {
        try{
            InputStream is = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col<gp.maxScreenCol && row<gp.maxScreenRow)
            {
                String line = br.readLine();
                while(col<gp.maxScreenCol)
                {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col==gp.maxScreenCol)
                {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }
        catch(Exception e)
        {

        }

    }

}