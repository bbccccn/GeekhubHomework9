package DAO;

import Entities.Employee;
import Entities.Salary;
import org.h2.tools.DeleteDbFiles;
import org.junit.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EmployeeDAOTest {
    private static EmployeeDAO employeeDAO;

    @BeforeClass
    public static void setUpDAO() throws Exception {
        DBUtils.setupDB();
        employeeDAO = new EmployeeDAO();
        employeeDAO.setSessionFactory(DBUtils.getSessionFactory());
    }

    @Test
    public void isSessionFactoryExistsWithoutDirectCreatingNewObj() throws Exception {
        assertNotNull(DBUtils.getSessionFactory());
    }

    @Test
    public void isDBAdded() throws Exception {
        final int KEY_ID = 1;
        List<Salary> salaries = new ArrayList<>();
        Employee employee = new Employee(KEY_ID, "James Doe", salaries);
        salaries.add(new Salary(1, Date.valueOf("2016-12-19"), BigDecimal.valueOf(140.00)));
        salaries.add(new Salary(3, Date.valueOf("2017-1-18"), BigDecimal.valueOf(50.00)));
        salaries.add(new Salary(4, Date.valueOf("2017-1-21"), BigDecimal.valueOf(45.00)));
        employeeDAO.insertEmployee(employee);

        Assert.assertEquals(employeeDAO.getEmployeeByID(KEY_ID), employee);
    }

    @Test
    public void isFullInsertWorking() throws Exception {
        final int KEY_ID = 2;
        List<Salary> salaries = new ArrayList<>();
        salaries.add(new Salary(5, Date.valueOf("2017-2-1"), BigDecimal.valueOf(200.00)));

        Employee testEmployee = new Employee(KEY_ID, "Test Dude", salaries);
        employeeDAO.insertEmployee(testEmployee);
        Employee employeeFromDB = employeeDAO.getEmployeeByID(KEY_ID);

        assertEquals(testEmployee, employeeFromDB);
    }

    @After
    public void clearDB() throws SQLException {
        employeeDAO.getSessionFactory().getCurrentSession().createStatement().execute("TRUNCATE TABLE employee");
        employeeDAO.getSessionFactory().getCurrentSession().createStatement().execute("TRUNCATE TABLE salary");
    }

    @AfterClass
    public static void setDown() throws Exception {
        DeleteDbFiles.execute("~", "test", true);
    }
}
