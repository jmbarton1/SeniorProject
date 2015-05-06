package mypackage;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Converter{
	private File pictures[];
	private int newW, newH;
	private String Ftype;
	public Converter(File pictures[], int newW, int newH, String Ftype){
		this.pictures = pictures;
		this.newW = newW;
		this.newH = newH;
		this.Ftype = Ftype;
	}
	public String newImages(){
		try{
			for(int i = 0; i < pictures.length; i++){
				File f =  pictures[i];
				String pictureName = f.getName().substring(0,f.getName().lastIndexOf("."))+Ftype;
				String newPictureName = f.getAbsolutePath().replace(f.getName(), "adjusted_"+pictureName);
				System.out.println("picture name: "+ pictureName.toString() + " newPictureName: "+ newPictureName.toString());
				BufferedImage og = ImageIO.read(f);
				int type = og.getType();
				System.out.println("get type: "+og.getType());
				if(type == 0){
					type = BufferedImage.TYPE_INT_ARGB;
					System.out.println("type: "+type);
				} 
				BufferedImage resize = resize(og,type);
			
				ImageIO.write(resize, "jpg", new File(newPictureName));
				
			}
			return "Conversion Successful";
		}catch(IOException ex){
			return "Conversion Failed"+ex.getMessage();
		}
	}
	private BufferedImage resize(BufferedImage og,int type){
		BufferedImage resize = new BufferedImage(newW,newH,type);
		Graphics2D pic = resize.createGraphics();
		pic.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		pic.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		pic.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		pic.drawImage(og,0,0, newW, newH, null);
		pic.dispose();
		pic.setComposite(AlphaComposite.Src);
		return resize;
	}
}