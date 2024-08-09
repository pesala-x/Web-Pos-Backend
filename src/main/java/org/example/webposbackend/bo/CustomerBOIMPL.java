package org.example.webposbackend.bo;

import org.example.webposbackend.bo.custom.CustomerBO;
import org.example.webposbackend.dao.CustomerDAOIMPL;
import org.example.webposbackend.dto.CustomerDTO;

import java.sql.Connection;

public class CustomerBOIMPL implements CustomerBO {
    @Override
    public String saveCustomer(CustomerDTO customer, Connection connection) throws Exception {
        var customerDAOImpl = new CustomerDAOIMPL();
        return customerDAOImpl.saveCustomer(customer, connection);
    }

    @Override
    public boolean updateCustomer(String id, CustomerDTO customer, Connection connection) throws Exception {
        var customerDAOImpl = new CustomerDAOIMPL();
        return customerDAOImpl.updateCustomer(id, customer, connection);
    }

    @Override
    public boolean deleteCustomer(String id, Connection connection) throws Exception {
        var customerDAOImpl = new CustomerDAOIMPL();
        return customerDAOImpl.deleteCustomer(id, connection);
    }

    @Override
    public CustomerDTO getCustomer(String id, Connection connection) throws Exception {
        return null;
    }
}
