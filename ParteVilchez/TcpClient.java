import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
public class TcpClient{
    private double[] serverMessage;
    public  String SERVERIP;
    public static final int SERVERPORT = 4444;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    DataOutputStream out;
    DataInputStream in;

    public TcpClient(String ip,OnMessageReceived listener) {
        SERVERIP = ip;
        mMessageListener = listener;
    }

    public void run(){
        mRun=true;
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            System.out.println("TCP Client"+ "C: Conectando...");
            Socket socket = new Socket(serverAddr, SERVERPORT);
            try {
               
                out = new DataOutputStream(socket.getOutputStream());
                System.out.println("Client"+ "C: Sent.");
                System.out.println("Client"+ "C: Done.");
                in = new DataInputStream(socket.getInputStream());
                System.out.println("llego supongo");
                while (mRun) {
                    System.out.println("BANDERA GA");
                    int arrayLength = in.readInt();
                    serverMessage = new double[arrayLength];
                    for (int i = 0; i < arrayLength; i++) {
                        serverMessage[i] = in.readDouble();
                    }
                    if (mMessageListener != null) {
                        mMessageListener.messageReceived(serverMessage);
                    }
                }
                System.out.println("no hace nada GAAAAAAA");
            } catch (Exception e) {
                System.out.println("Error del servidor "+e.getMessage());

            } finally {
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("Error del cliente "+ e.getMessage());
        }
    }

    public interface OnMessageReceived {
        public void messageReceived(double[] message);
    }
}