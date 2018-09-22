package model;


//Работники
public class Employee {

    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String position;
    private Integer salary;
    private Integer overTimePercent;
    private String department;
    private String subdivision;


    public Employee() {
    }

    public Employee(String lastName, String firstName, String middleName, String position, Integer salary, Integer overTimePercent, String department, String subdivision) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.position = position;
        this.salary = salary;
        this.overTimePercent = overTimePercent;
        this.department = department;
        this.subdivision = subdivision;
    }

    public Employee(Long id, String lastName, String firstName, String middleName, String position, Integer salary, Integer overTimePercent, String department, String subdivision) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.position = position;
        this.salary = salary;
        this.overTimePercent = overTimePercent;
        this.department = department;
        this.subdivision = subdivision;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getOverTimePercent() {
        return overTimePercent;
    }

    public void setOverTimePercent(Integer overTimePercent) {
        this.overTimePercent = overTimePercent;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
    }
}