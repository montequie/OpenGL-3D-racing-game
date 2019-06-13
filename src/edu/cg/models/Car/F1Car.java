package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import edu.cg.models.IRenderable;

/**
 * A F1 Racing Car.
 */
public class F1Car implements IRenderable {

    @Override
    public void render(GL2 gl) {
        // render Center
        new Center().render(gl);
        gl.glPushMatrix();
        gl.glTranslated(-(Specification.C_BASE_DEPTH + Specification.B_BASE_LENGTH) / 2, 0.0, 0.0);

        // render Back
        new Back().render(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated((Specification.C_BASE_DEPTH + Specification.B_BASE_LENGTH + Specification.TIRE_RADIUS) / 2,
                0.0, 0.0);

        // render Front
        new Front().render(gl);
        gl.glPopMatrix();
    }

    @Override
    public String toString() {
        return "F1Car";
    }

    @Override
    public void init(GL2 gl) {

    }
}
