# david-ups-web

Portfolio web hecho con Kotlin Multiplatform y Compose para WebAssembly.

## Ejecutar en local

```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

Luego abre:

```text
http://localhost:8080/
```

## Generar la version estatica

```bash
./gradlew :composeApp:wasmJsBrowserDistribution
```

La carpeta lista para publicar queda en:

```text
composeApp/build/dist/wasmJs/productionExecutable
```
