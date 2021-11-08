# MISO-vinilo-grupo32
APP Vinilos - (Grupo 32) - MISO: Ingeniería de software para aplicaciones móviles

## APK Version 1
El APK de la primera versión (VinilosAPP.apk) de la aplicacion lo podrá encontrar en la ruta https://github.com/anderson0365/MISO-vinilo-grupo32/tree/main/APK/Version1

## Consideraciones a tener en cuenta en version 1
* La version solo contiene la historia de usuario HU02. La app se encuentra configurada para que consulte el detalle del album con ID 101 en el api https://public-back-sandbox-vinyls.herokuapp.com/. Si desea cambiar estos parametros, debera descargar el proyecto y hacer las respectivas modificaciones con Android Studio. Para modificar el ID del album consultado, abra la actividad "MainActivity.kt", y en la linea 15 del documento modifique el valor 101 por la nueva ID. Para cambiar la URL del api, valla al archivo "NetworkServiceAdapter.kt" y en la línea 17, cambie el parámetro BASE_URL por la nueva url.

## Ejecucion local de aplicacion.
La versión mínima de SDK para ejecitar APP : API 21.

Podra ejecutar el APP usando el .apk que encontrará en la carpeta APK de la raiz del proyecto.

Tambien podrá descargar el proyecto del repositorio y ejecutarlo en Android Studio.
