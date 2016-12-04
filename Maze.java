import java.util.*;

/**
 * Represents the status of a node using BFS and DFS
 */
enum Color{
	WHITE, GREY, BLACK;
}
/**
 * Represents a Maze in a graph format
 */
public class Maze {
	
	private Random random;
	private MazeNode[][] maze;
	private int size;
	private int time;
	private boolean printDFS;
	private boolean printDFSShortest;
	private ArrayList<MazeNode> dfsNodes;
	private boolean printBFS;
	private boolean printBFSShortest;
	private ArrayList<MazeNode> bfsNodes;
	private boolean stopAdding = false;
	
	/**
	 * Initialize a Maze with the given size
	 * @param size Size of the matrix that is to be initiated
	 */
	public Maze(int s) {
		//initialize all variables
		time = 0;
		printDFS = false;
		printDFSShortest = false;
		dfsNodes = new ArrayList<MazeNode>();
		printBFS = false;
		printBFSShortest = false;
		bfsNodes = new ArrayList<MazeNode>();
		random = new Random();
		size = s;
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
	
	/**
	 * Uses a depth-first search to find a solution to the maze
	 */
	public void dfs(){
		// initialize vertices with color WHITE
		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++)
				maze[row][col].color = Color.WHITE;
		time = 0; //initial time
		for(MazeNode[] arr: maze)
			for(MazeNode node: arr)
				if(node.color == Color.WHITE)
					dfsVisit(node);
		stopAdding = false;
	}
	
	/**
	 * Recursive method to visit a node using depth-first search 
	 * @param node Node that is to be visited 
	 */
	private void dfsVisit(MazeNode node){
		//keeps track of nodes that are visited through a depth-first search
		if(node.equals(new MazeNode(size - 1, size - 1))){
			stopAdding = true;
			dfsNodes.add(node);
		}	
		if(!stopAdding)
			dfsNodes.add(node);
		node.color = Color.GREY;
		time++;
		node.discoveryTime = time;
		//search all neighbors of the nodes
		for(MazeNode n: node.neighbors)
			if(n.color == Color.WHITE)
				dfsVisit(n);
		
		node.color = Color.BLACK;
		time++;
		node.finishingTime = time;
	}
	
	/**
	 * Uses a breadth-first search to find a solution to the maze
	 */
	public void bfs(){
		//initialize vertices with color WHITE
		for(int row = 0; row < size; row++)
			for(int col = 0; col < size; col++)
				maze[row][col].color = Color.WHITE;
		Queue <MazeNode> queue = new LinkedList<MazeNode>();
		//choose first node as starting node
		int visit = 0;
		MazeNode node = maze[0][0];
		node.distance = 0;
		node.color = Color.GREY;
		node.numVisited = visit;
		visit++;
		bfsNodes.add(node); 
		queue.add(node);
		while(queue.size() != 0){
			node = queue.remove();
			if(node.row == size - 1 && node.col == size - 1)
				stopAdding = true;
			for(MazeNode v: node.neighbors)
				if(v.color == Color.WHITE){
					v.color = Color.GREY;
					v.numVisited = visit;
					visit ++;
					v.distance = node.distance + 1;
					v.predecessor = node;
					if(v.row == size - 1 && v.col == size - 1)
						stopAdding = true;
					if(!stopAdding)
						bfsNodes.add(v);
					queue.add(v);
				}
			
			node.color = Color.BLACK;
		}
		bfsNodes.add(maze[size-1][size-1]);
				
	}
	
