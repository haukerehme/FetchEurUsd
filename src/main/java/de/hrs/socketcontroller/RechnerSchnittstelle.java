package de.hrs.socketcontroller;

import de.hrs.model.Eurusd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by hrs on 19.06.16.
 */
public class RechnerSchnittstelle extends Thread{

    public static Logger log = LoggerFactory.getLogger(RechnerSchnittstelle.class);

    Eurusd eurusd;
    Socket outSocket = null;
    InetAddress inetAddress = null;
    OutputStream outStream = null;

    public RechnerSchnittstelle(Eurusd eurusd1){
        this.eurusd = eurusd1;
    }

    @Override
    public void run() {
        try {
            inetAddress = Inet4Address.getLocalHost();
            outSocket = new Socket(inetAddress,19999);
            outStream = outSocket.getOutputStream();
            String w = String.valueOf(eurusd.getValue());
            outStream.write(w.getBytes());
            log.info("Flush "+eurusd.getValue());
            outStream.flush();
            outStream.close();
            outSocket.close();
            outSocket = null;
            outSocket = null;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
