package org.example.webposbackend.dao;

import org.example.webposbackend.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public sealed interface CustomerDAO permits CustomerDAOImpl{
    String saveCustomer(CustomerDTO customer, Connection connection) throws SQLException;
    boolean updateCustomer(String id, CustomerDTO customer, Connection connection) throws SQLException;
    boolean deleteCustomer(String id, Connection connection) throws SQLException;
    boolean searchCustomer(String id, Connection connection) throws SQLException;
    CustomerDTO getCustomer(String id, Connection connection) throws SQLException;
    List<CustomerDTO> getAllCustomers(Connection connection) throws SQLException;
}
