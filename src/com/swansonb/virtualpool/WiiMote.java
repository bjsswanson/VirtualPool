package com.swansonb.virtualpool;

import oscP5.OscP5;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class WiiMote {

	private PApplet p;
	private OscP5 osc;
	private IRPoint ir1, ir2, ir3, ir4;
	private List<IRPoint> points;
	private boolean buttonB;

	public WiiMote(PApplet p) {
		this.p = p;

		osc = new OscP5(this,9000);
		osc.plug(this, "ir1", "/wii/1/ir/xys/1");
		osc.plug(this, "ir2", "/wii/1/ir/xys/2");
		osc.plug(this, "ir3", "/wii/1/ir/xys/3");
		osc.plug(this, "buttonB","/wii/1/button/B");

		ir1 = new IRPoint(p);
		ir2 = new IRPoint(p);
		ir3 = new IRPoint(p);

		points = new ArrayList<IRPoint>();
		points.add(ir1);
		points.add(ir2);
		//points.add(ir3);

		buttonB = false;
	}

	void ir1(float _ir1x, float _ir1y, float _ir1s) {
		ir1.update(_ir1x, _ir1y, _ir1s);
	}

	void ir2(float _ir2x, float _ir2y, float _ir2s) {
		ir2.update(_ir2x, _ir2y, _ir2s);
	}

	void ir3(float _ir3x, float _ir3y, float _ir3s) {
		ir3.update(_ir3x, _ir3y, _ir3s);
	}

	void buttonB(float b) {
		buttonB = b > 0;
	}

	public IRPoint getIr1() {
		return ir1;
	}

	public IRPoint getIr2() {
		return ir2;
	}

	public IRPoint getIr3() {
		return ir3;
	}

	public List<IRPoint> getPoints() {
		return points;
	}

	public boolean isButtonB() {
		return buttonB;
	}
}
