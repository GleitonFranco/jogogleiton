package br.com.drestranho.jogogleiton.core;

import static playn.core.PlayN.*;

import java.util.ArrayList;
import java.util.List;

import playn.core.CanvasImage;
import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.Sound;
import pythagoras.f.Rectangle;

public class JogoGleiton extends Game.Default {
	static final float GRAVITY = 64;

	ImageLayer layer;
	CanvasImage pontosImagem;
	ImageLayer pontosLayer;
	int pontos;
	Image inimigo2;
	List<Inimigo> inimigos;
	List<Canhao> canhoes; 
	//	GroupLayer misseisLayer;
	Pointer.Adapter pointer;
	Sound tiro;

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
		Image bgImage = assets().getImage("images/fundo2.png");
		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
//		bgLayer.setScale(Util.Y_MAX, Util.X_MAX);
		graphics().rootLayer().add(bgLayer);
		
		Image soloImage = assets().getImage("images/solo.png");
		for (int i=0; i<Util.X_MAX; i+=17) {
			ImageLayer soloLayer = graphics().createImageLayer(soloImage);
			graphics().rootLayer().add(soloLayer);
			soloLayer.transform();
			soloLayer.setTranslation(i,Util.Y_MAX+25);
		}

		x = graphics().width() / 2;
		y = graphics().height() / 2;
		ax = GRAVITY;
		
		initPontos();
		
		tiro = assets().getSound("images/tiro");

		canhoes = new ArrayList<Canhao>();
		float distancia = (float) (Util.X_MAX/(Util.N_CANHAO+1));
		for ( int i=0; i< Util.N_CANHAO ; i++) {
			canhoes.add(new Canhao((i+1)*distancia,Util.Y_CANHAO, Util.SRC_CANHAO));//51x61
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
					if (c.getBounds().contains(event.x(),event.y())) {
						c.atirar();
						tiro.play();
					}
			}
		});

	}
	
	void initPontos() {
		pontos=0;
		pontosImagem = graphics().createImage(640, 50);
		pontosLayer = graphics().createImageLayer(pontosImagem);
		pontosLayer.setScale(3f);
		graphics().rootLayer().add(pontosLayer);
	}

	private void adicionaInimigo() {
		inimigos.add(new Inimigo(Util.X_MAX,20f,"images/inimigo_2.gif"));//22x16
	}

	@Override
	public void update(int delta) {
		// Save previous position for interpolation.
		px = x;
		py = y;
		
		checarColisoes();

		if (inimigos.size()>0) if (inimigos.get(inimigos.size()-1).getX()==Util.X_MAX-70) {
			adicionaInimigo();
		}

		for (Canhao c : canhoes) {
			c.mover();
		}

		for (Inimigo i : inimigos) {
			i.mover();
		}

	}
	
	public void checarColisoes() {
		for (Canhao c : canhoes) {
			for (Missil m : c.getMisseis()) {
				for (Inimigo i : inimigos) {
					if (i.getBounds().intersects(m.getBounds())) {
						i.isVisible=false;
						m.isVisible=false;
						pontos++;
					}
				}
				if (m.getY()<=0) {
					m.isVisible=false;
				}
			}
		}
		for (int i=0;i<inimigos.size()-1;i++) { 
			if (!inimigos.get(i).isVisible) {
				graphics().rootLayer().remove(inimigos.get(i).getLayer());
				
				inimigos.remove(i);
			}
		}
		
	}

	@Override
	public void paint(float alpha) {
		// the background automatically paints itself, so no need to do anything here!
		// Interpolate current position.
		//	    float x = (this.x * alpha) + (px * (1f - alpha));
		//	    float y = (this.y * alpha) + (py * (1f - alpha));

		
		// Pontuacao
		String s = Integer.toString(pontos);
		pontosImagem.canvas().clear();
		pontosImagem.canvas().setFillColor(0xff00ffff);
		pontosImagem.canvas().drawText(s, 10f, 20f);
		
		// Update the layer.

		for (Canhao c : canhoes) {
			c.getLayer().transform();
			c.getLayer().setTranslation(c.getX(),c.getY());
			for (Missil m : c.misseis) {
				m.getLayer().transform();
				m.getLayer().setTranslation(m.getX(),m.getY());					
			}
		}

		for (Inimigo i : inimigos) {
			i.getLayer().transform();
			i.getLayer().setTranslation(i.getX(),i.getY());
		}
	}

	public int updateRate() {
		// Request that update() run at 20 fps.
		return 0;
	}
}
