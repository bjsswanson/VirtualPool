package com.swansonb.virtualpool;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.List;

public class Ball {

	private static final float DEFAULT_RADIUS = 20;
	private static final float DEFAULT_MASS = 100;
	private static final float RESTITUTION = 0.8f;
	private static final float FRICTION = 0.01f;
	private static final float MAX_VELOCITY = 20f;

	private PApplet p;

	private float radius;
	private float mass;

	private PVector position;
	private PVector velocity;
	private PVector acceleration;

	public Ball(PApplet p, PVector position) {
		this.p = p;
		this.position = position;
		this.velocity = new PVector();
		this.acceleration = new PVector();
		this.radius = DEFAULT_RADIUS ;
		this.mass = DEFAULT_MASS;
	}

	public Ball(PApplet p, float x, float y) {
		this(p, new PVector(x, y));
	}

	public void update(List<Ball> balls) {
		velocity.add(acceleration);
		friction();

		for(Ball ball : balls){
			if(!this.equals(ball)){
				if(colliding(ball)){
					collide(ball);
				}
			}
		}

		velocity.limit(MAX_VELOCITY);
		if(velocity.mag() < FRICTION ) {
			velocity = new PVector(0f, 0f, 0f);
		}

		position.add(velocity);
		checkEdges();

		draw();
	}

	public void friction(){
		float signX = velocity.x / Math.abs(velocity.x);
		float frictionX = Float.isNaN(signX) ? 0 : signX * FRICTION;

		float signY = velocity.y / Math.abs(velocity.y);
		float frictionY = Float.isNaN(signY) ? 0 : signY * FRICTION;
		if(velocity.mag() >= FRICTION){
			velocity.sub(frictionX, frictionY, 0f);
		}
	}

	public void draw() {
		float diameter = radius * 2;

		p.fill(255);
		p.ellipse(position.x, position.y, diameter, diameter);
	}

	void checkEdges() {
		if (position.x > p.width) {
			position.x = 0;
		} else if (position.x < 0) {
			position.x = p.width;
		}

		if (position.y > p.height) {
			position.y = 0;
		} else if (position.y < 0) {
			position.y = p.height;
		}
	}

	public boolean colliding(Ball o){
		float distance = position.dist(o.getPosition());
		float combinedDiameters = radius + o.getRadius();

		if(distance < combinedDiameters){
			return true;
		}

		return false;
	}

	public void collide(Ball o) {
		PVector delta = PVector.sub(position, o.getPosition());
		float d = delta.mag();

		// minimum translation distance to push balls apart after intersecting
		PVector mtd = PVector.mult(delta, ((getRadius() + o.getRadius()) - d) / d);

		// resolve intersection --
		// inverse mass quantities
		float im1 = 1 / getMass();
		float im2 = 1 / o.getMass();

		// push-pull them apart based off their mass
		setPosition(PVector.add(position, PVector.mult(mtd, (im1 / (im1 + im2)))));
		o.setPosition(PVector.sub(o.getPosition(), (PVector.mult(mtd, (im2 / (im1 + im2))))));

		// impact speed
		PVector v = PVector.sub(velocity, o.getVelocity());
		float vn = PVector.dot(v, mtd.normalize(null));

		// sphere intersecting but moving away from each other already
		if (vn > 0.0f) return;

		// collision impulse
		float i = (-(1.0f + RESTITUTION) * vn) / (im1 + im2);
		PVector impulse = PVector.mult(mtd.normalize(null), i);

		// change in momentum
		setVelocity(PVector.add(velocity, PVector.mult(impulse, im1)));
		o.setVelocity(PVector.sub(o.getVelocity(), PVector.mult(impulse, im2)));
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public PVector getPosition() {
		return position;
	}

	public void setPosition(PVector position) {
		this.position = position;
	}

	public PVector getVelocity() {
		return velocity;
	}

	public void setVelocity(PVector velocity) {
		this.velocity = velocity;
	}

	public PVector getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(PVector acceleration) {
		this.acceleration = acceleration;
	}
}
