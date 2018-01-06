package pl.bfs.test.desktop.path.search;

import pl.bfs.test.desktop.path.search.collisionDetectors.CollisionDetector;
import pl.bfs.test.desktop.path.search.distanceComparators.DistanceComparator;

import java.awt.*;
import java.util.*;
import java.util.List;

public class APrimPathFinder implements PathFinder
{
    public static final int distanceOnStep = 10;
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
		Map<ComparablePoint,Double> costs=new TreeMap<>();
		costs.put(new ComparablePoint(from),0.0);
		openList.add(new ComparablePoint(from));
		ComparablePoint n=null;
		while (!openList.isEmpty())
		{
			distanceComparator.setStartDistancedMap(costs);
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
                    Double successorCost=costs.get(n)+distanceOnStep;
                    if(!costs.containsKey(successor) || costs.get(successor)>successorCost) {
                        costs.put(successor, successorCost);
                        parents.put(successor, n);
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
		addIfNotColliding(collisionDetector, successors, new Point(n.x - distanceOnStep, n.y));
		addIfNotColliding(collisionDetector, successors, new Point(n.x, n.y - distanceOnStep));
		addIfNotColliding(collisionDetector, successors, new Point(n.x + distanceOnStep, n.y));
		addIfNotColliding(collisionDetector, successors, new Point(n.x, n.y + distanceOnStep));
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
