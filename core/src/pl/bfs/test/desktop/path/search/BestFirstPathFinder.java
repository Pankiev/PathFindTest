package pl.bfs.test.desktop.path.search;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import pl.bfs.test.desktop.path.search.collisionDetectors.CollisionDetector;
import pl.bfs.test.desktop.path.search.distanceComparators.DistanceComparator;

public class BestFirstPathFinder implements PathFinder
{

	@Override
	public Collection<? extends Point> find(Point from, Point destination, DistanceComparator distanceComparator,
			CollisionDetector collisionDetector)
	{
		Map<Point, Point> parents = new HashMap<>();
		PriorityQueue<Point> openList = new PriorityQueue<>(distanceComparator);
		List<Point> closedList = new ArrayList<>();
		openList.add(from);
		Point n = null; 
		while (!openList.isEmpty())
		{
			n = openList.poll(); 
			if (n.equals(destination))
				return createPath(from, destination, parents);

			closedList.add(n);
			List<Point> successors = createSuccessors(n, collisionDetector);
			for (Point successor: successors)
			{
				if (!closedList.contains(successor) && !openList.contains(successor))
				{
					openList.add(successor);
					if (!parents.containsKey(successor))
						parents.put(successor, n);
				}
			}
		}

		return createPath(from, n, parents);
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
		if (!collisionDetector.isColliding(e.x, e.y))
			successors.add(e);
	}
}
