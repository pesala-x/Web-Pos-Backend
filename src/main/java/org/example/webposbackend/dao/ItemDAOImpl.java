package org.example.webposbackend.dao;

import org.example.webposbackend.dto.CustomerDTO;
import org.example.webposbackend.dto.ItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ItemDAOImpl implements ItemDAO {
    public static final String SAVE_ITEM = "INSERT INTO items (code, description, price, qty) VALUES (?, ?, ?, ?)";

    @Override
    public String saveItem(ItemDTO item, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SAVE_ITEM)) {
            ps.setString(1, item.getCode());
            ps.setString(2, item.getDescription());
            ps.setString(3, item.getPrice());
            ps.setString(4, item.getQty());
            if (ps.executeUpdate() != 0) {
                return "Item saved successfully";
            } else {
                return "Failed to save Item";
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public boolean updateItem(String code, ItemDTO item, Connection connection) throws SQLException {
        String query = "UPDATE items SET description = ?, price = ?, qty = ? WHERE code = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, item.getDescription());
            ps.setString(2, item.getPrice());
            ps.setString(3, item.getQty());
            ps.setString(4, code);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteItem(String code, Connection connection) throws SQLException {
        String query = "DELETE FROM items WHERE code = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, code);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean searchItem(String code, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public CustomerDTO getItem(String code, Connection connection) throws SQLException {
        return null;
    }

    @Override
    public List<ItemDTO> getAllItems(Connection connection) throws SQLException {
        List<ItemDTO> items = new ArrayList<>();
        String query = "SELECT * FROM items";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ItemDTO item = new ItemDTO();
                item.setCode(rs.getString("code"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getString("price"));
                item.setQty(rs.getString("qty"));
                items.add(item);
            }
        }
        return items;
    }
}
