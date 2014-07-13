package mia.clustering.ch09;

import java.util.List;

import mia.clustering.ClusterHelper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.distance.CosineDistanceMeasure;

public class KMeansWithCanopyClustering {

	public static void main(String args[]) throws Exception {
		String inputDir = "reuters-vectors";

		Configuration conf = new Configuration();
		String vectorsFolder = inputDir + "/tfidf-vectors";
		Path samples = new Path(vectorsFolder + "/part-r-00000");

		Path output = new Path("output");
		HadoopUtil.delete(conf, output);

	    Path canopyCentroids = new Path(output, "canopy-centroids");
	    Path clusterOutput = new Path(output, "clusters");
		
		CanopyDriver.run(conf, samples, canopyCentroids, new CosineDistanceMeasure(),
				0.7, 0.5, false, 0, false);

	    KMeansDriver.run(conf, new Path(vectorsFolder), new Path(canopyCentroids, "clusters-0-final"), 
	            clusterOutput, 0.01, 20, true, 0.0, false);
		
	    List<List<Cluster>> Clusters = ClusterHelper.readClusters(conf, clusterOutput);
		for (Cluster cluster : Clusters.get(Clusters.size() - 1)) {
			System.out.println("Cluster id: " + cluster.getId() + " center: "
					+ cluster.getCenter().asFormatString());
		}
	}
}
