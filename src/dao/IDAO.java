package dao;

import javafx.collections.ObservableList;
import model.Category;
import model.IModel;

public interface IDAO<IModel> {

    // Добавление модели
    void add(IModel model);

    //Редактирование
    void update(IModel model);

    // Удаление
    void delete(Integer id);

    //Получение модели
    IModel get(Integer id);

    //Получение списка
    ObservableList<IModel> find();

}
