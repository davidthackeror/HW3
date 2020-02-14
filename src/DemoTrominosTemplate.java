import java.awt.*;
import java.util.Random;

/***************************************************************************
 * DemoTrominos - simple program for tiling a 2^k x 2^k board with Trominos
 *
 * YOU JUST NEED TO FINISH THE CODE FOR THE containsForbidden() AND doTiling() METHODS
 *
 * Author: C2C David J Thacker
 *
 */
public class DemoTrominosTemplate {

   /************************************************************************
    * main() - randomly places the forbidden tile, creates an TrominoTiling
    *    object of the specified size with the forbidden tile, and solves
    *    the tiling.
    */
   public static void main(String[] args) {
   
      int SIZE = 4;    // must be a power of 2
      
      // randomly place the forbidden tile
      
      Random rand = new Random();
      
      int forbiddenRow = rand.nextInt(SIZE);
      int forbiddenCol = rand.nextInt(SIZE);
      
      // create the TrominoTiling object
      
      TrominoTiling myTrominos = new TrominoTiling(SIZE, 
                                          forbiddenRow, forbiddenCol);
      
      myTrominos.solve();  // solve the tiling
      
   }  // end main()
 
 } // end DemoTrominos class
 
/***************************************************************************
 * TrominoTiling class - a tiling solution using Trominos with graphical
 *    presentation.
 */ 
class TrominoTiling {      

   private final int BOARD_SIZE = 512;  // size of the graphic window
   
   private int cellSize;  // cell size based upon board size and # of cells
   
   private boolean trominoBoard[][];  // tracks open versus forbidden cells
      
   private int size;  // number of cell rows (and columns), must be power of 2
   
   private DrawingPanel panel;  // Drawing panel for graphical display
   private Graphics2D g;        // Graphics2D object for drawing
   
   private Random rand = new Random();  // Random object for random colors
      
   /***********************************************************************
    * TrominoTiling constructor - sets size and forbidden cell location
    */
   TrominoTiling( int size, int forbiddenRow, int forbiddenCol ) {
      
      this.setSize(size);  // set the size
      
      // set cell size based upon board size and number of cells
      
      this.cellSize = this.BOARD_SIZE / this.getSize();
         
      // allocate the underlying 2D array
      
      this.trominoBoard = new boolean[this.getSize()][this.getSize()];
         
      // place the forbidden cell
      
      this.setForbiddenCell(forbiddenRow,forbiddenCol);
        
   }
   
   /***********************************************************************
    * setSize() - sets the number of rows (and columns).  Must be a power
    *    of 2 between 2 and 512.  
    *
    * @throws - Throws IllegalArgumentException if size is not a power of
    *           two between 2 and 512.
    */   
   void setSize( int newSize ) {
      
      if ( (newSize == 2) || (newSize == 4) || (newSize == 8) ||
         (newSize == 16) || (newSize == 32) || (newSize == 64) || 
         (newSize == 128) || (newSize == 256) || (newSize == 512) ) {
         this.size = newSize;           
      } else {          
         throw new IllegalArgumentException(
                                 "Board size must be a power of 2.");           
      }
   }  // end setSize()

   /***********************************************************************
    * getSize() - getter method for size attribute
    */
   int getSize() { return this.size; }
   
   /***********************************************************************
    * setForbiddenCell() - sets the location of the forbidden cell
    *
    * @throws - IllegalArgumentException if location is not on the board
    */    
   void setForbiddenCell( int row, int col ) {
      
      if ( (row < 0) || (row >= this.getSize()) ) {
         throw new IllegalArgumentException(
                           "Forbidden row value must be on the board.");
      }
         
      if ( (col < 0) || (col >= this.getSize()) ) {
         throw new IllegalArgumentException(
                           "Forbidden col value must be on the board.");
      }
         
      this.trominoBoard[row][col] = true;  // set true for forbidden
      
   } // end setForbiddenCell()
      
   /***********************************************************************
    * solve() - top-level method for solving the Tromino tiling.  Creates
    *    the graphical representation and shows the grid.  Then calls the
    *    recursive doTiling() method to create the tiling.
    */  
   void solve() {
       
       // establish the graphical display
       
       this.panel = new DrawingPanel( this.BOARD_SIZE, this.BOARD_SIZE );
	   this.panel.setBackground(Color.WHITE);
	   this.g = panel.getGraphics();
       this.g.setColor(Color.BLACK);
       
       // draw the grid
       
       for(int x=this.cellSize; x<this.BOARD_SIZE; x += this.cellSize) 
         for(int y=this.cellSize; y<this.BOARD_SIZE; y += this.cellSize) 
         {
            g.drawLine(x,0,x,this.BOARD_SIZE-1);
            g.drawLine(0,y,this.BOARD_SIZE-1,y);
         }
         
       panel.copyGraphicsToScreen();  // display the grid
       
       // tile the entire board using this recursive routine
       
       doTiling( 0, this.getSize()-1, 0, this.getSize()-1 );                  
                                       
   } // end solve()
   
   /***********************************************************************
    * containsForbidden() - checks the sub-grid of [startRow..endRow] X 
    *    [startCol..endCol] to see if it contains a forbidden cell
    */      
   boolean containsForbidden( int startRow, int endRow, 
                              int startCol, int endCol ) {
   
      boolean hasForbidden = false;  // assume no forbidden

      for (int i = startRow; i < endRow; i++) {
         for (int j = startCol; j < endCol; j++) {
            if (this.trominoBoard[i][j]) {
               hasForbidden = true;
               break;
            }
         }
      }
	  
      return hasForbidden;  // return results
      
   } // end containsForbidden()
   
   /***********************************************************************
    * doTiling() - main recursive method that implements the divide-and-
    *    conquer algorithm for doing the tiling.
    */
   void doTiling( int startRow, int endRow, int startCol, int endCol ) {
   
      // set a random color for this tromino
      
      Color trominoColor = new Color(rand.nextInt(255),
                           rand.nextInt(255),rand.nextInt(255));
 
      int size = (endRow+1)-startRow;  // determine sub-grid size

//      if (subArray.dimension == 2) {
//         place tromino in the three free cells
      if((endRow-startRow)+(endCol- startCol) == 2){
         for (int i = 0; i < endRow-startRow + 1; i++) {
            for (int j = 0; j < endCol-startCol + 1; j++) {
               if (!trominoBoard[startRow+i][startRow+j]){
                  g.setColor(trominoColor);
                  g.fillRect(cellSize*(startRow+i), cellSize*(startCol+j), cellSize, cellSize);
                  panel.copyGraphicsToScreen();
               }
            }
         }
      }
      else{

      }
//      } else {
//         for each of four quarter subArrays {
//            create next quarter subArray
//            if (quarterSubArray contains a forbidden cell) {
//               placeTrominos(array, quarterSubArray)
//            } else {
//               put a forbidden cell into the quarterSubArray where it connects to the others
//               placeTrominos(array, quarterSubArray)
//               replace the added forbidden cell with part of a new tromino
//            }
//         }
//      }
//   }


} // end doTiling()
   
} // end TrominoTiling class