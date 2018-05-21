package gr.auth.ee.dsproject.pacman;
import gr.auth.ee.dsproject.node.*;
import java.util.*;

/**
 * <p>Title: DataStructures2011</p>
 *
 * <p>Description: Data Structures project: year 2011-2012</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: A.U.Th.</p>
 *
 * @author Michael T. Tsapanos
 * @version 1.0
 */


public class Creature implements gr.auth.ee.dsproject.pacman.AbstractCreature
{

  public String getName ()
  {
    return "Mine";
  }

  private int step = 1;
  private boolean amPrey;

  public Creature (boolean isPrey)
  {
    amPrey = isPrey;

  }

  public int calculateNextPacmanPosition (Room[][] Maze, int[] currPosition)
  {
	//Εδω χρησιμοποιουμε το arrayList για να αποφασισουμε ποια κινηση θα επιλεξουμε εν τελει.
    int moveToReturn=0;
    ArrayList<Node88838858> moveList=new ArrayList<Node88838858>();
    Node88838858 moveI;
    
    //Ο ελεγχος γινεται και για τις 4 κατευθυνσεις, απορριπτοντας φυσικα καθε κατευθυνση προς την οποια υπαρχει τοιχος.
    for (int i=0;i<4;i++) {
    	if (Maze[currPosition[0]][currPosition[1]].walls[i]==1) {
    		
    		//Καλειται ο constructor του αντικειμενου moveI, με ορισματα τη θεση του πακμαν, την κατευθυνση της κινησης, και τον πινακα Maze.
    		//Υστερα, προστιθεται το συγκεκριμενο αντικειμενο στο δυναμικο πινακα moveList.
    		moveI=new Node88838858(currPosition[0],currPosition[1],i,Maze);
        	moveList.add(moveI);
    	}
    }
    //Θελουμε να επιλεξουμε την καλυτερη κινηση, δηλαδη αυτη με το max evaluation.
    double max=-1000;
    
    //Ο δυναμικος πινακας moveList δεν εχει παντα 4 αντικειμενα, εφοσον καποιες κατευθυνσεις μπορει να απορριφθηκαν λογω τοιχων.
    for (int i=0;i<moveList.size();i++) {
    	if (moveList.get(i).nodeEvaluation>max) {
    		max=moveList.get(i).nodeEvaluation;
    		moveToReturn=moveList.get(i).nodeMove;
    	}
    }
    return moveToReturn;

  }

  public int[] calculateNextGhostPosition (Room[][] Maze, int[][] currentPos)
  {

    int[] moves = new int[4];
    int[] eval = new int[4];
    boolean[] ghostColision = new boolean[PacmanUtilities.numberOfGhosts];

    // System.out.println("Ghosts Current Positions ");
    for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
      // System.out.println("ghost x = "+currentPos[i][0]+" ghost y = "+ currentPos[i][1]);
    }
    // System.out.println(" ");

    int pacmanX = 0;
    int pacmanY = 0;

    for (int i = 0; i < PacmanUtilities.numberOfRows; i++)
      for (int j = 0; j < PacmanUtilities.numberOfColumns; j++) {
        if (Maze[i][j].isPacman()) {
          pacmanX = i;
          pacmanY = j;

        }
      }

    // pacmanX = 0;
    // pacmanY = 0;

    // System.out.println(pacmanX);
    // System.out.println(pacmanY);
    // System.out.println("");

    eval = newGhostDir(pacmanX, pacmanY, currentPos);
    ghostColision = checkCollision(eval, currentPos);

