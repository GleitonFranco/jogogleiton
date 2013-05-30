package br.com.drestranho.jogogleiton.core;

public class Missil extends ObjTela {
	
	public final float VELOCIDADE = 6;
	
	public Missil(float x, float y, String iPath) {
		super(x, y, iPath);
		setX(getX()-largura/2);
		
	}

	@Override
	public void mover() {
		setY(getY()-VELOCIDADE);
		if (y < Util.Y_MIN-altura) isVisible = false;
	}

	
	
}
