import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.Socket;

public class ServerHilos extends Thread{
    private Socket client;
    private TcpServer tcpServer;
    private int clientID;
    private boolean running = false;
    private TcpServer.OnMessageReceived messageListener = null;

    public DataOutputStream mOut;
    public DataInputStream in;
    public double[] mensaje;

    ServerHilos[] clients;

    public ServerHilos(Socket client, TcpServer tcpServer, int clientID, ServerHilos[] clients){
        this.client=client;
        this.tcpServer = tcpServer;
        this.clientID=clientID;
        this.clients = clients;
    }

    public void run(){
        running=true;

        try{
            try{
                mOut = new DataOutputStream(client.getOutputStream());
                System.out.println("El servidor ha iniciado");

                messageListener = tcpServer.getMessageListener();
                in = new DataInputStream(client.getInputStream());

                System.out.println("CLIENT"+ "S: Received Message: '" + mensaje + "'");
            }catch(Exception e){
                System.out.println("Error del servdior :,v: "+ e);
            }finally{
                client.close();
            }
        }catch(Exception e){
            System.out.println("Error del cliente u.u: "+ e);
        }
    }

   public void enviarMensaje(double[][] message){
        if (mOut != null) {
            try {
                // Send the number of rows and columns in the 2D array
                mOut.writeInt(message.length);
                mOut.writeInt(message[0].length); // Assuming all rows have the same number of columns

                // Send the individual double values
                for (int i = 0; i < message.length; i++) {
                    for (int j = 0; j < message[i].length; j++) {
                        mOut.writeDouble(message[i][j]);
                    }
                }
                mOut.flush();
            } catch (Exception e) {
                System.out.println("Error al enviar mensaje: " + e);
            }
        }
    }
}