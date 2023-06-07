 

import java.util.ArrayList;
public class PathFinder
{
    GamePanel gp;
    node[][] node;
    ArrayList<node> openList = new ArrayList<>();
    public ArrayList<node> pathList = new ArrayList<>();
    node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel g)
    {
        gp =g;
        instantiateNodes();
    }

    public void instantiateNodes()
    {
        node = new node[gp.maxScreenCol][gp.maxScreenRow];

        int col =0;
        int row = 0;
        while(col<gp.maxScreenCol && row<gp.maxScreenRow)
        {
            node[col][row] = new node(col,row);

            col ++;
            if(col == gp.maxScreenCol)
            {
                col=0;
                row++;
            }
        }
    }

    public void resetNodes()
    {
        int col =0;
        int row = 0;

        //Resets the current states of each node
        while(col<gp.maxScreenCol && row<gp.maxScreenRow)
        {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col ++;
            if(col == gp.maxScreenCol)
            {
                col=0;
                row++;
            }
        }
        //resets other node settings
        pathList.clear();
        openList.clear();
        goalReached = false;
        step = 0;
    }
    
    public void setNodes(int StartCol, int StartRow, int GoalCol, int GoalRow)
    {
        resetNodes();
        
        //sets up starting and goal nodes
        startNode = node[StartCol][StartRow];
        currentNode = startNode;
        goalNode = node[GoalCol][GoalRow];
        openList.add(currentNode);
        
        int col =0;
        int row =0;
        
        while(col<gp.maxScreenCol - gp.tileSize && row<gp.maxScreenRow - gp.tileSize)
        {
            //checks for solid tiles
            int tileNum = gp.tile.mapTileNum[col][row];
            if(gp.tile.tile[tileNum].collision = true)
            {
                node[col][row].solid = true;
            }
            
            getCost(node[row][col]);
            col++;
            if(col>=gp.maxScreenCol)
            {
                col = 0;
                row++;
            }
        }
        
    }

    public void getCost(node node)
    {
        //G cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;
        
        //H cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;
        
        //F cost
        node.fCost = node.gCost + node.hCost;
    }
    
    public boolean search()
    {
       while(goalReached == false && step < 500)
       {
          int col = currentNode.col; 
          int row = currentNode.row; 
          
          //Check Current node
          currentNode.checked = true;
          openList.remove(currentNode);
          
          if(row-1>=0)
          {
              openNode(node[col][row-1]);
          }
          if(col-1>=0)
          {
              openNode(node[col-1][row]);
          }
          if(row+1<gp.maxScreenRow)
          {
              openNode(node[col][row+1]);
          }
          if(col+1<gp.maxScreenCol)
          {
              openNode(node[col+1][row]);
          }
          
          //Finding the best node
          int bestNodeIndex=0;
          int bestNodefCost = 999;
          
          for(int i =0; i<openList.size(); i++)
          {
              //Check if node has a better f cost
              if(openList.get(i).fCost < bestNodefCost)
              {
                  bestNodeIndex = i;
                  bestNodefCost = openList.get(i).fCost;
              }
              else if(openList.get(i).fCost == bestNodefCost)
              {
                 if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost)
                 {
                     bestNodeIndex = i;
                 }
              }
          }
          if(openList.size() == 0)
          {
              break;
          }
          currentNode = openList.get(bestNodeIndex);
          if(currentNode == goalNode)
          {
              goalReached = true;
              trackThePath();
          }
          step++;
       }
       return goalReached;
    }
    
    public void trackThePath()
    {
        node current = goalNode;
        while(current != startNode)
        {
          pathList.add(0,current);  
          current = current.parent;
        }
    }
    
    public void openNode(node node)
    {
        if(node.open == false && node.checked == false && node.solid == false)
        {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
    
}
