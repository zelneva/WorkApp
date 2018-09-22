package model;

//Подразделения
public class Subdivision {

    private Long id;
    private String name;
    private String department;

    public Subdivision(){}

    public Subdivision(String name, String department){
        this.name = name;
        this.department = department;
    }

    public Subdivision(Long id, String name, String department){
        this.id = id;
        this.name = name;
        this.department = department;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
