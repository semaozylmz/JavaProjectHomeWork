package org.project.frames.home.panels;

import org.project.App;
import org.project.models.Product;
import org.project.services.ImageService;
import org.project.services.ProductService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Panel for adding new products to the store inventory.
 * Provides a form interface for entering product details including:
 * - Product image upload
 * - Product name
 * - Description
 * - Price
 * - Quantity
 */
public class AddProduct extends JPanel {
    private JLabel imageLabel;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private File image;
    private Path imagePath;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private int innerPanelWith = 600;
    private int panelWidth;
    private int panelHeight;
    private JPanel innerPanel ;

    /**
     * Constructs an AddProduct panel.
     * Initializes the panel layout and components for adding new products.
     * 
     * @param cardLayout The card layout manager for switching between panels
     * @param cardPanel The parent panel containing this and other cards
     */
    public AddProduct(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        initializeInnerPanel();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panelWidth = getWidth();
                panelHeight = getHeight();
                updatePanelPositionToCenter(innerPanel);
                innerPanel.setPreferredSize(new Dimension(innerPanelWith, panelHeight));
                revalidate();
                repaint();
            }
        });
    }

    /**
     * Initializes and configures the inner panel containing the product form.
     * Sets up:
     * - Image upload area
     * - Text fields for product details
     * - Save button with validation and submission logic
     */
    public void initializeInnerPanel() {
        innerPanel = new JPanel();
        innerPanel.setLayout(null);
        innerPanel.setBackground(Color.WHITE);
        innerPanel.setPreferredSize(new Dimension(innerPanelWith, panelHeight));
        imageLabel = new JLabel();
        imageLabel.setBounds(100, 30, 400, 300);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageLabel.setText("Upload an image");
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        innerPanel.add(imageLabel);

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    image = ImageService.chooseImage();
                    assert image != null;
                    if (!image.exists()) {
                        JOptionPane.showMessageDialog(null, "Image is null!");
                        return;
                    }
                    setImage(image);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JLabel nameLabel = new JLabel("Product name:");
        nameLabel.setBounds(50, 360, 100, 30);
        innerPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(200, 360, 350, 30);
        innerPanel.add(nameField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(50, 410, 100, 30);
        innerPanel.add(descriptionLabel);

        JTextArea descriptionField = new JTextArea();
        descriptionField.setBounds(200, 410, 350, 60);
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);
        descriptionField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        innerPanel.add(descriptionField);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(50, 490, 100, 30);
        innerPanel.add(priceLabel);

        JTextField priceField = new JTextField();
        priceField.setBounds(200, 490, 350, 30);
        innerPanel.add(priceField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(50, 540, 100, 30);
        innerPanel.add(quantityLabel);

        JTextField quantityField = new JTextField();
        quantityField.setBounds(200, 540, 350, 30);
        innerPanel.add(quantityField);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(250, 600, 100, 30);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = nameField.getText();
                description = descriptionField.getText();
                try {
                    price = Double.parseDouble(priceField.getText());
                    quantity = Integer.parseInt(quantityField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Price or Quantity error!");
                    return;
                }
                if (name.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Fill all blanks!");
                } else if (!image.exists()) {
                    JOptionPane.showMessageDialog(null, "Upload an image!");
                } else {
                    try {
                        imagePath = ImageService.saveImage(image);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Product newProduct = new Product();
                    newProduct.setName(name);
                    newProduct.setDescription(description);
                    newProduct.setPrice(price);
                    newProduct.setImageUrl(imagePath.toString());
                    newProduct.setProductCount(quantity);
                    newProduct.setStoreId(App.getCurrentStore().getId());
                    ProductService.add(newProduct);

                    JOptionPane.showMessageDialog(null, "Product saved!");
                    for (Component component : cardPanel.getComponents()) {
                        if (component instanceof Products) {
                            Products productPanel = (Products) component;
                            productPanel.refresh();
                        }
                    }
                    cardLayout.show(cardPanel, "Products");
                }
            }
        });
        innerPanel.add(saveButton);
        add(innerPanel);
    }

    /**
     * Sets the product image in the UI.
     * Scales the image to fit the display area while maintaining aspect ratio.
     * 
     * @param file The image file to display
     */
    public void setImage(File file) {
        Image image = new ImageIcon(file.getAbsolutePath()).getImage();
        image = image.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(image);
        imageLabel.setIcon(icon);
        imageLabel.setText("");
        imageLabel.setBorder(null);
    }

    /**
     * Updates the inner panel position to remain centered when the window is resized.
     * 
     * @param innerPanel The panel to be centered
     */
    private void updatePanelPositionToCenter(JPanel innerPanel) {
        Dimension innerPanelSize = innerPanel.getPreferredSize();
        int x = (panelWidth - innerPanelSize.width) / 2;
        int y = (panelHeight - innerPanelSize.height) / 2;

        innerPanel.setBounds(x, y, innerPanelSize.width, innerPanelSize.height);
    }

    /**
     * Resets the form to its initial state.
     * Clears all fields and removes any uploaded images.
     */
    public void refresh() {
        removeAll();
        initializeInnerPanel();
        revalidate();
        repaint();
    }
}