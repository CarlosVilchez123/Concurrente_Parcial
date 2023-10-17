import java.util.Scanner;
public class Client {
    static int cont=1;
    static double[][] data;
    static double[][] centroides;
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
                            public void messageReceived(double[][] message){
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
        while( !salir.equals("s")){
            salir = sc.nextLine();
        }
    }

    void mensajeRecibido(double[][] message){
        if(cont==2){
            centroides=message;
            ClienteEnvia();
            cont=1;
        }else{
            data=message;
            cont++;
        }
    }

    void ClienteEnvia(){
        KMeansClient Kcliente = new KMeansClient(data, centroides);
        Kcliente.updateIndexes();
        int[] auxiliar = Kcliente.getIndexes(); // [{2,3,3,4,4,5}]
        System.out.print("AUXILIAR: ");
        /* 
        for(int i=0; i<auxiliar.length; i++){
            System.out.println(auxiliar[i] + " ");
        }*/
        double[][] envioIndex = new double[1][auxiliar.length];
        for(int i=0; i<auxiliar.length; i++){
            envioIndex[0][i] = (double)auxiliar[i];
        }
        for(int i=0; i<auxiliar.length; i++){
            System.out.print(envioIndex[0][i]+" ");
        }
        mTcpClient.enviarMensaje(envioIndex);
    }
}
