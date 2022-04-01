public class Main {
    public static void main(String[] args) {
        double A[][] = {{0.8894, 0.0000, -0.2323, 0.1634, 0.2723},
                {-0.0545, 0.5808, 0.0000, -0.1107, 0.0363},
                {0.0182, -0.1634, 1.0527, 0.0200, 0.0635},
                {0.0545, 0.0000, -0.1325, 1.0527, 0.0000},
                {0.0363, -0.0545, 0.2632, -0.0218, 0.7623}};
        double f[] = {4.2326, -4.1037, -2.6935, 1.6916, 3.1908};

        int n = 5;
        double[] X = new double[n];
        double[] R = new double[n];

        System.out.println("Матрица А:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%8f\t\t", A[i][j]);

            }
            System.out.println();
        }
        System.out.println("Столбец свободных членов f:");
        for (int j = 0; j < n; j++) {
            System.out.print(f[j] + "      ");

        }
        System.out.println();


        double[] cos_fi1k = new double[n];
        double[] sin_fi1k = new double[n];
        for (int l = 0; l < n - 1; l++) {
            for (int j = l + 1; j < n; j++) {
                double f1, znam = A[l][l] * A[l][l];
                for (int k = j; k < n; k++) {
                    znam += A[k][l] * A[k][l];
                    sin_fi1k[k] = -A[k][l] / Math.sqrt(znam);
                    cos_fi1k[k] = Math.sqrt(1 - sin_fi1k[k] * sin_fi1k[k]);
                }


                double[] first_line = new double[n];
                for (int k = l; k < n; k++) {
                    first_line[k] = cos_fi1k[j] * A[l][k] - sin_fi1k[j] * A[j][k];
                    A[j][k] = sin_fi1k[j] * A[l][k] + cos_fi1k[j] * A[j][k];
                    A[l][k] = first_line[k];

                }
                f1 = cos_fi1k[j] * f[l] - sin_fi1k[j] * f[j];
                f[j] = sin_fi1k[j] * f[l] + cos_fi1k[j] * f[j];
                f[l] = f1;

                System.out.println("cos,sin: " + cos_fi1k[j] + " " + sin_fi1k[j]);

                System.out.println("Матрица А:");
                for (int i = 0; i < n; i++) {
                    for (int k = 0; k < n; k++) {
                        System.out.printf("%8f\t\t", A[i][k]);

                    }
                    System.out.println();
                }
                System.out.println("Столбец свободных членов f:");
                for (int k = 0; k < n; k++) {
                    System.out.print(f[k] + "      ");

                }
                System.out.println();
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = n - 1; j > i; j--) {
                sum += A[i][j] * X[j];
            }
            X[i] = (f[i] - sum) / A[i][i];
        }
        System.out.println("Вектор решений Х:");
        for (int k = 0; k < n; k++) {
            System.out.print(X[k] + "      ");


        }
        System.out.println();
        System.out.println("Вектор невязок r:");
        for (int i = 0; i < n; i++) {
            R[i] = 0;
            for (int j = 0; j < n; j++) {
                R[i] += A[i][j] * X[j];
            }
            R[i] -= f[i];
            System.out.print(R[i] + "     ");
        }
    }
}
