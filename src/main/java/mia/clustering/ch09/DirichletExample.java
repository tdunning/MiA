/*
 * Source code for Listing 9.8
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
import org.apache.mahout.clustering.ModelDistribution;
import org.apache.mahout.clustering.dirichlet.DirichletDriver;
import org.apache.mahout.clustering.dirichlet.models.DistributionDescription;
import org.apache.mahout.clustering.dirichlet.models.GaussianClusterDistribution;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.distance.ManhattanDistanceMeasure;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

public class DirichletExample {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		List<Vector> sampleData = new ArrayList<Vector>();

		RandomPointsUtil.generateSamples(sampleData, 400, 1, 1, 3);
		RandomPointsUtil.generateSamples(sampleData, 300, 1, 0, 0.5);
		RandomPointsUtil.generateSamples(sampleData, 300, 0, 2, 0.1);

		List<VectorWritable> points = new ArrayList<VectorWritable>();
		for (Vector sd : sampleData) {
			points.add(new VectorWritable(sd));
		}
		
	    Configuration conf = new Configuration();
	    File testData = new File("input");
	    if (!testData.exists()) {
	      testData.mkdir();
	    }

	    ClusterHelper.writePointsToFile(sampleData, conf, new Path("input/file1"));
		
	    Path output = new Path("output");
	    HadoopUtil.delete(conf, output);
	    
		ModelDistribution<VectorWritable> modelDist = new GaussianClusterDistribution(new VectorWritable(
				new DenseVector(2))); 
		DistributionDescription description = new DistributionDescription(modelDist.getClass().getName(),
		        RandomAccessSparseVector.class.getName(), ManhattanDistanceMeasure.class.getName(), 2);
	    DirichletDriver.run(conf, new Path("input"), output, description, 10, 20, 1.0, true,
	            true, 0, true);
		
	    List<List<Cluster>> Clusters = ClusterHelper.readClusters(conf, output);
	    
	    for (Cluster cluster : Clusters.get(Clusters.size() - 1)) {
	      System.out.println("Cluster id: " + cluster.getId() + " center: "
	                         + cluster.getCenter().asFormatString());
	    }
	
	}
}
