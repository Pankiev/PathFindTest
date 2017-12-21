package pl.bfs.test.desktop.path.search.collisionDetectors;

import pl.bfs.test.desktop.collision.interfaces.CollisionMap;
import pl.bfs.test.desktop.collision.interfaces.RectangleCollisionObject;

public class RectangleCollisionDetector implements CollisionDetector
{
	private CollisionMap<RectangleCollisionObject> collisionMap;
	private int objectWidth;
	private int objectHeight;

	public RectangleCollisionDetector(CollisionMap<RectangleCollisionObject> collisionMap,
			int objectWidth, int objectHeight)
	{
		this.collisionMap = collisionMap;
		this.objectWidth = objectWidth;
		this.objectHeight = objectHeight;
	}

	@Override
	public boolean isColliding(int x, int y)
	{
		return !(collisionMap.isEmptySpace(x - objectWidth/2, y - objectHeight/2) 
				&& collisionMap.isEmptySpace(x + objectWidth/2, y - objectHeight/2)
				&& collisionMap.isEmptySpace(x - objectWidth/2, y + objectHeight/2) 
				&& collisionMap.isEmptySpace(x + objectWidth/2, y + objectHeight/2) 
				&& collisionMap.isEmptySpace(x, y));
		//return !collisionMap.isEmptySpace(x, y);
	}

}
