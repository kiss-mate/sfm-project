import com.google.inject.Guice;
import common.DbContextSettings;
import controller.SampleController;
import data.DbContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import logic.ILogic;
import logic.Logic;
import org.hibernate.SessionFactory;
import repository.IRepositoryBase;
import repository.ISampleRepository;
import repository.RepositoryBase;
import repository.SampleRepository;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
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
            config.bind(ISampleRepository.class).to(SampleRepository.class);
        });

        var loader = new FXMLLoader();
        loader.setLocation((getClass().getResource("view/sample.fxml")));
        loader.setControllerFactory(injector::getInstance);
        Parent root = loader.load();
        Scene scene = new Scene(root, 300, 300, Paint.valueOf("#5050FB"));
        stage.setTitle("Sample");
        stage.setScene(scene);
        stage.show();

        var log = injector.getInstance(Logger.class);
        var controller = injector.getInstance(SampleController.class);

        log.log(Level.INFO,"Application loaded");
        var thread = new Thread(controller::handleFakeAction);
        thread.start();
    }
}