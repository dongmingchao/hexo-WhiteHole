import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * HelloWorld
 */
public class HelloWorld {
    int port;
    ServerSocket socket;
    Socket server;
    InputStream inLocal;
    OutputStream outLocal;
    InputStream inRemote;    
    OutputStream outRemote;
    boolean online;

    Runnable createServer = () -> {
        online = true;
        socket = new ServerSocket(port);
        PrintWriter out = new PrintWriter(outLocal,true);//开启自动flush
        out.println("服务器已开启，请链接: "+socket.getLocalSocketAddress()+":"+socket.getLocalPort());
        while (online) {
            try {
                server = socket.accept();

                inRemote = server.getInputStream();
                outRemote = server.getOutputStream();

                while(in.hasNextLine()) System.out.println(in.nextLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        inLocal.close();
        outLocal.close();
    };
    public static void main(String[] args){
        
    }
}