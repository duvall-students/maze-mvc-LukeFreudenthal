package application;

import java.awt.Point;

import searches.BFS;
import searches.DFS;
import searches.Greedy;
import searches.Magic;
import searches.RandomWalk;
import searches.SearchAlgorithm;
import searches.SearchFactory;

public class MazeController {

	/* 
	 * Logic of the program
	 */
	// The search algorithms
	private SearchAlgorithm search;		
	// This string tells which algorithm is currently chosen.  Anything other than 
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
		SearchFactory factory = new SearchFactory();
		maze.reColorMaze();
		search = factory.makeSearch(searchType, maze, start, goal);
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
