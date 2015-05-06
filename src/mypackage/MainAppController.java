package mypackage;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.*;

import javafx.stage.Stage.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.util.StringConverter;
import javafx.scene.image.*;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.event.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;











import mypackage.MainApp;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;






















import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;






















import javax.imageio.ImageIO;






















import org.controlsfx.dialog.Dialogs;






















import javafx.concurrent.Task;
import javafx.concurrent.Worker;

import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class MainAppController {
	//toolbar
	@FXML
	private MenuBar MenuBar;
	@FXML
	private MenuItem About;
	@FXML
	private MenuItem Close;
	//File Browser
	@FXML
	private javafx.scene.control.TreeView<String> TreeView;
	@FXML
	private VBox VBox;
	@FXML
    private ScrollPane BrowserPane;
	
	//Image viewer FXML
	@FXML
	private Button LeftButton;
	@FXML
	private Button RightButton;
	@FXML
	private ScrollPane ScrollPane;
	@FXML
	private ImageView MainImageView;
	@FXML
	private TilePane TilePane;
	@FXML
	private BorderPane BorderPane;
	@FXML 
	private Button ClearGallery;
	@FXML
	private Tab ImageViewTab;
	//Thumbnail FXML
	@FXML
	private Button SelectImages;
	@FXML
	private Button SelectSourceFolder;
	@FXML
	private Button SelectDestinationFolder;
	@FXML 
	private ComboBox<String> FileFormats;
	private ObservableList<String> myFileFormats = FXCollections.observableArrayList();
	@FXML
	private  TextField xDimension;
	@FXML
	private  TextField yDimension;
	@FXML
	private TextArea textArea;
	@FXML
	private Button Go;
	@FXML
	private Stage PrimaryStage;
	@FXML 
	private  ProgressBar ProgressBar;
	
	private ArrayList<File> selectedImages;

	private MainApp mainApp;
	
	Task<?> copyWorker;
	
	private Model model;
	
	public boolean isDirectory;

	
	public MainAppController() {
		selectedImages = new ArrayList<File>();
		BrowserPane = new ScrollPane();
		VBox = new VBox();
		xDimension = new TextField();
		yDimension = new TextField();
		ProgressBar = new ProgressBar();
		model = new Model();
		
		
		
	}

	@SuppressWarnings({ "unchecked", "deprecation", "static-access" })
	@FXML
	private void initialize() {
		Close.setOnAction((event) -> {
			System.exit(0);
		});
		About.setOnAction((event) -> {
			JOptionPane.showMessageDialog(null, "This program is a collection of 3 modules combined by Joshua Barton \n"
											  + "This application is a senior project at Plymouth State University   \n"
											  + "is hereby allowed to be on the senior project display on their website.");
		});
		//**************************************************************************************MODULE 1************************************************************************
	/*
		xDimension.textProperty().addListener((ov, oldValue, newValue) -> {
			System.out.println("xTextfield text is: "+ xDimension.getText());
	});
		yDimension.textProperty().addListener((ov, oldValue, newValue) -> {
			System.out.println("yTextfield text is: "+ yDimension.getText());
	});
	*/
	
		FileFormats.getItems().addAll(
		".jpg",
		".png",
		".gif");
		// select images button event
		SelectImages.setOnAction((event) -> {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg","*.gif"));
			List<File> selectedFile = fc.showOpenMultipleDialog(SelectImages.getScene().getWindow());
			String fNames = selectedFile.toString();
			for(final File files : selectedFile){
				selectedImages.add(files);
				
			}
	
			System.out.println("File files list files: "+fNames.toString());
			System.out.println("selected Images "+selectedImages.size()+"\n to string: "+selectedImages.toString()+"\n Is empty: "+selectedImages.isEmpty());
			if (selectedImages != null) {
				String fileNames = (selectedFile.toString().replace(",", "\n").replace("[", "")).replace("]", "");
				String currentFiles = textArea.getText();
				File me = new File(MainAppController.class.getResource("").getFile());
				if (currentFiles.length() >= 0) {
					textArea.appendText(fileNames);
				} else {
					System.out.println("Image Selection Failed");
				}
			}
		
		});
		// source folder button event
		SelectSourceFolder.setOnAction((event) -> {
			DirectoryChooser fc = new DirectoryChooser();
			File selectedFolder = fc.showDialog(SelectImages.getScene().getWindow());
			try{
			File[] fileArray = selectedFolder.listFiles();
			
			String dNames = selectedFolder.toString();
			for(final File files : fileArray){
				selectedImages.add(files);
			}
			if (selectedImages != null) {
				System.out.println("selected files are: "+dNames.toString());
				String folder = (dNames.toString().replace(",", "\n").replace("[", "")).replace("]", "");
				String currentFiles = textArea.getText();
				if (currentFiles.length() >= 0) {
					textArea.appendText("\n"+folder);
				} else {
					System.out.println("Image Source Selection Failed");
				}
			}
			}catch(NullPointerException ex){
				ex.printStackTrace();
			}
		});
		// Clear Area / Array button event
		SelectDestinationFolder.setOnAction((event) -> {
			selectedImages.clear();
			textArea.clear();
		});
		Go.setOnAction((event) -> {
			try{
				if(xDimension.getText() == null || xDimension.getText().isEmpty() == true || yDimension.getText().isEmpty() == true || yDimension.getText() == null){
						JOptionPane.showMessageDialog(null, "Add positive x and y values");
				}
				else if(selectedImages.size() == 0){
					JOptionPane.showMessageDialog(null, "Add images first");
				}
				else if(Integer.parseInt(xDimension.getText()) == 0 || Integer.parseInt(yDimension.getText()) == 0){
					JOptionPane.showMessageDialog(null, "Plase select non-zero numbers for x and y values");
				}
				else if(FileFormats.getValue() == null){
					JOptionPane.showMessageDialog(null, "Pick a File Extension Type!");
				}
				else{
					int x = Integer.parseInt(xDimension.getText());
					int y = Integer.parseInt(yDimension.getText());
					
					String type = "";
					if(FileFormats.getValue() == ".jpg"){
						type = ".jpg";
					}
					else if(FileFormats.getValue() == ".png"){
						type = ".png";
					}
					else if(FileFormats.getValue() == ".gif"){
						type = ".gif";
					}
					else{
						type = ".jpg";
					}
					
					File[] files = new File[selectedImages.size()];
					files = selectedImages.toArray(files);
					System.out.println("selected Images Size: "+selectedImages.size());
					System.out.println(" files: "+selectedImages.toString() + "x: "+xDimension.getText() + " y: "+yDimension.getText() + "type: "+type.toString());
					
					Converter c = new Converter(files, x,y,type);
					ConverterThread thread1 = new ConverterThread(c);
					thread1.start();
					thread1.run();
					new Thread((Runnable) model.worker).start();
					final ReadOnlyObjectProperty<Worker.State> stateProperty = model.worker.stateProperty();
					ProgressBar.progressProperty().bind(model.worker.progressProperty());
					
				}
				}catch(NullPointerException e){
					e.printStackTrace();
				}
		});
		/**************************************************************MODULE 2: IMAGE VIEWER************************************************/

		RightButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/go-next.png"))));
		LeftButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/go-previous.png"))));
		
		ClearGallery.setOnAction((event) -> {
			TilePane.getChildren().clear();
			MainImageView.setImage(null);
		});
		RightButton.setOnAction((event) -> {
			ArrayList<File> a = new ArrayList<File>();
			File[] b = new File[1000];
			TilePane.getChildren().toArray(b);
			for( final File file2 : b){
				a.add(file2);
			}	
		});
		RightButton.setOnMouseEntered((event) -> {
			//RightButton.setOpacity(100);
		});
		RightButton.setOnMouseExited((event) -> {
			RightButton.setOpacity(0);
		});
		LeftButton.setOnMouseEntered((event) -> {
			//LeftButton.setOpacity(100);
		});
		LeftButton.setOnMouseExited((event) -> {
			LeftButton.setOpacity(0);
		});
		ImageViewTab.setOnSelectionChanged((event) -> {
			if(ImageViewTab.isSelected()){
			try{
				DirectoryChooser dc = new DirectoryChooser();
				File selectedFile = dc.showDialog(SelectImages.getScene().getWindow());
				String path = selectedFile.toString();
				File folder = new File(path);
				File[] listOfFiles = folder.listFiles();
				ScrollPane = new ScrollPane();
				for(final File file : listOfFiles){
					ImageView imageView;
					imageView = createImageView(file);
					TilePane.getChildren().addAll(imageView);
				}
			}catch(NullPointerException e){
				e.printStackTrace();
			}
			ScrollPane.setFitToWidth(false);
			ScrollPane.setContent(TilePane);
			}else if(ImageViewTab.isSelected() && TilePane.getChildren().isEmpty()){
					try{
						DirectoryChooser dc = new DirectoryChooser();
						File selectedFile = dc.showDialog(SelectImages.getScene().getWindow());
						String path = selectedFile.toString();
						File folder = new File(path);
						File[] listOfFiles = folder.listFiles();
						ScrollPane = new ScrollPane();
						for(final File file : listOfFiles){
							ImageView imageView;
							imageView = createImageView(file);
							TilePane.getChildren().addAll(imageView);
						}
					}catch(NullPointerException e){
						e.printStackTrace();
					}
			}else{
			}
		});
	
		/***********************************************MODULE 3***************************************/
	
	    String host =  "My Computer";
	    try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	    System.out.println("Hostname is: "+host);
	    TreeItem<String> rNode = new TreeItem<>(host, new ImageView(new Image(getClass().getResourceAsStream("/computer.png"))));
	    Iterable<Path> root = FileSystems.getDefault().getRootDirectories();
	    for(Path name: root){
			TreeItem<String> node = new FilePathTree(name);
			rNode.getChildren().add(node);
	    	System.out.println("File Path Tree nodes: "+rNode);	
		}
		rNode.setExpanded(true);
		TreeView = new TreeView<>(rNode);
		VBox.setVgrow(TreeView, Priority.ALWAYS);
		VBox.getChildren().addAll(TreeView);
		BrowserPane.setContent(VBox); 
		
	}
	   /***********************************************METHODS****************************************/
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void handleButtonAction() {

	}
	private TreeView buildFileSystem(){
		TreeItem<File> root = new TreeItem<File>(new File ("/"));
		return new TreeView<File>(root);
	}
	private ImageView createImageView(final File imageFile) {
        ImageView imageView = null;
        try {
            final Image image;
            image = new Image(new FileInputStream(imageFile), 200, 200, false, true);
            imageView = new ImageView(image);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {

                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

                        if(mouseEvent.getClickCount() == 2){
                            try {
                            
                            	MainImageView = new ImageView();
                                Image image = new Image(new FileInputStream(imageFile));
                        
                                MainImageView.setImage(image);
                                BorderPane.setCenter(MainImageView);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            });
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return imageView;
    }
	
	//classes
	public class ConverterThread extends Thread{
		Converter c1;
		public ConverterThread(Converter c1){
			this.c1 = c1;
		}
		public void run(){
			String result = c1.newImages();
			//JOptionPane.showMessageDialog(null,  result);
			try{
				Thread.sleep(4);
			}catch(InterruptedException ex){
				return ;
			}
		}
	}
	public static class Model{
		public Worker<String> worker;
		public AtomicBoolean willThrow = new AtomicBoolean(false);
		private Model(){
			worker = new Task<String>(){
				@Override
				protected String call() throws Exception {
					final int total = 100;
					updateProgress(0,total);
					for(int i = 1; i <= total; i++){
						if(isCancelled()){
							return null;
						}
						try{
							
							Thread.sleep(20);
						}catch(InterruptedException e){
							return null;
						}
						updateProgress(i, total);
					}
					return "Completed at" + System.currentTimeMillis();
				}
				@Override
				protected void scheduled(){
					
				}
				@Override
				protected void running(){
					
				}
			};
			((Task<String>)worker).setOnSucceeded(event -> {
				JOptionPane.showMessageDialog(null, "Conversion Complete");
			});
			((Task<String>)worker).setOnFailed(event -> {
				JOptionPane.showMessageDialog(null, "Conversion Failed");
			});
		}
	}
}

