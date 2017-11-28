import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Graph extends Thread{
    //private Vector<Server> servers = new Vector<Server>();
    Server s;
    private boolean running = false;
    private DefaultListModel model = new DefaultListModel();
    private JList list = new JList();

    public Graph(Server s)
    {
        this.s = s;
    }

    @Override
    public void run() {
        Border b = new Border();
        JComponent jp = b.$$$getRootComponent$$$();
        JFrame frame = new JFrame("Border");
        frame.setPreferredSize(new Dimension(600,600));
        frame.setContentPane(jp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar jmenubar = new JMenuBar();
        JMenu menu = new JMenu("Szerver");
        JMenu New = new JMenu("New");
        jmenubar.add(menu);
        jmenubar.add(New);
        JMenuItem NewUserFile = new JMenuItem("New Users File");
        NewUserFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Changeto(frame);
            }
        });
        JMenuItem NewUser = new JMenuItem(("New Account"));
        NewUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Register(frame);
            }
        });
        JMenuItem startszerver = new JMenuItem("start szerver");
        startszerver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                    if(!s.startready()) {
                        s.setStart();
                        s.start();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "A szerver már fut!", "Error", JOptionPane.PLAIN_MESSAGE);
            }
        });

        New.add(NewUser);
        New.add(NewUserFile);
        menu.add(startszerver);
        list.setModel(model);
        JScrollPane pane = new JScrollPane(list);
        JButton button = new JButton("Kick user");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(list.getSelectedValue());
                Vector<Client> client = s.getClients();
                for(int i = 0 ; i<client.size();i++)
                    if(client.elementAt(i).getUsername().equals(list.getSelectedValue())) {
                        s.removefromclients(i);
                    }
            }
        });
        frame.add(button,BorderLayout.SOUTH);
        frame.add(pane, BorderLayout.CENTER);
        frame.add(pane);
        frame.add(jmenubar,BorderLayout.NORTH);
        frame.setVisible(true);
        frame.pack();
        final Thread updater = new Thread()
        {
            @Override
            public void run() {
                while(true)
                {
                        Vector<Client> cl = s.getClients();
                        LogoutHandler handler = new LogoutHandler(cl);
                        boolean changed = false;
                        cl = handler.calculate();
                            for(int i=0;i<cl.size();i++)
                                if(model.size() == 0 || cl.size() != model.size() )
                                    changed = true;
                            if(cl.size()==0)
                                changed = true;

                            if(changed){
                                model.removeAllElements();
                                for(int i=0;i<cl.size();i++) {
                                    model.addElement( cl.elementAt(i).getUsername());
                                    changed = false;
                                }
                            }


                            try{
                            Thread.sleep(500);
                            }
                            catch(Exception e)
                            {
                                ;
                            }

                }
            }
        };
        updater.start();

    }
    private void Register(JFrame frame)
    {
        JPanel p = new JPanel(new BorderLayout(5,5));
        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
        labels.add(new JLabel("E-Mail",SwingConstants.RIGHT));
        labels.add(new JLabel("Username",SwingConstants.RIGHT));
        labels.add(new JLabel("Password",SwingConstants.RIGHT));
        p.add(labels,BorderLayout.WEST);

        JPanel fields = new JPanel(new GridLayout(0,1,2,2));
        JTextField username = new JTextField();
        fields.add(username);
        JTextField email = new JTextField();
        fields.add(email);
        JPasswordField password = new JPasswordField();
        fields.add(password);
        p.add(fields,BorderLayout.CENTER);
        JOptionPane.showMessageDialog(frame,p,"Register",JOptionPane.QUESTION_MESSAGE);
        try {
            Server.getuserfactory().CreateUser(email.getText(), username.getText(), new String(password.getPassword()));
        }
        catch(Exception e)
        {
            ;
        }

    }
    private void Changeto(JFrame frame)
    {
        Server.getuserfactory().changelocation(JOptionPane.showInputDialog("Name of the new list?"));
    }
}
