package model;

public class Category implements IModel<Category>{
    private Integer id;
    private String name;
    private Integer percent;

    public Category(){ }

    public Category(String name, Integer percent){
        this.name = name;
        this.percent = percent;
    }

    public Category(Integer id, String name, Integer percent){
        this.id = id;
        this.name = name;
        this.percent = percent;
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

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return name;
    }
}
