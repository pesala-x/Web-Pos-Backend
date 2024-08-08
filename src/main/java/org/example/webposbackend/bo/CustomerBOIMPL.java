package org.example.webposbackend.bo;

import org.example.webposbackend.dao.CustomerDAOImpl;
import org.example.webposbackend.dto.CustomerDTO;

import java.sql.Connection;

public class CustomerBOIMPL implements CustomerBO {
    @Override
    public String saveCustomer(CustomerDTO customer, Connection connection) throws Exception {
        var customerDAOImpl = new CustomerDAOImpl();
        return customerDAOImpl.saveCustomer(customer, connection);
    }

    @Override
    public boolean updateCustomer(String id, CustomerDTO customer, Connection connection) throws Exception {
        var customerDAOImpl = new CustomerDAOImpl();
        return customerDAOImpl.updateCustomer(id, customer, connection);
    }

    @Override
    public boolean deleteCustomer(String id, Connection connection) throws Exception {
        var customerDAOImpl = new CustomerDAOImpl();
        return customerDAOImpl.deleteCustomer(id, connection);
    }

    @Override
    public CustomerDTO getCustomer(String id, Connection connection) throws Exception {
        return null;
    }
}
