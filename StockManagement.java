import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// Class to hold Stock details
class Stock_Details {
    String name;
    String stockID;
    double cost;

    // Constructor
    Stock_Details(String name, String stockID, double cost) {
        this.name = name;
        this.stockID = stockID;
        this.cost = cost;
    }

    // To display stock info in a readable form
    public String toString() {
        return "Stock Name: " + name + ", ID: " + stockID + ", Cost: " + cost;
    }
}

// Class to manage stock operations
class StockManagement extends JFrame implements ActionListener {
    ArrayList<Stock_Details> stockList = new ArrayList<>();

    JLabel lblName, lblID, lblCost;
    JTextField txtName, txtID, txtCost;
    JTextArea txtArea;
    JButton btnAdd, btnDisplay;

    StockManagement() {
        setTitle("Stock Management System");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        lblName = new JLabel("Stock Name:");
        lblID = new JLabel("Stock ID:");
        lblCost = new JLabel("Cost:");

        txtName = new JTextField();
        txtID = new JTextField();
        txtCost = new JTextField();

        btnAdd = new JButton("Add");
        btnDisplay = new JButton("Display");

        btnAdd.addActionListener(this);
        btnDisplay.addActionListener(this);

        inputPanel.add(lblName);
        inputPanel.add(txtName);
        inputPanel.add(lblID);
        inputPanel.add(txtID);
        inputPanel.add(lblCost);
        inputPanel.add(txtCost);
        inputPanel.add(btnAdd);
        inputPanel.add(btnDisplay);

        // Output area
        txtArea = new JTextArea();
        txtArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Method to add stock details to ArrayList
    void Add_Stock_Details(Stock_Details stock) {
        stockList.add(stock);
    }

    // Method to display all stock details
    void Display_Stock_Details() {
        txtArea.setText(""); // clear previous text
        for (Stock_Details s : stockList) {
            txtArea.append(s.toString() + "\n");
        }
    }

    // Handle button clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            String name = txtName.getText();
            String id = txtID.getText();
            double cost = 0;
            try {
                cost = Double.parseDouble(txtCost.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid cost!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Stock_Details s = new Stock_Details(name, id, cost);
            Add_Stock_Details(s);

            JOptionPane.showMessageDialog(this, "Stock Added Successfully!");
            txtName.setText("");
            txtID.setText("");
            txtCost.setText("");
        } 
        else if (e.getSource() == btnDisplay) {
            Display_Stock_Details();
        }
    }

    public static void main(String[] args) {
        new StockManagement().setVisible(true);
    }
}