    for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
      while (!evaluateNewDirection(currentPos, i, eval, Maze) || ghostColision[i] || !checkFlag(currentPos, i, eval, Maze)) {
        eval[i] = (int) (4 * Math.random());
        ghostColision = checkCollision(eval, currentPos);
      }
    }

    moves = eval;

    // for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++)
    // System.out.println("direction of ghost "+i+" = " +moves[i]);
    // System.out.println(" ");

    return moves;

  }

  public int[] newGhostDir (int pacX, int pacY, int[][] currentPos)
  {
    int[] tempDirections = new int[4];

    for (int k = 0; k < PacmanUtilities.numberOfGhosts; k++) {
      if (pacX < currentPos[k][0]) // pacman norther than ghost
      {
        tempDirections[k] = Room.NORTH;
      }

      if (pacX > currentPos[k][0]) // pacman souther than ghost
      {
        tempDirections[k] = Room.SOUTH;
      }

      if (pacY > currentPos[k][1]) // pacman easter than ghost
      {
        tempDirections[k] = Room.EAST;
      }

      if (pacY < currentPos[k][1]) // pacman wester than ghost
      {
        tempDirections[k] = Room.WEST;
      }
    }

    return tempDirections;
  }

  public boolean evaluateNewDirection (int[][] curPos, int i, int[] direction, Room[][] Maze)
  {
    boolean validChoice = true;

    if (Maze[curPos[i][0]][curPos[i][1]].walls[direction[i]] == 0)
      validChoice = false;

    return validChoice;

  }

  public boolean checkFlag (int[][] curPos, int i, int[] direction, Room[][] Maze)
  {
    boolean validChoice = true;

    if (direction[i] == Room.NORTH) {
      if (Maze[curPos[i][0] - 1][curPos[i][1]].isFlag()) {
        validChoice = false;
      }
    }

    if (direction[i] == Room.SOUTH) {
      if (Maze[curPos[i][0] + 1][curPos[i][1]].isFlag()) {
        validChoice = false;
      }
    }

    if (direction[i] == Room.EAST) {
      if (Maze[curPos[i][0]][curPos[i][1] + 1].isFlag()) {
        validChoice = false;
      }
    }

    if (direction[i] == Room.WEST) {
      if (Maze[curPos[i][0]][curPos[i][1] - 1].isFlag()) {
        validChoice = false;
      }
    }
    return validChoice;
  }

  public boolean[] checkCollision (int[] moves, int[][] currentPos)
  {
    boolean[] collision = new boolean[PacmanUtilities.numberOfGhosts];

    int[][] newPos = new int[4][2];

    for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {

      if (moves[i] == 0) {
        newPos[i][0] = currentPos[i][0];
        newPos[i][1] = currentPos[i][1] - 1;
      } else if (moves[i] == 1) {
        newPos[i][0] = currentPos[i][0] + 1;
        newPos[i][1] = currentPos[i][1];
      } else if (moves[i] == 2) {
        newPos[i][0] = currentPos[i][0];
        newPos[i][1] = currentPos[i][1] + 1;
      } else {
        newPos[i][0] = currentPos[i][0] - 1;
        newPos[i][1] = currentPos[i][1];
      }

      collision[i] = false;
    }

    for (int k = 0; k < moves.length; k++) {
      // System.out.println("Ghost " + k + " new Position is (" + newPos[k][0] + "," + newPos[k][1] + ").");
    }

    for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
      for (int j = i + 1; j < PacmanUtilities.numberOfGhosts; j++) {
        if (newPos[i][0] == newPos[j][0] && newPos[i][1] == newPos[j][1]) {
          // System.out.println("Ghosts " + i + " and " + j + " are colliding");
          collision[j] = true;
        }

        if (newPos[i][0] == currentPos[j][0] && newPos[i][1] == currentPos[j][1] && newPos[j][0] == currentPos[i][0] && newPos[j][1] == currentPos[i][1]) {
          // System.out.println("Ghosts " + i + " and " + j + " are colliding");
          collision[j] = true;
        }

      }

    }
    return collision;
  }

  public int newPacmanDir (int pacX, int pacY, int[] currentPos)
  {
    int tempDirections = 5;

    if (pacX < currentPos[0]) // pacman norther than ghost
    {
      tempDirections = Room.NORTH;
    }

    if (pacX > currentPos[0]) // pacman souther than ghost
    {
      tempDirections = Room.SOUTH;
    }

    if (pacY > currentPos[1]) // pacman easter than ghost
    {
      tempDirections = Room.EAST;
    }

    if (pacY < currentPos[1]) // pacman wester than ghost
    {
      tempDirections = Room.WEST;
    }

    return tempDirections;
  }

}
