public class Main {
//Функция проверки на выполнение условия окончания метода Якоби
    public  static  double getEps(Matrix M) {
        double eps = 0.;
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                if (i == j) {
                    continue;
                }
                eps += Math.pow(M.get_element(i,j), 2);
            }
        }

        return Math.sqrt(eps);
    }
    public static void main(String[] args) {
        double array[][] = {{0.8894, 0.0000, -0.2323, 0.1634, 0.2723},
                {-0.0545, 0.5808, 0.0000, -0.1107, 0.0363},
                {0.0182, -0.1634, 1.0527, 0.0200, 0.0635},
                {0.0545, 0.0000, -0.1325, 1.0527, 0.0000},
                {0.0363, -0.0545, 0.2632, -0.0218, 0.7623}};
        int n=5;
        double[] q= new double[n];
        Matrix A=new Matrix(n,n);
        A.setMatrix(array);



        Matrix At=A.transposition();




        Matrix C=new Matrix(n,n+1);

        //double[][] C_gauss =new  double[n][n+1];

        for (int i = 1; i < n; i++) {
            C.set_elem(i,n-1,0);
        }
        C.set_elem(0,n-1,1);

        Matrix B=At.multiplyRight(A);

        System.out.println("Матрица В=Аt*A:");
        B.print();



//Метод Крылова
        System.out.println("Метод Крылова:");
        Matrix C0=new Matrix(n,1);
        System.out.println("vector c0");
        C0.print();


        for (int k = 1; k < n+1; k++) {
            C0=B.multiplyRight(C0);

            System.out.println("вектор с("+(k)+"): ");
            C0.print();
            if(k==n){
                for (int i = 0; i < n; i++) {
                    C.set_elem(i,C.getColumns()-1,C0.get_element(i,0));
                }

            }
            else {
                for (int i = 0; i < n; i++) {
                    C.set_elem(i, 4 - k, C0.get_element(i, 0));
                }
            }
            System.out.println("Matrix C");
            C.print();
        }

        for (int i = 0; i < C.getRows(); i++) {

            C.Swap(C.get_row_of_max(i),C.get_column_of_max(i),i);
            C.exclude(i);

        }
        double X[]=new double[C.getRows()];

        X[C.getRows()-1]=C.get_element(C.getRows()-1,C.getColumns()-1);



        for (int i = X.length-2; i >=0 ; i--) {
            double sum=0;
            for (int j = C.getRows()-1; j > i; j--) {
                sum += C.get_element(i, j) * X[j];
            }
            X[i]=C.get_element(i,C.getColumns()-1)-sum;
        }
        for (int i = 0; i < X.length; i++) {
            for (int j = i; j < X.length; j++) {
                if(C.P[i]>C.P[j]){
                    double tmp=X[i];
                    X[i]=X[j];
                    X[j]=tmp;
                    int tmp1=C.P[i];
                    C.P[i]=C.P[j];
                    C.P[j]=tmp1;
                }

            }

        }
        System.out.println("Коэффициенты p1-p5 методом Крылова:(vector q): ");
        for (int i = 0; i < n; i++) {
            System.out.printf("%10.10f\t\t", X[i]);
        }
        System.out.println();
        System.out.println("Собственные значения матрицы В, вычисленные в Wolphram: ");
        double[] roots={0.291163,0.391154,0.871323,1.18205,1.48751};
        for (int i = 0; i < n; i++) {
            System.out.printf("%10.10f\t\t", roots[i]);
        }
        System.out.println();
        System.out.println("невязка ");
        double[] fi=new double[n];
        for (int i = 0; i < n; i++) {
            fi[i]=-Math.pow(roots[i],5)+X[0]*Math.pow(roots[i],4)+X[1]*Math.pow(roots[i],3)+X[2]*Math.pow(roots[i],2)
                    +X[3]*roots[i]+X[4];
            System.out.printf("%10.10f\t\t", fi[i]);

        }
        System.out.println();




//Метод Данилевского
        System.out.println("Метод Данилевского:");
        Matrix F = new Matrix(B);


        Matrix S = new Matrix(n,n);

        for (int i = 3; i >= 0; --i) {
            Matrix M = new Matrix(n,n);
            Matrix M1 = new Matrix(n,n);

            for (int j = 0; j < 5; ++j) {
                M1.set_elem(i,j,F.get_element(i+1,j));
                if (i != j) {
                    M.set_elem(i,j,-F.get_element(i+1,j)/F.get_element(i+1,i));
                }
            }
            M.set_elem(i,i,1/F.get_element(i+1,i));
            Matrix FM=new Matrix(F.multiplyRight(M));

            F= M1.multiplyRight(FM);
            S = S.multiplyRight(M);
        }
        System.out.println("Matrix S:");
        S.print();
        System.out.println("Matrix F:");
        F.print();
        System.out.println("Коэффициенты p1-p5 методом Данилевского:(первая строка матрицы F): ");
        for (int i = 0; i < n; i++) {
            System.out.printf("%10.10f\t\t", F.get_element(0,i));
        }
        System.out.println();
        Matrix Y=new Matrix(n,n);
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n; j++) {
                Y.set_elem(j,i,Math.pow(roots[i],n-1-j));
            }
        }
        System.out.println("Matrix Y");
        Y.print();
        Matrix XX=new Matrix( S.multiplyRight(Y));
        System.out.println("Matrix X(cтолбцы- собственные вектора)");
        XX.print();

        Matrix R=new Matrix(n,n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <n ; j++) {
                R.set_elem(j,i,B.multiplyRight(XX).minus(XX.multiply_num(roots[i])).get_element(j,i));
            }

        }
        System.out.println();
        System.out.println("Matrix R");
        R.print();
        System.out.println();


