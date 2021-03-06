# GlassFish és MongoDB egy Java-maven project-ben.

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

4. Navigálj a glassfish4/bin/ mappába és add ki a `./asadmin start-domain` parancsot. Ennek hatására elindul a GlassFish.
Leállítani a `./asadmin stop-domain` paranccsal lehet. Indítás után egy néhány soros üzenet jelenik meg, melyben egy portszám is megtalálható.
A `localhost:port` címen a glassfish beállításait lehet elérni, tipikusan a `4848` -as porton.

5. Mostmár lehet indítani a projectet: `mvn clean package glassfish:deploy` -al, de a MongoDB hiánya miatt még nem fog rendesen működni.


Megjegyzés: Ha egyszer már megvolt a deploy, akkor utána a `glassfish:redeploy` -al lehet megint deploy-olni.


A MongoDB Community Edition letöltése és beállítása az alábbi oldalon megtalálható lépésekre bontva:
https://docs.mongodb.com/v3.2/tutorial/install-mongodb-on-ubuntu/


A GlassFish és a MongoDB elindítása után a `mvn clean package glassfish:deploy` vagy `mvn clean package glassfish:redeploy`
parancs lefutási után a project elérhetővé válik a `localhost:8080/glassfish/welcome` oldalon.
