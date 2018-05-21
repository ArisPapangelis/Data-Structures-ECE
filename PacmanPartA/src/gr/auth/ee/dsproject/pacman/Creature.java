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
  /**Ονοματεπώνυμο: Άρης-Ελευθέριος Παπαγγέλης
   * ΑΕΜ:8883
   * Τηλέφωνο:6946126130
   * Email:aris.papagelis@gmail.com
   * 
   * Ονοματεπώνυμο: Μιχαήλ Μηναδάκης
   * ΑΕΜ:8858
   * Τηλέφωνο:6972464204
   * Email:Thejokergr@hotmail.gr
   */
  public int[] calculateNextGhostPosition (Room[][] Maze, int[][] currentPos)
  {
	  //Ο πινακας που θα επιστρεψει η συναρτηση που υλοποιουμε. Περιεχει τις νεες διευθυνσεις που θα κινηθει το καθε φαντασμα.
	  int[] ghostMoves=new int[PacmanUtilities.numberOfGhosts];
	  
	  //Δεικτης.
	  int i;
	  
	  /*Αρχικοποιω με τιμες τον πινακα ghostMoves ωστε να μπορει
	   *να εισαχθει σαν ορισμα στην συναρτηση checkCollision, ωστε να μην υπαρχει
	   *αμφιβολια για τις τιμες του πινακα.*/
	  for (i=0;i<PacmanUtilities.numberOfGhosts;i++) {
		  ghostMoves[i]=0;	  
	  }
	  
	  //Ξανακανω το i ισο με 0 ωστε να μπορει να χρησιμοποιηθει για το while loop που ακολουθει.
	  i=0;
	  
	  /*Μεσα στο while loop, θετουμε μια random τιμη απο 0 εως 3, αρχικα για την πρωτη θεση του πινακα ghostMoves, δηλαδη τη διευθυνση που ενδεχεται να κινηθει 
	   * το πρωτο φαντασμα. Αν αυτη η τιμη ειναι επιτρεπτη, δηλαδη αν αφενος δεν υπαρχει τοιχος και αφετερου δεν προκειται να υπαρξει συγκρουση με αλλο φαντασμα,
	   * τοτε αυτη η τιμη γινεται δεκτη και προχωραμε στον ιδιο υπολογισμο και για το επομενο φαντασμα. Σε αντιθετη περιπτωση, το i=i+1 δε θα εκτελεστει  
	   * και ο υπολογισμος θα επαναληφθει για το τωρινο φαντασμα,μεχρι να παρουμε καποια εγκυρη κινηση. Το loop τερματιζει οταν υπολογιστουν οι διευθυνσεις 
	   * κινησης για ολα τα φαντασματα, ο αριθμος των οποιων οριζεται  απο την PacmanUtilities.numberOfGhosts*/
	  while (i<PacmanUtilities.numberOfGhosts) {
		  ghostMoves[i]=(int) (4*Math.random());
		  if (Maze[currentPos[i][0]][currentPos[i][1]].walls[ghostMoves[i]]==1) {
			  if (checkCollision(ghostMoves,currentPos)[i]==false) {
				  i++;
			  }  
		  }
	  }
	  
	  //Επιστροφη τιμης.
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
