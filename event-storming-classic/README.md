# Event Storming Board Service
Repository for code of backend service for event storming board application

# User Stories
As a requirements to our application I've created set of user stories:

* As a User of Application I want to be able to create new team in order to collaborate with other team members

_Note: We need to create endpoint that allow users creating new team. `POST /teams` and we provide team name and any description. `GET /teams/{id}` returns details of team with given id._
___
* As a User of Application I want to be able to create new board under specified team in order to start "event storming" meeting from stratch

_Note: In this case we have to create endpoint that allow operating on boards inside teams `POST /teams/{id}/boards`, `GET /teams/{id}/boards`, `GET /teams/{id}/boards/{boardId}`_
___
* As a User of Application I want to be able to stick new event card on board in order to inform other meeting members about domain event that can happen in business process

_Note: Considered endpoints: `/boards/{id}/items` + DTO with details  or separate endpoints for each resource such as events, doubts, commands etc that can occur as a card on board_
___
* As a User of Application I want to be able to stick new card with question/doubts to event to inform business stakeholders that more explanation is needed.

_Note: Same as above_
___
* As a User of Application I want to be able to stick card on board with additional explanation in order to precise requirement

_Note: Same as above_
___
* As a User of Application I want to be able to stick card on board with command in order to precise what happens in system to trigger given event.

_Note: Same as above_
___
* As a User of Application I want to ba able to assign an Actor to command in order to provide information which user executes command.

_Note: As long as actors are assigned to command we could use `POST /boards/{b_id}/commands/{comm_id}/actors` endpoint. (endpoint depends on way how we are going to desing rest resource for board items [cards])_
___
* As a User of Application I want to be able to add an aggregate in order to group previously created domain events

_Note: As long as actors are assigned to command we could use `POST /boards/{b_id}/aggregates` endpoint._
___
* As a User of Application I want to be informed about each creation and modification datetime

_Note: Only thing to implementing here is Jpa Auditing_
___
* As a User of Application I want that all sticky notes remember their places on board in order to see same view of board after rerun of application

_Note: Each item that is visible on board should have coordinates stored in database_
___
* As a User of Application I want to work with sticky notes like in real world in order to reflect on real board and real sticky notes

_Note: Save changes on each move and edition of text_
___
* As a User of Application I want to be able add event which causes that customer spends money and which events give income in order to generate report of profitability of designed system.

_Note: It's item that can be assigned to domain event to provide details about expenditure or income_
___
