package controllers;

import dao.PositionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Position;

public class PositionController {
    private ObservableList<Position> positionsData = FXCollections.observableArrayList();

    @FXML
    private TableView<Position> tablePosition;

    @FXML
    private TableColumn<Position, String> name;

    @FXML
    private Button update;

    private PositionDAO c = new PositionDAO();

    @FXML
    private void initialize() throws Exception {
        initData();
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        update.setOnAction(event -> {
            try {
                positionsData.clear();
                positionsData.addAll(c.findPosition());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        tablePosition.setItems(positionsData);
    }

    private void initData() throws Exception {
        positionsData.addAll(c.findPosition());
    }
}
