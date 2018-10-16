package controllers;

import dao.PositionDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

public class PositionFormController extends FormController{

    @FXML
    TextField name;

    @FXML
    Button saveBtn;

    private OnChangeListener listener;
    private Stage stage = new Stage();

    PositionDAO positionDAO = new PositionDAO();
    Position updatePosition = new Position();

    public PositionFormController(ActionType actionType) throws IOException {

        loadWindow("../view/position_form.fxml", stage, this);
        addHeader(actionType, stage);

        saveBtn.setOnAction(e ->{
            String positionName = name.getText();

            if (!this.validate(positionName)) {
                showAlert();
                return;
            }

            if (actionType == ActionType.ADD) {
                if(unique(new Position(positionName), positionDAO.find())){
                    showAlertUnique();
                    return;
                }
                _createPosition(positionName);
            }

            if (actionType == ActionType.EDIT) {
                _editPosition(positionName);
            }

            if (listener != null) {
                listener.onChange();
            }

            stage.close();
        });
    }


    private void _createPosition(String name) {
        Position position = new Position(name);
        positionDAO.add(position);
    }


    private void _editPosition(String name) {
        updatePosition.setName(name);

        try {
            positionDAO.update(updatePosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validate(String name) {
        return Validate.oneWord(name);
    }


    public static PositionFormController addPosition() throws Exception {
        PositionFormController controller = new PositionFormController(ActionType.ADD);
        controller.showWindow();

        return controller;
    }


    public static PositionFormController editPosition(Position position) throws Exception {
        PositionFormController controller = new PositionFormController(ActionType.EDIT);
        controller.updatePosition(position);
        controller.showWindow();

        return controller;
    }


    private void updatePosition(Position position) {
        name.setText(position.getName());
        updatePosition = position;
    }

    private boolean unique(Position position, ObservableList<Position> list) {
        Boolean flag = false;
        for (Position p : list) {
            if (position.getName().equals(p.getName()) ) {
                return true;
            } else {
                flag =  false;
            }
        }
        return flag;
    }

    public void setOnChangeListener(OnChangeListener listener) {
        this.listener = listener;
    }


    private void showWindow() {
        stage.show();
    }
}
