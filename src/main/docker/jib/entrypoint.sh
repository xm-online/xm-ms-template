#!/bin/sh

set -e

if [ -d "/run/secrets" ]
then
    secrets=`find  /run/secrets/ -maxdepth 1 -type f ! -name "*FILE"  -exec basename {} \;`
    for s in $secrets
    do
        echo "set env $s"
        export "$s"="$(cat /run/secrets/$s)"
    done
fi

echo "The application will start in ${JHIPSTER_SLEEP}s..." && sleep ${JHIPSTER_SLEEP}

exec java -Djava.security.egd=file:/dev/./urandom -cp $( cat /app/jib-classpath-file ) $( cat /app/jib-main-class-file )
