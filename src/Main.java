

public class Main {
    public static void main(String[] args)
    {
        boolean start = false;
        Server s = new Server(1520,"teszt.txt");
        Graph g = new Graph(s);
        g.start();


    }
}
