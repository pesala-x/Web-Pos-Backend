package org.example.webposbackend.dao.custom;

import org.example.webposbackend.dto.CustomerDTO;
import org.example.webposbackend.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ItemDAO {
    String saveItem(ItemDTO item, Connection connection) throws SQLException;
    boolean updateItem(String code, ItemDTO item, Connection connection) throws SQLException;
    boolean deleteItem(String code, Connection connection) throws SQLException;
    boolean searchItem(String code, Connection connection) throws SQLException;
    CustomerDTO getItem(String code, Connection connection) throws SQLException;
    List<ItemDTO> getAllItems(Connection connection) throws SQLException;
}
