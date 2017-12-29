import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
public class KNN_TEST_TRAIN {
	static String mini_path = new String();
	static long mini_distance = 28*28;
	static int[] test_size = new int[11];
	static int[] correct = new int[11];
	
	public static void traverseFolder2(String path, String test_path) {
		
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
                        traverseFolder2(file2.getAbsolutePath(),test_path);
                    } else {
                  //      System.out.println("文件:" + file2.getAbsolutePath());
                    	try {   
                    		int temp_distance = 28*28;
                    		File f2 = new File(test_path);
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
	
	
	
	public static void traverseFolder1(String path) {
		
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
                        traverseFolder1(file2.getAbsolutePath());
                    } else {
                  //      System.out.println("文件:" + file2.getAbsolutePath());
                    	try {   
                    		String[] ori = file2.getAbsolutePath().split("test_set");
                    		char orich =ori[1].charAt(1);
                    		test_size[orich-'A']++;
                    		test_size[10]++;
                    		traverseFolder2("test+training\\training_set",file2.getAbsolutePath());
                    		String s = mini_path;
                    		mini_distance = 28*28;
                    		String[] rs = s.split("training_set");
                    		char rsch = rs[1].charAt(1);
                    		if(rsch==orich){
                    			correct[orich-'A']++;
                    			correct[10]++;
                    		}
                    	//	System.out.println(rsch);
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

		traverseFolder1("test+training\\test_set");
		for(int i = 0;i<10;i++){
			char ch = 'n';
			switch(i){
				case 0: ch = 'A';
				break;
				case 1: ch = 'B';
				break;		
				case 2: ch = 'C';
				break;	
				case 3: ch = 'D';
				break;	
				case 4: ch = 'E';
				break;	
				case 5: ch = 'F';
				break;	
				case 6: ch = 'G';
				break;	
				case 7: ch = 'H';
				break;	
				case 8: ch = 'I';
				break;
				case 9: ch = 'J';
				break;
			}
			System.out.println(correct[i]+" of "+test_size[i]+" "+ch +" are recognized correctly. Accuracy: "+(float)correct[i]/(float)test_size[i]);
		}
		
		System.out.println(correct[10]+" of "+test_size[10]+" characters are recognized correctly. Accuracy: "+(float)correct[10]/(float)test_size[10]);
	
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
