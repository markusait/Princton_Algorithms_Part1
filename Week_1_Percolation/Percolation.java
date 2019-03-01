import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {

    public int[][] grid;
    public int size;
    public int topParent;
    public int bottomParent;
    public WeightedQuickUnionUF WQUF;
    public int openSites;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        size = n;
        topParent = 0;
        bottomParent = n*n + 1;
        grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0;
            }
        }
        //for first and last element
        WQUF = new WeightedQuickUnionUF(bottomParent + 1);
        unionBottonAndTopRow();
    }

    public void unionBottonAndTopRow(){
        for(int i=1; i < size+1; i++){
            WQUF.union(topParent,i);
            WQUF.union(bottomParent,bottomParent - i);
        }
    }
    public void test(){
        System.out.println(WQUF.connected(7,10));
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) throws IllegalAccessException {
        if( row > size || col > size || row < 1 || col < 1 ) throw new IllegalAccessException();
        int adjustedRow = row - 1;
        int adjustedCol = col - 1;
        grid[adjustedRow][adjustedCol] = 1;
        unionToNeighbours(adjustedRow,adjustedCol);
        openSites++;
    }

    public void unionToNeighbours(int adjustedRow,int adjustedCol){
        int[][] moves = {{-1,0},{0,1},{1,0},{0,-1}};
        for (int[] move : moves) {
            int neighbourRow = adjustedRow + move[0];
            int neighbourCol = adjustedCol + move[1];
            int elementIdx = getGridIndex(adjustedRow, adjustedCol);
            int neighbourIdx = getGridIndex(neighbourRow, neighbourCol);
            if(checkValidNeighbour(neighbourRow,neighbourCol) && elementIdx != neighbourIdx ){
                WQUF.union(elementIdx, neighbourIdx);
            }
        }
    }

    public boolean checkValidNeighbour(int neighbourRow, int neighbourCol ){
        if (neighbourRow >= 0 && neighbourRow < size &&
                neighbourCol >= 0 && neighbourCol < size &&
                grid[neighbourRow][neighbourCol] == 1){
            return true;
        } else {
            return false;
        }

    }

    public int getGridIndex(int row, int col){
        return (row * size) + col + 1;
    }
    //returns not adjusted values
    public int[] getRowCol(int idx){
        int row;
        int col;
        row = idx / size;
        if(idx % size != 0) row++;
        col = idx % size;
        int[] res = {row ,col};
        return res;
    }

    // number of open sites
    public int numberOfOpenSites()  {
        return openSites;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col){
        int adjustedRow = row - 1;
        int adjustedCol = col - 1;
        return grid[adjustedRow][adjustedCol] == 1;
    }
    // is site (row, col) full?
    public boolean isFull(int row, int col){
        int adjustedRow = row - 1;
        int adjustedCol = col - 1;
        int gridIdx = getGridIndex(adjustedRow, adjustedCol);
        System.out.println(gridIdx);
        return WQUF.connected(0,gridIdx);
    }
    // does the system percolate?
    public boolean percolates(){
        return WQUF.connected(0,bottomParent);
    }

    public void print(){
        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size ; j++) {
                int site = grid[i][j];
                if(site == 1){
                    System.out.print("1");
                } else {
                    System.out.print("0");
                }
            }
            System.out.println();
        }
    }

}
