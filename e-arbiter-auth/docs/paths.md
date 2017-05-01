## Resources
### Audit-events-mvc-endpoint

Audit Events Mvc Endpoint

#### findByPrincipalAndAfterAndType
```
GET /auditevents
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|principal|principal|false|string||
|QueryParameter|after|after|false|string (date-time)||
|QueryParameter|type|type|false|string||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### findByPrincipalAndAfterAndType
```
GET /auditevents.json
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|principal|principal|false|string||
|QueryParameter|after|after|false|string (date-time)||
|QueryParameter|type|type|false|string||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

### Basic-error-controller

Basic Error Controller

#### errorHtml
```
POST /error
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ModelAndView|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* text/html

#### errorHtml
```
HEAD /error
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ModelAndView|
|204|No Content|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|


##### Consumes

* application/json

##### Produces

* text/html

#### errorHtml
```
OPTIONS /error
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ModelAndView|
|204|No Content|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|


##### Consumes

* application/json

##### Produces

* text/html

#### errorHtml
```
PUT /error
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ModelAndView|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* text/html

#### errorHtml
```
PATCH /error
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ModelAndView|
|204|No Content|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|


##### Consumes

* application/json

##### Produces

* text/html

#### errorHtml
```
GET /error
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ModelAndView|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* text/html

#### errorHtml
```
DELETE /error
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ModelAndView|
|204|No Content|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|


##### Consumes

* application/json

##### Produces

* text/html

### Endpoint-mvc-adapter

Endpoint Mvc Adapter

#### invoke
```
GET /features.json
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /features
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /autoconfig
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /info.json
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /beans
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /configprops
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /archaius
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /mappings.json
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /autoconfig.json
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /configprops.json
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /info
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /archaius.json
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /dump
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /beans.json
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /dump.json
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /mappings
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /trace
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /trace.json
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

### Environment-manager-mvc-endpoint

Environment Manager Mvc Endpoint

#### value
```
POST /env
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|params|params|true|||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### invoke
```
GET /env
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### reset
```
POST /env/reset
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

### Environment-mvc-endpoint

Environment Mvc Endpoint

#### value
```
POST /env
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|params|params|true|||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### invoke
```
GET /env
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /env.json
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### value
```
GET /env/{name}
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|PathParameter|name|name|true|string||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

### Generic-postable-mvc-endpoint

Generic Postable Mvc Endpoint

#### invoke
```
POST /pause.json
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### invoke
```
POST /resume.json
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### invoke
```
POST /resume
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### invoke
```
POST /pause
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

### Health-mvc-endpoint

Health Mvc Endpoint

#### invoke
```
GET /health.json
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /health
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

### Heapdump-mvc-endpoint

Heapdump Mvc Endpoint

#### invoke
```
GET /heapdump.json
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|live|live|false|boolean|true|


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/octet-stream

#### invoke
```
GET /heapdump
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|live|live|false|boolean|true|


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/octet-stream

### Hystrix-stream-endpoint

Hystrix Stream Endpoint

#### handle
```
POST /hystrix.stream/**
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ModelAndView|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### handle
```
HEAD /hystrix.stream/**
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ModelAndView|
|204|No Content|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### handle
```
OPTIONS /hystrix.stream/**
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ModelAndView|
|204|No Content|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### handle
```
PUT /hystrix.stream/**
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ModelAndView|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### handle
```
PATCH /hystrix.stream/**
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ModelAndView|
|204|No Content|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### handle
```
GET /hystrix.stream/**
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ModelAndView|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### handle
```
DELETE /hystrix.stream/**
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ModelAndView|
|204|No Content|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|


##### Consumes

* application/json

##### Produces

* */*

### Login-controller

Login Controller

#### failLogin
```
GET /failLogin
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|string|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### login
```
GET /login
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|string|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

### Metrics-mvc-endpoint

Metrics Mvc Endpoint

#### invoke
```
GET /metrics.json
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### value
```
GET /metrics/{name}
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|PathParameter|name|name|true|string||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

#### invoke
```
GET /metrics
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* application/vnd.spring-boot.actuator.v1+json
* application/json

### Restart-mvc-endpoint

Restart Mvc Endpoint

#### invoke
```
POST /restart
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### invoke
```
POST /restart.json
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

### Service-registry-endpoint

Service Registry Endpoint

#### setStatus
```
POST /service-registry/instance-status
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|BodyParameter|status|status|true|string||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### getStatus
```
GET /service-registry/instance-status
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ResponseEntity|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

