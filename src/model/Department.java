package model;


//Отделы
public class Department {

    private Long id;
    private String name;
    private String type;
    private String category;

    public Department(){}

    public Department(String name, String type, String category){
        this.name = name;
        this.type = type;
        this.category = category;
    }

    public Department(Long id, String name, String type, String category){
        this.id = id;
        this.name = name;
        this.type = type;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
