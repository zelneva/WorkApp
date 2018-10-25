package controllers;

import dao.PositionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Department;
import model.Position;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class PositionController extends IController<Position> {
    private ObservableList<Position> positionsData = FXCollections.observableArrayList();

    @FXML
    private TableView<Position> tablePosition;

    @FXML
    private TableColumn<Position, String> name;

    @FXML
    private TextField searchFieldComponent;

    @FXML
    private Button add;

    private PositionDAO positionDAO = new PositionDAO();

    @FXML
    private void initialize() {
        if (tablePosition == null) return;

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        initData(positionsData, tablePosition, positionDAO, searchFieldComponent);

        add.setOnAction(event -> {
            try {
                PositionFormController.addPosition()
                        .setOnChangeListener(()->update(positionsData, positionDAO, tablePosition));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        searchFieldComponent.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilter();
        });

    }

    @FXML
    private void applyFilter() {
        String searchWord = searchFieldComponent.getText().toLowerCase().trim();
        ObservableList<Position> filterList = this.positionsData
                .stream().filter(i -> i.getName().toLowerCase().trim().contains(searchWord))
                .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
        setList(filterList, tablePosition);
    }

    public void deletePosition(ActionEvent event) {
        int index = tablePosition.getSelectionModel().getFocusedIndex();
        Integer id = tablePosition.getItems().get(index).getId();
        delete(id, tablePosition, positionDAO, positionsData);
    }

    public void editPosition(ActionEvent event) throws Exception {
        int index = tablePosition.getSelectionModel().getFocusedIndex();
        Position position = tablePosition.getItems().get(index);
        PositionFormController.editPosition(position)
                .setOnChangeListener(() -> update(positionsData, positionDAO, tablePosition));
    }
}
