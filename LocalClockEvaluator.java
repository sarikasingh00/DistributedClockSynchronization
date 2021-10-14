public class LocalClockEvaluator{
    long cristian_error[], berkeley_error[], ntp_error[];
    public LocalClockEvaluator(){
        cristian_error = new long[3000];
        berkeley_error = new long[3000];
        ntp_error = new long[3000];
    }

    public void print_error(){
        for(int i = 0; i<3000;i++){
            System.out.print(this.cristian_error[i] + ",");
        }
        // for(int i = 0; i<3000;i++){
        //     System.out.println(this.berkeley_error[i] + ",");
        // }
        // for(int i = 0; i<3000;i++){
        //     System.out.println(this.ntp_error[i] + ",");
        // }
    }
}