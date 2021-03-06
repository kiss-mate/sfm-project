import com.google.inject.Guice;
import common.DbContextSettings;
import common.logging.LoggingContext;
import data.DbContext;
import handlers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.ILogic;
import logic.ILoginLogic;
import logic.Logic;
import logic.LoginLogic;
import org.hibernate.SessionFactory;
import repository.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        LoggingContext.getProperties(getClass().getResource("log-dev.properties").getFile());

        //set up dependencies
        var injector = Guice.createInjector(config -> {
            // Add common services (java.util.logging.Logger binding is automatic)
            config.bind(SessionFactory.class).toProvider(() -> DbContext
                            .getDbContextInstance()
                            .getSessionFactory(DbContextSettings.contextSettings()));

            // Add the logic
            config.bind(ILogic.class).to(Logic.class);
            config.bind(ILoginLogic.class).to(LoginLogic.class);

            // Add handlers
            config.bind(IMainActionHandler.class).to(MainActionHandler.class);
            config.bind(IDriverActionHandler.class).to(DriverActionHandler.class);
            config.bind(IVehicleActionHandler.class).to(VehicleActionHandler.class);
            config.bind(IPackageActionHandler.class).to(PackageActionHandler.class);

            // Add repos
            config.bind(IRepositoryBase.class).to(RepositoryBase.class);
            config.bind(IDriverRepository.class).to(DriverRepository.class);
            config.bind(IUserRepository.class).to(UserRepository.class);
            config.bind(IVehicleRepository.class).to(VehicleRepository.class);
            config.bind(IPackageRepository.class).to(PackageRepository.class);
            config.bind(IDeliveryRepository.class).to(DeliveryRepository.class);
        });

        // set up the main window
        var mainLoader = new FXMLLoader();
        mainLoader.setLocation((getClass().getResource("UI/main.fxml")));
        mainLoader.setControllerFactory(injector::getInstance);
        Parent mainRoot = mainLoader.load();
        Scene mainScene = new Scene(mainRoot);

        // set up the login window
        var loginLoader = new FXMLLoader();
        loginLoader.setLocation(getClass().getResource("UI/login.fxml"));
        loginLoader.setControllerFactory(injector::getInstance);
        Parent loginRoot = loginLoader.load();
        Scene loginScene = new Scene(loginRoot);

        // show window
        primaryStage.setTitle("Skylex Delivery Manager");
        primaryStage.setScene(mainScene);
        primaryStage.show();

        // block main with login
        Stage loginStage = new Stage();
        loginStage.initStyle(StageStyle.UNDECORATED);
        loginStage.initModality(Modality.WINDOW_MODAL);
        loginStage.initOwner(primaryStage);
        loginStage.setScene(loginScene);
        loginStage.showAndWait();
    }
}