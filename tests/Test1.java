public class Test1 {

    int a = 0;

    int b = 0;

    String s = "";

    public void setA() {
        SubTest t = new SubTest();
        String d = t.b;
    }


    class SuperCla {

        void test() {
            int d = b;
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
