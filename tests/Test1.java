public class StudentSample {

    public static void main(String[] args) {
        String b = "test";
        sampleMethod(b);
        sampleMethodYield1(b);
    }


    public static void sampleMethod(String a) {
        System.out.println("Execution started");
        for (int j = 0; j < 20; j++) {
            System.out.println(a);
        }

        System.out.println("Execution ended");
    }
    public static void sampleMethodYield1(String a) {
        System.out.println("Execution started");
        System.out.println("second call.");

        System.out.println("Execution ended");
    }

}
