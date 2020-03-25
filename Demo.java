public class Demo {
	public static void main (String[] args) {
        System.out.print("Demo: started, use ps to find pid and use kill to stop it");
		for (int i=0; ; i++) {
			System.out.println("Demo: "+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ee) {
            }
		}
	}
}