import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javax.imageio.ImageIO;  

public class WriteImage {   
	
    /** 
     * 读取一张图片的RGB值 
     *  
     * @throws Exception 
     */  
    public void getImagePixel(String image) throws Exception {  
    	FileWriter output = new FileWriter("1.txt");
    	@SuppressWarnings("resource")
		BufferedWriter bw = new BufferedWriter(output);  
    	
        int[] rgb = new int[3];  
        File file = new File(image);  
        BufferedImage bi = null;  
        try {  
            bi = ImageIO.read(file);  
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
        
        for (int i = minx; i < width; i++) {  
            for (int j = miny; j < height; j++) {  
                int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字  
                rgb[0] = (pixel & 0xff0000) >> 16;  
                rgb[1] = (pixel & 0xff00) >> 8;  
                rgb[2] = (pixel & 0xff);  
                if(rgb[0]==0&&(i>11||j>11)){
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
     //   System.out.println(minminx);
     //   System.out.println(minminy);
        for(int i =0;i<28;i++){
        	for(int j = 0;j<28;j++){
        		int x = (i-0)*(newwidth-minminx)/(28-0)+ minminx;
        		int y = (j-0)*(newheight-minminy)/(28-0)+ minminy;
        		int pixel = bi.getRGB(x, y); // 下面三行代码将一个数字转换为RGB数字  
                rgb[0] = (pixel & 0xff0000) >> 16;  
                rgb[1] = (pixel & 0xff00) >> 8;  
                rgb[2] = (pixel & 0xff);
                if(rgb[0]==0&&(i>5||j>5)){
                	bw.write("0");  
                    bw.newLine();
                }else{
                	bw.write("1");  
                    bw.newLine();
                }
        	}
        }
        
        bw.flush();
    }  
    /** 
     * @param args 
     */  
    public static void main(String[] args) throws Exception {  
        WriteImage rc = new WriteImage();  
        rc.getImagePixel("1.png");  

        
        
        
        
        
        
        
        
        
        
    } 
}