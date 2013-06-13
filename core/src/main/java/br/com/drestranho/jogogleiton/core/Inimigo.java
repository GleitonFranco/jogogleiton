package br.com.drestranho.jogogleiton.core;

import playn.core.GroupLayer;

public class Inimigo extends ObjTela {

	public boolean isVisible;
	
	public Inimigo(float x, float y, String iPath, GroupLayer grLayer) {
		super(x, y, iPath, grLayer);
		isVisible = true;
	}

	@Override
	public void mover() {
		setX(getX()-Util.X_AVANCO);
		if (getX() < Util.X_MIN) {
			setX(Util.X_MAX);
			setY(getY()+Util.Y_AVANCO);
		}
	}

}
