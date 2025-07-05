package basic.hj0518;



import javax.swing.*;
import java.awt.*;

public class DrawPadv5 {
    DrawListener dl = new DrawListener();
    HPanel drawJP = new HPanel();

    /**
     * 显示画板界面
     * 初始化所有UI组件并设置布局
     */
    public void showUI() {
        drawJP.dl = dl;
        dl.drawJP = drawJP;
        // 创建主窗口
        JFrame jf = new JFrame();
        jf.setTitle("画板v5.0");
        jf.setSize(1000,800);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建各个面板
        JPanel topCtrlJP = new JPanel();        // 顶部控制面板
        JPanel shapeBtnJP = new JPanel();      // 图形按钮面板
        JPanel otherCtrlJP = new JPanel();     // 其他控制面板

        JPanel penCtrlJP  = new JPanel();      // 画笔控制面板
        JPanel strokeWidthJslJP = new JPanel();// 画笔粗细滑块面板
        JPanel colorBtnJP = new JPanel();      // 颜色按钮面板
        JPanel colorJslJP = new JPanel();      // 颜色滑块面板

        // 设置各个面板的尺寸
        Dimension dim_topCtrlJP = new Dimension(1100, 30);
        Dimension dim_shapeBtnJP = new Dimension(120, 570);
        Dimension dim_otherCtrlJP = new Dimension(900, 570);
        Dimension dim_penCtrlJP = new Dimension(900, 150);
        Dimension dim_strokeWidthJslJP = new Dimension(260, 150);
        Dimension dim_colorBtnJP = new Dimension(360, 150);
        Dimension dim_colorJslJP = new Dimension(280, 150);

        // 应用尺寸设置
        topCtrlJP.setPreferredSize(dim_topCtrlJP);
        shapeBtnJP.setPreferredSize(dim_shapeBtnJP);
        otherCtrlJP.setPreferredSize(dim_otherCtrlJP);
        penCtrlJP.setPreferredSize(dim_penCtrlJP);
        strokeWidthJslJP.setPreferredSize(dim_strokeWidthJslJP);
        colorBtnJP.setPreferredSize(dim_colorBtnJP);
        colorJslJP.setPreferredSize(dim_colorJslJP);

        // 设置布局管理器
        topCtrlJP.setLayout(new BorderLayout());
        otherCtrlJP.setLayout(new BorderLayout());
        penCtrlJP.setLayout(new BorderLayout());

        // 设置面板背景色
        topCtrlJP.setBackground(Color.LIGHT_GRAY);
        shapeBtnJP.setBackground(Color.DARK_GRAY);
        drawJP.setBackground(Color.WHITE);
        strokeWidthJslJP.setBackground(Color.LIGHT_GRAY);
        colorBtnJP.setBackground(Color.LIGHT_GRAY);

        // 组装面板
        penCtrlJP.add(strokeWidthJslJP, BorderLayout.WEST);
        penCtrlJP.add(colorBtnJP, BorderLayout.EAST);
        penCtrlJP.add(colorJslJP, BorderLayout.CENTER);

        otherCtrlJP.add(penCtrlJP, BorderLayout.SOUTH);
        otherCtrlJP.add(drawJP, BorderLayout.CENTER);

        jf.add(topCtrlJP, BorderLayout.NORTH);
        jf.add(shapeBtnJP, BorderLayout.WEST);
        jf.add(otherCtrlJP, BorderLayout.CENTER);

        // 初始化各个功能面板
        initColorBtnJp(colorBtnJP);
        initColorJslJp(colorJslJP);
        initStrokeWidthJp(strokeWidthJslJP);
        initShapeBtnJp(shapeBtnJP);

        jf.setVisible(true);

        // 为绘图面板添加鼠标监听器
        drawJP.addMouseListener(dl);

        // 获取绘图对象并传递给监听器
        Graphics g = drawJP.getGraphics();
        dl.g = g;
    }

