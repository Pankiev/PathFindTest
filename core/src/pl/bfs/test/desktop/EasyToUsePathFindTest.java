package pl.bfs.test.desktop;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.math.Rectangle;

import pl.bfs.test.desktop.path.search.BestFirstPathFinder;
import pl.bfs.test.desktop.path.search.PathFinder;
import pl.bfs.test.desktop.path.search.collisionDetectors.CollisionDetector;
import pl.bfs.test.desktop.path.search.collisionDetectors.RectangleCollisionDetector;
import pl.bfs.test.desktop.path.search.distanceComparators.DistanceComparator;
import pl.bfs.test.desktop.path.search.distanceComparators.ManhattanDistanceComparator;

public class EasyToUsePathFindTest extends PathFindTest
{
	@Override
	protected PathFinder getPathFinder()
	{
		return new BestFirstPathFinder();
	}
	
	@Override
	protected Point getStartPoint()
	{
		return new Point(100, 100);
	}

	@Override
	protected Point getEndPoint()
	{
		return new Point(400, 150);
	}
	
	@Override
	protected DistanceComparator getDistanceComparator()
	{
		return new ManhattanDistanceComparator(getEndPoint());
	}

	@Override
	protected Collection<Rectangle> getCollisionRectangles()
	{
		Collection<Rectangle> rectangles = new ArrayList<>();
		rectangles.add(new Rectangle(100, 350, 1000, 10));
		rectangles.add(new Rectangle(550, 100, 10, 500));
		return rectangles;
	}

	@Override
	protected CollisionDetector getCollisionDetector()
	{
		return new RectangleCollisionDetector(super.collisionMap, 0, 0);
	}



}