//итерационный метод вращений (Якоби)
        System.out.println("Итерационный метод Якоби");
        final double EPS=0.00001;

        int counter = 0;

        Matrix B1=new Matrix(B);


        Matrix Q = new Matrix(n,n);
        Matrix Rjakobi= new Matrix(n,n);

        while (getEps(B1) > EPS) {
            counter++;
            double max = 0;
            int maxI = 0;
            int maxJ = 0;

            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (i == j) {
                        continue;
                    }
                    else {
                        if (Math.abs(B1.get_element(i,j)) > Math.abs(max)) {
                            max = B1.get_element(i,j);
                            maxI = i;
                            maxJ = j;
                        }
                    }
                }
            }

            double tg = 2 * max / (B1.get_element(maxI,maxI) - B1.get_element(maxJ,maxJ));
            double cos = Math.sqrt((1 + 1 / Math.sqrt(1 + Math.pow(tg, 2))) / 2);
            double sin = Math.sqrt(1 - Math.pow(cos, 2));

            Matrix T =new Matrix(n,n);
            T.set_elem(maxI,maxI,cos);
            T.set_elem(maxJ,maxJ,cos);
            T.set_elem(maxI,maxJ,-sin);
            T.set_elem(maxJ,maxI,sin);


            Q = Q.multiplyRight(T);
            Matrix Ttr=T.transposition();
            Ttr=Ttr.multiplyRight(B1);
            B1=Ttr.multiplyRight(T);



        }
        System.out.println("число итераций: " + counter);
        System.out.println("Matrix B1");
        B1.print();
        System.out.println("Matrix Q");
        Q.print();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n-1; j++) {

                Q.set_elem(j,i,Q.get_element(j,i)/Q.get_element(n-1,i));

            }
            Q.set_elem(n-1,i,1);

        }
        System.out.println("Matrix Q");
        Q.print();
        System.out.println();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <n ; j++) {
                Rjakobi.set_elem(j,i,B.multiplyRight(Q).minus(Q.multiply_num(B1.get_element(i,i))).get_element(j,i));
            }

        }

        System.out.println("Matrix R_jakobi");
        Rjakobi.print();
        System.out.println();
//Метод обратных итераций
        final double eps=0.00001;
        System.out.println("Метод обратных итераций");
        Matrix Binv=B.inverse();
        System.out.println("Обратная к В матрица");

        Binv.print();
        Matrix y0=new Matrix(n,1);
        Matrix y1=new Matrix(n,1);
        for (int i = 0; i <n ; i++) {
            y0.set_elem(i,0,1);
        }
        double lambda0,lambda1;

        y0.print();
        y1=Binv.multiplyRight(y0);
        lambda0=y1.get_element(0,0)/y0.get_element(0,0);

        for (int i = 0; i <n; i++) {
            y0.set_elem(i,0,y1.get_element(i,0));
        }
        double epps=1000;
        int count=0;
        do{

            y1 = Binv.multiplyRight(y0);
            lambda1 = y1.get_element(0, 0) / y0.get_element(0, 0);
            epps = Math.abs(lambda0 - lambda1);
            for (int i = 0; i < n; i++) {
                y0.set_elem(i, 0, y1.get_element(i, 0));
            }
            lambda0 = lambda1;
            count++;
        }while(Math.abs(epps)>eps);
        System.out.println("число итераций "+count);
            System.out.println("Наименьшее по модулю собственное значение матрицы В: "+1/lambda1);
        System.out.println("Собственный вектор, ему соответствующий: ");
        y1.print();
        System.out.println("собственный вектор, разделеннный на последнюю координату");
        for (int i = 0; i <n-1 ; i++) {
            y1.set_elem(i,0,y1.get_element(i,0)/y1.get_element(n-1,0));

        }
        y1.set_elem(n-1,0,1);
        y1.print();
    }
}
