import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class ListUsers extends Thread {
    private Vector<Server> servers = new Vector<>();
    DefaultListModel model;
    JList list;
    JFrame frame;
    public ListUsers(Vector<Server> servers, DefaultListModel model,JList list,JFrame frame)
    {
        this.servers = servers;
        this.model = model;
        this.list = list;
        this.frame = frame;
    }

    @Override
    public void run() {
        model.removeAllElements();
        Vector<Client> cl = servers.elementAt(servers.size() - 1).getClients();
        for (int i = 0; i < cl.size(); i++) {
            model.setElementAt(cl.elementAt(i).getUsername(),i);
        }
        list.setModel(model);
        JScrollPane pane = new JScrollPane(list);
        frame.add(pane, BorderLayout.CENTER);
        frame.add(pane);
        SwingUtilities.updateComponentTreeUI(frame);



    }
}
