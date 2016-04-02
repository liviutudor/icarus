#!/bin/bash

# Updates the dependency lock
./gradlew updateLock saveLock --refresh-dependencies -PdependencyLock.updateDependencies=*

