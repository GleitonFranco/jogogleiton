package br.com.drestranho.jogogleiton.core;

public class Missil extends ObjTela {
	
	public final int VELOCIDADE = 2;
	
	public Missil(int x, int y, String iPath) {
		super(x, y, iPath);
		
	}

	@Override
	public void mover() {
		setY(getY()-VELOCIDADE);
		if (y < Util.Y_MIN-altura) isVisible = false;
	}

	
	
}
