import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class KNN {
	static String mini_path = new String();
	static long mini_distance = 28*28;
	
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
                   //     System.out.println("文件夹:" + file2.getAbsolutePath());
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                  //      System.out.println("文件:" + file2.getAbsolutePath());
                    	try {   
                    		int temp_distance = 28*28;
                    		File f2 = new File("1.txt");
                    		InputStreamReader reader2 = new InputStreamReader(new FileInputStream(f2));
                    		File f = new File(file2.getAbsolutePath()); 
                    		@SuppressWarnings("resource")
							BufferedReader br2 = new BufferedReader(reader2);
                    		String line2 = "";  
                            line2 = br2.readLine();
                    		
                    		InputStreamReader reader = new InputStreamReader(new FileInputStream(f));
                    		@SuppressWarnings("resource")
							BufferedReader br = new BufferedReader(reader);
                    		String line = "";  
                            line = br.readLine();
                            while (line != null) {  
                                line = br.readLine(); // 一次读入一行数据  
                                line2 = br2.readLine();
                           //     System.out.println(line2);
                                if(line != null&&line.equals(line2)){
                                	temp_distance--;
                                }
                            }
                    		if(temp_distance<mini_distance){
                    			mini_distance = temp_distance;
                    			mini_path = file2.getAbsolutePath();
                    		}
                    		
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
	
	public static void main(String arge[]) throws IOException {
		
		
	}
	
	
}
