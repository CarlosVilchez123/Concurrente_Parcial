/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kmeans;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author Gian
 */
public class CSVreader {
    
    File f;
    
    public CSVreader(File f){
        this.f = f;
    }
    
    /***
     * Parses an CSV into an 2-dimensional array(m x n). Each row is a point in R^n space.
     * @return and m x n array
     * @throws FileNotFoundException 
     */
    public double[][] toMatrix() throws FileNotFoundException{
        double[][] dataMatrix;
        //double[][] dataMatrixT;
        String[] titles = new String[]{};
        //dataMatrix = new double[4][];
        try (Scanner in = new Scanner(f)) {
           
            
            if(in.hasNextLine()){
                //titles will have length equal to the number of columns of our matrix.
                titles = in.nextLine().split(",");
            }
            
            int counter = 0;
            while(in.hasNextLine()){
                /*for(int i = 0; i < dataMatrix.length; i++){
                    dataMatrix[i][counter] = Double.parseDouble(in.nextLine().split(",")[i]);
                }
                counter++;*/
                in.nextLine();
                counter++;
                //counter will be the number of rows of our matrix( m )
            }
            
            dataMatrix = new double[counter][titles.length];
            //System.out.println(dataMatrix.length + dataMatrix[1].length);
        }
        
        //We create a new scanner so we can read the file again, this time to pass the values to a matrix
        try (Scanner in2 = new Scanner(f)){
            in2.nextLine();
            int counter = 0;
            while(in2.hasNextLine()){
                String[] temp = in2.nextLine().split(",");
                for(int i = 0; i < dataMatrix[0].length; i++){
                    dataMatrix[counter][i] = Double.parseDouble(temp[i]);
                }
                counter++;
            }
        }
        return dataMatrix;
    }
}
