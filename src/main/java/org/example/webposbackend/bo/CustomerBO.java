package org.example.webposbackend.bo;

import org.example.webposbackend.dto.CustomerDTO;

import java.sql.Connection;

public interface CustomerBO {
    String saveCustomer(CustomerDTO customer, Connection connection)throws Exception;
    boolean deleteCustomer(String id, Connection connection)throws Exception;
    boolean updateCustomer(String id,CustomerDTO customer,Connection connection)throws Exception;
    CustomerDTO getCustomer(String id, Connection connection)throws Exception;
}
