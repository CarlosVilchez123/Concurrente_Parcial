/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmeans;

import java.io.File;
import java.io.FileNotFoundException;
//import java.util.Arrays;
import kmeansClient.*;

/**
 *
 * @author Gian
 */
public class Main {
    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        /*Reading a csv file into a matrix.
        * Initializing an object kmeansServer with said matrix.
        * Number of clients and value of k can be modified accordingly.
        */
        
        File f;
        
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) f = new File(".\\src\\kmeans\\Data\\normalizedDataOne.csv");
        else if (os.contains("nix") || os.contains("aix") || os.contains("nux")) f = new File("./src/kmeans/Data/normalizedDataOne.csv");
        else f = new File(".\\src\\kmeans\\Data\\normalizedDataOne.csv");
    //Operating system is based on Linux/Unix/*AIX

        
        CSVreader csvReader = new CSVreader(f);
        double[][] dataSet = csvReader.toMatrix();
        KMeansServer server = new KMeansServer(dataSet, 2, 3);
        
        //printing stuff to see it can convince myself it works
        System.out.println("DataSet: ");
        server.printDataSet();
        System.out.println("Indices iniciales(Iteracion 0)");
        server.printIndexes();
        
        double[][] client1Data = server.divideDataSet(1);
        double[][] client2Data = server.divideDataSet(2);
        
        KMeansClient client1 = new KMeansClient(client1Data,server.getCentroids());
        KMeansClient client2 = new KMeansClient(client2Data,server.getCentroids());
        
        //server.printMatrix(server.getCentroids());
        
        int i = 1;
        while(!server.compareIndexes()){
            
            client1.updateIndexes();
            client2.updateIndexes();
            server.updateIndexes(client1.getIndexes(), client2.getIndexes());
            server.updateCentroids();
            client1.updateCentroids(server.getCentroids());
            client2.updateCentroids(server.getCentroids());
            //server.printArray(client1.getIndexes());
            //server.printArray(client2.getIndexes());
            System.out.println("Iteracion " + i);
            server.printArray(server.getIndexes());
            //server.printMatrix(server.getCentroids());
            i++;
        }
        
        //ÍNDICES FINALES
        //server.printArray(server.getIndexes());
        
        
        //I divite the data set so every client has a subset to work with.
        /*double[][] client1Set = kmeansServer.divideDataSet(1);
        
        //printing stuff to see it can convince myself it works
        kmeansServer.printMatrix(client1Set);
        //System.out.println("");
        
        //I initialize a kmeansClient object
        KMeansClient kmeansClient = new KMeansClient(client1Set, kmeansServer.getCentroids());
        
        kmeansServer.printMatrix(kmeansServer.getCentroids());
        
        kmeansClient.updateIndexes();
        
        kmeansServer.printArray(kmeansClient.getIndexes());
        
        kmeansServer.updateIndexes(kmeansClient.getIndexes());
        
        kmeansServer.printIndexes();
        
        kmeansServer.updateCentroids();
        
        kmeansServer.printMatrix(kmeansServer.getCentroids());
        
        kmeansClient.updateCentroids(kmeansServer.getCentroids());
        
        kmeansClient.updateIndexes();
        
        kmeansServer.printArray(kmeansClient.getIndexes());
        
        kmeansServer.updateIndexes(kmeansClient.getIndexes());
        
        kmeansServer.printIndexes(); */
        
        //kmeansServer.printMatrix(kmeansServer.divideDataSet(2));
        //System.out.println("");
        //kmeansServer.printMatrix(kmeansServer.divideDataSet(3));
        //System.out.println("");
        //kmeansServer.printMatrix(kmeansServer.divideDataSet(5));
        
        
        /*File f = new File(".\\src\\kmeans\\Data\\normalizedDataOne.csv");
        CSVreader csvReader = new CSVreader(f);
        double[][] toMatrix = csvReader.toMatrix();
        printMatrix(toMatrix);*/
        // TODO code application logic here
    }
    
}
