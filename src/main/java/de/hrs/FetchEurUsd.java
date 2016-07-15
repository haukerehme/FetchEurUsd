package de.hrs;

import de.hrs.dao.EurUsdDao;
import de.hrs.model.Eurusd;
import de.hrs.repository.EurUsdRepository;
import de.hrs.socketcontroller.RechnerSchnittstelle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by hrs on 17.06.16.
 */
@Service
public class FetchEurUsd extends Thread {

    public static Logger log = LoggerFactory.getLogger(FetchEurUsd.class);

    @Resource
    EurUsdDao eurUsdDao;

    public FetchEurUsd() {
    }

    @Override
    public void run() {
        while (true) {
            try {
                Eurusd eurusd = getClosewert();
                eurUsdDao.saveWert(eurusd);
                new RechnerSchnittstelle(eurusd).start();
                sleep(55000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Eurusd getClosewert() throws IOException, InterruptedException {
        double dWert = 0;
        //System.out.println("Hole mir nun Livewert. Warte auf die 59. sek");
        log.info("Hole mir nun Livewert. Warte auf die 59. sek");
        while (true) {
            Calendar cl = Calendar.getInstance();
            Timestamp akt = new Timestamp(cl.getTimeInMillis());
            Timestamp tmp = akt;
            int sek = tmp.getSeconds();

            if (tmp.getSeconds() == 59) {
                log.info("59. Sekunde!!!Uhrzeit: " + tmp.toString());
                Thread.sleep(1000);
                return new Eurusd(new Timestamp(new Date().getTime()), getEurUsdWert());
            }
        }
    }


    public double getEurUsdWert() throws MalformedURLException {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        try {
            Scanner scanner = new Scanner(new URL("http://62.75.142.111/eurusd.php").openStream());
            while (scanner.hasNextLine()) {
                if (i == 2) {
                    sb.append(scanner.nextLine() + "\n");
                    break;
                }
                scanner.nextLine();
                i++;
            }
            scanner.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        double result = Double.parseDouble(sb.substring(sb.indexOf("<body>") + 6, sb.indexOf("\n")));
        if (result != 0) {
            log.info("Get Live-Value {}", result);
            return result;
        } else {
            log.error("Fetched value == null");
            return getEurUsdWert();
        }
    }

}
