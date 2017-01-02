package utilities;

import java.io.*;
import javax.swing.ImageIcon;


public class IconFactory {
  private IconFactory() {}

  public static ImageIcon getIcon(String resourceName) {
    try {
      InputStream input = IconFactory.class.getClassLoader().getResourceAsStream(resourceName);
      byte[] data = new byte [input.available()];
      input.read(data);
      return new ImageIcon(data, resourceName);
    } catch (IOException e) {
      return null;
    }catch (NullPointerException e) {
      return null;
    }
  }
}
