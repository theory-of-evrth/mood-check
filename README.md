# MoodCheck

A microservices-based MVP for mood tracking and suggestion.

## Services

- mood-service: Accepts mood logs and provides suggestions
- stats-service: Consumes logs and stores/analyzes them
- ActiveMQ: Message broker
- Postgres: Stats DB
- Keycloak: Auth server

## To Run

Manually run each respective service. 

(in the future, hopefully:)

```bash
docker-compose up --build
```

## Notes

- Use Postman or curl to test endpoints.
- Tokens required for /moods endpoint.