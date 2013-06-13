package br.com.drestranho.jogogleiton.core;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;
import pythagoras.f.Rectangle;

public class Canhao extends ObjTela {

	public List<Missil> misseis;
	GroupLayer grLayer;
	boolean ocupado;
	
	public Canhao(float x, float y, String iPath, GroupLayer grLayer) {
		super(x, y, iPath, grLayer);
		misseis = new ArrayList<Missil>();
		this.grLayer= grLayer;
		ocupado=false;
	}
	
	@Override
	public void mover() {
		for (Missil m : misseis) {
			m.mover();
		}
	}
	
	public void atirar() {
		if (misseis.size()>0) return;
		misseis.add(new Missil(getX()+Util.LARGURA_CANHAO/2-Util.LARGURA_MISSIL/2,Util.Y_MISSIL,Util.SRC_MISSIL, grLayer));
	}

	public List<Missil> getMisseis() {
		return misseis;
	}

	public void setMisseis(List<Missil> misseis) {
		this.misseis = misseis;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(
				super.getBounds().x()-(Util.MARGEM_CANHAO)
				,super.getBounds().y()
				,super.getBounds().width()+(Util.MARGEM_CANHAO*2)
				,super.getBounds().height()+(Util.MARGEM_CANHAO)
				);
	}
	
	
	
}
