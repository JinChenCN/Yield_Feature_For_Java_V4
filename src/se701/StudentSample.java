package se701;

public class StudentSample {

    public static void main(String[] args) {
        String b = "test";
        sampleMethod(b);
        sampleMethodYield1(b);
        sampleMethodYield2(b);
    }


    public static void sampleMethod(String a) {
        System.out.println("Execution started");
        for (int i = 0; i < 20; i++) {
            System.out.println(a);
        }

        System.out.println("Execution ended");
    }
    public static void sampleMethodYield1(String a) {
        System.out.println("Execution started");
        System.out.println("second call.");

        System.out.println("Execution ended");
    }
    public static void sampleMethodYield2(String a) {
        System.out.println("Execution started");
        System.out.println("third call.");

        System.out.println("Execution ended");
    }

}
