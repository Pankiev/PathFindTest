package pl.bfs.test.desktop;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import pl.bfs.test.desktop.collision.interfaces.RectangleCollisionObject;
import pl.bfs.test.desktop.collision.pixelmap.PixelCollisionMap;
import pl.bfs.test.desktop.path.search.PathFinder;
import pl.bfs.test.desktop.path.search.collisionDetectors.CollisionDetector;
import pl.bfs.test.desktop.path.search.distanceComparators.DistanceComparator;

public abstract class PathFindTest extends ApplicationAdapter
{
	private SpriteBatch batch;
	private Texture img;
	private Collection<GameObject> gameObjects = new ArrayList<>();
	private Collection<? extends Point> points;
	protected PixelCollisionMap<RectangleCollisionObject> collisionMap;

	protected abstract DistanceComparator getDistanceComparator();

	protected abstract Collection<Rectangle> getCollisionRectangles();

	protected abstract Point getStartPoint();

	protected abstract Point getEndPoint();

	protected abstract CollisionDetector getCollisionDetector();
	
	protected abstract PathFinder getPathFinder();

	@Override
	public void create()
	{
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		collisionMap = new PixelCollisionMap<>(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 1,
				GameObject.NULL_OBJECT);
		getCollisionRectangles().forEach(r -> putObject(r.x, r.y, r.width, r.height));
		long start = System.nanoTime();
		Collection<? extends Point> path = getPathFinder().find(getStartPoint(), getEndPoint(), getDistanceComparator(),
				getCollisionDetector());
		long end = System.nanoTime();
		System.out.println((end - start) / 1000000000.0f + "s");
		points = path;
	}

	private void putObject(float x, float y, float width, float height)
	{
		GameObject gameObject = new GameObject(img);
		gameObject.setPosition(x, y);
		gameObject.setSize(width, height);
		gameObjects.add(gameObject);
		collisionMap.insert(gameObject);
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		gameObjects.forEach(object -> object.render(batch));
		points.forEach(point -> batch.draw(img, point.x, point.y, 10, 10));
		batch.end();
	}

	@Override
	public void dispose()
	{
		batch.dispose();
		img.dispose();
	}
}
