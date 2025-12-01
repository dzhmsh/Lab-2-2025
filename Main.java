import functions.*;

public class Main {
    public static void main(String[] args) {
        // Создается функция на интервале чтобы были целые точки
        TabulatedFunction fun = new TabulatedFunction(0, 20, 5);

        // пытаемся заполнить y
        for (int i = 0; i < fun.getPointsCount(); i++) {
            fun.setPointY(i, 2 * fun.getPointX(i));
        }

        System.out.println("Number of points: " + fun.getPointsCount());

        for (int i = 0; i < fun.getPointsCount(); i++) {
            fun.getFunctionValue(fun.getLeftDomainBorder() + i);
            System.out.print("F(" + fun.getPointX(i) + ") = ");
            System.out.println(fun.getPointY(i));
        }

        System.out.println("x belongs to [" + fun.getLeftDomainBorder() + ";" + fun.getRightDomainBorder() + "]");

        for (double i = 0.0; i <= 1.1; i += 0.1) {
            System.out.println("x = " + i + " y = " + fun.getFunctionValue(i));
        }

        printFunction(fun);

        FunctionPoint newPoint = new FunctionPoint(1, 2.005);

        fun.setPoint(1, newPoint);
        System.out.println("\nSet point [1] (1;2.005)");
        printFunction(fun);

        fun.deletePoint(1);
        System.out.println("\ndelete point [1] (1;2.005)");
        printFunction(fun); // все правильно

        fun.addPoint(newPoint);
        System.out.println("\nddPoint point [1] (1;2.005)");
        printFunction(fun);

        fun.setPointX(1, 5); // работает
        System.out.println("\nSetPointX point (1;5)");
        printFunction(fun);

        fun.setPointX(1, 600); // работает - no chanches
        System.out.println("\nSetPointX point (1;5)");
        printFunction(fun);

        fun.setPointX(1, 600); // работает - no chanches
        System.out.println("\nSetPointX point (1;5)");
        printFunction(fun);

        fun.setPointY(1, 5); // работает
        System.out.println("\nSetPointY point (1;5)");
        printFunction(fun);

    }

    static private void printFunction(TabulatedFunction func) {
        System.out.print("Function [ ");
        for (int i = 0; i < func.getPointsCount(); i++) {
            System.out.print("(" + func.getPointX(i) + ", " + func.getPointY(i) + ") ");
        }
        System.out.println("]");
    }
}