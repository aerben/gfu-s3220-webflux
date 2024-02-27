# Aufgabe 1: OAuth2-Profilinformationen

Für diese Aufgabe brauchst du einen Account auf github.com mit einem beliebigen Profilbild.
Solltest du noch keinen Account haben, leg dir am besten einen an und hinterlege ein Dummy-Profilbild.

Starte einmal die Anwendung und browse auf [http://localhost:8080](http://localhost:8080). Du solltest dich per GitHub anmelden können
und dann sollte dein Benutzername auf der Seite erscheiben.

Füge nun in der Ressource `/user` in die Map, die zurückgegeben wird, die Avatar-URL des Github-Users ein. 
Du kannst dir anschauen, wie dieses Feld heißt, indem du den Debugger benutzt. Setze in Zeile 36 in der `user`-
Methode einen Breakpoint und schaue dir die Attributes an. Im Hinweis steht die Lösung, falls du in Probleme läufst.

Wir wollen den Avatar nun auf der Seite rendern.
Füge dazu nach Zeile 16 in `index.html` folgende Zeile ein: 
```javascript
$("#avatar").attr("src", data.avatar_url)
```
Und daraufhin nach Zeile 39
```html
<img id="avatar" src="missing.png" alt="User Avatar">
```
Starte die Anwendung nun neu und schaue, ob es funktioniert.

## Hinweise

- Das Attribute für das Avatarbild heißt `avatar_url.`

# Aufgabe 2: Authentifizierte Endpunkte

Füge eine POST-Ressource `/user/track` in `ReactiveWebApplication` hinzu. Diese Ressource soll die Profilinformationen des aktuellen
Benutzers auf `System.out` ausgeben. Benutze dazu das gleiche Methodenargument wie die `user`-Methode:
```
@AuthenticationPrincipal OAuth2User principal
```
`OAuth2User` hat _keine_ sinnvolle `toString()`-Implementierung. Lies am besten die Attribute aus mit der `getAttributes()`-Methode.

In der `index.html` wird die `/user/track`-Ressource bereits aufgerufen. Du kannst nun also deinen Service
neu starten und solltest direkt nach Anmeldung über Github in der Standardausgabe des Services in deiner IDE
die Benutzerinformationen sehen.
