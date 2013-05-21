package br.com.drestranho.jogogleiton.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import br.com.drestranho.jogogleiton.core.JogoGleiton;

public class JogoGleitonJava {

  public static void main(String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    // use config to customize the Java platform, if needed
    JavaPlatform.register(config);
    PlayN.run(new JogoGleiton());
  }
}
