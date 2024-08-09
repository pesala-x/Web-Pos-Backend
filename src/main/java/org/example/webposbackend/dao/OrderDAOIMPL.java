package org.example.webposbackend.dao;

import org.example.webposbackend.dao.custom.OrderDAO;
import org.example.webposbackend.dto.ItemDTO;
import org.example.webposbackend.dto.OrderDTO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOIMPL implements OrderDAO {
    private final DataSource dataSource;

    public OrderDAOIMPL(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveOrder(OrderDTO order) throws SQLException {
        String insertOrderSQL = "INSERT INTO orders (order_id, order_date, customer_id, total, discount, subtotal, cash, balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement orderStmt = connection.prepareStatement(insertOrderSQL)) {
            orderStmt.setString(1, order.getOrderId());
            orderStmt.setDate(2, java.sql.Date.valueOf(order.getOrderDate()));
            orderStmt.setString(3, order.getCustomerId());
            orderStmt.setDouble(4, order.getTotal());
            orderStmt.setDouble(5, order.getDiscount());
            orderStmt.setDouble(6, order.getSubTotal());
            orderStmt.setDouble(7, order.getCash());
            orderStmt.setDouble(8, order.getBalance());
            orderStmt.executeUpdate();
        }
    }

    @Override
    public void saveOrderItem(String orderId, ItemDTO item) throws SQLException {
        String insertOrderItemSQL = "INSERT INTO order_items (order_id, item_code, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement orderItemStmt = connection.prepareStatement(insertOrderItemSQL)) {
            orderItemStmt.setString(1, orderId);
            orderItemStmt.setString(2, item.getCode());
            orderItemStmt.setInt(3, Integer.parseInt(item.getQty()));
            orderItemStmt.setDouble(4, Double.parseDouble(item.getPrice()));
            orderItemStmt.executeUpdate();
        }
    }

    @Override
    public void updateItemQuantity(ItemDTO item) throws SQLException {
        String updateItemQtySQL = "UPDATE items SET qty = qty - ? WHERE code = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement updateItemStmt = connection.prepareStatement(updateItemQtySQL)) {
            updateItemStmt.setInt(1, Integer.parseInt(item.getQty()));
            updateItemStmt.setString(2, item.getCode());
            updateItemStmt.executeUpdate();
        }
    }

    @Override
    public boolean checkCustomerExists(String customerId) throws SQLException {
        String customerCheckSQL = "SELECT 1 FROM customer WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement checkCustomerStmt = connection.prepareStatement(customerCheckSQL)) {
            checkCustomerStmt.setString(1, customerId);
            var rs = checkCustomerStmt.executeQuery();
            return rs.next();
        }
    }

    @Override
    public int getItemQuantity(String itemCode) throws SQLException {
        String itemQtyCheckSQL = "SELECT qty FROM items WHERE code = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement itemQtyCheckStmt = connection.prepareStatement(itemQtyCheckSQL)) {
            itemQtyCheckStmt.setString(1, itemCode);
            var rs = itemQtyCheckStmt.executeQuery();
            return rs.next() ? rs.getInt(1) : -1;
        }
    }
    @Override
    public List<OrderDTO> getAllOrders() throws SQLException {
        List<OrderDTO> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OrderDTO order = new OrderDTO();
                order.setOrderId(rs.getString("order_id"));
                order.setOrderDate(rs.getString("order_date"));
                order.setCustomerId(rs.getString("customer_id"));
                order.setTotal(rs.getDouble("total"));
                order.setDiscount(rs.getDouble("discount"));
                order.setSubTotal(rs.getDouble("subtotal"));
                order.setCash(rs.getDouble("cash"));
                order.setBalance(rs.getDouble("balance"));

                // Optionally, load items related to the order
                order.setItems(getItemsForOrder(order.getOrderId(), connection));

                orders.add(order);
            }
        }

        return orders;
    }

    private List<ItemDTO> getItemsForOrder(String orderId, Connection connection) throws SQLException {
        List<ItemDTO> items = new ArrayList<>();
        String query = "SELECT oi.item_code, i.description, oi.price, oi.quantity FROM order_items oi " +
                "JOIN items i ON oi.item_code = i.code WHERE oi.order_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ItemDTO item = new ItemDTO();
                    item.setCode(rs.getString("item_code"));
                    item.setDescription(rs.getString("description"));
                    item.setPrice(String.valueOf(rs.getDouble("price")));
                    item.setQty(String.valueOf(rs.getInt("quantity")));

                    items.add(item);
                }
            }
        }

        return items;
    }
}
