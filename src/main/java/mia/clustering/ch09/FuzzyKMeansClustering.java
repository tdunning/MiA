package mia.clustering.ch09;

import java.util.List;

import mia.clustering.ClusterHelper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.fuzzykmeans.FuzzyKMeansDriver;
import org.apache.mahout.clustering.kmeans.RandomSeedGenerator;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.distance.CosineDistanceMeasure;
import org.apache.mahout.common.distance.DistanceMeasure;

public class FuzzyKMeansClustering {

	public static void main(String args[]) throws Exception {
		// the vectors
		String inputDir = "reuters-vectors";

		Configuration conf = new Configuration();
		String vectorsFolder = inputDir + "/tfidf-vectors";
		Path samples = new Path(vectorsFolder + "/part-r-00000");

		Path output = new Path("output");
		HadoopUtil.delete(conf, output);

		Path clustersIn = new Path(output, "random-seeds");
		DistanceMeasure measure = new CosineDistanceMeasure();

		RandomSeedGenerator.buildRandom(conf, samples, clustersIn, 3, measure);
		FuzzyKMeansDriver.run(conf, samples, clustersIn, output, 0.01, 10,
				3, true, true, 0.0, true);

		List<List<Cluster>> Clusters = ClusterHelper.readClusters(conf, output);
		for (Cluster cluster : Clusters.get(Clusters.size() - 1)) {
			System.out.println("Cluster id: " + cluster.getId() + " center: "
					+ cluster.getCenter().asFormatString());
		}
	}
}
