# API_Files
Contenido:

- [Enunciado](#enunciado)
- [Versiones](#versiones)
- [Correr Proyecto](#correr-proyecto)
- [Swagger Endpoints](#swagger-endpoints)
- [Setear entorno](#setear-entorno)
- [Tools](#tools)

## Enunciado
Diseñar e implementar una API Rest para cargar archivos y almacenar un registro del mismo en una
Base de Datos (BD). Los archivos no se guardarán, sólo se guardará el registro de la subida del
mismo con la utilización de una función hash, guardando el resultado de aplicar la función hash en
la BD.
Para esto se deberá implementar un endpoint que permita cargar archivos a través de FORM-DATA
y determinar el algoritmo de hash a devolver por parámetro en la URL. Las funciones hash que se
deben aplicar sobre los archivos serán SHA-256 y SHA-512 del conjunto SHA-2, pero solo se
devolverá la especificada. Además, si el archivo ya se subió previamente, es decir, ese hash ya está
presente en la BD, se debe guardar la fecha de última vez en la que se cargó el archivo en una
variable llamada “lastUpload”. 

## Versiones

| Spring Boot | Java | PostgreSQL |
|---|---|---|
| 3.1.4 | 17 | 12 |

## Correr Proyecto

Se debe tener instalado postgreSQL

- Clonar proyecto
- Setear variables de entorno
- Ejecutar "maven clean"
- Ejecutar "maven build"
- finalmente "run"

## Swagger Endpoints

[Swagger](http://localhost:8080/swagger-ui/index.html#/)

- **GET /api/documents**
  
    Endpoint que permite listar todos los documentos subidos con sus hash.

    RESPUESTA:
```
[
 {
 "fileName": "test.txt",
 "hashSha256": "8cc6618520b943d7a32a69899f5c68ad2d43eae6e211d2c646b89cef4e7137f2",
 "hashSha512": "h74s33e3b22b159ef0d93e5947a7e28045b1a8f1452287b3c25c07312527519eef871
 9333f86080fc5e969e49aaa6faaa911cb7a64e1f938fb7b058ac4c6fyh6"
 },
 {
 "fileName": "test2.txt",
 "hashSha256": "dfg6618520b943d7a32a69899f5c68ad2d43eae6e211d2c646b89cef4e7137f3",
 "hashSha512": "g35233e3b22b159ef0d93e5947a7e28045b1a8f1452287b3c25c07312527519eef871
 9333f86080fc5e969e49aaa6faaa911cb7a64e1f938fb7b058ac4c6g74d",
 "lastUpload": "2022-04-27T15:21:53.678Z"
 }
]

```

- **GET /api/documents/{hashType}/{hash}**
  
    Endpoint para encontrar un archivo por su hash y devolver su hash y última actualización.

**Parámetros**

|          Nombre | Requerido |  Tipo   | Descripción                                                                                                                                                                  |
| -------------:|:--------:|:-------:| --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
|     `hashType` | requerido | string  | Tipo de algoritmo. <br/><br/> Valores soportados: “SHA-256” o “SHA-512”.                                                                     |

   RESPUESTA:
```
{
 "fileName": "test2.txt",
 "hash": "dfg6618520b943d7a32a69899f5c68ad2d43eae6e211d2c646b89cef4e7137f3",
 "lastUpload": "2022-04-27T15:21:53.678Z"
}
```

- **POST /api/hash/{hashType}**
  
  Endpoint para subir documentos. Retorna la lista de archivos subidos.

**Parámetros**

|          Nombre | Requerido |  Tipo   | Descripción                                                                                                                                                       |
| -------------:|:--------:|:-------:| --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
|     `hashType` | requerido | string  | Tipo de algoritmo. <br/><br/> Valores soportados: “SHA-256” o “SHA-512”.                                                                    |
|     `documents` | requerido | Body (multipart/formdata)  | Lista de archivos a subir (multipart/formdata)                                              |

  RESPUESTA:
```
{
 "algorithm": "SHA-256",
 "documents": [
 {
 "fileName": "test.txt",
 "hash": "8cc6618520b943d7a32a69899f5c68ad2d43eae6e211d2c646b89cef4e7137f2"
 },
 {
 "fileName": "test2.txt",
 "hash": "8cc6618520b943d7a32a69899f5c68ad2d43eae6e211d2c646b89cef4e7137f3",
 "lastUpload": "2022-04-27T15:21:53.678Z"
 }
 ]
```
## Setear Entorno

- Setear Variables de entorno:
  
  ```
  DB_HOST=;
  DB_PORT=;
  DB_NAME=;
  DB_USER=;
  DB_PASSWORD=;
  ```

## Tools


- Java 17
- Spring boot 3.1.4
- Maven
- Postgresql DB
- JPA - Hibernate
- Lombok
- IntelliJ Idea
- ModelMapper
- Postman
- Swagger
- MessageDigest


