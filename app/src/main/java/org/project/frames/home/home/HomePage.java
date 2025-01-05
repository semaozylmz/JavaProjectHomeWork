package org.project.frames.home.home;

import org.project.frames.home.home.panels.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePage extends JPanel {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private Inventory inventory;
    private ProductDetail productDetail;
    private Products products;
    private StorePanel store;
    private AddProduct addProduct;
    private Cargos cargos;
    private ReceiversPanel receiversPanel;
    private OrdersPanel ordersPanel;

    public HomePage() {
        super(new BorderLayout());

        JPanel menuPanel = createMenuPanel();
        menuPanel.setBackground(new Color(222, 49, 99));

        contentPanel = createContentPanel();

        add(menuPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createContentPanel() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        inventory = new Inventory(cardLayout, contentPanel);
        productDetail = new ProductDetail();
        products = new Products(cardLayout, contentPanel, productDetail);
        store = new StorePanel();
        addProduct = new AddProduct(cardLayout, contentPanel);
        cargos = new Cargos();
        receiversPanel = new ReceiversPanel();
        ordersPanel = new OrdersPanel();

        contentPanel.add(inventory, "Inventory");
        contentPanel.add(products, "Products");
        contentPanel.add(store, "Store");
        contentPanel.add(productDetail, "productDetail");
        contentPanel.add(addProduct, "createProduct");
        contentPanel.add(cargos, "Cargos");
        contentPanel.add(receiversPanel, "Receivers");
        contentPanel.add(ordersPanel, "OrdersPanel");

        return contentPanel;
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(250, getHeight()));

        JButton menuItem1 = createStyledButton("Inventory");
        JButton menuItem3 = createStyledButton("Products");
        JButton menuItem4 = createStyledButton("Store");
        JButton menuItem5 = createStyledButton("Cargos");
        JButton menuItem6 = createStyledButton("Receivers");
        JButton menuItem7 = createStyledButton("Orders");

        menuItem1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        menuItem3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        menuItem4.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        menuItem5.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        menuItem6.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        menuItem7.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventory.refresh();
                cardLayout.show(contentPanel, "Inventory");
            }
        });

        menuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                products.refresh();
                cardLayout.show(contentPanel, "Products");
            }
        });

        menuItem4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                store.refresh();
                cardLayout.show(contentPanel, "Store");
            }
        });

        menuItem5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Cargos");
            }
        });

        menuItem6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Receivers");
            }
        });

        menuItem7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "OrdersPanel");
            }
        });

        menuPanel.add(menuItem1);
        menuPanel.add(menuItem3);
        menuPanel.add(menuItem4);
        menuPanel.add(menuItem5);
        menuPanel.add(menuItem6);
        menuPanel.add(menuItem7);

        return menuPanel;
    }
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);

        // Buton font ve boyut ayarı
        button.setFont(new Font("Montserrat", Font.BOLD, 20));

        // Buton boyutları
        button.setPreferredSize(new Dimension(Integer.MAX_VALUE, 60));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Başlangıçta arka plan beyaz, yazı siyah
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);

        // Buton UI stilini ayarlama
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        button.setOpaque(true);

        button.setBorder(BorderFactory.createLineBorder(new Color(222, 49, 99), 2));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(222, 49, 99));
                button.setForeground(Color.BLACK); }
            @Override public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
                button.setForeground(Color.BLACK);
            }
        });

        return button;
    }

}

/*
package org.project.frames.home.home;

import org.project.frames.home.home.panels.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JPanel {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private Inventory inventory ;
    private ProductDetail productDetail ;
    private Products products ;
    //private Store store;
    private StorePanel store;
    private AddProduct addProduct ;
    private Cargos cargos;
    private ReceiversPanel receiversPanel;
    private OrdersPanel ordersPanel ;

    public HomePage() {
        super(new BorderLayout());
        JPanel menuPanel = createMenuPanel();
        menuPanel.setBackground(Color.BLUE);
        JPanel contentPanel = createContentPanel();

        add(menuPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }
    private JPanel createContentPanel(){
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        inventory = new Inventory(cardLayout, contentPanel);
        //orders = new Orders();
        productDetail = new ProductDetail();
        products = new Products(cardLayout, contentPanel, productDetail);
        //store = new Store();
        store = new StorePanel();
        addProduct = new AddProduct(cardLayout, contentPanel);
        cargos = new Cargos();
        receiversPanel = new ReceiversPanel();
        ordersPanel = new OrdersPanel();

        contentPanel.add(inventory, "Inventory");
        contentPanel.add(products, "Products");
        contentPanel.add(store, "Store");
        contentPanel.add(productDetail, "productDetail");
        contentPanel.add(addProduct, "createProduct");
        contentPanel.add(cargos, "Cargos");
        contentPanel.add(receiversPanel, "Receivers");
        contentPanel.add(ordersPanel, "OrdersPanel");
        return contentPanel;
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        JButton menuItem1 = new JButton("Inventory");
        JButton menuItem3 = new JButton("Products");
        JButton menuItem4 = new JButton("Store");
        JButton menuItem5 = new JButton("Cargos");
        JButton menuItem6 = new JButton("Receivers");
        JButton menuItem7 = new JButton("Orders");

        menuItem1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        menuItem3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        menuItem4.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        menuItem5.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        menuItem6.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        menuItem7.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventory.refresh();
                cardLayout.show(contentPanel, "Inventory");
            }
        });


        menuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                products.refresh();
                cardLayout.show(contentPanel, "Products");
            }
        });

        menuItem4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                store.refresh();
                cardLayout.show(contentPanel, "Store");
            }
        });

        menuItem5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Cargos");
            }
        });

        menuItem6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Receivers");
            }
        });
        menuItem7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "OrdersPanel");
            }
        });

        menuPanel.add(menuItem1);
        menuPanel.add(menuItem3);
        menuPanel.add(menuItem4);
        menuPanel.add(menuItem5);
        menuPanel.add(menuItem6);
        menuPanel.add(menuItem7);
        return menuPanel;
    }
}
*/