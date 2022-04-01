
import java.text.DecimalFormat;
import java.util.Random;

public class Matrix {
    private double A[][];
    private int rows;
    private int columns;
    public int P[];
    public int sgn=0;
    private Exception Exception;


    public double get_element(int i,int j){
        return A[i][j];
    }


    public void printP() {
        for (int i = 0; i < rows; i++) {
            System.out.print(P[i]+" ");
        }
        System.out.println("\n");
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }
    public void setArray(double[] array) throws Exception {

        try {
            if(this.getRows()!=1 && this.getColumns()!=1)
                throw Exception;

            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.columns; j++) {
                    this.A[i][j] = array[i];
                }
            }
        }catch (Exception ex){
            System.out.println("ERROR: Rows and columns aren't 1");
        }
    }
    public Matrix(Matrix b){

        this.rows=b.rows;
        this.columns=b.columns;
        this.P= new int[b.rows];
        for (int i = 0; i <b.rows ; i++) {
            P[i]=i;
        }

        this.A= new double[rows][columns];
        for (int i = 0; i <rows ; i++) {
            for (int j = 0; j < columns; j++) {
                A[i][j]=b.A[i][j];

            }

        }


    }
    public Matrix(int rows, int columns, Matrix M){
        this.rows=rows;

        this.columns = columns;
        this.A = new double[rows][columns];
        this.P= new int[rows];
        for (int i = 0; i <rows ; i++) {
            P[i]=i;
            for (int j = 0; j < columns; j++) {
                if(j<rows)
                    A[i][j]=M.A[i][j];
                else{
                    if(j==(i+rows))
                        A[i][j]=1;
                    else
                        A[i][j]=0;
                }




            }

        }

    }

    public Matrix(int rows, int columns) {
        this.rows = rows;

        this.columns = columns;
        this.A = new double[rows][columns];
        this.P = new int[rows];
        for (int i = 0; i < rows; i++) {
            P[i] = i;
            for (int j = 0; j < columns; j++) {
                if (i != j) {
                    A[i][j] = 0;
                } else {
                    A[i][j] = 1;
                }


            }

        }
    }



    public Matrix(int size) {
        this.rows =size;
        this.columns = size+1;
        this.A = new double[rows][columns];
        this.P= new int[size];
        for (int i = 0; i <rows ; i++) {
            P[i]=i;
            for (int j = 0; j < columns; j++) {
                if(i==j)
                A[i][j]=1;
                else
                    A[i][j]=0;


            }

        }



    }

    public void setRandom(int n) {
        Random rnd = new Random();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                this.A[i][j] = rnd.nextInt(n) - (n / 2);
            }
        }
    }
    public void setMatrix(double[][] array) {

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                this.A[i][j] = array[i][j];
            }
        }
    }

    public void print() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                if (j == rows) {
                    System.out.print("|     ");
                }
                DecimalFormat f=new DecimalFormat();
                System.out.printf("%10.16f\t\t", this.A[i][j]);

                System.out.print("             ");

            }
            System.out.println();

        }
        System.out.println("\n\n");
    }
    public void set_elem(int row,int column,double value){
        A[row][column]=value;
    }

    public int get_row_of_max(int n) {
        double max = 0;
        int maxI = 0;

        for (int i = n; i < rows; i++) {
            for (int j = i; j < rows; j++) {
                if (Math.abs(A[i][j]) > Math.abs(max)) {
                    max = A[i][j];
                    maxI = i;

                }
            }
        }
        return maxI;
    }
    public int get_column_of_max(int n) {
        double max = 0;
         int maxJ = 0;

        for (int i = n; i < rows; i++) {
            for (int j = i; j < rows; j++) {
                if (Math.abs(A[i][j]) > Math.abs(max)) {
                    max = A[i][j];

                    maxJ = j;
                }
            }
        }
        return maxJ;
    }
    public double[][] getA(){
        return A;

        }
    public void Swap(int maxI,int maxJ,int n){

            if(n!=maxI)
                sgn+=1;
            if(n!=maxJ)
                sgn+=1;
            for (int j = 0; j < columns; j++) {

                double tmp=A[n][j];
                A[n][j]=A[maxI][j];
                A[maxI][j]=tmp;
            }


            for (int k= 0; k < rows; k++) {


                double tmp=A[k][n];
                A[k][n]=A[k][maxJ];
                A[k][maxJ]=tmp;

                int tmp1=P[n];
                P[n]=P[maxJ];
                P[maxJ]=tmp1;
            }


    }
    public void exclude(int n){
        for (int j= columns-1; j >=n; j--) {
            A[n][j] /= A[n][n];
        }
        //A[n][n]=1;
        for (int k = n+1; k < rows; k++) {

            for (int l = columns-1; l >=n; l--) {
                A[k][l]-=A[n][l]*A[k][n];

            }
            //A[k][n]=0;
        }
    }

    public Matrix multiplyRight(Matrix right){
        try {
            if (this.getColumns() != right.getRows())
                throw Exception;
            Matrix res= new Matrix(this.getRows(),right.getColumns());
            for (int i = 0; i < this.getRows(); i++) {
                for (int j = 0; j < right.getColumns(); j++) {
                    double sum=0;
                    for (int k = 0; k <this.getColumns() ; k++) {
                        sum+=this.get_element(i,k)*right.get_element(k,j);
                    }
                    res.set_elem(i,j,sum);
                }

            }
            return res;

        }
        catch (Exception e){
            System.out.println("ERROR: Multiply impossible");
            return null;
        }

    }

    public Matrix minus(Matrix right){
        try {
            if (this.getColumns() != right.getColumns() || this.getRows() != right.getRows())
                throw Exception;
            Matrix res= new Matrix(this.getRows(),right.getColumns());
            for (int i = 0; i < this.getRows(); i++) {
                for (int j = 0; j < right.getColumns(); j++) {
                    res.set_elem(i,j,this.get_element(i,j)-right.get_element(i,j));
                }

            }
            return res;

        }
        catch (Exception e){
            System.out.println("ERROR: Minus impossible");
            return null;
        }

    }





}



