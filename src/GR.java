import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.util.Random;
//定义画图的基本图形单元
public class GR extends JFrame {//主类，扩展了JFrame类，用来生成主界面

    private ObjectOutputStream output; //定义输入输出流，用来调用和保存图像文件
    private JButton choices[];         //按钮数组，存放以下名称的功能按钮
    private String names[] = {
        "New",
        "Open",
        "Recognize", //这三个是基本操作按钮，包括"新建"、"打开"、"保存"
        /*接下来是我们的画图板上面有的基本的几个绘图单元按钮*/
        "Pencil", //铅笔画，也就是用鼠标拖动着随意绘图
    };
    //当然这里的灵活的结构可以让读者自己随意添加系统支持的字体
    private Icon items[];
 
    private JLabel status;            //显示鼠标状态的提示条

    private DrawPanel drawingArea;       //画图区域
    private ShowPanel sh;
    private int width = 880,  height = 550;    //定义画图区域初始大小
    drawings[] itemList = new drawings[5000]; //用来存放基本图形的数组
    private int currentChoice = 3;            //设置默认画图状态为随笔画
    int index = 0;                         //当前已经绘制的图形数目
  
    int R, G, B;                           //用来存放当前色彩值
    int f1, f2;                  //用来存放当前字体风格
    String style1;              //用来存放当前字体
    private float stroke = 20.0f;  //设置画笔粗细，默认值为1.0f
    
    public  GR(){ //构造函数
    
        super("Drawing Pad");
        
        
        
        JMenuBar bar = new JMenuBar();      //定义菜单条
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
//新建文件菜单条
        JMenuItem newItem = new JMenuItem("New");
        newItem.setMnemonic('N');
        newItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        newFile();      //如果被触发，则调用新建文件函数段
                    }
                });
        fileMenu.add(newItem);
//保存文件菜单项
        JMenuItem saveItem = new JMenuItem("Recognize");
        saveItem.setMnemonic('S');
        saveItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        saveFile();     //如果被触发，则调用保存文件函数段
                    }
                });
        fileMenu.add(saveItem);
//退出菜单项
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic('X');
        exitItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0); //如果被触发，则退出画图板程序
                    }
                });
        fileMenu.add(exitItem);
        bar.add(fileMenu);



//设置提示菜单项
        JMenuItem aboutItem = new JMenuItem("About this Drawing Pad!");
        aboutItem.setMnemonic('A');
        aboutItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null,
                                "This is a mini drawing pad!",
                                " 画图板程序说明 ",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                });

        items = new ImageIcon[names.length];
//创建各种基本图形的按钮
        drawingArea = new DrawPanel();
        sh = new ShowPanel();
        choices = new JButton[names.length];

        ButtonHandler handler = new ButtonHandler();

//导入我们需要的图形图标，这些图标都存放在与源文件相同的目录下面
        for (int i = 0; i < choices.length; i++) {//items[i]=new ImageIcon( MiniDrawPad.class.getResource(names[i] +".gif"));
            //如果在jbuilder下运行本程序，则应该用这条语句导入图片
            items[i] = new ImageIcon(names[i] + ".gif");
            //默认的在jdk或者jcreator下运行，用此语句导入图片
            choices[i] = new JButton("", items[i]);


        }
//将动作侦听器加入按钮里面
        for (int i = 3; i < choices.length - 3; i++) {
            choices[i].addActionListener(handler);
        }
        choices[0].addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        newFile();
                    }
                });

        choices[1].addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        saveFile();
                    }
                });


//字体选择

        
        Container c = getContentPane();
        super.setJMenuBar(bar);
        drawingArea.setBounds(50, 50, 250, 250);
        status = new JLabel();
        status.setBounds(50, 300, 250, 100);
        
        c.add(status);
        c.add(drawingArea);
        c.add(sh);
        status.setText("Result: ");

        createNewItem();
        setSize(width, height);
        show();
    }
//按钮侦听器ButtonHanler类，内部类，用来侦听基本按钮的操作
    public class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int j = 3; j < choices.length - 3; j++) {
                if (e.getSource() == choices[j]) {
                    currentChoice = j;
                    createNewItem();
                    repaint();
                }
            }
        }
    }

