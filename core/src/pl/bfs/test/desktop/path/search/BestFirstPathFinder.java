package pl.bfs.test.desktop.path.search;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import pl.bfs.test.desktop.path.search.collisionDetectors.CollisionDetector;
import pl.bfs.test.desktop.path.search.distanceComparators.DistanceComparator;

public class BestFirstPathFinder implements PathFinder
{
	HashMap<Point,Point> parents=new HashMap<>();
	Point startPoint=null;
	Point endPoint=null;

	@Override
	public Collection<? extends Point> find(Point from, Point destination, DistanceComparator distanceComparator, CollisionDetector collisionDetector)
	{
		startPoint=new Point(from);
		endPoint=new Point(destination);
		List<Point> openList = new ArrayList<>();
		List<Point> closedList = new ArrayList<>();
		openList.add(new Point(from));
		Point n=null;
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
				Point successor=new Point(successorNotComparable);
				if(!closedList.contains(successor) && !openList.contains(successor)) {
					openList.add(successor);
					if(!parents.containsKey(new Point(successor))){
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
		Point current=endPoint;

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
}
