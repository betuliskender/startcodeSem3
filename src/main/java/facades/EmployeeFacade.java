package facades;

import dtos.EmployeeDTO;
import entities.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

//import errorhandling.PersonNotFoundException;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class EmployeeFacade {

    private static EmployeeFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private EmployeeFacade() {}


    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static EmployeeFacade getEmployeeFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee(employeeDTO.getName(), employeeDTO.getAddress());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new EmployeeDTO(employee);
    }
    public EmployeeDTO getEmployeeById(int id) { //throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        Employee employee = em.find(Employee.class, id);
        return new EmployeeDTO(employee);
    }

    public List<EmployeeDTO> getAllEmployees(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);
        List<Employee> employees = query.getResultList();
        return EmployeeDTO.getDtos(employees);
    }

    public List<EmployeeDTO> getEmployeesByName(String name){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.name=:name", Employee.class);
        query.setParameter("name", name);

        return  EmployeeDTO.getDtos(query.getResultList());
    }

    public EmployeeDTO getEmployeeWithHighestSalary(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e ORDER BY e.salary DESC", Employee.class);
        return  new EmployeeDTO(query.setMaxResults(1).getSingleResult());

    }


    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        EmployeeFacade fe = getEmployeeFacade(emf);
        fe.getAllEmployees().forEach(dto->System.out.println(dto));
    }

}
