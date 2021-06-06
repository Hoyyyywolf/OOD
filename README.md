# 基础图形绘制软件
## 引言
本应用主要基于JavaFx的图形框架实现，核心技术主要包括：
+ 图元类的设计：主要基于模板方法的设计模式
+ 画布管理：主要基于外观模式的设计模式
+ 撤销操作的实现：主要基于备忘录的设计模式

## 设计思想
### 图元类
为了给用户更好的使用体验，对于所有图元都有设置悬停与选中状态，这部分的绘制逻辑对所有图元来说都是一样的。同时由于许多图元的控制逻辑存在相似之处（例如直线和矩形的形状都是由两点所决定），采用模板方法的设计模式封装各个通用行为逻辑，并将不同的绘制逻辑作为抽象方法在底层实现是一个较好的选择。具体的UML类图如下：
![](https://github.com/Hoyyyywolf/Images/raw/master/OOD/item.png)

### 画布管理
图形绘制软件中经常需要根据当前绘制状态的不同对用户的输入产生不同的响应（例如绘制直线时鼠标点击与拖动图元时的鼠标点击），用一个状态标志来记录当前状态并对应不同状态调用不同的响应函数是一个合理的做法。借鉴外观模式的思想，将某种状态下用户输入的各响应函数封装为一个子系统，并抽象出统一的门面接口。在管理类中通过注册的方式动态增加子功能系统，并根据状态不同调用对应的子系统响应函数。具体的UML类图如下：
![](https://github.com/Hoyyyywolf/Images/raw/master/OOD/cc.png)

### 撤销操作
实现撤销操作可以借助备忘录模式对系统中某个程序点的状态进行备份，由于备份所用状态开销过大，单独备份本次操作撤销所需用到的状态更加地高效。因此设计一个接口`Record`用于表示一次备份，具体备份所需用到的信息由操作类自行实现。同时为实现多步撤销需要一个`Recorder`类对备份进行管理。对应的部分代码如下：
```java
interface Record {
    void undo();
}

public class Recorder {
    public Recorder(int steps); 
    public void addRecord(Record r);
    public void undo();

    protected int steps; //steps记录最大撤销次数
    protected LinkedList<Record> records;
}
```

## 实现方案
### 图元类
`AbstractItem`是所有图元类的基类，需要实现的是图元通用的选中或悬停状态，这里是通过绘制图元的最小包容矩形（即最小的容纳整个图元的矩形）来表示的，而具体图元的绘制则交由子类实现`drawItem`来绘制。
```java
public abstract class AbstractItem {
    public void draw(GraphicsContext gc){
        drawItem(gc); //绘制具体图元
       ...
        Rect rec = boundingRect(); //获取最小包容矩形
        if(overed){ //优先绘制悬停状态
            gc.setStroke(Color.RED);
            gc.strokeRect(rec.x, rec.y, rec.w, rec.h);
        }
        else if(selected){
            gc.setStroke(Color.BLACK);
            gc.strokeRect(rec.x, rec.y, rec.w, rec.h);
        }
	...
    }
	
    public abstract void drawItem(GraphicsContext gc); //绘制具体图元
    public abstract boolean hangOver(double x, double y); //该点是否悬停图元之上
    public abstract Rect boundingRect(); //最小包容矩形
    public abstract void translate(double dx, double dy); //平移图元

    protected boolean overed;
    protected boolean selected;
}
```
`AbstractBinaryItem`是所有两元图元类（由两点定义图元形状）的抽象基类，需要实现的是`boundingRect`和`translate`这些对两元图元通用的操作。
```java
public abstract class AbstractBinaryItem extends AbstractItem {
    @Override
    public Rect boundingRect(){ 
	//返回两点作为对角点对应的矩形
        double x = Math.min(v1.x, v2.x);
        double y = Math.min(v1.y, v2.y);
        return new Rect(x - 2, y - 2, Math.abs(v1.x - v2.x) + 4, Math.abs(v1.y - v2.y) + 4);
    }

    @Override
    public void translate(double dx, double dy){
	//两点作相同位移即可
        v1.translate(dx, dy);
        v2.translate(dx, dy);
    }

    protected Vector2D v1;
    protected Vector2D v2;
}
```
图元的绘制逻辑大多较为简单，只有判断某点是否悬停图元之上的方法值得一说。对于基本图元（直线，三角形，矩形，椭圆），由于它们都存在简单的函数解析式，故都是通过判断该点与解析式的偏差来实现的。而文字图元与组合图元由于不存在函数解析式，故是直接通过判断该点是否在最小包容矩形中来实现的。

### 画布管理
`CanvasController`是整个画布的管理类，保存一个`String`作为画布的状态，维持一个`MouseHandler`引用并注册具体画布的事件处理函数，并根据状态的切换切换对应的处理器。同时将所有图元组织为一个链表，并在图元状态改变时重绘整个画布以刷新显示。
```java
public class CanvasController {
    public void changeState(String s){
        if(!state.equals(s)){ //仅当状态不同时才改变
            state = s;
            handler = handlerMap.get(s); //切换事件处理器
            tempItems.clear(); //删除可能的临时图元
        }
    }

    private void initTool(){ //储存状态与对应处理器的映射
	handlerMap.put("line", new LineTool(items, tempItems, recorder));
        ...
    }

    private void initHandler(){ //注册事件处理器
        canvas.setOnMouseMoved(evt -> {
            if(handler.mouseMoved(evt))
                updateCanvas();
        });
        ...
    }

   private void updateCanvas(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); //清空画布
        items.forEach(i -> i.draw(gc)); //绘制图元
        tempItems.forEach(i -> i.draw(gc)); //绘制临时图元
    }

    String state;
    private Canvas canvas;
    private Recorder recorder;
    private List<AbstractItem> items;
    private List<AbstractItem> tempItems;
    private List<AbstractItem> selectedItems;
    private MouseHandler handler;
    private Map<String, MouseHandler> handlerMap;
}
```
处理器的具体实现逻辑较为复杂，这里仅以二元图元类绘制工具`BinaryItemTool`为例作简单介绍：
`BinaryItemTool`为二元图元的通用绘制处理器，它以函数式接口的形式储存一个二元图元的构造函数来指明具体绘制的图元类，以一次点击作为绘制开始，第二次点击作为绘制结束，同时按住`shift`键可以限制两对角点为正方形。
```java
public class BinaryItemTool implements MouseHandler {
    @Override
    public boolean mouseMoved(MouseEvent evt){
        double x = evt.getX();
        double y = evt.getY();
        if(clicked){ //绘制图元中
            if(evt.isShiftDown()){ //按住shift键，以两边中较短边为限制更新
                x -= x1;
                y -= y1;
                if(Math.abs(x) < Math.abs(y)){
                    y = Math.signum(y)*Math.abs(x);
                }
                else{
                    x = Math.signum(x)*Math.abs(y);
                }
                temp.setVec2(x1 + x, y1 + y);
            }       
            else{ //直接更新第二个点
                temp.setVec2(x, y);
            }     
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseClicked(MouseEvent evt){
        if(!evt.getButton().equals(MouseButton.PRIMARY)){ //只响应左键点击
            return false;
        }

        double x = evt.getX();
        double y = evt.getY();
        if(clicked){ //第二次点击，加入图元到链表中
            clicked = false;
            recorder.addRecord(new CreateRecord(temp, items));
            items.add(temp);
            tempItems.clear();
        }
        else{ //第一次点击，新建图元到临时链表中
            clicked = true;
            x1 = x;
            y1 = y;
            temp = supplier.get(x, y, x, y);
            tempItems.add(temp);
        }

        return true;
    }

    protected double x1;
    protected double y1;
    protected boolean clicked;
    protected Recorder recorder;
    protected AbstractBinaryItem temp;
    protected List<AbstractItem> items;
    protected List<AbstractItem> tempItems;
    protected BinaryItemSupplier supplier;
}
```

### 撤销操作
以图形创建的撤销为例，图形创建撤销时只需要知道是哪个图元即可。
```java
class CreateRecord implements Record {
    CreateRecord(AbstractItem i, List<AbstractItem> items){
        this.i= i; //记录创建的图元
        this.items = items; //图元链表
    }

    @Override
    public void undo(){
        items.remove(i); //删除即可
    }

    private AbstractItem i;
    private List<AbstractItem> items;
}
```

### 功能介绍
+ 支持直线、三角形、矩形、椭圆的绘制，均是通过两次单击绘制，同时按住`shift`键可以限制直线的角度，正方形，圆形的绘制。
+ 支持添加文字，单击画布即可输入描述。
+ 支持图形组合，框选要组合的图形并按快捷键`Ctrl+X`即可。
+ 支持图形移动及复制，选中图形并拖拽即可移动，拖拽同时按下`Alt`键即可复制图形。
+ 支持多步撤销，按下快捷键`Ctrl+Z`即可，最多可撤销20步。

### 界面展示
![](https://github.com/Hoyyyywolf/Images/raw/master/OOD/gui.png)