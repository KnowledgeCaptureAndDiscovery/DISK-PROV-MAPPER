package io.github.knowledgecaptureanddiscovery.diskprovmapper;

import java.net.URI;
import java.net.URISyntaxException;

public class Utils {

  public static String getFragment(String uriStr) throws URISyntaxException {
    URI uri = new URI(uriStr);
    String path = uri.getPath();
    return path.substring(path.lastIndexOf('/') + 1);
  }
}
