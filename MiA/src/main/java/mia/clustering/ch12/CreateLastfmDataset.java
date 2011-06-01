package mia.clustering.ch12;

import mia.clustering.ch12.lastfm.VectorCreationJob;

import org.apache.hadoop.fs.Path;

public class CreateLastfmDataset {
  
  public static void main(String args[]) throws Exception {
    Path inputDir = new Path(
        "/Users/robinanil/data/Lastfm-ArtistTags2007/ArtistTags.txt");
    Path dictionaryDir = new Path("lastfm_dict");
    Path outputDir = new Path("lastfm_vectors");
    //VectorCreationJob.generateDictionary(inputDir, dictionaryDir);
    VectorCreationJob.createVectors(inputDir, outputDir,
      dictionaryDir);
  }
}
