package br.com.drestranho.jogogleiton.core;

public class Inimigo extends ObjTela {

	public boolean isVisible;
	public final int VELOCIDADE = 1;
	
	public Inimigo(int x, int y, String iPath) {
		super(x, y, iPath);
		isVisible = true;
	}

	@Override
	public void mover() {
		setX(getX()-VELOCIDADE);
		if (getX() < Util.X_MIN) {
			setX(Util.X_MAX);
			setX(getX()+10);
		}
	}

}
