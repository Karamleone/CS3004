import java.io.*;
import java.net.*;

public class WB_client_1 {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket WBClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int WBSocketNumber = 4553;
        String WBServerName = "localhost";
        String WBClientID = "MK WBClient1";

        try {
            WBClientSocket = new Socket(WBServerName, WBSocketNumber);
            out = new PrintWriter(WBClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(WBClientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+ WBSocketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised " + WBClientID + " client and IO connections");
        
        // This is modified as it's the client that speaks first

        while (true) {
            
            fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println(WBClientID + " sending " + fromUser + " to WBServer");
                out.println(fromUser);
            }
            fromServer = in.readLine();
            System.out.println(WBClientID + " received " + fromServer + " from WBServer");
        }

}
}