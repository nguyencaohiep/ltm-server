package server.DAO;

import java.sql.Connection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Effect;
import java.sql.PreparedStatement;
import model.Medicine;
import server.Controller;

public class EffectDAO {
   private Connection connection;
   private static EffectDAO instance;
   
    public EffectDAO(Connection connection) {
        this.connection = connection;
    }
   
    public static EffectDAO getInstance(Connection connection) throws IOException {
        if (instance == null) {
            instance = new EffectDAO(connection);
        }
        return instance;
    }
    
    public List<Effect> getEffects() throws SQLException, IOException {
        String sql = "SELECT * FROM effect";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();
        
        List<Effect> effects = new ArrayList<>();
        while(result.next()){
            if (result.next()) {
                List<Medicine> medicines = MedicineDAO.getInstance((Connection) Controller.getInstance().getDBConnection()).
                        getMedicines(String.valueOf(result.getInt("code")));
                Effect effect = new Effect(result.getInt("code"), result.getString("name"), 
                        result.getString("des"), medicines);
                effects.add(effect);
            } else {
                return null;
            }
        }
        
        result.close();
        statement.close();
        return effects;
    }
    
    
    public void addEffect(Effect effect) throws SQLException {
        String sql = "INSERT INTO medicine ( name, des) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, effect.getName());
        statement.setString(2, effect.getDes());
        statement.executeUpdate();
    }
    
    public void updateMedicine(Effect effect) throws SQLException {
        String sql = "UPDATE medicine SET name = ?, des = ? WHERE code = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
         statement.setString(1, effect.getName());
        statement.setString(2, effect.getDes());
        statement.executeUpdate();
    }
    
    public void deleteMedicine(int id) throws SQLException {
        String sql = "DELETE FROM medicine WHERE code = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
    
}
