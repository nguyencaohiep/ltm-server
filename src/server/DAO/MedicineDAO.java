package server.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Medicine;
import java.sql.SQLException;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MedicineDAO {
    private Connection connection;
    private static MedicineDAO instance;
    
    public MedicineDAO(Connection connection) {
        this.connection = connection;
    }
    
    public static MedicineDAO getInstance(Connection connection) throws IOException {
        if (instance == null) {
            instance = new MedicineDAO(connection);
        }
        return instance;
    }
    
    public List<Medicine> getMedicines(String effectCode) throws SQLException {
        String sql = "SELECT * FROM medicine where effectcode like '%?%';";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, effectCode);
        ResultSet result = statement.executeQuery();
        List<Medicine> medicines = new ArrayList<>();
        while(result.next()){
            if (result.next()) {
                Medicine medicine = new Medicine(result.getInt("code"), result.getString("name"), 
                        result.getString("price"), result.getString("effectCode"), result.getString("type"));
                medicines.add(medicine);
            } else {
                return null;
            }
        }
        
        result.close();
        statement.close();
        return medicines;
    }
    
    
    public void addMedicine(Medicine medicine) throws SQLException {
        String sql = "INSERT INTO medicine (name, price, effectcode, type) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, medicine.getName());
        statement.setString(2, medicine.getPrice());
        statement.setString(3, medicine.getEffectCode());
        statement.setString(4, medicine.getType());
        statement.executeUpdate();
    }
    
    public void updateMedicine(Medicine medicine) throws SQLException {
        String sql = "UPDATE medicine SET name = ?, price = ?,effectcode = ?, type = ? WHERE code = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
         statement.setString(1, medicine.getName());
        statement.setString(2, medicine.getPrice());
        statement.setString(3, medicine.getEffectCode());
        statement.setString(4, medicine.getType());
        statement.executeUpdate();
    }
    
    public void deleteMedicine(int code) throws SQLException {
        String sql = "DELETE FROM medicine WHERE code = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, code);
        statement.executeUpdate();
    }
}
