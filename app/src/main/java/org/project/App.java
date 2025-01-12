/*
Sema Nur Özyılmaz 220611039
Ahmet Recayi Öztürk 220611019
Muhammet Aslan 220611009
Murat Alperen Ulutaş 220611003
*/

package org.project;

import org.project.data.OsData;
import org.project.frames.entry.EntryFrame;
import org.project.frames.home.HomeFrame;
import org.project.models.*;
import org.project.services.*;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Main application class that serves as the entry point and manages core application state.
 * Handles user sessions, store management, and frame navigation.
 */
public class App {
    // Application-wide static fields for managing state
    private static final Path appDir = OsData.getUserDataPath();  // Directory for app data storage
    private static User currentUser;                              // Currently logged in user
    private static Store currentStore;                            // Currently active store
    private static HomeFrame homeFrame;                          // Main application frame
    private static EntryFrame entryFrame;                        // Login/Register frame

    /**
     * Application entry point. Initializes core components and starts the application.
     * @param args Command line arguments (not used)
     * @throws IOException If there's an error initializing storage
     */
    public static void main(String[] args) throws IOException {
        initializeAppStorage();
        initializeServices();
        startEntryFrame();
    }

    /**
     * Creates necessary application directories if they don't exist.
     */
    public static void initializeAppStorage(){
        OsData.createDirectoryIfNotExist();
    }

    /**
     * Initializes all service classes used throughout the application.
     * Services handle business logic and data operations.
     */
    public static void initializeServices(){
        new UserService();
        new ProductService();
        new ImageService();
        new CargoService();
        new ReceiverService();
        new OrderService();
        new StoreService();
    }

    /**
     * Creates and displays the entry frame for login/registration.
     * Uses SwingUtilities.invokeLater to ensure thread safety.
     */
    public static void startEntryFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                entryFrame=new EntryFrame();
            }
        });
    }

    /**
     * @return The application's data directory path
     */
    public static Path getAppDir() {
        return appDir;
    }

    /**
     * @return The currently active store
     */
    public static Store getCurrentStore() {
        return currentStore;
    }

    /**
     * Sets the current active store
     * @param currentStore The store to set as active
     */
    public static void setCurrentStore(Store currentStore) {
        App.currentStore = currentStore;
    }

    /**
     * Transitions from entry frame to home frame after successful login.
     * Loads the user's store and disposes of the entry frame.
     */
    public static void switchToHomeFrame() {
        currentStore=StoreService.getStoreById(currentUser.getStoreId());
        entryFrame.dispose();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                homeFrame= new HomeFrame();
            }
        });
    }

    /**
     * @return The currently logged in user
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current logged in user
     * @param currentUser The user to set as current
     */
    public static void setCurrentUser(User currentUser) {
        App.currentUser = currentUser;
    }

    /**
     * @return The main application HomeFrame
     */
    public static HomeFrame getHomeFrame() {
        return homeFrame;
    }

    /**
     * Sets the main application HomeFrame
     * @param homeFrame The HomeFrame to set
     */
    public static void setHomeFrame(HomeFrame homeFrame) {
        App.homeFrame = homeFrame;
    }

    /**
     * @return The login/register EntryFrame
     */
    public static EntryFrame getEntryFrame() {
        return entryFrame;
    }

    /**
     * Sets the login/register EntryFrame
     * @param entryFrame The EntryFrame to set
     */
    public static void setEntryFrame(EntryFrame entryFrame) {
        App.entryFrame = entryFrame;
    }
}





