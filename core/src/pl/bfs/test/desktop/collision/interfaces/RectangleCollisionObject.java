package pl.bfs.test.desktop.collision.interfaces;

import com.badlogic.gdx.math.Rectangle;

public interface RectangleCollisionObject
{
	Rectangle getCollisionRect();

	default boolean containsPoint(int gameX, int gameY)
	{
		return getCollisionRect().contains(gameX, gameY);
	}
	
	default boolean isCurrentlyCollideable()
	{
		return true;
	}
}
