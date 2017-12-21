package pl.bfs.test.desktop;

import java.awt.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Line
{
	private static final ShapeRenderer shapeRenderer = createShapeRenderer();
	private static final float LINE_WIDTH = 7;
	private Vector2 startingPosition;
	private Vector2 endingPosition;

	private static ShapeRenderer createShapeRenderer()
	{
		Gdx.gl.glLineWidth(LINE_WIDTH);
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setColor(new Color(1, 0.5f, 0, 1));
		return shapeRenderer;
	}
	
	public Line(Vector2 startingP, Vector2 endingP)
	{
		startingPosition = startingP;
		endingPosition = endingP;
	}
	
	public Line(Point startingP, Point endingP)
	{
		startingPosition = new Vector2(startingP.x, startingP.y);
		endingPosition = new Vector2(endingP.x, endingP.y);
	}

	public void render(SpriteBatch spriteBatch)
	{
		spriteBatch.end();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.line(startingPosition, endingPosition);
		shapeRenderer.end();
		spriteBatch.begin();
	}

}
