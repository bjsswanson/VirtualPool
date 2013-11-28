package com.swansonb.virtualpool;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class BallFactory {

	public PApplet p;

	public BallFactory(PApplet p) {
		this.p = p;
	}

	public List<Ball> createBalls(){

		List<Ball> balls = new ArrayList<Ball>();

		Ball ball = new Ball(p, 900, 300);
		ball.setVelocity(new PVector(0, 0));
		balls.add(ball);

		balls.add(new Ball(p, 300, 300));
		balls.add(new Ball(p, 275, 275));
		balls.add(new Ball(p, 275, 325));
//		balls.add(new Ball(p, 250, 250));
//		balls.add(new Ball(p, 250, 300));
//		balls.add(new Ball(p, 250, 250));
//		balls.add(new Ball(p, 225, 225));
//		balls.add(new Ball(p, 225, 275));
//		balls.add(new Ball(p, 225, 325));
//		balls.add(new Ball(p, 225, 375));


		return balls;
	}

}
