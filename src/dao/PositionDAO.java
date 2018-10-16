package dao;

import DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionDAO implements IDAO<Position> {

    private static final String SELECT
            = "SELECT id, name FROM position ORDER BY id";
    private static final String SELECT_ONE
            = "SELECT id, name FROM position WHERE id=?";
    private static final String INSERT
            = "INSERT INTO position (name) VALUES (?)";
    private static final String UPDATE
            = "UPDATE position SET name=? WHERE id=?";
    private static final String DELETE
            = "DELETE FROM position WHERE id=?";

    private DBConnection con = new DBConnection();

    private Connection getConnection() throws SQLException {
        return con.getConnection();
    }


    // Добавление должности
    @Override
    public void add(Position position) {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT, new String[]{"id"})) {
            Long id = -1L;
            pst.setString(1, position.getName());
            pst.executeUpdate();
            ResultSet gk = pst.getGeneratedKeys();
            if (gk.next()) {
                id = gk.getLong("id");
            }
            gk.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Редактирование должности
    @Override
    public void update(Position position) {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE)) {
            pst.setLong(2, position.getId());
            pst.setString(1, position.getName());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Удаление должности по ее ID
    @Override
    public void delete(Integer id) {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE)) {
            pst.setLong(1, id);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Получение должности
    @Override
    public Position get(Integer id) {
        Position position = null;
        try (Connection con = getConnection()) {
            PreparedStatement pst = con.prepareStatement(SELECT_ONE);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                position = fillPosition(rs);
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return position;
    }


    // Получение списка должностей
    @Override
    public ObservableList<Position> find() {
        ObservableList<Position> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(fillPosition(rs));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Position fillPosition(ResultSet rs) throws SQLException {
        Position position = new Position();
        position.setId(rs.getInt("id"));
        position.setName(rs.getString("name"));
        return position;
    }
}
