/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Listeners;

import GUI.GUIElement;
import userInput.Input;
import java.util.List;
import java.util.function.Consumer;
import org.lwjgl.util.vector.Vector2f;
import rx.Observable;
import rx.Subscription;
import userInput.Event;
import userInput.MouseState;

/**
 *
 * @author Blackened
 */
public class SimpleListener extends GUIElement implements IListener{
    
    private final String name;
    
    private final Consumer<Event> consumer;
    
    private Subscription mouseSubscription;
    
    /**
     *
     * @param name
     * @param consumer
     */
    public SimpleListener(String name, Consumer<Event> consumer) {
        super(0, 0, new Vector2f(123,123), new Vector2f(1,1));
        this.name = name;
        this.consumer = consumer;
    }
    
    private void click(Event event){
        consumer.accept(event);
    }


    public void subscribe(Observable<MouseState> inputObservable, List<Input> listenToInputs) {
        this.mouseSubscription = inputObservable.filter(x -> x.anyMatch(listenToInputs))
                .subscribe(x -> click(new Event(x, this)), 
                        x -> System.out.println("OnError recieved!"));
    }

    @Override
    public void unsubscribeToUserInput() {
        this.mouseSubscription.unsubscribe();
    }   

    @Override
    public void subscribe(Observable<MouseState> inputObservable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
