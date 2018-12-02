
import java.net.*;
import java.io.*;

/* Taken from The Java Tutorial (Campione and Walrath)
/* Further modifications made to accommodate lab and home running
          Simon Taylor October 2011 */

public class timeThread extends Thread {

  // Variable declarations

  private Socket socket = null;

  //Constructor method
  //The syntax used here can be confusing

  public timeThread(Socket socket) {
    super("timeThread");
    this.socket = socket;
  }

  public void run() {
    try {
      System.out.println("Initialising thread IO connections and state object");
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      String inputLine, outputLine;
      timeState kks = new timeState();
      outputLine = kks.processInput(null);
      out.println(outputLine);

      while ((inputLine = in.readLine()) != null) {
        outputLine = kks.processInput(inputLine);
        out.println(outputLine);
        if (outputLine.equals("Bye"))
          break;
      }

       out.close();
       in.close();
       socket.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}