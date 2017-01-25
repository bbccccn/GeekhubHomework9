package DAO;

import Entities.Employee;
import Entities.Salary;
import org.h2.tools.DeleteDbFiles;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private static SessionFactory sessionFactory;

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new SessionFactory();
        }
        return sessionFactory;
    }

    public static void setupDB() {
        sessionFactory = getSessionFactory();
        DeleteDbFiles.execute("~", "test", true);
        try {
            sessionFactory.getCurrentSession().createStatement()
                    .execute("CREATE TABLE employee(id INT PRIMARY KEY, name VARCHAR(255))");
            sessionFactory.getCurrentSession().createStatement()
                    .execute("CREATE TABLE salary (id INT PRIMARY KEY, date DATE," +
                            " amount DECIMAL, emp_id int)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initValues();
    }

    protected static void initValues(){
        EmployeeDAO employeeDAO = new EmployeeDAO();
        employeeDAO.setSessionFactory(getSessionFactory());

        List<Salary> salaries = new ArrayList<>();
        Employee employee = new Employee(0, "John Doe", salaries);
        salaries.add(new Salary(0, Date.valueOf("2016-12-15"), BigDecimal.valueOf(200.00)));
        salaries.add(new Salary(2, Date.valueOf("2017-1-17"), BigDecimal.valueOf(150.00)));

        employeeDAO.insertEmployee(employee);

        salaries = new ArrayList<>();
        employee = new Employee(1, "James Doe", salaries);
        salaries.add(new Salary(1, Date.valueOf("2016-12-19"), BigDecimal.valueOf(140.00)));
        salaries.add(new Salary(3, Date.valueOf("2017-1-18"), BigDecimal.valueOf(50.00)));
        salaries.add(new Salary(4, Date.valueOf("2017-1-21"), BigDecimal.valueOf(45.00)));

        employeeDAO.insertEmployee(employee);
    }
}