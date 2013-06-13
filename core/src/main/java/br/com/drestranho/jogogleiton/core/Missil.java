package br.com.drestranho.jogogleiton.core;

import playn.core.GroupLayer;

public class Missil extends ObjTela {
	
	public final float VELOCIDADE = 6;
	
	public Missil(float x, float y, String iPath, GroupLayer grLayer) {
		super(x, y, iPath, grLayer);
		setX(getX()-largura/2);
		
	}

	@Override
	public void mover() {
		setY(getY()-VELOCIDADE);
		if (y < Util.Y_MIN) {
			isVisible = false;
		}
	}

	
	
}
