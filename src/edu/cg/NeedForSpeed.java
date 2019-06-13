package edu.cg;

import java.awt.Component;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import edu.cg.algebra.Vec;
import edu.cg.models.Track;
import edu.cg.models.TrackSegment;
import edu.cg.models.Car.F1Car;
import edu.cg.models.Car.Specification;

/**
 * An OpenGL 3D Game.
 */
public class NeedForSpeed implements GLEventListener {
    private GameState gameState; // Tracks the car movement and orientation
    private F1Car car; // The F1 car we want to render
    private Vec carCameraTranslation; // The accumulated translation that should be applied on the car, camera
    // and light sources
    private Track gameTrack; // The game track we want to render
    private FPSAnimator ani; // This object is responsible to redraw the model with a constant FPS
    private Component glPanel; // The canvas we draw on.
    private boolean isModelInitialized = false; // Whether model.init() was called.
    private boolean isDayMode = true; // Indicates whether the lighting mode is day/night.
    private double TrackSegmentLength = 500.0;

    public NeedForSpeed(Component glPanel) {
        this.glPanel = glPanel;
        gameState = new GameState();
        gameTrack = new Track();
        carCameraTranslation = new Vec(0.0);
        car = new F1Car();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        if (!isModelInitialized) {
            initModel(gl);
        }
        if (isDayMode) {
            gl.glClearColor(0.52f, 0.824f, 1.0f, 1.0f);
        } else {
            gl.glClearColor(0.0f, 0.0f, 0.32f, 1.0f);
        }
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        // Step (1) You should update the accumulated translation that needs to be
        // applied on the car, camera and light sources.
        updateCarCameraTranslation(gl);
        // Step (2) Position the camera and setup its orientation
        setupCamera(gl);
        // Step (3) setup the lighting.
        setupLights(gl);
        // Step (4) render the car.
        renderCar(gl);
        // Step (5) render the track.
        renderTrack(gl);
    }

    private void updateCarCameraTranslation(GL2 gl) {
        // TODO: Update the car and camera translation values (not the
        // ModelView-Matrix).
        // - You should always keep track on the car offset relative to the starting
        // point.
        // - You should change the track segments here.
        // TODO: rename ret; test constant; why 7?
        Vec ret = gameState.getNextTranslation();
        carCameraTranslation = carCameraTranslation.add(ret);
        double dx = Math.max((double) carCameraTranslation.x, -7.0);
        carCameraTranslation.x = (float) Math.min(dx, 7.0);
        if ((double) Math.abs(carCameraTranslation.z) >= 10.0 + TrackSegmentLength) {
            carCameraTranslation.z = -((float) ((double) Math.abs(carCameraTranslation.z) % TrackSegmentLength));
            gameTrack.changeTrack(gl);
        }
    }

    private void setupCamera(GL2 gl) {
        // TODO: Setup the camera.
    }


    private void setupLights(GL2 gl) {
        if (isDayMode) {
            // switch-off any light sources that were used in night mode
            gl.glDisable(GL2.GL_LIGHT1);
            // setup day lighting
            int light = GL2.GL_LIGHT0;
            float[] dayColor = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
            Vec dir = new Vec(0.0, 1.0, 1.0).normalize();
            float[] pos = new float[]{dir.x, dir.y, dir.z, 0.0f};
            // TODO: check if order is correct
            gl.glLightfv(light, GL2.GL_AMBIENT, new float[]{0.1f, 0.1f, 0.1f, 1.0f}, 0);
            gl.glLightfv(light, GL2.GL_DIFFUSE, dayColor, 0);
            gl.glLightfv(light, GL2.GL_SPECULAR, dayColor, 0);
            gl.glLightfv(light, GL2.GL_POSITION, pos, 0);
            gl.glEnable(light);
        } else {
            // setup moon
            gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, new float[]{0.15f, 0.15f, 0.18f, 1.0f}, 0);

            // setup 2 spotlights
            int[] spotlight = new int[]{GL2.GL_LIGHT0, GL2.GL_LIGHT1};
            float[] position1 = {0.0f + carCameraTranslation.x, 8.0f + carCameraTranslation.y,
                                    -0.0f + carCameraTranslation.z, 1.0f};
            float[] position2 = {0.0f + carCameraTranslation.x, 8.0f + carCameraTranslation.y,
                                    -15.0f + carCameraTranslation.z, 1.0f};
            float[][] positions = new float[][]{position1, position2};
            float[] sunColor = new float[]{0.85f, 0.85f, 0.85f, 1.0f};
            float[] spotDirectionPosition = new float[]{0.0f, -1.0f, 0.0f};
            // switch-off any light sources that are used in day mode
            // spotlight sources also move with the camera.
            for (int i = 0; i < spotlight.length; i++) {
                gl.glLightfv(spotlight[i], GL2.GL_POSITION, positions[i], 0);
                gl.glLightf(spotlight[i], GL2.GL_SPOT_CUTOFF, 75.0f);
                gl.glLightfv(spotlight[i], GL2.GL_SPOT_DIRECTION , spotDirectionPosition, 0);
                gl.glLightfv(spotlight[i], GL2.GL_SPECULAR, sunColor, 0);
                gl.glLightfv(spotlight[i], GL2.GL_DIFFUSE, sunColor, 0);
                gl.glEnable(spotlight[i]);
            }
        }

    }

    private void renderTrack(GL2 gl) {
        // TODO: Render the track.
        //       * Note: the track shouldn't be translated. It should be fixed.
    }

    private void renderCar(GL2 gl) {
        // TODO: Render the car.
        //       * Remember: the car position should be the initial position + the accumulated translation.
    }

    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // TODO: Destroy all models.
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        // Initialize display callback timer
        ani = new FPSAnimator(30, true);
        ani.add(drawable);
        glPanel.repaint();

        initModel(gl);
        ani.start();
    }

    public void initModel(GL2 gl) {
        // TODO: You can change OpenGL modes during implementation for debug purposes.
        //		 Remember to change OpenGL mode as it was before.
        gl.glCullFace(GL2.GL_BACK);
        gl.glEnable(GL2.GL_CULL_FACE);

        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_SMOOTH);

        car.init(gl);
        gameTrack.init(gl);
        isModelInitialized = true;
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // TODO Setup the projection matrix here.
        //		- It is recommended to use gluPerspective - with fovy 57.0
    }

    /**
     * Start redrawing the scene with 30 FPS
     */
    public void startAnimation() {
        if (!ani.isAnimating())
            ani.start();
    }

    /**
     * Stop redrawing the scene with 30 FPS
     */
    public void stopAnimation() {
        if (ani.isAnimating())
            ani.stop();
    }

    public void toggleNightMode() {
        isDayMode = !isDayMode;
    }

}
