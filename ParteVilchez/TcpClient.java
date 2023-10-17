import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
public class TcpClient{
    private double[][] serverMessage;
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

    public void enviarMensaje(double[][] message){
        if (out != null) {
            try {
                out.writeInt(message.length);
                for (int i = 0; i < message.length; i++) {
                    out.writeInt(message[i].length);
                    for (int j = 0; j < message[i].length; j++) {
                        out.writeDouble(message[i][j]);
                    }
                }
                out.flush();
            } catch (Exception e) {
                System.out.println("Error al enviar mensaje: " + e);
            }
        }
        
    }
    public void run(){
        mRun=true;
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            System.out.println("TCP Client" + "C: Conectando...");
            Socket socket = new Socket(serverAddr, SERVERPORT);
            try {
                out = new DataOutputStream(socket.getOutputStream());
                System.out.println("Client" + "C: Sent.");
                System.out.println("Client" + "C: Done.");
                in = new DataInputStream(socket.getInputStream());
                
                while (mRun) {
                    int arrayCount = in.readInt();

                    serverMessage = new double[arrayCount][];
                    for (int i = 0; i < arrayCount; i++) {
                        int arrayLength = in.readInt();
                        
                        double[] subArray = new double[arrayLength];
                        for (int j = 0; j < arrayLength; j++) {
                            subArray[j] = in.readDouble();
                        }
                        serverMessage[i] = subArray;
                    }
                    if (mMessageListener != null) {
                        mMessageListener.messageReceived(serverMessage);
                    }
                }
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
        public void messageReceived(double[][] message);
    }
}