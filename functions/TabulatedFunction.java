package functions;

public class TabulatedFunction {

    private static final double EPS = 1e-9;
    private FunctionPoint arrayOfPoints[];
    private int pointCount;

    // создаёт объект табулированной функции
    // по заданным левой и правой границе области определения
    // и количеству точек для табулирования

    public TabulatedFunction(double leftX, double rightX, int pointsCount) {
        this.arrayOfPoints = new FunctionPoint[pointsCount];
        this.pointCount = pointsCount;
        double step = (rightX - leftX) / (pointsCount - 1);

        for (int i = 0; i < pointsCount; i++) {
            arrayOfPoints[i] = new FunctionPoint(leftX + step * i, 0);
        }
    }

    // вместо количества точек получает значения функции в виде массива
    public TabulatedFunction(double leftX, double rightX, double[] values) {
        int len = values.length;
        this.arrayOfPoints = new FunctionPoint[len];
        this.pointCount = values.length;
        double step = (rightX - leftX) / (len - 1);

        for (int i = 0; i < len; i++) {
            arrayOfPoints[i] = new FunctionPoint(leftX + step * i, values[i]);
        }
    }

    // возвращает значение левой границы
    public double getLeftDomainBorder() {
        return arrayOfPoints[0].getX();
    }

    // должен возвращать значение правой границы
    public double getRightDomainBorder() {
        return arrayOfPoints[pointCount - 1].getX();
    }

    // линейная интерполяция - вспомогательная функция
    private double linearInterpolation(double x, double x0, double y0, double x1, double y1) {
        double k = (y1 - y0) / (x1 - x0);
        double b = y0 - k * x0;
        double y = k * x + b;

        return y;
    }

    // должен возвращать значение функции в точке x
    public double getFunctionValue(double x) {
        if ((x > getRightDomainBorder() + EPS) || (x < getLeftDomainBorder() - EPS))
            return Double.NaN;

        if (Math.abs(x - arrayOfPoints[0].getX()) < EPS)
            return arrayOfPoints[0].getY();

        for (int i = 1; i < pointCount; i++) {
            if (Math.abs(arrayOfPoints[i].getX() - x) < EPS)
                return arrayOfPoints[i].getY();

            if (arrayOfPoints[i].getX() > x) {
                return linearInterpolation(x,
                        arrayOfPoints[i - 1].getX(),
                        arrayOfPoints[i - 1].getY(),
                        arrayOfPoints[i].getX(),
                        arrayOfPoints[i].getY());
            }
        }

        return Double.NaN;
    }

    // должен возвращать количество точек
    public int getPointsCount() {
        return pointCount;
    }

    // должен возвращать копию точки, соответствующей переданному индексу
    public FunctionPoint getPoint(int index) {
        return new FunctionPoint(arrayOfPoints[index]);
    }

    // должен заменять указанную на копию переданной точки
    public void setPoint(int index, FunctionPoint point) {
        if (index < 0 || index > getPointsCount() - 1)
            return;
        if (index > 0 && point.getX() <= arrayOfPoints[index - 1].getX() + EPS)
            return;
        if (index < getPointsCount() - 1 && point.getX() >= arrayOfPoints[index + 1].getX() - EPS)
            return;

        FunctionPoint NewPoint = new FunctionPoint(point);
        arrayOfPoints[index] = NewPoint;
    }

    // должен возвращать значение абсциссы точки с указанным номером.
    public double getPointX(int index) {
        return arrayOfPoints[index].getX();
    }

    // должен возвращать значение ординаты точки с указанным номером
    public double getPointY(int index) {
        return arrayOfPoints[index].getY();
    }

    // должен изменять значение абсциссы точки с указанным номером.
    // ! не должен изменять точку, если новое значение попадает в другой интервал
    public void setPointX(int index, double x) {
        if (index < 0 || index >= pointCount)
            return;

        if (pointCount == 1) {
            arrayOfPoints[index].setX(x);
            return;
        }

        if (index == 0 && arrayOfPoints[1].getX() > x + EPS) {
            arrayOfPoints[index].setX(x);
        } else if (index == pointCount - 1 && arrayOfPoints[pointCount - 2].getX() < x - EPS) {
            arrayOfPoints[index].setX(x);
        } else if (index > 0 && index < pointCount - 1 &&
                arrayOfPoints[index - 1].getX() < x - EPS && arrayOfPoints[index + 1].getX() > x + EPS) {
            arrayOfPoints[index].setX(x);
        }
    }

    // должен изменять значение ординаты точки с указанным номером.
    public void setPointY(int index, double y) {
        arrayOfPoints[index].setY(y);
    }

    // должен удалять заданную точку табулированной функции.
    public void deletePoint(int index) {
        if (index < 0 || index >= pointCount) {
            return;
        }

        if (pointCount - index - 1 > 0) {
            System.arraycopy(arrayOfPoints, index + 1, arrayOfPoints, index, pointCount - index - 1);
        }

        arrayOfPoints[pointCount - 1] = null;
        pointCount--;
    }

    // добавляем точку
    public void addPoint(FunctionPoint point) {
        FunctionPoint newPoint = new FunctionPoint(point);

        int insertIndex = 0;
        while (insertIndex < pointCount && arrayOfPoints[insertIndex].getX() < newPoint.getX() - EPS) {
            insertIndex++;
        }

        if (insertIndex < pointCount && Math.abs(arrayOfPoints[insertIndex].getX() - newPoint.getX()) < EPS) {
            return;
        }

        if (pointCount == arrayOfPoints.length) {
            FunctionPoint[] newArray = new FunctionPoint[arrayOfPoints.length * 2 + 1];
            System.arraycopy(arrayOfPoints, 0, newArray, 0, pointCount);
            arrayOfPoints = newArray;
        }

        if (pointCount - insertIndex > 0) {
            System.arraycopy(arrayOfPoints, insertIndex, arrayOfPoints, insertIndex + 1, pointCount - insertIndex);
        }

        arrayOfPoints[insertIndex] = newPoint;
        pointCount++;
    }

}