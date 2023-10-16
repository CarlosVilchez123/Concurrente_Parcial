import java.util.Scanner;
public class Server {
    TcpServer mTcpServer;
    Scanner sc;

    public static void main(String[] args) {
        Server server = new Server();
        server.init();
    }

    void init(){
        new Thread(
                new Runnable() {
            @Override
            public void run() {
                mTcpServer = new TcpServer(
                        new TcpServer.OnMessageReceived() {
                            
                    @Override 
                    public void messageReceived(double[][] message) {
                        
                    }
                }
                );
                mTcpServer.run();
            }
        }
        ).start();
        String salir = "n";
        sc = new Scanner(System.in);
        System.out.println("Servidor Ejecutandose");
        System.out.println("-----------------------------------------------");
        while (!salir.equals("s")) {
            salir = sc.nextLine();
            ServidorEnvia(salir);
        }
        System.out.println("Se cerro el server");
    }

    void ServidorEnvia(String envia){
        double[][] hola = {{2.0}, {2.0,5.0}, {3.0}};
        mTcpServer.enviarMensajeTcp(hola);
    }
}