    /**
     * 初始化图形按钮面板
     * 创建各种绘图工具的按钮
     */
    public void initShapeBtnJp(JPanel shapeBtnJp) {
        String[] btnType = {"直线", "圆形", "矩形", "实心圆形", "实心矩形", "等腰三角形",
                "实心等腰三角形","谢尔宾斯基地毯","长方体","门格海绵","橡皮擦","撤回","清空"};
        for (int i = 0; i < btnType.length; i++) {
            JButton btn = new JButton(btnType[i]);
            shapeBtnJp.add(btn);
            btn.addActionListener(dl);
        }
    }

    /**
     * 初始化颜色按钮面板
     * 创建预设颜色的按钮
     */
    public void initColorBtnJp(JPanel colorBtnJp) {
        // 定义预设颜色数组
        Color[] btnColors = {Color.BLACK, Color.RED, Color.GREEN,
                Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA,
                Color.GRAY, Color.LIGHT_GRAY, Color.DARK_GRAY,
                Color.PINK, Color.ORANGE, Color.WHITE};

        // 创建颜色按钮
        Dimension cDim = new Dimension(20, 20);
        for(int i = 0 ; i < btnColors.length ; i++) {
            JButton btn = new JButton();
            btn.setActionCommand("ColorBtn");
            btn.setBackground(btnColors[i]);
            colorBtnJp.add(btn);
            btn.setPreferredSize(cDim);
            btn.addActionListener(dl);
        }
    }

    /**
     * 初始化画笔粗细控制面板
     * 创建画笔粗细滑块和标签
     */
    public void initStrokeWidthJp(JPanel fontSizeJp) {
        JSlider sliderSize = new JSlider(JSlider.HORIZONTAL, 0, 20, 1);
        sliderSize.setMajorTickSpacing(4);
        sliderSize.setPaintLabels(true);
        sliderSize.addChangeListener(dl);
        JLabel labelSize = new JLabel("画笔尺寸:" + 1);

        fontSizeJp.add(labelSize);
        fontSizeJp.add(sliderSize);

        dl.setStrokeWidthComponent(sliderSize, labelSize);
    }

    /**
     * 初始化颜色滑块面板
     * 创建RGB颜色调节滑块和颜色预览按钮
     */
    public void initColorJslJp(JPanel colorJslJp) {
        // 创建RGB颜色滑块
        JSlider sliderR = new JSlider(JSlider.HORIZONTAL, 0, 255, 1);
        JSlider sliderG = new JSlider(JSlider.HORIZONTAL, 0, 255, 1);
        JSlider sliderB = new JSlider(JSlider.HORIZONTAL, 0, 255, 1);

        // 设置滑块刻度
        sliderR.setMajorTickSpacing(50);
        sliderG.setMajorTickSpacing(50);
        sliderB.setMajorTickSpacing(50);
        sliderR.setPaintLabels(true);
        sliderG.setPaintLabels(true);
        sliderB.setPaintLabels(true);

        // 添加监听器
        sliderR.addChangeListener(dl);
        sliderG.addChangeListener(dl);
        sliderB.addChangeListener(dl);

        // 创建颜色值标签
        JLabel labelR = new JLabel("R:" + 1);
        JLabel labelG = new JLabel("G:" + 1);
        JLabel labelB = new JLabel("B:" + 1);

        // 添加组件到面板
        colorJslJp.add(labelR);
        colorJslJp.add(sliderR);
        colorJslJp.add(labelG);
        colorJslJp.add(sliderG);
        colorJslJp.add(labelB);
        colorJslJp.add(sliderB);

        // 创建颜色预览按钮
        JButton jslColorBtn = new JButton();
        jslColorBtn.setActionCommand("ColorBtn");
        jslColorBtn.setBackground(Color.BLACK);
        jslColorBtn.setPreferredSize(new Dimension(200, 30));
        colorJslJp.add(jslColorBtn);
        jslColorBtn.addActionListener(dl);
        dl.jslColorBtn = jslColorBtn;

        // 设置监听器的颜色组件
        dl.setJslComponent(sliderR, sliderG, sliderB, labelR, labelG, labelB);
    }

    /**
     * 程序入口点
     */
    public static void main(String[] args) {
        DrawPadv5 dp = new DrawPadv5();
        dp.showUI();
    }
}
