import static org.junit.Assert.*;

import org.junit.Test;

public class MazeJUnit {
	
	@Test
	public void testDFSTimeSizeFour(){
		long start = System.currentTimeMillis();
		Maze maze = new Maze(4);
		System.out.println("Original maze size four:\n\n" + maze + "\n");
		
		//depth-first search
		maze.dfs();
		System.out.println("Depth-first search:\n");
		System.out.println(maze.printDFS());
		System.out.println("\n" + maze.printDFSShortestPath());
		long end = System.currentTimeMillis();
		System.out.println("DFS size four time taken: " + (end - start));
	}
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
		System.out.println("DFS size ten time taken: " + (end - start));
	}
	@Test
	public void testBFSTimeSizeFour(){
		long start = System.currentTimeMillis();
		Maze maze = new Maze(4);
		System.out.println("Original maze size four:\n\n" + maze + "\n");
		
		//breadth-first search
		maze.bfs();
		System.out.println("Breadth-first search:\n");
		System.out.println(maze.printBFS());
		System.out.println("\n" + maze.printDFSShortestPath());
		long end = System.currentTimeMillis();
		System.out.println("BFS size four time taken: " + (end - start));
	}
	@Test
	public void testBFSTimeSizeTen(){
		long start = System.currentTimeMillis();
		Maze maze = new Maze(10);
		System.out.println("Original maze size ten:\n\n" + maze + "\n");
		
		//breadth-first search
		maze.bfs();
		System.out.println("Breadth-first search:\n");
		System.out.println(maze.printBFS());
		System.out.println("\n" + maze.printDFSShortestPath());
		long end = System.currentTimeMillis();
		System.out.println("BFS size ten time taken: " + (end - start));
	}
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