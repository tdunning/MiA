package mia.clustering.ch07;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mia.clustering.ClusterHelper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.mahout.clustering.classify.WeightedVectorWritable;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.kmeans.Kluster;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;

public class SimpleKMeansClustering {
  public static final double[][] points = { {1, 1}, {2, 1}, {1, 2},
                                           {2, 2}, {3, 3}, {8, 8},
                                           {9, 8}, {8, 9}, {9, 9}};
  
 
  public static List<Vector> getPoints(double[][] raw) {
    List<Vector> points = new ArrayList<Vector>();
    for (int i = 0; i < raw.length; i++) {
      double[] fr = raw[i];
      Vector vec = new RandomAccessSparseVector(fr.length);
      vec.assign(fr);
      points.add(vec);
    }
    return points;
  }
  
  public static void main(String args[]) throws Exception {
    
    int k = 2;
    
    List<Vector> vectors = getPoints(points);
    
    File testData = new File("testdata");
    if (!testData.exists()) {
      testData.mkdir();
    }
    testData = new File("testdata/points");
    if (!testData.exists()) {
      testData.mkdir();
    }
    
    Configuration conf = new Configuration();
    FileSystem fs = FileSystem.get(conf);
    ClusterHelper.writePointsToFile(vectors, conf, new Path("testdata/points/file1"));
    
    Path path = new Path("testdata/clusters/part-00000");
    SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf,
        path, Text.class, Kluster.class);
    
    for (int i = 0; i < k; i++) {
      Vector vec = vectors.get(i);
      Kluster cluster = new Kluster(vec, i, new EuclideanDistanceMeasure());
      writer.append(new Text(cluster.getIdentifier()), cluster);
    }
    writer.close();
    
    Path output = new Path("output");
    HadoopUtil.delete(conf, output);
    
    KMeansDriver.run(conf, new Path("testdata/points"), new Path("testdata/clusters"),
      output, new EuclideanDistanceMeasure(), 0.001, 10,
      true, 0.0, false);
    
    SequenceFile.Reader reader = new SequenceFile.Reader(fs,
        new Path("output/" + Kluster.CLUSTERED_POINTS_DIR
                 + "/part-m-00000"), conf);
    
    IntWritable key = new IntWritable();
    WeightedVectorWritable value = new WeightedVectorWritable();
    while (reader.next(key, value)) {
      System.out.println(value.toString() + " belongs to cluster "
                         + key.toString());
    }
    reader.close();
  }
  
}
