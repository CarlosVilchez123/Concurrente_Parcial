import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer{

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
        System.out.println("Se esta enviando a los clientes");
        for(int i=1; i<=numClient; i++){
            sendClient[i].enviarMensaje(mensaje);
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
    //Interfaz hecha para poder recibir mensajes
    public  interface OnMessageReceived {
        public void messageReceived(double[][] message);
    }
}