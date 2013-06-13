package br.com.drestranho.jogogleiton.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;

import java.util.ArrayList;
import java.util.List;

import playn.core.CanvasImage;
import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.Sound;

public class JogoGleiton extends Game.Default {
	static final float GRAVITY = 64;

	ImageLayer layer;
	CanvasImage pontosImagem;
	ImageLayer pontosLayer;
	int pontos;
	Image inimigo2;
	List<Inimigo> inimigos;
	List<Canhao> canhoes; 
	GroupLayer jogoLayer;
	GroupLayer splashLayer;
	GroupLayer vitoriaLayer;
	GroupLayer derrotaLayer;
	Pointer.Adapter pointer;
	Sound tiro;

	int fase=0;
	int inimigosAtual=1;

	public JogoGleiton() {
		super(33); // call update every 33ms (30 times per second)
	}

	@Override
	public void init() {
		
		splashLayer = graphics().createGroupLayer();
		Image bgSplashImage = assets().getImage(Util.SRC_SPLASH);
		ImageLayer bgSplashLayer = graphics().createImageLayer(bgSplashImage);
		splashLayer.add(bgSplashLayer);
		
		vitoriaLayer = graphics().createGroupLayer();
		Image bgVitoriaImage = assets().getImage(Util.SRC_VITORIA);
		ImageLayer bgVitoriaLayer = graphics().createImageLayer(bgVitoriaImage);
		vitoriaLayer.add(bgVitoriaLayer);
		vitoriaLayer.setScale(1.8f);
		
		derrotaLayer = graphics().createGroupLayer();
		Image bgderrotaImage = assets().getImage(Util.SRC_DERROTA);
		ImageLayer bgderrotaLayer = graphics().createImageLayer(bgderrotaImage);
		derrotaLayer.add(bgderrotaLayer);
		derrotaLayer.setScale(1.5f);
		
		jogoLayer = graphics().createGroupLayer();
		Image bgImage = assets().getImage(Util.SRC_FUNDO);
		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
		jogoLayer.add(bgLayer);
		
		Image soloImage = assets().getImage("images/solo.png");
		for (int i=0; i<Util.X_MAX; i+=17) {
			ImageLayer soloLayer = graphics().createImageLayer(soloImage);
			jogoLayer.add(soloLayer);
			soloLayer.transform();
			soloLayer.setTranslation(i,Util.Y_MAX);
		}

	
		initPontos();
		
		tiro = assets().getSound("images/tiro");

		canhoes = new ArrayList<Canhao>();
		float distancia = (float) (Util.X_MAX/(Util.N_CANHAO+1));
		for ( int i=0; i< Util.N_CANHAO ; i++) {
			canhoes.add(new Canhao((i+1)*distancia,Util.Y_CANHAO, Util.SRC_CANHAO, jogoLayer));//51x61
		}
		

		inimigos = new ArrayList<Inimigo>();
		adicionaInimigo();

		pointer().setListener(new Pointer.Adapter() {
			@Override
			public void onPointerEnd(Pointer.Event event) {
				if (fase==0) {
					fase++;
				} else if (fase==1) {
					for (Canhao c : canhoes ) {
						if (c.getBounds().contains(event.x(),event.y())) {
							c.atirar();
							tiro.play();
						}
					}
				}
			}
		});

		graphics().rootLayer().add(splashLayer);
		graphics().rootLayer().add(jogoLayer);
		graphics().rootLayer().add(vitoriaLayer);
		graphics().rootLayer().add(derrotaLayer);
	}
	
	void initPontos() {
		pontos=0;
		pontosImagem = graphics().createImage(640, 50);
		pontosLayer = graphics().createImageLayer(pontosImagem);
		pontosLayer.setScale(3f);
		jogoLayer.add(pontosLayer);
	}

	private void adicionaInimigo() {
		inimigos.add(new Inimigo(Util.X_MAX,20f,"images/inimigo_2.gif", jogoLayer));//22x16
//		Util.incrementaAvanco();
	}

	@Override
	public void update(int delta) {
		// Save previous position for interpolation.
		checarColisoes();
		if (inimigosAtual<Util.ETS_MAX)
		if (inimigos.size()>0) if (inimigos.get(inimigos.size()-1).getX()==Util.X_MAX-70) {
			adicionaInimigo();
			inimigosAtual++;
		} 
		for (Canhao c : canhoes) {
			c.mover();
		}
		for (Inimigo i : inimigos) {
			i.mover();
			if (i.getY()>Util.Y_CANHAO) fase=2;
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
						if(pontos==Util.ETS_MAX) fase=3;
					}
				}
			}
		}
		for (int i=0;i<inimigos.size()-1;i++) { 
			if (!inimigos.get(i).isVisible) {
				jogoLayer.remove(inimigos.get(i).getLayer());
				inimigos.remove(i);
			}
		}
		for (Canhao c : canhoes) {
			int i=-1;
			while(++i<c.getMisseis().size()) {
				if (!c.getMisseis().get(i).isVisible) {
					jogoLayer.remove(c.getMisseis().get(i).getLayer());
					c.getMisseis().remove(c.getMisseis().get(i));
				}
			}
		}
		
	}

	@Override
	public void paint(float alpha) {
		// Torna a camada visivel conforme a fase
		if (fase==0) {
			splashLayer.setVisible(true);
			jogoLayer.setVisible(false);
			derrotaLayer.setVisible(false);
			vitoriaLayer.setVisible(false);
		} else if (fase==1) {
			splashLayer.setVisible(false);
			jogoLayer.setVisible(true);
			derrotaLayer.setVisible(false);
			vitoriaLayer.setVisible(false);

			// Exibe Pontuacao
			String s = Integer.toString(pontos);
			pontosImagem.canvas().clear();
			pontosImagem.canvas().setFillColor(0xff00ffff);
			pontosImagem.canvas().drawText(s, 10f, 20f);
			
			// Atualiza layer para todos os elementos moveis
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
		} else if (fase==2) { //Derrota
			splashLayer.setVisible(false);
			jogoLayer.setVisible(false);
			derrotaLayer.setVisible(true);
			vitoriaLayer.setVisible(false);
		} else if (fase==3) { //Vitoria
			splashLayer.setVisible(false);
			jogoLayer.setVisible(false);
			derrotaLayer.setVisible(false);
			vitoriaLayer.setVisible(true);
		}
	}

	public int updateRate() {
		// Request that update() run at 20 fps.
		return 0;
	}
}
