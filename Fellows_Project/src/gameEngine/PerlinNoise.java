package gameEngine;

import java.util.Random;
public class PerlinNoise {

	public static final Random random = new Random();
	public int width, height;

	private vec2[] values;

	public PerlinNoise(int width, int height) {
		this.width = width;
		this.height = height;

		values = new vec2[(width + 1) * (height + 1)];

		for (int y = 0; y < height + 1; y++) {
			for (int x = 0; x < width + 1; x++) {
				int rot = random.nextInt(359);
				values[x + y * width] = Rotation.point(new vec2(0, 0), new vec2(0, -1), rot);
			}
		}
	}

	public float noise(float x, float y) {
		int gx0 = (int) (Math.floor(x));
		int gy0 = (int) (Math.floor(y));
		int gx1 = gx0 + 1;
		int gy1 = gy0 + 1;

		vec2 g00 = g(gx0, gy0);
		vec2 g10 = g(gx1, gy0);
		vec2 g11 = g(gx1, gy1);
		vec2 g01 = g(gx0, gy1);

		vec2 delta00 = new vec2(x - gx0, y - gy0);
		vec2 delta10 = new vec2(x - gx1, y - gy0);
		vec2 delta11 = new vec2(x - gx1, y - gy1);
		vec2 delta01 = new vec2(x - gx0, y - gy1);

		float s = dot(g00, new vec2(delta00.x, delta00.y));
		float t = dot(g10, new vec2(delta10.x, delta10.y));
		float u = dot(g11, new vec2(delta11.x, delta11.y));
		float v = dot(g01, new vec2(delta01.x, delta01.y));

		float sx = weigh(delta00.x);
		float sy = weigh(delta00.y);

		float a = lerp(sy, s, v);
		float b = lerp(sy, t, u);
		float h = lerp(sx, a, b);

		h /= 15;

		if(h > 1) h = 1;
		if(h < -1) h = -1;

		return h;
	}

	private float weigh(float x) {
		return 3 * (x * x) - 2 * (x * x * x);
	}

	private float lerp(float weight, float a, float b) {
		float result = a + weight * (b - a);
		return result;
	}

	private float dot(vec2 v0, vec2 v1) {
		return (v0.x * v1.x) + (v0.y * v1.y);
	}

	private vec2 g(int x, int y) {
		if (x < 0) x = 0;
		if (y < 0) y = 0;
		if (x >= width) x = width;
		if (y >= height) y = height;
		return values[x + y * width];
	}

	public static class vec2 {

		public float x, y;

		public vec2(float x, float y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return (int) x;
		}
		public int getY() {
			return (int) y;
		}

	}

	public static class Rotation {

		public static vec2 point(vec2 pivot, vec2 point, float rotation) {

			float rot = (float)(1f / 180 * rotation * Math.PI);

			float x = point.x - pivot.x;
			float y = point.y - pivot.y;

			float newx = (float)(x * Math.cos(rot) - y * Math.sin(rot));
			float newy = (float)(x * Math.sin(rot) + y * Math.cos(rot));


			newx += pivot.x;
			newy += pivot.y;

			return new vec2(newx, newy);
		}

	}

}