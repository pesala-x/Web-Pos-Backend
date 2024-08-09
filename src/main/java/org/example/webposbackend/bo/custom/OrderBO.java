package org.example.webposbackend.bo.custom;

import org.example.webposbackend.dto.OrderDTO;

import java.sql.SQLException;
import java.util.List;

public interface OrderBO {
    void placeOrder(OrderDTO order) throws SQLException;
    List<OrderDTO> getAllOrders() throws SQLException;
}
