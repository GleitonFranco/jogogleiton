package br.com.drestranho.jogogleiton.core;

import static playn.core.PlayN.*;

import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;

public class JogoGleiton extends Game.Default {
	static final float GRAVITY = 64;

	ImageLayer layer;

	float px, py;
	float x, y;
	float vx, vy;
	float ax, ay;
		
	public JogoGleiton() {
		super(33); // call update every 33ms (30 times per second)
	}

	@Override
	public void init() {
		// create and add background image layer
		Image bgImage = assets().getImage("images/bg.png");
		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
		graphics().rootLayer().add(bgLayer);
		
		x = graphics().width() / 2;
	    y = graphics().height() / 2;
	    ay = GRAVITY;
	}

	@Override
	public void update(int delta) {
		// Save previous position for interpolation.
	    px = x;
	    py = y;

	    // Update physics.
	    delta /= 1000;
	    vx += ax * delta;
	    vy += ay * delta;
	    x += vx * delta;
	    y += vy * delta;
	}

	@Override
	public void paint(float alpha) {
		// the background automatically paints itself, so no need to do anything here!
	}
	
	public int updateRate() {
	    // Request that update() run at 20 fps.
	    return 50;
	  }
}
