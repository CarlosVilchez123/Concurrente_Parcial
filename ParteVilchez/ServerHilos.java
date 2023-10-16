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
    public double[][] mensaje;

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

                while (running) {
                    System.out.println("entro al while del recivimiento de datos");
                    int arrayCount = in.readInt();
                    mensaje = new double[arrayCount][];
                    for (int i = 0; i < arrayCount; i++) {
                        int arrayLength = in.readInt();
                        double[] subArray = new double[arrayLength];
                        for (int j = 0; j < arrayLength; j++) {
                            subArray[j] = in.readDouble();
                        }
                        mensaje[i] = subArray;
                    }
                    if (messageListener != null) {
                        messageListener.messageReceived(mensaje);
                    }
                }

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
        System.out.println("entro al metodo de envia mensaje de ServerHilos");
        System.out.println(message.length);
        if (mOut != null) {
            try {
                mOut.writeInt(message.length);
                for (int i = 0; i < message.length; i++) {
                    System.out.println("entro al primer for");
                    mOut.writeInt(message[i].length);
                    for (int j = 0; j < message[i].length; j++) {
                        System.out.println(message[i][j]);
                        mOut.writeDouble(message[i][j]);
                    }
                }
                System.out.println("salio del bucle");
                mOut.flush();
            } catch (Exception e) {
                System.out.println("Error al enviar mensaje: " + e);
            }
        }
        
    }
}