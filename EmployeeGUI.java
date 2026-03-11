import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

// ------------------------- Abstract Base Class -------------------------
abstract class Employee {
    String employeeName;
    int employeeID;

    Employee(String name, int id) {
        this.employeeName = name;
        this.employeeID = id;
    }

    abstract double calcNetSalary();
}

// ------------------------- Permanent Employee -------------------------
class PermanentEmployee extends Employee {
    double basicPay, hraPercent, daPercent, pfPercent;

    PermanentEmployee(String name, int id, double basicPay, double hra, double da, double pf) {
        super(name, id);
        this.basicPay = basicPay;
        this.hraPercent = hra;
        this.daPercent = da;
        this.pfPercent = pf;
    }

    @Override
    double calcNetSalary() {
        double gross = basicPay + (basicPay * hraPercent / 100) + (basicPay * daPercent / 100);
        double net = gross - (basicPay * pfPercent / 100);
        return net;
    }

    public String toString() {
        return "Permanent Employee [ID=" + employeeID + ", Name=" + employeeName + ", Net Salary=" + calcNetSalary() + "]";
    }
}

// ------------------------- Temporary Employee -------------------------
class TemporaryEmployee extends Employee {
    double hourlyWages;
    int hoursWorked;

    TemporaryEmployee(String name, int id, double hourlyWages, int hoursWorked) {
        super(name, id);
        this.hourlyWages = hourlyWages;
        this.hoursWorked = hoursWorked;
    }

    @Override
    double calcNetSalary() {
        return hourlyWages * hoursWorked;
    }

    public String toString() {
        return "Temporary Employee [ID=" + employeeID + ", Name=" + employeeName + ", Net Salary=" + calcNetSalary() + "]";
    }
}

// ------------------------- Custom Exceptions -------------------------
class NullFilledException extends Exception {
    public NullFilledException(String msg) {
        super(msg);
    }
}

class HumanRightsViolationException extends Exception {
    public HumanRightsViolationException(String msg) {
        super(msg);
    }
}

// ------------------------- Employee Management -------------------------
class EmployeeManagement {
    ArrayList<PermanentEmployee> permanentList = new ArrayList<>();
    ArrayList<TemporaryEmployee> temporaryList = new ArrayList<>();

    // Initialize with 5 dummy records each
    EmployeeManagement() {
        for (int i = 1; i <= 5; i++) {
            permanentList.add(new PermanentEmployee("P_Emp" + i, 100 + i, 30000 + i * 500, 10, 5, 12));
            temporaryList.add(new TemporaryEmployee("T_Emp" + i, 200 + i, 250 + i * 10, 100 + i));
        }
    }

    void addPermanentEmployee(PermanentEmployee pe) {
        permanentList.add(pe);
    }

    void addTemporaryEmployee(TemporaryEmployee te) {
        temporaryList.add(te);
    }

    Employee search(int id) {
        for (PermanentEmployee pe : permanentList)
            if (pe.employeeID == id) return pe;
        for (TemporaryEmployee te : temporaryList)
            if (te.employeeID == id) return te;
        return null;
    }

    ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> all = new ArrayList<>();
        all.addAll(permanentList);
        all.addAll(temporaryList);
        return all;
    }
}

// ------------------------- GUI Class -------------------------
public class EmployeeGUI extends JFrame implements ActionListener {
    JRadioButton rbPermanent, rbTemporary;
    JTextField tfName, tfID, tfBPay, tfHRA, tfDA, tfPF, tfHourly, tfHours;
    JTextArea taDisplay;
    JButton btnAdd, btnSearch, btnDisplay, btnMoveFirst, btnMovePrev, btnMoveNext, btnMoveLast;

    EmployeeManagement em = new EmployeeManagement();
    ArrayList<Employee> allList;
    int currentIndex = 0;

    EmployeeGUI() {
        setTitle("Employee Management System");
        setLayout(new GridLayout(11, 2, 5, 5));

        rbPermanent = new JRadioButton("Permanent");
        rbTemporary = new JRadioButton("Temporary");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbPermanent);
        bg.add(rbTemporary);

        tfName = new JTextField();
        tfID = new JTextField();
        tfBPay = new JTextField();
        tfHRA = new JTextField();
        tfDA = new JTextField();
        tfPF = new JTextField();
        tfHourly = new JTextField();
        tfHours = new JTextField();
        taDisplay = new JTextArea(5, 20);

