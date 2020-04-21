package wolox.bootstrap.services

import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import spock.lang.Specification
import wolox.bootstrap.repositories.LogRepository

@DataJpaTest
@ComponentScan
class InformationLoggingServiceSpec extends Specification {

    @SpringBean
    LogRepository logRepository = Mock()

    @Autowired
    InformationLoggingService service

    def "check logging"() {
        given: "add an entry to the log"
        service.log("Test_Works")
        and: "read the file log"
        String line = null
        def fileReader = new FileReader(service.getFileDestination())
        def bufferedReader = new BufferedReader(fileReader)
        int count = 0
        while ((line = bufferedReader.readLine()) != null && count <= 1) {
            if (line.contains("Test_Works")) {
                count++
            }
        }
        bufferedReader.close()

        expect:
        count == 1
    }
}