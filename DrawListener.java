package basic.hj0518;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class DrawListener implements MouseListener, ActionListener, ChangeListener {
    public Graphics g;
    HPanel drawJP;

    // 记录鼠标按下和释放的坐标，用于确定图形的位置和大小
    int x1, y1, x2, y2;

    // 当前选择的绘图类型，默认为直线
    String type = "直线";

    // 颜色相关的组件和变量
    int red = 1, green = 1, blue = 1;  // RGB颜色值，初始值设为1
    JSlider sliderR, sliderG, sliderB;  // RGB颜色滑块
    JLabel labelR, labelG, labelB;     // RGB颜色值显示标签
    JButton jslColorBtn;               // 颜色预览按钮

    Color color = Color.BLACK;

    ArrayList<HShape> shapeList = new ArrayList<>();
    int index = 0;

    /**
     * 设置颜色相关的组件
     * 将RGB滑块和标签的引用保存到监听器中
     */
    public void setJslComponent(JSlider sliderR, JSlider sliderG, JSlider sliderB,
                                JLabel labelR, JLabel labelG, JLabel labelB) {
        this.sliderR = sliderR;
        this.sliderG = sliderG;
        this.sliderB = sliderB;
        this.labelR = labelR;
        this.labelG = labelG;
        this.labelB = labelB;
    }

    // 画笔粗细相关组件
    int fontSize = 1;                  // 画笔粗细值，初始值为1
    JSlider jslFontSize;              // 画笔粗细滑块
    JLabel labelFontSize;             // 画笔粗细显示标签

    /**
     * 设置画笔粗细相关的组件
     * 将滑块和标签的引用保存到监听器中
     */
    public void setStrokeWidthComponent(JSlider sliderSize, JLabel labelSize) {
        this.jslFontSize = sliderSize;
        this.labelFontSize = labelSize;
    }

    /**
     * 处理滑块值改变事件
     * 用于更新颜色和画笔粗细
     * 根据滑块的类型执行不同的更新操作
     */
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        int value = slider.getValue();

        int sliHashCode = slider.hashCode();

        // 处理画笔粗细滑块
        if (sliHashCode == jslFontSize.hashCode()) {
            fontSize = value;
            Graphics2D g2d = (Graphics2D) g;
            BasicStroke stroke = new BasicStroke(fontSize);
            g2d.setStroke(stroke);
            labelFontSize.setText("画笔尺寸：" + fontSize);
        }

        // 处理颜色滑块
        if (sliHashCode == sliderR.hashCode()) {
            red = value;
            labelR.setText("红:" + red);
        } else if (sliHashCode == sliderG.hashCode()) {
            green = value;
            labelG.setText("绿:" + green);
        } else if (sliHashCode == sliderB.hashCode()) {
            blue = value;
            labelB.setText("蓝:" + blue);
        }
        // 更新当前颜色
        Color color2 = new Color(red, green, blue);
        color = color2;
        g.setColor(color);
        jslColorBtn.setBackground(color2);

    }

    /**
     * 处理按钮点击事件
     * 用于切换绘图工具和颜色
     * 根据按钮的类型执行不同的操作
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String ac = e.getActionCommand();

        // 处理颜色按钮点击
        if(ac.equals("ColorBtn")) {
            // 获取按钮对象 以及按钮对象的背景色
            JButton btn = (JButton) e.getSource();// 获取事件的源头对象 - 按钮对象
            color = btn.getBackground();// 获取按钮的背景色
            // 设置颜色给画笔
            g.setColor(color);
        }

        // 处理清空画板
        else if(ac.equals("清空")) {
            // 保存当前颜色
            Color currentColor = g.getColor();
            // 清除整个面板
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, drawJP.getWidth(), drawJP.getHeight());  // 使用固定尺寸，确保覆盖整个绘图区域
            // 恢复原来的颜色
            g.setColor(currentColor);
            shapeList.clear();
            index = 0;
        }
        // 处理撤回操作
        else if(ac.equals("撤回")) {
            if (index > 0) {
                shapeList.remove(--index);
                // 重绘所有图形
                drawJP.repaint();
            }
        }
        // 处理其他绘图工具
        else {
            type = ac;  // 更新绘图类型
            g.setColor(new Color(red, green, blue));  // 设置当前颜色
        }
    }

    /**
     * 处理鼠标点击事件
     * 目前仅用于调试输出
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("鼠标在窗体按钮上点击了");
    }

    /**
     * 记录鼠标按下的起始位置
     * 用于确定图形的起始点
     */
    @Override
    public void mousePressed(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();

        // 每次鼠标按下都更新画笔
        g = drawJP.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        BasicStroke stroke = new BasicStroke(fontSize);
        g2d.setStroke(stroke);


    }

    /**
     * 处理鼠标释放事件
     * 根据选择的工具类型绘制相应的图形
     * 支持多种图形的绘制，包括：
     * 1. 直线
     * 2. 矩形（空心和实心）
     * 3. 圆形（空心和实心）
     * 4. 等腰三角形（空心和实心）
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();

        if (type.equals("直线") || type.equals("矩形") || type.equals("圆形")
                || type.equals("实心矩形") || type.equals("实心圆形") || type.equals("等腰三角形")
                || type.equals("实心等腰三角形") || type.equals("谢尔宾斯基地毯") || type.equals("长方体")
                || type.equals("门格海绵") || type.equals("橡皮擦") || type.equals("清空")) {
            HShape hShape = new HShape(type, x1, y1, x2, y2, type.equals("橡皮擦")? Color.WHITE : color);
            hShape.draw(g);
            shapeList.add(index++, hShape);
        }
    }
    /**
     * 处理鼠标进入事件
     * 目前未实现具体功能
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * 处理鼠标离开事件
     * 目前未实现具体功能
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
