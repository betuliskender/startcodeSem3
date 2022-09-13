package dtos;

import entities.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeDTO {

    private long id;
    private String name;
    private String address;

    public EmployeeDTO(Employee employee){

        if(employee.getId() != null){
        this.id = employee.getId();
        }
        this.name = employee.getName();
        this.address = employee.getAddress();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeDTO)) return false;
        EmployeeDTO that = (EmployeeDTO) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public static List<EmployeeDTO> getDtos(List<Employee> employee){
        List<EmployeeDTO> employeeDTOS = new ArrayList();
        employee.forEach(e->employeeDTOS.add(new EmployeeDTO(e)));
        return employeeDTOS;
    }
}
