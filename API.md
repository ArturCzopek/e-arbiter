## Authentication Module

Admin Controller

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


Auth Controller

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

#### Endpoint for CHECKING if user can be logged out (it doesn't logout!). It allows API Gateway to clear user from cache on the gateway level. Returns ok if is user's token to be logged out
```
POST /api/logout
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|HeaderParameter|oauth-token|oauth-token|false|string||


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

#### Returns a current logged in user based on passed token. If user does not exist, then is created.
```
GET /api/user
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|HeaderParameter|oauth-token|oauth-token|true|string||


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

## Tournament Module

Task Controller

#### submitCode
```
POST /api/task/submit
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|roles[0].id||false|integer (int64)||
|QueryParameter|roles[0].name||false|string||
|QueryParameter|id||false|integer (int64)||
|QueryParameter|name||false|string||
|BodyParameter|codeSubmitForm|codeSubmitForm|true|CodeSubmitForm||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|ExecutionResult|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*


Tournament Controller

#### Returns a page with the newest tournaments in which user does not participate
```
GET /api/all/newest
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
|200|OK|Page«TournamentPreview»|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### Returns a page with active tournaments' details in which logged in user participates
```
GET /api/all/active
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|roles[0].id||false|integer (int64)||
|QueryParameter|roles[0].name||false|string||
|QueryParameter|id||false|integer (int64)||
|QueryParameter|name||false|string||
|QueryParameter|query|query|false|string||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|Page«TournamentPreview»|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### Returns a page with finished tournaments' details in which logged in user participates
```
GET /api/all/finished
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|roles[0].id||false|integer (int64)||
|QueryParameter|roles[0].name||false|string||
|QueryParameter|id||false|integer (int64)||
|QueryParameter|name||false|string||
|QueryParameter|query|query|false|string||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|Page«TournamentPreview»|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### Returns a page with the most popular tournaments in which user does not participate
```
GET /api/all/popular
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
|200|OK|Page«TournamentPreview»|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### Returns a page with almost ended tournaments in which user does not participate
```
GET /api/all/ending
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
|200|OK|Page«TournamentPreview»|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### Returns specific information about tournament with passed id. Amount of information is depending on user access to that tournament
```
GET /api/user-details/{id}
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|roles[0].id||false|integer (int64)||
|QueryParameter|roles[0].name||false|string||
|QueryParameter|id||false|integer (int64)||
|QueryParameter|name||false|string||
|PathParameter|id|id|true|string||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|TournamentDetails|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

### Tournament-management-controller

Tournament Management Controller

#### Returns a page with active tournaments which were created by user
```
GET /api/management/active
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|roles[0].id||false|integer (int64)||
|QueryParameter|roles[0].name||false|string||
|QueryParameter|id||false|integer (int64)||
|QueryParameter|name||false|string||
|QueryParameter|query|query|false|string||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|Page«TournamentPreview»|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### Returns a page with draft tournaments which were created by user
```
GET /api/management/draft
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|roles[0].id||false|integer (int64)||
|QueryParameter|roles[0].name||false|string||
|QueryParameter|id||false|integer (int64)||
|QueryParameter|name||false|string||
|QueryParameter|query|query|false|string||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|Page«TournamentPreview»|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### Endpoint for activating a tournament with given id.
```
PUT /api/management/{id}/activate
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|roles[0].id||false|integer (int64)||
|QueryParameter|roles[0].name||false|string||
|QueryParameter|id||false|integer (int64)||
|QueryParameter|name||false|string||
|PathParameter|id|id|true|string||


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

#### Endpoint for removing user from the tournament. If user has been removed, tournament is returned, else returns 4xx or 5xx code with error description
```
PUT /api/management/{id}/remove-user/{user-id}
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|roles[0].id||false|integer (int64)||
|QueryParameter|roles[0].name||false|string||
|QueryParameter|id||false|integer (int64)||
|QueryParameter|name||false|string||
|PathParameter|id|id|true|string||
|PathParameter|user-id|user-id|true|integer (int64)||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|Tournament|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### Endpoint for adding a new tournament. If is ok, then returns added tournament, else returns 4xx or 5xx code with error description
```
POST /api/management/save
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|roles[0].id||false|integer (int64)||
|QueryParameter|roles[0].name||false|string||
|QueryParameter|id||false|integer (int64)||
|QueryParameter|name||false|string||
|BodyParameter|tournament|tournament|true|Tournament||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|Tournament|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### Returns a page with finished tournaments which were created by user
```
GET /api/management/finished
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|roles[0].id||false|integer (int64)||
|QueryParameter|roles[0].name||false|string||
|QueryParameter|id||false|integer (int64)||
|QueryParameter|name||false|string||
|QueryParameter|query|query|false|string||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|Page«TournamentPreview»|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### Endpoint for deleting a tournament with given id.
```
PUT /api/management/{id}/delete
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|roles[0].id||false|integer (int64)||
|QueryParameter|roles[0].name||false|string||
|QueryParameter|id||false|integer (int64)||
|QueryParameter|name||false|string||
|PathParameter|id|id|true|string||


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

#### Endpoint for extending tournament deadline. If tournament deadline has been extended, tournament is returned, else returns 4xx or 5xx code with error description
```
PUT /api/management/{id}/extend/{duration-in-sec}
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|roles[0].id||false|integer (int64)||
|QueryParameter|roles[0].name||false|string||
|QueryParameter|id||false|integer (int64)||
|QueryParameter|name||false|string||
|PathParameter|id|id|true|string||
|PathParameter|duration-in-sec|duration-in-sec|true|integer (int64)||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|Tournament|
|201|Created|No Content|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

#### Endpoint for fetching Tournament by id and user id.
```
GET /api/management/{id}
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|roles[0].id||false|integer (int64)||
|QueryParameter|roles[0].name||false|string||
|QueryParameter|id||false|integer (int64)||
|QueryParameter|name||false|string||
|PathParameter|id|id|true|string||


##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|Tournament|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

### User-action-controller

User Action Controller

#### Endpoint for leaving from an existing and active tournament.
```
POST /api/user-action/leave
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|roles[0].id||false|integer (int64)||
|QueryParameter|roles[0].name||false|string||
|QueryParameter|id||false|integer (int64)||
|QueryParameter|name||false|string||
|BodyParameter|tournamentUserActionRequest|tournamentUserActionRequest|true|TournamentUserActionRequest||


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

#### Endpoint for joining to an existing and active tournament.
```
POST /api/user-action/join
```

##### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|roles[0].id||false|integer (int64)||
|QueryParameter|roles[0].name||false|string||
|QueryParameter|id||false|integer (int64)||
|QueryParameter|name||false|string||
|BodyParameter|tournamentUserActionRequest|tournamentUserActionRequest|true|TournamentUserActionRequest||


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

## Tournament Results Module

Results Controller

#### getAllResults
```
GET /api/all
```

##### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|Result array|
|401|Unauthorized|No Content|
|403|Forbidden|No Content|
|404|Not Found|No Content|


##### Consumes

* application/json

##### Produces

* */*

