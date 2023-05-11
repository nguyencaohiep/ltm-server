package server;

import java.sql.Connection;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Medicine;
import server.DAO.MedicineDAO;

public class Controller {
    private static Controller instance;
    private Connection dbConnection;
    private List<ServerThread> clients;
    
    private final int PORT_NUMBER = 7890;
    
    public Controller() {
        this.clients = new ArrayList<>();
        createDatabaseConnection();
    }
    
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }
    
    public void start() throws IOException, SQLException {
        ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
        System.out.printf("ServerSocket is running on port: %d\n", PORT_NUMBER);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accept a connection: " + clientSocket.getPort());
            ServerThread serverThread = new ServerThread(clientSocket);
            clients.add(serverThread);
            new Thread(serverThread).start();
        }
    }
    
    private void createDatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/ltm";
            String username = "postgres";
            String password = "password";
            this.dbConnection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.err.println("Could not find database driver class: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Could not connect to database: " + e.getMessage());
        }
        System.out.println("Database connected");
    }
    
    public Connection getDBConnection() {
        return dbConnection;
    }
}
