**Nexus Backend Client Plugin**

This plugin implements all services defined inside nexus-api and re-routes those calls
to nexus backend server. Be sure to set environment variables to access nexus backend server.

Environment variables:

NEXUS_BACKEND_PROTOCOL - protocol under which nexus backend is reachable (http or https)
NEXUS_BACKEND_BASE_URL - base url for nexus backend
NEXUS_BACKEND_PORT - port number for nexus backend
KAFKA_SERVER_URL - address and port of kafka server