	/**
	 * Generates a perfect maze - no loops, single solution, and randomized 
	 */
	public void generatePerfectMaze(){
		Stack<MazeNode> stack = new Stack<MazeNode>();
		int totalCells = size * size;
		MazeNode currentNode = maze[0][0]; //start at upper left hand corner
		currentNode.setVisited();
		int visitedCells = 1;
		//Runs until all cells have been visited
		while(visitedCells < totalCells){
			ArrayList<MazeNode> neighbors = getValidNeighbors(currentNode);
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
	
	/**
	 * Returns an ArrayList of the neighbors that have all its walls intact and that haven't been visited
	 * @param node The node whose neighbors are to be found
	 * @return ArrayList of valid neighbors
	 */
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
	
	/**
	 * Returns a string that represents the DFS maze solution
	 * @return String of DFS maze solution
	 */
	public String printDFS(){
		printDFS = true;
		String s = this.toString();
		printDFS = false;
		return s;
	}
	
	/**
	 * Returns a string that represents the shortest path using DFS
	 * @return Shortest path of maze solution using DFS
	 */
	public String printDFSShortestPath(){
		printDFSShortest = true;
		for(int x = dfsNodes.size() - 2; x > -1; x--)
			if(dfsNodes.get(x).finishingTime < dfsNodes.get(x+1).finishingTime)
				dfsNodes.remove(x);
		String s = this.toString();
		printDFSShortest = false;
		return s;
	}
	
	/**
	 * Returns a string that represents the BFS maze solution
	 * @return String of BFS maze solution
	 */
	public String printBFS(){
		printBFS = true;
		String s = this.toString();
		printBFS = false;
		return s;
	}
	
	/**
	 * Returns a string that represents the shortest path using BFS
	 * @return Shortest path of maze solution using BFS
	 */
	public String printBFSShortestPath(){
		bfsNodes = new ArrayList<MazeNode>();
		MazeNode node = maze[size-1][size-1];
		while(node != null){
			bfsNodes.add(node);
			node = node.predecessor;
		}
		printBFSShortest = true;
		String s = this.toString();
		printBFSShortest = false;
		return s;
	}
	
	@Override
	public String toString(){
		String[][] chars = new String[size*2 + 1][size*2 + 1];
		String s = "";
		for(int row = 0; row < chars.length; row ++){
			for(int col = 0; col < chars.length; col ++){
				//if there is not a node at the given location, node is null
				MazeNode node = null;
				if(row % 2 == 1 && col % 2 == 1)
					node = maze[row /2][col/2];
				//represents maze entrance and exit
				if((row == 0 && col == 1) || (row == size*2 && col == size*2 - 1))
					chars[row][col] = "  ";
				//represents corners
				else if(chars[row][col] == null && row % 2 == 0 && col % 2 == 0)
					chars[row][col] = "+";
				//represents top and bottom row
				else if(row == 0 || row == chars.length - 1)
					chars[row][col] = "--";
				//represents leftmost and rightmost column
				else if(row % 2 == 1 && (col == 0 || col == chars[0].length - 1))
					chars[row][col] = "|";
				else if(node != null){
					if(node.hasWestWall)
						chars[row][col - 1] = "|";
					else
						chars[row][col - 1] = " ";
					if(node.hasEastWall)
						chars[row][col + 1] = "|";
					else
						chars[row][col + 1] = " ";
					if(node.hasNorthWall)
						chars[row - 1][col] = "--";
					if(node.hasSouthWall)
						chars[row + 1][col] = "--";
					Integer t = (Integer)node.discoveryTime;
					Integer visit = node.numVisited;
					if(printDFS && t < 10 && dfsNodes.contains(node))
						chars[row][col] = t.toString() + " ";
					else if(printDFS && t >= 10 && dfsNodes.contains(node))
						chars[row][col] = t.toString() + "";
					else if(printDFSShortest  && dfsNodes.contains(node))
						chars[row][col] = "##";
					else if(printBFS && visit < 10 && bfsNodes.contains(node))
						chars[row][col] = visit.toString() + " ";
					else if(printBFS && visit >= 10 && bfsNodes.contains(node))
						chars[row][col] = visit.toString() + "";
					else if(printBFSShortest  && bfsNodes.contains(node))
						chars[row][col] = "##";
					else
						chars[row][col] = "  ";
				}
				else 
					chars[row][col] = "  ";
			}
		}
		for(int row = 0; row < chars.length; row ++){
			for(int col = 0; col < chars.length; col ++)
				s += chars[row][col];
			s += "\n";
		}
		return s;
	
	}

	/**Represents a vertex in a maze. Can have a maximum of 4 neighbors.*/
	 class MazeNode {
		int row;
		int col;
		boolean hasNorthWall;
		boolean hasSouthWall;
		boolean hasEastWall;
		boolean hasWestWall;
		boolean wasVisited;
		ArrayList<MazeNode> neighbors;
		Color color;
		int discoveryTime;
		int finishingTime;
		Integer distance;
		Integer numVisited;
		MazeNode predecessor;

		/**
		 * Constructor for a MazeNode with a given row and column
		 * @param r The row of the MazeNode
		 * @param c The column of the MazeNode
		 */
		public MazeNode(int r, int c) {
			neighbors = new ArrayList<MazeNode>();
			wasVisited = false;
			hasWestWall = hasEastWall = hasNorthWall = hasSouthWall = true;
			row = r;
			col = c;
			color = Color.WHITE;
			discoveryTime = 0;
			finishingTime = 0;
			distance = 0;
			numVisited = 0;
			predecessor = null;
		}
		
		/**
		 * Sets the node as "Visited"
		 */
		public void setVisited(){
			wasVisited = true;
		}
		
		/**
		 * Returns true if the node has been visited before
		 * @return Whether node has been visited before
		 */
		public boolean wasVisited(){
			return wasVisited;
		}
		
		/**
		 * Determines if the node has all its walls intact
		 * @return True if the node has all its walls intact
		 */
		public boolean allWallsIntact(){
			return neighbors.size() == 0;
		}

		//Two nodes are equal if they have the same location 
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
		
		/**
		 * Knocks down the wall between two walls and sets them as neighbors.
		 * @param node The other node who is to become its neighbor
		 */
		public void knockDownWall(MazeNode node){
			//node is west neighbor
			if(node.row == row && node.col == col - 1){
				hasWestWall = false;
				node.hasEastWall = false;
				neighbors.add(node);
				node.neighbors.add(this);
			}
			//node is east neighbor
			else if(node.row == row && node.col == col + 1){
				hasEastWall = false;
				node.hasWestWall = false;
				neighbors.add(node);
				node.neighbors.add(this);
			}
			//node is north neighbor
			else if(node.col == col && node.row == row - 1){
				hasNorthWall = false;
				node.hasSouthWall = false;
				neighbors.add(node);
				node.neighbors.add(this);
			}
			//node is south neighbor
			else if(node.col == col && node.row == row + 1){
				hasSouthWall = false;
				node.hasNorthWall = false;
				neighbors.add(node);
				node.neighbors.add(this);
			}
		}
	}

}