//鼠标事件mouseA类，继承了MouseAdapter，用来完成鼠标相应事件操作
    class mouseA extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            
            itemList[index].x1 = itemList[index].x2 = e.getX();
            itemList[index].y1 = itemList[index].y2 = e.getY();
            //如果当前选择的图形是随笔画或者橡皮擦，则进行下面的操作
            if (currentChoice == 3 ) {
                itemList[index].x1 = itemList[index].x2 = e.getX();
                itemList[index].y1 = itemList[index].y2 = e.getY();
                index++;
                createNewItem();
            }
        }
        public void mouseReleased(MouseEvent e) {
            
            if (currentChoice == 3 ) {
                itemList[index].x1 = e.getX();
                itemList[index].y1 = e.getY();
            }
            itemList[index].x2 = e.getX();
            itemList[index].y2 = e.getY();
            repaint();
            index++;
            createNewItem();
        }
        public void mouseEntered(MouseEvent e) {
          
        }
        public void mouseExited(MouseEvent e) {
         
        }
    }
//鼠标事件mouseB类继承了MouseMotionAdapter，用来完成鼠标拖动和鼠标移动时的相应操作
    class mouseB extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
           
            if (currentChoice == 3 ) {
                itemList[index - 1].x1 = itemList[index].x2 = itemList[index].x1 = e.getX();
                itemList[index - 1].y1 = itemList[index].y2 = itemList[index].y1 = e.getY();
                index++;
                createNewItem();
            } else {
                itemList[index].x2 = e.getX();
                itemList[index].y2 = e.getY();
            }
            repaint();
        }
        public void mouseMoved(MouseEvent e) {
            
        }
    }
    class ShowPanel extends JPanel {
 
    	Image image=null;
        
        public void paint(Graphics g){
            try {
                image=ImageIO.read(new File("multistrokes.gif"));
                g.drawImage(image, 355, 25, 449, 357, null);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
//画图面板类，用来画图
    class DrawPanel extends JPanel {
        public DrawPanel() {
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            setBackground(Color.white);
            addMouseListener(new mouseA());
            addMouseMotionListener(new mouseB());
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;    //定义画笔
            int j = 0;
            while (j <= index) {
                draw(g2d, itemList[j]);
                j++;
            }
        }
        void draw(Graphics2D g2d, drawings i) {
            i.draw(g2d);//将画笔传入到各个子类中，用来完成各自的绘图
        }
    }
//新建一个画图基本单元对象的程序段
    void createNewItem() {
        drawingArea.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        itemList[index] = new Pencil();
                
        itemList[index].type = currentChoice;
        itemList[index].R = R;
        itemList[index].G = G;
        itemList[index].B = B;
        itemList[index].stroke = stroke;
    }

   
//保存图形文件程序段
    public void saveFile() {
    	status.setText("Calculating...");
    	Dimension imageSize = drawingArea.getSize();
        BufferedImage image = new BufferedImage(imageSize.width,
                imageSize.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        drawingArea.paint(g);
        g.dispose();
        try {
            ImageIO.write(image, "png", new File("1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }  
      
        WriteImage rc = new WriteImage();  
        try {
			rc.getImagePixel("1.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        KNN.traverseFolder2("AtoJ");
        String s = KNN.mini_path;
 // System.out.println(s);
  KNN.mini_distance = 28*28;
        String[] r = s.split("AtoJ");
        System.out.println(r[1].charAt(1));
        status.setText("Result is "+r[1].charAt(1)+".");
    }

//新建一个文件程序段
    public void newFile() {
    	status.setText("Result: ");
        index = 0;
        currentChoice = 3;

        stroke = 20.0f;
        createNewItem();
        repaint();//将有关值设置为初始状态，并且重画
    }
//主函数段
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }//将界面设置为当前windows风格
        GR newPad = new GR();
        newPad.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
    }
}
class drawings implements Serializable {//父类，基本图形单元，用到串行化接口，保存时所用

    int x1, y1, x2, y2; //定义坐标属性
    int R, G, B;        //定义色彩属性
    float stroke;       //定义线条粗细属性
    int type;       //定义字体属性
    String s1;
    String s2;      //定义字体风格属性
    void draw(Graphics2D g2d) {
    }
    ;//定义绘图函数
}
/*******************************************************************************
下面是各种基本图形单元的子类，都继承自父类drawings，请仔细理解继承的概念
 ********************************************************************************/

class Pencil extends drawings {//随笔画类

    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g2d.drawLine(x1, y1, x2, y2);
    }
}
