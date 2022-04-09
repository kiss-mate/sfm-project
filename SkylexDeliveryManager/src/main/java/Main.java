import com.google.inject.Guice;
import common.DbContextSettings;
import common.logging.LoggingContext;
import data.DbContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.ILogic;
import logic.Logic;
import org.hibernate.SessionFactory;
import repository.*;

import java.util.Scanner;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        LoggingContext.getProperties(getClass().getResource("log-dev.properties").getFile());

        //set up dependencies
        var injector = Guice.createInjector(config -> {
            // Add common services (java.util.logging.Logger binding is automatic)
            config.bind(SessionFactory.class).toProvider(() -> DbContext
                            .getDbContextInstance()
                            .getSessionFactory(DbContextSettings.contextSettings()));
            config.bind(Scanner.class).toProvider(() -> new Scanner(System.in));

            // Add the logic
            config.bind(ILogic.class).to(Logic.class);

            // Add repos
            config.bind(IRepositoryBase.class).to(RepositoryBase.class);
            config.bind(IDriverRepository.class).to(DriverRepository.class);
        });

        // set up the main window
        var loader = new FXMLLoader();
        loader.setLocation((getClass().getResource("UI/login.fxml")));
        loader.setControllerFactory(injector::getInstance);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Skylex Delivery Manager");
        stage.setScene(scene);
        stage.show();
    }
}