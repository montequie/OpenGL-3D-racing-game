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
    };

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

    @Override
    public void render(GL2 gl) {

        if (displayTexture) setParams(gl);

        gl.glNormal3d(1, 0, 0);
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2d(0, 0);
        gl.glVertex3d(length / 2, 0, depth2 / 2);
        gl.glTexCoord2d(0, 1);
        gl.glVertex3d(length / 2, 0, -depth2 / 2);
        gl.glTexCoord2d(1, 1);
        gl.glVertex3d(length / 2, height2, -depth2 / 2);
        gl.glTexCoord2d(1, 0);
        gl.glVertex3d(length / 2, height2, depth2 / 2);
        gl.glEnd();

        gl.glNormal3d(-1, 0, 0);
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2d(0, 0);
        gl.glVertex3d(-length / 2, 0, -depth1 / 2);
        gl.glTexCoord2d(0, 1);
        gl.glVertex3d(-length / 2, 0, depth1 / 2);
        gl.glTexCoord2d(1, 1);
        gl.glVertex3d(-length / 2, height1, depth1 / 2);
        gl.glTexCoord2d(1, 0);
        gl.glVertex3d(-length / 2, height1, -depth1 / 2);
        gl.glEnd();

        Vec normal = new Vec(height1 - height2, 1, 0).normalize();

        gl.glNormal3d(normal.x, normal.y, normal.z);
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2d(0, 0);
        gl.glVertex3d(-length / 2, height1, depth1 / 2);
        gl.glTexCoord2d(0, 1);
        gl.glVertex3d(length / 2, height2, depth2 / 2);
        gl.glTexCoord2d(1, 1);
        gl.glVertex3d(length / 2, height2, -depth2 / 2);
        gl.glTexCoord2d(1, 0);
        gl.glVertex3d(-length / 2, height1, -depth1 / 2);
        gl.glEnd();

        gl.glNormal3d(0, -1, 0);
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2d(0, 0);
        gl.glVertex3d(length / 2, 0, depth1 / 2);
        gl.glTexCoord2d(0, 1);
        gl.glVertex3d(-length / 2, 0, -depth1 / 2);
        gl.glTexCoord2d(1, 1);
        gl.glVertex3d(length / 2, 0, -depth2 / 2);
        gl.glTexCoord2d(1, 0);
        gl.glVertex3d(length / 2, 0, depth2 / 2);
        gl.glEnd();

        normal = new Vec(depth1 - depth2, 0.0, 1.0).normalize();

        gl.glNormal3d(normal.x, 0, normal.z);
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2d(0, 0);
        gl.glVertex3d(-length / 2, height1, depth1 / 2);
        gl.glTexCoord2d(0, 1);
        gl.glVertex3d(-length / 2, 0, depth1 / 2);
        gl.glTexCoord2d(1, 1);
        gl.glVertex3d(length / 2, 0, depth2 / 2);
        gl.glTexCoord2d(1, 0);
        gl.glVertex3d(length / 2, height2, depth2 / 2);
        gl.glEnd();

        normal = new Vec(depth1 - depth2, 0, -1).normalize();

        gl.glNormal3d(normal.x, 0, normal.z);
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2d(0, 0);
        gl.glVertex3d(-length / 2, 0, -depth1 / 2);
        gl.glTexCoord2d(0, 1);
        gl.glVertex3d(-length / 2, height1, -depth1 / 2);
        gl.glTexCoord2d(1, 1);
        gl.glVertex3d(length / 2, height2, -depth2 / 2);
        gl.glTexCoord2d(1, 0);
        gl.glVertex3d(length / 2, 0, -depth2 / 2);
        gl.glEnd();

        gl.glDisable(GL2.GL_TEXTURE_2D);

    }

    //TODO: change this
    private void setParams(GL2 gl) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        texture.bind(gl);
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE );
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR );
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR );
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAX_LOD, GL2.GL_LINES);

    }


    @Override
    public void init(GL2 gl) {
        if (displayTexture) {
            try {
                File WoodPic = new File("Textures/WoodBoxTexture.jpg");
                texture = TextureIO.newTexture(WoodPic, true);
            } catch (Exception e) {}
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
