
public class KMeansClient {
    double[][] clientData;
    double[][] centroids;
    int[] indexes;
    int k;
    
    
    public KMeansClient(double[][] clientData, double[][] centroids){
        this.clientData = clientData;
        indexes = new int[clientData.length];
        this.centroids = centroids;
        k = centroids.length;
    }
    
    public void updateIndexes(){
        int n_points = indexes.length;
        
        for(int i = 0; i < n_points; i++){
            indexes[i] = closestCentroid(clientData[i]);
        }
    }
    
    public int[] getIndexes(){return indexes;}
    
    public void updateCentroids(double[][] centroids){
        this.centroids = centroids;
    }
    
    private int closestCentroid(double[] vector){
        double min = Double.MAX_VALUE;
        int minInd = 0;
        double dist;
        for(int i = 0; i < k; i++){
            dist = distance(vector, centroids[i]);
            //System.out.println(dist);
            if(dist < min){
                min = dist;
                minInd = i;
            } 
        }
        return minInd;
    }
    
    //vector1.length = vector2.length = clientData[0].length
    private double distance(double[] vector1, double[] vector2){
        int l = clientData[0].length;
        double norm = 0;
        for (int i = 0; i < l; i++){
            norm = norm + (vector1[i]*vector1[i] + vector2[i]*vector2[i])*1000 - 2000*vector1[i]*vector2[i];
            /*(vector1[i] - vector2[i])*(vector1[i] - vector2[i])*/
        }
        return norm;
    }
    
}
