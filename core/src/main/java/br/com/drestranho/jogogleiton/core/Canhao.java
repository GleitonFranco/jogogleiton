package br.com.drestranho.jogogleiton.core;

import java.util.ArrayList;
import java.util.List;

public class Canhao extends ObjTela {

	public List<Missil> misseis;
	
	public Canhao(float x, float y, String iPath) {
		super(x, y, iPath);
		misseis = new ArrayList<Missil>();
	}
	
	@Override
	public void mover() {
		for (Missil m : misseis) {
			m.mover();
		}
	}
	
	public void atirar() {
		misseis.add(new Missil(getX()+35/2,Util.Y_MISSIL,"images/missil.png"));//15x35 +getLayer().width()/2
		System.out.println(getLayer().width());
	}
	
}
