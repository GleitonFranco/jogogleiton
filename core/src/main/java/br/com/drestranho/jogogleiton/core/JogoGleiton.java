package br.com.drestranho.jogogleiton.core;

import static playn.core.PlayN.*;

import java.util.ArrayList;
import java.util.List;

import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;

public class JogoGleiton extends Game.Default {
	static final float GRAVITY = 64;

	ImageLayer layer;
	Image inimigo2;
	List<Inimigo> inimigos;
	List<Canhao> canhoes; 
	//	GroupLayer misseisLayer;
	Pointer.Adapter pointer;

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
		Image bgImage = assets().getImage("images/fundo.png");
		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
		graphics().rootLayer().add(bgLayer);

		x = graphics().width() / 2;
		y = graphics().height() / 2;
		ax = GRAVITY;

		canhoes = new ArrayList<Canhao>();
		float distancia = (float) (Util.X_MAX/(Util.N_CANHAO+1));
		for ( int i=0; i< Util.N_CANHAO ; i++) {
			canhoes.add(new Canhao((i+1)*distancia,Util.Y_CANHAO, "images/canhao.png"));//51x61
		}
		

		inimigos = new ArrayList<Inimigo>();
		adicionaInimigo();

		//	    misseisLayer = graphics().createGroupLayer();
		//	    graphics().rootLayer().add(misseisLayer);

		pointer().setListener(new Pointer.Adapter() {
			@Override
			public void onPointerEnd(Pointer.Event event) {
				//	    	    ImageLayer tiro = graphics().createImageLayer(assets().getImage("images/missil.png"));
				//	    	    tiro.setTranslation(event.x(), event.y());
				//	    	    misseisLayer.add(tiro);
				for (Canhao c : canhoes )
					if (c.getBounds().contains(event.x(), event.y()))
						c.atirar();
			}
		});

	}

	private void adicionaInimigo() {
		inimigos.add(new Inimigo(Util.X_MAX,20f,"images/inimigo_2.gif"));//22x16
	}

	@Override
	public void update(int delta) {
		// Save previous position for interpolation.
		px = x;
		py = y;

		if (inimigos.get(inimigos.size()-1).getX()==Util.X_MAX-70) {
			adicionaInimigo();
		}

		for (Canhao c : canhoes) {
			c.mover();
		}

		for (Inimigo i : inimigos) {
			i.mover();
		}

		// Update physics.
		//	    delta /= 1000;
		//	    vx += ax * delta;
		//	    vy += ay * delta;
		//	    x += 1;//vx * delta;
		//	    y += vy * delta;
	}

	@Override
	public void paint(float alpha) {
		// the background automatically paints itself, so no need to do anything here!
		// Interpolate current position.
		//	    float x = (this.x * alpha) + (px * (1f - alpha));
		//	    float y = (this.y * alpha) + (py * (1f - alpha));

		// Update the layer.

		for (Canhao c : canhoes) {
			c.getLayer().transform();
			c.getLayer().setTranslation(
					c.getX(),
					c.getY()
					);
			for (Missil m : c.misseis) {
				m.getLayer().transform();
				m.getLayer().setTranslation(
						m.getX(),
						m.getY()
						);
			}
		}

		for (Inimigo i : inimigos) {
			i.getLayer().transform();
			i.getLayer().setTranslation(
					i.getX(),
					i.getY()
					);
		}
		//	    layer.transform();	    
		//	    layer.setTranslation(
		//	      x - layer.image().width() / 2,
		//	      y - layer.image().height() / 2
		//	    );
	}

	public int updateRate() {
		// Request that update() run at 20 fps.
		return 0;
	}
}
