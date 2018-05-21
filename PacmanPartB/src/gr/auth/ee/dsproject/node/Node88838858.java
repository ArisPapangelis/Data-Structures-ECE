  /**Ονοματεπώνυμο: ’ρης-Ελευθέριος Παπαγγέλης
   * ΑΕΜ:8883
   * Τηλέφωνο:6946126130
   * Email:aris.papagelis@gmail.com
   * 
   * Ονοματεπώνυμο: Μιχαήλ Μηναδάκης
   * ΑΕΜ:8858
   * Τηλέφωνο:6972464204
   * Email:Thejokergr@hotmail.gr
   */


package gr.auth.ee.dsproject.node;

import gr.auth.ee.dsproject.pacman.*;

public class Node88838858
{
	
  //Οι μεταβλητες της κλασης μας.
  int nodeX;
  int nodeY;
  public int nodeMove;
  public double nodeEvaluation;
  int[][] currentGhostPos;
  int[][] flagPos;
  boolean[] currentFlagStatus;
  Room[][] Maze;
  
  
//Κενος constructor.
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
  
  //Constructor αναθεσης τιμων σε μεταβλητες.
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

  //Η μεθοδος αυτη βρισκει τις συντεταγμενες ολων των φαντασματων στο Maze, προσπελαυνοντας ολη την περιοχη παιχνιδιου
  private int[][] findGhosts (Room[][] PlayArea)
  {
	  //Το num αυξανεται μονο οταν βρισκουμε ενα φαντασμα.
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

  //Η μεθοδος αυτη βρισκει τις συντεταγμενες ολων των σημαιων στο Maze προσπελαυνοντας ολη την περιοχη παιχνιδιου.
  private int[][] findFlags (Room[][] PlayArea)
  {
	  //Το num2 αυξανεται μονο οταν βρισκουμε μια σημαια.
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

  //Η μεθοδος αυτη βρισκει ποιες απο τις σημαιες που βρηκαμε μεσω της προηγουμενης μεθοδου, εχουν ηδη κατακτηθει απο τον Πακμαν.
  private boolean[] checkFlags (Room[][] PlayArea)
  {
	  boolean[] status=new boolean[PacmanUtilities.numberOfFlags];
	  for (int i=0;i<4;i++) {
		  
		  //Ως συντεταγμενες στα κελια του PlayArea βαζουμε τις συντεταγμενες των σημαιων.
		  if (PlayArea[findFlags(PlayArea)[i][0]][findFlags(PlayArea)[i][1]].isCapturedFlag()==true) {
			  status[i]=true;
		  }
		  else {
			  status[i]=false;
		  }
		  
	  }
	  return status;
  }

  //Η μεθοδος αυτη, αξιολογει το ποσο καλη ειναι μια κινηση με βαση διαφορα κριτηρια, και επιστρεφει την αξιολογηση της. Οσο καλυτερη ειναι
  //η κινηση, τοσο μεγαλυτερος θα ειναι αυτος ο αριθμος.
  public double evaluate ()
  {
    double evaluation = 0;
    
    //Για την κατευθυνση West.
    if (nodeMove==0) {
    	
    	//Κοιταμε ολες τις σημαιες, και αν υπαρχει σημαια που βρισκεται δυτικα του pacman, αυξανουμε το evaluation αναλογα με την αποσταση του απο αυτη.
    	for (int i=0;i<PacmanUtilities.numberOfFlags;i++) {
    		
    		//Στη συνθηκη ελεγχουμε επισης η σημαια να μην εχει ηδη κατακτηθει. Ελεγχουμε επιπλεον μονο για μια σημαια, εξου και το evaluation<=0, διοτι σε
    		//διαφορετικη περιπτωση, αν υπαρχουν δυο σημαιες προς την κατευθυνση αυτη και μονο ενα φαντασμα στην ιδια κατευθυνση, δινει υπερβολικη
    		//βαρυτητα σε αυτες και ενδεχεται να πεσει πανω του.
    		if (flagPos[i][1]<nodeY && currentFlagStatus[i]==false && evaluation<=0) {
    			
    			//Αποσταση συντεταγμενων Υ εντος της παρενθεσης.
    			evaluation=evaluation+(100-(nodeY-flagPos[i][1]));
    		}
    	}
    	
    	//Κοιταμε ολα τα φαντασματα, και για καθε φάντασμα που είναι δυτικά του pacman και στην ιδια γραμμη με αυτον μειώνουμε το evaluation αναλογα με την αποσταση του
    	//απο αυτον.
    	for (int i=0;i<PacmanUtilities.numberOfGhosts;i++) {
    		if (currentGhostPos[i][1]<nodeY && currentGhostPos[i][0]==nodeX) {
    			
    			//Αποσταση συντεταγμενων Υ εντος της παρενθεσης.
    			evaluation=evaluation-(100-Math.abs(nodeY-currentGhostPos[i][1]));
    		}
    		//Επιπλεον ελεγχουμε αν ειναι φάντασμα ακριβώς διαγώνια του pacman προς την δυτική κατευθυνση, και αν ειναι, μειωνουμε αναλογα το evaluation. Αυτος ο ελεγχος 
    		//γίνεται μονο αν δεν ειμαστε στα ακρα του πινακα προς αυτην την κατευθυνση, ώστε να μην έχουμε indexOutOfBounds error.
    		if (nodeY!=0 && currentGhostPos[i][1]==nodeY-1) {
    			if (currentGhostPos[i][0]==nodeX-1 || currentGhostPos[i][0]==nodeX+1) {
    				evaluation=evaluation-98;
    			}
    		}
    	}
    	//Επισης ελεγχουμε αν η θέση δυτικα του pacman ειναι διπλα σε τοίχο, και αν ειναι μειώνουμε το evaluation, λιγότερο όμως από το αν είχε φάντασμα δίπλα του.
    	//Αυτό το κάνουμε διότι αν κινείται δίπλα σε τοίχους, είναι πολύ πιθανό να εγκλωβιστεί απο τα φαντάσματα.
    	if (nodeY!=0 && Maze[nodeX][nodeY-1].walls[nodeMove]==0) {
			evaluation=evaluation-95;
		}
    	
    	//Ο αριθμος που προσθετουμε ή αφαιρουμε απο το evaluation ειναι διαφορετικος σε καθε συνθηκη, διοτι καποιες συνθηκες ειναι πιο σημαντικες απο αλλες. Ας πουμε, το να
    	//αποφυγει τα φαντασματα ειναι πιο σημαντικο απο το να πιασει την πιο κοντινη σημαια.
    }
    
    //Η ΑΝΑΛΥΣΗ ΕΙΝΑΙ ΑΚΡΙΒΩΣ Η ΙΔΙΑ ΚΑΙ ΓΙΑ ΤΙΣ ΑΛΛΕΣ ΤΡΕΙΣ ΚΑΤΕΥΘΥΝΣΕΙΣ, ΜΕ ΤΑ INDEX ΦΥΣΙΚΑ ΝΑ ΑΛΛΑΖΟΥΝ ΑΝΑΛΟΓΑ ΜΕ ΤΗΝ
    //ΚΑΤΕΥΘΥΝΣΗ.
    
    //Για την κατευθυνση South.
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
    
    //Για την κατευθυνση East.
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
    
    //Για την κατευθυνση North.
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
    
    //Για debugging.
    System.out.println("Move" + nodeMove + " " + evaluation);
    return evaluation;

  }

}
