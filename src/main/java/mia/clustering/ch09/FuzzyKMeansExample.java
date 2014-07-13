/*
 * Source code for Listing 9.6
 * 
 */

package mia.clustering.ch09;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mia.clustering.ClusterHelper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.fuzzykmeans.FuzzyKMeansDriver;
import org.apache.mahout.clustering.kmeans.RandomSeedGenerator;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.math.Vector;

public class FuzzyKMeansExample {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		List<Vector> sampleData = new ArrayList<Vector>();

		RandomPointsUtil.generateSamples(sampleData, 400, 1, 1, 3);
		RandomPointsUtil.generateSamples(sampleData, 300, 1, 0, 0.5);
		RandomPointsUtil.generateSamples(sampleData, 300, 0, 2, 0.1);

		int k = 3;
		
	    File testData = new File("input");
	    if (!testData.exists()) {
	      testData.mkdir();
	    }

	    Configuration conf = new Configuration();
	    Path samples = new Path("input/file1");
	    ClusterHelper.writePointsToFile(sampleData, conf, samples);

		Path output = new Path("output");
		HadoopUtil.delete(conf, output);

		Path clustersIn = new Path(output, "random-seeds");
		DistanceMeasure measure = new EuclideanDistanceMeasure();

		RandomSeedGenerator.buildRandom(conf, samples, clustersIn, k, measure);
		FuzzyKMeansDriver.run(conf, samples, clustersIn, output, 0.01, 10,
				3, true, true, 0.0, true);

		List<List<Cluster>> Clusters = ClusterHelper.readClusters(conf, output);

		for (Cluster cluster : Clusters.get(Clusters.size() - 1)) {
			System.out.println("Fuzzy Cluster id: " + cluster.getId() + " center: "
					+ cluster.getCenter().asFormatString());
		}
	}
}
