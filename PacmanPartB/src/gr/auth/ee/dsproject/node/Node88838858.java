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


package gr.auth.ee.dsproject.node;

import gr.auth.ee.dsproject.pacman.*;

public class Node88838858
{
	
  //�� ���������� ��� ������ ���.
  int nodeX;
  int nodeY;
  public int nodeMove;
  public double nodeEvaluation;
  int[][] currentGhostPos;
  int[][] flagPos;
  boolean[] currentFlagStatus;
  Room[][] Maze;
  
  
//����� constructor.
  public Node88838858 () // Constructor
  {
	  nodeX=0;
	  nodeY=0;
	  nodeMove=0;
	  nodeEvaluation=0;
	  Maze=new Room[PacmanUtilities.numberOfRows][PacmanUtilities.numberOfColumns];
	  currentGhostPos=new int[PacmanUtilities.numberOfGhosts][2];
	  flagPos=new int[PacmanUtilities.numberOfFlags][2];
	  currentFlagStatus=new boolean[PacmanUtilities.numberOfFlags];
	  for (int i=0;i<4;i++) {
		  flagPos[i][0]=0;
		  flagPos[i][1]=0;
		  currentFlagStatus[i]=false;
		  currentGhostPos[i][0]=0;
		  currentGhostPos[i][1]=0;
	  }
  }
  
  //Constructor �������� ����� �� ����������.
  public Node88838858 (int nX, int nY, int nM, Room[][] M) // Constructor
  {
	  nodeX=nX;
	  nodeY=nY;
	  nodeMove=nM;
	  Maze=M;
	  currentGhostPos=new int[PacmanUtilities.numberOfGhosts][2];
	  currentGhostPos=findGhosts(M);
	  flagPos=new int[PacmanUtilities.numberOfFlags][2];
	  flagPos=findFlags(M);
	  currentFlagStatus=new boolean[PacmanUtilities.numberOfFlags];
	  currentFlagStatus=checkFlags(M);
	  nodeEvaluation=evaluate();
  }

  //� ������� ���� ������� ��� ������������� ���� ��� ����������� ��� Maze, ��������������� ��� ��� ������� ����������
  private int[][] findGhosts (Room[][] PlayArea)
  {
	  //�� num ��������� ���� ���� ��������� ��� ��������.
	  int num=0;
	  int[][] pos = new int[PacmanUtilities.numberOfGhosts][2];
	  for (int i=0;i<PacmanUtilities.numberOfRows;i++) {
		  for (int j=0;j<PacmanUtilities.numberOfColumns;j++) {
			  if (PlayArea[i][j].isGhost()==true) {
				  pos[num][0]=i;
				  pos[num][1]=j;
				  num++;
			  }
		  }
	  }
	  return pos;
  }

  //� ������� ���� ������� ��� ������������� ���� ��� ������� ��� Maze ��������������� ��� ��� ������� ����������.
  private int[][] findFlags (Room[][] PlayArea)
  {
	  //�� num2 ��������� ���� ���� ��������� ��� ������.
	  int num2=0;
	  int[][] pos=new int[PacmanUtilities.numberOfFlags][2];
	  for (int i=0;i<PacmanUtilities.numberOfRows;i++) {
		  for (int j=0;j<PacmanUtilities.numberOfColumns;j++) {
			  if (PlayArea[i][j].isFlag()==true) {
				  pos[num2][0]=i;
				  pos[num2][1]=j;
				  num2++;
			  } 
		  }
	  }
	  return pos;
  }

  //� ������� ���� ������� ����� ��� ��� ������� ��� ������� ���� ��� ������������ �������, ����� ��� ���������� ��� ��� ������.
  private boolean[] checkFlags (Room[][] PlayArea)
  {
	  boolean[] status=new boolean[PacmanUtilities.numberOfFlags];
	  for (int i=0;i<4;i++) {
		  
		  //�� ������������� ��� ����� ��� PlayArea ������� ��� ������������� ��� �������.
		  if (PlayArea[findFlags(PlayArea)[i][0]][findFlags(PlayArea)[i][1]].isCapturedFlag()==true) {
			  status[i]=true;
		  }
		  else {
			  status[i]=false;
		  }
		  
	  }
	  return status;
  }

