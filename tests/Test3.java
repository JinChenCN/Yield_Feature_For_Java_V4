package se701;

public class StudentSample {

    public static void main(String[] args) {
        sampleMethod();
        sampleMethodYield1();
        sampleMethodYield2();
    }


    public static void sampleMethod() {
        System.out.println("Execution started");
        for (int j = 0; j < 20; j++) {
            System.out.println(j);
        }

        System.out.println("Execution ended");
    }
    public static void sampleMethodYield1() {
        System.out.println("Execution started");
        System.out.println("second call.");

        System.out.println("Execution ended");
    }
    public static void sampleMethodYield2() {
        System.out.println("Execution started");
        System.out.println("third call.");

        System.out.println("Execution ended");
    }

}
