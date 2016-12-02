import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class Maze {
	
	private Random random;
	private MazeNode[][] maze;
	private int size;

	public Maze(int size) {
		random = new Random();
		this.size = size;
		//initialize maze with specified size
		maze = new MazeNode[size][size];
		for(int row = 0; row < size; row++)
			for(int col = 0; col < size; col++){
				maze[row][col] = new MazeNode(row, col);
				if(row == 0 & col == 0)
					maze[row][col].hasNorthWall = false;
				if(row == size - 1 & col == size - 1)
					maze[row][col].hasSouthWall = false;
			}	
		generatePerfectMaze();
	}
	
	//creates a perfect maze
	//needs to be fixed
	public void generatePerfectMaze(){
		Stack<MazeNode> stack = new Stack<MazeNode>();
		int totalCells = size * size;
		MazeNode currentNode = maze[0][0]; //start at upper left hand corner
		currentNode.setVisited();
		int visitedCells = 1;
		//Runs until all cells have been visited
		while(visitedCells < totalCells){
			ArrayList<MazeNode> neighbors = getValidNeighbors(currentNode);
			//System.out.println(this);
			if(!neighbors.isEmpty()){
				//randomly choose an index
				int index = random.nextInt(neighbors.size());
				MazeNode node = neighbors.get(index);
				currentNode.knockDownWall(node);
				stack.push(currentNode);
				currentNode = node;
				currentNode.setVisited();
				visitedCells ++;
				
			}
			else if(!stack.isEmpty())
				currentNode = stack.pop();
		}
		
	}
	
	
	//returns an ArrayList of the neighbors that have all its walls intact
	public ArrayList<MazeNode> getValidNeighbors(MazeNode node){
		ArrayList<MazeNode> neighbors = new ArrayList<MazeNode>();
		int row = node.row;
		int col = node.col;
		//top neighbor
		if(row - 1 >=0 && maze[row - 1][col].allWallsIntact() && !maze[row - 1][col].wasVisited())
			neighbors.add(maze[row - 1][col]);
		//bottom neighbor
		if(row + 1 < size && maze[row + 1][col].allWallsIntact() && !maze[row + 1][col].wasVisited())
			neighbors.add(maze[row + 1][col]);
		//west neighbor
		if(col - 1 >= 0 && maze[row][col - 1].allWallsIntact() && !maze[row][col - 1].wasVisited())
			neighbors.add(maze[row][col - 1]);
		//east neighbor
		if(col + 1 < size && maze[row][col + 1].allWallsIntact() && !maze[row][col + 1].wasVisited())
			neighbors.add(maze[row][col + 1]);
		return neighbors;
		
	}
	
	@Override
	public String toString(){
		String[][] chars = new String[size*2 + 1][size*2 + 1];
		String s = "";
		for(int row = 0; row < chars.length; row ++){
			for(int col = 0; col < chars.length; col ++){
				MazeNode node = null;
				if(row % 2 == 1 && col % 2 == 1)
					node = maze[row /2][col/2];
				//represents corners
				if((row == 0 && col == 1) || (row == size*2 && col == size*2 - 1))
					chars[row][col] = " ";
				else if(chars[row][col] == null && row % 2 == 0 && col % 2 == 0)
					chars[row][col] = "+";
				//represents top and bottom row
				else if(row == 0 || row == chars.length - 1)
					chars[row][col] = "-";
				//represents leftmost and rightmost column
				else if(row % 2 == 1 && (col == 0 || col == chars[0].length - 1))
					chars[row][col] = "|";
				else if(node != null){
					if(node.hasWestWall)
						chars[row][col - 1] = "|";
					if(node.hasEastWall)
						chars[row][col + 1] = "|";
					if(node.hasNorthWall)
						chars[row - 1][col] = "-";
					if(node.hasSouthWall)
						chars[row + 1][col] = "-";
					chars[row][col] = " ";
				}
				else 
					chars[row][col] = " ";
			}
		}
		for(int row = 0; row < chars.length; row ++){
			for(int col = 0; col < chars.length; col ++)
				s += chars[row][col];
			s += "\n";
		}
		return s;
	
	}

	/**Represents a vertex in a maze. Can have a maximum of 4 neighbors*/
	 class MazeNode {
		int row;
		int col;
		boolean hasNorthWall;
		boolean hasSouthWall;
		boolean hasEastWall;
		boolean hasWestWall;
		boolean wasVisited;
		ArrayList<MazeNode> neighbors;

		//node references are initially null
		//null reference represents a wall
		public MazeNode(int r, int c) {
			neighbors = new ArrayList<MazeNode>();
			wasVisited = false;
			hasWestWall = hasEastWall = hasNorthWall = hasSouthWall = true;
			row = r;
			col = c;
		
		}
		
		public void setVisited(){
			wasVisited = true;
		}
		
		public boolean wasVisited(){
			return wasVisited;
		}
		
		//true if all walls are null
		public boolean allWallsIntact(){
			return neighbors.size() == 0;
		}

		//equal if in the same location 
		@Override
		public boolean equals(Object o){
			if(o == null)
				return false;
			MazeNode other = (MazeNode)o;
			return other.row == row && other.col == col;
		}
		
		@Override
		public String toString(){
			return "(" + row + ", " + col + ")"; 
		}
		
		//sets the references of nodes to each other
		public void knockDownWall(MazeNode node){
			//node is westNeighbor
			if(node.row == row && node.col == col - 1){
				hasWestWall = false;
				node.hasEastWall = false;
				neighbors.add(node);
				node.neighbors.add(this);
			}
			else if(node.row == row && node.col == col + 1){
				hasEastWall = false;
				node.hasWestWall = false;
				neighbors.add(node);
				node.neighbors.add(this);
			}
			else if(node.col == col && node.row == row - 1){
				hasNorthWall = false;
				node.hasSouthWall = false;
				neighbors.add(node);
				node.neighbors.add(this);
			}
			else if(node.col == col && node.row == row + 1){
				hasSouthWall = false;
				node.hasNorthWall = false;
				neighbors.add(node);
				node.neighbors.add(this);
			}
		}
	}
	
	public static void main(String[] args){
		Maze maze = new Maze(5);
		System.out.println(maze);
	
	}

}
