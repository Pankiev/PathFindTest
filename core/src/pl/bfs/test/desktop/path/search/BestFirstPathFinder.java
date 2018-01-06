package pl.bfs.test.desktop.path.search;

import java.awt.Point;
import java.util.*;

import javafx.collections.transformation.SortedList;
import pl.bfs.test.desktop.path.search.collisionDetectors.CollisionDetector;
import pl.bfs.test.desktop.path.search.distanceComparators.DistanceComparator;

public class BestFirstPathFinder implements PathFinder
{
	TreeMap<ComparablePoint,ComparablePoint> parents=new TreeMap<>();
	ComparablePoint startPoint=null;
	ComparablePoint endPoint=null;

	@Override
	public Collection<? extends Point> find(Point from, Point destination, DistanceComparator distanceComparator, CollisionDetector collisionDetector)
	{
		startPoint=new ComparablePoint(from);
		endPoint=new ComparablePoint(destination);
		List<ComparablePoint> openList = new ArrayList<>();
		List<ComparablePoint> closedList = new ArrayList<>();
		openList.add(new ComparablePoint(from));
		ComparablePoint n=null;
		while (!openList.isEmpty())
		{
			Collections.sort(openList,distanceComparator);
			n = openList.get(0);
			if (n.equals(destination))
				return createPath();

			closedList.add(n);
			openList.remove(n);
			List<Point> successors = createSuccessors(n, collisionDetector);
			for (Point successorNotComparable:successors)
			{
				ComparablePoint successor=new ComparablePoint(successorNotComparable);
				if(!closedList.contains(successor) && !openList.contains(successor)) {
					openList.add(successor);
					if(!parents.containsKey(new ComparablePoint(successor))){
						parents.put(successor,n);
					}
				}
			};
		}

		endPoint=n;
		return createPath();
	}

	private Collection<? extends Point> createPath() {
		List<Point> path=new ArrayList<>();
		ComparablePoint current=endPoint;

		while(!current.equals(startPoint)){
			path.add(current);
			current=parents.get(current);
		}
		path.add(current);

		Collections.reverse(path);
		return path;
	}

	private List<Point> createSuccessors(Point n, CollisionDetector collisionDetector)
	{
		List<Point> successors = new ArrayList<>();
		addIfNotColliding(collisionDetector, successors, new Point(n.x - 10, n.y));
		addIfNotColliding(collisionDetector, successors, new Point(n.x, n.y - 10));
		addIfNotColliding(collisionDetector, successors, new Point(n.x + 10, n.y));
		addIfNotColliding(collisionDetector, successors, new Point(n.x, n.y + 10));
		return successors;
	}

	private void addIfNotColliding(CollisionDetector collisionDetector, Collection<Point> successors, Point e)
	{
		if(!collisionDetector.isColliding(e.x, e.y))
			successors.add(e);
	}

	class ComparablePoint extends Point implements Comparable{
		public ComparablePoint(Point p){
			super(p);
		}

		@Override
		public int compareTo(Object o) {
			Point other=(Point)o;
			int c1=this.x-other.x;
			int c2=this.y-other.y;
			if(c1==0 && c2==0)return 0;
			if(c1==0) return c2/Math.abs(c2);
			else return c1/Math.abs(c1);
		}
	}
}
