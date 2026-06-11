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

## Publicar gratis en GitHub Pages

1. Crea o usa el repositorio `davidups.github.io`.
2. Sube este proyecto a ese repo.
3. En GitHub, ve a `Settings > Pages`.
4. En `Build and deployment`, selecciona `GitHub Actions` como fuente.
5. Haz push a `main` o `master`.

El workflow `.github/workflows/deploy.yml` compila la app y publica automaticamente la carpeta estatica en GitHub Pages.
