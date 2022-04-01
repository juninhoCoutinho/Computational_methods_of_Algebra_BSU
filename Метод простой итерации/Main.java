public class Main {
    public static void main(String[] args) {
        double A[][]={{0.8894,0.0000,-0.2323,0.1634,0.2723 },
                {-0.0545,	0.5808,	0.0000,	-0.1107,	0.0363},
                {0.0182,	-0.1634,	1.0527,	0.0200,	0.0635 },
                {0.0545,	0.0000,	-0.1325,	1.0527,	0.0000 },
                {0.0363,	-0.0545,	0.2632,	-0.0218,	0.7623 }};
        double f[]={ 4.2326,-4.1037,-2.6935,1.6916,3.1908};
        //double A[][]={{5,0,3},{1,7,1},{2,1,6}};
        //double f[]={11,-4,13};
        //double A[][]={{2,3,4,5,6},{3,4,5,6,7},{2,3,5,6,7},{6,5,0,4,2},{1,-5,4,-1,6}};
        int n=5;
        double eps=0.00001;
        System.out.println("Точность:"+ eps);
        System.out.println("Матрица А:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%8f\t\t", A[i][j]);

            }
            System.out.println();
        }
        System.out.println("Столбец свободных членов f:");
        for (int j = 0; j < n; j++) {
            System.out.print(f[j]+"      ");

        }
        System.out.println();
 //Норма А
        double A_norma=0;
        for (int i = 0; i <n ; i++) {
            double sum=0;
            for (int j = 0; j < n; j++) {
                sum+=Math.abs(A[i][j]);

            }
            if(sum>A_norma)
                A_norma=sum;

        }
        System.out.println("Норма A: "+ A_norma);
        double tau=1/A_norma;

  //Матрица R
        double[][] R_inverse=new double[n][n];
        double[][] R= new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i==j) {
                    R[i][j] = 1 / tau;
                    R_inverse[i][j] = tau;
                }
                else {
                    R[i][j] = 0;
                    R_inverse[i][j] = 0;
                }

            }
        }

        System.out.println("Матрица R:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%8f\t\t", R[i][j]);

            }
            System.out.println();
        }
        System.out.println();
 //Матрица S
        double[][] S= new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                    S[i][j] = A[i][j]-R[i][j];
            }
        }

        System.out.println("Матрица S:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%8f\t\t", S[i][j]);

            }
            System.out.println();
        }



        System.out.println("Матрица R_inverse:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%8f\t\t", R_inverse[i][j]);
            }
            System.out.println();
        }


        double[][] B=new double[n][n];
        double b[]=new double[n];
        System.out.println("Вектор b:");
        for (int i = 0; i < n; i++) {
            b[i] = f[i]*tau;
            System.out.print(b[i]+"      ");
        }
        System.out.println();

        System.out.println("Матрица В:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(i==j)
                    B[i][j]=1-tau*A[i][j];
                else
                    B[i][j]=-tau*A[i][j];
                System.out.printf("%8f\t\t", B[i][j]);
            }
            System.out.println();

        }

        double B_norma=0;
        for (int i = 0; i <n ; i++) {
            double sum=0;
            for (int j = 0; j < n; j++) {
                sum+=Math.abs(B[i][j]);

            }
            if(sum>B_norma)
                B_norma=sum;

        }
        System.out.println("Норма B: "+ B_norma);

        System.out.println();
        System.out.println();


        double[] x_k= new double[n];
        double[] x_next= new double[n];
        for (int i = 0; i < n; i++) {
            x_k[i]=b[i];
        }

        double norm=0;
        double razn[]=new double[n];
        int count=0;

        do {
            for (int i = 0; i < n; i++) {
                double sum = 0;
                for (int j = 0; j < n; j++) {
                    sum += B[i][j] * x_k[j];
                }
                sum += b[i];
                x_next[i] = sum;
                System.out.print(x_next[i] + " ");
            }
            System.out.println();


            for (int j = 0; j < n; j++) {
                razn[j] = x_next[j] - x_k[j];
                x_k[j] = x_next[j];
            }
            norm = 0;
            for (int j = 0; j < n; j++) {
                if (Math.abs(razn[j]) > norm)
                    norm = Math.abs(razn[j]);
            }
            System.out.println("Norm: "+norm);
            count+=1;
        }while (norm>eps);
        System.out.println();
        System.out.println("Вектор решений Х: ");
        for (int j = 0; j < n; j++) {
            System.out.print(x_k[j]+" ");
        }
        System.out.println();
        System.out.println("Число итераций: "+count);
        double r[]=new double[n];

        System.out.println("Вектор невязок r:");
        for (int i = 0; i < n; i++) {
            r[i]=0;
            for (int j = 0; j < n; j++) {
                r[i]+=A[i][j]*x_k[j];
            }
            r[i]-=f[i];
            System.out.print(r[i]+"     ");
        }
    }
}
