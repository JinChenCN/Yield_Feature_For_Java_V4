package se701;

public class StudentSample {

    public static void main(String[] args) {
        someMethod();
        someMethodYield1();
        someMethodYield2();
        someMethodYield3();
        someMethodYield4();
    }


    public static void someMethod() {
        System.out.println("Execution started");
        System.out.println("test3");

        System.out.println("Execution ended");
    }
    public static void someMethodYield1() {
        System.out.println("Execution started");
        System.out.println("second call.");

        System.out.println("Execution ended");
    }
    public static void someMethodYield2() {
        System.out.println("Execution started");
        System.out.println("third call.");

        System.out.println("Execution ended");
    }
    public static void someMethodYield3() {
        System.out.println("Execution started");
        System.out.println("fourth call.");

        System.out.println("Execution ended");
    }
    public static void someMethodYield4() {
        System.out.println("Execution started");
        System.out.println("fifth call.");

        System.out.println("Execution ended");
    }

}
