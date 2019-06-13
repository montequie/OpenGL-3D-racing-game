package edu.cg.models;

import java.io.File;
import java.util.LinkedList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import edu.cg.algebra.Point;
import edu.cg.models.Car.Materials;

public class TrackSegment implements IRenderable {
	// TODO: Some constants you can use
	public final static double ASPHALT_TEXTURE_WIDTH = 20.0;
	public final static double ASPHALT_TEXTURE_DEPTH = 10.0;
	public final static double GRASS_TEXTURE_WIDTH = 10.0;
	public final static double GRASS_TEXTURE_DEPTH = 10.0;
	public final static double TRACK_LENGTH = 500.0;
	public final static double BOX_LENGTH = 1.5;
	private LinkedList<Point> boxesLocations; // Store the boxes centroids (center points) here.
	private SkewedBox woodenBox = new SkewedBox(BOX_LENGTH, true);
    private Texture asphaltTexture;
    private Texture grassTexture;

	public void setDifficulty(double difficulty) {
		//       Note: In our implementation, the difficulty is the probability of a box to appear in the scene.
		//             We divide the scene into rows of boxes and we sample boxes according the difficulty probability.
		difficulty = Math.min(difficulty, 0.95);
		difficulty = Math.max(difficulty, 0.05);
		double numberOfLanes = 4.0;
		double deltaZ = 0.0;
		if (difficulty < 0.25) {
			deltaZ = 100.0;
		} else if (difficulty < 0.5) {
			deltaZ = 75.0;
		} else {
			deltaZ = 50.0;
		}
		boxesLocations = new LinkedList<Point>();
		for (double dz = deltaZ; dz < TRACK_LENGTH - BOX_LENGTH / 2.0; dz += deltaZ) {
			int cnt = 0; // Number of boxes sampled at each row.
			boolean flag = false;
			for (int i = 0; i < 12; i++) {
				double dx = -((double) numberOfLanes / 2.0) * ((ASPHALT_TEXTURE_WIDTH - 2.0) / numberOfLanes) + BOX_LENGTH / 2.0
						+ i * BOX_LENGTH;
				if (Math.random() < difficulty) {
					boxesLocations.add(new Point(dx, BOX_LENGTH / 2.0, -dz));
					cnt += 1;
				} else if (!flag) {// The first time we don't sample a box then we also don't sample the box next to. We want enough space for the car to pass through. 
					i += 1;
					flag = true;
				}
				if (cnt > difficulty * 10) {
					break;
				}
			}
		}
	}

	public TrackSegment(double difficulty) {
		// TODO initialize your fields here.
		// Here by setting up the difficulty, we decide on the boxes locations.
		setDifficulty(difficulty);
	}

	@Override
	public void render(GL2 gl) {
		// TODO: Render the track segment

		// boxes rendering - render box at each given box location
		Materials.setWoodenBoxMaterial(gl);
		for (Point p : boxesLocations) {
			gl.glPushMatrix();
			gl.glTranslated(p.x, 0.0, p.z);
			woodenBox.render(gl);
			gl.glPopMatrix();
		}

		// asphalt rendering
		Materials.setAsphaltMaterial(gl);
		gl.glPushMatrix();
		textureRender(gl, asphaltTexture, ASPHALT_TEXTURE_WIDTH, ASPHALT_TEXTURE_DEPTH, 6, TRACK_LENGTH);
		gl.glPopMatrix();

		// grass rendering
		Materials.setGreenMaterial(gl);
		double deltaX = 15.0;
		gl.glTranslated(deltaX, 0.0, 0.0);
		textureRender(gl, grassTexture, GRASS_TEXTURE_WIDTH, GRASS_TEXTURE_DEPTH, 2, TRACK_LENGTH);
		gl.glTranslated(-2.0 * deltaX, 0.0, 0.0);
		textureRender(gl, grassTexture, GRASS_TEXTURE_WIDTH, GRASS_TEXTURE_DEPTH, 2, TRACK_LENGTH);
		gl.glPopMatrix();
	}

	// TODO: what is split?
	private void textureRender(GL2 gl, Texture texture, double width, double depth, int parts, double length) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        texture.bind(gl);
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_ALPHA_TEST_REF, GL2.GL_MODULATE);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TRUE, GL2.GL_TEXTURE_MAX_LOD);
        gl.glColor3d(1.0, 0.0, 0.0);
        GLU glu = new GLU();
        GLUquadric quadric = glu.gluNewQuadric();
        gl.glColor3d(1.0, 0.0, 0.0);
        gl.glNormal3d(0.0, 1.0, 0.0);
		double d = 1.0 / (double)parts;
		double deltaZ = depth / (double)parts;
		double deltaX = width / (double)parts;
		for (double tz = 0.0; tz < length; tz += depth) {
			for (double i = 0.0; i < (double)parts; i++) {
				gl.glBegin(GL2.GL_TRIANGLE_STRIP);
				for (double j = 0.0; j <= (double)parts; j++) {
					double x = -width / 2.0 + j * deltaX;
					gl.glTexCoord2d(j * d, (i + 1.0) * d);
					gl.glVertex3d(x, 0.0, -tz - (i + 1.0) * deltaZ);
					gl.glTexCoord2d(j * d, i * d);
					gl.glVertex3d(x, 0.0, -tz - i * deltaZ);
				}
				gl.glEnd();
			}
		}
		glu.gluDeleteQuadric(quadric);
		gl.glDisable(GL2.GL_TEXTURE_2D);
	}


	@Override
	public void init(GL2 gl) {
		woodenBox.init(gl);
		try {
			// TODO: change names of pictures
			asphaltTexture = TextureIO.newTexture(new File("Textures/RoadTexture.jpg"), true);
			grassTexture = TextureIO.newTexture(new File("Textures/GrassTexture.jpg"), true);
		}
		catch (Exception e) {
			System.err.print(e.getMessage());
		}
	}

	public void destroy(GL2 gl) {
		woodenBox.destroy(gl);
		asphaltTexture.destroy(gl);
		grassTexture.destroy(gl);
	}

}
