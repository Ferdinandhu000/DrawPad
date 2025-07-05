package basic.hj0518;

import java.awt.*;

public class HShape {
    String type;
    Color color;
    int x1,x2,y1,y2;

    public HShape(String type, int x1, int y1, int x2, int y2, Color color) {
        this.type = type;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(color);

        if (type.equals("直线")) {
            g.drawLine(x1, y1, x2, y2);
        } else if (type.equals("矩形")) {
            g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
        } else if (type.equals("圆形")) {
            g.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
        } else if (type.equals("实心矩形")) {
            g.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
        } else if (type.equals("实心圆形")) {
            g.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
        } else if (type.equals("实心等腰三角形")) {
            // 绘制实心等腰三角形
            int mx = (x1 + x2) / 2;  // 计算顶点x坐标
            int my = y1;             // 顶点y坐标
            int h = Math.abs(y2 - y1);// 计算高度
            int w = Math.abs(x2 - x1);// 计算宽度
            double dx = w / 2.0 / h;  // 计算每行宽度变化量
            // 逐行绘制三角形
            for (int i = 0; i < h; i++) {
                if (y1 < y2) {
                    g.drawLine((int) (mx - dx * i), my + i, (int) (mx + dx * i), my + i);
                } else {
                    g.drawLine((int) (mx - dx * i), my - i, (int) (mx + dx * i), my - i);
                }
            }
        } else if (type.equals("等腰三角形")) {
            // 绘制等腰三角形轮廓
            int mx = (x1 + x2) / 2;  // 计算顶点x坐标
            int my = y1;             // 顶点y坐标
            // 绘制三条边
            g.drawLine(x1, y2, mx, my);  // 左边
            g.drawLine(mx, my, x2, y2);  // 右边
            g.drawLine(x1, y2, x2, y2);  // 底边
        } else if (type.equals("橡皮擦")) {
            // 绘制橡皮擦效果
            g.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
        } else if (type.equals("谢尔宾斯基地毯")) {
            drawSherbenskyCarpet(g, Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
        } else if (type.equals("长方体")) {
//            draw3DRect(g, Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
            int[][][] arr = new int[10][10][10];
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                    for (int k = 0; k < arr[i][j].length; k++) {
                        if (i != 0 || i != arr.length - 1) {
                            if (j == 0 || j == arr[i].length - 1) {
                                if (k == 0 || k == arr[i][j].length - 1) {
                                    if (i % 3 == 0) {
                                        arr[i][j][k] = 1;

                                    }
                                }
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                    for (int k = 0; k < arr[i][j].length; k++) {
                        if (arr[i][j][k] == 1) {
                            draw3DRect(g, x1 + j * 50 - i * 25, y1 + k * 50 + i * 10, 50, 50);
                        }
                    }
                }
            }
        } else if (type.equals("门格海绵")) {
            drawMengerSponge(g, Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
        }
    }
    public void drawSherbenskyCarpet(Graphics g, int x, int y, int w, int h) {
        if(w < 3) {
            return;
        }
        g.fillRect(x+w/3, y+h/3, w/3, h/3);

        drawSherbenskyCarpet(g, x, y, w/3, h/3);
        drawSherbenskyCarpet(g, x+w/3, y, w/3, h/3);
        drawSherbenskyCarpet(g, x+w/3*2, y, w/3, h/3);
        drawSherbenskyCarpet(g, x, y+h/3, w/3, h/3);
        drawSherbenskyCarpet(g, x+w/3, y+h/3, w/3, h/3);
        drawSherbenskyCarpet(g, x+w/3*2, y+h/3, w/3, h/3);
        drawSherbenskyCarpet(g, x, y+h/3*2, w/3, h/3);
        drawSherbenskyCarpet(g, x+w/3, y+h/3*2, w/3, h/3);
        drawSherbenskyCarpet(g, x+w/3*2, y+h/3*2, w/3, h/3);
    }

    public void draw3DRect(Graphics g, int x, int y, int w, int h) {
        Point p1 = new Point(x, y + h/4);
        Point p2 = new Point(x + w/2, y + h/2);
        Point p3 = new Point(x + w/2, y + h);
        Point p4 = new Point(x, y + h - h/4);
        Point p5 = new Point(x + w/2, y);
        Point p6 = new Point(x + w, y + h/4);
        Point p7 = new Point(x + w, y + h - h/4);
        Point p8 = new Point(x + w/2, y + h + h/2);

        Polygon py1 = new Polygon();
        py1.addPoint(p1.x, p1.y);
        py1.addPoint(p2.x, p2.y);
        py1.addPoint(p3.x, p3.y);
        py1.addPoint(p4.x, p4.y);

        Polygon py2 = new Polygon();
        py2.addPoint(p1.x, p1.y);
        py2.addPoint(p5.x, p5.y);
        py2.addPoint(p6.x, p6.y);
        py2.addPoint(p2.x, p2.y);

        Polygon py3 = new Polygon();
        py3.addPoint(p2.x, p2.y);
        py3.addPoint(p6.x, p6.y);
        py3.addPoint(p7.x, p7.y);
        py3.addPoint(p3.x, p3.y);

        g.setColor(new Color(220, 220, 220));
        g.fillPolygon(py1);

        g.setColor(new Color(170, 170, 170));
        g.fillPolygon(py2);

        g.setColor(new Color(120, 120, 120));
        g.fillPolygon(py3);


    }

    public void drawMengerSponge(Graphics g, int x, int y, int w, int h) {

        Point p1 = new Point(x, y + h/4);
        Point p2 = new Point(x + w/2, y + h/2);
        Point p3 = new Point(x + w/2, y + h);
        Point p4 = new Point(x, y + h - h/4);
        Point p5 = new Point(x + w/2, y);
        Point p6 = new Point(x + w, y + h/4);
        Point p7 = new Point(x + w, y + h - h/4);
        Point p8 = new Point(x + w/2, y + h/2);

        Polygon py1 = new Polygon();
        py1.addPoint(p1.x, p1.y);
        py1.addPoint(p2.x, p2.y);
        py1.addPoint(p3.x, p3.y);
        py1.addPoint(p4.x, p4.y);

        Polygon py2 = new Polygon();
        py2.addPoint(p1.x, p1.y);
        py2.addPoint(p5.x, p5.y);
        py2.addPoint(p6.x, p6.y);
        py2.addPoint(p2.x, p2.y);

        Polygon py3 = new Polygon();
        py3.addPoint(p2.x, p2.y);
        py3.addPoint(p6.x, p6.y);
        py3.addPoint(p7.x, p7.y);
        py3.addPoint(p3.x, p3.y);

        g.setColor(new Color(220, 220, 220));
        g.fillPolygon(py1);

        g.setColor(new Color(170, 170, 170));
        g.fillPolygon(py2);

        g.setColor(new Color(120, 120, 120));
        g.fillPolygon(py3);

        if(w < 16 || h < 16 ) {
            return;
        }


        int subW = w/3;
        int subH = h/3;

        // 第一层
        drawMengerSponge(g, p1.x + subW, (p1.y+p4.y)/2 - subH/2, subW, subH);//7
        drawMengerSponge(g, p1.x + subW*3/2, (p1.y+p4.y)/2 - subH/4, subW, subH);//6
        drawMengerSponge(g, p1.x + subW*2, (p1.y+p4.y)/2, subW, subH);//5
        drawMengerSponge(g, p1.x + subW/2, (p1.y+p4.y)/2 - subH/4, subW, subH);//8
        drawMengerSponge(g, p1.x + subW*3/2, (p1.y+p4.y)/2 + subH/4, subW, subH);//4
        drawMengerSponge(g, p1.x, (p1.y+p4.y)/2,subW, subH);//1
        drawMengerSponge(g, p1.x + subW/2, (p1.y+p4.y)/2 + subH/4, subW, subH);//2
        drawMengerSponge(g, p1 .x+ subW, (p1.y+p4.y)/2 + subH/2, subW, subH);//3






        // 第二层
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        drawMengerSponge(g, p1.x + subW, (p1.y+p4.y)/2  - subH, subW, subH);
        drawMengerSponge(g, p1.x + subW*2, (p1.y+p4.y)/2 - subH/2, subW, subH);
        drawMengerSponge(g, p1.x, (p1.y+p4.y)/2 - subH/2,subW, subH);
        drawMengerSponge(g, p1 .x+ subW, (p1.y+p4.y)/2 + subH/2 - subH/2, subW, subH);






        // 第三层
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        drawMengerSponge(g, p1.x + subW, (p1.y+p4.y)/2 - subH/2 - subH, subW, subH);//7
        drawMengerSponge(g, p1.x + subW*3/2, (p1.y+p4.y)/2 - subH/4 - subH, subW, subH);//6
        drawMengerSponge(g, p1.x + subW*2, (p1.y+p4.y)/2 - subH, subW, subH);//5
        drawMengerSponge(g, p1.x + subW/2, (p1.y+p4.y)/2 - subH/4 - subH, subW, subH);//8
        drawMengerSponge(g, p1.x + subW*3/2, (p1.y+p4.y)/2 + subH/4 - subH, subW, subH);//4
        drawMengerSponge(g, p1.x, (p1.y+p4.y)/2 - subH,subW, subH);//1
        drawMengerSponge(g, p1.x + subW/2, (p1.y+p4.y)/2 + subH/4 - subH, subW, subH);//2
        drawMengerSponge(g, p1 .x+ subW, (p1.y+p4.y)/2 + subH/2 - subH, subW, subH);//3

    }
}


