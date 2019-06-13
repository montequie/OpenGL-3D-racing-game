package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import edu.cg.models.IRenderable;

public class PairOfWheels implements IRenderable {
    private final Wheel wheel = new Wheel();

    @Override
    public void render(GL2 gl) {
        // Initialize quad
        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();

        gl.glPushMatrix();
        gl.glTranslated(0.0, 0.0, -Specification.PAIR_OF_WHEELS_ROD_DEPTH / 2);
        Materials.SetDarkGreyMetalMaterial(gl);
        // Draw cylinder - the ROD
        glu.gluCylinder(q, Specification.PAIR_OF_WHEELS_ROD_RADIUS, Specification.PAIR_OF_WHEELS_ROD_RADIUS,
                Specification.PAIR_OF_WHEELS_ROD_DEPTH, Specification.SLICES_IN_WHEEL, Specification.STACKS_IN_WHEEL);

        gl.glTranslated(0.0, 0.0, Specification.PAIR_OF_WHEELS_ROD_DEPTH + Specification.TIRE_RADIUS / 2);
        // Draw right wheel
        wheel.render(gl);
        gl.glTranslated(0.0, 0.0, - (Specification.PAIR_OF_WHEELS_ROD_DEPTH + Specification.TIRE_RADIUS));
        gl.glRotated(180.0, 0.0, 1.0, 0.0);
        // Draw left wheel
        wheel.render(gl);
        gl.glPopMatrix();
        glu.gluDeleteQuadric(q);
    }

    @Override
    public void init(GL2 gl) {
        // TODO Auto-generated method stub

    }

    @Override
    public String toString() {
        return "PairOfWheels";
    }

}
