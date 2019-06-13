package edu.cg.models.Car;

import com.jogamp.opengl.GL2;

public class Materials {
	// TODO: Use this class to update the color of the vertexes when drawing.
	private static final float DARK_GRAY[] = { 0.2f, 0.2f, 0.2f };
	private static final float DARK_RED[] = { 0.25f, 0.01f, 0.01f };
	private static final float RED[] = { 0.7f, 0f, 0f };
	private static final float BLACK[] = { 0.05f, 0.05f, 0.05f };

	public static void SetMetalMaterial(GL2 gl, float[] color) {
		gl.glColor3fv(color, 0);
	}

	public static void SetBlackMetalMaterial(GL2 gl) {
		SetMetalMaterial(gl, BLACK);
	}

	public static void SetRedMetalMaterial(GL2 gl) {
		SetMetalMaterial(gl, RED);
	}

	public static void SetDarkRedMetalMaterial(GL2 gl) {
		SetMetalMaterial(gl, DARK_RED);
	}

	public static void SetDarkGreyMetalMaterial(GL2 gl) {
		SetMetalMaterial(gl, DARK_GRAY);
	}

	public static void setMaterialTire(GL2 gl) {
		float col[] = { .05f, .05f, .05f };
		gl.glColor3fv(col,0);
        float shine = 100f;
        setTexture(gl, shine, col, col);
	}

	public static void setMaterialRims(GL2 gl) {
        float[] col = new float[]{0.8f, 0.8f, 0.8f};
		gl.glColor3fv(DARK_GRAY,0);
        float shine = 20f;
        setTexture(gl, shine, col, col);
	}

    public static void SetGoldMetalMaterial(GL2 gl) {
        float[] ambient = new float[]{0.24725f, 0.1995f, 0.0745f, 0.5f};
        float[] diffuse = new float[]{0.75164f, 0.60648f, 0.22648f, 0.5f};
        float[] specular = new float[]{0.628281f, 0.555802f, 0.366065f, 0.5f};
        float shine = 0.4f;
        setTexture(gl, shine, ambient, diffuse, specular);
    }

    public static void SetChromeMetalMaterial(GL2 gl) {
        float[] ambient = new float[]{0.25f, 0.25f, 0.25f, 0.31f};
        float[] diffuse = new float[]{0.4f, 0.4f, 0.4f, 0.31f};
        float[] specular = new float[]{0.774597f, 0.774597f, 0.774597f, 0.5f};
        float shine = 0.6f;
        setTexture(gl, shine, ambient, diffuse, specular);
    }

    public static void SetJadeMaterial(GL2 gl) {
        float[] ambient = new float[]{0.135f, 0.2225f, 0.1575f, 1.0f};
        float[] diffuse = new float[]{0.54f, 0.89f, 0.63f, 1.0f};
        float[] specular = new float[]{0.316228f, 0.316228f, 0.316228f, 1.0f};
        float shine = 0.1f;
        setTexture(gl, shine, ambient, diffuse, specular);
    }

    public static void setGrassMaterial(GL2 gl) {
        float[] ambient = new float[]{0.00f, 0.05f, 0.0f, 1.0f};
        float[] diffuse = new float[]{0.4f, 0.5f, 0.4f, 1.0f};
        float[] specular = new float[]{0.04f, 0.7f, 0.04f, 1.0f};
        float shine = 0.078125f;
        setTexture(gl, shine, ambient, diffuse, specular);
    }

    public static void setAsphaltMaterial(GL2 gl) {
        float[] ambient = new float[]{0.15375f, 0.15f, 0.16625f, 1.0f};
        float[] diffuse = new float[]{0.68275f, 0.67f, 0.72525f, 1.0f};
        float[] specular = new float[]{0.332741f, 0.328634f, 0.346435f, 1.0f};
        float shine = 38.4f;
        setTexture(gl, shine, ambient, diffuse, specular);
    }

    public static void setWoodenBoxMaterial(GL2 gl) {
        float[] ambient = new float[]{0.4f, 0.4f, 0.4f, 1.0f};
        float[] diffuse = new float[]{0.7f, 0.4f, 0.2f, 1.0f};
        float[] specular = new float[]{0.4f, 0.3f, 0.15f, 1.0f};
        float shine = 25.6f;
        setTexture(gl, shine, ambient, diffuse, specular);
    }

    private static void setTexture(GL2 gl, float shine, float[] ambient, float[] diffuse, float[] specular) {
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, shine);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, ambient, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, diffuse, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, specular, 0);
    }

    private static void setTexture(GL2 gl, float shine, float[] diffuse, float[] specular) {
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, shine);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, diffuse, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, specular, 0);
    }

}
