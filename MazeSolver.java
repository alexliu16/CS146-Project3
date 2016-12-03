import java.util.Scanner;

/**
 * Generates and solve a random maze with the size of your choice  
 */
public class MazeSolver {
	
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter the size of your choice (4-10): ");
		int size = 10; //default size
		try{
			String s = scanner.nextLine();
			size = Integer.parseInt(s);
			if(!(size >= 4 && size <= 10))
				size = 10;
		}catch(NumberFormatException e){
			System.out.println("You have entered an invalid size. Generating a maze of size 10");
		}
		Maze maze = new Maze(size);
		System.out.println("Original maze:\n\n" + maze + "\n");
		
		//depth-first search
		maze.dfs();
		System.out.println("Depth-first search:\n");
		System.out.println(maze.printDFS());
		System.out.println("\n" + maze.printDFSShortestPath());
		
		//breadth-first search
		maze.bfs();
		System.out.println("Breadth-first search:\n");
		System.out.println(maze.printBFS());
		System.out.println("\n" + maze.printDFSShortestPath());
	}
}
