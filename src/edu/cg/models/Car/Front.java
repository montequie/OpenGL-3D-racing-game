package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

public class Front implements IRenderable {
    private SkewedBox hoodBox1 = new SkewedBox(Specification.F_HOOD_LENGTH_1, Specification.F_HOOD_HEIGHT_1, Specification.F_HOOD_HEIGHT_2, Specification.F_HOOD_DEPTH_1, Specification.F_HOOD_DEPTH_2);
    private SkewedBox hoodBox2 = new SkewedBox(Specification.F_HOOD_LENGTH_2, Specification.F_HOOD_HEIGHT_2, Specification.F_BUMPER_HEIGHT_1, Specification.F_HOOD_DEPTH_2, Specification.F_HOOD_DEPTH_3);
    private SkewedBox bumperBox = new SkewedBox(Specification.F_BUMPER_LENGTH, Specification.F_BUMPER_HEIGHT_1, Specification.F_BUMPER_HEIGHT_2, Specification.F_BUMPER_DEPTH, Specification.F_BUMPER_DEPTH);
    private SkewedBox bumperWingsBox = new SkewedBox(Specification.F_BUMPER_LENGTH, Specification.F_BUMPER_WINGS_HEIGHT, Specification.F_BUMPER_HEIGHT_2, Specification.F_BUMPER_WINGS_DEPTH, Specification.F_BUMPER_WINGS_DEPTH);
    private PairOfWheels wheels = new PairOfWheels();


    @Override
    public void render(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslated(-(Specification.F_FRONT_LENGTH - Specification.F_HOOD_LENGTH_1) / 2, 0.0, 0.0);
        gl.glPushMatrix();
        Materials.SetRedMetalMaterial(gl);
        this.hoodBox1.render(gl);
        gl.glTranslated(Specification.F_HOOD_TOTAL_LENGTH / 2, 0.0, 0.0);
        this.hoodBox2.render(gl);
        gl.glTranslated((Specification.F_FRONT_LENGTH - Specification.F_HOOD_LENGTH_1) / 2, 0.0, 0.0);
        Materials.SetDarkRedMetalMaterial(gl);
        this.bumperBox.render(gl);
        Materials.SetRedMetalMaterial(gl);
        gl.glPushMatrix();
        gl.glTranslated(0.0, 0.0, (Specification.F_BUMPER_DEPTH + Specification.F_BUMPER_WINGS_DEPTH) / 2);
        this.bumperWingsBox.render(gl);
        gl.glPopMatrix();
        gl.glTranslated(0.0, 0.0, -(Specification.F_BUMPER_DEPTH + Specification.F_BUMPER_WINGS_DEPTH) / 2);
        this.bumperWingsBox.render(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(Specification.F_HOOD_TOTAL_LENGTH / 2, Specification.TIRE_RADIUS / 2, 0.0);
        this.wheels.render(gl);
        gl.glPopMatrix();
        gl.glPopMatrix();
    }

    @Override
    public void init(GL2 gl) {
    }

    @Override
    public void destroy(GL2 gl) {
    }

}
