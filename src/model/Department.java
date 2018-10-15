package model;


//Отделы
public class Department {

    private Integer id;
    private String name;
    private String type;
    private Category category;


    public Department() {
    }

    public Department(String name, String type, Category category) {
        this.name = name;
        this.type = type;
        this.category = category;
    }

    public Department(Integer id, String name, String type, Category category) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Category getCategory(){
        return category;
    }

    public Integer getCategoryId(){
        return category.getId();
    }

    public String getCategoryName() {
        return category.getName();
    }

    public void setCategory(Category categoryModel) {
        this.category = categoryModel;
    }
}
