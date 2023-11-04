package eventSystem;

import java.util.ArrayList;
import java.util.List;

public class EventSystem {
    private List<EventListener> eventListeners;

    public EventSystem() {
        this.eventListeners = new ArrayList<>();
    }

    public void subscribe(EventListener listener) {
        eventListeners.add(listener);
    }

    public void unsubscribe(EventListener listener) {
        eventListeners.remove(listener);
    }

    public void triggerEvent(EventType eventType, Object data) {
        for (EventListener listener : eventListeners) {
            listener.onEvent(eventType, data);
        }
    }
}
