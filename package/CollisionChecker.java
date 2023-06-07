
import java.util.ArrayList;
public class CollisionChecker
{
    GamePanel gp;

    public CollisionChecker(GamePanel g)
    {
        gp = g;
    }

    public void checkTile(Entity entity)
    {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y+entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch(entity.direction)
        {
            case "up":
                entityTopRow = (entityTopWorldY -entity.speed)/gp.tileSize;
                tileNum1 = gp.tile.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tile.mapTileNum[entityRightCol][entityTopRow];
                if(gp.tile.tile[tileNum1].collision == true || gp.tile.tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;   
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY -entity.speed)/gp.tileSize;
                tileNum1 = gp.tile.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tile.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tile.tile[tileNum1].collision == true || gp.tile.tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;   
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX -entity.speed)/gp.tileSize;
                tileNum1 = gp.tile.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tile.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tile.tile[tileNum1].collision == true || gp.tile.tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;   
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX -entity.speed)/gp.tileSize;
                tileNum1 = gp.tile.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tile.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.tile.tile[tileNum1].collision == true || gp.tile.tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;   
                }
                break;
        }

    }

    public int CheckEntity(Entity entity, ArrayList<Entity> target)
    {
        int index = 999;
        for(int i = 0; i<target.size();i++)
        {
            if(target.get(i) != null)
            {
                //Gets the entitys current solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //Gets other entitys solid area postion
                target.get(i).solidArea.x = target.get(i).worldX + target.get(i).solidArea.x;
                target.get(i).solidArea.y = target.get(i).worldY + target.get(i).solidArea.y ;

                switch(entity.direction)
                {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(target.get(i).solidArea))
                        {
                            //entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(target.get(i).solidArea))
                        {
                            //entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(target.get(i).solidArea))
                        {
                            //entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(target.get(i).solidArea))
                        {
                            //entity.collisionOn = true;
                            index = i;
                        }
                        break;

                }
            }
            entity.solidArea.x = entity.solidAreaDefaultX;
            entity.solidArea.y = entity.solidAreaDefaultY;
            target.get(i).solidArea.x=target.get(i).solidAreaDefaultY;
            target.get(i).solidArea.y=target.get(i).solidAreaDefaultY;
        }
        return index;
    }

    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;

        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        checkFutureMovement(entity);

        if (entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactPlayer;
    }
    
     private void checkFutureMovement(Entity entity) {
        switch (entity.direction) {
            case "up" -> entity.solidArea.y -= entity.speed;
            case "down" -> entity.solidArea.y += entity.speed;
            case "left" -> entity.solidArea.x -= entity.speed;
            case "right" -> entity.solidArea.x += entity.speed;
        }
    }
}