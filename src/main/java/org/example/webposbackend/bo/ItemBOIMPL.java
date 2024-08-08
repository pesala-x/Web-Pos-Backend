package org.example.webposbackend.bo;

import org.example.webposbackend.dao.CustomerDAOImpl;
import org.example.webposbackend.dao.ItemDAOImpl;
import org.example.webposbackend.dto.CustomerDTO;
import org.example.webposbackend.dto.ItemDTO;

import java.sql.Connection;

public class ItemBOIMPL implements ItemBO {
    @Override
    public String saveItem(ItemDTO item, Connection connection) throws Exception {
        var itemDAOImpl = new ItemDAOImpl();
        return itemDAOImpl.saveItem(item, connection);
    }

    @Override
    public boolean updateItem(String code, ItemDTO item, Connection connection) throws Exception {
        var itemDAOImpl = new ItemDAOImpl();
        return itemDAOImpl.updateItem(code, item, connection);
    }

    @Override
    public boolean deleteItem(String code, Connection connection) throws Exception {
        var itemDAOImpl = new ItemDAOImpl();
        return itemDAOImpl.deleteItem(code, connection);
    }

    @Override
    public ItemDTO getItem(String code, Connection connection) throws Exception {
        return null;
    }
}
