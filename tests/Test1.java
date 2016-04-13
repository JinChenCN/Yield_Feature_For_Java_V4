public class Test1 {

    int a;

    public void setA() {
        this.a = 0;
        SubTest t = new SubTest();
        t.b = 1;
    }


    class SubTest {

        int b = 0;

        void reset() {
        }

    }
}
