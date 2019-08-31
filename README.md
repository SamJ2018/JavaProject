### flyway使用
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-maven-plugin</artifactId>
            <version>5.0.5</version>
            <configuration>
                <url>jdbc:mysql://localhost:3306/ssm_crud</url>
                <user>username</user>
                <password>password</password>
            </configuration>
        </plugin>
    </plugins>
</build>
```
*   在resources下创建db/migrate/V1__xxx_xxx.sql