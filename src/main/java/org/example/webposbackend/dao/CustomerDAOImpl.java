package org.example.webposbackend.dao;

import org.example.webposbackend.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CustomerDAOImpl implements CustomerDAO {
    public static final String SAVE_CUSTOMER = "INSERT INTO customer (id, name, address, mobile) VALUES (?, ?, ?, ?)";
    public static final String GET_CUSTOMER = "SELECT * FROM customer WHERE id = ?";
    public static final String UPDATE_CUSTOMER = "UPDATE customer SET name = ?, address = ?, mobile = ?, WHERE id = ?";
    public static final String DELETE_CUSTOMER = "DELETE FROM customer WHERE id = ?";
    public static final String SEARCH_CUSTOMER = "SELECT * FROM customer WHERE id = ?";

    @Override
    public String saveCustomer(CustomerDTO customer, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SAVE_CUSTOMER)) {
            ps.setString(1, customer.getId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getMobile());
            if (ps.executeUpdate() != 0) {
                return "Customer saved successfully";
            } else {
                return "Failed to save Customer";
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public boolean updateCustomer(String id, CustomerDTO customer, Connection connection) throws SQLException {
        String query = "UPDATE customer SET name = ?, address = ?, mobile = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getMobile());
            ps.setString(4, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteCustomer(String id, Connection connection) throws SQLException {
        String query = "DELETE FROM customer WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean searchCustomer(String id, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public CustomerDTO getCustomer(String id, Connection connection) throws SQLException {
        return null;
    }

    @Override
    public List<CustomerDTO> getAllCustomers(Connection connection) throws SQLException {
        List<CustomerDTO> customers = new ArrayList<>();
        String query = "SELECT * FROM customer";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CustomerDTO customer = new CustomerDTO();
                customer.setId(rs.getString("id"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setMobile(rs.getString("mobile"));
                customers.add(customer);
            }
        }
        return customers;
    }
}
