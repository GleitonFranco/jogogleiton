package br.com.drestranho.jogogleiton.core;

public class Util {
	public static final boolean ANDROID=false;
	public static final float X_MAX=ANDROID?320:650;
	public static final float X_MIN=0;
	public static final float Y_MAX=ANDROID?200:440;
	public static final float Y_MIN=0;
	public static final float Y_MISSIL=Y_MAX-(ANDROID?10:51);
	public static final int N_CANHAO=3;
	public static final float Y_CANHAO=Y_MAX-(ANDROID?10:28);
	public static final float MARGEM_CANHAO = ANDROID?20:0;
	public static final float LARGURA_CANHAO=ANDROID?25:51;
	public static final float LARGURA_MISSIL=ANDROID?9:15;
	public static final String SRC_FUNDO=ANDROID?"images/fundo-p.png":"images/fundo.png";
	public static final String SRC_CANHAO=ANDROID?"images/canhao-p.png":"images/canhao.png";
	public static final String SRC_MISSIL=ANDROID?"images/missil-p.png":"images/missil.png";
}
