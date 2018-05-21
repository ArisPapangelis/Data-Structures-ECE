package gr.auth.ee.dsproject.pacman;

/**
 * <p>
 * Title: DataStructures2006
 * </p>
 * 
 * <p>
 * Description: Data Structures project: year 2011-2012
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * <p>
 * Company: A.U.Th.
 * </p>
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

    int newDirection = -1;

    while (newDirection == -1) {

      int temp_dir = (int) (4 * Math.random());

      if (Maze[currPosition[0]][currPosition[1]].walls[temp_dir] == 1) {
        newDirection = temp_dir;
      }

    }
    step++;

    return newDirection;
  }

  // THIS IS THE FUNCTION TO IMPLEMENT!!!!!!
  /**�������������: ����-���������� ����������
   * ���:8883
   * ��������:6946126130
   * Email:aris.papagelis@gmail.com
   * 
   * �������������: ������ ���������
   * ���:8858
   * ��������:6972464204
   * Email:Thejokergr@hotmail.gr
   */
  public int[] calculateNextGhostPosition (Room[][] Maze, int[][] currentPos)
  {
	  //� ������� ��� �� ���������� � ��������� ��� ����������. �������� ��� ���� ����������� ��� �� ������� �� ���� ��������.
	  int[] ghostMoves=new int[PacmanUtilities.numberOfGhosts];
	  
	  //�������.
	  int i;
	  
	  /*���������� �� ����� ��� ������ ghostMoves ���� �� ������
	   *�� �������� ��� ������ ���� ��������� checkCollision, ���� �� ��� �������
	   *��������� ��� ��� ����� ��� ������.*/
	  for (i=0;i<PacmanUtilities.numberOfGhosts;i++) {
		  ghostMoves[i]=0;	  
	  }
	  
	  //�������� �� i ��� �� 0 ���� �� ������ �� �������������� ��� �� while loop ��� ���������.
	  i=0;
	  
	  /*���� ��� while loop, ������� ��� random ���� ��� 0 ��� 3, ������ ��� ��� ����� ���� ��� ������ ghostMoves, ������ �� ��������� ��� ��������� �� ������� 
	   * �� ����� ��������. �� ���� � ���� ����� ���������, ������ �� ������ ��� ������� ������ ��� �������� ��� ��������� �� ������� ��������� �� ���� ��������,
	   * ���� ���� � ���� ������� ����� ��� ��������� ���� ���� ���������� ��� ��� �� ������� ��������. �� �������� ���������, �� i=i+1 �� �� ����������  
	   * ��� � ����������� �� ����������� ��� �� ������ ��������,����� �� ������� ������ ������ ������. �� loop ���������� ���� ������������ �� ����������� 
	   * ������� ��� ��� �� ����������, � ������� ��� ������ ��������  ��� ��� PacmanUtilities.numberOfGhosts*/
	  while (i<PacmanUtilities.numberOfGhosts) {
		  ghostMoves[i]=(int) (4*Math.random());
		  if (Maze[currentPos[i][0]][currentPos[i][1]].walls[ghostMoves[i]]==1) {
			  if (checkCollision(ghostMoves,currentPos)[i]==false) {
				  i++;
			  }  
		  }
	  }
	  
	  //��������� �����.
	  return ghostMoves;

    // THIS IS THE FUNCTION TO IMPLEMENT!!!!!!

  }

  public boolean[] checkCollision (int[] moves, int[][] currentPos)
  {
    boolean[] collision = new boolean[PacmanUtilities.numberOfGhosts];

    int[][] newPos = new int[4][2];

    for (int i = 0; i < moves.length; i++) {

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

    for (int i = 0; i < moves.length; i++) {
      for (int j = i + 1; j < moves.length; j++) {
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

}
