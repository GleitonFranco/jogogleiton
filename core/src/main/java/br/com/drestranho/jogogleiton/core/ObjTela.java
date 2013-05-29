package br.com.drestranho.jogogleiton.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import java.awt.event.KeyEvent;

import playn.core.Image;
import playn.core.ImageLayer;
//import java.awt.Rectangle;
import pythagoras.f.Rectangle;

public class ObjTela {
	float x,y,dx,dy,largura,altura;
	ImageLayer layer;
	public boolean isVisible;
	
	public ObjTela(float x, float y, String iPath) {
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
		Image imagem = assets().getImage(iPath);
		layer = graphics().createImageLayer(imagem);
		graphics().rootLayer().add(layer);
		altura = imagem.height();
		largura = imagem.width();
		isVisible = true;
	}
	
	public ImageLayer getLayer() {
		return layer;
	}

	public void setLayer(ImageLayer layer) {
		this.layer = layer;
	}

	public void keyPressed(KeyEvent tecla) {
		int codigo = tecla.getKeyCode();
		if (codigo == KeyEvent.VK_UP) dy=-1;
		if (codigo == KeyEvent.VK_DOWN) dy=+1;
		if (codigo == KeyEvent.VK_LEFT) dx=-1;
		if (codigo == KeyEvent.VK_RIGHT) dx=+1;
		
	}
	
	public void keyReleased(KeyEvent tecla) {
		int codigo = tecla.getKeyCode();
		if (codigo == KeyEvent.VK_UP) dy=0;
		if (codigo == KeyEvent.VK_DOWN) dy=0;
		if (codigo == KeyEvent.VK_LEFT) dx=0;
		if (codigo == KeyEvent.VK_RIGHT) dx=0;
	}
	
	public void mover() {
		x+=dx;
		y+=dy;
		if (y < Util.Y_MIN) y = Util.Y_MIN;
		if (y > Util.Y_MAX-altura) y = Util.Y_MAX-altura;
		if (x < Util.X_MIN) x = Util.X_MIN;
		if (x > Util.X_MAX-largura) x = Util.X_MAX-largura;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), layer.image().width(), layer.image().height());
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public float getLargura() {
		return largura;
	}

	public void setLargura(float largura) {
		this.largura = largura;
	}

	public float getAltura() {
		return altura;
	}

	public void setAltura(float altura) {
		this.altura = altura;
	}

	public Image getImagem() {
		return layer.image();
	}

	public void setImagem(Image imagem) {
		this.layer.setImage(imagem);
	}
	
}
