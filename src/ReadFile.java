import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadFile {
	public static int miniPoint=200;
	public static int minx = 200;
	public static int maxx = 0;
	public static int miny = 200;
	public static int maxy = 0;
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
                    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
                    		DocumentBuilder builder = factory.newDocumentBuilder();   
                    		Document doc = builder.parse(f);   
                    		NodeList n2 = doc.getElementsByTagName("Gesture");
                    		NamedNodeMap attributes = n2.item(0).getAttributes();
                    		Node attribute = attributes.item(2);
                    		String attributeValue = attribute.getNodeValue();
                    		if(Integer.parseInt(attributeValue)<miniPoint){	
                    			miniPoint = Integer.parseInt(attributeValue);
                    		}
                    		NodeList nl = doc.getElementsByTagName("Point"); 
                    		for(int i = 0; i < nl.getLength();i++){
                    			Node node0=nl.item(i);
                    			NamedNodeMap m1=node0.getAttributes();
                    			Node nodey=m1.item(2);
                    			Node nodex=m1.item(3);
                    			nodey.getNodeValue();
                    			if(Integer.parseInt(nodey.getNodeValue())<miny)
                    				miny = Integer.parseInt(nodey.getNodeValue());
                    			if(Integer.parseInt(nodex.getNodeValue())<minx)
                    				minx = Integer.parseInt(nodex.getNodeValue());
                    			if(Integer.parseInt(nodex.getNodeValue())>maxx)
                    				maxx = Integer.parseInt(nodex.getNodeValue());
                    			if(Integer.parseInt(nodey.getNodeValue())>maxy)
                    				maxy = Integer.parseInt(nodey.getNodeValue());
                    			
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
	
	public static void main(String arge[]) {
		traverseFolder2("gesture");
		System.out.println(miniPoint);
		System.out.println(maxx);
		System.out.println(minx);
		System.out.println(maxy);
		System.out.println(miny);
	}
	
	
	
}
