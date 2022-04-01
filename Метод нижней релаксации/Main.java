public class Main {
    public  static double errorEstimation(Matrix x2, Matrix x1) {
        double cubeNorm = 0.;

        for (int i = 0; i < 5; ++i) {
            double res = Math.abs(x2.get_element(i,0) - x1.get_element(i,0));
            if (res > cubeNorm) {
                cubeNorm = res;
            }
        }
        return cubeNorm;
    }

    public static void main(String[] args) throws Exception {
        double array[][]={{0.8894,0.0000,-0.2323,0.1634,0.2723 },
                {-0.0545,	0.5808,	0.0000,	-0.1107,	0.0363},
                {0.0182,	-0.1634,	1.0527,	0.0200,	0.0635 },
                {0.0545,	0.0000,	-0.1325,	1.0527,	0.0000 },
                {0.0363,	-0.0545,	0.2632,	-0.0218,	0.7623 }};
        double right_part[]={ 4.2326,-4.1037,-2.6935,1.6916,3.1908};


        int n=5;
        final double EPS=0.00001;
        final double w=0.95;
        Matrix f=new Matrix(n,1);
        f.setArray(right_part);

        Matrix A=new Matrix(n,n);
        A.setMatrix(array);
        System.out.println("Matrix A");
        A.print();

        f=f.multiplyLeft(A.transposition());

        A=A.multiplyLeft(A.transposition());


        System.out.println("Точность:"+ EPS);
        System.out.println("Матрица АtA:");
        A.print();
        System.out.println();
        System.out.println("Правая часть:");
        f.print();

        Matrix x0=new Matrix(f);
        Matrix xk=new Matrix(n,1);
        for (int i = 0; i < n; i++) {
            xk.set_elem(i,0, 0);
        }



        int count=0;

        while (errorEstimation(xk, x0) > EPS) {
            if (count != 0) {
                for (int i = 0; i < n; i++) {
                    x0.set_elem(i,0, xk.get_element(i,0));
                }


            }
            ++count;

            for (int i = 0; i < n; ++i) {
                xk.set_elem(i,0,0);
                xk.set_elem(i,0,xk.get_element(i,0)+ (1 - w) * x0.get_element(i,0) + w * f.get_element(i,0) /A.get_element(i,i));

                double sum = 0.;
                for (int j = 0; j < i; ++j) {
                    sum += A.get_element(i,j) * xk.get_element(j,0);
                }
                for (int j = i + 1; j < n; ++j) {
                    sum += A.get_element(i,j) * x0.get_element(j,0);
                }

                xk.set_elem(i,0,xk.get_element(i,0)- w * sum / A.get_element(i,i));
            }
            System.out.println();
            xk.print();
        }
        System.out.println("Число итераций: "+count);
        Matrix R=new Matrix(n,1);
        R=A.multiplyRight(xk).minus(f);
        System.out.println("Невязка:");
        R.print();




    }
}
