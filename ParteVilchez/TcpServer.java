import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer{

    private KMeansServer Kserver;
    private double[][] message;
    private static final int SERVERPORT=4444;
    private OnMessageReceived messageListener=null;
    int numClient=0;
    private boolean running = false;
    ServerHilos[] sendClient = new ServerHilos[100];

    PrintWriter escribeEnElCliente;
    BufferedReader in;

    ServerSocket socket;

    public TcpServer(OnMessageReceived messageListener){
        this.messageListener = messageListener;
    }

    public void enviarMensajeTcp(double[][] mensaje){
        Kserver = new KMeansServer(mensaje, numClient, 3);
        Kserver.printDataSet();
    
        for(int i=1; i<=numClient; i++){
            sendClient[i].enviarMensaje(Kserver.divideDataSet(i));
            sendClient[i].enviarMensaje(Kserver.getCentroids());
        }
    }

    public OnMessageReceived getMessageListener(){
        return this.messageListener;
    }
    

    public void run(){
        running = true;
        try{
            System.out.println("Conectando Servidor");
            socket = new ServerSocket(SERVERPORT);

            while(running){
                Socket client = socket.accept();
                System.out.println("se acepto al cliente");
                numClient++;
                System.out.println("Creando sesion "+ numClient);
                sendClient[numClient] = new ServerHilos(client, this, numClient, sendClient);
                Thread t = new Thread(sendClient[numClient]);
                t.start();

                System.out.println("Nuevo cliente conectado: "+numClient);

            }
        }catch(Exception e){
            System.out.println("error"+ e.getMessage());
        }finally{

        }
    }

    public int getNumClient(){
        return numClient;
    }

    public KMeansServer getKServer(){
        return Kserver;
    }
    //Interfaz hecha para poder recibir mensajes
    public  interface OnMessageReceived {
        public void messageReceived(double[][] message);
    }
}