        btnAdd = new JButton("Add");
        btnSearch = new JButton("Search");
        btnDisplay = new JButton("Display");
        btnMoveFirst = new JButton("MoveFirst");
        btnMovePrev = new JButton("MovePrevious");
        btnMoveNext = new JButton("MoveNext");
        btnMoveLast = new JButton("MoveLast");

        add(new JLabel("Name:")); add(tfName);
        add(new JLabel("Employee ID:")); add(tfID);
        add(new JLabel("Basic Pay:")); add(tfBPay);
        add(new JLabel("HRA %:")); add(tfHRA);
        add(new JLabel("DA %:")); add(tfDA);
        add(new JLabel("PF %:")); add(tfPF);
        add(new JLabel("Hourly Wages:")); add(tfHourly);
        add(new JLabel("Hours Worked:")); add(tfHours);
        add(rbPermanent); add(rbTemporary);
        add(btnAdd); add(btnSearch);
        add(btnDisplay); add(btnMoveFirst);
        add(btnMovePrev); add(btnMoveNext);
        add(btnMoveLast);
        add(new JScrollPane(taDisplay));

        btnAdd.addActionListener(this);
        btnSearch.addActionListener(this);
        btnDisplay.addActionListener(this);
        btnMoveFirst.addActionListener(this);
        btnMovePrev.addActionListener(this);
        btnMoveNext.addActionListener(this);
        btnMoveLast.addActionListener(this);

        setSize(700, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        allList = em.getAllEmployees();
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == btnAdd) {
                // Validate input
                if (tfName.getText().isEmpty() || tfID.getText().isEmpty())
                    throw new NullFilledException("Some fields are missing!");

                if (rbPermanent.isSelected()) {
                    PermanentEmployee pe = new PermanentEmployee(
                        tfName.getText(),
                        Integer.parseInt(tfID.getText()),
                        Double.parseDouble(tfBPay.getText()),
                        Double.parseDouble(tfHRA.getText()),
                        Double.parseDouble(tfDA.getText()),
                        Double.parseDouble(tfPF.getText())
                    );
                    em.addPermanentEmployee(pe);
                    taDisplay.setText("Permanent Employee Added Successfully!");
                } 
                else if (rbTemporary.isSelected()) {
                    int hours = Integer.parseInt(tfHours.getText());
                    if (hours > 200)
                        throw new HumanRightsViolationException("Hours worked exceed 200!");

                    TemporaryEmployee te = new TemporaryEmployee(
                        tfName.getText(),
                        Integer.parseInt(tfID.getText()),
                        Double.parseDouble(tfHourly.getText()),
                        hours
                    );
                    em.addTemporaryEmployee(te);
                    taDisplay.setText("Temporary Employee Added Successfully!");
                } 
                else {
                    throw new NullFilledException("Please select Employee Type!");
                }
            }

            else if (e.getSource() == btnSearch) {
                int id = Integer.parseInt(tfID.getText());
                Employee found = em.search(id);
                if (found != null)
                    taDisplay.setText(found.toString());
                else
                    taDisplay.setText("Employee not found!");
            }

            else if (e.getSource() == btnDisplay) {
                StringBuilder sb = new StringBuilder();
                for (Employee emp : em.getAllEmployees())
                    sb.append(emp.toString()).append("\n");
                taDisplay.setText(sb.toString());
            }

            else if (e.getSource() == btnMoveFirst) {
                currentIndex = 0;
                taDisplay.setText(allList.get(currentIndex).toString());
            }

            else if (e.getSource() == btnMoveNext) {
                if (currentIndex < allList.size() - 1) currentIndex++;
                taDisplay.setText(allList.get(currentIndex).toString());
            }

            else if (e.getSource() == btnMovePrev) {
                if (currentIndex > 0) currentIndex--;
                taDisplay.setText(allList.get(currentIndex).toString());
            }

            else if (e.getSource() == btnMoveLast) {
                currentIndex = allList.size() - 1;
                taDisplay.setText(allList.get(currentIndex).toString());
            }
        } 
        catch (Exception ex) {
            taDisplay.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new EmployeeGUI();
    }
}
