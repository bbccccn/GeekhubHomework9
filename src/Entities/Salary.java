package Entities;

import java.math.BigDecimal;
import java.sql.Date;

public class Salary {
    private int id;
    private Date date;
    private BigDecimal amount;

    public Salary(int id, Date date, BigDecimal amount) {
        this.id = id;
        this.date = date;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "id=" + id +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Salary salary = (Salary) o;

        if (id != salary.id) return false;
        if (date != null ? !date.equals(salary.date) : salary.date != null) return false;
        return amount != null ? amount.equals(salary.amount) : salary.amount == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
