# What is it?

This project is a PoC (proof of concept) that you can verify if your MongoDB's queries use an index in a project that
uses Spring Data MongoDB.

# What is already done?

1. I've managed to plug in a place where I can obtain a `Query` object that is later used by MongoDB's driver.
2. I map a `Query` to an [explain](https://www.mongodb.com/docs/manual/reference/command/explain/)
   command and run the command.
3. After that, I check if a `winninPlan` is
   not [COLLSCAN](https://www.mongodb.com/docs/manual/reference/explain-results/#collection-scan-vs.-index-use)
   nor [SORT](https://www.mongodb.com/docs/manual/reference/explain-results/#sort-stage).
4. If this assertion fails, an exception is thrown.

# What needs to be done?

To make it production-ready there a few things that should be done.

1. Figure out a way to use more stable API than internal often package-private methods of Spring Data MongoDB.
2. Code it from ground up using TDD - I was exploring much, so code doesn't have much quality. It's just a PoC.
3. Check other actions [CRUD operations](https://www.mongodb.com/docs/manual/crud/).
4. Support sharded collections - results of the explain command are slightly different.
5. Separate example repository to a new module that is not published.
7. Make this feature available as
   an [auto-configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-auto-configuration)
8. Publish it :)
