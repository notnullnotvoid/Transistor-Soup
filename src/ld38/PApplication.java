package ld38;

import processing.core.PApplet;

public class PApplication extends PApplet {
	private boolean strokeEnabled = true;
	private int strokeColor = 0xFF000000;
	private float strokeWidth = 0.5f;
	private int strokeCap = SQUARE;

	@Override
	public void stroke(int rgb) {
		strokeEnabled = true;
		strokeColor = rgb;
	}

	@Override
	public void stroke(float gray) {
		strokeEnabled = true;
		strokeColor = color(gray);
	}

	@Override
	public void stroke(float gray, float alpha) {
		strokeEnabled = true;
		strokeColor = color(gray, alpha);
	}

	@Override
	public void stroke(int rgb, float alpha) {
		strokeEnabled = true;
		strokeColor = color(rgb, alpha);
	}

	@Override
	public void stroke(float r, float g, float b) {
		strokeEnabled = true;
		strokeColor = color(r, g, b);
	}

	@Override
	public void stroke(float r, float g, float b, float a) {
		strokeEnabled = true;
		strokeColor = color(r, g, b, a);
	}

	@Override
	public void noStroke() {
		strokeEnabled = false;
	}

	@Override
	public void strokeWeight(float weight) {
		strokeWidth = weight * 0.5f;
	}

	@Override
	public void strokeCap(int type) {
		strokeCap = type;
	}

	@Override
	public void line(float x1, float y1, float x2, float y2) {
		super.noStroke();

		//setting and resetting one color is probably much faster
		//than pushing/popping the whole style stack
		int fillColor = g.fillColor;
		boolean fillEnabled = g.fill;
		fill(strokeColor);

		//copied from the libgdx rectLine() algorithm
		float d = dist(x1, y1, x2, y2);
		float tx = (y2 - y1) / d * strokeWidth;
		float ty = (x2 - x1) / d * strokeWidth;

		if (strokeCap == PROJECT) {
			x1 -= ty;
			x2 += ty;
			y1 -= tx;
			y2 += tx;
		}

		super.triangle(x1 - tx, y1 + ty, x1 + tx, y1 - ty, x2 - tx, y2 + ty);
		super.triangle(x2 + tx, y2 - ty, x2 - tx, y2 + ty, x1 + tx, y1 - ty);

		//TODO: ROUND projection mode

		if (fillEnabled)
			fill(fillColor);
		else
			noFill();
	}

	@Override
	public void rect(float x, float y, float w, float h) {
		super.noStroke();
		super.rect(x, y, w, h);

		float x1, x2, y1, y2;
		if (g.rectMode == CORNER) {
			x1 = x;
			y1 = y;
			x2 = x + w;
			y2 = y + h;
		} else if (g.rectMode == CORNERS) {
			x1 = x;
			y1 = y;
			x2 = w;
			y2 = h;
		} else if (g.rectMode == CENTER) {
			x1 = x - w * 0.5f;
			y1 = y - h * 0.5f;
			x2 = x + w * 0.5f;
			y2 = y + h * 0.5f;
		} else { //RADIUS
			x1 = x - w;
			y1 = y - h;
			x2 = x + w;
			y2 = y + h;
		}

		if (strokeEnabled) {
			line(x1, y1, x2, y1);
			line(x2, y1, x2, y2);
			line(x2, y2, x1, y2);
			line(x1, y2, x1, y1);
		}
	}

	@Override
	public void ellipse(float x, float y, float d1, float d2) {
		super.noStroke();
		super.ellipse(x, y, d1, d2);

		//TODO: support multiple ellipse modes

		//setting and resetting one color is probably much faster
		//than pushing/popping the whole style stack
		int fillColor = g.fillColor;
		boolean fillEnabled = g.fill;
		fill(strokeColor);

		int points = 2; //points per octant TODO: determine based on size, maybe?
		float ir1 = d1 * 0.5f - strokeWidth; //inner horizontal radius
		float ir2 = d2 * 0.5f - strokeWidth; //inner vertical radius
		float or1 = d1 * 0.5f + strokeWidth; //outer horizontal radius
		float or2 = d2 * 0.5f + strokeWidth; //outer vertical radius

		float sin1 = 0;
		float cos1 = 1;
		for (int i = 1; i <= points; ++i) {
			float theta = QUARTER_PI / points * i;
			float sin2 = sin(theta);
			float cos2 = cos(theta);

			ellipseImpl(x, y,
			            sin1 * ir1, cos1 * ir2,
			            sin1 * or1, cos1 * or2,
			            sin2 * or1, cos2 * or2);
			ellipseImpl(x, y,
			            sin1 * ir1, cos1 * ir2,
			            sin2 * ir1, cos2 * ir2,
			            sin2 * or1, cos2 * or2);

			ellipseImpl(x, y,
			            cos1 * ir1, sin1 * ir2,
			            cos1 * or1, sin1 * or2,
			            cos2 * or1, sin2 * or2);
			ellipseImpl(x, y,
			            cos1 * ir1, sin1 * ir2,
			            cos2 * ir1, sin2 * ir2,
			            cos2 * or1, sin2 * or2);

			sin1 = sin2;
			cos1 = cos2;
		}

		if (fillEnabled)
			fill(fillColor);
		else
			noFill();
	}

	private void ellipseImpl(float x, float y,
	                         float dx1, float dy1, float dx2, float dy2, float dx3, float dy3) {
		super.triangle(x + dx1, y + dy1, x + dx2, y + dy2, x + dx3, y + dy3);
		super.triangle(x - dx1, y + dy1, x - dx2, y + dy2, x - dx3, y + dy3);
		super.triangle(x + dx1, y - dy1, x + dx2, y - dy2, x + dx3, y - dy3);
		super.triangle(x - dx1, y - dy1, x - dx2, y - dy2, x - dx3, y - dy3);
	}

	@Override
	public void triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
		//TODO: do we need to optimize this?
		pushStyle();

		if (strokeEnabled)
			super.stroke(strokeColor);
		else
			super.noStroke();
		super.strokeWeight(strokeWidth * 2);
		super.triangle(x1, y1, x2, y2, x3, y3);

		popStyle();
	}

	@Override
	public void point(float x, float y) {
		int mode = g.rectMode;
		int color = g.fillColor;
		boolean fillEnabled = g.fill;

		rectMode(RADIUS);
		fill(strokeColor);

		super.rect(x, y, strokeWidth, strokeWidth);

		rectMode(mode);
		if (fillEnabled)
			fill(color);
		else
			noFill();
	}

	//TODO: arc, quad
}
