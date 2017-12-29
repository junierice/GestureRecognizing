import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.util.Random;
//���廭ͼ�Ļ���ͼ�ε�Ԫ
public class GR extends JFrame {//���࣬��չ��JFrame�࣬��������������

    private ObjectOutputStream output; //����������������������úͱ���ͼ���ļ�
    private JButton choices[];         //��ť���飬����������ƵĹ��ܰ�ť
    private String names[] = {
        "New",
        "Open",
        "Recognize", //�������ǻ���������ť������"�½�"��"��"��"����"
        /*�����������ǵĻ�ͼ�������еĻ����ļ�����ͼ��Ԫ��ť*/
        "Pencil", //Ǧ�ʻ���Ҳ����������϶��������ͼ
    };
    //��Ȼ��������Ľṹ�����ö����Լ��������ϵͳ֧�ֵ�����
    private Icon items[];
 
    private JLabel status;            //��ʾ���״̬����ʾ��

    private DrawPanel drawingArea;       //��ͼ����
    private ShowPanel sh;
    private int width = 880,  height = 550;    //���廭ͼ�����ʼ��С
    drawings[] itemList = new drawings[5000]; //������Ż���ͼ�ε�����
    private int currentChoice = 3;            //����Ĭ�ϻ�ͼ״̬Ϊ��ʻ�
    int index = 0;                         //��ǰ�Ѿ����Ƶ�ͼ����Ŀ
  
    int R, G, B;                           //������ŵ�ǰɫ��ֵ
    int f1, f2;                  //������ŵ�ǰ������
    String style1;              //������ŵ�ǰ����
    private float stroke = 20.0f;  //���û��ʴ�ϸ��Ĭ��ֵΪ1.0f
    
    public  GR(){ //���캯��
    
        super("Drawing Pad");
        
        
        
        JMenuBar bar = new JMenuBar();      //����˵���
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
//�½��ļ��˵���
        JMenuItem newItem = new JMenuItem("New");
        newItem.setMnemonic('N');
        newItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        newFile();      //�����������������½��ļ�������
                    }
                });
        fileMenu.add(newItem);
//�����ļ��˵���
        JMenuItem saveItem = new JMenuItem("Recognize");
        saveItem.setMnemonic('S');
        saveItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        saveFile();     //���������������ñ����ļ�������
                    }
                });
        fileMenu.add(saveItem);
//�˳��˵���
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic('X');
        exitItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0); //��������������˳���ͼ�����
                    }
                });
        fileMenu.add(exitItem);
        bar.add(fileMenu);



//������ʾ�˵���
        JMenuItem aboutItem = new JMenuItem("About this Drawing Pad!");
        aboutItem.setMnemonic('A');
        aboutItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null,
                                "This is a mini drawing pad!",
                                " ��ͼ�����˵�� ",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                });

        items = new ImageIcon[names.length];
//�������ֻ���ͼ�εİ�ť
        drawingArea = new DrawPanel();
        sh = new ShowPanel();
        choices = new JButton[names.length];

        ButtonHandler handler = new ButtonHandler();

//����������Ҫ��ͼ��ͼ�꣬��Щͼ�궼�������Դ�ļ���ͬ��Ŀ¼����
        for (int i = 0; i < choices.length; i++) {//items[i]=new ImageIcon( MiniDrawPad.class.getResource(names[i] +".gif"));
            //�����jbuilder�����б�������Ӧ����������䵼��ͼƬ
            items[i] = new ImageIcon(names[i] + ".gif");
            //Ĭ�ϵ���jdk����jcreator�����У��ô���䵼��ͼƬ
            choices[i] = new JButton("", items[i]);


        }
//���������������밴ť����
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


//����ѡ��

        
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
//��ť������ButtonHanler�࣬�ڲ��࣬��������������ť�Ĳ���
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

//����¼�mouseA�࣬�̳���MouseAdapter��������������Ӧ�¼�����
    class mouseA extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            
            itemList[index].x1 = itemList[index].x2 = e.getX();
            itemList[index].y1 = itemList[index].y2 = e.getY();
            //�����ǰѡ���ͼ������ʻ�������Ƥ�������������Ĳ���
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
//����¼�mouseB��̳���MouseMotionAdapter�������������϶�������ƶ�ʱ����Ӧ����
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
//��ͼ����࣬������ͼ
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
            Graphics2D g2d = (Graphics2D) g;    //���廭��
            int j = 0;
            while (j <= index) {
                draw(g2d, itemList[j]);
                j++;
            }
        }
        void draw(Graphics2D g2d, drawings i) {
            i.draw(g2d);//�����ʴ��뵽���������У�������ɸ��ԵĻ�ͼ
        }
    }
//�½�һ����ͼ������Ԫ����ĳ����
    void createNewItem() {
        drawingArea.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        itemList[index] = new Pencil();
                
        itemList[index].type = currentChoice;
        itemList[index].R = R;
        itemList[index].G = G;
        itemList[index].B = B;
        itemList[index].stroke = stroke;
    }

   
//����ͼ���ļ������
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

//�½�һ���ļ������
    public void newFile() {
    	status.setText("Result: ");
        index = 0;
        currentChoice = 3;

        stroke = 20.0f;
        createNewItem();
        repaint();//���й�ֵ����Ϊ��ʼ״̬�������ػ�
    }
//��������
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }//����������Ϊ��ǰwindows���
        GR newPad = new GR();
        newPad.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
    }
}
class drawings implements Serializable {//���࣬����ͼ�ε�Ԫ���õ����л��ӿڣ�����ʱ����

    int x1, y1, x2, y2; //������������
    int R, G, B;        //����ɫ������
    float stroke;       //����������ϸ����
    int type;       //������������
    String s1;
    String s2;      //��������������
    void draw(Graphics2D g2d) {
    }
    ;//�����ͼ����
}
/*******************************************************************************
�����Ǹ��ֻ���ͼ�ε�Ԫ�����࣬���̳��Ը���drawings������ϸ���̳еĸ���
 ********************************************************************************/

class Pencil extends drawings {//��ʻ���

    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g2d.drawLine(x1, y1, x2, y2);
    }
}
