fitty
=====

======SONAR==========
Download and unpack sonar. We use postgreSQL database our projects so we store the reports that database.
In the sonar's configuration file (.../sonar-x.y.z/conf/sonar.properties):

Comment this line:
	sonar.jdbc.url:     jdbc:h2:tcp://localhost:9092/sonar

And PostgreSQL section uncomment and modify the lines like this:

	#----- PostgreSQL 8.x/9.x
	# Comment the embedded database and uncomment the following property to use PostgreSQL
	sonar.jdbc.url:                            jdbc:postgresql://localhost:5432/sonar
	
	# Optional properties
	sonar.jdbc.driverClassName:                org.postgresql.Driver
	
	# Uncomment the following property if the PostgreSQL account has permissions to access multiple schemas,
	# for example sonar schemas with different versions. In that case, use the same property during project analysis
	(-Dsonar.jdbc.schema=<schema>)
	sonar.jdbc.schema:                         public
	

Add new profile to maven's configuration file "M2_HOME/conf/settings.xml" :
  
	<profile>
		<id>sonar</id>
			<activation>
			<activeByDefault>true</activeByDefault>
			</activation>
		<properties>
			<sonar.jdbc.url>jdbc:postgresql://localhost:5432/sonar</sonar.jdbc.url>
			<sonar.jdbc.driver>org.postgresql.Driver</sonar.jdbc.driver>
			<sonar.jdbc.username>postgres</sonar.jdbc.username>
			<sonar.jdbc.password>root</sonar.jdbc.password>
			<!-- SERVER ON A REMOTE HOST -->
			<sonar.host.url>http://localhost:9000</sonar.host.url>
		</properties>
	</profile>

Have to add in project's pom.xml:
	<sonar.language>java</sonar.language>
	<sonar.dynamicAnalysis>true</sonar.dynamicAnalysis>
	<sonar.core.codeCoveragePlugin>cobertura</sonar.core.codeCoveragePlugin>	
	
Run:
	Project's root (.../fitness-studio/ where exists pom.xml) maven sonar:sonar
