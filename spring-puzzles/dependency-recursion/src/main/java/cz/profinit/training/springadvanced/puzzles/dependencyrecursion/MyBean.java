package cz.profinit.training.springadvanced.puzzles.dependencyrecursion;

public class MyBean {

    private MyBean self;

    public MyBean getSelf() {
        return self;
    }

    public void setSelf(MyBean self) {
        this.self = self;
    }
}
