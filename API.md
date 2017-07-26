## Authentication Module

Admin Controller

#### Ping for admins. Filter returns 401 if user has no admin role what is checked by API Gateway.
```
GET /admin/ping
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

#### Returns all users from db. Filter returns 401 if user has no admin role what is checked by API Gateway.
```
GET /admin/all
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|DbUser array|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*


Auth Controller

#### Returns a current logged in user based on passed token. If user does not exist, then is created.
```
GET /api/user
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|HeaderParameter|oauth_token|oauth_token|true|string||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|User|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### Returns a current logged in user based on object from request from API Gateway
```
GET /api/me
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|roles[0].id||false|integer (int64)||
|QueryParameter|roles[0].name||false|string||
|QueryParameter|id||false|integer (int64)||
|QueryParameter|name||false|string||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|User|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### Returns a token for current logged in user. Token is widely used in app to authenticate user.
```
GET /api/token
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


Data Controller

#### Returns a web client url. It is used for redirecting from server to client
```
GET /api/data/clientUrl
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

## Executor Module

Executor Controller

#### example
```
GET /api/example
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ExecutionResult|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### Executes code
```
GET /api/execute
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|HeaderParameter|oauth_token|oauth_token|true|string||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|User|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### Hystrix demo
```
GET /api/hystrix
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

## Tournament Module

Tournament Controller

#### test
```
GET /api/test
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|Tournament array|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

## Tournament Results Module

Results Controller

#### test
```
GET /api/test
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

