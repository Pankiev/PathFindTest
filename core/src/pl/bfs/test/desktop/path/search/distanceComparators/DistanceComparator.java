package pl.bfs.test.desktop.path.search.distanceComparators;

import java.awt.Point;
import java.util.Comparator;
import java.util.Map;

public abstract class DistanceComparator implements Comparator<Point>
{
	private static final float nearZero = 0.1f;
	private final Point destination;
	private Map<? extends Point, Double> startDistancedMap=null;

	public DistanceComparator(Point destination)
	{
		this.destination = new Point(destination);
	}

	public void setStartDistancedMap(Map<? extends Point,Double> map){
		startDistancedMap=map;
	}
	
	@Override
	public int compare(Point first, Point second)
	{
		double firstDistance=getDistance(first, destination);
		double secondDistance=getDistance(second, destination);
		if(startDistancedMap!=null) {
			firstDistance += startDistancedMap.get(first);
			secondDistance+=startDistancedMap.get(second);
		}

		double distance =  firstDistance-secondDistance ;
		if(distance > -nearZero && distance < nearZero)
			return 0;
		if(distance < 0)
			return -1;
		return 1;
	}

	public abstract double getDistance(Point from, Point to);
}
