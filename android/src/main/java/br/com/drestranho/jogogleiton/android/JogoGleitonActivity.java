package br.com.drestranho.jogogleiton.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import br.com.drestranho.jogogleiton.core.JogoGleiton;

public class JogoGleitonActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new JogoGleiton());
  }
}
