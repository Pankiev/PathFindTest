package pl.bfs.test.desktop.path.search;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import pl.bfs.test.desktop.path.search.collisionDetectors.CollisionDetector;
import pl.bfs.test.desktop.path.search.distanceComparators.DistanceComparator;

public class BestFirstPathFinder implements PathFinder
{
	private static final int STEP_VALUE = 10;

	@Override
	public Collection<? extends Point> find(Point from, Point destination, DistanceComparator distanceComparator,
			CollisionDetector collisionDetector)
	{
		Map<Point, Point> parents = new HashMap<>();
		TreeSet<Point> openList = new TreeSet<>(distanceComparator);
		HashSet<Point> closedList = new HashSet<>();
		openList.add(from);
		Point n = null; 
		Point closestNode = from;
		while (!openList.isEmpty())
		{
			n = openList.pollFirst();
			if (n.equals(destination))
				return createPath(from, destination, parents);
			if(distanceComparator.compare(closestNode, n) > 0)
				closestNode = n;
			
			closedList.add(n);
			List<Point> successors = createSuccessors(n, collisionDetector);
			for (Point successor: successors)
			{
				if (!closedList.contains(successor))
				{
					openList.add(successor);
					if (!parents.containsKey(successor)) 
						parents.put(successor, n);
				}
			}
		}

		return createPath(from, closestNode, parents);
	}

	private Collection<? extends Point> createPath(Point startPoint, Point endPoint, Map<Point, Point> parents)
	{
		List<Point> path = new ArrayList<>();
		Point current = endPoint;

		while (!current.equals(startPoint))
		{
			path.add(current);
			current = parents.get(current);
		}
		path.add(current); 
		return path;
	}

	private List<Point> createSuccessors(Point n, CollisionDetector collisionDetector)
	{
		List<Point> successors = new ArrayList<>();
		addIfNotColliding(collisionDetector, successors, new Point(n.x - STEP_VALUE, n.y));
		addIfNotColliding(collisionDetector, successors, new Point(n.x, n.y - STEP_VALUE));
		addIfNotColliding(collisionDetector, successors, new Point(n.x + STEP_VALUE, n.y));
		addIfNotColliding(collisionDetector, successors, new Point(n.x, n.y + STEP_VALUE));
		return successors;
	}

	private void addIfNotColliding(CollisionDetector collisionDetector, Collection<Point> successors, Point e)
	{
		if (!collisionDetector.isColliding(e.x, e.y))
			successors.add(e);
	}
}
