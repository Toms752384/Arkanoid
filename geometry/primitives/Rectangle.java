// 209379239 Tom Sasson
package geometry.primitives;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public class Rectangle {
    //fields
    private final Point upperLeft, upperRight, lowerLeft, lowerRight;
    private final double width, height;
    private final Line leftLine, rightLine, upLine, downLine;
    /**
     * Constructor method of a rectangle object.
     * @param upperLeft is the upper left point of the rectangle.
     * @param width is the width of the rectangle.
     * @param height is the height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        //apply values
        this.width = width;
        this.height = height;
        //vertexes of rectangle
        this.upperLeft = new Point(upperLeft.getX(), upperLeft.getY());
        this.upperRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        this.lowerLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        this.lowerRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height);
        //lines of rectangle
        this.leftLine = new Line(this.lowerLeft, this.upperLeft);
        this.rightLine = new Line(this.lowerRight, this.upperRight);
        this.upLine = new Line(this.upperLeft, this.upperRight);
        this.downLine = new Line(this.lowerLeft, this.lowerRight);
    }
    /**
     * Returns a list of intersection point with a specified line, using the
     * "intersectionWith" method of line to line (uses the rectangle lines).
     * @param line is the line to check intersections with.
     * @return list of intersection points.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        //create new list of points
        List<Point> intersectionPoints = new ArrayList<>();
        //check for intersection points. If there are, add to list
        if (line.intersectionWith(this.leftLine) != null) {
            checkUniquePoint(intersectionPoints, line.intersectionWith(this.leftLine));
        }
        if (line.intersectionWith(this.rightLine) != null) {
            checkUniquePoint(intersectionPoints, line.intersectionWith(this.rightLine));
        }
        if (line.intersectionWith(this.upLine) != null) {
            checkUniquePoint(intersectionPoints, line.intersectionWith(this.upLine));
        }
        if (line.intersectionWith(this.downLine) != null) {
            checkUniquePoint(intersectionPoints, line.intersectionWith(this.downLine));
        }
        //if list is empty, return null
        if (intersectionPoints.size() == 0) {
            return null;
        }
        return intersectionPoints;
    }
    /**
     * Method checks if in a given list, a point exists - if not, adds to list.
     * @param points is the list.
     * @param pointToAdd is the point to add.
     */
    private void checkUniquePoint(java.util.List<Point> points, Point pointToAdd) {
        for (Point point : points) {
            if (point.equals(pointToAdd)) {
                return;
            }
        }
        //if reached here, add to list
        points.add(pointToAdd);
    }
    /**
     * Getter method.
     * @return width of rectangle.
     */
    public double getWidth() {
        //return value
        return this.width;
    }
    /**
     * Getter method.
     * @return height of rectangle.
     */
    public double getHeight() {
        //return value
        return this.height;
    }
    /**
     * Getter method.
     * @return upper left point of rectangle.
     */
    public Point getUpperLeft() {
        //return copy of upper left point
        return new Point(this.upperLeft.getX(), this.upperLeft.getY());
    }
    /**
     * Getter method.
     * @return upper right point of rectangle.
     */
    public Point getUpperRight() {
        //return copy of upper right point
        return new Point(this.upperRight.getX(), this.upperRight.getY());
    }
    /**
     * Getter method.
     * @return lower left point of rectangle.
     */
    public Point getLowerLeft() {
         //return copy of lower left point
        return new Point(this.lowerLeft.getX(), this.lowerLeft.getY());
    }
    /**
     * Getter method.
     * @return lower right point of rectangle.
     */
    public Point getLowerRight() {
        //return copy of lower right point
        return new Point(this.lowerRight.getX(), this.lowerRight.getY());
    }
    /**
     * Getter method to return a copy of left line.
     * @return copy of line.
     */
    public Line getLeftLine() {
        //return copy
        return new Line(this.leftLine.start(), this.leftLine.end());
    }
    /**
     * Getter method to return a copy of right line.
     * @return copy of line.
     */
    public Line getRightLine() {
        //return copy
        return new Line(this.rightLine.start(), this.rightLine.end());
    }
    /**
     * Getter method to return a copy of upper line.
     * @return copy of line.
     */
    public Line getUpLine() {
        //return copy
        return new Line(this.upLine.start(), this.upLine.end());
    }
    /**
     * Getter method to return a copy of lower line.
     * @return copy of line.
     */
    public Line getDownLine() {
        //return copy
        return new Line(this.downLine.start(), this.downLine.end());
    }
}
