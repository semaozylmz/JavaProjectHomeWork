package org.project.frames.home.panels;

import org.project.App;
import org.project.models.Product;
import org.project.services.ProductService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

/**
 * Panel that displays all products in a grid layout with the ability to add new products.
 * Handles product display, layout management, and navigation to product details.
 */
public class Products extends JPanel {
    private int numColumns;                    // Number of product columns in grid
    private int remainedWith;                  // Remaining width for centering
    private static final int PRODUCT_SIZE = 250; // Size of each product panel
    private CardLayout cardLayout;             // For switching between panels
    private JPanel cardPanel;                  // Container for card layout
    private List<Product> products;            // List of store's products
    private ProductDetail productDetail;        // Panel for showing product details
    private AddProduct addProduct;             // Panel for adding new products
    private JPanel productsPanel;              // Container for product grid

    public Products(CardLayout cardLayout,JPanel cardPanel,ProductDetail productDetail,AddProduct addProduct) {
        this.numColumns = 2;
        this.addProduct=addProduct;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.productDetail=productDetail;
        initialize();
    }

    /**
     * Initializes the products panel with necessary components and data.
     * Loads products from the current store and sets up the scrollable grid layout.
     */
    public void initialize(){
        this.products=ProductService.getAllStoreProducts(App.getCurrentStore().getId());
        setLayout(new BorderLayout());

        productsPanel = new JPanel();
        productsPanel.setLayout(null);
        productsPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateNumColumns();
                revalidate();
                repaint();
            }
        });

        addProducts();

        JScrollPane scrollPane = new JScrollPane(productsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Updates the number of columns based on panel width.
     * Calculates remaining width for centering products.
     */
    private void updateNumColumns() {
        int panelWidth = getWidth();
        numColumns = Math.max(1, panelWidth / (PRODUCT_SIZE + 10));
        remainedWith = panelWidth-(numColumns * (PRODUCT_SIZE + 10)-10);
    }

    /**
     * Creates and adds individual product panels to the grid.
     * Also adds the "Add New Product" panel at the end.
     */
    private void addProducts() {
        for (Product product : products) {
            productsPanel.add(createProductPanel(product));
        }
        productsPanel.add(addNewProduct());
    }

    /**
     * Creates a panel for displaying a single product.
     * Includes product image, name, price, and hover effects.
     * Clicking navigates to product details.
     * 
     * @param product The product to display
     * @return JPanel containing the product information
     */
    private JPanel createProductPanel(Product product) {
        JPanel productPanel = new JPanel();
        productPanel.setPreferredSize(new Dimension(PRODUCT_SIZE, PRODUCT_SIZE));
        productPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        productPanel.setLayout(new BorderLayout(10, 10));
        productPanel.setBackground(Color.WHITE);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        JLabel productLabel = new JLabel(product.getName());
        productLabel.setFont(new Font("Arial", Font.BOLD, 14));
        productLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        topPanel.add(productLabel, BorderLayout.CENTER);
        
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(Color.WHITE);
        ImageIcon productImageIcon = new ImageIcon(product.getImageUrl());
        Image productImage = productImageIcon.getImage();
        Image resizedImage = productImage.getScaledInstance(PRODUCT_SIZE - 40, PRODUCT_SIZE - 100, Image.SCALE_SMOOTH);
        JLabel productImageLabel = new JLabel(new ImageIcon(resizedImage));
        productImageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        imagePanel.add(productImageLabel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        JLabel priceLabel = new JLabel(String.format("$ %.2f", product.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priceLabel.setForeground(new Color(0, 100, 0));
        priceLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        bottomPanel.add(priceLabel, BorderLayout.CENTER);
        
        productPanel.add(topPanel, BorderLayout.NORTH);
        productPanel.add(imagePanel, BorderLayout.CENTER);
        productPanel.add(bottomPanel, BorderLayout.SOUTH);

        productPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                productPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 2));
                productPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                productPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
                productPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                productDetail.setProduct(product);
                cardLayout.show(cardPanel, "productDetail");
            }
        });

        return productPanel;
    }

    /**
     * Creates the "Add New Product" panel that appears at the end of the grid.
     * Displays a plus icon and text, with hover effects.
     * Clicking navigates to the add product form.
     * 
     * @return JPanel for adding new products
     */
    private JPanel addNewProduct() {
        JPanel productPanel = new JPanel();
        productPanel.setPreferredSize(new Dimension(PRODUCT_SIZE, PRODUCT_SIZE));
        productPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        productPanel.setLayout(new BorderLayout());
        productPanel.setBackground(Color.WHITE);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        
        JLabel plusLabel = new JLabel("+");
        plusLabel.setFont(new Font("Arial", Font.BOLD, 60));
        plusLabel.setForeground(new Color(0, 120, 215));
        
        JLabel textLabel = new JLabel("Add New Product");
        textLabel.setFont(new Font("Arial", Font.BOLD, 14));
        textLabel.setForeground(new Color(100, 100, 100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        centerPanel.add(plusLabel, gbc);
        
        gbc.gridy = 1;
        centerPanel.add(textLabel, gbc);
        
        productPanel.add(centerPanel, BorderLayout.CENTER);

        productPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                productPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 2));
                productPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                productPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
                productPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                addProduct.refresh();
                cardLayout.show(cardPanel, "createProduct");
            }
        });

        return productPanel;
    }

    /**
     * Handles the layout of product panels in the grid.
     * Calculates positions for each product panel based on the number of columns.
     * Centers the grid horizontally in the available space.
     */
    @Override
    public void doLayout() {
        super.doLayout();
        if (productsPanel != null) {
            int panelWidth = productsPanel.getWidth();
            int columnWidth = PRODUCT_SIZE + 10;
            int x = 0;
            int y = 0;

            for (Component component : productsPanel.getComponents()) {
                if (x >= numColumns) {
                    x = 0;
                    y++;
                }
                component.setBounds(x * columnWidth + remainedWith/2, y * (PRODUCT_SIZE + 10) + PRODUCT_SIZE/6, PRODUCT_SIZE, PRODUCT_SIZE);
                x++;
            }
            
            int totalHeight = (y + 1) * (PRODUCT_SIZE + 10) + PRODUCT_SIZE/6;
            productsPanel.setPreferredSize(new Dimension(panelWidth, totalHeight));
        }
    }

    /**
     * Refreshes the products display by reloading data and rebuilding the grid.
     * Called after adding, updating, or deleting products.
     */
    public void refresh(){
        productsPanel.removeAll();
        this.products = ProductService.getAllStoreProducts(App.getCurrentStore().getId());
        addProducts();
        revalidate();
        repaint();
    }

}