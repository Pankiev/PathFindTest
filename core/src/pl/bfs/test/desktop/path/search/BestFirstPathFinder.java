package pl.bfs.test.desktop.path.search;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import pl.bfs.test.desktop.path.search.collisionDetectors.CollisionDetector;
import pl.bfs.test.desktop.path.search.distanceComparators.DistanceComparator;

public class BestFirstPathFinder implements PathFinder
{
	@Override
	public Collection<? extends Point> find(Point from, Point destination, DistanceComparator distanceComparator, CollisionDetector collisionDetector)
	{
		TreeMap<Point, Point> openList = new TreeMap<>(distanceComparator);
		TreeMap<Point, Point> closedList = new TreeMap<>(distanceComparator);
		Collection<Point> path = new ArrayList<>();
		openList.put(from, from);
		while (!openList.isEmpty())
		{
			Point n = openList.pollFirstEntry().getValue();
			closedList.put(n, n);
			if (n.equals(destination))
				return path;
			Collection<Point> successors = createSuccessors(n, collisionDetector);
			successors.forEach(successor -> 
			{
				if(closedList.containsKey(successor))
					return;
				if (!openList.containsKey(successor))
					path.add(n);
				openList.put(successor, successor);				
			});
		}

		return path;
	}

	private Collection<Point> createSuccessors(Point n, CollisionDetector collisionDetector)
	{
		Collection<Point> successors = new ArrayList<>();
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
