import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Test_writetraining {
	public static String aaaaaa = "J";
	
	public static void traverseFolder2(String path) {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("Folder is empty!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                     //   System.out.println("文件夹:" + file2.getAbsolutePath());
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                     //   System.out.println("文件:" + file2.getAbsolutePath());
                    	try {   
                    		File f = new File(file2.getAbsolutePath());   
                    		
                    		 
                    		int random=(int)(Math.random()*1000000000);
                        	String name = Integer.toString(random);
                        	FileWriter output = new FileWriter("test+training\\test\\"+aaaaaa+"\\"+name+".txt");
                        	@SuppressWarnings("resource")
                    		BufferedWriter bw = new BufferedWriter(output); 
                        	int[] rgb = new int[3];  
                            
                            BufferedImage bi = null;  
                            try {  
                                bi = ImageIO.read(f);  
                            } catch (Exception e) {  
                                e.printStackTrace();  
                            }  
                            int width = bi.getWidth();  
                            int height = bi.getHeight();  
                            int minx = bi.getMinX();  
                            int miny = bi.getMinY();  
                            int minminx = minx+width;
                            int minminy = miny+height;
                            int newwidth = minx;
                            int newheight = miny;  
                         //   System.out.println("width=" + width + ",height=" + height + ".");  
                         //   System.out.println("minx=" + minx + ",miniy=" + miny + ".");  
                            for (int i = minx; i < width; i++) {  
                                for (int j = miny; j < height; j++) {  
                                    int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字  
                                    rgb[0] = (pixel & 0xff0000) >> 16;  
                                    rgb[1] = (pixel & 0xff00) >> 8;  
                                    rgb[2] = (pixel & 0xff);
                                    if(rgb[0]==0){
                                        if(i<minminx)
                                        	minminx = i;
                                        if(j<minminy)
                                        	minminy = j;
                                        if(i>newwidth)
                                        	newwidth = i;
                                        if(j>newheight)
                                        	newheight = j;
                                    }
                                }
                            }   
                            for(int i =0;i<28;i++){
                            	for(int j = 0;j<28;j++){
                            		int x = (i-0)*(newwidth-minminx)/(28-0)+ minminx;
                            		int y = (j-0)*(newheight-minminy)/(28-0)+ minminy;
                            		int pixel = bi.getRGB(x, y); // 下面三行代码将一个数字转换为RGB数字  
                                    rgb[0] = (pixel & 0xff0000) >> 16;  
                                    rgb[1] = (pixel & 0xff00) >> 8;  
                                    rgb[2] = (pixel & 0xff);
                                    if(rgb[0]==0){
                                    	bw.write("0");  
                                        bw.newLine();
                                    }else{
                                    	bw.write("1");  
                                        bw.newLine();
                                    }
                            	}
                            }
                            bw.flush();
                            
                    		
                    	} catch (Exception e) {   
                    		e.printStackTrace();   
                    	}
                    	
                    	
                    }
                }
            }
        } else {
            System.out.println("Folder does not exist!");
        }
	
	
	}
	
	public static void main(String arge[]) {
		
		traverseFolder2("by_class\\"+aaaaaa);
		
		
	}
}
