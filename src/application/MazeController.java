package application;

import java.awt.Point;

import searches.BFS;
import searches.DFS;
import searches.Greedy;
import searches.Magic;
import searches.RandomWalk;
import searches.SearchAlgorithm;

public class MazeController {

	/* 
	 * Logic of the program
	 */
	// The search algorithms
//	private Greedy greedy;				
//	private BFS bfs;
//	private DFS dfs;
//	private RandomWalk rand;
//	private Magic magic;
	private SearchAlgorithm search;		// This string tells which algorithm is currently chosen.  Anything other than 
	// the implemented search class names will result in no search happening.
	// Where to start and stop the search
	private Point start;
	private Point goal;

	// The maze to search
	private Maze maze;
	
	// Maze Display instance
	private MazeDisplay display;
	
	public MazeController(int rows, int cols, MazeDisplay display) {
		// Initializing logic state
		int numRows = rows;
		int numColumns = cols;
		start = new Point(1,1);
		goal = new Point(numRows-2, numColumns-2);
		maze = new Maze(numRows, numColumns);
		
		this.display = display;
	}
	
	// Create new maze helper
	public void newMaze() {
		maze.createMaze(maze.getNumRows(),maze.getNumCols());
		search = null;
		display.redraw();
	}
	
	// Search method
	public void startSearch(String searchType) {
		maze.reColorMaze();
		if(searchType.equals("DFS")) search = new DFS(maze, start, goal);
		else if (searchType.equals("BFS")) search = new BFS(maze, start, goal);
		else if (searchType.equals("Greedy")) search = new Greedy(maze, start, goal);
		else if (searchType.equals("RandomWalk")) search = new RandomWalk(maze, start, goal);
		else if (searchType.equals("Magic")) search = new Magic(maze, start, goal);
		
		// Restart the search.  Since I don't know 
		// which one, I'll restart all of them.
		
//		search = new BFS(maze, start, goal);	// start in upper left and end in lower right corner
//		search = new DFS(maze, start, goal);
//		greedy = new Greedy(maze, start, goal);
//		search = new RandomWalk(maze, start, goal);
//		magic = new Magic(maze, start, goal);
	}
	
	/*
	 * Does a step in the search regardless of pause status
	 */
	public void doOneStep(double elapsedTime){
		if(search != null) search.step();
		display.redraw();
	}

	public int getCellState(Point position) {
		return maze.get(position);
	}
	
}
