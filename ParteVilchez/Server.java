import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Server {
    static double[][][] confe;
    static int[][] confe2;
    static int cont = 0;
    TcpServer mTcpServer;
    Scanner sc;

    public static void main(String[] args) throws FileNotFoundException {
        Server server = new Server();
        server.init();
    }

    void init() throws FileNotFoundException{
        new Thread(
                new Runnable() {
            @Override
            public void run() {
                mTcpServer = new TcpServer(
                        new TcpServer.OnMessageReceived() {
                            
                    @Override 
                    public void messageReceived(double[][] message) {
                        synchronized(this){
                            ServidorRecibe(message);
                        }
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
    }

    void ServidorEnvia(String envia) throws FileNotFoundException {
        confe = new double[mTcpServer.getNumClient()][1][];
        confe2 = new int[mTcpServer.getNumClient()][];
        File f = new File("ParteVilchez/Kmeans/normalizedDataOne.csv");
        CSVreader csv = new CSVreader(f);
        double[][] matrix = csv.toMatrix();
        mTcpServer.enviarMensajeTcp(matrix);
    }

    void ServidorRecibe(double[][] llego){
        confe2[cont] = new int[llego[0].length];
        confe[cont][0] = new double[llego.length];
        confe[cont] = llego;
        System.out.println("index");
        for (double[] ds : confe[cont]) {
            for(double ds1: ds){
                System.out.print(ds1+" ");
            }
            System.out.println();
        }

        for(int i=0; i<llego[0].length; i++){
            confe2[cont][i] = (int)confe[cont][0][i];
        }
        System.out.println("matriz");
        for(int i=0; i<llego[0].length; i++){
            System.out.println(confe2[cont][i]);
        }
        cont++;
        if(cont == mTcpServer.getNumClient()-1){
            KMeansServer server = mTcpServer.getKServer();
            server.updateIndexes(confe2);
            server.updateCentroids();
            server.getCentroids();
        }
    }
}
