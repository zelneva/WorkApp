package dao;

import DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;
import model.IModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAO implements IDAO<Category>{

    private static final String SELECT
            = "SELECT id, name, percent FROM category ORDER BY id";
    private static final String SELECT_ONE
            = "SELECT id, name, percent FROM category WHERE id=?";
    private static final String INSERT
            = "INSERT INTO category (name, percent) VALUES (?, ?)";
    private static final String UPDATE
            = "UPDATE category SET name=?, percent=? WHERE id=?";
    private static final String DELETE
            = "DELETE FROM category WHERE id=?";

    private DBConnection con = new DBConnection();

    private Connection getConnection() throws SQLException {
        return con.getConnection();
    }


    // Добавление категории
    @Override
    public void add(Category category) {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT, new String[]{"id"})) {
            Long id = -1L;
            pst.setString(1, category.getName());
            pst.setInt(2, category.getPercent());
            pst.executeUpdate();
            ResultSet gk = pst.getGeneratedKeys();
            if (gk.next()) {
                id = gk.getLong("id");
            }
            gk.close();

        } catch (Exception e) {
            e.getMessage();
        }
    }


    // Редактирование категории
    @Override
    public void update(Category category){
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE)) {
            pst.setLong(3, category.getId());
            pst.setString(1, category.getName());
            pst.setInt(2, category.getPercent());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Удаление категории по ее ID
    @Override
    public void delete(Integer id)  {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE)) {
            pst.setLong(1, id);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Получение категории
    @Override
    public Category get(Integer id) {
        Category category = null;
        try (Connection con = getConnection()) {
            PreparedStatement pst = con.prepareStatement(SELECT_ONE);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                category = fillCategory(rs);
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }


    // Получение списка категорий
    @Override
    public ObservableList<Category> find() {
        ObservableList<Category> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(fillCategory(rs));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Category fillCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setName(rs.getString("name"));
        category.setPercent(rs.getInt("percent"));
        return category;
    }
}
