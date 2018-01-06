package pl.bfs.test.desktop.path.search;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import pl.bfs.test.desktop.path.search.collisionDetectors.CollisionDetector;
import pl.bfs.test.desktop.path.search.distanceComparators.DistanceComparator;

public class APrimPathFinder implements PathFinder
{
	public static final int distanceOnStep = 10;

	@Override
	public Collection<? extends Point> find(Point from, Point destination, DistanceComparator distanceComparator,
			CollisionDetector collisionDetector)
	{
		HashSet<Point> closedSet = new HashSet<>();
		TreeSet<Point> openSet = new TreeSet<>(distanceComparator);
		Map<Point, Point> cameFrom = new HashMap<>();
		Map<Point, Float> gScore = new HashMap<>();
		gScore.put(from, 0.0f);

		while (!openSet.isEmpty())
		{
			Point current = openSet.pollLast();
			if (current.equals(destination))
				return reconstructPath(cameFrom, current);
			closedSet.add(current);
			for (Point neighbour : createSuccessors(current, collisionDetector))
			{
				if (closedSet.contains(neighbour))
					continue;
				openSet.add(neighbour);
				Float currentGScore = gScore.get(current);
				if (currentGScore == null)
					continue;
				float tentativeGScore = currentGScore + (float) distanceComparator.getDistance(current, neighbour);
				Float neighbourGScore = gScore.get(neighbour);
				if (neighbourGScore == null)
					neighbourGScore = Float.POSITIVE_INFINITY;
				if (tentativeGScore >= neighbourGScore)
					continue;
				cameFrom.put(neighbour, current);
				gScore.put(neighbour, tentativeGScore);
			}
		}

		return Collections.emptyList();
	}

	private Collection<? extends Point> reconstructPath(Map<Point, Point> cameFrom, Point current)
	{
		Collection<Point> totalPath = new ArrayList<>();
		totalPath.add(current);
		while (cameFrom.containsKey(current))
		{
			current = cameFrom.get(current);
			totalPath.add(current);
		}
		return totalPath;
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
		if (!collisionDetector.isColliding(e.x, e.y))
			successors.add(e);
	}
}
