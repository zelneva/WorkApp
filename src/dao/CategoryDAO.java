package dao;

import DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAO {

    private static final String SELECT
            = "SELECT id, name, percent FROM category ORDER BY name, percent";
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


    // Добавление категории - возвращает ID добавленной категории
    public void addCategory(Category category) {
        Long iid = -1L;
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
            iid = id;
            gk.close();

        } catch (Exception e) {
            e.getMessage();
        }

    }


    // Редактирование категории
    public void updateCategory(Category category) throws Exception {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE)) {
            pst.setLong(3, category.getId());
            pst.setString(1, category.getName());
            pst.setInt(2, category.getPercent());
            pst.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


    // Удаление категории по ее ID
    public void deleteCategory(Integer id) throws Exception {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE)) {
            pst.setLong(1, id);
            pst.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


    // Получение категории
    public Category getCategory(Integer id) throws Exception {
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
            throw new Exception(e);
        }
        return category;
    }


    // Получение списка категорий
    public ObservableList<Category> findCategory() throws Exception {
        ObservableList<Category> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(fillCategory(rs));
            }
            rs.close();
        } catch (Exception e) {
            throw new Exception(e);
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
