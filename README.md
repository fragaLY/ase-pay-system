# ASEPAY Service
[![ASE PAY Service CI/CD](https://github.com/fragaLY/ase-pay-system/actions/workflows/ase-pay-service.yml/badge.svg)](https://github.com/fragaLY/ase-pay-system/actions/workflows/ase-pay-service.yml)

## DIGITAL BANK - A$EPAY

## System Design for the final solution

## How to run

Data is stored and handled by PostgreSQL. See docker-compose.yml:

```yml
version: "3.8"

services:

  postgres:
    image: postgres:16-alpine
    restart: always
    environment:
      POSTGRES_DB: ${PG_DB}
      POSTGRES_USER: ${PG_USER}
      POSTGRES_PASSWORD: ${PG_USER_PASSWORD}
    ports:
      - ${PG_PORT}:5432
    volumes:
      - ./data:/var/lib/postgresql/data
```

Feel free to run it to work localy. 
To boot up the application use ```./gradlew bootRun``` command or pull the image ```docker pull fragaly/asepay-system```.

See the Open Api documentation to be familiar with service API:

```yaml

TBD

```

### System Design

![system-design](./static/asepay.drawio.png)
