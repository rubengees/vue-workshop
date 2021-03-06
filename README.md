# Vue Workshop

German presentation and workshop around [Vue.js](https://vuejs.org/).<br>
Direct link to the presentation [here](https://rubengees.github.io/vue-workshop).

The presentation covers the basic concepts, compares it to other frameworks and shows the usage.<br>
The frontend and backend folders contain a simple note list project to implement.

### Prerequisites

- [Java 1.8 JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or higher
- [Node.js 8](https://nodejs.org/en/) or higher
- [Yarn](https://yarnpkg.com/lang/en/) (Not required but recommended)
- JavaScript IDE, e.g. [Visual Studio Code](https://code.visualstudio.com/)

### Backend

The _backend_ directory contains a ready to use REST-API implementation for working with notes.

The following APIs are available:

| Method | endpoint        | description                              |
| ------ | --------------- | ---------------------------------------- |
| GET    | /api/notes      | Returns all available notes as an array. |
| GET    | /api/notes/{id} | Returns a specific note based on the id. |
| POST   | /api/notes      | Creates a new note.                      |
| PUT    | /api/notes/{id} | Updates a specific note based on the id. |
| DELETE | /api/notes/{id} | Deletes a specific note based on the id. |

A note is structured like this:

```json
{
  "content": "the content",
  "isDone": true
}
```

You can start the server with `./gradlew bootRun -Dserver.port=8000`.<br>
Of course, you can choose any port you like, apart from `8080`, which will be used by the frontend.

### Frontend

The _frontend_ directory contains a vue skeletton with some predefined code, which should help getting started.<br>
You will mainly work in the `App.vue` file. See the presentation and the comments in that file.

After installing the dependencies with running `yarn`, the server can be started with `yarn start`
and is then accessible on http://localhost:8080.<br>
It also runs `lint` on every code change, so be sure to have a look at that window once in a while.

The _frontend-solution_ directory contains a possible solution to compare yours to.
