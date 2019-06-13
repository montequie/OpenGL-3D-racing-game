package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import edu.cg.models.IRenderable;

public class Wheel implements IRenderable {

    @Override
    public void render(GL2 gl) {
        // The wheel should be in the center relative to its local coordinate system.
        // Initialize quad
        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();

        Materials.setMaterialTire(gl);
        gl.glPushMatrix();
        gl.glTranslated(0.0, 0.0, -Specification.TIRE_DEPTH / 2);
        // Draw cylinder
        glu.gluCylinder(q, Specification.TIRE_RADIUS, Specification.TIRE_RADIUS, Specification.TIRE_DEPTH,
                Specification.SLICES_IN_WHEEL, Specification.STACKS_IN_WHEEL);

        gl.glRotated(180.0, 1.0, 0.0, 0.0);
        // Draw disk
        glu.gluDisk(q, Specification.TIRE_INNER_RADIUS, Specification.TIRE_RADIUS,
                Specification.SLICES_IN_WHEEL, Specification.LOOPS_IN_WHEEL);

        gl.glRotated(180.0, 1.0, 0.0, 0.0);
        gl.glTranslated(0.0, 0.0, Specification.TIRE_DEPTH);
        // Draw disk
        glu.gluDisk(q, Specification.TIRE_INNER_RADIUS, Specification.TIRE_RADIUS,
                Specification.SLICES_IN_WHEEL, Specification.LOOPS_IN_WHEEL);

        Materials.setMaterialRims(gl);
        // Draw disk
        glu.gluDisk(q, 0.0, Specification.TIRE_INNER_RADIUS,
                Specification.SLICES_IN_WHEEL, Specification.LOOPS_IN_WHEEL);

        gl.glTranslated(0.0, 0.0, -Specification.TIRE_DEPTH);
        gl.glRotated(180.0, 1.0, 0.0, 0.0);
        // Draw disk
        glu.gluDisk(q, 0.0, Specification.TIRE_INNER_RADIUS,
                Specification.SLICES_IN_WHEEL, Specification.LOOPS_IN_WHEEL);

        gl.glPopMatrix();

        //Clear from memory
        glu.gluDeleteQuadric(q);
    }

    @Override
    public void init(GL2 gl) {
    }

    @Override
    public void destroy(GL2 gl) {
    }

        @Override
    public String toString() {
        return "Wheel";
    }

}
