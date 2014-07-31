import com.google.gson.JsonObject
import io.elastic.api.JSON
import spock.lang.*

@Unroll
class JSONSpec extends Specification {

    def "parse JSON object from #input results in #result"() {
        expect:
        JSON.parseJsonObject(input) == result

        where:
        input << [null, "{}"]
        result << [null, new JsonObject()]
    }

    def "parsing a JSON array as JSON fails"() {
        when:
        JSON.parseJsonObject("[]")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "[] cannot be parsed to a JsonObject"
    }
}