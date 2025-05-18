# MoodCheck

A microservices-based MVP for mood tracking and random quote.

## Services

- mood-service: Accepts mood logs and provides suggestions
- stats-service: Consumes logs and stores/analyzes them
- ActiveMQ: Message broker
- H2: Stats data persistance (minimal)
- Keycloak: Auth server

## To Run

```bash
docker-compose up --build
```
After that, manually run mood-service and stats-service. 

## Notes

- Use Postman or curl to test endpoints.
- Tokens required for most endpoints, to check functionality without token, use /moods/check for moods-service, /ping for stats-service; or swagger-ui.html for either of them.
