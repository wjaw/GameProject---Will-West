
import java.util.ArrayList;
public class Wave
{
    public int wave = 0;
    public int Count1;
    private int numOfEnemies = 1;
    public int aliveEnemies = 0;
    GamePanel gp;

    public Wave(GamePanel g)
    {
        gp = g;
    }

    public void Update()
    {
        if(aliveEnemies <= 0)
        {
            wave++;
            gp.ui.showMessage("WAVE: "+ wave);       
            SpawnWave();
        }
    }

    public void SpawnWave()
    {
        if(wave < 50)
        {
            for(int i = 0; i<wave; i++)
            {
                spawnEnemy(i);
                aliveEnemies ++;
            }
        }
        else
        {
            for(int i = 0; i<50; i++)
            {
                aliveEnemies ++;
                spawnEnemy(i);
                Count1++;
                gp.enemies.get(i).Life += Count1;
            } 
        }
    }

    public void spawnEnemy(int i)
    {
        //random spawn location on map
        int y = 7 + (int)(Math.random() * 12);
        int x = 3 + (int)(Math.random() * 21);

        Entity e = new Fast_Monkey(gp);
        gp.enemies.add(e);
        gp.enemies.get(i).worldX = gp.tileSize*x;
        gp.enemies.get(i).worldY = gp.tileSize*y;
    }

}
