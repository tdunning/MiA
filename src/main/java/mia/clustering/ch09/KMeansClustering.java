package mia.clustering.ch09;

import java.util.List;

import mia.clustering.ClusterHelper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.kmeans.RandomSeedGenerator;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.distance.CosineDistanceMeasure;
import org.apache.mahout.common.distance.DistanceMeasure;

public class KMeansClustering {

	public static void main(String args[]) throws Exception {
		String inputDir = "reuters-vectors";

		Configuration conf = new Configuration();
		String vectorsFolder = inputDir + "/tfidf-vectors";
		Path samples = new Path(vectorsFolder + "/part-r-00000");
		Path output = new Path("output");
		HadoopUtil.delete(conf, output);
		DistanceMeasure measure = new CosineDistanceMeasure();

		Path clustersIn = new Path(output, "random-seeds");
		RandomSeedGenerator.buildRandom(conf, samples, clustersIn, 3, measure);
		KMeansDriver.run(conf, samples, clustersIn, output, 0.01, 10, true,
				0.0, true);

		List<List<Cluster>> Clusters = ClusterHelper.readClusters(conf, output);

		for (Cluster cluster : Clusters.get(Clusters.size() - 1)) {
			System.out.println("Cluster id: " + cluster.getId() + " center: "
					+ cluster.getCenter().asFormatString());
		}
	}
}
