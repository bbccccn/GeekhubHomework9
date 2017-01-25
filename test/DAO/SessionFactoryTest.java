package DAO;

import Entities.Employee;
import Entities.Salary;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SessionFactoryTest {
    @BeforeClass
    public static void setUpDAO() throws Exception {
        DBUtils.setupDB();
    }

    @Test
    public void isSalaryInsertionNotCallsWithEmptySalaryList() throws Exception {
        EmployeeDAO employeeDAO = Mockito.spy(new EmployeeDAO());
        employeeDAO.setSessionFactory(DBUtils.getSessionFactory());

        final int KEY_ID = 3;
        List<Salary> salaries = new ArrayList<>();
        Employee employee = new Employee(KEY_ID, "Test Dude II", salaries);

        employeeDAO.insertEmployee(employee);

        verify(employeeDAO, times(0)).insertSalary(anyInt(), anyObject());
    }

    @Test
    public void isSessionConnectionsCalledTwice() throws Exception {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        SessionFactory sessionFactory = Mockito.spy(DBUtils.getSessionFactory());
        employeeDAO.setSessionFactory(sessionFactory);

        employeeDAO.getEmployeeByID(0);

        verify(sessionFactory, times(2)).getCurrentSession();
    }
}
