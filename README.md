# GlassFish-with-Java-and-maven
Egy egyszerű Java-Servlet app, ami a GlassFish szervert használja.

GlassFish beüzemelése Linux (esetemben Ubuntu MATE 16.04) alatt:

1. Töltsd le a glassfish-4.1.1.zip fájlt innen: https://glassfish.java.net/download.html

2. Csomagold ki a glassfish-4.1.1.zip fájlt egy tetszőleges helyre.

3. A maven home mappába (.m2) hozz létre egy settings.xml fájlt, az alábbi tartalommal:
```
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" 
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
    <profiles>
        <profile>
            <id>glassfish-context</id>
            <properties>
                <local.glassfish.home>/opt/glassfish4/glassfish</local.glassfish.home>
                <local.glassfish.user>admin</local.glassfish.user>
                <local.glassfish.domain>domain1</local.glassfish.domain>
                <local.glassfish.passfile>
            ${local.glassfish.home}/domains/${local.glassfish.domain}/config/domain-passwords
                </local.glassfish.passfile>
            </properties>
        </profile>
    </profiles>
 
    <activeProfiles>
        <activeProfile>glassfish-context</activeProfile>
    </activeProfiles>
</settings>
```
A local.glassfish.home -ba írd be a korábban kicsomagolt mappában lévő glassfish mappát. Én a /opt -ba csomagoltam ki.

4. Navigálj a glassfish4/bin/ mappába és add ki az "asadmin start-domain" parancsot. Ennek hatására elindul a GlassFish.
5. Mostmár lehet indítani a projectet: mvn clean package glassfish:deploy

A böngészőben a
`localhost:8080/glassfish/welcome`
oldalra menj, ahol egy kérsoros üzenetet kell látnod.

Megjegyzés: Ha egyszer már megvolt a deploy, akkor utána a `glassfish:redeploy` -al lehet megint deploy-olni.
