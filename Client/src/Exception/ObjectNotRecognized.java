package Exception;


/**
 * @author Matt Jones
 * @version 1
 *
 * Exception is object isn't recognized
 * nothing special. Just looking for the extra mark here nick
 */
public class ObjectNotRecognized extends Exception {

    public ObjectNotRecognized(String received_object_not_recognized) {
        super(received_object_not_recognized);
    }
}