// 209379239 Tom Sasson
package geometry.primitives;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public class Line {
    //static values
    private static final double THRESHOLD = 0.00001;
    //Fields
    private Point startPoint;
    private Point endPoint;
    //constructors
    /**
     * Constructor method of a line.
     * @param start is the starting point.
     * @param end is the end point.
     */
    public Line(Point start, Point end) {
        //apply values to points
        this.startPoint = new Point(start.getX(), start.getY());
        this.endPoint = new Point(end.getX(), end.getY());
    }
    /**
     * Constructor method of a line.
     * @param x1 is x value of starting point.
     * @param y1 is y value of starting point.
     * @param x2 is x value of ending point.
     * @param y2 is y value of ending point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        //apply values to points
        this.startPoint = new Point(x1, y1);
        this.endPoint = new Point(x2, y2);
    }
    //public methods of class
    /**
     * Method that checks the length of the line, using
     * the measure distance method.
     * @return length of the line.
     */
    public double length() {
        //use the distance method
        return this.startPoint.distance(endPoint);
    }
    /**
     * Method that calculates the middle point of the line.
     * @return middle point.
     */
    public Point middle() {
        //calculate the mid values
        double midX = (this.startPoint.getX() + this.endPoint.getX()) / 2;
        double midY = (this.startPoint.getY() + this.endPoint.getY()) / 2;
        //return the new point
        return new Point(midX, midY);
    }
    /**
     * Method that returns the start point of the line.
     * @return start point.
     */
    public Point start() {
        //return the start point.
        return this.startPoint;
    }
    /**
     * Method that returns the end point of the line.
     * @return end point.
     */
    public Point end() {
        //return the end point.
        return this.endPoint;
    }
    /**
     * Method that returns true if two lines intersect,
     * false if not.
     * @param other is the line we check if intersects with our line.
     * @return boolean value according to intersection.
     */
    public boolean isIntersecting(Line other) {
        //check if they are equal
        Line myLine = new Line(this.startPoint, this.endPoint);
        if (myLine.equals(other)) {
            return true;
        }

        //check if one of them at least parallels to the y-axis
        if (Math.abs(myLine.startPoint.getX() - myLine.endPoint.getX()) < THRESHOLD
        || Math.abs(other.startPoint.getX() - other.endPoint.getX()) < THRESHOLD) {
            //call function to handle
            return parallelToY(myLine, other);
        }

        //check if one of them at least parallels to the x-axis
        if ((Math.abs(slope(myLine.startPoint, myLine.endPoint) - 0) < THRESHOLD)
        || (Math.abs(slope(other.startPoint, other.endPoint) - 0) < THRESHOLD)) {
            return parallelToX(myLine, other);
        }

        //check if they both have the same slope
        if (Math.abs(slope(myLine.startPoint, myLine.endPoint) - slope(other.startPoint, other.endPoint)) < THRESHOLD) {
            return sameSlope(myLine, other, slope(myLine.startPoint, myLine.endPoint));
        }

        //check their intersection points, and check if they are in range
        return checkIntersectionLegal(myLine, other);
    }
    /**
     * Method that returns true if two lines intersect with a given line,
     * false if not.
     * @param other1 is the first line we check if intersects with our line.
     * @param other2 is the second line we check if intersects with our line.
     * @return boolean value according to intersections.
     */
    public boolean isIntersecting(Line other1, Line other2) {
        Line myLine = new Line(this.startPoint, this.endPoint);
        if (myLine.isIntersecting(other1) && myLine.isIntersecting(other2)) {
            return true;
        }

        //if reached here, return false
        return false;
    }
    /**
     * Method that returns the point of intersection between two line,
     * or null if there isn't one.
     * @param other is the line we check if intersects with our line.
     * @return the point object of intersection, null if there is none.
     */
    public Point intersectionWith(Line other) {
        //create copy for convenience
        Line myLine = new Line(this.startPoint, this.endPoint);

        //check if they intersect at all
        if (!myLine.isIntersecting(other)) {
            return null;
        }

        //check how they are intersecting
        return howIsIntersecting(myLine, other);
    }
    /**
     * Method that returns true if two lines are the same,
     * false if not.
     * @param other is the line we check if the same as our line.
     * @return boolean value according to the lines.
     */
    public boolean equals(Line other) {
        //check if they have the same values of points
        return ((other.startPoint.getX() == this.startPoint.getX())
                && (other.startPoint.getY() == this.startPoint.getY())
                && (other.endPoint.getX() == this.endPoint.getX())
                && (other.endPoint.getY() == this.endPoint.getY()))
                ||
                ((other.startPoint.getX() == this.endPoint.getX())
                && (other.startPoint.getY() == this.endPoint.getY())
                && (other.endPoint.getX() == this.startPoint.getX())
                && (other.endPoint.getY() == this.startPoint.getY()));
    }
    /**
     * Method to return the closest point of intersection to the start point of a line.
     * @param rect is the rectangle we check intersections with.
     * @return null if there is no intersection, the point of intersection if yes.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        //check if there are intersection points at all
        Line myLine = new Line(this.startPoint, this.endPoint);
        if (rect.intersectionPoints(myLine) == null) {
            return null;
        }
        //check for the smallest distance
        Point firstPoint = rect.intersectionPoints(myLine).get(0);
        int counter = 0, iteration = 0;
        double distance = myLine.startPoint.distance(firstPoint), checkValue = 0;
        for (Point intersection : rect.intersectionPoints(myLine)) {
            checkValue = myLine.startPoint.distance(intersection);
            if (checkValue < distance) {
                distance = checkValue;
                counter = iteration;
            }
            iteration++;
        }
        //return the desired value
        Point closeIntersection = new Point(rect.intersectionPoints(myLine).get(counter).getX(),
                rect.intersectionPoints(myLine).get(counter).getY());
        return closeIntersection;
    }
    // private methods of class
    /**
     * Method that calculates the slope of a line
     * using two points.
     * @param p1 is the first point.
     * @param p2 is the second point.
     * @return the slope.
     */
    private double slope(Point p1, Point p2) {
        //calculate the slope
        return (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
    }
    /**
     * Method that calculates the y - intercept of the
     * linear function.
     * @param x is the x value of the function.
     * @param y is the y value of the function.
     * @param m is the slope.
     * @return y - intercept.
     */
    private double bValue(double x, double y, double m) {
        //use the linear function formula.
        return y - (m * x);
    }
    /**
     * Method that calculates the x value an intersection.
     * @param m1 is the first slope.
     * @param m2 is the second slope.
     * @param b1 is the first y - intercept.
     * @param b2 is the second y - intercept.
     * @return x value.
     */
    private double intersectionXValue(double m1, double m2, double b1, double b2) {
        //use the linear function formula.
        return (b2 - b1) / (m1 - m2);
    }
    /**
     * Method that calculates the y value an intersection.
     * @param m is the slope.
     * @param b is the y - intercept.
     * @param xValue is parameter of x.
     * @return y value.
     */
    private double intersectionYValue(double m, double b, double xValue) {
        //use the linear function formula
        return m * xValue + b;
    }
    /**
     * Method that checks if a value is within range of
     * 2 other values.
     * @param checkValue is the number we check.
     * @param max is the max value.
     * @param min is the min value.
     * @param threshold is the value that helps check
     * accuracy with doubles.
     * @return true if in range, false if not.
     */
    private boolean withinRange(double checkValue, double max, double min, double threshold) {
        //check if within range using the threshold
        if ((checkValue >= min - threshold) && (checkValue <= max + threshold)) {
            return true;
        }
        return false;
    }
    /**
     * Method that checks if two lines that at least one is parallel to the y-axis intersect.
     * @param myLine is the first line.
     * @param other is the second line.
     * @return boolean value according to intersections.
     */
    private boolean parallelToY(Line myLine, Line other) {
        //check if both are parallel
        if (Math.abs(myLine.startPoint.getX() - myLine.endPoint.getX()) < THRESHOLD
        && Math.abs(other.startPoint.getX() - other.endPoint.getX()) < THRESHOLD) {
            //call method to handle both parallel
            return bothParallelToY(myLine, other);
        }
        //if the first line is parallel
        if (Math.abs(myLine.startPoint.getX() - myLine.endPoint.getX()) < THRESHOLD) {
            return oneParallelToY(myLine, other);
        }
        //if the second is parallel
        return oneParallelToY(other, myLine);
    }
    /**
     * Method that checks if two lines that are parallel to the y-axis intersect.
     * @param myLine is the first line.
     * @param other is the second line.
     * @return boolean value according to intersections.
     */
    private boolean bothParallelToY(Line myLine, Line other) {
        //check if the x values are different
        if (Math.abs(myLine.startPoint.getX() - other.startPoint.getX()) > THRESHOLD) {
            return false;
        }

        //check the values of the minimum and maximum for each line
        double myLineMaxY = Math.max(myLine.startPoint.getY(), myLine.endPoint.getY());
        double myLineMinY = Math.min(myLine.startPoint.getY(), myLine.endPoint.getY());
        double otherMaxY = Math.max(other.startPoint.getY(), other.endPoint.getY());
        double otherMinY = Math.min(other.startPoint.getY(), other.endPoint.getY());

        //check if they are in range
        if (myLineMinY > otherMaxY || otherMinY > myLineMaxY) {
            return false;
        }

        //if passed tests, they intersect
        return true;
    }
    /**
     * Method that checks if two lines, one is parallel to the y-axis and the other
     * not, are intersecting .
     * @param parallel is the parallel line.
     * @param other is the second line.
     * @return boolean value according to intersections.
     */
    private boolean oneParallelToY(Line parallel, Line other) {
        //find slope of second line
        double otherSlope = slope(other.startPoint, other.endPoint);

        //if it is parallel to the x-asix
        if (Math.abs(otherSlope - 0) < THRESHOLD) {
            //x value in parallel will be the same in all the line - parallel to y-axis
            //y value in other will be the same in all the line - parallel to x-axis
            double xValue = parallel.end().getX();
            double yValue  = other.start().getY();

            //check if x and y values of the lines are in range for intersection
            if (((withinRange(yValue, parallel.startPoint.getY(), parallel.endPoint.getY(), THRESHOLD))
                    || (withinRange(yValue, parallel.endPoint.getY(), parallel.startPoint.getY(), THRESHOLD)))
                    && ((withinRange(xValue, other.startPoint.getX(), other.endPoint.getX(), THRESHOLD))
                    || (withinRange(xValue, other.endPoint.getX(), other.startPoint.getX(), THRESHOLD)))) {
                    return true;
            }
            return false;
        }

        //find y - intercept of second line
        double bValue = bValue(other.startPoint.getX(), other.startPoint.getY(), otherSlope);

        //find y value of interception
        double interceptionY = parallel.startPoint.getX() * otherSlope + bValue;

        //check if y value is within range
        if (((withinRange(interceptionY, other.endPoint.getY(), other.startPoint.getY(), THRESHOLD))
                || (withinRange(interceptionY, other.startPoint.getY(), other.endPoint.getY(), THRESHOLD)))
        && ((withinRange(interceptionY, parallel.endPoint.getY(), parallel.startPoint.getY(), THRESHOLD))
                || (withinRange(interceptionY, parallel.startPoint.getY(), parallel.endPoint.getY(), THRESHOLD)))) {
            return true;
        }
        //if reached here, return false
        return false;
    }
    /**
     * Method that checks if two lines that at least one is parallel to the x-axis intersect.
     * @param myLine is the first line.
     * @param other is the second line.
     * @return boolean value according to intersections.
     */
    private boolean parallelToX(Line myLine, Line other) {
        //check if both are parallel to x-axis
        if ((Math.abs(slope(myLine.startPoint, myLine.endPoint) - 0) < THRESHOLD)
                && (Math.abs(slope(other.startPoint, other.endPoint) - 0) < THRESHOLD)) {
            return bothParallelToX(myLine, other);
        }

        //if the first is parallel
        if (Math.abs(slope(myLine.startPoint, myLine.endPoint) - 0) < THRESHOLD) {
            return oneParallelToX(myLine, other);
        }

        //if the second one is
        return oneParallelToX(other, myLine);
    }
    /**
     * Method that checks if two lines that are parallel to the x-axis intersect.
     * @param myLine is the first line.
     * @param other is the second line.
     * @return boolean value according to intersections.
     */
    private boolean bothParallelToX(Line myLine, Line other) {
        //check if they have different y values
        if (Math.abs(myLine.startPoint.getY() - other.startPoint.getY()) > THRESHOLD) {
            return false;
        }

        //check the values of the minimum and maximum for each line
        double myLineMaxX = Math.max(myLine.startPoint.getX(), myLine.endPoint.getX());
        double myLineMinX = Math.min(myLine.startPoint.getX(), myLine.endPoint.getX());
        double otherMaxX = Math.max(other.startPoint.getX(), other.endPoint.getX());
        double otherMinX = Math.min(other.startPoint.getX(), other.endPoint.getX());

        //check if they are in range
        if (myLineMinX > otherMaxX || otherMinX > myLineMaxX) {
            return false;
        }

        //if passed tests, they intersect
        return true;
    }
    /**
     * Method that checks if two lines that one of them is parallel to the x-axis intersect.
     * @param parallel is the line that is parallel.
     * @param other is the second line.
     * @return boolean value according to intersections.
     */
    private boolean oneParallelToX(Line parallel, Line other) {
        //find slope of second line
        double otherSlope = slope(other.startPoint, other.endPoint);

        //find y - intercept of second line
        double bValue = bValue(other.startPoint.getX(), other.startPoint.getY(), otherSlope);

        //find the x value of interception
        double interceptionX = intersectionXValue(otherSlope, 0, bValue, parallel.startPoint.getY());

        //check if x value is in range
        if (((withinRange(interceptionX, other.endPoint.getX(), other.startPoint.getX(), THRESHOLD))
                || (withinRange(interceptionX, other.startPoint.getX(), other.endPoint.getX(), THRESHOLD)))
        && ((withinRange(interceptionX, parallel.endPoint.getX(), parallel.startPoint.getX(), THRESHOLD))
                || (withinRange(interceptionX, parallel.startPoint.getX(), parallel.endPoint.getX(), THRESHOLD)))) {
            return true;
        }

        //if reached here, return false
        return false;
    }
    /**
     * Method that checks if two lines that have the same slope, intersect.
     * @param myLine is the first line.
     * @param other is the second line.
     * @param slope is the slope they share.
     * @return boolean value according to intersections.
     */
    private boolean sameSlope(Line myLine, Line other, double slope) {
        //find their b values
        double myB = bValue(myLine.startPoint.getX(), myLine.startPoint.getY(), slope);
        double otherB = bValue(other.startPoint.getX(), other.startPoint.getY(), slope);

        //if they don't have the same b values, they do not intersect
        if (myB != otherB) {
            return false;
        }

        //find minimum and maximum values of x for each line
        double myLineMaxX = Math.max(myLine.startPoint.getX(), myLine.endPoint.getX());
        double myLineMinX = Math.min(myLine.startPoint.getX(), myLine.endPoint.getX());
        double otherMaxX = Math.max(other.startPoint.getX(), other.endPoint.getX());
        double otherMinX = Math.min(other.startPoint.getX(), other.endPoint.getX());

        //check if they are not in range
        if (myLineMinX > otherMaxX || otherMinX > myLineMaxX) {
            return false;
        }

        //if passed tests, they intersect
        return true;
    }
    /**
     * Method that checks if two regular lines intersect.
     * @param myLine is the first line.
     * @param other is the second line.
     * @return boolean value according to intersections.
     */
    private boolean checkIntersectionLegal(Line myLine, Line other) {
        //calculate slopes
        double mySlope = slope(myLine.startPoint, myLine.endPoint);
        double otherSlope = slope(other.startPoint, other.endPoint);

        //find x and y values of points
        double myX1 = myLine.startPoint.getX(), myY1 = myLine.startPoint.getY();
        double myX2 = myLine.endPoint.getX(), myY2 = myLine.endPoint.getY();
        double otherX1 = other.startPoint.getX(), otherY1 = other.startPoint.getY();
        double otherX2 = other.endPoint.getX(), otherY2 = other.endPoint.getY(); //check if needed

        //find min and max values of x and y for each line
        double myMaxX = Math.max(myX1, myX2), myMaxY = Math.max(myY1, myY2);
        double myMinX = Math.min(myX1, myX2), myMinY = Math.min(myY1, myY2);
        double otherMaxX = Math.max(otherX1, otherX2), otherMaxY = Math.max(otherY1, otherY2);
        double otherMinX = Math.min(otherX1, otherX2), otherMinY = Math.min(otherY1, otherY2);

        //calculate y-intercepts
        double myB = bValue(myX1, myY1, mySlope);
        double otherB = bValue(otherX1, otherY1, otherSlope);

        //find the intersection point
        double intersectionX = intersectionXValue(mySlope, otherSlope, myB, otherB);
        double intersectionY = intersectionYValue(mySlope, myB, intersectionX);

        //check if intersection point is within range
        if (withinRange(intersectionX, myMaxX, myMinX, THRESHOLD)
        && withinRange(intersectionX, otherMaxX, otherMinX, THRESHOLD)
        && withinRange(intersectionY, myMaxY, myMinY, THRESHOLD)
        && withinRange(intersectionY, otherMaxY, otherMinY, THRESHOLD)) {
            return true;
        }

        //if reached here, return false
        return false;
    }
    /**
     * Method that returns the point of intersection if two lines intersect,
     * null if not.
     * @param myLine is the first line.
     * @param other is the second line.
     * @return point or null.
     */
    private Point howIsIntersecting(Line myLine, Line other) {
        //check if they are equal
        if (myLine.equals(other)) {
            return null;
        }

        //check if one of them at least parallels to the y-axis
        if (Math.abs(myLine.startPoint.getX() - myLine.endPoint.getX()) < THRESHOLD
                || Math.abs(other.startPoint.getX() - other.endPoint.getX()) < THRESHOLD) {
            //call function to handle
            return parallelToYPoint(myLine, other);
        }

        //check if one of them at least parallels to the x-axis
        if ((Math.abs(slope(myLine.startPoint, myLine.endPoint) - 0) < THRESHOLD)
                || (Math.abs(slope(other.startPoint, other.endPoint) - 0) < THRESHOLD)) {
            return parallelToXPoint(myLine, other);
        }

        //check if they both have the same slope
        if (Math.abs(slope(myLine.startPoint, myLine.endPoint) - slope(other.startPoint, other.endPoint)) < THRESHOLD) {
            return sameSlopePoint(myLine, other, slope(myLine.startPoint, myLine.endPoint));
        }

        //check their intersection points, and check if they are in range
        return checkIntersectionLegalPoint(myLine, other);
    }
    /**
     * Method that returns the point of intersection if two lines that at least one
     * parallel to y-axis intersect, and null if not.
     * @param myLine is the first line.
     * @param other is the second line.
     * @return point or null.
     */
    private Point parallelToYPoint(Line myLine, Line other) {
        //check if both are parallel
        if (Math.abs(myLine.startPoint.getX() - myLine.endPoint.getX()) < THRESHOLD
                && Math.abs(other.startPoint.getX() - other.endPoint.getX()) < THRESHOLD) {
            //call method to handle both parallel
            return bothParallelToYPoint(myLine, other);
        }
        //if the first line is parallel
        if (Math.abs(myLine.startPoint.getX() - myLine.endPoint.getX()) < THRESHOLD) {
            return oneParallelToYpoint(myLine, other);
        }
        //if the second is parallel
        return oneParallelToYpoint(other, myLine);
    }
    /**
     * Method that returns the point of intersection if two lines that are both
     * parallel to y-axis intersect, and null if they don't intersect.
     * @param myLine is the first line.
     * @param other is the second line.
     * @return point or null.
     */
    private Point bothParallelToYPoint(Line myLine, Line other) {
        //check if the x values are different
        if (Math.abs(myLine.startPoint.getX() - other.startPoint.getX()) > THRESHOLD) {
            return null;
        }

        //check the values of the minimum and maximum for each line
        double myLineMaxY = Math.max(myLine.startPoint.getY(), myLine.endPoint.getY());
        double myLineMinY = Math.min(myLine.startPoint.getY(), myLine.endPoint.getY());
        double otherMaxY = Math.max(other.startPoint.getY(), other.endPoint.getY());
        double otherMinY = Math.min(other.startPoint.getY(), other.endPoint.getY());

        //check if they intersect, but not get on top of each other
        if (myLineMinY == otherMaxY) {
            return new Point(myLine.startPoint.getX(), myLineMinY);
        }

        if (otherMinY == myLineMaxY) {
            return new Point(myLine.startPoint.getX(), myLineMaxY);
        }

        //if reached here, return null
        return null;
    }
    /**
     * Method that returns the point of intersection if two lines that one
     * parallels to y-axis intersect, and null if they don't intersect.
     * @param parallel is the first line.
     * @param other is the second line.
     * @return point or null.
     */
    private Point oneParallelToYpoint(Line parallel, Line other) {
        //find slope of second line
        double otherSlope = slope(other.startPoint, other.endPoint);

        //if it is parallel to the x-asix
        if (Math.abs(otherSlope - 0) < THRESHOLD) {
            //x value in parallel will be the same in all the line - parallel to y-axis
            //y value in other will be the same in all the line - parallel to x-axis
            double xValue = parallel.end().getX();
            double yValue  = other.start().getY();

            //check if x and y values of the lines are in range for intersection
            if (((withinRange(yValue, parallel.startPoint.getY(), parallel.endPoint.getY(), THRESHOLD))
                    || (withinRange(yValue, parallel.endPoint.getY(), parallel.startPoint.getY(), THRESHOLD)))
                    && ((withinRange(xValue, other.startPoint.getX(), other.endPoint.getX(), THRESHOLD))
                    || (withinRange(xValue, other.endPoint.getX(), other.startPoint.getX(), THRESHOLD)))) {
                return new Point(xValue, yValue);
            }
            return null;
        }

        //find y - intercept of second line
        double bValue = bValue(other.startPoint.getX(), other.startPoint.getY(), otherSlope);

        //find y value of interception
        double interceptionY = parallel.startPoint.getX() * otherSlope + bValue;

        //check if y value is within range
        if (((withinRange(interceptionY, other.endPoint.getY(), other.startPoint.getY(), THRESHOLD))
                || (withinRange(interceptionY, other.startPoint.getY(), other.endPoint.getY(), THRESHOLD)))
                && ((withinRange(interceptionY, parallel.endPoint.getY(), parallel.startPoint.getY(), THRESHOLD))
                || (withinRange(interceptionY, parallel.startPoint.getY(), parallel.endPoint.getY(), THRESHOLD)))) {
            return new Point(parallel.startPoint.getX(), interceptionY);
        }
        //if reached here, return null
        return null;
    }
    /**
     * Method that returns the point of intersection if two lines that at least one
     * parallel to x-axis intersect, and null if not.
     * @param myLine is the first line.
     * @param other is the second line.
     * @return point or null.
     */
    private Point parallelToXPoint(Line myLine, Line other) {
        //check if both are parallel to x-axis
        if ((Math.abs(slope(myLine.startPoint, myLine.endPoint) - 0) < THRESHOLD)
                && ((Math.abs(slope(other.startPoint, other.endPoint) - 0)) < THRESHOLD)) {
            return bothParallelToXPoint(myLine, other);
        }

        //if the first is parallel
        if (Math.abs(slope(myLine.startPoint, myLine.endPoint) - 0) < THRESHOLD) {
            return oneParallelToXPoint(myLine, other);
        }

        //if the second one is
        return oneParallelToXPoint(other, myLine);
    }
    /**
     * Method that returns the point of intersection if two lines that are both
     * parallel to x-axis, and null if they don't intersect.
     * @param myLine is the first line.
     * @param other is the second line.
     * @return point or null.
     */
    private Point bothParallelToXPoint(Line myLine, Line other) {
        //check if they have different y values
        if (Math.abs(myLine.startPoint.getY() - other.startPoint.getY()) > THRESHOLD) {
            return null;
        }

        //check the values of the minimum and maximum for each line
        double myLineMaxX = Math.max(myLine.startPoint.getX(), myLine.endPoint.getX());
        double myLineMinX = Math.min(myLine.startPoint.getX(), myLine.endPoint.getX());
        double otherMaxX = Math.max(other.startPoint.getX(), other.endPoint.getX());
        double otherMinX = Math.min(other.startPoint.getX(), other.endPoint.getX());

        //check if they intersect, but not get on top of each other
        if (myLineMinX == otherMaxX) {
            return new Point(myLineMinX, myLine.startPoint.getY());
        }

        if (otherMinX == myLineMaxX) {
            return new Point(myLineMaxX, myLine.startPoint.getY());
        }

        //if reached here, return null
        return null;
    }
    /**
     * Method that returns the point of intersection between one line
     * that is parallel to the x-axis, and another that isn't, or null
     * if they don't intersect.
     * @param parallel is the first line.
     * @param other is the second line.
     * @return point or null.
     */
    private Point oneParallelToXPoint(Line parallel, Line other) {
        //find slope of second line
        double otherSlope = slope(other.startPoint, other.endPoint);

        //find y - intercept of second line
        double bValue = bValue(other.startPoint.getX(), other.startPoint.getY(), otherSlope);

        //find the x value of interception
        double interceptionX = intersectionXValue(otherSlope, 0, bValue, parallel.startPoint.getY());

        //check if x value is in range
        if (((withinRange(interceptionX, other.endPoint.getX(), other.startPoint.getX(), THRESHOLD))
                || (withinRange(interceptionX, other.startPoint.getX(), other.endPoint.getX(), THRESHOLD)))
                && ((withinRange(interceptionX, parallel.endPoint.getX(), parallel.startPoint.getX(), THRESHOLD))
                || (withinRange(interceptionX, parallel.startPoint.getX(), parallel.endPoint.getX(), THRESHOLD))))  {
            return new Point(interceptionX, parallel.startPoint.getY());
        }

        //if reached here, return null
        return null;
    }
    /**
     * Method that returns the point of intersection if two lines have the
     * same slope, and null if they don't intersect.
     * @param myLine is the first line.
     * @param other is the second line.
     * @param slope is the slope they share.
     * @return point or null.
     */
    private Point sameSlopePoint(Line myLine, Line other, double slope) {
        //find their b values
        double myB = bValue(myLine.startPoint.getX(), myLine.startPoint.getY(), slope);
        double otherB = bValue(other.startPoint.getX(), other.startPoint.getY(), slope);

        //if they don't have the same b values, they do not intersect
        if (myB != otherB) {
            return null;
        }

        //find minimum and maximum values of x for each line
        double myLineMaxX = Math.max(myLine.startPoint.getX(), myLine.endPoint.getX());
        double myLineMinX = Math.min(myLine.startPoint.getX(), myLine.endPoint.getX());
        double otherMaxX = Math.max(other.startPoint.getX(), other.endPoint.getX());
        double otherMinX = Math.min(other.startPoint.getX(), other.endPoint.getX());

        //check if they intersect, but not get on top of each other
        if (myLineMinX == otherMaxX) {
            return new Point(myLineMinX, intersectionYValue(slope, myB, myLineMinX));
        }

        if (otherMinX == myLineMaxX) {
            return new Point(myLineMaxX, intersectionYValue(slope, myB, myLineMaxX));
        }

        //if reached here, return null
        return null;
    }
    /**
     * Method that returns the point of intersection between two lines,
     * and null if they don't intersect.
     * @param myLine is the first line.
     * @param other is the second line.
     * @return point or null.
     */
    private Point checkIntersectionLegalPoint(Line myLine, Line other) {
        //calculate slopes
        double mySlope = slope(myLine.startPoint, myLine.endPoint);
        double otherSlope = slope(other.startPoint, other.endPoint);

        //find x and y values of points
        double myX1 = myLine.startPoint.getX(), myY1 = myLine.startPoint.getY();
        double myX2 = myLine.endPoint.getX(), myY2 = myLine.endPoint.getY();
        double otherX1 = other.startPoint.getX(), otherY1 = other.startPoint.getY();
        double otherX2 = other.endPoint.getX(), otherY2 = other.endPoint.getY(); //check if needed

        //find min and max values of x and y for each line
        double myMaxX = Math.max(myX1, myX2), myMaxY = Math.max(myY1, myY2);
        double myMinX = Math.min(myX1, myX2), myMinY = Math.min(myY1, myY2);
        double otherMaxX = Math.max(otherX1, otherX2), otherMaxY = Math.max(otherY1, otherY2);
        double otherMinX = Math.min(otherX1, otherX2), otherMinY = Math.min(otherY1, otherY2);

        //calculate y-intercepts
        double myB = bValue(myX1, myY1, mySlope);
        double otherB = bValue(otherX1, otherY1, otherSlope);

        //find the intersection point
        double intersectionX = intersectionXValue(mySlope, otherSlope, myB, otherB);
        double intersectionY = intersectionYValue(mySlope, myB, intersectionX);

        //check if intersection point is within range
        if (withinRange(intersectionX, myMaxX, myMinX, THRESHOLD)
                && withinRange(intersectionX, otherMaxX, otherMinX, THRESHOLD)
                && withinRange(intersectionY, myMaxY, myMinY, THRESHOLD)
                && withinRange(intersectionY, otherMaxY, otherMinY, THRESHOLD)) {
            return new Point(intersectionX, intersectionY);
        }

        //if reached here, return null
        return null;
    }
}