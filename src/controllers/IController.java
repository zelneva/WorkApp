package controllers;

import dao.IDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.IModel;

import java.util.Optional;

public abstract class IController<IModel>{


    void update(ObservableList<IModel> dataList, IDAO idao, TableView<IModel> tableView) {
        try {
            dataList.clear();
            dataList.addAll(idao.find());
            setList(dataList, tableView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void setList(ObservableList<IModel> list, TableView<IModel> tableView) {
        tableView.getItems().clear();
        ObservableList<IModel> items = FXCollections.observableArrayList(list);
        tableView.setItems(items);
    }


    void initData(ObservableList<IModel> list, TableView<IModel> tableView, IDAO<IModel> dao, TextField searchFieldComponent) {
        list.addAll(dao.find());
        setList(list, tableView);
        searchFieldComponent.setText("");
    }


    void delete(Integer id, TableView<IModel> tableView, IDAO dao, ObservableList<IModel> list){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление категории");
        alert.setContentText("Вы точно хотите удалить эту категорию?");
        alert.setHeaderText(null);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            dao.delete(id);
        }
        update(list, dao, tableView);
    }

}
