package basic.hj0518;

import javax.swing.*;
import java.awt.*;

public class HPanel extends JPanel {
    DrawListener dl;

    // 改造父类的刷新方法
    public void paint(Graphics g) {
        super.paint(g);// 刷新父类方法 superClass

        // 增加我们需要绘制的图形的代码
        for(int i = 0; i < dl.index; i++){
            dl.shapeList.get(i).draw(g);
        }
    }
}
