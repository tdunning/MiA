package mia.clustering.ch12;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.mahout.common.Parameters;

public class CreateTwitterUserDataset {
  public static void main(String args[]) throws Exception {
    Parameters params = new Parameters();
    params.set("splitPattern", "\t");
    String inputDir = "/Users/robinanil/data/twitter_data/tweets.data";
    String outputDir = "twitter_seqfiles";
    params.set("input", inputDir);
    params.set("output", outputDir);
    params.set("selectedField", "2");
    params.set("groupByField", "0");
    //ByKeyGroupingJob.startJob(params);
    DoubleMetaphone filter = new DoubleMetaphone();
    System.out.println(filter.encode("Loke"));
    System.out.println(filter.encode("companymancomic"));
    System.out.println(filter.encode("webcomics"));
    System.out.println(filter.encode("@comic"));
  }
}
