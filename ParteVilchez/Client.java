import java.util.Scanner;
public class Client {
    TcpClient mTcpClient;
    Scanner sc;

    public static void main(String[] args)  {
        Client cliente = new Client();
        cliente.init();
    }

    void init(){
        
        new Thread(
            new Runnable(){
                @Override
                public void run(){
                    mTcpClient = new TcpClient("192.168.100.25",
                        new TcpClient.OnMessageReceived() {
                            @Override
                            public void messageReceived(double[] message){
                                mensajeRecibido(message);
                            }
                        }
                    );
                    mTcpClient.run();
                }
            }
        ).start();


        String salir = "n";
        sc = new Scanner(System.in);
        System.out.println("Cliente bandera 01");
        while( !salir.equals("s")){
            salir = sc.nextLine();

        }
        System.out.println("Cliente bandera 02");
    }

    void mensajeRecibido(double[] message){
        System.out.println("llego mi array");
        for(int i=0; i<message.length; i++){
            System.out.print(message[i] + " ");
        }
    }
}
