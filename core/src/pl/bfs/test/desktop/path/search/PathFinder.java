package pl.bfs.test.desktop.path.search;

import java.awt.Point;
import java.util.Collection;

import pl.bfs.test.desktop.path.search.collisionDetectors.CollisionDetector;
import pl.bfs.test.desktop.path.search.distanceComparators.DistanceComparator;

public interface PathFinder
{
	Collection<? extends Point> find(Point from, Point to, DistanceComparator distanceComparator,
			CollisionDetector collisionDetector);
}
