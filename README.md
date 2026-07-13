# file-service

Serviço de arquivos do Rota Fácil. Mantém metadados no PostgreSQL, objetos no MinIO e remove arquivos relacionados quando recebe eventos de exclusão.

## Porta e base path

- Porta: `8088`
- Context path: `/files`
- Via gateway: `http://localhost:8080/files`

## Endpoints

Usuário autenticado:

- `POST /files/users/me/profile`, `GET /files/users/me/profile`, `DELETE /files/users/me/profile/{profileId}`.
- `POST /files/users/me/documents`, `GET /files/users/me/documents`, `GET|PUT|DELETE /files/users/me/documents/{documentId}`.

Ônibus:

- `POST /files/bus/{busId}`, `GET /files/bus/{busId}`.
- `GET|PUT|DELETE /files/bus/{fileId}`.

Instituições e pontos:

- `POST /files/institutions/{institutionId}`, `GET /files/institutions`, `GET /files/institutions/{institutionId}/all`, `GET|PUT|DELETE /files/institutions/{fileId}`.
- `POST /files/board-points/{boardPointId}`, `GET /files/board-points`, `GET /files/board-points/{boardPointId}/all`, `GET|PUT|DELETE /files/board-points/{fileId}`.

Mapas de calor:

- `POST /files/heat-map/{routeId}`
- `GET /files/heat-map/{routeId}/all`
- `GET|PUT|DELETE /files/heat-map/{fileId}`

O `BusController` declara hoje dois GETs com o mesmo padrão `/bus/{id}` — um para listar por ônibus e outro para buscar arquivo. Essa colisão de mapeamento é um ponto conhecido e deve ser corrigida antes de depender desses dois GETs; os demais recursos distinguem listagem por `/{ownerId}/all` e arquivo por `/{fileId}`. Listagem e exclusão de mapas usadas pelo painel exigem `ADMIN/SUPERUSER` no gateway.

Infra: `GET /files/health-check`, `/files/v3/api-docs`, `/files/swagger-ui.html`.

## Categorias

`PROFILE_PIC`, `DOCUMENT`, `BUS_PHOTO`, `INSTITUTION_PIC`, `BOARD_POINT_PIC` e `ROUTE_BOARD_POINT_HEAT_MAP`.

## Eventos

Publica `file.created`, `file.updated` e `file.deleted` em `file.events`.

Consome `user.deleted`, `prefecture.deleted`, `institution.deleted`, `boarding.deleted` e `bus.deleted` para limpeza em cascata.

## Persistência e MinIO

- Banco: `jdbc:postgresql://localhost:5437/file_database`
- Usuário padrão: `rota-facil`
- Migrations: `src/main/resources/db/migration`
- MinIO padrão: `http://localhost:9000`, bucket `rota-facil`

Variáveis: `FILE_DATASOURCE_URL`, `DATASOURCE_*`, `MINIO_URL`, `MINIO_ROOT_USER`, `MINIO_ROOT_PASSWORD`, `MINIO_BUCKET_NAME`, `RABBITMQ_*` e `EUREKA_URL`.

## Como rodar

```bash
cd file-service
./mvnw spring-boot:run
```

Requer Java 21, PostgreSQL, MinIO, Eureka e RabbitMQ.
