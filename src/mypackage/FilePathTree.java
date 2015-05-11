package mypackage;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.*;
import java.nio.file.attribute.BasicFileAttributes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FilePathTree extends TreeItem<String>{
	public  Image closedFolder=new Image(getClass().getResourceAsStream("/folder.png"));
	public  Image OpenFolder=new Image(getClass().getResourceAsStream("/folder-open.png"));
	public  Image fileImage=new Image(getClass().getResourceAsStream("/text-x-generic.png"));
	private String fPath;
	private boolean isChild = true;
	private boolean isLeaf = true;
	private boolean isFirstChild = true;
	private boolean isFirstLeaf = true;
	private boolean isDirectory;
	
	public boolean isDirectory(){
		return this.isDirectory;
	}
	public String getPath(){
		System.out.println("this is fullPath "+fPath);
		return this.fPath;
	}
	
	/*@Override public ObservableList<TreeItem<String>> getChildren(){
		if(isFirstChild){
			isFirstChild = false;
			super.getChildren().setAll(buildFiles(this));
		}
		return super.getChildren();
	
	}
	*/
	@Override
	public boolean isLeaf(){
		if(isFirstLeaf){
			return false;
		}
		else if(this.isDirectory){
			return true;
		}
		return false;
	}
	 @SuppressWarnings("unchecked")
	public FilePathTree(Path file){
		
	    super(file.toString());
	    this.fPath=file.toString();	    
	    if(Files.isDirectory(file)){
	    	isDirectory=true;
	        this.setGraphic(new ImageView(closedFolder));
	        System.out.println("Directories: "+file);
	    }else{
	    	isDirectory=false;
	        this.setGraphic(new ImageView(fileImage));
	        System.out.println("Files: "+file);
	    }
	    if(!fPath.endsWith(File.separator)){ 
	    	System.out.println("yes");
	        String value=file.toString();
	        int indexOf=value.lastIndexOf(File.separator);
	        System.out.println("value "+value);
	        if(indexOf>0){
	        	this.setValue(value.substring(indexOf+1));
	        }else{
	        	this.setValue(value);
	        }
	    }
	    this.addEventHandler(TreeItem.branchExpandedEvent(),new EventHandler(){
	    	@Override
	        public void handle(Event e){
	          FilePathTree original=(FilePathTree)e.getSource();
	          System.out.println("source: "+original);
	          if(original.isDirectory()&&original.isExpanded()){
	        	  ImageView fileView=(ImageView)original.getGraphic();
	        	  fileView.setImage(OpenFolder);
	          }
	          try{
	        	  if(original.getChildren().isEmpty()){
	        		  isFirstLeaf = true;
	        		  Path treePath=Paths.get(original.getPath());
	        		  System.out.println("Path path: "+treePath);
	        		  BasicFileAttributes fileAttributes=Files.readAttributes(treePath,BasicFileAttributes.class);
	        		  if(fileAttributes.isDirectory()){
	        			  DirectoryStream<Path> dirStream=Files.newDirectoryStream(treePath);
	        			  for(Path file:dirStream){
	        				  FilePathTree treeNode=new FilePathTree(file);
	        				  original.getChildren().add(treeNode);
	        			  }

	        		  }
	        	 }else{
	            }
	          }catch(IOException x){
	            x.printStackTrace();
	          }
	        }
	      });
	    this.addEventHandler(TreeItem.branchCollapsedEvent(),new EventHandler(){
	        @Override
	        public void handle(Event e){
	          FilePathTree original=(FilePathTree)e.getSource();
	          if(original.isDirectory()&&!original.isExpanded()){
	            ImageView fileView=(ImageView)original.getGraphic();
	            fileView.setImage(closedFolder);
	          }
	        }
	      });
	    
	  }
}
	

	
