package DAO;

import Entities.Employee;
import Entities.Salary;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    public static final String INSERT_EMPLOYEE = "INSERT INTO employee VALUES (?,?)";
    public static final String INSERT_INTO_SALARY_VALUES = "INSERT INTO salary VALUES (?,?,?,?)";
    public static final String SELECT_EMPLOYEE_BY_ID = "SELECT name FROM employee WHERE ID=?";
    public static final String SELECT_SALARY_BY_EMP_ID = "SELECT id,date,amount FROM salary WHERE emp_id=(?)";
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void insertEmployee(Employee employee) {
        try {
            PreparedStatement ps = sessionFactory.getCurrentSession().prepareStatement(INSERT_EMPLOYEE);
            ps.setInt(1, employee.getId());
            ps.setString(2, employee.getName());
            ps.execute();
            ps.close();
            for (Salary salary : employee.getSalaries()) {
                insertSalary(employee.getId(), salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void insertSalary(int employeeId, Salary salary) throws SQLException {
        PreparedStatement salaryQuery = sessionFactory.getCurrentSession().prepareStatement(INSERT_INTO_SALARY_VALUES);
        salaryQuery.setInt(1, salary.getId());
        salaryQuery.setDate(2, salary.getDate());
        salaryQuery.setBigDecimal(3, salary.getAmount());
        salaryQuery.setInt(4, employeeId);
        salaryQuery.execute();
        salaryQuery.close();
    }

    public Employee getEmployeeByID(int key) throws SQLException {
        PreparedStatement statement = sessionFactory.getCurrentSession().prepareStatement(SELECT_EMPLOYEE_BY_ID);
        statement.setInt(1, key);
        ResultSet resultSet = statement.executeQuery();

        String name = "";
        if (resultSet.next()) {
            name = resultSet.getString("name");
        }

        statement = sessionFactory.getCurrentSession().prepareStatement(SELECT_SALARY_BY_EMP_ID);
        statement.setInt(1, key);
        resultSet = statement.executeQuery();
        List<Salary> salaries = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            BigDecimal amount = resultSet.getBigDecimal("amount");
            Date date = resultSet.getDate("date");
            salaries.add(new Salary(id, date, amount));
        }

        return new Employee(key, name, salaries);
    }
}
