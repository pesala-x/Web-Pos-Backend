package org.example.webposbackend.dao.custom;

import org.example.webposbackend.dto.ItemDTO;
import org.example.webposbackend.dto.OrderDTO;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {
    void saveOrder(OrderDTO order) throws SQLException;
    void saveOrderItem(String orderId, ItemDTO item) throws SQLException;
    void updateItemQuantity(ItemDTO item) throws SQLException;
    boolean checkCustomerExists(String customerId) throws SQLException;
    int getItemQuantity(String itemCode) throws SQLException;
    List<OrderDTO> getAllOrders() throws SQLException;
}
