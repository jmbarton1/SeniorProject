package mypackage;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.SwingUtilities;
import mypackage.MainAppController.Model;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;

public class MainApp extends Application {
	private Stage primaryStage; 
	private AnchorPane rootLayout;
	
	@Override
	public void start(Stage primaryStage){
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Photo Utility");
		initLayout();
	}
	
	public void initLayout(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("SeniorProjectUI.fxml"));
			rootLayout = (AnchorPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.setHeight(1000);
			primaryStage.setWidth(1300);
			primaryStage.show();
			MainAppController controller = loader.getController();
			controller.setMainApp(this);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
	
}
