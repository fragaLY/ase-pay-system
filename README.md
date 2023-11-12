# ASEPAY Service
[![ASE PAY Service CI/CD](https://github.com/fragaLY/ase-pay-system/actions/workflows/ase-pay-service.yml/badge.svg)](https://github.com/fragaLY/ase-pay-system/actions/workflows/ase-pay-service.yml)

## DIGITAL BANK - A$EPAY

## How to run

Data is stored and handled by PostgreSQL. See docker-compose.yml:

```yml
version: "3.8"

services:

  postgres:
    image: postgres:16-alpine
    restart: always
    environment:
      POSTGRES_DB: "asepay"
      POSTGRES_USER: "user"
      POSTGRES_PASSWORD: "password"
    ports:
      - 5432:5432
    volumes:
      - ./data:/var/lib/postgresql/data
```

Feel free to run it to work locally.

To boot up the application use ```./gradlew bootRun``` command or pull the image ```docker pull fragaly/asepay-system```.

Check that system is ready to receive the traffic: ```http://localhost:8080/actuator/health/readiness```

To operate with the system use Swagger: ```http://localhost:8080/swagger-ui/index.html```

### How to test

There are predefined users in the system: ```User1, User2, User3```

|Username | UserId |
|---      | ---    |
|User1    | 1      |
|User2    | 2      |
|User3    | 3      |

User1 supported currencies and initial balances:

|Currency | Balance |
|---      | ---     |
|USD      | 10001   |
|EUR      | 200     |
|BRL      | 300     |
|ARS      | 400     |
|UYU      | 500     |

User2 supported currencies and initial balances:

|Currency | Balance |
|---      | ---     |
|USD      | 1000    |
|EUR      | 2000    |
|BRL      | 3000    |
|ARS      | 4000    |
|UYU      | 5000    |

User3 supported currencies and initial balances:

|Currency | Balance |
|---      | ---     |
|USD      | 10000   |

See the Open Api documentation to be familiar with service API:

```yaml

openapi: 3.0.1
info:
  title: ASEPAY Service
  description: Documentation APIs v1.0
  version: 1.0.0-RC1
servers:
  - url: http://localhost:8080
    description: Generated server url
tags:
  - name: Actuator
    description: Monitor and interact
    externalDocs:
      description: Spring Boot Actuator Web API Documentation
      url: https://docs.spring.io/spring-boot/docs/current/actuator-api/html/
paths:
  /api/v1/transfer:
    post:
      tags:
        - ledger-api
      operationId: transfer
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/TransactionPayload'
        required: true
      responses:
        '200':
          description: OK
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/LedgerResponse'
        '400':
          description: Bad Request
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/ExceptionInformation'
        '500':
          description: Internal Server Error
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/ExceptionInformation'

components:
  schemas:
    ExceptionInformation:
      type: object
      properties:
        status:
          type: string
          enum:
            - 100 CONTINUE
            - 101 SWITCHING_PROTOCOLS
            - 102 PROCESSING
            - 103 EARLY_HINTS
            - 103 CHECKPOINT
            - 200 OK
            - 201 CREATED
            - 202 ACCEPTED
            - 203 NON_AUTHORITATIVE_INFORMATION
            - 204 NO_CONTENT
            - 205 RESET_CONTENT
            - 206 PARTIAL_CONTENT
            - 207 MULTI_STATUS
            - 208 ALREADY_REPORTED
            - 226 IM_USED
            - 300 MULTIPLE_CHOICES
            - 301 MOVED_PERMANENTLY
            - 302 FOUND
            - 302 MOVED_TEMPORARILY
            - 303 SEE_OTHER
            - 304 NOT_MODIFIED
            - 305 USE_PROXY
            - 307 TEMPORARY_REDIRECT
            - 308 PERMANENT_REDIRECT
            - 400 BAD_REQUEST
            - 401 UNAUTHORIZED
            - 402 PAYMENT_REQUIRED
            - 403 FORBIDDEN
            - 404 NOT_FOUND
            - 405 METHOD_NOT_ALLOWED
            - 406 NOT_ACCEPTABLE
            - 407 PROXY_AUTHENTICATION_REQUIRED
            - 408 REQUEST_TIMEOUT
            - 409 CONFLICT
            - 410 GONE
            - 411 LENGTH_REQUIRED
            - 412 PRECONDITION_FAILED
            - 413 PAYLOAD_TOO_LARGE
            - 413 REQUEST_ENTITY_TOO_LARGE
            - 414 URI_TOO_LONG
            - 414 REQUEST_URI_TOO_LONG
            - 415 UNSUPPORTED_MEDIA_TYPE
            - 416 REQUESTED_RANGE_NOT_SATISFIABLE
            - 417 EXPECTATION_FAILED
            - 418 I_AM_A_TEAPOT
            - 419 INSUFFICIENT_SPACE_ON_RESOURCE
            - 420 METHOD_FAILURE
            - 421 DESTINATION_LOCKED
            - 422 UNPROCESSABLE_ENTITY
            - 423 LOCKED
            - 424 FAILED_DEPENDENCY
            - 425 TOO_EARLY
            - 426 UPGRADE_REQUIRED
            - 428 PRECONDITION_REQUIRED
            - 429 TOO_MANY_REQUESTS
            - 431 REQUEST_HEADER_FIELDS_TOO_LARGE
            - 451 UNAVAILABLE_FOR_LEGAL_REASONS
            - 500 INTERNAL_SERVER_ERROR
            - 501 NOT_IMPLEMENTED
            - 502 BAD_GATEWAY
            - 503 SERVICE_UNAVAILABLE
            - 504 GATEWAY_TIMEOUT
            - 505 HTTP_VERSION_NOT_SUPPORTED
            - 506 VARIANT_ALSO_NEGOTIATES
            - 507 INSUFFICIENT_STORAGE
            - 508 LOOP_DETECTED
            - 509 BANDWIDTH_LIMIT_EXCEEDED
            - 510 NOT_EXTENDED
            - 511 NETWORK_AUTHENTICATION_REQUIRED
        code:
          type: integer
          format: int32
        message:
          type: string
    TransactionPayload:
      required:
        - recipientId
        - senderId
        - sourceCurrency
        - targetCurrency
      type: object
      properties:
        senderId:
          type: integer
          format: int64
        recipientId:
          type: integer
          format: int64
        amount:
          maximum: 10000
          exclusiveMaximum: false
          minimum: 10
          exclusiveMinimum: false
          type: number
        sourceCurrency:
          type: string
          enum:
            - USD
            - EUR
            - BRL
            - ARS
            - UYU
        targetCurrency:
          type: string
          enum:
            - USD
            - EUR
            - BRL
            - ARS
            - UYU
    LedgerResponse:
      required:
        - status
      type: object
      properties:
        status:
          type: string
          enum:
            - REQUESTED
            - COMPLETED
            - DECLINED
        reason:
          type: string

```

### System Design

![system-design](./static/asepay.drawio.png)
