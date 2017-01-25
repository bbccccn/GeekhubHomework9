import DAO.DBUtils;

public class Main {

    public static void main(String[] args) {
        DBUtils.setupDB();
    }
}

/*
* JDBC

      Create Java application which can:

      Install H2 In memory JDBC driver. Download.
      Create two tables:
      employee (id, name)
      salary (id, date, value, emp_id)
      Insert employees and their salary
      Select list of employees and their total amount of received salary.
      select e.id, e.name, sum(s.value) from
*/