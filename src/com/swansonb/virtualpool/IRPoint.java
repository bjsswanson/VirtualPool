package com.swansonb.virtualpool;

import processing.core.PApplet;
import processing.core.PVector;

public class IRPoint {

	private PVector position;
	private float size;
	private PApplet p;

	public IRPoint(PApplet p) {
		this.p = p;
		position = new PVector();
	}

	public void update(float x, float y, float size) {
		this.position = new PVector(x, y);
		this.size = size;
	}

	public float getX(){
		return position.x;
	}

	public float getY(){
		return position.y;
	}

	public void setX(float x) {
		position.set(x, position.y);
	}

	public void setY(float y) {
		position.set(position.x, y);
	}

	public PVector getPosition() {
		return position;
	}

	public void setPosition(PVector position) {
		this.position = position;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public void draw() {

		//float realX = p.map(getX(), 0.0f, 1.0f, 1.0f, 0.0f);
		//float x = realX * p.width;

		float x = position.x * p.width;
		float y = position.y * p.width;

		p.ellipse(x, y, 10, 10);
	}

	public double distance(IRPoint other) {
		return position.dist(other.getPosition());
	}

	@Override
	public String toString() {
		return "IRPoint{" +
				"position=" + position +
				'}';
	}
}
