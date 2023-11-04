package eventSystem;

public interface EventListener {
    void onEvent(EventType eventType, Object data);
}
