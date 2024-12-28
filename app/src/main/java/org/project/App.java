/*
 * This source file was generated by the Gradle 'init' task
 */
package org.project;

import org.project.data.JsonRepository;
import org.project.data.OsData;
import org.project.frames.home.HomeFrame;
import org.project.models.*;
import org.project.services.*;

import javax.swing.*;
import java.io.IOException;


public class App {
    public static void main(String[] args) throws IOException {
        String appDir=OsData.getUserDataPath();
        UserService userService = new UserService(new JsonRepository<>(appDir, User[].class));
        ProductService productService= new ProductService(new JsonRepository<>(appDir, Product[].class));
        ImageService imageService=new ImageService(appDir);
        CargoService cargoService=new CargoService(new JsonRepository<>(appDir, Cargo[].class));
        ReceiverService receiverService=new ReceiverService(new JsonRepository<>(appDir, Receiver[].class));
        OrderService orderService=new OrderService(new JsonRepository<>(appDir, Order[].class));
        StoreService storeService=new StoreService(new JsonRepository<>(appDir, Store[].class));

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //new Entry();
                new HomeFrame();
            }
        });

    }
}




