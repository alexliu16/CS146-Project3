import static org.junit.Assert.*;
import org.junit.Test;

/**
 * JUnit test for the Maze class
 */
public class MazeJUnit {
	
	/**
	 * Tests the timing of DFS and BFS of a maze with size four
	 */
	@Test
	public void testTimeSizeFour(){
		
		Maze maze = new Maze(4);
		System.out.println("Original maze size four:\n\n" + maze + "\n");
		
		//depth-first search
		long start = System.currentTimeMillis();
		maze.dfs();
		System.out.println("Depth-first search:\n");
		System.out.println(maze.printDFS());
		System.out.println("\n" + maze.printDFSShortestPath());
		long end = System.currentTimeMillis();
		System.out.println("DFS size four time taken: " + (end - start) + " milliseconds\n");
		
		//breadth-first search
		start = System.currentTimeMillis();
		maze.bfs();
		System.out.println("Breadth-first search:\n");
		System.out.println(maze.printBFS());
		System.out.println("\n" + maze.printBFSShortestPath());
		end = System.currentTimeMillis();
		System.out.println("BFS size four time taken: " + (end - start) + " milliseconds\n");
	}

	/**
	 * Tests the timing of DFS and BFS of a maze with size ten
	 */
	@Test
	public void testDFSTimeSizeTen(){
		long start = System.currentTimeMillis();
		Maze maze = new Maze(10);
		System.out.println("Original maze size ten:\n\n" + maze + "\n");
		
		//depth-first search
		maze.dfs();
		System.out.println("Depth-first search:\n");
		System.out.println(maze.printDFS());
		System.out.println("\n" + maze.printDFSShortestPath());
		long end = System.currentTimeMillis();
		System.out.println("DFS size ten time taken: " + (end - start) + " milliseconds\n");
		
		// breadth-first search
		start = System.currentTimeMillis();
		maze.bfs();
		System.out.println("Breadth-first search:\n");
		System.out.println(maze.printBFS());
		System.out.println("\n" + maze.printBFSShortestPath());
		end = System.currentTimeMillis();
		System.out.println("BFS size ten time taken: " + (end - start) + " milliseconds\n");
	}
	
	/**
	 * Tests to make sure that the shortest path of the DFS and BFS are equal
	 */
	@Test
	public void DFSEqualBFS(){
		Maze maze = new Maze(10);
		
		maze.dfs();
		maze.printDFS();
		String dfs = maze.printDFSShortestPath();
	
		maze.bfs();
		maze.printBFS();
		String bfs = maze.printBFSShortestPath();
		
		assertEquals(dfs,bfs);
		
	}
}
