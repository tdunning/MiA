package mia.clustering;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

final class ClustersFilter implements PathFilter {
	  @Override
	  public boolean accept(Path path) {
	    String pathString = path.toString();
	    return pathString.contains("/clusters-");
	  }
}
