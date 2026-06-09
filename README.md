# file-service

Servico de arquivos do Rota Facil. Gerencia upload, metadados e remocao de arquivos associados a usuarios, onibus, instituicoes, pontos de embarque e mapas de calor.

## Para que serve

- Upload de foto de perfil.
- Upload de documentos de usuario.
- Upload de fotos de onibus.
- Upload de fotos de instituicoes.
- Upload de fotos de pontos de embarque.
- Upload de arquivos de mapa de calor de rota.
- Persistencia de metadados no PostgreSQL.
- Armazenamento binario no MinIO.
- Remocao em cascata por eventos de outros servicos.

## Porta e base path

- Aplicacao: `file-service`
- Porta: `8088`
- Context path: `/files`
- Via gateway: `http://localhost:8080/files`

## Endpoints principais

Usuarios:

- `POST /files/users/me/profile`: upload de foto de perfil com multipart `file`.
- `GET /files/users/me/profile`: busca foto de perfil.
- `DELETE /files/users/me/profile/{profileId}`: remove foto de perfil.
- `POST /files/users/me/documents`: upload de documento com multipart `file`.
- `GET /files/users/me/documents`: lista documentos.
- `GET /files/users/me/documents/{documentId}`: busca documento.
- `PUT /files/users/me/documents/{documentId}`: substitui documento.
- `DELETE /files/users/me/documents/{documentId}`: remove documento.

Onibus:

- `POST /files/bus/{busId}`: upload de foto do onibus.
- `GET /files/bus/{busId}`: lista fotos de onibus por categoria.
- `GET /files/bus/{fileId}`: busca foto.
- `PUT /files/bus/{fileId}`: substitui foto.
- `DELETE /files/bus/{fileId}`: remove foto.

Instituicoes:

- `POST /files/institutions/{institutionId}`: upload de foto.
- `GET /files/institutions`: lista todas as fotos de instituicoes.
- `GET /files/institutions/{institutionId}/all`: lista fotos da instituicao.
- `GET /files/institutions/{fileId}`: busca foto.
- `PUT /files/institutions/{fileId}`: substitui foto.
- `DELETE /files/institutions/{fileId}`: remove foto.

Pontos de embarque:

- `POST /files/board-points/{boardPointId}`: upload de foto.
- `GET /files/board-points`: lista todas as fotos.
- `GET /files/board-points/{boardPointId}/all`: lista fotos do ponto.
- `GET /files/board-points/{fileId}`: busca foto.
- `PUT /files/board-points/{fileId}`: substitui foto.
- `DELETE /files/board-points/{fileId}`: remove foto.

Mapas de calor:

- `POST /files/heat-map/{routeId}`: upload de mapa de calor.
- `GET /files/heat-map/{routeId}/all`: lista mapas de uma rota.
- `GET /files/heat-map/{fileId}`: busca arquivo.
- `PUT /files/heat-map/{fileId}`: substitui arquivo.
- `DELETE /files/heat-map/{fileId}`: remove arquivo.

Infra:

- `GET /files/health-check`
- `/files/v3/api-docs`
- `/files/swagger-ui.html`

## Categorias

- `PROFILE_PIC`
- `DOCUMENT`
- `BUS_PHOTO`
- `INSTITUTION_PIC`
- `BOARD_POINT_PIC`
- `ROUTE_BOARD_POINT_HEAT_MAP`

## MinIO

Propriedades default:

- `MINIO_URL=http://localhost:9000`
- `MINIO_ROOT_USER=admin`
- `MINIO_ROOT_PASSWORD=admin123`
- `MINIO_BUCKET_NAME=rota-facil`

## Eventos

Publica em `file.events`:

- `file.created`
- `file.updated`
- `file.deleted`

Consome eventos para limpeza:

- `user.deleted`
- `prefecture.deleted`
- `institution.deleted`
- `boarding.deleted`
- `bus.deleted`

## Banco de dados

- Default: `jdbc:postgresql://localhost:5437/file_database`
- Usuario default: `rota-facil`
- Senha default: `admin`
- Migrations: `src/main/resources/db/migration`

## Como rodar

Pre-requisitos:

- Java 21.
- PostgreSQL com banco `file_database`.
- Eureka.
- RabbitMQ.
- MinIO com bucket configurado/criavel.

Comando:

```bash
cd file-service
./mvnw spring-boot:run
```

## Especializacao

Este servico e responsavel apenas por arquivos e seus metadados. Nao deve implementar regra de negocio de usuarios, rotas ou lugares alem de associar arquivos a donos/categorias.
