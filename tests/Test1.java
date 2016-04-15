public class StudentSample {

    public static void main(String[] args) {
        String b = "test";
        myMethod(b);
        myMethodYield1(b);
    }


    public static void myMethod(String a) {
        System.out.println("Execution started");
        for (int j = 0; j < 20; j++) {
            System.out.println(a);
        }

        System.out.println("Execution ended");
    }
    public static void myMethodYield1(String a) {
        System.out.println("Execution started");
        System.out.println("second call.");

        System.out.println("Execution ended");
    }

}
