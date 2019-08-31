### 反射学习
##### 获取对象方式
* 1 Object ——> getClass();
* 2 任何数据类型（包括基本数据类型）都有一个“静态”的class属性
* 3 通过Class类的静态方法：forName（String  className）(常用)
注意：在运行期间，一个类，只有一个Class对象产生



##### 获取属性
* 1.批量的
    > * 1).Field[] getFields():获取所有的"公有字段"
    > * 2).Field[] getDeclaredFields():获取所有字段，包括：私有、受保护、默认、公有；
* 2.单个的
    > * 1).public Field getField(String fieldName):获取某个"公有的"字段；
    > * 2).public Field getDeclaredField(String fieldName):获取某个字段(可以是私有的)
    > * 设置字段的值：
         Field --> public void set(Object obj,Object value):
                     					参数说明：
                      					1.obj:要设置的字段所在的对象；
                     					2.value:要为字段设置的值；
    > *  注意:私有属性 需要 f.setAccessible(true);
        
 

##### 获取方法
* 1.批量的：
    > * public Method[] getMethods():获取所有"公有方法"；（包含了父类的方法也包含Object类）
    > * public Method[] getDeclaredMethods():获取所有的成员方法，包括私有的(不包括继承的)
* 2.获取单个的：
    > * public Method getMethod(String name,Class<?>... parameterTypes):
        参数：
                 name : 方法名；
                 Class ... : 形参的Class类型对象
    > * public Method getDeclaredMethod(String name,Class<?>... parameterTypes)
        调用方法：
              		Method --> public Object invoke(Object obj,Object... args):
             					参数说明：
             					obj : 要调用方法的对象；
              					args:调用方式时所传递的实参；    