package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Effect;
import model.Medicine;
import server.DAO.EffectDAO;
import server.DAO.MedicineDAO;
;

public class ServerThread implements Runnable {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ServerThread(Socket socket) throws IOException {
        this.socket = socket;
        this.dis = new DataInputStream(socket.getInputStream());
        this.dos = new DataOutputStream(socket.getOutputStream());
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
    }

    public void run() {
        while (true) {
            try {
                String message = dis.readUTF();
                System.out.println("Request: " + message);
                
                if (message.contains("medicines/add")) {
                    System.out.println("add medicine");
                    Medicine medicine = (Medicine) ois.readObject();
                    addMedicine(medicine);
                } else if (message.contains("medicines/update")) {
                    System.out.println("update medicine");
                    Medicine medicine = (Medicine) ois.readObject();
                    updateMedicine(medicine);
                } else if (message.contains("medicines/remove")) {
                    System.out.println("remove medicine");
                    Medicine medicine = (Medicine) ois.readObject();
                    deleteMedicine(medicine);
                } else if(message.contains("effects/list")) {
                    System.out.println("get effects");
                    getEffects();
                }
                
            } catch (Exception e) {
                System.err.println(e);
                closeConnection();
                return;
            }
        }
    }
     
    private void closeConnection() {
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Connection is closed!");
    }
     
     
    private void addMedicine(Medicine medicine) throws IOException, SQLException {
        try {
            MedicineDAO.getInstance((Connection) Controller.getInstance().getDBConnection()).addMedicine(medicine);
            dos.writeUTF("success!");
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            dos.writeUTF(ex.toString());
            dos.flush();
        }
    }
    
    private void updateMedicine(Medicine medicine) throws IOException, SQLException {
        try {
            MedicineDAO.getInstance((Connection) Controller.getInstance().getDBConnection()).updateMedicine(medicine);
            dos.writeUTF("success!");
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            dos.writeUTF(ex.toString());
            dos.flush();
        }
    }
    
    private void deleteMedicine(Medicine medicine) throws IOException, SQLException {
        try {
            MedicineDAO.getInstance((Connection) Controller.getInstance().getDBConnection()).deleteMedicine(medicine.getCode());
            dos.writeUTF("success!");
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            dos.writeUTF(ex.toString());
            dos.flush();
        }
    }
    
    private void getEffects() throws IOException, SQLException {
        try {
            List<Effect> effects = EffectDAO.getInstance((Connection) Controller.getInstance().getDBConnection()).getEffects();
            oos.writeObject(effects);
            oos.flush();
        } catch (Exception e) {
            System.err.println(e);
            oos.writeObject(null);
            oos.flush();
        }
    }
}
