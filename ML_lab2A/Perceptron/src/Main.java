public class Main {

    public static void main(String[] args) {
	    Perceptron p=new Perceptron("data16.csv");
	    p.learn(1000);
	    p.test();
	    p.printWeight();
       /* Perceptron p2=new Perceptron("data16.csv",new double[]{1.1715114126613024,-0.022734245966786845});
        p2.test();*/
    }
}
