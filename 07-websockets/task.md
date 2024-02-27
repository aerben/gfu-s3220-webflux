# Aufgabe: WebSocket Handler schreiben

Solltest du keinen Websocket-Client auf deinem Gerät haben, dann lade dir Postman herunter:
[https://www.postman.com/downloads/](https://www.postman.com/downloads/).

Lege nun eine neue Klasse an und nenne sie `MessageReceiver`. Sie soll das Interface `WebSocketHandler` implementieren.
Mache eine `@Component` aus der Klasse.
Implementiere die `handle`-Methode dieser Klasse wie folgt:
```java
@Override
public Mono<Void> handle(WebSocketSession session) {
    return session.receive().map(WebSocketMessage::getPayloadAsText)
        .doOnNext(System.out::println)
        .last().then();
}
```
Diese Implementierung wird Nachrichten empfangen und auf `System.out` drucken.
Baue deinen neuen Receiver nun in `WebSocketApplication` ein. Autowire ihn so wie auch den `EventReceiver`
und füge nach Zeile 30 folgende Zeile ein:
```java
map.put("/message-receiver", messageReceiver);
```
wobei du den Variablennamen `messageReceiver` ggf. anpassen musst.
Verbinde dich nun mit dem Websocket-Endpunkt mit Postman unter der URL
`localhost:8080/message-receiver` und schicke eine Nachricht ab. Sie sollte auf `System.out` erscheinen.
