import parcs.*;
import java.math.BigInteger;
import java.util.Random;

public class Algorithm implements AM {
    
    public int jacobi(long k, long n){
    	long ka = new Long(Long.toString(k));
    	long ne = new Long(Long.toString(n));
        int jac = 1;
        if (ka < 0L) {
            ka = -ka;
            if (ne % 4 == 3)
                jac = -jac;
        }
        while (ka != 0) {
            long t = 0;
            while (ka % 2 == 0) {
                t++;
                ka /= 2;
            }
            if (t % 2 == 1) {
                if (ne % 8 == 3 || ne % 8 == 5)
                    jac = -jac;
            }
            if (ka % 4 == 3 && ne % 4 == 3)
                jac = -jac;
            long c = new Long(Long.toString(ka));
            long temp = new Long(Long.toString(ne));
            ka = new Long(Long.toString(temp % c));
            ne = new Long(Long.toString(c));
        }
        return jac;
    }

    @Override
    public void run(AMInfo info){
        long number, iters;
        number = info.parent.readLong();
        iters = info.parent.readLong();
        System.out.print("class Algorithm method run read data from parent server\nNumber = " + number + "\nIters = " +	iters + "\n\n");
        
        if (number < 0){
            number = -number;
        }
        
        if (number == 2 || number == 3){
            System.out.print("The number equals 2 or 3. The number is PROBABLY PRIME\n");
            info.parent.write(1);
        }
        else if (number % 2 == 0){
            System.out.print("The number is even. The number is COMPOSITE\n");
            info.parent.write(-1);
        }
        else if (number == 1 || number == 0){
            System.out.print("The number equals 1 or 0. The number is COMPOSITE\n");
            info.parent.write(-1);
        }
        else{
            Random rand = new Random();
            //System.out.print("Loop reached\n");
            boolean got = false;
            for (int i = 0; i < iters; i++){
            	long ran = 2 + ((long)(rand.nextDouble()*(number - 2)));
            	//System.out.print("Random number generated\n");
            	int ja = jacobi(ran, number);
            	if (ja == 0){
            		System.out.print("The number has another dividor. The number is COMPOSITE\n");
            		got = true;
            		info.parent.write(-1);
            		break;
            	}
            	//System.out.print("Jacobi calculated\n\n");
            	long t = new Long(Long.toString(number - 1));
            	t /= 2;
            	BigInteger tempor = new BigInteger(Long.toString(ran));
            	tempor = tempor.modPow(new BigInteger(Long.toString(t)), new BigInteger(Long.toString(number)));
            	//tempor = tempor.remainder(new BigInteger(Integer.toString(number)));
            	long temp = tempor.longValue();
            	if (temp == number - 1)
            		temp = -1;
            	//System.out.print("Pow calculated\n");
            	if (ja != temp) {
            		System.out.print("Jacobi symbol and remainder aren't equal. The number is COMPOSITE\n");
            		got = true;
            		info.parent.write(-1);
            		break;
            	}
            	//System.out.print("Iteration " + i + " finished\n");
            }
            if (!got){
                System.out.print("The number is PROBABLY PRIME\n");
                info.parent.write(1);
            }
        }
    }
}
