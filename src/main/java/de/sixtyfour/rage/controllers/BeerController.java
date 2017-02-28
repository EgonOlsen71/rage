package de.sixtyfour.rage.controllers;

import de.skaliant.wax.app.ControllerBase;

/**
 * A simple controller for 99 bottles of beer as a web app.
 * 
 * @author EgonOlsen
 *
 */
public class BeerController extends ControllerBase {

	private int max = 99;

	public void setMax(int max) {
		this.max = max;
	}

	public int getMax() {
		return this.max;
	}

	public String basic() {
		setMaxValue();
		return "basic_beer";
	}

	public String asm() {
		setMaxValue();
		return "asm_beer";
	}

	private void setMaxValue() {
		this.request.setAttribute("mx", Math.max(1, Math.min(99, max)));
	}

}
