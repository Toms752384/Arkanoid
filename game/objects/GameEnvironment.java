// 209379239 Tom Sasson
package game.objects;
import base.objects.Collidable;
import geometry.primitives.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public class GameEnvironment {
    //fields
    private List<Collidable> collidableList;
    /**
     * Constructor method.
     */
    public GameEnvironment() {
        //initialize list.
        this.collidableList = new ArrayList<>();
    }
    /**
     * Method that add a given collidable to a list.
     * @param c is the collidable to add.
     */
    public void addCollidable(Collidable c) {
        //add the given collidable to the environment.
        this.collidableList.add(c);
    }
    /**
     * Method that removes a given collidable to a list.
     * @param c is the collidable to remove.
     */
    public void removeCollidable(Collidable c) {
        //remove
        this.collidableList.remove(c);
    }
    /**
     * return the information about the closest intersection of the line trajectory -
     * the object in hand, and the first collision point.
     * @param trajectory is the route we check.
     * @return information about the closest object that collides with the line.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        //create list of collisionInfo objects
        List<CollisionInfo> objects = new ArrayList<>();
        //traverse the list of collidables
        for (Collidable collidable : this.collidableList) {
            //check what is the closest point to start of trajectory - if there is one, add to objects list
            if (trajectory.closestIntersectionToStartOfLine(collidable.getCollisionRectangle()) != null) {
                objects.add(new CollisionInfo(trajectory.closestIntersectionToStartOfLine(
                        collidable.getCollisionRectangle()), collidable));
            }
        }
        //traverse the objects list, and find the one with the closest intersection point
        if (!objects.isEmpty()) {
            double distance = trajectory.start().distance(objects.get(0).collisionPoint()), checkValue = 0;
            int counter = 0, iteration = 0;
            for (CollisionInfo object: objects) {
                checkValue = trajectory.start().distance(object.collisionPoint());
                if (checkValue < distance) {
                    distance = checkValue;
                    counter = iteration;
                }
                iteration++;
            }
            //return the desired value
            return objects.get(counter);
        }
        //if reached here list is empty and method should return null
        return null;
    }
    /**
     * Returns collidables list.
     * @return list.
     */
    public List<Collidable> getCollidableList() {
        //return list
        return this.collidableList;
    }
}
