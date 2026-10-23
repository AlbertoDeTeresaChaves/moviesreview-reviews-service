# Reviews Microservice
> Microservicio encargado de centralizar el ciclo de vida y la información base de las reviews dentro del ecosistema del sistema. Todo ello dentro de una arquitectura por Capas por su facil aprendizaje

## Implemetaciones
  * **Spring boot Actuator**
  * **Config Client**
  * **Spring Web**
  * **OpenFeign**
  * **SpringDoc OpenAPI**
  * **Spring Boot DevTools (Solo sera para entorno de desarrollo)**
  * **Lombok**
  * **Spring Data MongoDB**
  * **Validation**

---

## Flujo del funcionamiento
<img width="1207" height="800" alt="image" src="https://github.com/user-attachments/assets/2f53efa9-2b11-4974-b2ca-6449baaaf2ca" />

1. **Peticion HTTP POST:** El cliente realiza una peticion `HTTP POST` para crear una nueva review.
2. **Controller:** El controller recibe la peticion y le redirige la logica de negocio al `Reviews Service`.
3. **Service → FeignReviewDataProvider:** El service consume los metodos de `FeignReviewDataProvider` acorde para obtener datos externos (Accounts, Movies).
   * 3.3.  **FeignReviewDataProvider → AccountsClient:** FeignReviewDataProvider consume los metodos `getAuthor()` y `getMovie()` del `AccountsClient` y `MoviesClient`.
   * 3.6. **AccountsClient → AccountsController:** AccountsClient realiza un `HTTP GET` a la url acorde a AccountsController para obtener dichos datos.
4. **Service → Mapper:** El service crea una variable `newReview` llamando al metodo `toEntity()` del mapper.
5. **Service → Repository:** El service llama al `ReviewRepository` para guardar la review a la database y a su vez almacenar dicha respuesta en `savedReview`.
6. **Service → Mapper:** El service llama al metodo `toDto()` del mapper.
   * 6.5. **Service → Controller:** El service devuelve el review mapeado a `ReviewResponseDto`.
7. **Controller → Cliente:** El `ReviewsController` devuelve la respuesta en un JSON con un codigo **201 CREATED**

---

## Uso de OpenAPI/Swagger
> Gracias a la documentacion de OpenAPI y el refuerzo de las herramientas de Swagger tenemos un vistazo en detalle sobre los endpoints, parametros, respuestas, modelos, etc.
> 
> Para ello solo debemos escribir en el navegador `IP DE REVIEWS-SERVICE:PORT/swagger-ui.html`
<img width="1846" height="915" alt="image" src="https://github.com/user-attachments/assets/55b18c72-788a-4903-bfa3-bf6693182385" />

---

## Contenerización con Docker & Cloud Native Builds
En este entorno, cada microservicio debe ser agnóstico a la máquina donde se ejecuta. Para ello recurrimos a **Docker**, abstrayendo el código, entorno de ejecución y dependencias en imágenes ligeras.

Para optimizar la generación de imágenes y evitar mantener archivos `Dockerfile` redundantes, delegué la responsabilidad a <a href="https://cloud.google.com/blog/products/application-development/introducing-jib-build-java-docker-images-better">**Google Jib**</a>. Herramienta que compila de forma nativa imágenes optimizadas para Java directamente hacia el daemon de Docker sin necesidad de configuraciones complejas.

Basta con incluir el plugin en el `pom.xml` y ejecutar:

```bash
mvn compile jib:dockerBuild
```

