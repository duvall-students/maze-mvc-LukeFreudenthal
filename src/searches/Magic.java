package searches;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import application.Maze;

public class Magic extends Greedy{	

		public Magic(Maze mazeBlocks, Point startPoint, Point goalPoint){
			super(mazeBlocks, startPoint, goalPoint);
			// For a greedy searcher, we will use a priority queue
			// based on the number of steps away from the goal.		
			data = new PriorityQueue<Point>(15, (p1, p2) -> distanceToGoal(p1)-distanceToGoal(p2));
			data.add(startPoint);
		}

		private int distanceToGoal(Point p){
			return goal.x-p.x + goal.y-p.y;
		}
		
		/*
		 * Rather than choosing the (first) closest NON-wall, choose 
		 * any of the closest next squares.
		 */
		@Override
		protected Point chooseNeighbor(Collection<Point> neighbors){
			Point closest = closestToGoal(neighbors);
			List<Point> possibles = new ArrayList<>();
			for(Point p: neighbors){
				if(distanceToGoal(p) == distanceToGoal(closest)){
					possibles.add(p);
				}
			}
			if(!possibles.isEmpty()){
				int randIndex = (int)(Math.random()*possibles.size());
				return possibles.get(randIndex);
			}
			return null;
		}

		/*
		 * Of all the neighbors, choose one with the smallest distance to goal.
		 */
		private Point closestToGoal(Collection<Point> neighbors){
			int smallestDistance = Integer.MAX_VALUE;
			Point next = null;
			for(Point p: neighbors){
				int dist = distanceToGoal(p);
				if(dist < smallestDistance){
					next = p;
					smallestDistance = dist;
				}

			}
			return next;
		}

		/*
		 * When a next step is found, add it to the queue and remember the child-parent relationship
		 */
		@Override
		protected void recordLink(Point next){	
			data.add(next);
			childParent.put(next,current);
		}
		
		@Override
		protected void noStepIsFound() {
			maze.markVisited(current);
			PriorityQueue<Point> queue = (PriorityQueue<Point>) data;
			queue.remove();
		}

		/*
		 * The current node is the one chosen by the priority queue
		 */
		@Override
		protected void resetCurrent(){
			PriorityQueue<Point> queue = (PriorityQueue<Point>) data;
			current = queue.peek();
		}
}
