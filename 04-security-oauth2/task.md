# Aufgabe 1: OAuth2-Profilinformationen

Füge in der Ressource `/user` in die Map, die zurückgegeben wird, die Avatar-URL des Github-Users ein. 
Lese diesen Link in der `index.html` aus, wenn der Benutzer authentifiziert ist.
Bonus: Rendere den Avatar in der HTML-Seite. 
Es reicht, wenn du den Link einfach als Text ausgibst.

## Hinweise

- Im Principal, den du als Argument zu `/user` erhältst, stecken viele Informationen. Nutze den Debugger!
- Du kannst JQuery nutzen, um das src-Attribut eines img-Tags zu ändern. Hier sind ein paar Snippets, die helfen:
```javascript
// Im <script>-Tag, wo /user aufgerufen wird:
$("#avatar").attr("src", data.avatar_url)
```
```html
<!-- Im <div>, wo auch der Benutzername angezeigt wird -->
<img id="avatar" src="missing.png" alt="User Avatar">
```

# Aufgabe 2: Authentifizierte Endpunkte

Füge eine POST-Ressource `/user/track` hinzu. Diese Ressource soll die Profilinformationen des aktuellen
Benutzers auf `System.out` ausgaben.

## Hinweise

- Lege ein neues POST-Mapping in `ReactiveWebApplication` an. Du kannst, wenn du willst, auch eine neue Klasse verwenden. Denke dann nur daran, sie mit `@RestController` zu annotieren.
- `OAuth2User` hat _keine_ sinnvolle `toString()`-Implementierung. Lies am besten die Attribute aus mit der `getAttributes()`-Methode.
