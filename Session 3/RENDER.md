# PostgreSQL on Render

1. Create a Render Postgres database in the same account and region as the web service.
2. Open this database in pgAdmin using the External Database URL details (host, port, database, user, password; SSL mode Require).
3. Use pgAdmin Query Tool to execute schema.sql against this database once.
4. In the Render web service, go to Environment and add DATABASE_URL with the database's Internal Database URL. Paste it as-is, beginning with postgresql:// (or postgres://). Do not add jdbc: or commit this secret to Git.
5. Deploy the updated code including WEB-INF/lib/postgresql-42.7.14.jar. If the Git repo root is LTWeb, set Root Directory to Session 3, runtime Docker, Dockerfile path ./Dockerfile and health check path /index.html. The Docker image serves the app at /, so open https://YOUR-SERVICE.onrender.com/index.html (without /email_app).
6. Submit the form and check the users and surveys tables with pgAdmin.

Local IntelliJ: set DATABASE_URL to the External Database URL in the Tomcat run configuration's environment variables, then restart Tomcat. Without DATABASE_URL, local development keeps writing TSV files. Existing TSV rows are not migrated automatically.

Submitting an existing email updates that user's details and adds a new survey response. Both writes are committed in one transaction. A configured but unreachable database produces an error; it never silently falls back to TSV.

Never paste your database password or connection URL into chat or source code.
