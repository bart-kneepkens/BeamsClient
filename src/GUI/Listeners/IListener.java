/* 
 * An OpenGL GameEngine test project.
 * By BlackenedSandman - marcvandeuren@gmail.com
 */
package GUI.Listeners;

import userInput.Input;
import java.util.List;
import rx.Observable;
import userInput.MouseState;
/**
 * Interface designed for classes that will react to user input.
 * @author Blackened
 */
public interface IListener {
    
    /**
     * Subscribe this instance of the class to an observable which will notify
     * all subscribers of user input.
     * @param inputObservable: The observable to subscribe to.
     * @param listenToInputs
     */
    void subscribe(Observable<MouseState> inputObservable);
    
    /**
     * Un-subscribes from the observable which will notify all subscribers of 
     * user input.
     */
    void unsubscribeToUserInput();
    
}
