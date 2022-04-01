import java.text.DecimalFormat;

public class Main {


    public static void main(String[] args) throws Exception {
        double array[][]={{0.8894,0.0000,-0.2323,0.1634,0.2723,  4.2326},
                {-0.0545,	0.5808,	0.0000,	-0.1107,	0.0363,-4.1037},
                {0.0182,	-0.1634,	1.0527,	0.0200,	0.0635, -2.6935},
                {0.0545,	0.0000,	-0.1325,	1.0527,	0.0000, 1.6916},
                {0.0363,	-0.0545,	0.2632,	-0.0218,	0.7623, 3.1908}};

        int n=5;
        double f[]={4.2326, -4.1037,-2.6935,1.6916,3.1908};
        Matrix A=new Matrix(n);

        double X[]=new double[A.getRows()];
        Matrix R=new Matrix(n,1);
        Matrix F=new Matrix(n,1);
        F.setArray(f);
        //double R[]=new double[A.getRows()];
        double det=1;
        A.setMatrix(array);

        Matrix B=new Matrix(A);
        Matrix Aclone=new Matrix(n,n,A);
        Matrix E=new Matrix(n,n);
        Matrix C=new Matrix(n,2*n,A);
        Matrix Ainverse = new Matrix(n,n);
        System.out.println("Исходная матрица:");
        B.print();

        for (int i = 0; i < A.getRows(); i++) {

            A.Swap(A.get_row_of_max(i),A.get_column_of_max(i),i);
            det*=A.get_element(i,i);
            A.exclude(i);

        }


        //РЕШЕНИЕ

        X[A.getRows()-1]=A.get_element(A.getRows()-1,A.getColumns()-1);



        for (int i = X.length-2; i >=0 ; i--) {
            double sum=0;
            for (int j = A.getRows()-1; j > i; j--) {
                sum += A.get_element(i, j) * X[j];
            }
            X[i]=A.get_element(i,A.getColumns()-1)-sum;
        }
        for (int i = 0; i < X.length; i++) {
            for (int j = i; j < X.length; j++) {
                if(A.P[i]>A.P[j]){
                    double tmp=X[i];
                    X[i]=X[j];
                    X[j]=tmp;
                    int tmp1=A.P[i];
                    A.P[i]=A.P[j];
                    A.P[j]=tmp1;
                }

            }

        }
        Matrix XX=new Matrix(n,1);
        XX.setArray(X);
        //Невязка
        /*for (int i = 0; i < A.getRows(); i++) {

             R[i]=0;
            for (int j = 0; j < B.getRows(); j++) {
                R[i]+=B.get_element(i,j)*X[j];
            }
            R[i]-=B.get_element(i,B.getColumns()-1);


        }*/
        R=Aclone.multiplyRight(XX).minus(F);


        det*=Math.pow(-1,A.sgn);
        System.out.println("Определитель матрицы: "+det);

        System.out.print("Вектор решений: ");
        for (double item: X) {

            System.out.printf("%10.16f\t\t", item);
        }
        System.out.println();
        System.out.print("Вектор невязки: ");
        R.print();
        System.out.println("\n\n============================================================");


        System.out.println("Maтрица C");
        C.print();


//Обратная матрица
        for (int i = 0; i < C.getRows(); i++) {

            C.Swap(C.get_row_of_max(i),C.get_column_of_max(i),i);

            C.exclude(i);


        }


        for (int k = 0; k < C.getRows(); k++) {


        Ainverse.set_elem(Ainverse.getRows()-1, k,C.get_element(C.getRows()-1,C.getRows()+k));



        for (int i = C.getRows()-2; i >=0 ; i--) {
            double sum=0;
            for (int j = C.getRows()-1; j > i; j--) {
                sum += C.get_element(i, j) * Ainverse.get_element(j,k);
            }
            Ainverse.set_elem(i,k, C.get_element(i,C.getRows()+k)-sum);
        }

    }




            for (int i = 0; i < Ainverse.P.length; i++) {
                for (int j = i+1; j < Ainverse.P.length; j++) {
                    if(C.P[i] > C.P[j]) {
                        for (int k = 0; k <Ainverse.getRows() ; k++) {
                        double tmp = Ainverse.get_element(i, k);
                        Ainverse.set_elem(i, k, Ainverse.get_element(j, k));
                        Ainverse.set_elem(j, k, tmp);
                        int tmp1 = C.P[i];
                        C.P[i] = C.P[j];
                        C.P[j] = tmp1;
                    }

                }

            }
        }

        System.out.println("\n\n Обратная матрица: ");
        Ainverse.print();
        double A_norma=0;
        double A_inv_norma=0;
        for (int i = 0; i <B.getRows() ; i++) {
            double sumA=0;
            for (int j = 0; j < B.getRows(); j++) {
                sumA+=Math.abs(B.get_element(i,j));

            }
            if(sumA>A_norma)
                A_norma=sumA;

        }
        for (int i = 0; i <Ainverse.getRows() ; i++) {
            double sumAinv=0;
            for (int j = 0; j < Ainverse.getRows(); j++) {
                sumAinv+=Math.abs(Ainverse.get_element(i,j));

            }
            if(sumAinv>A_inv_norma)
                A_inv_norma=sumAinv;

        }
        double obus=A_inv_norma*A_norma;
        System.out.println("Число обусловленности: "+obus);
        System.out.println("********************************************");


        Matrix Nev=new Matrix(Aclone.multiplyRight(Ainverse).minus(E));

        double Nev_norma=0;
        for (int i = 0; i <Nev.getRows() ; i++) {
            double sumA=0;
            for (int j = 0; j < Nev.getRows(); j++) {
                sumA+=Math.abs(Nev.get_element(i,j));

            }
            if(sumA>Nev_norma)
                Nev_norma=sumA;

        }

        System.out.println("Норма матрицы невязок: "+Nev_norma);
        System.out.println("Матрица невязок: ");

        Nev.print();

    }
}
