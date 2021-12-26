# Todo Application With React Spring boot

This project is Todo Application


## Skill Set
* H2 InMomory DB
* Spring Boot v2.5.5
* Spring Data JPA
* React Js

## Development Environment
* openjdk version "11.0.10" 2021-01-19 LTS
* node v17.2.0
* npm v8.1.4
* yarn 1.22.4
* IntelliJ Community 2021.1

## Run and Install

[comment]: <> (### Working Backend Server in IntelliJ)
### Working Server in IntelliJ

#### Steps:

1) Command line
```
https://github.com/willdonejw/todo-rs.git
```
2) IntelliJ
```
File -> Open -> todo-rs\pom.xml -> Open as Project
```
3) Run
```
com/example/todo/TodoRsApplication.java.main() 실행 - (단축키: alt + f10)
```

4) Connection
```
http://localhost:8080/
```

[comment]: <> (### Working Frontend Server in vscode)

[comment]: <> (#### Steps:)

[comment]: <> (1&#41; vscode)

[comment]: <> (```)

[comment]: <> (File -> Open Folders -> todo-rs\ui 선택)

[comment]: <> (```)

[comment]: <> (2&#41; command)

[comment]: <> (* ctrl + `: vscode bash 실행)

[comment]: <> (```)

[comment]: <> (npm install)

[comment]: <> (yarn start)

[comment]: <> (```)

### Build
* 빌드시 node 버전에 따른 에러 발생 시
  * Error: error:0308010C:digital envelope routines::unsupported
  * `yarn add react-script` 후 `mvn clean package`
```
mvn clean package
```

### Deploy

#### jar 파일로 빌드 시
```
cd todo-rs/target
java -jar todo-rs-0.0.1-SNAPSHOT.jar
```

#### war 파일로 빌드 시

1. SpringBootServletInitializer 상속
2. configure() 를 overriding
3. pom.xml 수정
   1. Embed servlet을 provider로 수정
   2. packaging: jar -> war 수정
  
###### TodoRsApplication
```
@SpringBootApplication
public class TodoRsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TodoRsApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(TodoRsApplication.class);
	}
}
```

###### pom.xml
```
<packaging>war</packaging>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
    <scope>provided</scope>
</dependency>
```