  //� ������� ����, ��������� �� ���� ���� ����� ��� ������ �� ���� ������� ��������, ��� ���������� ��� ���������� ���. ��� �������� �����
  //� ������, ���� ����������� �� ����� ����� � �������.
  public double evaluate ()
  {
    double evaluation = 0;
    
    //��� ��� ���������� West.
    if (nodeMove==0) {
    	
    	//������� ���� ��� �������, ��� �� ������� ������ ��� ��������� ������ ��� pacman, ��������� �� evaluation ������� �� ��� �������� ��� ��� ����.
    	for (int i=0;i<PacmanUtilities.numberOfFlags;i++) {
    		
    		//��� ������� ��������� ������ � ������ �� ��� ���� ��� ����������. ��������� �������� ���� ��� ��� ������, ���� ��� �� evaluation<=0, ����� ��
    		//����������� ���������, �� �������� ��� ������� ���� ��� ���������� ���� ��� ���� ��� �������� ���� ���� ����������, ����� ����������
    		//�������� �� ����� ��� ��������� �� ����� ���� ���.
    		if (flagPos[i][1]<nodeY && currentFlagStatus[i]==false && evaluation<=0) {
    			
    			//�������� ������������� � ����� ��� ����������.
    			evaluation=evaluation+(100-(nodeY-flagPos[i][1]));
    		}
    	}
    	
    	//������� ��� �� ����������, ��� ��� ���� �������� ��� ����� ������ ��� pacman ��� ���� ���� ������ �� ����� ��������� �� evaluation ������� �� ��� �������� ���
    	//��� �����.
    	for (int i=0;i<PacmanUtilities.numberOfGhosts;i++) {
    		if (currentGhostPos[i][1]<nodeY && currentGhostPos[i][0]==nodeX) {
    			
    			//�������� ������������� � ����� ��� ����������.
    			evaluation=evaluation-(100-Math.abs(nodeY-currentGhostPos[i][1]));
    		}
    		//�������� ��������� �� ����� �������� ������� �������� ��� pacman ���� ��� ������ ����������, ��� �� �����, ��������� ������� �� evaluation. ����� � ������� 
    		//������� ���� �� ��� ������� ��� ���� ��� ������ ���� ����� ��� ����������, ���� �� ��� ������ indexOutOfBounds error.
    		if (nodeY!=0 && currentGhostPos[i][1]==nodeY-1) {
    			if (currentGhostPos[i][0]==nodeX-1 || currentGhostPos[i][0]==nodeX+1) {
    				evaluation=evaluation-98;
    			}
    		}
    	}
    	//������ ��������� �� � ���� ������ ��� pacman ����� ����� �� �����, ��� �� ����� ��������� �� evaluation, �������� ���� ��� �� �� ���� �������� ����� ���.
    	//���� �� ������� ����� �� �������� ����� �� �������, ����� ���� ������ �� ����������� ��� �� ����������.
    	if (nodeY!=0 && Maze[nodeX][nodeY-1].walls[nodeMove]==0) {
			evaluation=evaluation-95;
		}
    	
    	//� ������� ��� ����������� � ��������� ��� �� evaluation ����� ������������ �� ���� �������, ����� ������� �������� ����� ��� ���������� ��� �����. �� �����, �� ��
    	//�������� �� ���������� ����� ��� ��������� ��� �� �� ������ ��� ��� ������� ������.
    }
    
    //� ������� ����� ������� � ���� ��� ��� ��� ����� ����� ������������, �� �� INDEX ������ �� �������� ������� �� ���
    //����������.
    
    //��� ��� ���������� South.
    if (nodeMove==1) {
    	for (int i=0;i<PacmanUtilities.numberOfFlags;i++) {
    		if (flagPos[i][0]>nodeX && currentFlagStatus[i]==false && evaluation<=0) {
    			evaluation=evaluation+(100-(flagPos[i][0]-nodeX));
    		}
    	}
    	
    	for (int i=0;i<PacmanUtilities.numberOfGhosts;i++) {
    		if (currentGhostPos[i][0]>nodeX && currentGhostPos[i][1]==nodeY) {
    			evaluation=evaluation-(100-Math.abs(currentGhostPos[i][0]-nodeX));
    		}
    		if (nodeX!=PacmanUtilities.numberOfRows && currentGhostPos[i][0]==nodeX+1) {
    			if (currentGhostPos[i][1]==nodeY-1 || currentGhostPos[i][1]==nodeY+1) {
    				evaluation=evaluation-98;
    			}
    		}
    	}
    	if (nodeX!=PacmanUtilities.numberOfRows && Maze[nodeX+1][nodeY].walls[nodeMove]==0) {
			evaluation=evaluation-95;
		}
    }
    
    //��� ��� ���������� East.
    if (nodeMove==2) {
    	for (int i=0;i<PacmanUtilities.numberOfFlags;i++) {
    		if (flagPos[i][1]>nodeY && currentFlagStatus[i]==false && evaluation<=0) {
    			evaluation=evaluation+(100-(flagPos[i][1]-nodeY));
    		}
    	}
    	
    	for (int i=0;i<PacmanUtilities.numberOfGhosts;i++) {
    		if (currentGhostPos[i][1]>nodeY && currentGhostPos[i][0]==nodeX) {
    			evaluation=evaluation-(100-Math.abs(currentGhostPos[i][1]-nodeY));
    		}
    		if (nodeY!=PacmanUtilities.numberOfColumns && currentGhostPos[i][1]==nodeY+1) {
    			if (currentGhostPos[i][0]==nodeX-1 || currentGhostPos[i][0]==nodeX+1) {
    				evaluation=evaluation-98;
    			}
    		}
    	}
    	if (nodeY!=PacmanUtilities.numberOfColumns && Maze[nodeX][nodeY+1].walls[nodeMove]==0) {
			evaluation=evaluation-95;
		}
    }
    
    //��� ��� ���������� North.
    if (nodeMove==3) {
    	for (int i=0;i<PacmanUtilities.numberOfFlags;i++) {
    		if (flagPos[i][0]<nodeX && currentFlagStatus[i]==false && evaluation<=0) {
    			evaluation=evaluation+(100-(nodeX-flagPos[i][0]));
    		}
    	}
    	
    	for (int i=0;i<PacmanUtilities.numberOfGhosts;i++) {
    		if (currentGhostPos[i][0]<nodeX && currentGhostPos[i][1]==nodeY) {
    			evaluation=evaluation-(100-Math.abs(nodeX-currentGhostPos[i][0]));
    		}
    		if (nodeX!=0 && currentGhostPos[i][0]==nodeX-1) {
    			if (currentGhostPos[i][1]==nodeY-1 || currentGhostPos[i][1]==nodeY+1) {
    				evaluation=evaluation-98;
    			}
    		}
    	}
    	if (nodeX!=0 && Maze[nodeX-1][nodeY].walls[nodeMove]==0) {
			evaluation=evaluation-95;
		}
    }
    
    //��� debugging.
    System.out.println("Move" + nodeMove + " " + evaluation);
    return evaluation;

  }

}
