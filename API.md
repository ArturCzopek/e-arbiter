## Authentication Module

Data Controller

=======
#### getClientUrl
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

#### getClientUrl
```
HEAD /api/data/clientUrl
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|string|
|204|No Content|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### getClientUrl
```
POST /api/data/clientUrl
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|string|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### getClientUrl
```
DELETE /api/data/clientUrl
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|string|
|204|No Content|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### getClientUrl
```
PUT /api/data/clientUrl
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|string|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### getClientUrl
```
PATCH /api/data/clientUrl
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|string|
|204|No Content|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### getClientUrl
```
OPTIONS /api/data/clientUrl
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|string|
|204|No Content|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|


##### Consumes

* application/json

##### Produces

* */*

User Controller

#### Returns a token for current logged in user. Token is widely used in app to authenticate user
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

#### Returns a current logged in user based on passed token
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

## Executor Module

Executor Controller

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

## Tournament Module

Tournament Controller

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

