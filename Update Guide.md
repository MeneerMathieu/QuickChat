# Update Guide

How to update the project to the next Minecraft version.


1. Update Gradle. You can't continue without updating Gradle. If you continue without updating Gradle, it will not update later.

    ```./gradlew wrapper --gradle-version latest --distribution-type bin```

2. Update gradle.properties with info from https://fabricmc.net/develop/
3. Reload Gradle (This might take a while)
4. If code changes are needed, make them.
5. Build the project and upload to Modrinth.
6. Done :D