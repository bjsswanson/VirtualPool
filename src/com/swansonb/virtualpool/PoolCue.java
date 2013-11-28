package com.swansonb.virtualpool;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.LinkedList;

public class PoolCue {

	private static final int MAX_STORED_POSITIONS = 5;
	private static final float FORCE_MULTIPLIER = 5;

	private static final float RADIUS = 20;
	private static final int CUE_LENGTH = 100;

	private PApplet p;
	private WiiMote wiiMote;

	private IRPoint front;
	private IRPoint back;
	private LinkedList<PVector> storedPositions = new LinkedList<PVector>();

	private Ball white;
	private boolean oldB;

	public PoolCue(PApplet p, Ball white){
		this.p = p;
		this.white = white;
		this.oldB = false;
		setup();
	}

	public void setup() {
		wiiMote = new WiiMote(p);
	}

	public void update() {
		front = calculateFront();
		back = calculateBack(front);
		storePosition();
		draw();
	}

	public void draw() {
		drawCue();
		drawPoints();

		oldB = wiiMote.isButtonB();

//		drawLine();
//		drawPoints();

	}

	private void drawCue() {
		if(front != null && back != null){

			PVector cueDirection = PVector.sub(front.getPosition(), back.getPosition());
			cueDirection.normalize();

			PVector cue = PVector.mult(cueDirection, CUE_LENGTH);
			PVector tip = PVector.sub(white.getPosition(), PVector.mult(cueDirection, RADIUS));

			drawLine(tip,  PVector.sub(tip, cue));

			if(wiiMote.isButtonB() && !oldB){
				white.setVelocity(PVector.mult(cueDirection, FORCE_MULTIPLIER));
			}
		}
	}

	private void drawLine(PVector start, PVector end) {
		p.line(start.x, start.y, end.x, end.y);
	}

	private void drawIrLine() {
		if(front != null && back != null){
			p.line(front.getX() * p.width, front.getY() * p.width, back.getX() * p.width, back.getY() * p.width);
		}
	}

	private void drawPoints() {

		for(IRPoint point : wiiMote.getPoints()){
			if(point.equals(front)){
				p.fill(0,255,0);
			}

			if(point.equals(back)){
				p.fill(255,0,0);
			}

			point.draw();
			p.fill(0);
		}
	}

	private void storePosition(){
		if(front != null){
			storedPositions.addFirst(new PVector(front.getX(), front.getY()));

			if(storedPositions.size() > MAX_STORED_POSITIONS){
				storedPositions.removeLast();
			}
		}
	}

	private IRPoint calculateFront(){

		IRPoint front = null;
		double maxDistance = 0;

		for(IRPoint point : wiiMote.getPoints()){

			double distanceToOthers = 0;

			for(IRPoint other : wiiMote.getPoints()){
				if(!point.equals(other)){
					distanceToOthers += point.distance(other);
				}
			}

			if(distanceToOthers > maxDistance){
				front = point;
				maxDistance = distanceToOthers;
			}
		}

		return front;
	}

	private IRPoint calculateBack(IRPoint front){
		IRPoint back = null;
		double maxDistance = 0;

		if(front != null){
			for(IRPoint point : wiiMote.getPoints()){
				if(!point.equals(front)){
					double distance = front.distance(point);
					if(distance > maxDistance){
						back = point;
						maxDistance = distance;
					}
				}
			}
		}

		return back;
	}

}
