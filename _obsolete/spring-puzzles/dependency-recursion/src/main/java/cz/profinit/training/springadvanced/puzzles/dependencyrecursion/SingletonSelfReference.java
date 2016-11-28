package cz.profinit.training.springadvanced.puzzles.dependencyrecursion;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SingletonSelfReference {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("singleton-self-reference.xml");

        MyBean r1 = applicationContext.getBean("r1", MyBean.class);

        System.out.println("r1 = " + r1);
        System.out.println("r1.getSelf() = " + r1.getSelf());
    }
}
