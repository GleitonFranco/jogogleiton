package br.com.drestranho.jogogleiton.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import br.com.drestranho.jogogleiton.core.JogoGleiton;

public class JogoGleitonHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform.Config config = new HtmlPlatform.Config();
    // use config to customize the HTML platform, if needed
    HtmlPlatform platform = HtmlPlatform.register(config);
    platform.assets().setPathPrefix("jogogleiton/");
    PlayN.run(new JogoGleiton());
  }
}
