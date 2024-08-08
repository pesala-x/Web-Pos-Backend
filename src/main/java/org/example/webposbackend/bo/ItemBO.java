package org.example.webposbackend.bo;

import org.example.webposbackend.dto.CustomerDTO;
import org.example.webposbackend.dto.ItemDTO;

import java.sql.Connection;

public interface ItemBO {
    String saveItem(ItemDTO item, Connection connection)throws Exception;
    boolean deleteItem(String code, Connection connection)throws Exception;
    boolean updateItem(String code,ItemDTO item,Connection connection)throws Exception;
    ItemDTO getItem(String code, Connection connection)throws Exception;
}
