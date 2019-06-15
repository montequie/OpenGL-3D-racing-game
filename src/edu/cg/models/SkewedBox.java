package edu.cg.models;

import java.io.File;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import edu.cg.algebra.Vec;

public class SkewedBox implements IRenderable {
    // TODO: fix class

    private double length, height1, height2, depth1, depth2;
    private Texture texture;
    private boolean displayTexture;

    public SkewedBox() {
        length = .1;
        height1 = .2;
        height2 = .1;
        depth1 = .2;
        depth2 = .1;
        displayTexture = false;
    }

    ;

    public SkewedBox(double length, double h1, double h2, double d1, double d2) {
        this.length = length;
        this.height1 = h1;
        this.height2 = h2;
        this.depth1 = d1;
        this.depth2 = d2;
        displayTexture = false;
    }

    //TODO: change this
    public SkewedBox(double length, boolean textureOn) {
        this.length = length;
        this.depth1 = length;
        this.depth2 = length;
        this.height1 = length;
        this.height2 = length;
        displayTexture = textureOn;
    }

    private void drawVertex3D(GL2 gl, int numOfVertices, double nx, double ny, double nz,
                              double[] vx, double[] vy, double[] vz, int[] s, int[] t) {
        gl.glNormal3d(nx, ny, nz);
        gl.glBegin(GL2.GL_QUADS);
        for (int i = 0; i < numOfVertices; i++) {
            gl.glTexCoord2d(s[i], t[i]);
            gl.glVertex3d(vx[i], vy[i], vz[i]);
        }
        gl.glEnd();
    }

    @Override
    public void render(GL2 gl) {

        if (displayTexture) setParams(gl);

        int numOfVertices = 4;
        double[] vx, vy, vz;
        int[] s = new int[]{0, 0, 1, 1};
        int[] t = new int[]{0, 1, 1, 0};

        vx = new double[]{length / 2, length / 2, length / 2, length / 2};
        vy = new double[]{0.0, 0.0, height2, height2};
        vz = new double[]{depth2 / 2, -depth2 / 2, -depth2 / 2, depth2 / 2};
        drawVertex3D(gl, numOfVertices, 1, 0, 0, vx, vy, vz, s, t);

        vx = new double[]{-length / 2, -length / 2, -length / 2, -length / 2.0};
        vy = new double[]{0, 0, height1, height1};
        vz = new double[]{-depth1 / 2, depth1 / 2, depth1 / 2, -depth1 / 2};
        drawVertex3D(gl, numOfVertices, -1, 0, 0, vx, vy, vz, s, t);

        Vec normal = new Vec(height1 - height2, 1, 0).normalize();
        vx = new double[]{-length / 2, length / 2, length / 2, -length / 2};
        vy = new double[]{height1, height2, height2, height1};
        vz = new double[]{depth1 / 2, depth2 / 2, -depth2 / 2, -depth1 / 2};
        drawVertex3D(gl, numOfVertices, normal.x, normal.y, normal.z, vx, vy, vz, s, t);

        vx = new double[]{length / 2, -length / 2, length / 2, length / 2};
        vy = new double[]{0, 0, 0, 0};
        vz = new double[]{depth1 / 2, -depth1 / 2, -depth2 / 2, depth2 / 2};
        drawVertex3D(gl, numOfVertices, 0, -1, 0, vx, vy, vz, s, t);

        normal = new Vec(depth1 - depth2, 0.0, 1.0).normalize();

        vx = new double[]{-length / 2, -length / 2, length / 2, length / 2};
        vy = new double[]{height1, 0, 0, height2};
        vz = new double[]{depth1 / 2, depth1 / 2, depth2 / 2, depth2 / 2};
        drawVertex3D(gl, numOfVertices, normal.x, 0, normal.z, vx, vy, vz, s, t);

        normal = new Vec(depth1 - depth2, 0, -1).normalize();

        vy = new double[]{0, height1, height2, 0};
        vz = new double[]{-depth1 / 2, -depth1 / 2, -depth2 / 2, -depth2 / 2};
        drawVertex3D(gl, numOfVertices, normal.x, 0, normal.z, vx, vy, vz, s, t);

        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    private void setParams(GL2 gl) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        texture.bind(gl);
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAX_LOD, GL2.GL_LINES);

    }


    @Override
    public void init(GL2 gl) {
        if (displayTexture) {
            try {
                File WoodPic = new File("Textures/WoodBoxTexture.jpg");
                texture = TextureIO.newTexture(WoodPic, true);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void destroy(GL2 gl) {
        if (displayTexture) texture.destroy(gl);
        texture = null;
    }

    public String toString() {
        return "SkewedBox";
    }

}
