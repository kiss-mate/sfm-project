import com.google.inject.Guice;
import common.DbContextSettings;
import common.logging.LoggingContext;
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

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        log.log(Level.INFO, "Application loaded");
        var thread = new Thread(controller::handleFakeAction);
        thread.start();
    }
}

class Package {
    public int Id;
    public int Weight;
    public int DeliveryId;
    public String Content;
    public String Destination;
    public boolean Selected;

    @Override
    public String toString() {
        return Selected ? String.format("%s (%dkg), to %s", Content, Weight, Destination)
                : String.format("NOT IN DELIVERY | %s (%dkg), to %s", Content, Weight, Destination);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }

    public int getDeliveryId() {
        return DeliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        DeliveryId = deliveryId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }
}

class Delivery {
    public int Id;
    public int NumOfPackages;
    public String DriverName;
    public String VehiclePlateNumber;
    public List<Package> Packages;

    @Override
    public String toString() {
        return String.format("ID= %d\t DRIVER= %s\t VEHICLE= %s\t PACKAGES= %d", Id, DriverName, VehiclePlateNumber, NumOfPackages);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getNumOfPackages() {
        return NumOfPackages;
    }

    public void setNumOfPackages(int numOfPackages) {
        NumOfPackages = numOfPackages;
    }

    public String getDriverName() {
        return DriverName;
    }

    public void setDriverName(String driverName) {
        DriverName = driverName;
    }

    public String getVehiclePlateNumber() {
        return VehiclePlateNumber;
    }

    public void setVehiclePlateNumber(String vehiclePlateNumber) {
        VehiclePlateNumber = vehiclePlateNumber;
    }

    public List<Package> getPackages() {
        return Packages;
    }

    public void setPackages(List<Package> packages) {
        Packages = packages;
    }
}