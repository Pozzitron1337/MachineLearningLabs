import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


public class Perceptron {

    private double[] enters;
    private double outer;
    private double[] weigths;

    private double train[][];
    private double test[][];

    public Perceptron(String pathToData){
        this.enters = new double[2];
        this.weigths=new double[enters.length];
        for(int i=0;i<enters.length;i++){
            weigths[i]=Math.random()*0.2+0.1;
        }
        this.train=new double[75][3];
        this.test=new double[25][3];
        try {
            BufferedReader csvReader=new BufferedReader(new FileReader(pathToData));
            String[] data;
            String row="";
            int i=0;
            while ((row=csvReader.readLine())!=null&&i<75){
                data=row.split(";");
                train[i][0] = Double.parseDouble(data[0]);
                train[i][1] = Double.parseDouble(data[1]);
                train[i][2] = Double.parseDouble(data[2]);
                i++;
            }
            i=0;
            while ((row=csvReader.readLine())!=null&&i<25){
                data=row.split(";");
                test[i][0] = Double.parseDouble(data[0]);
                test[i][1] = Double.parseDouble(data[1]);
                test[i][2] = Double.parseDouble(data[2]);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Perceptron(String pathToData,double[] weigths){
        this.enters = new double[2];
        this.weigths=weigths;
        this.train=new double[75][3];
        this.test=new double[25][3];
        try {
            BufferedReader csvReader=new BufferedReader(new FileReader(pathToData));
            String[] data;
            String row="";
            int i=0;
            while ((row=csvReader.readLine())!=null&&i<75){
                data=row.split(";");
                train[i][0] = Double.parseDouble(data[0]);
                train[i][1] = Double.parseDouble(data[1]);
                train[i][2] = Double.parseDouble(data[2]);
                i++;
            }
            i=0;
            while ((row=csvReader.readLine())!=null&&i<25){
                data=row.split(";");
                test[i][0] = Double.parseDouble(data[0]);
                test[i][1] = Double.parseDouble(data[1]);
                test[i][2] = Double.parseDouble(data[2]);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void countOuter(){
        outer=0;
        for (int i=0;i<enters.length;i++){
            outer+=enters[i]*weigths[i];
        }
        if(outer>0.5){
            outer=1.0;
        }
        else {
            outer=0.0;
        }
    }

    public void learn(int iterations){
        int t=iterations;
        double globalError;
        double error;
        do {
            globalError=0;
            for(int p=0;p<train.length;p++){
                enters= Arrays.copyOf(train[p],train[p].length-1);
                countOuter();
                error=train[p][2]-outer;
                globalError+=Math.abs(error);
                for (int i = 0; i < enters.length; i++) {
                    weigths[i]+=0.1*error*enters[i];
                }
            }
            t--;
        }while (t>0);
    }

    public void test(){
        int trues=0;
        boolean equal;
        int len=25;
        double values[]=new double[len];
        for (int i = 0; i <test.length ; i++) {
            enters=Arrays.copyOf(test[i],test[i].length-1);
            countOuter();
            values[i]=outer;
            equal=(outer==test[i][2]);
            if(equal){
                trues++;
            }
            System.out.println("Test inputs: "+enters[0]+","+enters[1]);
            System.out.println("    Outer:"+outer+","+"Test value:"+test[i][2]+"----"+equal);
        }
        System.out.println("Indentified:"+trues+"/25");

        double ss_res=0.0;
        for (int i = 0; i < len; i++) {
            ss_res+=(test[i][2]-values[i])*(test[i][2]-values[i]);
        }

        double avg=0;
        for (int i = 0; i < len; i++) {
            avg+=values[i];
        }
        avg/=(double)len;
        double ss_tot=0.0;
        for (int i = 0; i < len; i++) {
            ss_tot+=(values[i]-avg)*(values[i]-avg);
        }
        double r2=1-(ss_res/ss_tot);
        System.out.println("R2: "+r2);
        System.out.print("x=[");
        for (int i=0;i<len-1;i++){
            System.out.print((i+1)+",");
        }
        System.out.println("25]");
        System.out.print("expected_y=[");
        for (int i=0;i<len-1;i++){
            System.out.print(test[i][2]+",");
        }
        System.out.println(test[24][2]+"]");
        System.out.print("real_y=[");
        for (int i=0;i<len-1;i++){
            System.out.print(values[i]+",");
        }
        System.out.println(values[24]+"]");
    }

    public void printWeight(){
        System.out.println(weigths[0]+" "+weigths[1]);
    }
}
