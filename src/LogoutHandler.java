import java.util.Vector;

public class LogoutHandler{
    /**
     * Klienseket Tároló vektor
     */
    private Vector<Client> clients;

    /**Konstruktor
     * @param clients
     * klienseket tároló vektor
     */
    public LogoutHandler(Vector<Client> clients)
    {
        this.clients = clients;
    }

    /**Kiszámolja mely userek léptek ki.
     * @return
     * Visszatér a módosított kliens listával
     */
    public Vector<Client> calculate() {

            for(int i = 0;i<clients.size();i++)
                    if(!clients.elementAt(i).isAlive()) {
                    System.out.println("Valaki kilepett.....");
                       //clients.elementAt(i).stopit();
                        clients.remove(i);


                    }
        return clients;
        }


}
