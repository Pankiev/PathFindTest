package pl.bfs.test.desktop.path.search.distanceComparators;

import java.awt.Point;
import java.util.Comparator;

public abstract class DistanceComparator implements Comparator<Point>
{
	private static final float nearZero = 0.1f;
	private final Point destination;

	public DistanceComparator(Point destination)
	{
		this.destination = new Point(destination);
	}
	
	@Override
	public int compare(Point first, Point second)
	{
		double distance = getDistance(first, destination) - getDistance(second, destination);
		if(distance > -nearZero && distance < nearZero)
			return 0;
		if(distance < 0)
			return -1;
		return 1;
	}

	public abstract double getDistance(Point from, Point to);
}
