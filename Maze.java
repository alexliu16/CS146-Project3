import java.util.*;

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
			for(int col = 0; col < size; col++)
				maze[row][col] = new MazeNode(row, col);

		generatePerfectMaze();
	}
	
	//creates a perfect maze
	//needs to be fixed
	public void generatePerfectMaze(){
		Stack<MazeNode> stack = new Stack<MazeNode>();
		int totalCells = size * size;
		MazeNode currentNode = maze[0][0]; //start at upper left hand corner
		int visitedCells = 1;
		//Runs until all cells have been visited
		while(visitedCells < totalCells){
			ArrayList<MazeNode> neighbors = getNeighborsWithAllWalls(currentNode);
			//System.out.println(neighbors.size() + "\n");
			if(!neighbors.isEmpty()){
				//randomly choose an index
				int index = random.nextInt(neighbors.size());
				MazeNode node = neighbors.get(index);
				currentNode.setNeighbor(node);
				stack.push(currentNode);
				currentNode = node;
				visitedCells ++;
			}
			else
				currentNode = stack.pop();
		}
		
	}
	
	//returns an ArrayList of the neighbors that have all its walls intact
	public ArrayList<MazeNode> getNeighborsWithAllWalls(MazeNode node){
		ArrayList<MazeNode> neighbors = new ArrayList<MazeNode>();
		int row = node.row;
		int col = node.col;
		//top neighbor
		if(row - 1 >=0 && maze[row - 1][col].allWallsIntact())
			neighbors.add(maze[row - 1][col]);
		//bottom neighbor
		if(row + 1 < size && maze[row + 1][col].allWallsIntact())
			neighbors.add(maze[row + 1][col]);
		//west neighbor
		if(col - 1 >= 0 && maze[row][col - 1].allWallsIntact())
			neighbors.add(maze[row][col - 1]);
		//east neighbor
		if(col + 1 < size && maze[row][col + 1].allWallsIntact())
			neighbors.add(maze[row][col + 1]);
		return neighbors;
		
	}
	
	@Override
	public String toString(){
		String[][] chars = new String[size*2 - 1][size*2 - 1];
		String s = "";
		for(int row = 0; row < chars.length; row ++){
			for(int col = 0; col < chars.length; col ++){
				MazeNode node = maze[row/2][col/2];
				if(chars[row][col] == null && row % 2 == 0 && col % 2 == 0)
					chars[row][col] = "+";
				else if(chars[row][col] == null && row % 2 == 1 && col % 2 == 0)
					chars[row][col] = "|";
				else if(chars[row][col] == null && row % 2 == 0)
					chars[row][col] = "-";
				else
					chars[row][col] = " ";
				
				if(col % 2 ==0 && row % 2 == 0 && row != 0 && node.north != null )
					chars[row - 1][col] = " ";
				else if(col % 2 ==0 && row % 2 == 0 && row < size * 2 && node.south != null )
					chars[row + 1][col] = " ";
				else if(col % 2 ==0 && row % 2 == 0 && col != 0 && node.west != null )
					chars[row][col - 1] = " ";
				else if(col % 2 ==0 && row % 2 == 0 && col <= (size * 2) && node.east != null )
					chars[row][col + 1] = " ";
			
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
		MazeNode west;
		MazeNode east;
		MazeNode north;
		MazeNode south;
		boolean visited;

		//node references are initially null
		//null reference represents a wall
		public MazeNode(int r, int c) {
			visited = false;
			west = east = north = south = null;
			row = r;
			col = c;
		}
		
		public boolean isVisited(){
			return visited;
		}
		
		//true if all walls are null
		public boolean allWallsIntact(){
			return west == null && east == null && north == null && south == null;
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
		public void setNeighbor(MazeNode node){
			//node is westNeighbor
			if(node.row == row && node.col == col - 1){
				west = node;
				node.east = this;
			}
			else if(node.row == row && node.col == col + 1){
				east = node;
				node.west = this;
			}
			else if(node.col == col && node.row == row - 1){
				north = node;
				node.south = this;
			}
			else if(node.col == col && node.row == row + 1){
				south = node;
				node.north = this;
			}
		}
	}
	
	public static void main(String[] args){
		Maze maze = new Maze(4);
		//commented out code tests the toString method
		/*maze.maze[0][0].east = maze.maze[0][1];
		maze.maze[0][1].south = maze.maze[1][1];
		maze.maze[1][1].east = maze.maze[1][2];
		maze.maze[2][1].east = maze.maze[2][2];
		maze.maze[2][2].south = maze.maze[2][3];
		maze.maze[2][3].south = maze.maze[3][3];*/
		System.out.println(maze);
		
	}

}
