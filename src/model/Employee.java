package model;


//Работники
public class Employee {

    private Integer id;
    private String lastName;
    private String firstName;
    private String middleName;
    private Position position;
    private Integer salary;
    private Integer overTimePercent;
    private Department department;
    private Subdivision subdivision;
    private Integer passport;
    private Integer totalSalary;


    public Employee() {
    }

    public Employee(String lastName, String firstName, String middleName, Position position, Integer salary, Integer overTimePercent, Department department, Subdivision subdivision, Integer passport) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.position = position;
        this.salary = salary;
        this.overTimePercent = overTimePercent;
        this.department = department;
        this.subdivision = subdivision;
        this.passport = passport;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Position getPosition() {
        return position;
    }

    public Integer getPositionId(){ return position.getId(); }

    public String getPositionName() {return position.getName();}

    public void setPosition(Position position) {
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

    public Department getDepartment() {
        return department;
    }

    public String getDepartmentName() {
        return department.getName();
    }

    public Integer getDepartmenId(){
        return department.getId();
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Subdivision getSubdivision() {
        return subdivision;
    }

    public String getSubdivisionName(){return subdivision.getName(); }

    public Integer getSubdivisionId(){ return  subdivision.getId(); }

    public void setSubdivision(Subdivision subdivision) {
        this.subdivision = subdivision;
    }

    public Integer getPassport() {
        return passport;
    }

    public void setPassport(Integer passport) {
        this.passport = passport;
    }

    public Integer getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(Integer totalSalary) {
        this.totalSalary = totalSalary;
    }
}