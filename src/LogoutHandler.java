import java.util.Vector;

public class LogoutHandler{
    private Vector<Client> clients;
    public LogoutHandler(Vector<Client> clients)
    {
        this.clients = clients;
    }

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
