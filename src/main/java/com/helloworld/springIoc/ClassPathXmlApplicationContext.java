package com.helloworld.springIoc;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author daxia li
 * @time 2019/8/3
 */
public class ClassPathXmlApplicationContext {

    private String xmlPath;

    public ClassPathXmlApplicationContext(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public Object getBean(String beanId) throws DocumentException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException {

        Object object = null;

        //1.读取xml配置文件
        SAXReader saxReader = new SAXReader();
        //this.getClass().getClassLoader().getResourceAsStream(xmlPath) 获取当前项目路劲
        Document document = saxReader.read(xmlPath);
//        Document document = saxReader.read(this.getClass().getClassLoader().getResourceAsStream(xmlPath));
        //获取根节点对象
        Element element = document.getRootElement();
        List<Element> elements = element.elements();
        //2.读取每个bean配置文件，获取class地址
        for (Element element1 : elements){
            String beanid = element1.attributeValue("id");
            if (!beanId.equals(beanid)){
                 continue;
            }
            //3.拿到class地址 进行反射实例化对象，使用反射api得到属性赋值
            String beanClassPath = element1.attributeValue("class");
            Class<?> aClass = Class.forName(beanClassPath);
             object = aClass.newInstance();
            List<Element> elementList = element1.elements();
            for (Element element2 : elementList){
                String name = element2.attributeValue("name");
                String value = element2.attributeValue("value");
                //使用反射api 为私有属性赋值
                Field field = aClass.getDeclaredField(name);
                field.setAccessible(true);
                field.set(object,value);

            }
        }



        return object;

    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, DocumentException, NoSuchFieldException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("D:\\idea\\java\\helloworld\\src\\resource\\user.xml");
        Object object = classPathXmlApplicationContext.getBean("user1");
        UserEntity userEntity = (UserEntity) object;
        System.out.println(userEntity.getUserName());

    }
}
