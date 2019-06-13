package edu.cg.models;

import com.jogamp.opengl.GL2;

public class SkewedBox implements IRenderable {
    private double length, height1, height2, depth1, depth2;

    public SkewedBox(double boxLength, boolean b) {
        length = .1;
        height1 = .2;
        height2 = .1;
        depth1 = .2;
        depth2 = .1;
    }

    ;

    public SkewedBox(double length, double h1, double h2, double d1, double d2) {
        this.length = length;
        this.height1 = h1;
        this.height2 = h2;
        this.depth1 = d1;
        this.depth2 = d2;
    }

    @Override
    public void render(GL2 gl) {
        int numOfVertices = 4;
        double [] vx, vy, vz;

        vx = new double[]{this.length / 2.0, this.length / 2.0, this.length / 2.0, this.length / 2.0};
        vy = new double[]{0.0, 0.0, this.height2, this.height2};
        vz = new double[]{this.depth2 / 2.0, -this.depth2 / 2.0, -this.depth2 / 2.0, this.depth2 / 2.0};
        drawVertex3D(gl, numOfVertices, 1.0, 0.0, 0.0, vx, vy, vz);

        vx = new double[]{-this.length / 2.0, -this.length / 2.0, -this.length / 2.0, -this.length / 2.0};
        vy = new double[]{0.0, 0.0, this.height1, this.height1};
        vz = new double[]{-this.depth1 / 2.0, this.depth1 / 2.0, this.depth1 / 2.0, -this.depth1 / 2.0};
        drawVertex3D(gl, numOfVertices, -1.0, 0.0, 0.0, vx, vy, vz);

        vx = new double[]{-this.length / 2.0, this.length / 2.0, this.length / 2.0, -this.length / 2.0};
        vy = new double[]{this.height1, this.height2, this.height2, this.height1};
        vz = new double[]{this.depth1 / 2.0, this.depth2 / 2.0, -this.depth2 / 2.0, -this.depth1 / 2.0};
        drawVertex3D(gl, numOfVertices, 0.0, 1.0, 0.0, vx, vy, vz);

        vx = new double[]{-this.length / 2.0, -this.length / 2.0, this.length / 2.0, this.length / 2.0};
        vy = new double[]{0.0, 0.0, 0.0, 0.0};
        vz = new double[]{this.depth1 / 2.0, -this.depth1 / 2.0, -this.depth2 / 2.0, this.depth2 / 2.0};
        drawVertex3D(gl, numOfVertices, 0.0, -1.0, 0.0, vx, vy, vz);

        vy = new double[]{this.height1, 0.0, 0.0, this.height2};
        vz = new double[]{this.depth1 / 2.0, this.depth1 / 2.0, this.depth2 / 2.0, this.depth2 / 2.0};
        drawVertex3D(gl, numOfVertices, 0.0, 0.0, 1.0, vx, vy, vz);

        vy = new double[]{0.0, this.height1, this.height2, 0.0};
        vz = new double[]{-this.depth1 / 2.0, -this.depth1 / 2.0, -this.depth2 / 2.0, -this.depth2 / 2.0};
        drawVertex3D(gl, numOfVertices, 0.0, 0.0, -1.0, vx, vy, vz);
    }

    private void drawVertex3D(GL2 gl, int numOfVertices , double  nx, double ny, double nz,
                              double [] vx, double [] vy, double [] vz) {
        gl.glNormal3d(nx, ny, nz);
        gl.glBegin(GL2.GL_QUADS);
        for (int i = 0; i < numOfVertices; i++) {
            gl.glVertex3d(vx[i], vy[i], vz[i]);
        }
        gl.glEnd();
    }

    @Override
    public void init(GL2 gl) {
    }

    @Override
    public void destroy(GL2 gl) {

    }

    @Override
    public String toString() {
        return "SkewedBox";
    }

}
