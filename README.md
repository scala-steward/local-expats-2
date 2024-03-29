NepaliUS
==============
Connect with local Nepali communities in the US

[NepaliUS.com](https://nepalius.com/)


## Contributing

### Database
Install [PostgreSQL](https://www.postgresql.org/download/)

Create a database named `nepalius` (configurable in `application.conf`)

### Backend

Start up sbt:

```shell
./sbt
```

Once sbt has loaded, you can start up the application

```sbtshell
> ~backend/reStart
```

This uses revolver which automatically rebuilds the application when you make code changes.

### Frontend

Run Dev Server

```shell
cd frontend
npm run dev
```

Compile the frontend ScalaJS to JavaScript

```shell
./sbt
```
Once sbt has loaded

```sbtshell
> ~frontend/fastLinkJS
```

### Tech Stacks
- [Scala 3](https://www.scala-lang.org/) for backend
- [TypeScript](https://www.typescriptlang.org/) for frontend
- [ZIO](https://zio.dev/) as an Effect system
- [ZIO-HTTP](https://zio.github.io/zio-http/) to write HTTP endpoints
- [ZIO-Quill](https://getquill.io/) for compile-time SQL query generation
- [Flyway](https://flywaydb.org/) for database migration
- [PostgreSQL](https://www.postgresql.org/) as database
- [ScalaJS](https://www.scala-js.org/) for frontend
- [Laminar](https://laminar.dev/) for reactive frontend
- [NextJS](https://nextjs.org/) as frontend framework
- [React](https://reactjs.org/) for building user interfaces
- [MUI](https://mui.com/) for UI components

### Development Approaches
- [Effectful](https://www.edward-huang.com/functional-programming/scala/monad/2020/06/21/what-is-effect-or-effectful-mean-in-functional-programming/) [Functional Reactive Programming](https://blog.danlew.net/2017/07/27/an-introduction-to-functional-reactive-programming/)
- [Domain Driven Design](https://martinfowler.com/bliki/DomainDrivenDesign.html)
- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [Mobile First Design](https://developer.mozilla.org/en-US/docs/Web/Progressive_web_apps/Responsive/Mobile_first)

