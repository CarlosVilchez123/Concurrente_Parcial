/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package kmeans;

//import java.io.*;

//import java.util.Arrays;


/**
 * Class that encapsulates the tasks of the server side of the implementation.
 * @author Gian
 */
public class KMeansServer {
    double[][] dataSet;
    int nClients;
    int[] previousIndexes;
    int[] indexes;
    int k;
    //int[] classify;
    double[][] centroids;
    
    /***
     * 
     * @param dataSet The set of points to which we'll apply the algorithm. Each point is one row of the matrix.
     * @param nClients The number of clients the server will connect/is connected to.
     * @param k the number of clusters in which we want to split our data.
     */
    public KMeansServer(double[][] dataSet,int nClients, int k){
        this.dataSet = dataSet;
        this.nClients = nClients;
        this.k = k;
        previousIndexes = new int[dataSet.length];
        initializeIndexes();
        initializeCentroids();
    }
    
    /***
     * An array that indicates to which cluster each element dataSet[i] belongs to
     * It initializes the values in a naive way.
     */
    private void initializeIndexes(){
        
        int n_points = dataSet.length;
        
        indexes = new int[n_points];
        
        //int factor = (n_points/k)*k;
        
        //int l = (n_points%nClients == 0 || whichClient + factor >= n_points)? n_points/nClients:(n_points/nClients + 1);
        
        for(int i = 0; i < k; i++){
            for(int j = i; j < n_points; j = j + k){
                //System.arraycopy(dataSet[j], 0, clientData[(j - whichClient)/nClients], 0, dataSet[0].length);
                indexes[j] = i;
            }
        }
        
        
        //return clientData;
        
        /*int n_points = dataSet.length;
        int factor = (n_points%nClients == 0)?  n_points/nClients : n_points/nClients + 1;
        
        indexes = new int[n_points];
        
        for (int i = 0; i < n_points; i++){
            indexes[i] = i/factor;
        }*/
    }
    
    /***
     * This will take care of initializing values of centroids[][]
     * It will also call to updateCentroids once so we get the initial values of our iteration 
     * If you don't know what iteration I'm talking about i recommend checking the wikipedia page on k-means
     */
    private void initializeCentroids(){
        //centroids = new double[k][dataSet[0].length];        
        updateCentroids();
    }
    
    /***
     * basically use the current indexes to calculate the centroids
     */
    public void updateCentroids(){
        //the number of points on our dataSet. You couldn't tell? 
        int n_points = indexes.length;
        
        centroids = new double[k][dataSet[0].length];
        
        //basically counts the number of elements of every cluster.(so we can calculate the mean later)
        int[] cardinals = new int[k];
        //for(int i = 0; i < k; i++) cardinals[i] = 0;//we initialize it do java doesn't bother us with null exception shenanigans
        //and we pick 0 because we have to count from 0, of course.//Apparently that wasn't needed.
        
        
        //We sum the elements of each cluster.
        for (int i = 0; i < n_points ; i++){
            centroids[indexes[i]] = sum(centroids[indexes[i]], dataSet[i]);
            cardinals[indexes[i]] = cardinals[indexes[i]] + 1;
        }
        
        //it wouldn't be a mean if we didn't divide into the number of elements of each set, right?
        for(int i = 0; i < k; i++){
            for(int j = 0; j < centroids[0].length; j++){
                centroids[i][j] = centroids[i][j]/cardinals[i];
            }          
        }
    }
    
    public double[][] getCentroids(){
        return centroids;
    }
    
    /***
     * This will update the indexes with every index subarray the clients return. It's very finicky stuff.
     * @param index 
     */
    public void updateIndexes(int[]... index){
        //int l = index.length;
        previousIndexes = indexes.clone();
        for(int i = 0; i < index.length; i++){
            for (int j = 0; j < index[i].length; j++){
                indexes[i + j*nClients] = index[i][j] ;
            }
        }
    }
    
    public boolean compareIndexes(){
        int l = indexes.length;
        boolean flag = true;
        for(int i = 0; i < l; i++){
            if (indexes[i] != previousIndexes[i]) flag = false;
        }
        return flag;
    }
    
    public int[] getIndexes(){
        return indexes;
    }
    
    private double[] sum(double[] vector1, double[] vector2){
        double[] sumOfVectors;
        int l = vector1.length;
        sumOfVectors = new double[l];
        for(int i = 0; i < l; i++){
            sumOfVectors[i] = vector1[i] + vector2[i];
        }
        return sumOfVectors;
    }
    
    
    
    public double[][] divideDataSet(int whichClient){
        
        double[][] clientData;
        
        int n_points = dataSet.length;
        
        whichClient = whichClient - 1; //just accomodating for 0-based indexing
        
        int factor = (n_points/nClients)*nClients;
        /*only really matters when n_points is not a multiple of nClients, in which case it takes the value of the
        biggest multiple of nClients that is less than n_points.  */
        
        //int k = (whichClient + factor >= n_points)? n_points/nClients:(n_points/nClients + 1) ;
        
        int l = (n_points%nClients == 0 || whichClient + factor >= n_points)? n_points/nClients:(n_points/nClients + 1);
        //this computes the length of clientData. 
        
        clientData = new double[l][dataSet[0].length];
        
        for(int i = whichClient; i < n_points; i = i + nClients){
            System.arraycopy(dataSet[i], 0, clientData[(i - whichClient)/nClients], 0, dataSet[0].length);
        }
        
        return clientData;
        /*whichClient = whichClient - 1;
        
        double[][] clientData;
        
        int n_points = indexes.length;
        int factor = (n_points%nClients == 0)?  n_points/nClients : n_points/nClients + 1;
        //int l = (whichClient != nClients - 1 && n_points%nClients == 0)?  2:n_points%nClients + 1;
        if (whichClient == nClients  - 1 && n_points%nClients != 0){
            int remainder = n_points%factor;
            clientData = new double[remainder][dataSet[0].length];
            for(int i = (nClients - 1)*factor; i < (nClients - 1)*factor + remainder; i++){
                System.arraycopy(dataSet[i], 0, clientData[i - (nClients - 1)*factor], 0, dataSet[0].length);
            }
            return clientData;
        }
        
        clientData = new double[factor][dataSet[0].length];
        
        for(int i = whichClient*factor; i < (whichClient + 1)*factor; i++){
            System.arraycopy(dataSet[i], 0, clientData[i - whichClient*factor], 0, dataSet[0].length);
        }
        
        return clientData;*/
    }
    
    
    public void printDataSet(){
        printMatrix(dataSet);
    }
            
    /**
     * Esta funcion imprime el dataSet de manera ordenada
     * @param matrix The matrix we want to print
     */
    public void printMatrix(double[][] matrix){
        int n_rows = matrix.length;
        int n_cols = matrix[0].length;
        for(int i = 0; i < n_rows; i++){
            for(int j = 0; j < n_cols; j++){
                System.out.print(i + " " + matrix[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
    public void printIndexes(){
        printArray(indexes);
    }
    
    public void printArray(int[] vector){
        int n_elem = vector.length;
        for(int i = 0; i < n_elem; i++){
            System.out.print(" " + vector[i]);
        }
        System.out.println("");
        System.out.println("");
    }
    
}
