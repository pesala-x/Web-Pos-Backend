package org.example.webposbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.webposbackend.bo.CustomerBOIMPL;
import org.example.webposbackend.bo.ItemBOIMPL;
import org.example.webposbackend.dao.CustomerDAOImpl;
import org.example.webposbackend.dao.ItemDAOImpl;
import org.example.webposbackend.dto.CustomerDTO;
import org.example.webposbackend.dto.ItemDTO;
import org.example.webposbackend.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/item", loadOnStartup = 1)
public class Item extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(Item.class);

    Connection connection;

    @Override
    public void init() throws ServletException {
        logger.info("Init method invoked");
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/customer");
            this.connection = pool.getConnection();
            logger.info("Connection initialized");
        } catch (SQLException | NamingException e) {
            logger.error("Connection failed", e);
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Request not matched with the criteria");
        }
        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            var itemBOIMPL = new ItemBOIMPL();
            ItemDTO item = jsonb.fromJson(req.getReader(), ItemDTO.class);
            logger.info("Invoke ItemIdGenerate()");
            item.setCode(Util.itemIdGenerate());
            //Save data in the DB
            writer.write(itemBOIMPL.saveItem(item,connection));
            logger.info("Item saved successfully");
            resp.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            logger.error("Connection failed");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PrintWriter writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            var itemDAOImpl = new ItemDAOImpl();
            List<ItemDTO> items = itemDAOImpl.getAllItems(connection); // Add this method to retrieve all customers
            String json = jsonb.toJson(items);
            writer.write(json);
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Request not matched with the criteria");
            return;
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            var itemBOIMPL = new ItemBOIMPL();
            ItemDTO item = jsonb.fromJson(req.getReader(), ItemDTO.class);
            String itemCode = req.getParameter("code");

            boolean result = itemBOIMPL.updateItem(itemCode, item, connection);
            if (result) {
                writer.write("Item updated successfully");
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Item not found");
            }
        } catch (Exception e) {
            logger.error("Update failed", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemCode = req.getParameter("code");
        if (itemCode == null || itemCode.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Item ID is required");
            return;
        }

        try (var writer = resp.getWriter()) {
            var itemBOIMPL = new ItemBOIMPL();
            boolean result = itemBOIMPL.deleteItem(itemCode, connection);
            if (result) {
                writer.write("Item deleted successfully");
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Item not found");
            }
        } catch (Exception e) {
            logger.error("Delete failed", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
