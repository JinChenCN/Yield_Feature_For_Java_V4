public class Test1 {

    int a;

    public void setA() {
        this.a = 0;
        SubTest t = new SubTest();
    }


    class SuperCla {

        int c = 0;
    }

    class SubTest extends SuperCla {

        int b = 0;

        void reset() {
            this.c = 1;
        }

    }
}
