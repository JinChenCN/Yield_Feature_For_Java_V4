public class Test1 {

    int a = 0;

    public void setA() {
        this.a = 3;
        SubTest t = new SubTest();
    }


    class SuperCla {

        void test() {
        }


        int c = 0;
    }

    class SubTest extends SuperCla {

        int b = 0;

        void reset() {
            this.c = 1;
        }

    }
}
