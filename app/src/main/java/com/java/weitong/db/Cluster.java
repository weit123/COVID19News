package com.java.weitong.db;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.DensityBasedSpatialClustering;
import net.sf.javaml.core.Dataset;

public class Cluster {
    Dataset data;
    Clusterer dbscan = new DensityBasedSpatialClustering();
}
