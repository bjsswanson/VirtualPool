package com.swansonb.virtualpool;

import processing.core.*;

import java.util.List;

public class VirtualPool extends PApplet {

	private PoolCue cue;
	private BallFactory ballFactory;
	private List<Ball> balls;

	public void setup() {
		size(1200, 600, OPENGL);
		background(255);
		smooth();
		fill(0);
		stroke(0);

		ballFactory = new BallFactory(this);
		balls = ballFactory.createBalls();

		Ball white = balls.get(0);

		cue = new PoolCue(this, white);
	}

	public void draw() {
		background(255);
		cue.update();

		for(Ball ball : balls){
			ball.update(balls);
		}
	}
}
