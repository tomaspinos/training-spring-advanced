package cz.profinit.training.springadvanced.puzzles.dependencyrecursion;

/**
 * @author tpinos@csob.cz Tomas Pinos (JD71691)
 */
public class MyBean {

    private MyBean self;

    public MyBean getSelf() {
        return self;
    }

    public void setSelf(MyBean self) {
        this.self = self;
    }
}
