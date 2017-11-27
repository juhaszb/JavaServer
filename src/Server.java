import javax.net.ssl.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.security.PrivilegedActionException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Vector;

import com.sun.net.ssl.internal.ssl.Provider;

public class Server extends Thread{
    SSLServerSocketFactory sslServerSocketFactory;
    SSLServerSocket sslServerSocket;
    SSLSocket sslSocket;
    ServerSocket serversocket;
    Vector<Client> clients = new Vector<>();
    private boolean ready;
    Socket socket = null;
    private int port ;
    private static Userfactory uf;
    private boolean stopsignal = false;
    public Server(int port,String locationofuserfactory)
    {
        this.port = port;
        Security.addProvider(new Provider());

        System.setProperty("javax.net.ssl.keyStore", "cacerts.jks");
        System.setProperty("javax.net.ssl.keyStorePassword","changeit");
        System.setProperty("javax.net.debug","all");
        uf = new Userfactory(locationofuserfactory);

        /** try {
            serversocket = new ServerSocket(port);
        }
        catch( Exception e)
        {
            ;
        }
        clients = new ArrayList<>();*/

    }
    public void setStart()
    {
        ready = true;
    }
    public boolean startready()
    {
        return ready;
    }
    public void run()
    {
        if(ready) {
            try {
                TrustManager[] trustAllCerts = new TrustManager[] {
                        new X509TrustManager() {
                            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                            }

                            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                            }

                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }
                        }
                };

                KeyStore ks = KeyStore.getInstance("JKS");
                InputStream ksIs = new FileInputStream("keystore.jks");
                try {
                    ks.load(ksIs, "changeit".toCharArray());
                } finally {
                    if (ksIs != null) {
                        ksIs.close();
                    }
                }
                KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory
                        .getDefaultAlgorithm());
                kmf.init(ks, "changeit".toCharArray());
                SSLContext sc = SSLContext.getInstance("TLSv1.2");
                sc.init(kmf.getKeyManagers(), null, null);
                sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
                sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);

                while (!stopsignal) {
                    sslSocket = (SSLSocket) sslServerSocket.accept();
                    sslSocket.setEnabledCipherSuites(sslSocket.getSupportedCipherSuites());
                    Client c = new Client(sslSocket,this);
                    c.start();
                    clients.add(c);
                    for(int i = 0;i<clients.size();i++)
                        if(clients.elementAt(i) != null)
                            if(!clients.elementAt(i).socketrunning()) {
                                clients.elementAt(i).stopit();
                                clients.remove(i);
                            }


                }
            } catch (Exception e) {
                PrivilegedActionException priexp = new PrivilegedActionException(e);
                System.out.println("Priv" + priexp.getMessage());

                System.out.println("Exception ..." + e);
                e.printStackTrace();
            }
        }
    }
    public void changefactory(String changeto)
    {
        uf.changelocation(changeto);
    }
    public static Userfactory getuserfactory()
    {
        return uf;
    }
    public static String getuserfactorylocation()
    {
        return uf.getlocation();
    }
    public Vector<Client> getClients()
    {
        return clients;
    }
    public void stopit()
    {
        stopsignal = false;
    }

}
